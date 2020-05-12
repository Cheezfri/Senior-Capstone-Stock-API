package edu.albany.icsi418.fa19.teamy.backend.api;

import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShare;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShareApiModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Api(value = "portfolioShare", description = "the portfolioShare API")
public interface PortfolioShareApi {

    @ApiOperation(value = "", nickname = "portfolioShareIdDelete", notes = "Delete a portfolioShare", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "PortfolioShare has been deleted"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioShare/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> portfolioShareIdDelete(@ApiParam(value = "The unique identifier of a portfolioShare",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioShareIdGet", notes = "Retrieve the information about a specific portfolioShare", response = PortfolioShare.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "PortfolioShare record found", response = PortfolioShare.class),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioShare/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<PortfolioShareApiModel> portfolioShareIdGet(@ApiParam(value = "The unique identifier of a portfolioShare",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioShareIdPut", notes = "Update a portfolioShare", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Succesfully updated the portfolioShare"),
        @ApiResponse(code = 304, message = "Record not modified"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioShare/{id}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> portfolioShareIdPut(@ApiParam(value = "", required = true) @Valid @RequestBody PortfolioShareApiModel body, @ApiParam(value = "The unique identifier of a portfolioShare", required = true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "portfolioSharePost", notes = "creates a new portfolioShare object in the database", response = PortfolioShare.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successfully created a new PortfolioShare object", response = PortfolioShare.class),
        @ApiResponse(code = 409, message = "Record already exists in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/portfolioShare",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<PortfolioShareApiModel> portfolioSharePost(@ApiParam(value = "", required = true) @Valid @RequestBody PortfolioShareApiModel body);

}
