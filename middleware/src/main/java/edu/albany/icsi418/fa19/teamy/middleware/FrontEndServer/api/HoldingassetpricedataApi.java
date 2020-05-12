/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.14).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetPriceOverTime;
import org.threeten.bp.OffsetDateTime;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-26T18:04:30.614Z[GMT]")
@Api(value = "holdingassetpricedata", description = "the holdingassetpricedata API")
public interface HoldingassetpricedataApi {

    @ApiOperation(value = "", nickname = "holdingassetpricedataGet", notes = "Get all the pricedata related to the holding assets of a particular portfolio", response = AssetPriceOverTime.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "list of the assets and their pricedatas over time", response = AssetPriceOverTime.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/holdingassetpricedata",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<AssetPriceOverTime>> holdingassetpricedataGet(@NotNull @ApiParam(value = "Id of the portfolio", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId, @ApiParam(value = "to get the assetprice data starting from a particular date") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate, @ApiParam(value = "to get the assetprice data till a particular date") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate);

}