package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a page reset password page after authorized by email
 */
@Route("resetpassword")
public class ResetpasswordView extends VerticalLayout {
    public static final String NAME = "resetpassword";

    private VerticalLayout innerLayout;
    private Label title;
    private Label emptyLabel1;
    private FormLayout form0;
    private Label text0;
    private PasswordField current;
    private FormLayout form1;
    private Label text1;
    private PasswordField password;
    private FormLayout form2;
    private Label text2;
    private PasswordField repeat;
    private Label emptyLabel2;
    private Button confirm;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    private String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    private Pattern pwPattern = Pattern.compile(PASSWORD_REGEX);
    private Matcher pwMatcher;

    public ResetpasswordView() {

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        innerLayout = new VerticalLayout();
        innerLayout.addClassName("innerlayout");
        innerLayout.setWidth("300px");

        Image logo = new Image("img/logo.png", "logo image");
        logo.addClassName("logo");

        title = new Label();
        title.setText("Reset Password");

        emptyLabel1 = new Label("");
        emptyLabel1.setHeight("1em");

        form0 = new FormLayout();
        text0 = new Label();
        text0.setText("Current Password");
        current = new PasswordField();
        form0.add(text0, current);

        form1 = new FormLayout();
        text1 = new Label();
        text1.setText("New Password");
        password = new PasswordField();
        form1.add(text1, password);

        form2 = new FormLayout();
        text2 = new Label();
        text2.setText("Repeat Password");
        repeat = new PasswordField();
        form2.add(text2, repeat);

        emptyLabel2 = new Label("");
        emptyLabel2.setHeight("2em");

        confirm = new Button("Confirm");
        confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        confirm.setWidthFull();
        confirm.addClickListener(e -> {
            pwMatcher = pwPattern.matcher(password.getValue());
            if(!pwMatcher.matches()) {
                Notification.show("Invalid password format (at least 8 characters, contains upper case, lower case, digit and special char");
            } else if(!(password.getValue()).equals(repeat.getValue())) {
                Notification.show("The password does not match");
            } else {
                try {
                    defaultApi.userResetPasswordPost(userLogin.getUser().getId(), current.getValue(), password.getValue());
                } catch (ApiException ex) {
                    ex.printStackTrace();
                }
                // Show the new password on the screen
                Dialog dialog = new Dialog();
                dialog.add(new Label("Your password has been successfully updated!!!"));
                Button closeButton = new Button("Close");
                closeButton.addClickListener(event -> {
                    dialog.close();
                });
                dialog.add(closeButton);
                dialog.open();
            }



        });

        innerLayout.add(logo, title, emptyLabel1, form0, form1, form2, emptyLabel2, confirm);
        add(innerLayout);

        setAlignItems(Alignment.CENTER);
    }
}
