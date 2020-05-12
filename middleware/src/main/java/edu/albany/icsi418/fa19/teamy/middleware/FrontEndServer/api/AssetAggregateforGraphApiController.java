package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetAggregateData;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetCategory;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceDataWithAnalytic;
import org.threeten.bp.OffsetDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-07T21:36:00.664Z[GMT]")
@Controller
public class AssetAggregateforGraphApiController implements AssetAggregateforGraphApi {

    private static final Logger log = LoggerFactory.getLogger(AssetAggregateforGraphApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AssetAggregateforGraphApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<AssetAggregateData> assetAggregateforGraphGet(@NotNull @ApiParam(value = "Id of the portfolio", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId, @ApiParam(value = "The start date of the analytic calculation") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate, @ApiParam(value = "The end date of the analytic calculation") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("assetAggregateforGraphGet called in the MW");
            if (portfolioId == null || portfolioId == 0) {
                return new ResponseEntity<AssetAggregateData>(HttpStatus.BAD_REQUEST);
            }
            //get the functions from backend
            DefaultApi apiAccess = new DefaultApi();

            ApiResponse<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AssetAggregateData> retrievedAssetAnalytics = null;
            try {
                retrievedAssetAnalytics = apiAccess.analyticAssetAggregateGetWithHttpInfo(portfolioId, startDate, endDate);
            } catch (ApiException ex) {
                log.error("An error has occured trying to retrieve asset analytics form the BE using analytic asset aggregate get, errorstatuscode: " + ex.getCode());
                return new ResponseEntity<AssetAggregateData>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
            AssetAggregateData returnAssetAnalytics = new AssetAggregateData();
            returnAssetAnalytics.setPortfolioId(retrievedAssetAnalytics.getData().getPortfolioId());
            returnAssetAnalytics.setName(retrievedAssetAnalytics.getData().getName());

            returnAssetAnalytics.setStartDate(retrievedAssetAnalytics.getData().getStartDate());
            returnAssetAnalytics.setEndDate(retrievedAssetAnalytics.getData().getEndDate());
            for(Asset item: retrievedAssetAnalytics.getData().getAssetsInPortfolio()){
                //errorprone
                edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Asset assetToBeAdded = new edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Asset();
                assetToBeAdded.setId(item.getId());
                assetToBeAdded.setName(item.getName());
                assetToBeAdded.setTicker(item.getTicker());
                assetToBeAdded.setDescription(item.getDescription());
                if ("STOCK".equals(item.getCategory().getValue())) {
                    assetToBeAdded.setCategory(AssetCategory.STOCK);
                } else if ("BOND".equals(item.getCategory().getValue())) {
                    assetToBeAdded.setCategory(AssetCategory.BOND);
                } else if ("CRYPTO".equals(item.getCategory().getValue())) {
                    assetToBeAdded.setCategory(AssetCategory.CRYPTO);
                } else if ("CURRENCY".equals(item.getCategory().getValue())) {
                    assetToBeAdded.setCategory(AssetCategory.CURRENCY);
                }
                returnAssetAnalytics.addAssetsInPortfolioItem(assetToBeAdded);
            }

            double twelveDayMultipler = 0.154;
            double twentySixDayMultiplier = 0.074;

            HashMap<String, List<AssetPriceDataWithAnalytic>> frontEndHashmap = new HashMap<String, List<AssetPriceDataWithAnalytic>>();
            retrievedAssetAnalytics.getData().getAssetValueHashMap().forEach((k, v) -> {
                double i = 0;
                double twelveEMA = 0;
                double twentySixEMA = 0;
                double multiplier = 0;
                double simpleMovingAverage = 0;

                double startPrice = v.get(0).getAdjustedClosePrice();
                List<AssetPriceDataWithAnalytic> backendListofPricedata = new ArrayList<AssetPriceDataWithAnalytic>();
                for (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AssetPriceData item : v) {
                    AssetPriceDataWithAnalytic toAddToList = new AssetPriceDataWithAnalytic();
                    log.info("i: " + i);
                    if (i < 26) {
                        simpleMovingAverage = simpleMovingAverage + item.getAdjustedClosePrice();
                        log.info("simple moving average i<26 , SMA: " + simpleMovingAverage);
                    }
                    if (i == 11) {
                        twelveEMA = Math.floor((simpleMovingAverage / 12) * 1000000) / 1000000;
                        log.info("initial twelveEMA : " + twelveEMA);
                    }
                    if (i > 11) {
                        multiplier = 2 / (i + 1);
                        twelveEMA = ((item.getAdjustedClosePrice() - twelveEMA) * (multiplier)) + twelveEMA;
                        log.info("calculation: " + (((item.getAdjustedClosePrice() - twelveEMA) * (2 / ((double) i + 1))) + twelveEMA));
                        // EMA: {Close - EMA(previous day)}*Multiplier + EMA(previousDay)
                    }
                    if (i == 25) {

                        twentySixEMA = (Math.floor((simpleMovingAverage / 26) * 1000000) / 1000000);
                        log.info("i = 25 , twentysixEMA: " + twentySixEMA + " for moving average " + simpleMovingAverage);
                        toAddToList.setMacdIndex(twelveEMA - twentySixEMA);
                    }
                    if (i > 25) {
                        twentySixEMA = ((item.getAdjustedClosePrice()- twentySixEMA) * (multiplier)) + twentySixEMA;
                        log.info("calculation: " + (((item.getAdjustedClosePrice()- twentySixEMA) * (2 / (i + 1))) + twentySixEMA));
                        // EMA: {Close - EMA(previous day)}*Multiplier + EMA(previousDay)
                        log.info("12EMA: " + twelveEMA + " 26EMA: " + twentySixEMA);
                        toAddToList.setMacdIndex(twelveEMA - twentySixEMA);
                    }
                    i++;
                    toAddToList.setId(item.getId());
                    toAddToList.setAssetId(item.getAssetId());
                    toAddToList.setDateTime(item.getDateTime());
                    toAddToList.setOpenPrice(item.getOpenPrice());
                    toAddToList.setLowPrice(item.getLowPrice());
                    toAddToList.setHighPrice(item.getHighPrice());
                    toAddToList.setClosePrice(item.getClosePrice());
                    toAddToList.setAdjustedClosePrice(item.getAdjustedClosePrice());

                    if (v.indexOf(item) != 0) {
                        toAddToList.setPercentageGrowth(((item.getAdjustedClosePrice() - startPrice) / startPrice) * 100);
                    } else {
                        toAddToList.setPercentageGrowth(0D);
                        startPrice = item.getAdjustedClosePrice();
                    }
                    backendListofPricedata.add(toAddToList);
                }
                frontEndHashmap.put(k, backendListofPricedata);
            });
            returnAssetAnalytics.setAssetValueHashMap(frontEndHashmap);

            log.info("AssetAggregateforGraphApiController has executed succesfully");
            return new ResponseEntity<AssetAggregateData>(returnAssetAnalytics, HttpStatus.OK);

        }

        return new ResponseEntity<AssetAggregateData>(HttpStatus.NOT_IMPLEMENTED);
    }

}
