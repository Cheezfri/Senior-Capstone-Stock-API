package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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
 * This is a screen for end user after click the '+' button in portfolio management page
 */
@Component
@VaadinSessionScope
public class AddPortfolio extends Dialog {

    private static final Logger log = LoggerFactory.getLogger(AddPortfolio.class);

    private VerticalLayout innerLayout;
    private FormLayout form_pn;
    private Label text_pn;
    private TextField portfolioName;

    private HorizontalLayout btns;
    private Button cancel;
    private Button save;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    public AddPortfolio(){
        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        innerLayout = new VerticalLayout();
        innerLayout.setWidth("500px");

        form_pn = new FormLayout();
        text_pn = new Label("Portfolio Name");
        portfolioName = new TextField();
        form_pn.add(text_pn, portfolioName);

        btns = new HorizontalLayout();
        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(e -> {
            close();
        });
        save = new Button("Add");
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        save.addClickListener(e -> {
            // Add portfolio
            try {
                defaultApi.portfolioAddPost(userLogin.getUser().getId(), portfolioName.getValue(), "NORMAL");
                log.info("portfolio is added");
            } catch (ApiException ex) {
                log.info(ex.getCode() + " found in AddPortfolio while adding portfolio");
            }

            List<Portfolio> pflist = new ArrayList<>();
            UserView userView = new UserView();
            // Refresh grid and select
            try {
                pflist = defaultApi.portfolioSearchGet(userLogin.getUser().getId(), null);
                PortfolioManagement portfolioManagement = new PortfolioManagement();
                portfolioManagement.getGrid().setItems(pflist);
                List<String> pf_name_list = new ArrayList<>();
                for(Portfolio portfolio : pflist) {
                    pf_name_list.add(portfolio.getName());
                }
                UserView.select.setItems(pf_name_list);
            } catch (ApiException ae) {
                log.info(ae.getCode() + " found in AddPortfolio while refreshing grid and select");
            }

            close();
        });

        btns.add(cancel, save);
        innerLayout.add(form_pn, btns);
        add(innerLayout);
    }
}
