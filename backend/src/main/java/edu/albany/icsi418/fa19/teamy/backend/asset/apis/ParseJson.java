package edu.albany.icsi418.fa19.teamy.backend.asset.apis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a utility function called by the StockData class to help parse the JSON of AlphaVantage
 */
public final class ParseJson {
    private static final Logger log = LoggerFactory.getLogger(ParseJson.class);

    private ParseJson() {
        // Do not instantiate Utility classes
    }

    /**
     * Used to get the nodes from HttpEntity jsonTree
     *
     * @param entity1 = HttpEntity that is parsed to find nodes
     * @param mapper  = used to map the HttpEntity to a jsonTree
     * @return returns two nodes("Meta Data", "Time Series (Daily)") of Stock Data.
     * returns empty JsonNode[0] if error in APIcall
     * @throws IOException needed
     */
    public static JsonNode[] getNodesStock(HttpEntity entity1, ObjectMapper mapper) throws IOException {
        String jsonString = EntityUtils.toString(entity1);
        int sizeOfTree = mapper.readTree(jsonString).size();
        JsonNode nodeToParse = mapper.readTree(jsonString);
        if (sizeOfTree == 2) {
            JsonNode[] nodesToReturn = new JsonNode[2];
            nodesToReturn[0] = nodeToParse.get("Meta Data");
            nodesToReturn[1] = nodeToParse.get("Time Series (Daily)");
            return nodesToReturn;
        }else{
            log.info("Nodes not of size 2: {}", nodeToParse);
            return new JsonNode[0];
        }
    }

    /**
     * Used to get the nodes from HttpEntity jsonTree
     * @param entity1 = HttpEntity that is parsed to find nodes
     * @param mapper = used to map the HttpEntity to a jsonTree
     * @return returns two nodes("Meta Data", "Time Series FX (Daily)") of currency.
     * returns empty JsonNode[0] if error in APIcall
     * @throws IOException needed
     */
    public static JsonNode[] getNodesCurrency(HttpEntity entity1, ObjectMapper mapper) throws IOException  {
        String jsonString = EntityUtils.toString(entity1);
        int sizeOfTree = mapper.readTree(jsonString).size();
        JsonNode nodeToParse = mapper.readTree(jsonString);
        if (sizeOfTree == 2){
            JsonNode[] nodesToReturn = new JsonNode[2];
            nodesToReturn[0] = nodeToParse.get("Meta Data");
            nodesToReturn[1] = nodeToParse.get("Time Series FX (Daily)");
            return nodesToReturn;
        }else {
            log.info("Nodes not of size 2: {}", nodeToParse);
            return new JsonNode[0];
        }
    }

    /**
     * Used to get the nodes from HttpEntity jsonTree
     * @param entity1 = HttpEntity that is parsed to find nodes
     * @param mapper = used to map the HttpEntity to a jsonTree
     * @return returns two nodes("Meta Data", "Time Series (Digital Currency Daily)") of crypto.
     * returns empty JsonNode[0] if error in APIcall
     * @throws IOException needed
     */
    public static JsonNode[] getNodesCrypto(HttpEntity entity1, ObjectMapper mapper) throws IOException  {
        String jsonString = EntityUtils.toString(entity1);
        int sizeOfTree = mapper.readTree(jsonString).size();
        JsonNode nodeToParse = mapper.readTree(jsonString);
        if (sizeOfTree == 2){
            JsonNode[] nodesToReturn = new JsonNode[2];
            nodesToReturn[0] = nodeToParse.get("Meta Data");
            nodesToReturn[1] = nodeToParse.get("Time Series (Digital Currency Daily)");
            return nodesToReturn;
        }else {
            log.info("Nodes not of size 2: {}", nodeToParse);
            return new JsonNode[0];
        }
    }

    /**
     * Used to get timezone in the format to use for OffsetDateTime for STOCK calls only
     *
     * @param metaData = JsonNode that contains the JsonNode with the metadata of apiRequest
     * @return String = time zone name Ex: US/Eastern
     */
    public static String getTimeZoneStock(JsonNode metaData) { //Gets timeZone from MetaData node
        return metaData.get("5. Time Zone").toString().replaceAll("\"", "");
    }

    /**
     * Used to get timezone in the format to use for OffsetDateTime for CURRENCY calls only
     *
     * @param metaData = JsonNode that contains the JsonNode with the metadata of apiRequest
     * @return String = time zone name Ex: US/Eastern
     */
    public static String getTimeZoneCurrency(JsonNode metaData) { //Gets timeZone from MetaData node
        return metaData.get("6. Time Zone").toString().replaceAll("\"", "");
    }

