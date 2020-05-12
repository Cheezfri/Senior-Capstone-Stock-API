package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;

/**
 * This is a forgot password page after click the 'forgot password' button
 */
@Route("forgotpassword")
public class ForgotPasswordView extends VerticalLayout {
    public static final String NAME = "forgotpassword";

    private VerticalLayout innerLayout;
    private Image logo;
    private Label title;
    private Label emptyLabel1;
    private FormLayout form1;
    private Label text1;
    private TextField email;
    private Label emptyLabel2;
    private Button reset;

    private String password;

    private DefaultApi defaultApi;

    /**
     * This screen contains input textfield for email address and button for sending email
     */
    public ForgotPasswordView() {

        defaultApi = new DefaultApi();

        innerLayout = new VerticalLayout();
        innerLayout.addClassName("innerlayout");
        innerLayout.setWidth("300px");

        logo = new Image("img/logo.png", "logo image");
        logo.addClassName("logo");

        title = new Label();
        title.setText("Forgot Password");

        emptyLabel1 = new Label("");
        emptyLabel1.setHeight("1em");

        form1 = new FormLayout();
        text1 = new Label();
        text1.setText("Email ID");
        email = new TextField();
        form1.add(text1, email);

        emptyLabel2 = new Label("");
        emptyLabel2.setHeight("2em");

        reset = new Button("Reset Password");
        reset.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reset.setWidthFull();

        /**
         * after click the reset button, the application will send the email to the email address
         */
        reset.addClickListener(e -> {

            try {
                password = defaultApi.userForgotPasswordPost(email.getValue());
            } catch (ApiException ex) {
                ex.printStackTrace();
            }

            if(password != null) {
                // Show the new password on the screen
                Dialog dialog = new Dialog();
                dialog.add(new Label("Your new password is " + password));
                Button closeButton = new Button("Close");
                closeButton.addClickListener(event -> {
                    dialog.close();
                });
                dialog.add(closeButton);
                dialog.open();
            } else {
                Notification.show("Reset password failed");
            }

        });

        innerLayout.add(logo, title, emptyLabel1, form1, emptyLabel2, reset);
        add(innerLayout);

        setAlignItems(Alignment.CENTER);
    }
}
