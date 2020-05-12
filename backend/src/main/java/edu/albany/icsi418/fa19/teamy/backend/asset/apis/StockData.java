package edu.albany.icsi418.fa19.teamy.backend.asset.apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetCategory;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility helper class that is used to call the API and retrieve the stock data
 */
public final class StockData {

    private static final Logger LOG = LoggerFactory.getLogger(StockData.class);

    private StockData() {
        // Private Constructor for Utility Class
        
    }

    /**
     * This method updates the most recent stock data from stockName
     *
     * @param stock = the stock in the database that needs to be updated
     * @return = List<AssetPriceData> of last 100 days of AssetPriceData (The stockdata)
     * @throws IOException needed
     */
    public static List<AssetPriceData> updateStockData(Asset stock) throws IOException {
        if (!stock.getCategory().equals(AssetCategory.STOCK)) {
            LOG.error("Asset ID {} of type {} passed to STOCK only method", stock.getId(), stock.getCategory());
            throw new IllegalArgumentException("I only handle stocks");
        }
       return getStockData100(stock, ApiKeys.getCurrentKey());
    }

    /**
     * This method is called when stockData is requested for the first time
     *
     * @param stock = the stock in the database that needs to be put in the database
     * @return List<AssetPriceData> is a full history of AssetPriceData (The stockdata)
     * @throws IOException needed
     */
    public static List<AssetPriceData> getStockDataFirstTime(Asset stock) throws IOException {
        if (!stock.getCategory().equals(AssetCategory.STOCK)) {
            LOG.error("Asset ID {} of type {} passed to STOCK only method", stock.getId(), stock.getCategory());
            throw new IllegalArgumentException("I only handle stocks");
        }
        return getStockDataFull(stock, ApiKeys.getCurrentKey());
    }

    /**
     * Used for updating a Stock since we only need the most recent data
     *
     * @param stock  = stock that needs to be updated
     * @param apiKey = current apiKey used to extract the data from AlphaVantage
     * @return last 100 AssetPriceData of the stock
     * return empty ArrayList<> if APIcall error in getNodesStock() function
     * @throws IOException from HTTP connection
     */
    private static List<AssetPriceData> getStockData100(Asset stock, String apiKey) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String apiRequest = "https://www.alphavantage.co/query?function=" + "TIME_SERIES_DAILY_ADJUSTED" +
                    "&symbol=" + stock.getTicker() + "&apikey=" + apiKey;
            HttpGet httpGet = new HttpGet(apiRequest);
            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                ObjectMapper mapper = new ObjectMapper();
                HttpEntity entity1 = response1.getEntity();
                JsonNode[] nodes = ParseJson.getNodesStock(entity1, mapper);
                if (nodes.length == 2) {
                    String timeZone = ParseJson.getTimeZoneStock(nodes[0]);
                    EntityUtils.consume(entity1);
                    return ParseJson.parseTimeSeriesStock(stock, nodes[1], timeZone);
                } else {
                    EntityUtils.consume(entity1); //Master didn't have this but i here its good practice to keep this?
                    return new ArrayList<>();
                }
            }
        }
    }

    /**
     * This method returns the full stockData to date
     *
     * @param stock  = stock that needs full history of stock
     * @param apiKey = current apiKey used to extract the data from AlphaVantage
     * @return ArrayList of last 100 days of AssetPriceData
     * return empty ArrayList<> if APIcall error in getNodesStock() function
     * @throws IOException from HTTP connection
     */
    private static List<AssetPriceData> getStockDataFull(Asset stock, String apiKey) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String apiRequest = "https://www.alphavantage.co/query?function=" + "TIME_SERIES_DAILY_ADJUSTED" + "&symbol="
                    + stock.getTicker() + "&outputsize=full" + "&apikey=" + apiKey;
            HttpGet httpGet = new HttpGet(apiRequest);
            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                ObjectMapper mapper = new ObjectMapper();
                HttpEntity entity1 = response1.getEntity();
                JsonNode[] nodes = ParseJson.getNodesStock(entity1, mapper);
                if (nodes.length == 2) {
                    String timeZone = ParseJson.getTimeZoneStock(nodes[0]);
                    EntityUtils.consume(entity1);
                    return ParseJson.parseTimeSeriesStock(stock, nodes[1], timeZone);
                } else {
                    EntityUtils.consume(entity1); //Master didn't have this but i here its good practice to keep this?
                    return new ArrayList<>();
                }
            }
        }
    }
}

