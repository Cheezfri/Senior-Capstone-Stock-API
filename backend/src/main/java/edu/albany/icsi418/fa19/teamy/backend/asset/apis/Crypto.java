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

public final class Crypto {
    private static final Logger LOG = LoggerFactory.getLogger(Crypto.class);

    private Crypto() {
        //Utility Classes are not instantiated
    }

    /**
     * Gets crypto rates for first time call
     *
     * @param cryptoNames = the cryptoNames that needs to put in the database, Asset.getTicker() saved in format
     *                      "BTC_USD" which means convert from 1 Bitcoin crypto to however many USD currency
     * @return = List<AssetPriceData> of all available days of the crypto exchange
     * @return = empty ArrayList<> if AlphaVantage API call error
     * @throws IOException needed
     */
    public static List<AssetPriceData> getCryptoData(Asset cryptoNames) throws IOException {
        if (!cryptoNames.getCategory().equals(AssetCategory.CRYPTO)) {
            throw new IllegalArgumentException("I only handle crypto");
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String[] cryptosToGet = cryptoNames.getTicker().split("_");
            if (cryptosToGet.length == 2){
                String cryptoName = cryptosToGet[0];
                String currencyName = cryptosToGet[1];
                String apiRequest = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + cryptoName
                        + "&market=" + currencyName + "&apikey=" + ApiKeys.getCurrentKey();
                HttpGet httpGet = new HttpGet(apiRequest);
                try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                    ObjectMapper mapper = new ObjectMapper();
                    HttpEntity entity1 = response1.getEntity();
                    JsonNode[] nodes = ParseJson.getNodesCrypto(entity1, mapper);
                    if (nodes.length == 2) {
                        String timeZone = ParseJson.getTimeZoneCrypto(nodes[0]);
                        EntityUtils.consume(entity1);
                        return ParseJson.parseTimeSeriesCrypto(cryptoNames, nodes[1], timeZone);
                    } else {
                        EntityUtils.consume(entity1);
                        return new ArrayList<>();
                    }
                }
            }else{
                LOG.error("Asset ID {} of type {} is not of the right format 'BTC_USD'", cryptoNames.getId(), cryptoNames.getCategory());
                throw new IllegalArgumentException("cryptoNames not saved correctly");
            }
        }
    }

}
