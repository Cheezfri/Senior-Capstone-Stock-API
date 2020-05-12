package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.server.VaadinSession;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.User;

/**
 * this class is for the Vaadin session management
 */
public class UserLogin {
    private String userName;
    private User user;

    public UserLogin(String userName, User user) {
        this.userName = userName;
        this.user = user;
    }

    public void setUser(User user) {
        VaadinSession.getCurrent().setAttribute("user", user);
    }

    public User getUser() {
        return user;
    }
}
