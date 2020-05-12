package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Portfolio;
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
public class PortfolioDeleteApiController implements PortfolioDeleteApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioDeleteApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioDeleteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioDeletePost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser,@NotNull @ApiParam(value = "The id of the portfolio to to be deleted", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId) {
        String accept = request.getHeader("Accept");
        //assertedUser, PortfolioId- parameters
        //when asserted user matches portfolioIdOwner, then set portfolio deleted to true
        log.info("Portfoliodelete called in MW");
        DefaultApi apiAccess = new DefaultApi();



            //retrieve the portfolio from backend
        ApiResponse<Portfolio> T1 = null;
            try {log.info("Portfolio object is retrieved from the be");
                T1 = apiAccess.portfolioIdGetWithHttpInfo(portfolioId);
            } catch(ApiException ex){
                log.error("An error has occured using portfolioIdGet, errorsttuscode: "+ex.getCode());
                if(ex.getCode() == 500){
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            Portfolio backPortolio = T1.getData();
            if (backPortolio.isDeleted() == false) {
                log.info("The portfolio is not deleted yet and the owner is making the call to delete the portolio");
                backPortolio.setDeleted(true);

                ApiResponse T2 = null;
                try {
                    T2 = apiAccess.portfolioIdPutWithHttpInfo(backPortolio, backPortolio.getId());
                } catch (ApiException ex){
                    log.error("An occured while using the portfolioIdPut, errorstatuscode: "+ex.getCode());
                    if(ex.getCode() == 500){
                        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }

            } else {
                log.error("portfolio is already marked deleted or the user making the call is not thw owner, deletestatus: "+ backPortolio.isDeleted());
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            log.info("Successfully executed the portfolioDelete method");
            return new ResponseEntity<Void>(HttpStatus.OK);


    }

}
