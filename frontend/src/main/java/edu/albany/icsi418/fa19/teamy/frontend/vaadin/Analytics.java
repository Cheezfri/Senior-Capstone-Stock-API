package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.AssetPriceOverTime;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.AssetProportion;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.Portfolio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a screen for end user after click the analytics button of navigation bar
 */
@org.springframework.stereotype.Component
@VaadinSessionScope
public class Analytics extends HorizontalLayout {

    private static final Logger log = LoggerFactory.getLogger(Analytics.class);

    private VerticalLayout left;
    private VerticalLayout right;
    private Tab pie;
    private Div page_pie;
    private Tab highlow;
    private Div page_highlow;
    private Tab line;
    private Div page_line;
    private Tab movingAverage;
    private Div page_ma;
    private Map<Tab, Component> tabsToPages;
    private Tabs tabs;
    private Div pages;
    private Set<Component> pagesShown;
    private Component selectedPage;

    private Grid<AssetProportion> grid_pie;
    private Grid<AssetPriceOverTime> grid_hl;
    private Grid<AssetPriceOverTime> grid_ln;
    private Grid<AssetPriceOverTime> grid_ma;
    private Div gridpage_pie;
    private Div gridpage_hl;
    private Div gridpage_ln;
    private Div gridpage_ma;
    private Div rightPages;
    private Map<Tab, Component> tabsToRightPages;
    private Set<Component> rightPagesShown;
    private Component selectedRightPage;

    // Pie
    private Chart pie_chart;
    private Configuration pie_conf;
    private Tooltip pie_tooltip;
    private PlotOptionsPie pie_options;
    private DataSeries pie_series;
    // High Low
    private Chart highlow_chart;
    private Configuration hl_configuration;
    private RangeSelector hl_rangeSelector;
    private DataSeries hl_dataSeries;
    private PlotOptionsCandlestick hl_plotOptionsCandlestick;
    private DataGrouping hl_grouping;
    // Line
    private Chart ln_chart;
    private Configuration ln_conf;
    private YAxis yAxis;
    private Labels label;
    private PlotLine plotLine;
    private Tooltip ln_tooltip;
    private PlotOptionsSeries plotOptionsSeries;
    private RangeSelector ln_rangeSelector;
    // Moving Average
    private Chart ma_chart;
    private Configuration ma_configuration;
    private RangeSelector ma_rangeSelector;
    private DataSeries ma_ohlc_series;
    private DataSeries ma_spline_series;
    private PlotOptionsCandlestick ma_plotOptionsCandlestick;
    private PlotOptionsSpline ma_plotOptionsSpline;
    private Tooltip ma_tooltip;


    private DefaultApi defaultApi;
    private String sortBy;
    private List<AssetProportion> ap_list = new ArrayList<>();
    private List<AssetPriceOverTime> hl_list = new ArrayList<>();
    private List<AssetPriceOverTime> ln_list = new ArrayList<>();
    private List<AssetPriceOverTime> ma_list = new ArrayList<>();

    public static Portfolio currentPortfolio = new Portfolio();


