package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.*;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.HoldingAsset;
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
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class HoldingAssetSearchApiController implements HoldingAssetSearchApi {

    private static final Logger log = LoggerFactory.getLogger(HoldingAssetSearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public HoldingAssetSearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<HoldingAsset>> holdingAssetSearchGet(@NotNull @ApiParam(value = "Id of the portfolio", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {//need to return asset information and portfolio value
                DefaultApi apiAccess = new DefaultApi();

                //portfolioId is required
                if (portfolioId == null || portfolioId == 0) {
                    return new ResponseEntity<List<HoldingAsset>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                //retrieve the portfolio from backend
                ApiResponse<Portfolio> userPortfolio = apiAccess.portfolioIdGetWithHttpInfo(portfolioId);
                if (userPortfolio.getStatusCode() == 404 || userPortfolio.getStatusCode() == 304 || userPortfolio.getStatusCode() == 500){
                    throw new ApiException(userPortfolio.getStatusCode(),"An error has occured");
                }
                Portfolio backFormatPortfolio = userPortfolio.getData();
                //the portfolio should not have a deleted stattus
                if (backFormatPortfolio.isDeleted() == true) {
                    return new ResponseEntity<List<HoldingAsset>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                log.info("ToReturnlist is returned in holding assetsearchapi controller");
                List<HoldingAsset> toReturnList = Utility.getHoldingAssets(portfolioId);

                if(toReturnList.isEmpty()){
                    log.info("No Holding assets were found for the portfolio or an error occured trying to get them");
                    return new ResponseEntity<List<HoldingAsset>>(HttpStatus.NOT_FOUND);
                }

                return new ResponseEntity<List<HoldingAsset>>(toReturnList, HttpStatus.OK);
            } catch (ApiException ae) {
                if (ae.getCode() == 404) {
                    return new ResponseEntity<List<HoldingAsset>>(HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<List<HoldingAsset>>(HttpStatus.INTERNAL_SERVER_ERROR);

                }
            }
        }

        return new ResponseEntity<List<HoldingAsset>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
