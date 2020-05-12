package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;


/**
 * This is a entry page
 * set this as a login view but will change to other pages as needed
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class EntryView extends VerticalLayout {

    public static final String NAME = "";

    /**
     * Currently, entry point is login view
     */
    public EntryView() {
        add(new LoginView());
    }

}
