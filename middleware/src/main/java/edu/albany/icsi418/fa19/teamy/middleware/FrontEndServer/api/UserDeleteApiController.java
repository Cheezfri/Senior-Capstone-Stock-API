package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class UserDeleteApiController implements UserDeleteApi {

    private static final Logger log = LoggerFactory.getLogger(UserDeleteApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserDeleteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> userDeletePost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "the id to be deleted", required = true) @Valid @RequestParam(value = "id", required = true) Long id) {
        String accept = request.getHeader("Accept");
        //deleting a user
        //asserted user , id - parameters
        //deleted in two cases
        //when the asserted_user = id i.e users deleting their own account
        //when the asserted_user is an admin
        //admins cannot delete their own accounts

        //get the user object form backend and check if it is admin
        DefaultApi apiAccess = new DefaultApi();

        ApiResponse<User> T1 = null;
        try {
            T1 = apiAccess.userIdGetWithHttpInfo(assertedUser);
        } catch (ApiException ex) {
            log.error("Failed to retrieve user with userIdGet,errorstatuscode: " + ex.getCode(), ex);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (T1.getStatusCode() == 404) {
            log.error("No user with id " + assertedUser + " could be found with userIdGet");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User backUserFormat = T1.getData();
        //checcked if user is admin
        if (backUserFormat.getAccessLevel().getRole().getValue().equalsIgnoreCase("ADMIN")) {
            log.info("Admin is deleting an account");
            if (assertedUser == id) {
                log.error("Admin trying to delete their account, assertedUser: " + assertedUser);
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


            ApiResponse T2 = null;
            try {
                T2 = apiAccess.userIdDeleteWithHttpInfo(id);
            } catch (ApiException ex) {
                log.error("Failed to delete the user using userIdDelete, errorstatuscode" + ex.getCode(), ex);
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            log.info("An admin has succesfully delted a user account");
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        //checked if user is "USER"
        if (backUserFormat.getAccessLevel().getRole().getValue().equalsIgnoreCase("USER")) {
            log.info("User is deleting an account");
            if (assertedUser == id) {//the user is deleting their own account

                ApiResponse T3 = null;
                try {
                    T3 = apiAccess.userIdDeleteWithHttpInfo(id);
                } catch (ApiException ex) {
                    log.error("Failed to delete the user using userIdDelete, errorstatuscode" + ex.getCode(), ex);
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                log.info("A user has succesfully deleted their own account");
                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }

        log.error("The execution has reached the not implemented part of the code");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
