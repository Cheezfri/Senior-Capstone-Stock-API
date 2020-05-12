package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.User;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.UserSignup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a sign up page after click 'sign up' button of login page
 */
@Route("signup")
public class SignUpView extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(SignUpView.class);

    public static final String NAME = "signup";

    private VerticalLayout innerLayout;
    private Label title;
    private Label emptyLabel1;
    private FormLayout form1;
    private Label text1;
    private TextField firstname;
    private FormLayout form2;
    private Label text2;
    private TextField lastname;
    private FormLayout form3;
    private Label text3;
    private TextField email;
    private FormLayout form4;
    private Label text4;
    private PasswordField password;
    private FormLayout form5;
    private Label text5;
    private PasswordField repeat;
    private FormLayout form6;
    private Label text6;
    private ComboBox<String> currency;
    private Label emptyLabel2;
    private Button signup;

    private String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern = Pattern.compile(EMAIL_REGEX);
    private Matcher matcher;

    private String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    private Pattern pwPattern = Pattern.compile(PASSWORD_REGEX);
    private Matcher pwMatcher;

    private DefaultApi defaultApi;

    public SignUpView() {

        defaultApi = new DefaultApi();

        innerLayout = new VerticalLayout();
        innerLayout.addClassName("innerlayout");
        innerLayout.setWidth("300px");

        Image logo = new Image("img/logo.png", "logo image");
        logo.addClassName("logo");

        title = new Label("Sign Up");

        emptyLabel1 = new Label("");
        emptyLabel1.setHeight("1em");

        form1 = new FormLayout();
        text1 = new Label("First Name");
        firstname = new TextField();
        form1.add(text1, firstname);

        form2 = new FormLayout();
        text2 = new Label("Last Name");
        lastname = new TextField();
        form2.add(text2, lastname);

        form3 = new FormLayout();
        text3 = new Label("Email ID");
        email = new TextField();
        form3.add(text3, email);

        form4 = new FormLayout();
        text4 = new Label("Password");
        password = new PasswordField();
        form4.add(text4, password);

        form5 = new FormLayout();
        text5 = new Label("Repeat Password");
        repeat = new PasswordField();
        form5.add(text5, repeat);

        form6 = new FormLayout();
        text6 = new Label("Preferred Currency");
        currency = new ComboBox<>();
        currency.setItems("USD", "GBP", "EUR", "JPY", "CNY");
        form6.add(text6, currency);

        emptyLabel2 = new Label("");
        emptyLabel2.setHeight("1em");

        signup = new Button("Sign Up");
        signup.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        signup.setWidthFull();
        signup.addClickListener(e -> {

            matcher = pattern.matcher(email.getValue());
            pwMatcher = pwPattern.matcher(password.getValue());
            if(!matcher.matches()) {
                Notification.show("Invalid email address");
            } else if(!pwMatcher.matches()) {
                Notification.show("Invalid password format (at least 8 characters, contains upper case, lower case, digit and special char");
            } else if(!(password.getValue()).equals(repeat.getValue())) {
                Notification.show("The password does not match");
            } else {
                UserSignup newUser = new UserSignup();
                newUser.setFirstName(firstname.getValue());
                newUser.setLastName(lastname.getValue());
                newUser.setEmail(email.getValue());
                newUser.setPassword(password.getValue());
                newUser.setLocalCurrency(currency.getValue());
                try {
                    defaultApi.userSignupPost(newUser);
                } catch (ApiException ex) {
                    ex.printStackTrace();
                }
                // after account is created, automatically navigate to login page
                Dialog dialog = new Dialog();
                Label dialogText = new Label("The account has been successfully created!");
                Button buttonOk = new Button("OK");
                dialog.add(dialogText, buttonOk);
                buttonOk.addClickListener(event -> {
                    buttonOk.getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
                    dialog.close();
                });
                dialog.open();
            }
        });

        innerLayout.add(logo, title, emptyLabel1, form1, form2, form3, form4, form5, form6, emptyLabel2, signup);
        add(innerLayout);

        setAlignItems(Alignment.CENTER);
    }
}
