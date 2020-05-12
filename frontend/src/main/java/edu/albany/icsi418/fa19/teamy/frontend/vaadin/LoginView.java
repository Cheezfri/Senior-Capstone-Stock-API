package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.AccessRole;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a login page which is an entry point
 */
@Route("login")
// TODO: Fix @CssImport
//@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout {

    public static final String NAME = "login";
    private User USER;
    private static final Logger log = LoggerFactory.getLogger(LoginView.class);

    private VerticalLayout innerLayout;
    private Image logo;
    private Label title;
    private Label emptyLabel1;
    private FormLayout form1;
    private Label text1;
    private TextField username;
    private FormLayout form2;
    private Label text2;
    private PasswordField password;
    private Label emptyLabel2;
    private Button signin;
    private Button forgotpassword;
    private Button signup;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    /**
     * This screen contains textfields for username and password field for password
     * and buttons of sign in, forgot password and sign up
     */
    public LoginView() {

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();

        innerLayout = new VerticalLayout();
        innerLayout.addClassName("innerlayout");
        innerLayout.setWidth("300px");

        logo = new Image("img/logo.png", "logo image");
        logo.addClassName("logo");

        title = new Label();
        title.setText("Sign In");

        emptyLabel1 = new Label("");
        emptyLabel1.setHeight("1em");

        form1 = new FormLayout();
        text1 = new Label();
        text1.setText("Username");
        username = new TextField();
        username.setAutofocus(true);
        username.setAutoselect(true);
        form1.add(text1, username);

        form2 = new FormLayout();
        text2 = new Label();
        text2.setText("Password");
        password = new PasswordField();
        form2.add(text2, password);

        emptyLabel2 = new Label("");
        emptyLabel2.setHeight("1em");

        signin = new Button("Sign In");
        signin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        signin.setWidthFull();

        forgotpassword = new Button("Forgot Password");
        forgotpassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        forgotpassword.setWidthFull();

        signup = new Button("Sign Up");
        signup.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        signup.setWidthFull();





        /**
         * if user name exists in the database and password matches
         * user can move on to the page according to their access level (End, Support, Admin)
         * if username or password are incorrect, shows error message
         */
        signin.addClickListener(e -> {
            try {
                USER = defaultApi.userLoginPost(username.getValue(), password.getValue());
                log.info("User information: " + USER);
            } catch (ApiException ex) {
                log.info(ex.getCode() + " found in LoginView while logging in");
            }

            if (USER == null) {
                Notification.show("Incorrect username or password");
                log.info("User is null");
            } else if(username.getValue() == null) {
                Notification.show("Username is empty");
                log.info("username is empty");
            } else if(password.getValue() == null) {
                Notification.show("Password is empty");
                log.info("password is empty");
            } else if(USER.getAccesslevel().getRole().equals(AccessRole.USER)) {
                userLogin = new UserLogin("user", USER);
                vaadinSession.setAttribute(UserLogin.class, userLogin);
                log.info("User info: " + userLogin.getUser());
                signin.getUI().ifPresent(ui -> ui.navigate(UserView.NAME));
            } else if(USER.getAccesslevel().getRole().equals(AccessRole.SUPPORT)) {
                userLogin = new UserLogin("user", USER);
                vaadinSession.setAttribute(UserLogin.class, userLogin);
                signin.getUI().ifPresent(ui -> ui.navigate(SupportView.NAME));
            } else if(USER.getAccesslevel().getRole().equals(AccessRole.ADMIN)) {
                userLogin = new UserLogin("user", USER);
                vaadinSession.setAttribute(UserLogin.class, userLogin);
                signin.getUI().ifPresent(ui -> ui.navigate(AdminView.NAME));
            }
        });

        /**
         * user can go to the ForgotPasswordView page
         */
        forgotpassword.addClickListener(e -> {
            forgotpassword.getUI().ifPresent(ui -> ui.navigate(ForgotPasswordView.NAME));
        });

        /**
         * user can go to the SignUpView page
         */
        signup.addClickListener(e -> {
            signup.getUI().ifPresent(ui -> ui.navigate(SignUpView.NAME));
        });


        innerLayout.add(logo, title, emptyLabel1, form1, form2, emptyLabel2, signin, forgotpassword, signup);
        add(innerLayout);

        setAlignItems(Alignment.CENTER);
    }
}
