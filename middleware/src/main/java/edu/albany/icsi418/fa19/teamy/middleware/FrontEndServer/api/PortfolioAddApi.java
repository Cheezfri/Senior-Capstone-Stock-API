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
@Api(value = "portfolio_add", description = "the portfolio_add API")
public interface PortfolioAddApi {

    @ApiOperation(value = "", nickname = "portfolioAddPost", notes = "Add a portfolio to the user profile", response = Portfolio.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Portfolio has been created", response = Portfolio.class),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolio_add",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Portfolio> portfolioAddPost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "Name of the portfolio", required = true) @Valid @RequestParam(value = "portfolio_name", required = true) String portfolioName, @NotNull @ApiParam(value = "The type of the portfolio", required = true) @Valid @RequestParam(value = "portfolio_type", required = true) String portfolioType);

}
