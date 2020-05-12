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

public final class CurrencyExchange {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyExchange.class);

    private CurrencyExchange() {
        //Utility Classes are not instantiated
    }

    /**
     * Gets currency exchange rates for first time call
     *
     * @param currencyNames = the currencyNames that needs to put in the database, Asset.getTicker() saved in format
     *                      "USD_EUR" which means convert from USD currency to EUR currency
     * @return = List<AssetPriceData> of all available days of the currency exchange
     * @return = empty ArrayList<> if AlphaVantage API call error
     * @throws IOException needed
     */
    public static List<AssetPriceData> getCurrencyFirstTime(Asset currencyNames) throws IOException {
        if (!currencyNames.getCategory().equals(AssetCategory.CURRENCY)) {
            throw new IllegalArgumentException("I only handle currency");
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String[] currenciesToGet = currencyNames.getTicker().split("_");
            if (currenciesToGet.length == 2){
                String fromCurrency = currenciesToGet[0];
                String toCurrency = currenciesToGet[1];
                String apiRequest = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=" + fromCurrency
                        + "&to_symbol=" + toCurrency + "&outputsize=full" + "&apikey=" + ApiKeys.getCurrentKey();
                HttpGet httpGet = new HttpGet(apiRequest);
                try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                    ObjectMapper mapper = new ObjectMapper();
                    HttpEntity entity1 = response1.getEntity();
                    JsonNode[] nodes = ParseJson.getNodesCurrency(entity1, mapper);
                    if (nodes.length == 2) {
                        String timeZone = ParseJson.getTimeZoneCurrency(nodes[0]);
                        EntityUtils.consume(entity1);
                        return ParseJson.parseTimeSeriesCurrency(currencyNames, nodes[1], timeZone);
                    } else {
                        EntityUtils.consume(entity1);
                        return new ArrayList<>();
                    }
                }
            }else{
                LOG.error("Asset ID {} of type {} is not of the right format 'USD_EUR'", currencyNames.getId(), currencyNames.getCategory());
                throw new IllegalArgumentException("currencyName not saved correctly");
            }
        }
    }

    /**
     * This method updates the last 100 days of the currency exchange rates from param
     *
     * @param currencyNames = the currencyNames that needs to be updated,
     *                       Asset.getTicker() saved in format
     *                      "USD_EUR" which means convert from USD currency to EUR currency
     * @return = List<AssetPriceData> of last 100 days of the currency exchange
     * @return = empty ArrayList<> if AlphaVantage API call error
     * @throws IOException needed
     */
    public static List<AssetPriceData> getCurrencyUpdate(Asset currencyNames) throws IOException {
        if (!currencyNames.getCategory().equals(AssetCategory.CURRENCY)) {
            throw new IllegalArgumentException("I only handle currency");
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String[] currenciesToGet = currencyNames.getTicker().split("_");
            if (currenciesToGet.length == 2){
                String fromCurrency = currenciesToGet[0];
                String toCurrency = currenciesToGet[1];
                String apiRequest = "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=" + fromCurrency
                        + "&to_symbol=" + toCurrency + "&outputsize=compact" + "&apikey=" + ApiKeys.getCurrentKey();
                HttpGet httpGet = new HttpGet(apiRequest);
                try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                    ObjectMapper mapper = new ObjectMapper();
                    HttpEntity entity1 = response1.getEntity();
                    JsonNode[] nodes = ParseJson.getNodesCurrency(entity1, mapper);
                    if (nodes.length == 2) {
                        String timeZone = ParseJson.getTimeZoneCurrency(nodes[0]);
                        EntityUtils.consume(entity1);
                        return ParseJson.parseTimeSeriesCurrency(currencyNames, nodes[1], timeZone);
                    } else {
                        EntityUtils.consume(entity1);
                        return new ArrayList<>();
                    }
                }
            }else{
                LOG.error("Asset ID {} of type {} is not of the right format 'USD_EUR'", currencyNames.getId(), currencyNames.getCategory());
                throw new IllegalArgumentException("currencyName not saved correctly");
            }
        }
    }
}