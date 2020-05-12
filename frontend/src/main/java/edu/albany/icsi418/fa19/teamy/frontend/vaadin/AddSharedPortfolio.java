package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import org.springframework.stereotype.Component;

/**
 * This screen is accessible from SharedPortfolio View's '+' button
 */
@Component
@VaadinSessionScope
public class AddSharedPortfolio extends Dialog {

    private VerticalLayout innerLayout;
    private FormLayout form_sh;
    private Label text_sh;
    private TextField sharedUser;
    private HorizontalLayout btns;
    private Button cancel;
    private Button save;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    /**
     * This screen contains Portfolio name, text field for shared users email id, and asset holding list
     */
    public AddSharedPortfolio(long portfolioId) {

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        innerLayout = new VerticalLayout();
        innerLayout.setWidth("500px");

        form_sh = new FormLayout();
        text_sh = new Label("Share with");
        sharedUser = new TextField();
        form_sh.add(text_sh, sharedUser);


        btns = new HorizontalLayout();
        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addClickListener(e -> {
            close();
        });
        save = new Button("Add");
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        save.addClickListener(e -> {
            try {
                defaultApi.portfolioShareAddPost(userLogin.getUser().getId(), portfolioId, sharedUser.getValue());
                Notification.show("Now, user(" + sharedUser.getValue() + ") shares this portfolio with you");
            } catch (ApiException ex) {
                ex.printStackTrace();
            }
            close();
        });
        btns.add(cancel, save);

        innerLayout.add(form_sh, btns);


        add(innerLayout);

    }
}
