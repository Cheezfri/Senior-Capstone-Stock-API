package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Asset;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.PortfolioTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a screen after user clicks transaction list
 */
@Component
@VaadinSessionScope
public class TransactionView extends Dialog {

    private static final Logger log = LoggerFactory.getLogger(TransactionView.class);

    private FormLayout datetime;
    private Label text_dt;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private FormLayout assetNameForm;
    private Label text_an;
    private Label assetName;
    private FormLayout priceForm;
    private Label text_pr;
    private Label pricePerQuantity;
    private FormLayout transactionType;
    private Label text_tt;
    private Label buysell;
    private FormLayout quantityForm;
    private Label text_qt;
    private NumberField quantity;
    private HorizontalLayout buttons;
    private Button cancel;
    private Button ok;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;
    private List<Asset> assetList = new ArrayList<>();
    private String nameOfAsset;

    /**
     * This screen contains Date/Time pickers, combo boxes for asset category, asset name and transaction type
     * text field for quantity (same as AddTransaction)
     * but only changeable data is date/time and quantity
     */
    public TransactionView(long portfolioId, PortfolioTransaction pt) {
        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }

        datetime = new FormLayout();
        text_dt = new Label("Transaction Date/Time");
        datePicker = new DatePicker();
        timePicker = new TimePicker();
        datetime.add(text_dt, datePicker, timePicker);

        assetNameForm = new FormLayout();
        text_an = new Label("Asset Name");
        assetName = new Label();
        try {
            assetList = defaultApi.assetSearchGet(null);
        } catch (ApiException ae) {
            log.info("{} found in TransactionView during assetSearchPost", ae.getCode());
        }
        for(Asset asset : assetList) {
            if(asset.getId().equals(pt.getId())) {
                nameOfAsset = asset.getName();
            }
        }
        assetName.setText(nameOfAsset);
        assetNameForm.add(text_an, assetName);

        priceForm = new FormLayout();
        text_pr = new Label("Price Each");
        pricePerQuantity = new Label();
        pricePerQuantity.setText(userLogin.getUser().getLocalCurrency() + pt.getPrice());
        priceForm.add(text_pr, pricePerQuantity);

        transactionType = new FormLayout();
        text_tt = new Label("Transaction Type");
        buysell = new Label(pt.getType().toString());
        transactionType.add(text_tt, buysell);

        quantityForm = new FormLayout();
        text_qt = new Label("Quantity");
        quantity = new NumberField();
        quantityForm.add(text_qt, quantity);

        buttons = new HorizontalLayout();
        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        ok = new Button("Save");
        ok.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        buttons.add(cancel, ok);
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(datetime, assetNameForm, priceForm, transactionType, quantityForm, buttons);

        cancel.addClickListener(e -> close());

        ok.addClickListener(e -> {
            if (datePicker.isInvalid()) {
                datePicker.setErrorMessage("Enter a valid Date");
            }
            if (timePicker.isInvalid()) {
                timePicker.setErrorMessage("Enter a valid Time");
            }
            if (quantity.isInvalid()) {
                quantity.setErrorMessage("Enter a valid numeric quantity");
            }

            if (datePicker.isInvalid() || timePicker.isInvalid() || quantity.isInvalid()) {
                log.info("Rejecting input on invalid form data");
                return;
            }

            org.threeten.bp.OffsetDateTime offsetDateTime =
                    org.threeten.bp.OffsetDateTime.of(org.threeten.bp.LocalDateTime.of(
                            datePicker.getValue().getYear(),
                            datePicker.getValue().getMonthValue(),
                            datePicker.getValue().getDayOfMonth(),
                            timePicker.getValue().getHour(),
                            timePicker.getValue().getMinute()
                    ), org.threeten.bp.ZoneOffset.ofHoursMinutes(timePicker.getValue().getHour(), timePicker.getValue().getMinute()));

            try {
                defaultApi.portfolioTransactionUpdatePost(pt.getId(), quantity.getValue(), offsetDateTime, pt.getPrice());
            } catch (ApiException ex) {
                log.info("{} found in TransactionView while update transaction", ex.getCode());
            }
            close();
        });
    }
}
