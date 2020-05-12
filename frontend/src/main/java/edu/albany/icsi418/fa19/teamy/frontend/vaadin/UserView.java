package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This is a page for end user after login
 */
@Route("user")
//@CssImport("styles/mainview-styles.css")
public class UserView extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(UserView.class);
    public static final String NAME = "user";

    // header
    private HorizontalLayout header;
    private Image logo;
    public static Select<String> select = new Select<>();
    private List<Portfolio> pf_list = new ArrayList<>();

    // nav
    private HorizontalLayout body;
    private VerticalLayout nav;
    private Button home;
    private Button portfolio;
    private Button analytics;
    private Button analyticsCompare;
    private Button sharedPortfolio;
    private Button account;
    private Button support;
    private Button logout;
    // main
    private VerticalLayout main;
    private Div mainpage = new Div();
    private VerticalLayout portfoliopage;
    private HorizontalLayout innerBlock;

    public static Label portfolioName = new Label();
    private TextField search;
    private Div pfmain;
    private Div analyticsPage = new Div();
    private Div analyticsComparePage;
    private Div sharedPortfolioPage;
    private Div accountpage;
    private Div supportpage;
    private Component selectedPage;
    private Set<Component> pagesShown;
    private Map<Button, Component> buttonsToPages;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    /**
     * This screen contains header, navigation bar and main screen
     * header that contains label
     * navigation bar contains home, portfolio, analytics, analyticsCompare, analyticsCompareWhatif,
     * account, support and logout buttons
     * main contains the screens depending on the button
     */
    public UserView() {
        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        // header
        header = new HorizontalLayout();
        header.addClassName("header");
        //Todo: after fix css import, delete this
        header.setHeight("10%");
        header.setWidthFull();

        logo = new Image("img/logo.png", "logo image");
        logo.addClassName("logo");
        select.setLabel("PORTFOLIO");
        try {
            log.info("PortfolioSearchGet is being called!!!!!, LoginViewUserId: "+userLogin.getUser().getId());
            log.info("login user info: " + userLogin.getUser());
            pf_list = defaultApi.portfolioSearchGet(userLogin.getUser().getId(), null);
        } catch (ApiException e) {
            log.info(e.getCode() + " Found in UserView while getting portfolio list");
        } catch (NullPointerException npe) {
            log.info("Null Pointer Exception happened in UserView while getting portfolio list");
        }
        List<String> nameList = new ArrayList<>();
        for(Portfolio pf : pf_list) {
            nameList.add(pf.getName());
        }
        select.setItems(nameList);
        portfolioName = new Label("Current Portfolio: ");
        select.getElement().getStyle().set("margin-left", "auto");
        // if portfolio list is not null, set first portfolio as default
        if(!nameList.isEmpty()) {
            select.setValue(nameList.get(0));
            portfolioName.setText("Current Portfolio: " + select.getValue());

            for(Portfolio pf : pf_list) {
                if(pf.getName().equals(select.getValue())) {
                    UserHome.currentPortfolio = pf;
                    Analytics.currentPortfolio = pf;
                    log.info("Current portfolio is " + pf.getName());

                    // when user change current portfolio, reset the UserHome and Analytics as well
                    mainpage.removeAll();
                    mainpage.add(new UserHome());
                    analyticsPage.removeAll();
                    analyticsPage.add(new Analytics());
                }
            }

        }
        select.addValueChangeListener(e -> {
            portfolioName.setText("Current Portfolio: " + select.getValue());

            for(Portfolio pf : pf_list) {
                if(pf.getName().equals(select.getValue())) {
                    UserHome.currentPortfolio = pf;
                    Analytics.currentPortfolio = pf;
                    log.info("Current portfolio is " + pf.getName());

                    // when user change current portfolio, reset the UserHome and Analytics as well
                    mainpage.removeAll();
                    mainpage.add(new UserHome());
                    analyticsPage.removeAll();
                    analyticsPage.add(new Analytics());
                }
            }
        });

        header.add(logo, select);

        

        // body
        body = new HorizontalLayout();
        body.setWidthFull();
        body.setHeightFull();
        body.setMargin(false);
        body.setPadding(false);
        body.setSpacing(false);

        // navigation bar
        nav = new VerticalLayout();
        nav.addClassName("nav");
        nav.setWidth("20%");
        home = new Button("Home", new Icon(VaadinIcon.HOME));
        portfolio = new Button("Portfolio Management", new Icon(VaadinIcon.CLIPBOARD_TEXT));
        analytics = new Button("Analytics", new Icon(VaadinIcon.CHART_LINE));
        analyticsCompare = new Button("Analytics Compare", new Icon(VaadinIcon.SCALE_UNBALANCE));
        sharedPortfolio = new Button("Shared Portfolio", new Icon(VaadinIcon.LINK));
        account = new Button("Account Management", new Icon(VaadinIcon.USER));
        support = new Button("Support", new Icon(VaadinIcon.COG));
        logout = new Button("Log Out", new Icon(VaadinIcon.EXIT_O));
        logout.setIconAfterText(true);
        nav.setPadding(false);
        nav.add(home, portfolio, analytics, analyticsCompare, sharedPortfolio, account, support, logout);





        // Main Page
        main = new VerticalLayout();
        main.addClassName("main");

        mainpage = new Div();
        mainpage.add(new UserHome());
        portfoliopage = new VerticalLayout();
        portfoliopage.setVisible(false);

        // portfolio management
        innerBlock = new HorizontalLayout();
        innerBlock.add(portfolioName);
        innerBlock.setWidthFull();
        pfmain = new Div();
        pfmain.add(new PortfolioManagement());
        portfoliopage.add(innerBlock, pfmain);
        
        analyticsPage = new Div();
        analyticsPage.add(new Analytics());
        analyticsPage.setVisible(false);
        analyticsComparePage = new Div();
        analyticsComparePage.add(new AnalyticsCompare());
        analyticsComparePage.setVisible(false);
        sharedPortfolioPage = new Div();
        sharedPortfolioPage.add(new SharedPortfolioManagement());
        sharedPortfolioPage.setVisible(false);
        accountpage = new Div();
        accountpage.add(new AccountManagement());
        accountpage.setVisible(false);
        supportpage = new Div();
        supportpage.add(new UserSupport());
        supportpage.setVisible(false);
        pagesShown = Stream.of(mainpage).collect(Collectors.toSet());

        buttonsToPages = new HashMap<>();
        buttonsToPages.put(home, mainpage);
        buttonsToPages.put(portfolio, portfoliopage);
        buttonsToPages.put(analytics, analyticsPage);
        buttonsToPages.put(analyticsCompare, analyticsComparePage);
        buttonsToPages.put(sharedPortfolio, sharedPortfolioPage);
        buttonsToPages.put(account, accountpage);
        buttonsToPages.put(support, supportpage);
        main.add(mainpage, portfoliopage, analyticsPage, analyticsComparePage,
                 sharedPortfolioPage, accountpage, supportpage);

        /**
         * each button goes to each screen depending on the button
         * when user clicks logout button, user can logout
         */
        home.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(home);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        logo.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(home);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        portfolio.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(portfolio);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        analytics.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(analytics);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        analyticsCompare.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(analyticsCompare);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        sharedPortfolio.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(sharedPortfolio);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        account.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(account);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        support.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(support);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });
        logout.addClickListener(e -> {
            // refresh the user information
            userLogin.setUser(null);
            vaadinSession.close();
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

