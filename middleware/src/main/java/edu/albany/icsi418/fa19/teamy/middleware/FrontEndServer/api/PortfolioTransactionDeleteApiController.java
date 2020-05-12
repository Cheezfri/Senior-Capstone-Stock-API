package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTransaction;
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
public class PortfolioTransactionDeleteApiController implements PortfolioTransactionDeleteApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioTransactionDeleteApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioTransactionDeleteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioTransactionDeletePost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "Unique identifier of PortfolioTransaction that needs to be deleted", required = true) @Valid @RequestParam(value = "portfolioTransactionId", required = true) Long portfolioTransactionId) {
        String accept = request.getHeader("Accept");
        log.info("PortfolioTransactionDeletePost called in the MW");

        //parameters, asserted user, portfoliotransactionID,
        //if the owner asserted user is equal to the ownerid of the portfolio involved in transaction
        //then the transacted is deleted

        //both values are not null and not empty
        if (assertedUser == null || assertedUser == 0 || portfolioTransactionId == null || portfolioTransactionId == 0) {
            log.error("Invalid inputt data has been provided");
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        DefaultApi apiAccess = new DefaultApi();
        //retrieve the portfoliotransaction item - err 404,500
        ApiResponse<PortfolioTransaction> transactionGetResponse = null;
        try {
            transactionGetResponse = apiAccess.portfolioTransactionIdGetWithHttpInfo(portfolioTransactionId);
        } catch (ApiException ex) {
            log.error("Could not retrieve the ortfoliotransaction using portfolioTransactionIdGet, errorstatuscode: " + ex.getCode(), ex);
            if (ex.getCode() == 404) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PortfolioTransaction toBeDeletedTransaction = transactionGetResponse.getData();
        //retrieve the portfolio of the portfolio involved in transaction - err 404,500
        ApiResponse<Portfolio> portfolioOfTransaction = null;
        try {
            portfolioOfTransaction = apiAccess.portfolioIdGetWithHttpInfo(transactionGetResponse.getData().getPortfolioId());
        } catch (ApiException ex) {
            log.error("Could not retrieve the portfolio from the be using portfolioIdGet, errorstatuscode: " + ex.getCode(), ex);
            if (ex.getCode() == 404) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (assertedUser.equals(portfolioOfTransaction.getData().getOwnerUserId()) ) {
            log.info("Succesfully checked if the owner of tthe portfolio is the one making the delete call");
            try {
                ApiResponse deleteTransactionResponse = apiAccess.portfolioTransactionIdDeleteWithHttpInfo(portfolioTransactionId); //err - 404,500
            } catch (ApiException ex) {
                log.error("Could not delete the transaction using portfolioransactionDelete, errorstatuscoe: " + ex.getCode(), ex);
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


            log.info("successfully execued the portfoliotransactiondeletemethod");
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            log.info("owner of retrieved portfolio: "+portfolioOfTransaction.getData().getOwnerUserId()+" asserteduser: "+assertedUser);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
