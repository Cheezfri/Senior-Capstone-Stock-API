package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;

import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioApiModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Api(value = "portfolios", description = "the portfolios API")
public interface PortfoliosApi {

    @ApiOperation(value = "", nickname = "portfoliosGet", notes = "Search for all, or a subset of all portfolios", response = Portfolio.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Found one or more portfolios", response = Portfolio.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolios",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<PortfolioApiModel>> portfoliosGet(@ApiParam(value = "The unique id of the user") @Valid @RequestParam(value = "userId", required = false) Long userId, @ApiParam(value = "Name of the portfolio") @Valid @RequestParam(value = "portfolioName", required = false) String portfolioName);

}
