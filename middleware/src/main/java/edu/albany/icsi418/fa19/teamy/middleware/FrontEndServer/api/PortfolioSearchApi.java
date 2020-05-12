/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.14).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Portfolio;
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
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Api(value = "portfolio_search", description = "the portfolio_search API")
public interface PortfolioSearchApi {

    @ApiOperation(value = "", nickname = "portfolioSearchGet", notes = "Search for all, or a subset of all portfolios", response = Portfolio.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "List of portfolios", response = Portfolio.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolio_search",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Portfolio>> portfolioSearchGet(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @ApiParam(value = "Name of the portfolio") @Valid @RequestParam(value = "portfolio_name", required = false) String portfolioName);

}
