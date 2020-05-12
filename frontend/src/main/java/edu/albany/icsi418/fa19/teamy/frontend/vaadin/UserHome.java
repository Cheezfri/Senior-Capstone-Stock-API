package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.AssetProportion;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an entry page (home) after end user login
 * users can see their portfolio status in this page
 */
@Component
@VaadinSessionScope
public class UserHome extends HorizontalLayout {

    private static final Logger log = LoggerFactory.getLogger(UserHome.class);

    private Div leftscreen;
    private Chart chart;
    private Configuration conf;
    private Tooltip tooltip;
    private PlotOptionsPie options;
    private DataSeries series;
    private Div rightscreen;
    private Grid<AssetProportion> grid;

    private DefaultApi defaultApi;
    private List<AssetProportion> list = new ArrayList<>();
    public static Portfolio currentPortfolio = new Portfolio();

    /**
     * this screen contains chart and grid which contains data of chart
     */
    public UserHome() {

        defaultApi = new DefaultApi();

        leftscreen = new Div();
        chart = new Chart(ChartType.PIE);
        conf = chart.getConfiguration();
        conf.setTitle("Asset Net Price Proportions");
        tooltip = new Tooltip();
        tooltip.setValueDecimals(1);
        conf.setTooltip(tooltip);
        options = new PlotOptionsPie();
        options.setAllowPointSelect(true);
        options.setCursor(Cursor.POINTER);
        options.setShowInLegend(true);
        conf.setPlotOptions(options);
        series = new DataSeries();

        try {
            if(UserView.select == null) {
                log.info("UserView.select is null in UserHome");
                list = new ArrayList<>();
            } else if(UserView.select.getValue() == null) {
                log.info("UserView.select.getValue() is null in UserHome");
                list = new ArrayList<>();
            } else {
                log.info("getting asset proportion data of the portfolio id: " + currentPortfolio.getId());
                list = defaultApi.assetProportionsGet(currentPortfolio.getId(), "rebuild");
            }
        } catch(ApiException ex) {
            log.info(ex.getCode() + " found in UserHome while getting asset proportions");
        }

        for (AssetProportion asp : list) {
            series.add(new DataSeriesItem(asp.getAssetName(), asp.getAssetNetPriceProportion()));
            log.info("Asset Name: " + asp.getAssetName() + ", Asset Net Price: " + asp.getAssetNetPrice());
        }
        log.info("DataSeries: " + series);

        conf.setSeries(series);
        leftscreen.add(chart);

        rightscreen = new Div();
        grid = new Grid<>();
        grid.setWidth("800px");
        grid.setItems(list);
        grid.addColumn(AssetProportion::getAssetName).setHeader("Name");
        grid.addColumn(AssetProportion::getAssetQuantity).setHeader("Quantity");
        grid.addColumn(AssetProportion::getAssetNetPrice).setHeader("Net Price");
        grid.addColumn(AssetProportion::getAssetType).setHeader("Type");
        grid.addColumn(AssetProportion::getAssetTicker).setHeader("Ticker");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        rightscreen.add(grid);


        add(leftscreen, rightscreen);
    }

}

