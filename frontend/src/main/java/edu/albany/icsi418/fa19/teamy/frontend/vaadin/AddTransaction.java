package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Asset;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.PortfolioTransactionItem;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.PortfolioTransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * This is a screen for end user after click the '+' button in the portfolio view
 */
public class AddTransaction extends Dialog {

    private static final Logger log = LoggerFactory.getLogger(AddTransaction.class);

    private FormLayout datetime;
    private Label text_dt;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private FormLayout assetName;
    private Label text_an;
    private ComboBox<String> assetList;
    private List<Asset> ls;
    private FormLayout priceForm;
    private Label text_pr;
    private NumberField pricePerQuantity;
    private FormLayout transactionType;
    private Label text_tt;
    private ComboBox<PortfolioTransactionType> buysell;
    private FormLayout quantityForm;
    private Label text_qt;
    private NumberField quantity;
    private HorizontalLayout buttons;
    private Button cancel;
    private Button ok;

    private DefaultApi defaultApi;

    /**
     * This screen contains Date/Time pickers, combo boxes for asset category, asset name and transaction type
     * text field for quantity
     * users can modify their transaction details here
     */
    public AddTransaction(long portfolioId) {

        defaultApi = new DefaultApi();

        datetime = new FormLayout();
        text_dt = new Label("Date/Time of Transaction");
        datePicker = new DatePicker();
        datePicker.setRequired(true);
        timePicker = new TimePicker();
        timePicker.setRequired(true);
        datetime.add(text_dt, datePicker, timePicker);

        assetName = new FormLayout();
        text_an = new Label("Asset Name");
        assetList = new ComboBox<>();
        ls = new ArrayList<>();
        try {
            ls = defaultApi.assetSearchGet(null);
        } catch(ApiException ex) {
            log.info("{} found in AddTransaction during assetSearchPost", ex.getCode());
        } catch(NullPointerException npe) {
            log.info("Null Pointer Exception happened in AddTransaciton during assetSearchPost");
        }
        List<String> nameAndTicker = new ArrayList<>();
        for (Asset as : ls) {
            nameAndTicker.add(as.getName() + "(" + as.getTicker() + ")");
        }
        assetList.setItems(nameAndTicker);
        assetList.setRequired(true);
        assetName.add(text_an, assetList);

        priceForm = new FormLayout();
        text_pr = new Label("Price Each");
        pricePerQuantity = new NumberField();
        priceForm.add(text_pr, pricePerQuantity);

        transactionType = new FormLayout();
        text_tt = new Label("Transaction Type");
        buysell = new ComboBox<>();

        buysell.setItems(PortfolioTransactionType.BUY, PortfolioTransactionType.SELL);
        buysell.setRequired(true);
        transactionType.add(text_tt, buysell);

        quantityForm = new FormLayout();
        text_qt = new Label("Quantity");
        quantity = new NumberField();
        quantityForm.add(text_qt, quantity);

        buttons = new HorizontalLayout();
        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        ok = new Button("Add");
        ok.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        buttons.add(cancel, ok);
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(datetime, assetName, priceForm, transactionType, quantityForm, buttons);

        cancel.addClickListener(e -> close());

        ok.addClickListener(e -> {
            if (datePicker.isInvalid()) {
                datePicker.setErrorMessage("Enter a valid Date");
            }
            if (timePicker.isInvalid()) {
                timePicker.setErrorMessage("Enter a valid Time");
            }
            if (assetList.isInvalid()) {
                assetList.setErrorMessage("An Asset is Required");
            }
            if (quantity.isInvalid()) {
                quantity.setErrorMessage("Enter a valid numeric quantity");
            }
            if (buysell.isInvalid()) {
                buysell.setErrorMessage("Transaction Type Required");
            }

            if (datePicker.isInvalid() || timePicker.isInvalid() || assetList.isInvalid() || quantity.isInvalid()
                    || buysell.isInvalid()) {
                log.info("Rejecting input on invalid form data");
                return;
            }

            PortfolioTransactionItem item = new PortfolioTransactionItem();
            org.threeten.bp.OffsetDateTime offsetDateTime =
                    org.threeten.bp.OffsetDateTime.of(org.threeten.bp.LocalDateTime.of(
                            datePicker.getValue().getYear(),
                            datePicker.getValue().getMonthValue(),
                            datePicker.getValue().getDayOfMonth(),
                            timePicker.getValue().getHour(),
                            timePicker.getValue().getMinute()
                    ), org.threeten.bp.ZoneOffset.ofHoursMinutes(timePicker.getValue().getHour(), timePicker.getValue().getMinute()));
            item.setDate(offsetDateTime);
            item.setPortfolioId(portfolioId);

            try {
                ls = defaultApi.assetSearchGet(null);
            } catch (ApiException ex) {
                log.info("{} found in AddTransaction while getting asset list", ex.getCode());
            }
            long val = -1;
            for(Asset as : ls) {
                if((as.getName() + "(" + as.getTicker() + ")").equals(assetList.getValue())) {
                    val = as.getId();
                    log.info("asset Id: {}", val);
                    break;
                }
            }
            item.setAssetId(val);
            item.setQuantity(quantity.getValue());
            item.setTransactionType(buysell.getValue());
            item.setAssetPrice(pricePerQuantity.getValue());

            // Add transaction
            try {
                defaultApi.portfolioTransactionAddPost(item);
            } catch (ApiException ex) {
                log.info("{} found in AddTransaction while adding transaction", ex.getCode());
            }

            // refresh portfolio view
            

            close();
        });

    }

}
