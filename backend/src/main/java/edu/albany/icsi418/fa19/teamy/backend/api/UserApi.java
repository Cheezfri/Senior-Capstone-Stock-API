package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;

import edu.albany.icsi418.fa19.teamy.backend.models.user.User;
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
@Api(value = "user", description = "the user API")
public interface UserApi {

    @ApiOperation(value = "", nickname = "userIdDelete", notes = "deletes an user", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successfully deleted the user"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/user/{id}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> userIdDelete(@ApiParam(value = "The unique id of the user",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "userIdGet", notes = "Retrieve the information of a specific user", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User record found", response = User.class),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/user/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<User> userIdGet(@ApiParam(value = "The unique id of the user",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "userIdPut", notes = "Update the User Record", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Succesfully updated the user"),
        @ApiResponse(code = 304, message = "Record not modified"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/user/{id}",
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> userIdPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User body,@ApiParam(value = "The unique id of the user",required=true) @PathVariable("id") Long id);


    @ApiOperation(value = "", nickname = "userPost", notes = "creates a new user in the database", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "successfully created a new user", response = User.class),
        @ApiResponse(code = 409, message = "Record already exists in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/user",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<User> userPost(@ApiParam(value = "", required = true) @Valid @RequestBody User body);

}
