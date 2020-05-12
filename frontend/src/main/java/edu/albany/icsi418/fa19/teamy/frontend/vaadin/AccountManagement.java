package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a screen for end user after click the account button of navigation bar
 */
@org.springframework.stereotype.Component
@VaadinSessionScope
public class AccountManagement extends HorizontalLayout {

    private VerticalLayout btns;
    private Button personalInfo;
    private Button changePw;
    private VerticalLayout screens;
    private Div empty;
    private Div psinfoScreen;
    private Div changePwScreen;
    private Set<Component> pagesShown;
    private Map<Button, Component> buttonsToPages;
    private Component selectedPage;

    /**
     * This screen contains Tabs for changing personal information and password
     */
    public AccountManagement() {
        btns = new VerticalLayout();
        personalInfo = new Button("Change Personal Information");
        personalInfo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        changePw = new Button("Change Password");
        changePw.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btns.add(personalInfo, changePw);

        screens = new VerticalLayout();
        empty = new Div();
        psinfoScreen = new Div();
        psinfoScreen.add(new ChangeCurrency());
        psinfoScreen.setVisible(false);
        changePwScreen = new Div();
        changePwScreen.add(new ResetpasswordView());
        changePwScreen.setVisible(false);
        screens.add(empty, psinfoScreen, changePwScreen);

        pagesShown = Stream.of(empty).collect(Collectors.toSet());
        buttonsToPages = new HashMap<>();
        buttonsToPages.put(personalInfo, psinfoScreen);
        buttonsToPages.put(changePw, changePwScreen);

        /**
         * When user clicks "Change Personal Information" button, it shows the "PersonalInformation" screen
         */
        personalInfo.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(personalInfo);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        /**
         * When user clicks "Change Password" button, it shows the "ResetpasswordView" screen
         */
        changePw.addClickListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = buttonsToPages.get(changePw);
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        add(btns, screens);
    }
}
