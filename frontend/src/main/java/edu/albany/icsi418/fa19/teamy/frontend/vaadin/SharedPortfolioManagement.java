package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.SharedPortfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This screen is accessible by Shared Portfolio button of the navigation bar
 */
@Component
@VaadinSessionScope
public class SharedPortfolioManagement extends HorizontalLayout {

    private static final Logger log = LoggerFactory.getLogger(SharedPortfolioManagement.class);

    private List<SharedPortfolio> list = new ArrayList<>();
    private Grid<SharedPortfolio> grid;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;


    /**
     * This screen contains portfolio information (portfolio name, shared email id, and holding asset list)
     * all information cannot modifiable by shared user
     */
    public SharedPortfolioManagement() {

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        try{
            list = defaultApi.portfolioShareSearchGet(userLogin.getUser().getId(), (long)0);
        } catch (ApiException ae) {
            log.info(ae.getCode() + " found in SharedPortfolioManagement while getting portfolioShareSearch");
        }


        grid = new Grid<>();
        grid.setWidth("1000px");
        grid.setItems(list);
        grid.addColumn(SharedPortfolio::getName).setHeader("Shared Portfolio Name");
        
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addItemClickListener(e -> {
            SharedPortfolioView spv =  new SharedPortfolioView(e.getItem().getId());
            spv.open();
        });

        add(grid);

    }


}
