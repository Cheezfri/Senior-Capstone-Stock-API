package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a screen for end user after click the portfolio management button of navigation bar
 */
@Component
@VaadinSessionScope
public class PortfolioManagement extends HorizontalLayout {

    private static final Logger log = LoggerFactory.getLogger(PortfolioManagement.class);

    private Button addButton;
    private List<Portfolio> list = new ArrayList<>();
    private Grid<Portfolio> grid = new Grid<>();
    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    /**
     * There's a grid in portfolio management page
     * This grid contains portfolio list of the user
     * user can add, modify and delete portfolio in this screen
     */
    public PortfolioManagement(){
        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        try {
            list = defaultApi.portfolioSearchGet(userLogin.getUser().getId(), null);
        } catch (ApiException e) {
            log.info(e.getCode() + " found in portfolio management while getting portfolio list");
        } catch (NullPointerException npe) {
            log.info("Null Pointer Exception happened in PortfolioManagement while getting portfolio list");
        }

        

        addButton = new Button("+");
        addButton.getElement().getStyle().set("margin-bottom", "auto");

        grid.setWidth("1000px");
        grid.setItems(list);
        grid.addColumn(Portfolio::getName).setHeader("Portfolio Name");
        grid.addComponentColumn(item -> createRemoveButton(grid, item));
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        grid.addItemClickListener(e -> {
            UserView.select.setValue(e.getItem().getName());
            UserView.portfolioName.setText("Current Portfolio: " + UserView.select.getValue());
            if(e.getItem() != null) {
                log.info("Portfolio Name: " + e.getItem().getName());
                log.info("Portfolio ID: " + e. getItem().getId());
                PortfolioView pv = new PortfolioView(e.getItem());
                pv.open();
            }
        });

        GridContextMenu<Portfolio> contextMenu = new GridContextMenu<>(grid);
        GridMenuItem<Portfolio> share = contextMenu.addItem("Share Portfolio");
        share.addMenuItemClickListener(e -> {
            AddSharedPortfolio asp = new AddSharedPortfolio(e.getItem().get().getId());
            asp.open();
        });


        add(addButton, grid);

        addButton.addClickListener(e -> {
            AddPortfolio ap = new AddPortfolio();
            ap.open();
        });

    }

    /**
     * add '-' button next to portfolio item
     * @param grid The grid of Portfolio
     * @param item The Portfolio item
     * @return '-' button
     */
    Button createRemoveButton(Grid<Portfolio> grid, Portfolio item) {
        Button button = new Button("-", e -> {
            ConfirmDialog confirmation = new ConfirmDialog();
            confirmation.setHeader("Delete Portfolio");
            confirmation.setText("Are you sure you want to permanently delete this portfolio?");
            Button delete = new Button("Delete");
            delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
            delete.addClickListener(ev -> {
                // Delete portfolio
                try {
                    defaultApi.portfolioDeletePost(userLogin.getUser().getId(), item.getId());
                } catch (ApiException ex) {
                    log.info(ex.getCode() + " found in PortfolioManagement while deleting portfolio");
                }

                // refresh select
                try {
                    List<Portfolio> pf_list = new ArrayList<>();
                    pf_list = defaultApi.portfolioSearchGet(userLogin.getUser().getId(), null);
                    List<String> pf_name_list = new ArrayList<>();
                    for(Portfolio portfolio : pf_list) {
                        pf_name_list.add(portfolio.getName());
                    }
                    UserView.select.setItems(pf_name_list); // refresh select
                } catch (ApiException ae) {
                    log.info(ae.getCode() + " found in PortfolioManagement while refreshing select");
                }

                ListDataProvider<Portfolio> dataProvider = (ListDataProvider<Portfolio>) grid
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

    public Grid<Portfolio> getGrid() {
        return grid;
    }

}

