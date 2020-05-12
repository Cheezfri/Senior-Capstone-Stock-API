package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransactionType;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTransaction;
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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class PortfolioTransactionSearchApiController implements PortfolioTransactionSearchApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioTransactionSearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioTransactionSearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<PortfolioTransaction>> portfolioTransactionSearchGet(@NotNull @ApiParam(value = "Portfolio for which the transaction was made", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("Portfoliotransacttionsearch method called in the MW");
            //parameters - portfolioId
            if (portfolioId == null || portfolioId == 0) {
                log.error("PortfolioId is empty in the input");
                return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.BAD_REQUEST);
            }

            DefaultApi apiAccess = new DefaultApi();

            //this will be the return list
            List<PortfolioTransaction> returnableTransactionList = new ArrayList<PortfolioTransaction>();

            ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransaction>> portfolioTransactionsList = null;
            try {
                portfolioTransactionsList = apiAccess.portfolioTransactionsGetWithHttpInfo(portfolioId);
            } catch (ApiException ex) {
                log.error("Cpuld not retrieve portfolio transactios from tthe BE using portfolioTransacttionsGet , errorstatuscode: " + ex.getCode(), ex);
                if (ex.getCode() == 404) {
                    return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            log.info("Retrieved the porttfolio transactions from the backend, converting them to frontend format now");

            ApiResponse<Portfolio> portfolioInfo = null;
            try {
                portfolioInfo = apiAccess.portfolioIdGetWithHttpInfo(portfolioId);
            } catch (ApiException ex){
                log.error("Could not retrieve the portfolio using portfolioIdGet from the BE, errorstatuscode: "+ex.getCode(),ex);
                return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            ApiResponse<User> userInfo = null;
            try{
                userInfo = apiAccess.userIdGetWithHttpInfo(portfolioInfo.getData().getOwnerUserId());
            } catch (ApiException ex){
                log.error("Could not retrieve the User using UserIdGet from the BE, errorstatuscode: "+ex.getCode(),ex);
                return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            for (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransaction item : portfolioTransactionsList.getData()) {
                PortfolioTransaction returnListObject = new PortfolioTransaction();
                log.info("Transaction " + item.getId() + " is being iterated");
                returnListObject.setId(item.getId());
                returnListObject.setPortfolioId(item.getPortfolioId());
                try {
                    returnListObject.setPrice(Utility.currencyConvert(item.getPrice(), "USD", userInfo.getData().getLocalCurrency(), item.getDateTime()));
                }  catch (ApiException ex){
                log.error("Could not retrieve the converted price using currency convert from the utility, errorstatuscode: "+ex.getCode(),ex);
                return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
                returnListObject.setQuantity(item.getQuantity());
                returnListObject.setDateTime(item.getDateTime());
                returnListObject.setAssetId(item.getAssetId());



                returnListObject.setPortfolioname(portfolioInfo.getData().getName());

                ApiResponse<Asset> assetInfo = null;
                try {
                    assetInfo = apiAccess.assetIdGetWithHttpInfo(item.getAssetId());
                } catch (ApiException ex) {
                    log.error("Could not retrieve the assetdata using assetIdGet, errorstatuscode: " + ex.getCode(), ex);
                    return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                returnListObject.setAssetName(assetInfo.getData().getName());
                if (item.getType().equals(PortfolioTransactionType.BUY)) {
                    log.info("transaction type: " + item.getType().getValue());
                    returnListObject.setType(edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTransactionType.BUY);
                } else if (item.getType().equals(PortfolioTransactionType.SELL)) {
                    log.info("transaction type: " + item.getType().getValue());
                    returnListObject.setType(edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTransactionType.SELL);
                }
                log.info("Transaction " + item.getId() + " is succesfully added to the list of transactions the portfolio is involved in");
                returnableTransactionList.add(returnListObject);
            }

            log.info("Succesfully executed the Portfoliotransactionsearch method");
            return new ResponseEntity<List<PortfolioTransaction>>(returnableTransactionList, HttpStatus.OK);
        }

        return new ResponseEntity<List<PortfolioTransaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
