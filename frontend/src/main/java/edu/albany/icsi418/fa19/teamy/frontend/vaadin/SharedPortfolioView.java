package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.HoldingAsset;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.SharedPortfolio;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This screen is accessible by clicking shared portfolio list of SharedPortfolioManagement grid
 */
@Component
@VaadinSessionScope
public class SharedPortfolioView extends Dialog {

    private static final Logger log = LoggerFactory.getLogger(SharedPortfolioView.class);

    private VerticalLayout innerLayout;
    private FormLayout form_pn;
    private Label text_pn;
    private Label portfolioName;
    private FormLayout form_ow;
    private Label text_ow;
    private FormLayout form_sh;
    private Label text_sh;
    private Label sharedUser;
    private Label text_assetholding;
    private Grid<HoldingAsset> grid;
    private Button ok;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;
    private List<SharedPortfolio> sp_list = new ArrayList<>();
    private List<HoldingAsset> list = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private String ownerUserName = null;
    private SharedPortfolio sportfolio = new SharedPortfolio();

    /**
     * This screen is almost same as AddSharedPortfolio
     * but there is no changeable data in this screen
     * READONLY
     */
    public SharedPortfolioView(long sharedPortfolioId) {

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        innerLayout = new VerticalLayout();
        innerLayout.setWidth("500px");

        // Get shared portfolio object
        try {
            sp_list = defaultApi.portfolioShareSearchGet(userLogin.getUser().getId(), (long)0);
        } catch(ApiException ae) {
            log.info(ae.getCode() + " found in SharedPortfolioView while getting portfolio share search");
        }
        for(SharedPortfolio sp : sp_list) {
            if(sp.getId().equals(sharedPortfolioId)) {
                sportfolio = sp;
            }
        }
        log.info("sportfolio name: " + sportfolio.getName() +
                ", sportfolio id: " + sportfolio.getId() +
                ", owner id: " + sportfolio.getOwnerUserId() +
                ", shared with user email: " + sportfolio.getSharedWithUserEmail()
        );


        form_pn = new FormLayout();
        text_pn = new Label("Portfolio Name");
        portfolioName = new Label();
        portfolioName.setText(sportfolio.getName());
        form_pn.add(text_pn, portfolioName);

        form_ow = new FormLayout();

        //TODO: Sai's gonna make get owner name method, use that

        ownerUserName = sportfolio.getOwnerEmail();
        text_ow = new Label("Owner: " + ownerUserName);
        form_ow.add(text_ow);

        List<SharedPortfolio> sharedList = new ArrayList<>();

        form_sh = new FormLayout();
        text_sh = new Label("Shared with");
        sharedUser = new Label();
        sharedUser.setText(sportfolio.getSharedWithUserEmail());
        form_sh.add(text_sh, sharedUser);

        text_assetholding = new Label("Holding Asset List");

        grid = new Grid<>();
        grid.setWidth("400px");
        try {
            list = defaultApi.holdingAssetSearchGet(sharedPortfolioId);
        } catch (ApiException ae) {
            log.info(ae.getCode() + " found in SharedPortfolioView while getting holding asset search");
        }
        grid.setItems(list);
        grid.addColumn(HoldingAsset::getAssetName).setHeader("Asset Name");
        grid.addColumn(HoldingAsset::getNetValue).setHeader("Net Price");
        grid.addColumn(HoldingAsset::getQuantity).setHeader("Quantity");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        ok = new Button("Close");
        ok.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        ok.addClickListener(e -> {
            close();
        });

        innerLayout.add(form_pn, form_ow, form_sh, text_assetholding, grid, ok);


        add(innerLayout);
    }
}
