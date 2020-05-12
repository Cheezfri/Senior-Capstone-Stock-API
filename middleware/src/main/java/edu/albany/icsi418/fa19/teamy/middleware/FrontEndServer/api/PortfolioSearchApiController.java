package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;


import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Portfolio;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioType;
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
public class PortfolioSearchApiController implements PortfolioSearchApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioSearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioSearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Portfolio>> portfolioSearchGet(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @ApiParam(value = "Name of the portfolio") @Valid @RequestParam(value = "portfolio_name", required = false) String portfolioName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("PortfolioSearchGet called in MW");

            //returns a specific portfolio or a list of portfolios
            List<Portfolio> returnableFrontPortfolios = new ArrayList<Portfolio>();

            DefaultApi apiAccess = new DefaultApi();

            ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio>> T1 = null;
            try {
                log.info("Requesting the backed for the portfolios matching the assertedUser and portfolioName");
                T1 = apiAccess.portfoliosGetWithHttpInfo(assertedUser, portfolioName);
            } catch (ApiException ex) {
                log.error("Could not retrieve tthe porfolios from be using portfoliosGet !!,errorstatus: " + ex.getCode(), ex);
                if (ex.getCode() == 404) {
                    return new ResponseEntity<List<Portfolio>>(returnableFrontPortfolios, HttpStatus.OK);
                }
            }


            List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio> tempPortfolios = T1.getData();

            log.info("Retrievendg the asserted user details for name and details");
            ApiResponse<User> T2 = null;
            try {
                log.info("Requesting the backend for the user datta using userIdGet");
                T2 = apiAccess.userIdGetWithHttpInfo(assertedUser);
            } catch (ApiException ex) {
                log.error("Could not retriece users from the backend using userIdGet, errorstatuscode: " + T2.getStatusCode(), ex);
                return new ResponseEntity<List<Portfolio>>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (NullPointerException npe) {
                log.info("Null pointer exception was found");
            }


            log.info("Iterating through the porfolios to check for deleted portfolios");
            for (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio item : tempPortfolios) {
                Portfolio tempFrontPortfolio = new Portfolio();
                if (item.isDeleted()) {
                    log.info("The portfolio has been deleted ,portfolio name: " + item.getName());
                    continue;
                }
                tempFrontPortfolio.setId(item.getId());
                tempFrontPortfolio.setName(item.getName());
                tempFrontPortfolio.setDeleted(item.isDeleted());
                tempFrontPortfolio.setSerialNumber(item.getSerialNumber());

                tempFrontPortfolio.setOwnerUserId(T2.getData().getId());
                tempFrontPortfolio.setOwnerEmail(T2.getData().getEmail());
                tempFrontPortfolio.setOwnerFirstName(T2.getData().getFirstName());
                tempFrontPortfolio.setOwnerLastName(T2.getData().getLastName());

                if (item.getType().getValue().equals("NORMAL")) {
                    tempFrontPortfolio.setType(PortfolioType.NORMAL);
                } else if (item.getType().getValue().equals("WHATIF")) {
                    tempFrontPortfolio.setType(PortfolioType.WHATIF);
                }
                returnableFrontPortfolios.add(tempFrontPortfolio);
            }

            log.info("Successfully executed the portfolioSearch method");
            return new ResponseEntity<List<Portfolio>>(returnableFrontPortfolios, HttpStatus.OK);

        }

        log.error("Reached a not implemented point");
        return new ResponseEntity<List<Portfolio>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
