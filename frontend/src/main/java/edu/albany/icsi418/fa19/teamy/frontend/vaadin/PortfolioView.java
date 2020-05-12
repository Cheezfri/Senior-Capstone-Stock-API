package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.HoldingAsset;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Portfolio;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.PortfolioTransaction;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.SharedPortfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a screen after click the portfolio list in the portfolio management
 */
@Component
@VaadinSessionScope
public class PortfolioView extends Dialog {

    private static final Logger log = LoggerFactory.getLogger(PortfolioView.class);

    private VerticalLayout innerLayout;
    private Label text_pn;

    private FormLayout form_ow;
    private Label text_ow;

    private Label text_hold;
    private List<HoldingAsset> ha_list = new ArrayList<>();
    private Grid<HoldingAsset> ha_grid;

    private Label text_tran;
    private HorizontalLayout form_tran;
    private Button addButton;
    private Grid<PortfolioTransaction> grid;

    private List<SharedPortfolio> sh_list = new ArrayList<>();
    private Label text_sh;
    private HorizontalLayout form_sh;
    private Button shAddButton;
    private Grid<SharedPortfolio> sh_grid;

    private HorizontalLayout btns;
    private Button cancel;
    private Button save;


    private long portfolioId;
    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;
    private List<PortfolioTransaction> list = new ArrayList<>();


    public PortfolioView(Portfolio portfolio) {
        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        portfolioId = portfolio.getId();
        innerLayout = new VerticalLayout();
        innerLayout.setWidth("800px");

        text_pn = new Label("Portfolio Name: " + portfolio.getName());

        form_ow = new FormLayout();
        text_ow = new Label("Owner: " + userLogin.getUser().getEmail());
        form_ow.add(text_ow);


        text_hold = new Label("Holding Asset List");
        ha_grid = new Grid<>();
        ha_grid.setWidth("600px");
        try {
            ha_list = defaultApi.holdingAssetSearchGet(portfolio.getId());
        } catch(ApiException e) {
            log.info(e.getCode() + " found in PortfolioView while getting holding asset list");
        } catch(NullPointerException npe) {
            log.info("Null Pointer Exception happened in PortfolioView while getting holding asset list");
        }
        ha_grid.setItems(ha_list);
        ha_grid.addColumn(HoldingAsset::getAssetName).setHeader("Asset Name");
        ha_grid.addColumn(HoldingAsset::getNetValue).setHeader("Net Value");

        text_tran = new Label("Transaction List");
        form_tran = new HorizontalLayout();
        addButton = new Button("+");
        addButton.getElement().getStyle().set("margin-bottom", "auto");


        grid = new Grid<>();
        grid.setWidth("600px");

        try {
            list = defaultApi.portfolioTransactionSearchGet(portfolio.getId());
        } catch (ApiException e) {
            log.info(e.getCode() + " found in PortfolioView while getting transaction list");
        } catch (NullPointerException npe) {
            log.info("Null Pointer Exception happened in PortfolioView while getting transaction list");
        }
        grid.setItems(list);
        grid.addColumn(PortfolioTransaction::getAssetName).setHeader("Asset Name");
        grid.addColumn(PortfolioTransaction::getType).setHeader("Transaction Type");
        grid.addColumn(PortfolioTransaction -> PortfolioTransaction.getQuantity() * PortfolioTransaction.getPrice()).
                setHeader("Net Price");
        grid.addComponentColumn(item -> createRemoveButton(grid, item));
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // when user clicks the transaction list, it shows transaction view
        grid.addItemClickListener(e -> {
            TransactionView tv = new TransactionView(portfolio.getId(), e.getItem());
            tv.open();
        });

        form_tran.add(addButton, grid);

        try {
            sh_list = defaultApi.portfolioShareSearchGet((long)0, portfolioId);
        } catch (ApiException ae) {
            log.info(ae.getCode() + " found in Portfolio View while getting portfolio share search");
        }

        text_sh = new Label("Shared with");
        form_sh = new HorizontalLayout();

        shAddButton = new Button("+");
        shAddButton.getElement().getStyle().set("margin-bottom", "auto");

        sh_grid = new Grid<>();
        sh_grid.setItems(sh_list);
        sh_grid.addColumn(SharedPortfolio::getSharedWithUserEmail).setHeader("Email ID");
        sh_grid.addComponentColumn(item -> createRemoveButtonForSharedUser(sh_grid, item));
        sh_grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        form_sh.add(shAddButton, sh_grid);
        shAddButton.addClickListener(e -> {
            AddSharedPortfolio asp = new AddSharedPortfolio(portfolio.getId());
            asp.open();
        });


        addButton.addClickListener(e -> {
            AddTransaction aa =  new AddTransaction(portfolio.getId());
            aa.open();
        });

        btns = new HorizontalLayout();
        save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        save.addClickListener(e -> {
            // reset portfolio list
            PortfolioManagement portfolioManagement = new PortfolioManagement();
            List<Portfolio> pl = new ArrayList<>();
            try {
                pl = defaultApi.portfolioSearchGet(userLogin.getUser().getId(), null);
            } catch (ApiException ae) {
                log.info(ae.getCode() + " found in Portfolio View while getting portfolio search");
            }
            portfolioManagement.getGrid().setItems(pl);

            close();
        });
        btns.add(save);

        innerLayout.add(text_pn, form_ow, text_hold, ha_grid, text_tran, form_tran, text_sh, form_sh, btns);


        add(innerLayout);
    }


