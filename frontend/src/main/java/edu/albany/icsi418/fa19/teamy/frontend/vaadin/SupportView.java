package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


/**
 * This is a page for support user after login
 */
@Route("support")
public class SupportView extends VerticalLayout {
    public static final String NAME = "support";

    private HorizontalLayout header;
    private Image logo;
    private HorizontalLayout body;
    private VerticalLayout nav;
    private Button home;
    private Button portfolio;
    private Button account;
    private Button support;
    private Button logout;
    private VerticalLayout main;
    private Label maintitle;

    /**
     * This screen contains header, navigation bar and main screen
     * header that contains label
     * navigation bar contains home and logout button
     * main contains the screens depending on the button
     */
    public SupportView() {
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
        portfolio = new Button("Portfolio Management", new Icon(VaadinIcon.CHART_GRID));
        account = new Button("Account Management", new Icon(VaadinIcon.USER));
        support = new Button("Support", new Icon(VaadinIcon.COG));
        logout = new Button("Log Out", new Icon(VaadinIcon.EXIT_O));
        logout.setIconAfterText(true);

        nav.add(home, portfolio, account, support, logout);

        main = new VerticalLayout();
        main.addClassName("main");

        maintitle = new Label("Main");
        main.add(maintitle);


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
