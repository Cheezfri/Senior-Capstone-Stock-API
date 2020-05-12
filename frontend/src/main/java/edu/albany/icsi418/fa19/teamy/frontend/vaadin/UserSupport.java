package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.stereotype.Component;

/**
 * This is a page after click 'support' button in the navigation bar
 */
@Component
@VaadinSessionScope
public class UserSupport extends VerticalLayout {

    private Label title;
    private Accordion accordion;

    /**
     * this screen will contain user-support features
     */
    public UserSupport() {
        title = new Label("How To Use");

        accordion = new Accordion();
        accordion.add("How to add portfolio", new Span(
                "Click the Portfolio Management\n" +
                "Click the '+' button\n" +
                "Type in the portfolio detail and click the add button")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to edit portfolio", new Span(
                "Click the Portfolio Management\n" +
                        "Click the portfolio item of the portfolio list\n" +
                        "Edit the detail and click the save button")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to delete portfolio", new Span(
                "Click the Portfolio Management\n" +
                        "Click the '-' button next to the portfolio item of the portfolio list\n" +
                        "Click the delete button")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to add transaction", new Span(
                "Click the Portfolio Management\n" +
                        "Enter the portfolio view by adding or editing portfolio\n" +
                        "Click the '+' button\n" +
                        "type in the transaction detail and click the Add button")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to edit transaction", new Span(
                "Click the Portfolio Management\n" +
                        "Enter the portfolio view by adding or editing portfolio\n" +
                        "Click the transaction item in the transaction list\n" +
                        "Edit the detail and click the save button")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to delete transaction", new Span(
                "Click the Portfolio Management\n" +
                        "Enter the portfolio view by adding or editing portfolio\n" +
                        "Click the '-' button next to the transaction item of the transaction list\n" +
                "Click the delete button")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to see charts of the portfolio", new Span(
                "Choose the portfolio from the select button on top-right of the screen\n" +
                        "Click the Analytics\n" +
                        "Choose the chart you want to see")).addThemeVariants(DetailsVariant.FILLED);

        accordion.add("How to change the currency", new Span(
                "Click the Account Management\n" +
                        "Click the Change Currency\n" +
                        "Type in the preferred currency and click the save button"
        )).addThemeVariants(DetailsVariant.FILLED);


        add(title, accordion);
    }
}
