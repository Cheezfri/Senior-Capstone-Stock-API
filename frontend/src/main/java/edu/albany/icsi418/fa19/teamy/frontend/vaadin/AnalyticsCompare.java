package edu.albany.icsi418.fa19.teamy.frontend.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.ApiException;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.frontend.swagger.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a screen for end user after click the analytics compare button of navigation bar
 */
@org.springframework.stereotype.Component
@VaadinSessionScope
public class AnalyticsCompare extends HorizontalLayout {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsCompare.class);

    private VerticalLayout mainLayout;
    private HorizontalLayout buttons;
    private Button first_portfolio;
    private Button second_portfolio;
    private Tab pie;
    private HorizontalLayout piePage;
    private Div leftside_pi;
    private Div rightside_pi;
    private Tab highlow;
    private HorizontalLayout highLowPage;
    private Div leftside_hl;
    private Div rightside_hl;
    private Tab line;
    private HorizontalLayout linePage;
    private Div leftside_ln;
    private Div rightside_ln;
    private Tabs tabs;
    private HorizontalLayout pages;
    private Map<Tab, Component> tabsToPages;
    private Set<Component> pagesShown;
    private Component selectedPage;
    private VerticalLayout gridSide;
    private Grid<Portfolio> grid;

    // Pie
    private Chart pie_chart1;
    private Configuration pie_conf1;
    private Tooltip pie_tooltip1;
    private PlotOptionsPie pie_options1;
    private DataSeries pie_series1;
    private Chart pie_chart2;
    private Configuration pie_conf2;
    private Tooltip pie_tooltip2;
    private PlotOptionsPie pie_options2;
    private DataSeries pie_series2;

    // High Low
    private Chart highlow_chart1;
    private Configuration hl_configuration1;
    private RangeSelector hl_rangeSelector1;
    private DataSeries hl_dataSeries1;
    private PlotOptionsCandlestick hl_plotOptionsCandlestick1;
    private DataGrouping hl_grouping1;
    private Chart highlow_chart2;
    private Configuration hl_configuration2;
    private RangeSelector hl_rangeSelector2;
    private DataSeries hl_dataSeries2;
    private PlotOptionsCandlestick hl_plotOptionsCandlestick2;
    private DataGrouping hl_grouping2;

    // Moving Average
    private Chart ln_chart1;
    private Configuration ln_conf1;
    private YAxis yAxis1;
    private Labels label1;
    private PlotLine plotLine1;
    private Tooltip ln_tooltip1;
    private PlotOptionsSeries ln_plotOptionsSeries1;
    private RangeSelector ln_rangeSelector1;
    private Chart ln_chart2;
    private Configuration ln_conf2;
    private YAxis yAxis2;
    private Labels label2;
    private PlotLine plotLine2;
    private Tooltip ln_tooltip2;
    private PlotOptionsSeries ln_plotOptionsSeries2;
    private RangeSelector ln_rangeSelector2;

    private DefaultApi defaultApi;
    private VaadinSession vaadinSession;
    private UserLogin userLogin;

    private List<Portfolio> portfolioList = new ArrayList<>();
    private List<AssetProportion> ap_list = new ArrayList<>();
    private List<AssetPriceOverTime> hl_list = new ArrayList<>();
    private List<AssetPriceOverTime> ln_list = new ArrayList<>();

    private List<AssetPriceOverTime> hl_first_list = new ArrayList<>();
    private List<AssetPriceOverTime> hl_second_list = new ArrayList<>();
    private Grid<AssetPriceOverTime> hl_first_grid = new Grid<>();
    private Grid<AssetPriceOverTime> hl_second_grid = new Grid<>();
    private List<HoldingAsset> hal = new ArrayList<>();

    /**
     * This screen contains buttons, tabs, screens and portfolio list
     * buttons can be empty or filled with portfolio name
     * tabs has names of charts and after click each tab, screen shows the chart according to the tab
     */
    public AnalyticsCompare(){

        defaultApi = new DefaultApi();
        vaadinSession = VaadinSession.getCurrent();
        if(userLogin.getUser() == null) {
            getUI().ifPresent(ui -> ui.navigate(LoginView.NAME));
        } else {
            userLogin = vaadinSession.getAttribute(UserLogin.class);
        }


        mainLayout = new VerticalLayout();
        mainLayout.setWidth("1000px");
        buttons = new HorizontalLayout();
        buttons.setWidthFull();
        first_portfolio = new Button("Add Portfolio");
        first_portfolio.addClickListener(e -> {
           if(!first_portfolio.getText().equals("Add Portfolio")) {
               first_portfolio.setText("Add Portfolio");
               leftside_pi.removeAll();
               leftside_hl.removeAll();
               leftside_ln.removeAll();
               gridSide.removeAll();
               hl_first_grid = new Grid<>();
               hl_second_grid = new Grid<>();
               gridSide.add(grid);
           }
        });
        second_portfolio = new Button("Add Portfolio");
        second_portfolio.addClickListener(e -> {
            if(!second_portfolio.getText().equals("Add Portfolio")) {
                second_portfolio.setText("Add Portfolio");
                rightside_pi.removeAll();
                rightside_hl.removeAll();
                rightside_ln.removeAll();
                gridSide.removeAll();
                hl_first_grid = new Grid<>();
                hl_second_grid = new Grid<>();
                gridSide.add(grid);
            }
        });
        buttons.add(first_portfolio, second_portfolio);


        // Pie
        pie = new Tab("Pie Chart");
        piePage = new HorizontalLayout();
        piePage.setWidthFull();

        pie_chart1 = new Chart(ChartType.PIE);
        pie_conf1 = pie_chart1.getConfiguration();
        pie_conf1.setTitle("Proportion of Asset Net Price");
        pie_tooltip1 = new Tooltip();
        pie_tooltip1.setValueDecimals(1);
        pie_conf1.setTooltip(pie_tooltip1);
        pie_options1 = new PlotOptionsPie();
        pie_options1.setAllowPointSelect(true);
        pie_options1.setCursor(Cursor.POINTER);
        pie_options1.setShowInLegend(true);
        pie_conf1.setPlotOptions(pie_options1);
        pie_series1 = new DataSeries();

        pie_chart2 = new Chart(ChartType.PIE);
        pie_conf2 = pie_chart2.getConfiguration();
        pie_conf2.setTitle("Proportion of Asset Net Price");
        pie_tooltip2 = new Tooltip();
        pie_tooltip2.setValueDecimals(1);
        pie_conf2.setTooltip(pie_tooltip2);
        pie_options2 = new PlotOptionsPie();
        pie_options2.setAllowPointSelect(true);
        pie_options2.setCursor(Cursor.POINTER);
        pie_options2.setShowInLegend(true);
        pie_conf2.setPlotOptions(pie_options2);
        pie_series2 = new DataSeries();

        leftside_pi = new Div();
        leftside_pi.setWidth("500px");
        rightside_pi = new Div();
        rightside_pi.setWidth("500px");
        piePage.add(leftside_pi, rightside_pi);

        // Instead, implement it in Analytics page
        // High Low
        highlow = new Tab("High-Low Chart");
        highLowPage = new HorizontalLayout();
        highLowPage.setWidthFull();

        highlow_chart1 = new Chart(ChartType.CANDLESTICK);
        highlow_chart1.setTimeline(true);
        hl_configuration1 = highlow_chart1.getConfiguration();
        hl_rangeSelector1 = new RangeSelector();
        hl_configuration1.setRangeSelector(hl_rangeSelector1);
        hl_configuration1.getTitle().setText("Stock Price");
        hl_dataSeries1 = new DataSeries();
        hl_plotOptionsCandlestick1 = new PlotOptionsCandlestick();
        hl_grouping1 = new DataGrouping();
        hl_grouping1.addUnit(new TimeUnitMultiples(TimeUnit.WEEK, 1));
        hl_grouping1.addUnit(new TimeUnitMultiples(TimeUnit.MONTH, 1, 2, 3, 4, 6));
        hl_plotOptionsCandlestick1.setDataGrouping(hl_grouping1);
        hl_dataSeries1.setPlotOptions(hl_plotOptionsCandlestick1);
        hl_configuration1.setSeries(hl_dataSeries1);
        highlow_chart1.drawChart();

        highlow_chart2 = new Chart(ChartType.CANDLESTICK);
        highlow_chart2.setTimeline(true);
        hl_configuration2 = highlow_chart2.getConfiguration();
        hl_rangeSelector2 = new RangeSelector();
        hl_configuration2.setRangeSelector(hl_rangeSelector2);
        hl_configuration2.getTitle().setText("Stock Price");
        hl_dataSeries2 = new DataSeries();
        hl_plotOptionsCandlestick2 = new PlotOptionsCandlestick();
        hl_grouping2 = new DataGrouping();
        hl_grouping2.addUnit(new TimeUnitMultiples(TimeUnit.WEEK, 1));
        hl_grouping2.addUnit(new TimeUnitMultiples(TimeUnit.MONTH, 1, 2, 3, 4, 6));
        hl_plotOptionsCandlestick2.setDataGrouping(hl_grouping2);
        hl_dataSeries2.setPlotOptions(hl_plotOptionsCandlestick2);
        hl_configuration2.setSeries(hl_dataSeries2);
        highlow_chart2.drawChart();

        leftside_hl = new Div();
        leftside_hl.setWidth("500px");
        rightside_hl = new Div();
        rightside_hl.setWidth("500px");
        highLowPage.add(leftside_hl, rightside_hl);
        highLowPage.setVisible(false);


        // line
        line = new Tab("Line Chart");
        linePage = new HorizontalLayout();
        linePage.setWidthFull();

        ln_chart1 = new Chart();
        ln_chart1.setTimeline(true);
        ln_conf1 = ln_chart1.getConfiguration();
        yAxis1 = new YAxis();
        label1 = new Labels();
        label1.setFormatter("function() { return (this.value > 0 ? ' + ' : '') + this.value + '%'; }");
        yAxis1.setLabels(label1);
        plotLine1 = new PlotLine();
        plotLine1.setValue(2);
        yAxis1.setPlotLines(plotLine1);
        ln_conf1.addyAxis(yAxis1);
        ln_tooltip1 = new Tooltip();
        ln_tooltip1.setPointFormat("<span>{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>");
        ln_tooltip1.setValueDecimals(2);
        ln_conf1.setTooltip(ln_tooltip1);
        ln_plotOptionsSeries1 = new PlotOptionsSeries();
        ln_plotOptionsSeries1.setCompare(Compare.PERCENT);
        ln_plotOptionsSeries1.setShowInLegend(true);
        ln_conf1.setPlotOptions(ln_plotOptionsSeries1);
        ln_rangeSelector1 = new RangeSelector();
        ln_rangeSelector1.setSelected(4);
        ln_conf1.setRangeSelector(ln_rangeSelector1);

        ln_chart2 = new Chart();
        ln_chart2.setTimeline(true);
        ln_conf2 = ln_chart2.getConfiguration();
        yAxis2 = new YAxis();
        label2 = new Labels();
        label2.setFormatter("function() { return (this.value > 0 ? ' + ' : '') + this.value + '%'; }");
        yAxis2.setLabels(label2);
        plotLine2 = new PlotLine();
        plotLine2.setValue(2);
        yAxis2.setPlotLines(plotLine2);
        ln_conf2.addyAxis(yAxis2);
        ln_tooltip2 = new Tooltip();
        ln_tooltip2.setPointFormat("<span>{series.name}</span>: <b>{point.y}</b> ({point.change}%)<br/>");
        ln_tooltip2.setValueDecimals(2);
        ln_conf2.setTooltip(ln_tooltip2);
        ln_plotOptionsSeries2 = new PlotOptionsSeries();
        ln_plotOptionsSeries2.setCompare(Compare.PERCENT);
        ln_plotOptionsSeries2.setShowInLegend(true);
        ln_conf2.setPlotOptions(ln_plotOptionsSeries2);
        ln_rangeSelector2 = new RangeSelector();
        ln_rangeSelector2.setSelected(4);
        ln_conf2.setRangeSelector(ln_rangeSelector2);

        leftside_ln = new Div();
        leftside_ln.setWidth("500px");
        rightside_ln = new Div();
        rightside_ln.setWidth("500px");
        linePage.add(leftside_ln, rightside_ln);
        linePage.setVisible(false);

        //Todo: add high-low
        tabs = new Tabs(pie, highlow, line);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setSelectedTab(pie);
        selectedPage = piePage;
        pages = new HorizontalLayout(piePage, highLowPage, linePage);
        tabsToPages = new HashMap<>();
        tabsToPages.put(pie, piePage);
        tabsToPages.put(highlow, highLowPage);
        tabsToPages.put(line, linePage);
        pagesShown = Stream.of(piePage).collect(Collectors.toSet());

        tabs.addSelectedChangeListener(e -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        mainLayout.add(buttons, tabs, pages);

        gridSide = new VerticalLayout();
        grid = new Grid<>();
        grid.setWidth("200px");
        try {
            portfolioList = defaultApi.portfolioSearchGet(userLogin.getUser().getId(), null);
        } catch (ApiException ae) {
            log.info(ae.getCode() + " found in AnalyticsCompare while getting portfolioSearch");
        }
        grid.setItems(portfolioList);
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        /**
         * When user click the portfolio, it goes to the first or second button depending on the empty button
         * after put portfolio into the button, then click the tabs
         * then charts will be shown depending on the tab name (chart type)
         */

        grid.addItemClickListener(e -> {
            if(first_portfolio.getText().equals("Add Portfolio")) {
                first_portfolio.setText(e.getItem().getName());
                if(selectedPage.equals(piePage)) {
                    pie_series1.clear();
                    // reset configuration
                    pie_chart1 = new Chart(ChartType.PIE);
                    pie_conf1 = pie_chart1.getConfiguration();
                    pie_conf1.setTooltip(pie_tooltip1);
                    pie_conf1.setPlotOptions(pie_options1);
                    pie_series1 = new DataSeries();
                    try {
                        ap_list = defaultApi.assetProportionsGet(e.getItem().getId(), "");
                    } catch (ApiException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        hal = defaultApi.holdingAssetSearchGet(e.getItem().getId());
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holding asset search");
                    }

                    if(hal.isEmpty()) {
                        leftside_pi.removeAll();
                    } else {
                        for(AssetProportion ap : ap_list) {
                            pie_series1.add(new DataSeriesItem(ap.getAssetTicker(), ap.getAssetNetPriceProportion()));
                        }
                        pie_conf1.setSeries(pie_series1);
                        leftside_pi.add(pie_chart1);
                    }
                } else if(selectedPage.equals(highLowPage)) {

                    try {
                        hl_first_list = defaultApi.holdingassetpricedataGet(e.getItem().getId(), null, null);
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holdingassetpricedata");
                    }
                    hl_first_grid = new Grid<>();
                    hl_first_grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
                    hl_first_grid.setItems(hl_first_list);
                    hl_first_grid.addColumn(AssetPriceOverTime::getAssetName).setHeader("Asset Name");
                    hl_first_grid.addItemClickListener(ev -> {
                        //if click the asset, show its high low chart
                        log.info("Asset Name: " + ev.getItem().getAssetName() + " in high low chart of Analytics");
                        leftside_hl.removeAll();
                        // reset configuration
                        highlow_chart1 = new Chart(ChartType.CANDLESTICK);
                        hl_configuration1 = highlow_chart1.getConfiguration();
                        hl_configuration1.setRangeSelector(hl_rangeSelector1);
                        hl_configuration1.getTitle().setText(ev.getItem().getAssetName());
                        hl_dataSeries1.clear();
                        for(AssetPriceData data : ev.getItem().getAssetPriceDataList()) {
                            OhlcItem item = new OhlcItem();
                            item.setX(new Date(data.getDateTime().getYear(), data.getDateTime().getMonthValue(), data.getDateTime().getDayOfMonth()));
                            item.setLow(data.getLowPrice());
                            item.setHigh(data.getHighPrice());
                            item.setOpen(data.getOpenPrice());
                            item.setClose(data.getClosePrice());
                            hl_dataSeries1.add(item);
                        }
                        hl_configuration1.setSeries(hl_dataSeries1);
                        highlow_chart1.drawChart();
                        leftside_hl.add(highlow_chart1);
                    });
                    gridSide.add(hl_first_grid);

                } else if(selectedPage.equals(linePage)) {
                    leftside_ln.removeAll();
                    // reset configuration
                    ln_chart1 = new Chart();
                    ln_conf1 = ln_chart1.getConfiguration();
                    ln_conf1.addyAxis(yAxis1);
                    ln_conf1.setTooltip(ln_tooltip1);
                    ln_conf1.setPlotOptions(ln_plotOptionsSeries1);
                    ln_conf1.setRangeSelector(ln_rangeSelector1);
                    try {
                        ln_list = defaultApi.holdingassetpricedataGet(e.getItem().getId(), null, null);
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holdingassetpricedata");
                    }

                    try {
                        hal = defaultApi.holdingAssetSearchGet(e.getItem().getId());
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holding asset search");
                    }

                    if(hal.isEmpty()) {
                        leftside_ln.removeAll();
                    } else {
                        for(AssetPriceOverTime apot : ln_list) {
                            DataSeries ds = new DataSeries();
                            ds.setName(apot.getAssetName());
                            for(AssetPriceData apd : apot.getAssetPriceDataList()) {
                                DataSeriesItem item = new DataSeriesItem();
                                item.setX(new Date(apd.getDateTime().getYear(), apd.getDateTime().getMonthValue(), apd.getDateTime().getDayOfMonth()));
                                // TODO: Change this value
                                item.setY(apd.getHighPrice());
                                ds.add(item);
                            }
                            ln_conf1.addSeries(ds);
                        }
                        leftside_ln.add(ln_chart1);
                    }
                }
            } else if (second_portfolio.getText().equals("Add Portfolio")) {
                second_portfolio.setText(e.getItem().getName());
                if(selectedPage.equals(piePage)) {
                    pie_series2.clear();
                    // reset configuration
                    pie_chart2 = new Chart(ChartType.PIE);
                    pie_conf2 = pie_chart2.getConfiguration();
                    pie_conf2.setTooltip(pie_tooltip2);
                    pie_conf2.setPlotOptions(pie_options2);
                    pie_series2 = new DataSeries();

                    try {
                        ap_list = defaultApi.assetProportionsGet(e.getItem().getId(), "");
                    } catch (ApiException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        hal = defaultApi.holdingAssetSearchGet(e.getItem().getId());
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holding asset search");
                    }

                    if(hal.isEmpty()) {
                        rightside_pi.removeAll();
                    } else {
                        for(AssetProportion ap : ap_list) {
                            pie_series2.add(new DataSeriesItem(ap.getAssetTicker(), ap.getAssetNetPriceProportion()));
                        }
                        pie_conf2.setSeries(pie_series2);
                        rightside_pi.add(pie_chart2);
                    }
                } else if(selectedPage.equals(highLowPage)) {

                    try {
                        hl_second_list = defaultApi.holdingassetpricedataGet(e.getItem().getId(), null, null);
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holdingassetpricedata");
                    }
                    hl_second_grid = new Grid<>();
                    hl_second_grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
                    hl_second_grid.setItems(hl_second_list);
                    hl_second_grid.addColumn(AssetPriceOverTime::getAssetName).setHeader("Asset Name");
                    hl_second_grid.addItemClickListener(ev -> {
                        //if click the asset, show its high low chart
                        log.info("Asset Name: " + ev.getItem().getAssetName() + " in high low chart of Analytics");
                        rightside_hl.removeAll();
                        // reset configuration
                        highlow_chart2 = new Chart(ChartType.CANDLESTICK);
                        hl_configuration2 = highlow_chart2.getConfiguration();
                        hl_configuration2.setRangeSelector(hl_rangeSelector2);
                        hl_configuration2.getTitle().setText(ev.getItem().getAssetName());
                        hl_dataSeries2.clear();
                        for(AssetPriceData data : ev.getItem().getAssetPriceDataList()) {
                            OhlcItem item = new OhlcItem();
                            item.setX(new Date(data.getDateTime().getYear(), data.getDateTime().getMonthValue(), data.getDateTime().getDayOfMonth()));
                            item.setLow(data.getLowPrice());
                            item.setHigh(data.getHighPrice());
                            item.setOpen(data.getOpenPrice());
                            item.setClose(data.getClosePrice());
                            hl_dataSeries2.add(item);
                        }
                        hl_configuration2.setSeries(hl_dataSeries2);
                        highlow_chart2.drawChart();
                        rightside_hl.add(highlow_chart2);
                    });
                    gridSide.add(hl_second_grid);

                } else if(selectedPage.equals(linePage)) {
                    rightside_ln.removeAll();
                    // reset configuration
                    ln_chart2 = new Chart();
                    ln_conf2 = ln_chart2.getConfiguration();
                    ln_conf2.addyAxis(yAxis2);
                    ln_conf2.setTooltip(ln_tooltip2);
                    ln_conf2.setPlotOptions(ln_plotOptionsSeries2);
                    ln_conf2.setRangeSelector(ln_rangeSelector2);

                    try {
                        ln_list = defaultApi.holdingassetpricedataGet(e.getItem().getId(), null, null);
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holdingassetpricedata");
                    }

                    try {
                        hal = defaultApi.holdingAssetSearchGet(e.getItem().getId());
                    } catch (ApiException ae) {
                        log.info(ae.getCode() + " found in AnalyticsCompare while getting holding asset search");
                    }

                    if(hal.isEmpty()) {
                        rightside_ln.removeAll();
                    } else {
                        for(AssetPriceOverTime apot : ln_list) {
                            DataSeries ds = new DataSeries();
                            ds.setName(apot.getAssetName());
                            for(AssetPriceData apd : apot.getAssetPriceDataList()) {
                                DataSeriesItem item = new DataSeriesItem();
                                item.setX(new Date(apd.getDateTime().getYear(), apd.getDateTime().getMonthValue(), apd.getDateTime().getDayOfMonth()));
                                // TODO: Change this value
                                item.setY(apd.getHighPrice());
                                ds.add(item);
                            }
                            ln_conf2.addSeries(ds);
                        }
                        rightside_ln.add(ln_chart2);
                    }
                }
            }
        });

        grid.addColumn(Portfolio::getName).setHeader("Portfolio List");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        gridSide.add(grid);
        add(mainLayout, gridSide);

    }
}