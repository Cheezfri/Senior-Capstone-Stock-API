package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.*;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.HoldingAsset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Utility {
    private static final Logger log = LoggerFactory.getLogger(Utility.class);


    public static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public static final double twelveDayMultipler = 0.154;

    public static final double twentySixDayMultiplier = 0.074;

    public static List<HoldingAsset> getHoldingAssets(long portfolioId) throws ApiException {

        log.info("getHolding assets was called in the utility class");
        DefaultApi apiAccess = new DefaultApi();
        List<HoldingAsset> toReturnList = new ArrayList<HoldingAsset>();
        //retrieve all the portfolio transactions


        ApiResponse<Portfolio> portfolioInfo = null;
        try{
            portfolioInfo = apiAccess.portfolioIdGetWithHttpInfo(portfolioId);
        } catch(ApiException ex){
            log.error("An error has occured trying to retrieve the portfolio information using portfolioID get, wrrorstatuscode: "+ex.getCode());
            return toReturnList;
        }

        ApiResponse<User> userInfo = null;
        try{
            userInfo = apiAccess.userIdGetWithHttpInfo(portfolioInfo.getData().getOwnerUserId());
        } catch(ApiException ex){
            log.error("An error has occured trying to retrieve the User information using UserID get, wrrorstatuscode: "+ex.getCode());
            return toReturnList;
        }

        ApiResponse<List<PortfolioTransaction>> allTransactions = null;
        try {
            allTransactions = apiAccess.portfolioTransactionsGetWithHttpInfo(portfolioId);
        } catch (ApiException ex) {
            log.info("Utility: Could not retrieve the portfoliotransacttions using portfoliotransactionget " + ex.getCode(), ex);
            return toReturnList;
        }

        //iterate through each of the portfolio transaction and create new holding objects and add them to the list


        List<PortfolioTransaction> visitedAsset = new ArrayList<PortfolioTransaction>();

        HashMap<Long, PortfolioTransaction> transactionHashMap = new HashMap<Long, PortfolioTransaction>();

        for (PortfolioTransaction item : allTransactions.getData()) {
            if (transactionHashMap.containsKey(item.getAssetId())) {
                log.info("Transaction was in hashMap");
                PortfolioTransaction handlingTransaction = transactionHashMap.get(item.getAssetId());
                //item.getType().getValue().equals(PortfolioTransactionType.BUY

                if (item.getType().equals(PortfolioTransactionType.SELL)) {
                    handlingTransaction.setQuantity(handlingTransaction.getQuantity()-item.getQuantity());
                    //log.info(" SELL : handling transaction id: " + handlingTransaction.getId() + " updated quantity = " + handlingTransaction.getQuantity());
                    handlingTransaction.setPrice(handlingTransaction.getPrice()- currencyConvert((item.getQuantity() * item.getPrice()),"USD",userInfo.getData().getLocalCurrency(),item.getDateTime()));
                    //log.info("SELL2: handling transaction id: " + handlingTransaction.getId() + " updated price = " + handlingTransaction.getPrice());
                } else if (item.getType().equals(PortfolioTransactionType.BUY)) {
                    handlingTransaction.setQuantity(item.getQuantity()+handlingTransaction.getQuantity());
                    //log.info(" BUY : handling transaction id: " + handlingTransaction.getId() + " updated quantity = " + handlingTransaction.getQuantity());
                    handlingTransaction.setPrice(handlingTransaction.getPrice() + currencyConvert((item.getQuantity() * item.getPrice()),"USD",userInfo.getData().getLocalCurrency(),item.getDateTime()));
                    //log.info("BUY2: handling transaction id: " + handlingTransaction.getId() + " updated price = " + handlingTransaction.getPrice());
                }

                transactionHashMap.replace(item.getAssetId(), transactionHashMap.get(item.getAssetId()), handlingTransaction);
                //log.info("Replaced in hashmap: " + transactionHashMap.get(item.getAssetId()));
            } else {
                log.info("Transaction was not in HashMap");
                if (item.getType().equals(PortfolioTransactionType.BUY)) {
                    item.setPrice(currencyConvert(item.getPrice() * item.getQuantity(),"USD",userInfo.getData().getLocalCurrency(),item.getDateTime()));
                    //log.info("BUY: First time set price: " + item.getPrice()+ "First time set quantity:"+ item.getQuantity());
                    //log.info("BUY: First time set quantity: " + item.getQuantity());
                } else if (item.getType().equals(PortfolioTransactionType.SELL)) {
                    item.setPrice(currencyConvert(0 - item.getPrice() * item.getQuantity(),"USD",userInfo.getData().getLocalCurrency(),item.getDateTime()));
                    item.setQuantity(0 - item.getQuantity());
                    //log.info("SELL: First time set price: " + item.getPrice()+ "First time set quantity:"+ item.getQuantity());
                }
                transactionHashMap.put(item.getAssetId(), item);
            }
        }
        Collection<PortfolioTransaction> hashMapValues = transactionHashMap.values();
        ArrayList<PortfolioTransaction> holdingAssetsInTransactionsFormat = new ArrayList<PortfolioTransaction>(hashMapValues);

        for (PortfolioTransaction item : holdingAssetsInTransactionsFormat) {
            HoldingAsset toBeAddedToList = new HoldingAsset();
            if(item.getQuantity() ==0 ){
                continue;
            }
            toBeAddedToList.setOwnedAssetId(item.getAssetId());
            //netvalue will now be the latest price available in the system
            ApiResponse<List<AssetPriceData>> retrieveLatestAssePrices = null;
            try{
                retrieveLatestAssePrices = apiAccess.assetPriceDatasGetWithHttpInfo(item.getAssetId(),OffsetDateTime.now().minusDays(30),OffsetDateTime.now());
            } catch (ApiException ex){
                log.error("Could not retrieve the latest prices for the asset, errorstattuscode: "+ex.getCode());
                toBeAddedToList.setNetValue(item.getPrice());
            }
            //0 is the latest item
            toBeAddedToList.setNetValue(retrieveLatestAssePrices.getData().get(retrieveLatestAssePrices.getData().size()-1).getAdjustedClosePrice() * item.getQuantity());
            toBeAddedToList.setQuantity(item.getQuantity());
            //need to make an asset get call
            ApiResponse<Asset> T2 = null;
            try {
                T2 = apiAccess.assetIdGetWithHttpInfo(item.getAssetId());
            } catch (ApiException ex) {
                //log.info(" Utility: Could not retrieve asset information using assetIdGet");
                if (ex.getCode() == 404) {
                    log.error("Utility: Could not retrieve the Assets using AssetId get" + ex.getCode(), ex);
                    //log.info("partially finished toreturnlist has been flushed");
                    toReturnList.clear();
                    return toReturnList; //list is empty when any error occurs
                }
                if (ex.getCode() == 500) {
                    log.error("Utility: Could not retrieve the Asset using AssetId get" + ex.getCode(), ex);
                    //log.info("partially finished toreturnlist has been flushed");
                    toReturnList.clear();
                    return toReturnList; //list is empty when any error occurs
                }
            }

            toBeAddedToList.setAssetType(T2.getData().getCategory().getValue());
            toBeAddedToList.setAssetTicker(T2.getData().getTicker());
            toBeAddedToList.setAssetName(T2.getData().getName());
            toReturnList.add(toBeAddedToList);
        }
        return toReturnList;
    }

    public static double currencyConvert(double value, String inputCurrency, String outputCurrency, OffsetDateTime conversionDate) throws ApiException{

        DefaultApi apiAccess = new DefaultApi();

        if(inputCurrency.equalsIgnoreCase(outputCurrency)){
            log.info("The target currency and the user currency are of the same type");
            return  value;
        }

        String requiredConversion = inputCurrency + '_' + outputCurrency;
        log.info("conversion currency object required: "+requiredConversion);

        //get the asset id
        ApiResponse<List<Asset>> assetInfo = null;
        try{
            assetInfo = apiAccess.assetsGetWithHttpInfo(requiredConversion, null);
        } catch (ApiException ex){
            log.error("An error has occurred trying to retrieve the asset info of the currency using assetsGet, errorstatuscode: "+ex.getCode());
            return 0;
        }
        log.info("Asset being used for conversion : "+assetInfo.getData().get(0).getName());
        /*if(assetInfo.getData().size()>1){
            log.error("Multiple assets of same type have been found");
        }*/ //will be enabled later

        ApiResponse <List<AssetPriceData>> conversionVariable = null;
        try{
            conversionVariable = apiAccess.assetPriceDatasGetWithHttpInfo(assetInfo.getData().get(0).getId(),conversionDate.minusDays(7),conversionDate);
        } catch (ApiException ex){
            log.error("An error has occured trying to retrieve the conversion price data using assetPriceDatasGet, errorstauscode: "+ex.getCode());
            if(ex.getCode() == 404){
                log.error("No conversion rate could be found in the past seven days, so trying to find the latest one till date");
                try{
                    conversionVariable = apiAccess.assetPriceDatasGetWithHttpInfo(assetInfo.getData().get(0).getId(),null,conversionDate);
                } catch (ApiException ex2){
                    log.error("No price conversion ratte could be found for the currency before the transaction date");
                }
            } else{
                log.error(" no price data could be retrieved at all");
            }
        }

        log.info("Conversion rate date being used for conversion: "+conversionVariable.getData().get(conversionVariable.getData().size()-1).getClosePrice());

        double returnValue = value * conversionVariable.getData().get(conversionVariable.getData().size()-1).getClosePrice();
        log.info("Input value: "+value+" Ouptut value: "+returnValue);
        return  returnValue;
    }

}