    /**
     * This screen contains Tabs for pie, high low and line charts
     * Each tab shows the chart depending on the tab name
     */
    public Analytics() {
        defaultApi = new DefaultApi();

        left = new VerticalLayout();
        right = new VerticalLayout();

        // Pie
        pie = new Tab("Pie Chart");
        page_pie = new Div();
        pie_chart = new Chart(ChartType.PIE);
        pie_conf = pie_chart.getConfiguration();
        pie_conf.setTitle("Proportion of Assets");
        pie_tooltip = new Tooltip();
        pie_tooltip.setValueDecimals(1);
        pie_conf.setTooltip(pie_tooltip);
        pie_options = new PlotOptionsPie();
        pie_options.setAllowPointSelect(true);
        pie_options.setCursor(Cursor.POINTER);
        pie_options.setShowInLegend(true);
        pie_conf.setPlotOptions(pie_options);
        pie_series = new DataSeries();
        page_pie.add(pie_chart);


        // High Low
        highlow = new Tab("High-Low Chart");
        page_highlow = new Div();
        page_highlow.setVisible(false);

        highlow_chart = new Chart(ChartType.CANDLESTICK);
        highlow_chart.setTimeline(true);
        hl_configuration = highlow_chart.getConfiguration();
        hl_rangeSelector = new RangeSelector();
        hl_configuration.setRangeSelector(hl_rangeSelector);
        hl_dataSeries = new DataSeries();
        hl_plotOptionsCandlestick = new PlotOptionsCandlestick();
        hl_grouping = new DataGrouping();
        hl_grouping.addUnit(new TimeUnitMultiples(TimeUnit.WEEK, 1));
        hl_grouping.addUnit(new TimeUnitMultiples(TimeUnit.MONTH, 1, 2, 3, 4, 6));
        hl_plotOptionsCandlestick.setDataGrouping(hl_grouping);
        hl_dataSeries.setPlotOptions(hl_plotOptionsCandlestick);
//        hl_dataSeries.add(new OhlcItem(new Date(2019, 5, 31), 149.31, 149.74, 147.55, 148.15));
//        hl_dataSeries.add(new OhlcItem(new Date(2019, 6, 27), 143.89, 144.39, 143.22, 143.46));
//        hl_dataSeries.add(new OhlcItem(new Date(2019, 7, 15), 153.37, 153.57, 151.78, 152.16));
//        hl_dataSeries.add(new OhlcItem(new Date(2019, 8, 24), 142.94, 143.14, 142.12, 142.50));
//        hl_dataSeries.add(new OhlcItem(new Date(2019, 10, 9), 142.57, 143.12, 141.87, 142.51));
//        hl_configuration.setSeries(hl_dataSeries);
//        highlow_chart.drawChart();
        page_highlow.add(highlow_chart);

        // Line
        line = new Tab("Line Chart");
        page_line = new Div();
        page_line.setVisible(false);

        ln_chart = new Chart();
        ln_chart.setTimeline(true);
        ln_conf = ln_chart.getConfiguration();
        yAxis = new YAxis();
        label = new Labels();
        label.setFormatter("function() { return (this.value > 0 ? ' + ' : '') + this.value + '%'; }");
        yAxis.setLabels(label);
        plotLine = new PlotLine();
        plotLine.setValue(2);
        yAxis.setPlotLines(plotLine);
        ln_conf.addyAxis(yAxis);
        ln_tooltip = new Tooltip();
        ln_tooltip.setPointFormat("<span>{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>");
        ln_tooltip.setValueDecimals(2);
        ln_conf.setTooltip(ln_tooltip);
//        DataSeries applSeries = new DataSeries();
//        applSeries.setName("AAPL");
//        int j=100;
//        for(int i=1; i<=31; i++) {
//            DataSeriesItem item = new DataSeriesItem();
//            item.setX(new Date(2019, 01, i));
//            if(j > 100) {
//                item.setY(j);
//                j = 90;
//            } else {
//                item.setY(j++);
//            }
//            applSeries.add(item);
//        }
//        DataSeries googSeries = new DataSeries();
//        ma_conf.setSeries(applSeries, googSeries);
        plotOptionsSeries = new PlotOptionsSeries();
        plotOptionsSeries.setCompare(Compare.PERCENT);
        plotOptionsSeries.setShowInLegend(true);
        ln_conf.setPlotOptions(plotOptionsSeries);
        ln_rangeSelector = new RangeSelector();
        ln_rangeSelector.setSelected(4);
        ln_conf.setRangeSelector(ln_rangeSelector);
        page_line.add(ln_chart);

        // Moving Average
        movingAverage = new Tab("Moving Average");
        page_ma = new Div();
        page_ma.setVisible(false);
        ma_chart = new Chart(ChartType.CANDLESTICK);
        ma_chart.setTimeline(true);
        ma_configuration = ma_chart.getConfiguration();
        ma_rangeSelector = new RangeSelector();
        ma_configuration.setRangeSelector(ma_rangeSelector);
        ma_ohlc_series = new DataSeries();
        ma_plotOptionsCandlestick = new PlotOptionsCandlestick();
        ma_ohlc_series.setPlotOptions(ma_plotOptionsCandlestick);
        ma_configuration.addSeries(ma_ohlc_series);
        ma_spline_series = new DataSeries();
        ma_plotOptionsSpline = new PlotOptionsSpline();
        ma_spline_series.setPlotOptions(ma_plotOptionsSpline);
        ma_tooltip = new Tooltip();
        ma_tooltip.setFormatter("function() { "
                + "return 'High: '+ this.point.high + <br>"
                + "'Low: '+ this.point.low + <br>"
                + "'Open: ' + this.point.open + <br>"
                + "'Close: ' + this.point.close ; }");
        ma_configuration.setTooltip(ma_tooltip);
        ma_configuration.addSeries(ma_spline_series);



        // ------------------------------- Grid part ------------------------------------
        //make an Asset Proportion grid for Pie chart

        try {
            if(UserView.select == null) {
                log.info("UserView.select is null in Analytics");
                ap_list = new ArrayList<>();
            } else if(UserView.select.getValue() == null) {
                log.info("UserView.select.getValue() is null in Analytics");
                ap_list = new ArrayList<>();
            } else {
                log.info("getting asset proportion data of the portfolio id: " + currentPortfolio.getId());
                ap_list = defaultApi.assetProportionsGet(currentPortfolio.getId(), "rebuild");
            }
        } catch(ApiException ex) {
            log.info(ex.getCode() + " found in Analytics while getting asset proportions");
        }

        ListDataProvider<AssetProportion> pie_dataProvider = DataProvider.ofCollection(ap_list);
        grid_pie = new Grid<>();
        grid_pie.setDataProvider(pie_dataProvider);
        grid_pie.setWidth("500px");
        Grid.Column<AssetProportion> nameColumn = grid_pie.addColumn(AssetProportion::getAssetName)
                .setHeader("Asset Name");
        Grid.Column<AssetProportion> quantityColumn = grid_pie.addColumn(AssetProportion::getAssetQuantity)
                .setHeader("Asset Quantity");
        Grid.Column<AssetProportion> netPriceColumn = grid_pie.addColumn(AssetProportion::getAssetNetPrice)
                .setHeader("Net Price");
        Grid.Column<AssetProportion> typeColumn = grid_pie.addColumn(AssetProportion::getAssetType)
                .setHeader("Asset Type");
        Grid.Column<AssetProportion> tickerColumn = grid_pie.addColumn(AssetProportion::getAssetTicker)
                .setHeader("Asset Ticker");
        grid_pie.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        HeaderRow topRow = grid_pie.prependHeaderRow();
        HeaderRow.HeaderCell buttonsCell = topRow.join(nameColumn, quantityColumn, netPriceColumn, typeColumn, tickerColumn);
        Button sortByQuantity = new Button("Sort By Quantity", e -> {
            pie_conf.setTitle("Proportion of Asset Quantity");
            page_pie.removeAll();
            pie_series.clear();
            for(AssetProportion ap : ap_list) {
                pie_series.add(new DataSeriesItem(ap.getAssetTicker(), ap.getAssetQuantityProportion()));
            }
            sortBy = "Quantity";
            pie_conf.setSeries(pie_series);
            page_pie.add(pie_chart);
        });
        Button sortByNetPrice = new Button("Sort By Net Price", e -> {
            pie_conf.setTitle("Proportion of Asset Net Price");
            page_pie.removeAll();
            pie_series.clear();
            for(AssetProportion ap : ap_list) {
                pie_series.add(new DataSeriesItem(ap.getAssetTicker(), ap.getAssetNetPriceProportion()));
            }
            sortBy = "Net Price";
            pie_conf.setSeries(pie_series);
            page_pie.add(pie_chart);
        });
        HorizontalLayout filter = new HorizontalLayout(sortByQuantity, sortByNetPrice);
        buttonsCell.setComponent(filter);

        // grid for High Low chart
        try {
            hl_list = defaultApi.holdingassetpricedataGet(currentPortfolio.getId(), null, null);
        } catch (ApiException ex) {
            log.info(ex.getCode() + " found in Analytics while getting holding asset price data");
        }
        grid_hl = new Grid<>();
        grid_hl.setWidth("500px");
        grid_hl.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid_hl.setItems(hl_list);
        grid_hl.addColumn(AssetPriceOverTime::getAssetName).setHeader("Asset Name");
        grid_hl.addItemClickListener(e -> {
            //if click the asset, show its high low chart
            log.info("Asset Name: " + e.getItem().getAssetName() + " in high low chart of Analytics");
            page_highlow.removeAll();
            highlow_chart = new Chart(ChartType.CANDLESTICK);
            highlow_chart.setTimeline(true);
            hl_configuration = highlow_chart.getConfiguration();
            hl_configuration.getTitle().setText(e.getItem().getAssetName());
            hl_rangeSelector = new RangeSelector();
            hl_configuration.setRangeSelector(hl_rangeSelector);
            hl_dataSeries = new DataSeries();
            hl_plotOptionsCandlestick = new PlotOptionsCandlestick();
            hl_grouping = new DataGrouping();
            hl_grouping.addUnit(new TimeUnitMultiples(TimeUnit.WEEK, 1));
            hl_grouping.addUnit(new TimeUnitMultiples(TimeUnit.MONTH, 1, 2, 3, 4, 6));
            hl_plotOptionsCandlestick.setDataGrouping(hl_grouping);
            hl_dataSeries.setPlotOptions(hl_plotOptionsCandlestick);
            hl_dataSeries.clear();
            for(AssetPriceData data : e.getItem().getAssetPriceDataList()) {
                OhlcItem item = new OhlcItem();
                item.setX(new Date(data.getDateTime().getYear(), data.getDateTime().getMonthValue(), data.getDateTime().getDayOfMonth()));
                item.setLow(data.getLowPrice());
                item.setHigh(data.getHighPrice());
                item.setOpen(data.getOpenPrice());
                item.setClose(data.getClosePrice());
                hl_dataSeries.add(item);
            }
            hl_configuration.setSeries(hl_dataSeries);
            highlow_chart.drawChart();
            page_highlow.add(highlow_chart);
        });

        // grid for line chart
        try {
            ln_list = defaultApi.holdingassetpricedataGet(currentPortfolio.getId(), null, null);
        } catch(ApiException ex) {
            log.info(ex.getCode() + " found in Anayltics while getting holding asset price data");
        }

        grid_ln = new Grid<>();
        grid_ln.setWidth("500px");
        grid_ln.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid_ln.setItems(ln_list);
        grid_ln.addColumn(AssetPriceOverTime::getAssetName).setHeader("Asset Name");

        page_line.removeAll();
        ln_chart = new Chart();
        ln_chart.setTimeline(true);
        ln_conf = ln_chart.getConfiguration();
        yAxis = new YAxis();
        label = new Labels();
        label.setFormatter("function() { return (this.value > 0 ? ' + ' : '') + this.value + '%'; }");
        yAxis.setLabels(label);
        plotLine = new PlotLine();
        plotLine.setValue(2);
        yAxis.setPlotLines(plotLine);
        ln_conf.addyAxis(yAxis);
        ln_tooltip = new Tooltip();
        ln_tooltip.setPointFormat("<span>{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>");
        ln_tooltip.setValueDecimals(2);
        ln_conf.setTooltip(ln_tooltip);
        plotOptionsSeries = new PlotOptionsSeries();
        plotOptionsSeries.setCompare(Compare.PERCENT);
        plotOptionsSeries.setShowInLegend(true);
        ln_conf.setPlotOptions(plotOptionsSeries);
        ln_rangeSelector = new RangeSelector();
        ln_rangeSelector.setSelected(4);
        ln_conf.setRangeSelector(ln_rangeSelector);
        for(AssetPriceOverTime apot : ln_list) {
            DataSeries ds = new DataSeries();
            ds.setName(apot.getAssetName());
            log.info("data series name: " + ds.getName() + " in line chart of Analytics");
            for(AssetPriceData apd : apot.getAssetPriceDataList()) {
                DataSeriesItem item = new DataSeriesItem();
                item.setX(new Date(apd.getDateTime().getYear(), apd.getDateTime().getMonthValue(), apd.getDateTime().getDayOfMonth()));
                // TODO: Change this value
                item.setY(apd.getHighPrice());
                ds.add(item);
            }
            ln_conf.addSeries(ds);
            log.info("data series added: " + ds.getName());
        }
        page_line.add(ln_chart);

        // grid for Moving Average chart
        try {
            ma_list = defaultApi.holdingassetpricedataGet(currentPortfolio.getId(), null, null);
        } catch (ApiException ex) {
            log.info(ex.getCode() + " found in Analytics while getting holding asset price data");
        }
        grid_ma = new Grid<>();
        grid_ma.setWidth("500px");
        grid_ma.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid_ma.setItems(ma_list);
        grid_ma.addColumn(AssetPriceOverTime::getAssetName).setHeader("Asset Name");
        grid_ma.addItemClickListener(e -> {
            //if click the asset, show its high low chart
            log.info("Asset Name: " + e.getItem().getAssetName() + " in moving average chart of Analytics");
            page_ma.removeAll();
            ma_ohlc_series.clear();
            ma_spline_series.clear();
            for(AssetPriceData data : e.getItem().getAssetPriceDataList()) {
                OhlcItem item = new OhlcItem();
                item.setX(new Date(data.getDateTime().getYear(), data.getDateTime().getMonthValue(), data.getDateTime().getDayOfMonth()));
                item.setLow(data.getLowPrice());
                item.setHigh(data.getHighPrice());
                item.setOpen(data.getOpenPrice());
                item.setClose(data.getClosePrice());
                ma_ohlc_series.add(item);

                DataSeriesItem dsi = new DataSeriesItem();
                dsi.setX(item.getX());
                //TODO: change this value to moving average
                dsi.setY(((double)item.getHigh() + (double)item.getLow())/2);
                ma_spline_series.add(dsi);
            }
            ma_configuration.addSeries(ma_ohlc_series);
            ma_configuration.addSeries(ma_spline_series);
            ma_chart.drawChart();
            page_ma.add(ma_chart);
        });


        // Each tab has its own chart and grid
        tabsToPages = new HashMap<>();
        tabsToPages.put(pie, page_pie);
        tabsToPages.put(highlow, page_highlow);
        tabsToPages.put(line, page_line);
        tabsToPages.put(movingAverage, page_ma);
        tabs = new Tabs(pie, highlow, line, movingAverage);
        pages = new Div(page_pie, page_highlow, page_line, page_ma);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setSelectedTab(pie);
        pagesShown = Stream.of(page_pie).collect(Collectors.toSet());

        gridpage_pie = new Div();
        gridpage_pie.add(grid_pie);
        gridpage_hl = new Div();
        gridpage_hl.add(grid_hl);
        gridpage_hl.setVisible(false);
        gridpage_ln = new Div();
        gridpage_ln.add(grid_ln);
        gridpage_ln.setVisible(false);
        gridpage_ma = new Div();
        gridpage_ma.add(grid_ma);
        gridpage_ma.setVisible(false);
        rightPages = new Div(gridpage_pie, gridpage_hl, gridpage_ln, gridpage_ma);
        tabsToRightPages = new HashMap<>();
        tabsToRightPages.put(pie, gridpage_pie);
        tabsToRightPages.put(highlow, gridpage_hl);
        tabsToRightPages.put(line, gridpage_ln);
        tabsToRightPages.put(movingAverage, gridpage_ma);
        rightPagesShown = Stream.of(gridpage_pie).collect(Collectors.toSet());

        //each tab -> each chart and each grid
        //ex) pie tab -> pie chart and pie grid
        tabs.addSelectedChangeListener(e -> {
           pagesShown.forEach(page -> page.setVisible(false));
           pagesShown.clear();
           selectedPage = tabsToPages.get(tabs.getSelectedTab());
           selectedPage.setVisible(true);
           pagesShown.add(selectedPage);

           rightPagesShown.forEach(page -> page.setVisible(false));
           rightPagesShown.clear();
           selectedRightPage = tabsToRightPages.get(tabs.getSelectedTab());
           selectedRightPage.setVisible(true);
           rightPagesShown.add(selectedRightPage);

        });

        left.add(tabs, pages);
        right.add(rightPages);
        add(left, right);
    }

}


