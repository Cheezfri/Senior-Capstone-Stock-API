package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;

import edu.albany.icsi418.fa19.teamy.backend.models.user.AccessLevel;
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
@Api(value = "accessLevel", description = "the accessLevel API")
public interface AccessLevelApi {

    @ApiOperation(value = "", nickname = "accessLevelIdGet", notes = "Retrieve the information of a specific access level", response = AccessLevel.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Access Level record found", response = AccessLevel.class),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/accessLevel/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<AccessLevel> accessLevelIdGet(@ApiParam(value = "The unique id of the access level",required=true) @PathVariable("id") Long id);

}
