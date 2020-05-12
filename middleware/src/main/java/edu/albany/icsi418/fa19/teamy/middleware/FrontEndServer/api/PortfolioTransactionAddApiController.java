package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransaction;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTransactionItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTransactionType;
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
import org.threeten.bp.OffsetDateTime;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class PortfolioTransactionAddApiController implements PortfolioTransactionAddApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioTransactionAddApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioTransactionAddApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioTransactionAddPost(@ApiParam(value = "Filled out porttfolio transaction item", required = true) @Valid @RequestBody PortfolioTransactionItem body) {
        String accept = request.getHeader("Accept");
        PortfolioTransaction backFormatPortfolioTransaction = new PortfolioTransaction();
        log.info("PortfolioTransactionPost called in the MW");
        DefaultApi apiAccess = new DefaultApi();

        backFormatPortfolioTransaction.setAssetId(body.getAssetId());
        backFormatPortfolioTransaction.setDateTime(body.getDate());
        backFormatPortfolioTransaction.setPortfolioId(body.getPortfolioId());
        backFormatPortfolioTransaction.setQuantity(body.getQuantity());

        if (body.getTransactionType().equals(PortfolioTransactionType.BUY)) {
            log.info(body.getPortfolioId() + " is making a BUY Transaction");
            backFormatPortfolioTransaction.setType(edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransactionType.BUY);
        } else if (body.getTransactionType().equals(PortfolioTransactionType.SELL)) {
            log.info(body.getPortfolioId() + " is making a SELL Transaction");
            backFormatPortfolioTransaction.setType(edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransactionType.SELL);
        }

        if (body.getAssetPrice() != null && body.getAssetPrice() != 0) {
            log.info("The asset price was provided by the user and is henceforth being used for the following transaction");
            backFormatPortfolioTransaction.setPrice(body.getAssetPrice());
        } else {
            ApiResponse<List<AssetPriceData>> priceDataGetResponse = null;
            try {
                priceDataGetResponse = apiAccess.assetPriceDatasGetWithHttpInfo(body.getAssetId(), body.getDate(), body.getDate().plusDays(1));
            } catch (ApiException ex) {
                log.error("Could not retrieve assetpricedatas from be using assetPriceDataGet, errorstatuscode: " + ex.getCode() + " starttdate: " + body.getDate() + " end date: " + body.getDate().plusDays(1), ex);
                if (ex.getCode() == 404) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<AssetPriceData> assetPrices = priceDataGetResponse.getData();
            if (assetPrices.size() != 1) {
                log.error("The retrieved assetPrices arraylist is of unexpected size, size: " + assetPrices.size());
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("assetprices 0: " + assetPrices.get(0).toString());
            if (assetPrices.get(0).getAdjustedClosePrice() == null || assetPrices.get(0).getAdjustedClosePrice() == 0) {
                log.info("No closing price can be found in the assetPriceData  object, id of assetPriceData: " + assetPrices.get(0).getId());

                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

            } else {
                log.info("THe Adjustedclose price retrieved from the backend has been set as the price for the transaction");
                backFormatPortfolioTransaction.setPrice(assetPrices.get(0).getAdjustedClosePrice());
            }
        }

        try {
            ApiResponse<PortfolioTransaction> transactionAddResponse = apiAccess.portfolioTransactionPostWithHttpInfo(backFormatPortfolioTransaction);
        } catch (ApiException ex) {
            log.error("Could not post the portfolio transaction using portfolioTransactionPost, errorstatuscode: " + ex.getCode());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Succesfully executed the portfolioTransactionAdd method");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
