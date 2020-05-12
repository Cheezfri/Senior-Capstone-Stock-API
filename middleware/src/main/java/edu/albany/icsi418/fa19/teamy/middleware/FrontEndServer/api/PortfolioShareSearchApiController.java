package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioShare;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioRights;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.SharedPortfolio;
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
public class PortfolioShareSearchApiController implements PortfolioShareSearchApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioShareSearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioShareSearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<SharedPortfolio>> portfolioShareSearchGet(@ApiParam(value = "The userid of the user viewing portfolio shared with him/her") @Valid @RequestParam(value = "userId", required = false) Long userId, @ApiParam(value = "The portfolioId of the portfolio that is being shared with another user/users") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("PortfolioShareSearchGet called in MW");
            // two parameters userId, PortfolioId

                if ((userId == null || userId == 0) && (portfolioId == null || portfolioId == 0)) {
                    log.error("The user Id or the portfolio id given at input is invalid");
                    return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.BAD_REQUEST);
                }

                DefaultApi apiAccess = new DefaultApi();
                List<SharedPortfolio> returnableSharedPortolios = new ArrayList<SharedPortfolio>();

                if ((userId != null && userId != 0) && (portfolioId == null || portfolioId == 0)) {
                    //it is when the user is viewing the portfolios shared with him
                    log.info("The method is being used to view the porttfolio shared with the user, userId: "+userId);
                    ApiResponse<List<PortfolioShare>> portfolioSharesList = null;
                    try {
                        portfolioSharesList = apiAccess.portfolioSharesGetWithHttpInfo(0L, userId);
                    } catch (ApiException ex){
                        log.error("Could not rettrieve portfolioshares using portfolioshareGet, errorstatuscode: "+ex.getCode());
                        if(ex.getCode() == 404){
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.NOT_FOUND);
                        } else {
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }

                    }

                    ApiResponse<User> shareviewerRetrieveResponse = null;
                    try {
                        shareviewerRetrieveResponse = apiAccess.userIdGetWithHttpInfo(userId);
                    } catch (ApiException ex){
                        log.error("An error has occured when using userIdGet, errorstatuscode: "+ex.getCode());
                        return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    User shareviewer = shareviewerRetrieveResponse.getData();
                    for (PortfolioShare item : portfolioSharesList.getData()) {
                        log.info("The id of the portfolioshareobject: "+item.getId());
                        log.info("Iterating through the portfolioshare objects returned");
                        SharedPortfolio portfolioShareObjecToAddToList = new SharedPortfolio(); //to add at end of each iteration

                        //retrieve portfolio information in each iteration
                        ApiResponse<Portfolio> portfolioShared = null;
                       try {
                           portfolioShared = apiAccess.portfolioIdGetWithHttpInfo(item.getPortfolioId());
                       } catch (ApiException ex){
                           log.error("An error has occured when using portfolioIdGet , errorstatuscode: "+ex.getCode());
                           return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                       }

                        if (portfolioShared.getData().isDeleted() == true) {
                            log.info("The portfolio found in a portfolioShare has been marked deleted, portfolioId: "+item.getPortfolioId());
                            continue;
                        }

                        portfolioShareObjecToAddToList.setId(portfolioShared.getData().getId()); // it is the id of the portfolio
                        portfolioShareObjecToAddToList.setDeleted(portfolioShared.getData().isDeleted());
                        portfolioShareObjecToAddToList.setName(portfolioShared.getData().getName());
                        //TODO instead of owneruserid, get the name
                        ApiResponse<User> ownerInfo = null;
                        try{
                            ownerInfo = apiAccess.userIdGetWithHttpInfo(portfolioShared.getData().getOwnerUserId());
                        } catch (ApiException ex){
                            log.info("An error has occured trying to retrieve , errorstatuscode: "+ex.getCode());
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                        portfolioShareObjecToAddToList.setOwnerEmail(ownerInfo.getData().getEmail());
                        portfolioShareObjecToAddToList.setOwnerUserId(portfolioShared.getData().getOwnerUserId());
                        portfolioShareObjecToAddToList.setPortfolioRights(PortfolioRights.READ); //default set
                        portfolioShareObjecToAddToList.setSharedWithUserEmail(shareviewer.getEmail());
                        //user email id will be same in all iterations in each case
                        returnableSharedPortolios.add(portfolioShareObjecToAddToList);
                    }
                    log.info("The portfolioShareSearch method has been successfully executed");
                    return new ResponseEntity<List<SharedPortfolio>>(returnableSharedPortolios, HttpStatus.OK);
                } else if ((userId == null || userId == 0) && (portfolioId != null && portfolioId != 0)) {
                    log.info("The method is being used to check what are the shares done from a porttfolio");
                    //owner is seeing with whom the portfolio is shared with

                    ApiResponse<List<PortfolioShare>> portfolioSharesRetrieveResponse = null;
                    try {
                        portfolioSharesRetrieveResponse = apiAccess.portfolioSharesGetWithHttpInfo(portfolioId, 0L);
                    } catch (ApiException ex){
                        log.error("Could not rettrieve portfolioshares using portfolioshareGet, errorstatuscode: "+ex.getCode());
                        if(ex.getCode() == 404){
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.NOT_FOUND);
                        } else {
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }


                    for (PortfolioShare item : portfolioSharesRetrieveResponse.getData()) {
                        log.info("The id of the portfolioshareobject: "+item.getId());
                        SharedPortfolio portfolioShareObjecToBeAddedToList = new SharedPortfolio(); //to add at end of each iteration

                        //retrieve portfolio information in each iteration
                        ApiResponse<Portfolio> retrievePortfolioResponse = null;
                       try {
                           retrievePortfolioResponse = apiAccess.portfolioIdGetWithHttpInfo(item.getPortfolioId());
                       } catch (ApiException ex){
                           log.error("An error has occured when using portfolioIdGet , errorstatuscode: "+ex.getCode());
                           return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                       }

                        if (retrievePortfolioResponse.getData().isDeleted() == true) {
                            log.info("The portfolio found in a portfolioShare has been marked deleted, portfolioId: "+item.getPortfolioId());
                            continue;
                        }

                        portfolioShareObjecToBeAddedToList.setId(retrievePortfolioResponse.getData().getId()); // it is the id of the portfolio
                        portfolioShareObjecToBeAddedToList.setDeleted(retrievePortfolioResponse.getData().isDeleted());
                        portfolioShareObjecToBeAddedToList.setName(retrievePortfolioResponse.getData().getName());
                        //TODO instead of owneruserid, get the name
                        ApiResponse<User> ownerInfo = null;
                        try{
                            ownerInfo = apiAccess.userIdGetWithHttpInfo(retrievePortfolioResponse.getData().getOwnerUserId());
                        } catch (ApiException ex){
                            log.info("An error has occured trying to retrieve , errorstatuscode: "+ex.getCode());
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                        portfolioShareObjecToBeAddedToList.setOwnerEmail(ownerInfo.getData().getEmail());
                        portfolioShareObjecToBeAddedToList.setOwnerUserId(retrievePortfolioResponse.getData().getOwnerUserId());
                        portfolioShareObjecToBeAddedToList.setPortfolioRights(PortfolioRights.READ); //default set

                        ApiResponse<User> sharedWithUserObject = null;
                        try{
                            sharedWithUserObject = apiAccess.userIdGetWithHttpInfo(item.getSharedWithUserId());
                        } catch (ApiException ex){
                            log.error("An error has occured when using userIdGet, errorstatuscode: "+ex.getCode());
                            return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }

                        portfolioShareObjecToBeAddedToList.setSharedWithUserEmail(sharedWithUserObject.getData().getEmail());
                        //user email id will be same in all iterations in each case
                        returnableSharedPortolios.add(portfolioShareObjecToBeAddedToList);
                    }
                    log.info("The portfolioShareSearch method has been successfully executed");
                    return new ResponseEntity<List<SharedPortfolio>>(returnableSharedPortolios, HttpStatus.OK);
                }
        }
        return new ResponseEntity<List<SharedPortfolio>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
