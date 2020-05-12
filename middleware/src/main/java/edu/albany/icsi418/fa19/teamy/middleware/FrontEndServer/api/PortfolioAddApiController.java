package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioType;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Portfolio;
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
public class PortfolioAddApiController implements PortfolioAddApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioAddApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioAddApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Portfolio> portfolioAddPost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser,@NotNull @ApiParam(value = "Name of the portfolio", required = true) @Valid @RequestParam(value = "portfolio_name", required = true) String portfolioName,@NotNull @ApiParam(value = "The type of the portfolio", required = true) @Valid @RequestParam(value = "portfolio_type", required = true) String portfolioType) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("PortfolioAddpost called in MW");
                //add a new portfolio
                //asserted user,portfolio name, portfolio type

                DefaultApi apiAccess = new DefaultApi();
                edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio backFormatPortfolio = new edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio();

                //need to check if a portfolio with the same name exists
            ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio>> T1= null;
            try {
                   T1 = apiAccess.portfoliosGetWithHttpInfo(assertedUser, portfolioName);
               } catch (ApiException ex){
                   if(ex.getCode()!=404){
                       log.error("dupes were found");
                       return new ResponseEntity<Portfolio>(HttpStatus.INTERNAL_SERVER_ERROR);
                   }
               }


                backFormatPortfolio.setName(portfolioName);
                backFormatPortfolio.setOwnerUserId(assertedUser);
                if (portfolioType.equals("NORMAL")) {
                    backFormatPortfolio.setType(PortfolioType.NORMAL);
                } else if (portfolioType.equals("WHATIF")) {
                    backFormatPortfolio.setType(PortfolioType.WHATIF);
                } else {
                    log.error("Invalid portfolio type provided as input portfoliotype :"+portfolioType);
                    return new ResponseEntity<Portfolio>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                backFormatPortfolio.setDeleted(false);



            ApiResponse<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio> T2= null;
            log.info("Portfolio to be posted : "+backFormatPortfolio.toString());
            try {
                T2 = apiAccess.portfolioPostWithHttpInfo(backFormatPortfolio);
            } catch(ApiException ex){
                    log.error("could not post the portfoliosusing portfolioPost , errorstatus: "+ex.getCode());
                    if(ex.getCode() == 500){
                   return new ResponseEntity<Portfolio>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
            }

                Portfolio frontFormatPortfolio = new Portfolio();
                log.info("frontformatportfolio ready to be added iwth details");


                edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio createdPortfolio = T2.getData();
                log.info("The newly created portfolio information recieved back");
                frontFormatPortfolio.setId(createdPortfolio.getId());
                frontFormatPortfolio.setDeleted(createdPortfolio.isDeleted());
                frontFormatPortfolio.setName(createdPortfolio.getName());
                frontFormatPortfolio.setOwnerUserId(createdPortfolio.getOwnerUserId());
                frontFormatPortfolio.setSerialNumber(createdPortfolio.getSerialNumber());

                ApiResponse<User> T3 = null;
                try {
                    T3 = apiAccess.userIdGetWithHttpInfo(assertedUser);
                } catch (ApiException ex){
                    log.error(" Could not retrieve the user with the id from the backend, errorstatuscode: " + ex.getCode());
                    if(ex.getCode() == 500) {
                        return new ResponseEntity<Portfolio>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }

                User owner = T3.getData();
                frontFormatPortfolio.setOwnerEmail(owner.getEmail());
                frontFormatPortfolio.setOwnerFirstName(owner.getFirstName());
                frontFormatPortfolio.setOwnerLastName(owner.getLastName());

                if (createdPortfolio.getType().getValue().equals("NORMAL")) {
                    frontFormatPortfolio.setType(edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioType.NORMAL);
                } else if (createdPortfolio.getType().getValue().equals("WHAIF")) {
                    frontFormatPortfolio.setType(edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioType.WHATIF);
                }

                log.info("Successfully executed the portfolioAdd method");
                return new ResponseEntity<Portfolio>(frontFormatPortfolio, HttpStatus.OK);

        }
        log.error("Reached a not implemented point");
        return new ResponseEntity<Portfolio>(HttpStatus.NOT_IMPLEMENTED);
    }

}