    /**
     * add '-' button next to transaction item
     * @param grid The grid of PortfolioTransaction
     * @param item The PortfolioTransaction item
     * @return '-' button
     */
    Button createRemoveButton(Grid<PortfolioTransaction> grid, PortfolioTransaction item) {
        Button button = new Button("-", e -> {

            ConfirmDialog confirmation = new ConfirmDialog();
            confirmation.setHeader("Delete Transaction");
            confirmation.setText("Are you sure you want to delete this transaction?");
            Button delete = new Button("Delete");
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            delete.addClickListener(ev -> {
                // Delete transaction
                try {
                    defaultApi.portfolioTransactionDeletePost(userLogin.getUser().getId(),item.getId());
                } catch (ApiException ae) {
                    log.info(ae.getCode() + " found in PortfolioView while deleting portfolio transaction");
                }

                // Refresh holding asset list after delete transaction
                try {
                    ha_list = defaultApi.holdingAssetSearchGet(portfolioId); // refresh holding asset list
                } catch (ApiException ae) {
                    log.info(ae.getCode() + " found in PortfolioView while refreshing holding asset list");
                }

                // Refresh transaction list after delete transaction
                try {
                    list = defaultApi.portfolioTransactionSearchGet(portfolioId); // refresh transaction list
                } catch (ApiException ae) {
                    log.info(ae.getCode() + " found in PortfolioView while refreshing transaction list");
                }

                ListDataProvider<PortfolioTransaction> dataProvider = (ListDataProvider<PortfolioTransaction>) grid
                        .getDataProvider();
                dataProvider.getItems().remove(item);
                dataProvider.refreshAll();
                confirmation.close();
            });
            Button cancel = new Button("Cancel");
            cancel.addClickListener(ev -> {
                confirmation.close();
            });
            confirmation.setConfirmButton(delete.getElement());
            confirmation.setCancelButton(cancel.getElement());
            confirmation.open();
        });
        return button;
    }

    /**
     * add '-' button next to shared user
     * @param sh_grid The grid of SharedPortfolio
     * @param item The SharedPortfolio item
     * @return '-' button
     */
    Button createRemoveButtonForSharedUser(Grid<SharedPortfolio> sh_grid, SharedPortfolio item) {
        Button button = new Button("-", e -> {

            ConfirmDialog confirmation = new ConfirmDialog();
            confirmation.setHeader("Stop Sharing Portfolio");
            confirmation.setText("Are you sure you want to stop sharing the portfolio with this user?");
            Button delete = new Button("Delete");
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            delete.addClickListener(ev -> {
                // stop sharing portfolio with user
                try {
                    defaultApi.portfolioShareDeletePost(userLogin.getUser().getId(), item.getId(), item.getSharedWithUserEmail());
                } catch (ApiException ae) {
                    log.info(ae.getCode() + " found in PortfolioView while deleting shared user");
                }

                // Refresh shared user list
                try {
                    sh_list = defaultApi.portfolioShareSearchGet((long)0, portfolioId);
                } catch (ApiException ae) {
                    log.info(ae.getCode() + " found in PortfolioView while refreshing shared user list");
                }

                ListDataProvider<SharedPortfolio> dataProvider = (ListDataProvider<SharedPortfolio>) sh_grid
                        .getDataProvider();
                dataProvider.getItems().remove(item);
                dataProvider.refreshAll();
                confirmation.close();
            });
            Button cancel = new Button("Cancel");
            cancel.addClickListener(ev -> {
                confirmation.close();
            });
            confirmation.setConfirmButton(delete.getElement());
            confirmation.setCancelButton(cancel.getElement());
            confirmation.open();
        });
        return button;
    }


}
