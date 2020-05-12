package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a page for admin user
 * It contains grid of all users information
 */
@Component
@VaadinSessionScope
public class UserManagement extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(UserManagement.class);

    private Grid<User> grid;
    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;
    private List<User> list = new ArrayList<>();

    /**
     * This screen contains all user list
     * user list can be sorted
     * by right click on the user list, admin user can change their lock status and access level
     */
    public UserManagement() {

        // Get User list
        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }
        try {
            list = defaultApi.userSearchGet(userLogin.getUser().getId(), null);
        } catch(ApiException ex) {
            log.info(ex.getCode() + " found in UserManagement while getting user list");
        }


        grid = new Grid<>(User.class);
        grid.setWidth("1000px");
        grid.setItems(list);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        GridContextMenu<User> contextMenu = new GridContextMenu<>(grid);
        GridMenuItem<User> lock = contextMenu.addItem("Lock/Unlock");
        //Todo: lock/unlock api call
        GridMenuItem<User> changeAccess = contextMenu.addItem("Change Access Level");
        changeAccess.getSubMenu().addItem("ADMIN");
        changeAccess.getSubMenu().addItem("SUPPORT");
        changeAccess.getSubMenu().addItem("USER");
        //Todo: change access level api call

        contextMenu.addItem("Delete", e -> {
            try {
                defaultApi.userDeletePost(userLogin.getUser().getId(), e.getItem().get().getId());
            } catch (ApiException ex) {
                log.info(ex.getCode() + " found in UserManagement while deleting user");
            }
        });


        add(grid);
    }
}