    /**
     * Used to get timezone in the format to use for OffsetDateTime for CRYPTO calls
     *
     * @param metaData = JsonNode that contains the JsonNode with the metadata of apiRequest
     * @return String = time zone name Ex: US/Eastern
     */
    public static String getTimeZoneCrypto(JsonNode metaData) { //Gets timeZone from MetaData node
        return metaData.get("7. Time Zone").toString().replaceAll("\"", "");
    }

    /**
     * Used to parse TimeSeries node of JsonTree, finds the STOCK data from this
     *
     * @param ts       = TimeSeries JsonNode
     * @param timeZone = Timezone used for OffsetDateTime
     * @return = List of StockData from TimeSeries
     */
    public static List<AssetPriceData> parseTimeSeriesStock(Asset stock, JsonNode ts, String timeZone) {
        Iterator<String> iJsonNode = ts.fieldNames();
        ArrayList<AssetPriceData> stocks = new ArrayList<>();
        while (iJsonNode.hasNext()) {
            String date = iJsonNode.next();
            AssetPriceData apd = new AssetPriceData();
            apd.setOpenPrice(ts.get(date).get("1. open").asDouble());
            apd.setHighPrice(ts.get(date).get("2. high").asDouble());
            apd.setLowPrice(ts.get(date).get("3. low").asDouble());
            apd.setClosePrice(ts.get(date).get("4. close").asDouble());
            apd.setAdjustedClosePrice(ts.get(date).get("5. adjusted close").asDouble());
            apd.setDateTime(convertStringToDateTime(date, timeZone));
            apd.setAsset(stock);
            stocks.add(apd);
        }
        return stocks;
    }

    /**
     * Used to parse TimeSeries node of JsonTree, finds the CURRENCY data from this
     *
     * @param ts       = TimeSeries JsonNode
     * @param timeZone = Timezone used for OffsetDateTime
     * @return = List of StockData from TimeSeries
     */

    public static List<AssetPriceData> parseTimeSeriesCurrency(Asset currency, JsonNode ts, String timeZone) {
        Iterator<String> iJsonNode = ts.fieldNames();
        ArrayList<AssetPriceData> currencies = new ArrayList<>();
        while (iJsonNode.hasNext()) {
            String date = iJsonNode.next();
            AssetPriceData apd = new AssetPriceData();
            apd.setOpenPrice(ts.get(date).get("1. open").asDouble());
            apd.setHighPrice(ts.get(date).get("2. high").asDouble());
            apd.setLowPrice(ts.get(date).get("3. low").asDouble());
            apd.setClosePrice(ts.get(date).get("4. close").asDouble());
            apd.setDateTime(convertStringToDateTime(date, timeZone));
            apd.setAsset(currency);
            currencies.add(apd);
        }
        return currencies;
    }

    /**
     * Used to parse TimeSeries node of JsonTree, finds the CRYPTO data from this
     *
     * @param ts       = TimeSeries JsonNode
     * @param timeZone = Timezone used for OffsetDateTime
     * @return = List of StockData from TimeSeries
     */

    public static List<AssetPriceData> parseTimeSeriesCrypto(Asset crypto, JsonNode ts, String timeZone) {
        Iterator<String> iJsonNode = ts.fieldNames();
        ArrayList<AssetPriceData> cryptos = new ArrayList<>();
        while (iJsonNode.hasNext()) {
            String date = iJsonNode.next();
            AssetPriceData apd = new AssetPriceData();
            apd.setOpenPrice(ts.get(date).get("1a. open (USD)").asDouble());
            apd.setHighPrice(ts.get(date).get("2a. high (USD)").asDouble());
            apd.setLowPrice(ts.get(date).get("3a. low (USD)").asDouble());
            apd.setClosePrice(ts.get(date).get("4a. close (USD)").asDouble());
            apd.setDateTime(convertStringToDateTime(date, timeZone));
            apd.setAsset(crypto);
            cryptos.add(apd);
        }
        return cryptos;
    }

    /**
     * Helper function to convert the string of Date from apiRequest to OffSetDateTime
     *
     * @param convertMe = String for converting
     * @param timeZone  = timezone from ApiRequest
     * @return OffsetDateTime
     */
    private static OffsetDateTime convertStringToDateTime(String convertMe, String timeZone) {
        ZoneId zone = ZoneId.of(timeZone);
        LocalDateTime ldt = LocalDateTime.parse(convertMe + "T23:59:00.00");
        ZoneOffset zoneOffSet = zone.getRules().getOffset(ldt);
        return ldt.atOffset(zoneOffSet);
    }
}