package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioRights;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioShare;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
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
public class PortfolioShareAddApiController implements PortfolioShareAddApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioShareAddApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioShareAddApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioShareAddPost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "The id of the portfolio being shared", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId, @NotNull @ApiParam(value = "The email with which the portfolio is being shared with", required = true) @Valid @RequestParam(value = "SharedWithEmail", required = true) String sharedWithEmail) {
        String accept = request.getHeader("Accept");
        log.info("PortfolioShareAddpost called in MW");
        //parameters - assertedUser,portfolioId,sharedWithEmail

        //check if assertedUser is the owner of the portfolio

        DefaultApi apiAccess = new DefaultApi();
        //Retrieve the portfolio

        ApiResponse<Portfolio> portfolioRetrieveResponse = null;
        try {
            portfolioRetrieveResponse = apiAccess.portfolioIdGetWithHttpInfo(portfolioId); //404,500
        } catch (ApiException ex) {
            log.error("Could not retrieve the portfolio using portfoliId get, errorstatuscode: " + ex.getCode());
            if (ex.getCode() == 404) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        Portfolio toCheckPortfolio = portfolioRetrieveResponse.getData();
        if (assertedUser.equals(toCheckPortfolio.getOwnerUserId())) {
            //need the portfolioID,shredwithUserId,portfolioRight(Default Read for now)
            if (sharedWithEmail == null || sharedWithEmail.isEmpty()) {
                log.error("Sharedwith email parameter is invalid");
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


            //create a new backendmodel user list to store o/p of usersGet
            ApiResponse<List<User>> userList = null;
            try {
                userList = apiAccess.usersGetWithHttpInfo(sharedWithEmail); //404,500
            } catch (ApiException ex) {
                log.error("Could not retrieve users with matching email using usersGet, errorstatuscode: " + ex.getCode());
                if (ex.getCode() == 404) {
                    return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            List<User> tempusers = userList.getData();
            if (userList.getData() == null || userList.getData().isEmpty()) {
                log.error("Returned user list is empty");
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            User sharedWithUser = tempusers.get(0);

            ApiResponse<List<PortfolioShare>> doesShareAlreadyExist = null;
            try {
                doesShareAlreadyExist = apiAccess.portfolioSharesGetWithHttpInfo(portfolioId, sharedWithUser.getId()); //404,500
            } catch (ApiException ex) {
                if (ex.getCode() == 404) {
                    log.info("errorstatuscode: "+ex.getCode()+" implies such portfolio share does not already exist");
                    PortfolioShare backFormatPortfolioShare = new PortfolioShare();
                    backFormatPortfolioShare.setPortfolioId(portfolioId);
                    backFormatPortfolioShare.setSharedWithUserId(sharedWithUser.getId());
                    backFormatPortfolioShare.setPortfolioRights(PortfolioRights.READ);

                    try {
                        ApiResponse postingShareResponse = apiAccess.portfolioSharePostWithHttpInfo(backFormatPortfolioShare); //409,500
                    } catch (ApiException ex2) {
                        log.error("An error has occured when using PortfolioSharePost , errorstatuscode: " + ex2.getCode());
                        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    log.info("Successfully executed the portfolioShareAdd method");
                    return new ResponseEntity<Void>(HttpStatus.OK);
                } else
                {
                    log.error("An error has occured trying to retrieve portfolioshares using portfoliosharege, errorstatuscode: "+ex.getCode());
                }
            }
            log.info("Not found error was not thrown , i.e a similar share already exists");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {

            log.error("The user making the request is not the owner of the porttfolio being shared, asserteduser: " + assertedUser + "owner of portfolio: " + toCheckPortfolio.getOwnerUserId());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
