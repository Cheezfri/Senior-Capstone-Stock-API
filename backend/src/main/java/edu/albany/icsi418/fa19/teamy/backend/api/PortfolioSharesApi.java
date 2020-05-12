package edu.albany.icsi418.fa19.teamy.backend.api;

import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShare;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShareApiModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Api(value = "portfolioShares", description = "the portfolioShares API")
public interface PortfolioSharesApi {

    @ApiOperation(value = "", nickname = "portfolioSharesGet", notes = "Search for a portfolioShare / get all portfolioShares ", response = PortfolioShare.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Found one or more portfolioShares", response = PortfolioShare.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioShares",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<PortfolioShareApiModel>> portfolioSharesGet(@ApiParam(value = "Id of the portfolio to which the portfolioShare belongs") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId, @ApiParam(value = "Id of the user with whom the portfolio is shared") @Valid @RequestParam(value = "sharedUserId", required = false) Long sharedUserId);

}
