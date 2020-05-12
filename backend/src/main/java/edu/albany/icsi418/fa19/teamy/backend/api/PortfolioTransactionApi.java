package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;

import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransaction;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransactionApiModel;
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
@Api(value = "portfolioTransaction", description = "the portfolioTransaction API")
public interface PortfolioTransactionApi {

    @ApiOperation(value = "", nickname = "portfolioTransactionIdDelete", notes = "deletes a portfolioTransaction", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successfully deleted the portfolioTransaction"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioTransaction/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> portfolioTransactionIdDelete(@ApiParam(value = "The unique id of the portfolioTransaction",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioTransactionIdGet", notes = "Retrieve the information of a specific PortfolioTransaction", response = PortfolioTransaction.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "PortfolioTransaction record found", response = PortfolioTransaction.class),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioTransaction/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<PortfolioTransactionApiModel> portfolioTransactionIdGet(@ApiParam(value = "The unique id of the PortfolioTransaction",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioTransactionIdPut", notes = "Update the transaction", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Succesfully updated the transaction"),
        @ApiResponse(code = 304, message = "Record not modified"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioTransaction/{id}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> portfolioTransactionIdPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioTransactionApiModel body,@ApiParam(value = "The unique id of the transaction that is updated",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioTransactionPost", notes = "create a new portfolioTransaction in the database", response = PortfolioTransaction.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successfully created a new Portfolio Transaction", response = PortfolioTransaction.class),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioTransaction",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<PortfolioTransactionApiModel> portfolioTransactionPost(@ApiParam(value = "", required = true) @Valid @RequestBody PortfolioTransactionApiModel body);

}
