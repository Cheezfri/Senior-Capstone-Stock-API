package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransaction;
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
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class PortfolioTransactionUpdateApiController implements PortfolioTransactionUpdateApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioTransactionUpdateApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioTransactionUpdateApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioTransactionUpdatePost(@NotNull @ApiParam(value = "The transaction to be updated", required = true) @Valid @RequestParam(value = "transactionId", required = true) Long transactionId, @ApiParam(value = "the new quantity of the asset") @Valid @RequestParam(value = "assetQuantity", required = false) Double assetQuantity, @ApiParam(value = "the new date on which the asset has been sold or purchased") @Valid @RequestParam(value = "newDate", required = false) OffsetDateTime newDate, @ApiParam(value = "the new price of the asset") @Valid @RequestParam(value = "assetPrice", required = false) Double assetPrice) {
        String accept = request.getHeader("Accept");
        log.info("Portfoliotransactionupdate is being called in the backend");
        // user has to update either the quantity of the asset involved in a portfoliotransacttion
        // or the date on which the portfolio was transacted
        // or both

        //check if both quantity and date are null - if yes then return error code
        //else check if one of tthem needs to be updated or both of them needs to be updated

        if ((assetQuantity == null || assetQuantity == 0) && (newDate == null) && assetPrice ==null) {
            log.error("Invalid input is porvided");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        DefaultApi apiAccess = new DefaultApi();
        //we need to retrieve a porfoliotransaction item

        ApiResponse<PortfolioTransaction> toUpdateTransactionApiResponse = null;
        try {
            toUpdateTransactionApiResponse = apiAccess.portfolioTransactionIdGetWithHttpInfo(transactionId); //404,500
        } catch (ApiException ex) {
            log.error("Could not retrievve the portfoliotransacion from the backend using the portfolioTransactionIdGet, errorstatuscode: " + ex.getCode(), ex);
            if (ex.getCode() == 404) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PortfolioTransaction toUpdateTransaction = toUpdateTransactionApiResponse.getData();

        if(assetQuantity !=null && assetQuantity !=0){
            log.info("New Asset quantity has been provided");
            toUpdateTransaction.setQuantity(assetQuantity);
        }

        if(assetPrice !=null && assetPrice !=0){
            log.info("New AssetPrice has been set");
            toUpdateTransaction.setPrice(assetPrice);
        }

        if(newDate != null){
            if(assetPrice == null || assetPrice ==0){
                ApiResponse<List<AssetPriceData>> priceDataList = null;
                try{
                    priceDataList = apiAccess.assetPriceDatasGetWithHttpInfo(toUpdateTransaction.getAssetId(),newDate,newDate.plusDays(1));
                } catch (ApiException ex){
                    log.error("Could not retrieve assetprocedatas form be using assetpricedatasget , errorstatuscode: "+ex.getCode(),ex);
                    log.info("New startdate"+newDate);
                    log.info("New enddate"+newDate.plusDays(1));
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if(priceDataList.getData().size()>1){
                    log.error("More than one assetprice data objects were returned by the be");
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                if(priceDataList.getData().get(0).getAdjustedClosePrice() !=null && priceDataList.getData().get(0).getAdjustedClosePrice() !=0){
                    log.info("Retrieved assetprice data has a closing price");
                    toUpdateTransaction.setPrice(priceDataList.getData().get(0).getAdjustedClosePrice());
                } else{
                    log.error("The rettrieved asset price data has no closing price and also no user price has been given at input");
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            log.info("New Date has been set");
            toUpdateTransaction.setDateTime(newDate);
        }

        ApiResponse updateResponse = null;
        try {
            updateResponse = apiAccess.portfolioTransactionIdPutWithHttpInfo(toUpdateTransaction, transactionId); //304,404,500
        } catch (ApiException ex) {
            log.error("Could not update portfolioTransaction using portfolioTransactionPut, errorstatuscode: " + ex.getCode(), ex);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
