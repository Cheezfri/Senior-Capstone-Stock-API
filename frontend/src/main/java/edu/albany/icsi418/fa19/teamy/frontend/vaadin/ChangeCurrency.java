package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This is a screen after click the 'change personal information' button of account management page
 */
@Component
@VaadinSessionScope
public class ChangeCurrency extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(ChangeCurrency.class);


    private Label title;
    private Label empty;
    private FormLayout form4;
    private Label text4;
    private ComboBox<String> currency;
    private Label empty2;
    private HorizontalLayout btns;
    private Button cancel;
    private Button save;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;


    /**
     * This screen contains text field for first name, last name and email address
     * and buttons for save and cancel processes
     */
    public ChangeCurrency() {

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        title = new Label("Change Currency");
        empty = new Label("");
        empty.setHeight("1em");

        form4 = new FormLayout();
        text4 = new Label("Preferred Currency");
        currency = new ComboBox<>();
        currency.setItems("USD", "GBP", "EUR", "JPY", "CNY");
        form4.add(text4, currency);

        empty2 = new Label("");
        empty2.setHeight("1em");

        btns = new HorizontalLayout();
        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        /**
         * after user clicks this button, user can cancel the changing process and leave this screen
         */
        cancel.addClickListener(e -> {

        });
        save = new Button("Save");
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        /**
         * after user clicks this button, user can save new personal information into the database
         */
        save.addClickListener(e -> {
            try {
                defaultApi.userChangeCurrencyPost(userLogin.getUser().getId(), currency.getValue());
                Notification.show("Your preferred currency changed to " + currency.getValue());
            } catch (ApiException ex) {
                log.info(ex.getCode() + " found in ChangeCurrency while saving new currency");
            }
        });

        btns.add(cancel, save);
        add(title, empty, form4, empty2, btns);

    }
}
