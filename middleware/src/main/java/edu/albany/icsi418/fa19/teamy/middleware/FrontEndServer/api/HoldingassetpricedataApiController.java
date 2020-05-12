package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceOverTime;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.HoldingAsset;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-26T18:04:30.614Z[GMT]")
@Controller
public class HoldingassetpricedataApiController implements HoldingassetpricedataApi {

    private static final Logger log = LoggerFactory.getLogger(HoldingassetpricedataApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public HoldingassetpricedataApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<AssetPriceOverTime>> holdingassetpricedataGet(@NotNull @ApiParam(value = "Id of the portfolio", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId, @ApiParam(value = "to get the assetprice data starting from a particular date") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate, @ApiParam(value = "to get the assetprice data till a particular date") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("Holdingassetpricedataget is called in MW");
            if (portfolioId == null || portfolioId == 0) {
                return new ResponseEntity<List<AssetPriceOverTime>>(HttpStatus.BAD_REQUEST);
            }

            DefaultApi apiAccess = new DefaultApi();

            ApiResponse<Portfolio> userPortfolio = null;
            try {
                userPortfolio = apiAccess.portfolioIdGetWithHttpInfo(portfolioId);
            } catch (ApiException ex) {
                log.error("An error has occured trying to retrieve the portfolio object using portfolioIdGet, errorstatuscode: " + ex.getCode());
            }

            if (userPortfolio.getData().isDeleted() == true) {
                log.error("The retreved portfolio is deleted already");
                return new ResponseEntity<List<AssetPriceOverTime>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<HoldingAsset> holdingAssetList = null;
            try {
                holdingAssetList = Utility.getHoldingAssets(portfolioId);
            } catch (ApiException ex) {
                log.error("An error has occured trying to retrieve the holding assets using utility metod, errorstatuscode: " + ex.getCode());
            }

            if (holdingAssetList.isEmpty()) {
                log.info("No Holding assets were found for the portfolio or an error occured trying to get them");
                return new ResponseEntity<List<AssetPriceOverTime>>(HttpStatus.NOT_FOUND);
            }

            List<AssetPriceOverTime> returnAssetPriceList = new ArrayList<AssetPriceOverTime>();
            for(HoldingAsset item: holdingAssetList){
                AssetPriceOverTime toBeAdded = new AssetPriceOverTime();
                List<AssetPriceData> eachAssetPriceData = new ArrayList<AssetPriceData>();
                List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AssetPriceData> backendPriceObjects = null;
                try {
                    backendPriceObjects = apiAccess.assetPriceDatasGet(item.getOwnedAssetId(), startDate, endDate);
                } catch (ApiException ex){
                    log.error("Could not retrieve the assetprice using assetpricedataget, errorstatuscode: "+ex.getCode());
                    return new ResponseEntity<List<AssetPriceOverTime>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                toBeAdded.setAssetId(item.getOwnedAssetId());
                toBeAdded.setAssetName(item.getAssetName());
                for(edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AssetPriceData priceDataItem: backendPriceObjects){
                    AssetPriceData frontPriceItem = new AssetPriceData();
                    frontPriceItem.setId(priceDataItem.getId());
                    frontPriceItem.setAssetId(priceDataItem.getAssetId());
                    frontPriceItem.setDateTime(priceDataItem.getDateTime());
                    frontPriceItem.setOpenPrice(priceDataItem.getOpenPrice());
                    frontPriceItem.setClosePrice(priceDataItem.getClosePrice());
                    frontPriceItem.setHighPrice(priceDataItem.getHighPrice());
                    frontPriceItem.setLowPrice(priceDataItem.getLowPrice());
                    frontPriceItem.setAdjustedClosePrice(priceDataItem.getAdjustedClosePrice());
                    eachAssetPriceData.add(frontPriceItem);
                }
                toBeAdded.setAssetPriceDataList(eachAssetPriceData);
                returnAssetPriceList.add(toBeAdded);
            }

            return new ResponseEntity<List<AssetPriceOverTime>>(returnAssetPriceList,HttpStatus.OK);
        }

        return new ResponseEntity<List<AssetPriceOverTime>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
