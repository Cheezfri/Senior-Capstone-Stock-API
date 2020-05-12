package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
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
public class PortfolioShareDeleteApiController implements PortfolioShareDeleteApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioShareDeleteApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioShareDeleteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    public ResponseEntity<Void> portfolioShareDeletePost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "The id of the portfolio involved in sharing.", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId, @NotNull @ApiParam(value = "The email with which the portfolio is being shared with", required = true) @Valid @RequestParam(value = "SharedWithEmail", required = true) String sharedWithEmail) {
        String accept = request.getHeader("Accept");
        log.info("PortfolioDeletePost called in MW");
        //parameters all required - assertedUser,portfolioId,sharedwithemail

        //retrieve protfolio and check if owner is asserted user
        //if yes then retriece all the userids with email and then using that userid

            DefaultApi apiAccess = new DefaultApi();
        ApiResponse<Portfolio> toCheckPortfolio = null;
            try {
                toCheckPortfolio = apiAccess.portfolioIdGetWithHttpInfo(portfolioId); //404,500
            } catch (ApiException ex){
                log.error("An error has occured trying to retrieve the portfolio using portfolioId get, errorstatuscode: "+ex.getCode());
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (assertedUser.equals(toCheckPortfolio.getData().getOwnerUserId())) {

                ApiResponse<List<User>> userList = null;
                try {
                    userList = apiAccess.usersGetWithHttpInfo(sharedWithEmail); //404,500
                } catch (ApiException ex){
                    log.error("An error has occured trying to retrieve userlist using userset, errorstatuscode: "+ex.getCode());
                }

                if(userList.getData().isEmpty()){
                    log.error("No user with such email is returned by the backend");
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                ApiResponse<List<PortfolioShare>> portfolioShareObject = null;
                try {
                  portfolioShareObject  = apiAccess.portfolioSharesGetWithHttpInfo(portfolioId, userList.getData().get(0).getId()); //404,500
                } catch (ApiException ex){
                    log.error("An error has occured trying to retrieve the portfolioshares using portfoliosharesget , errorstatuscode: "+ex.getCode());
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                    if(portfolioShareObject.getData().isEmpty()){
                        log.error("Portfolioshares returned by the portfolioShares is empty");
                        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                    try {
                        ApiResponse deletingShareResponse = apiAccess.portfolioShareIdDeleteWithHttpInfo(portfolioShareObject.getData().get(0).getId());
                    } catch (ApiException ex){
                        log.error("An error has occured trying to delete teh portfolioshare object using portfolioshareIdelete , errorstatuscode: "+ex.getCode());
                        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                log.info("Successfully executed the portfolioShareDelete method");
                return new ResponseEntity<Void>(HttpStatus.OK);

            }else{
                log.error("The user making the requestt is not the owner of the portfolio");
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
    }

}
