package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This is a page for admin user after login
 */
@Route("admin")
public class AdminView extends VerticalLayout {
    public static final String NAME = "admin";

    private HorizontalLayout header;
    private Image logo;
    private HorizontalLayout body;
    private VerticalLayout nav;
    private Button home;
    private Button user;
    private Button support;
    private Button logout;
    private VerticalLayout main;
    private Div mainpage;
    private Div userpage;
    private Div supportpage;
    private Set<Component> pagesShown;
    private Map<Button, Component> buttonsToPages;

    /**
     * This screen contains header, navigation bar and main screen
     * header that contains label
     * navigation bar contains home, user management, support and logout button
     * main contains the screens depending on the button
     */
    public AdminView() {
        header = new HorizontalLayout();
        header.addClassName("header");
        header.setWidthFull();
        //Todo: after fix css import, delete this
        header.setHeight("80px");
        logo = new Image("img/logo.png", "logo image");
        logo.addClassName("logo");
        header.add(logo);

        body = new HorizontalLayout();
        body.setWidthFull();
        body.setHeightFull();
        body.setMargin(false);
        body.setPadding(false);
        body.setSpacing(false);

        nav = new VerticalLayout();
        nav.addClassName("nav");
        nav.setWidth("250px");

        home = new Button("Home", new Icon(VaadinIcon.HOME));
        user = new Button("User Management", new Icon(VaadinIcon.USER));
        support = new Button("Support", new Icon(VaadinIcon.COG));
        logout = new Button("Log Out", new Icon(VaadinIcon.EXIT_O));
        logout.setIconAfterText(true);

        nav.add(home, user, support, logout);

        mainpage = new Div();
        mainpage.setText("main page");
        userpage = new Div();
        try {
            userpage.add(new UserManagement());
        } catch (Exception e) {

        }
        userpage.setVisible(false);
        supportpage = new Div();
        supportpage.setText("support page");
        supportpage.setVisible(false);
        pagesShown = Stream.of(mainpage).collect(Collectors.toSet());

        buttonsToPages = new HashMap<>();
        buttonsToPages.put(home, mainpage);
        buttonsToPages.put(user, userpage);
        buttonsToPages.put(support, supportpage);

        main = new VerticalLayout();
        main.addClassName("main");

        main.add(mainpage, userpage, supportpage);

        /**
         * each button goes to each screen depending on the button
         * when user clicks logout button, user can logout
         */
        home.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = buttonsToPages.get(home);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        user.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = buttonsToPages.get(user);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        support.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = buttonsToPages.get(support);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        logout.addClickListener(e -> {
            logout.getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        });

        body.add(nav, main);
        add(header, body);
        setMargin(false);
        setPadding(false);
        setSpacing(false);
        setHeightFull();
    }

}
