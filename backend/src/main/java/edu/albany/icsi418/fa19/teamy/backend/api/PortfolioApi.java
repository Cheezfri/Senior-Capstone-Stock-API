package edu.albany.icsi418.fa19.teamy.backend.api;

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
@Api(value = "portfolio", description = "the portfolio API")
public interface PortfolioApi {

    @ApiOperation(value = "", nickname = "portfolioIdDelete", notes = "Delete a portfolio", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Portfolio has been deleted"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolio/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> portfolioIdDelete(@ApiParam(value = "The unique identifier of a portfolio",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioIdGet", notes = "Retrieve the information about a specific portfolio", response = Portfolio.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Portfolio record found", response = Portfolio.class),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolio/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<PortfolioApiModel> portfolioIdGet(@ApiParam(value = "The unique identifier of a portfolio",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioIdPut", notes = "Update a portfolio", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Succesfully updated the portfolio"),
        @ApiResponse(code = 304, message = "Record not modified"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolio/{id}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> portfolioIdPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioApiModel body, @ApiParam(value = "The unique identifier of a portfolio",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioPost", notes = "creates a new portfolio for the user", response = Portfolio.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successfully created a new portfolio", response = Portfolio.class),
        @ApiResponse(code = 409, message = "Record already exists in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolio",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<PortfolioApiModel> portfolioPost(@ApiParam(value = "", required = true) @Valid @RequestBody PortfolioApiModel body);

}
