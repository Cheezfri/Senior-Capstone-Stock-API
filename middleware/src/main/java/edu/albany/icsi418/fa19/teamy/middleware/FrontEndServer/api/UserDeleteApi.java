/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.14).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

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
@Api(value = "user_delete", description = "the user_delete API")
public interface UserDeleteApi {

    @ApiOperation(value = "", nickname = "userDeletePost", notes = "The user deleting their own account or an admin deleting an account", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "sucessfuly deleted the user"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/user_delete",
        method = RequestMethod.POST)
    ResponseEntity<Void> userDeletePost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "the id to be deleted", required = true) @Valid @RequestParam(value = "id", required = true) Long id);

}
