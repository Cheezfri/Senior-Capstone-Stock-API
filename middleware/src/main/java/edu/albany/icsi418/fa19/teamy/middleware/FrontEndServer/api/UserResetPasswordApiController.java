package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import io.swagger.annotations.*;
import org.mindrot.jbcrypt.BCrypt;
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
public class UserResetPasswordApiController implements UserResetPasswordApi {

    private static final Logger log = LoggerFactory.getLogger(UserResetPasswordApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserResetPasswordApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> userResetPasswordPost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "The old password of the user", required = true) @Valid @RequestParam(value = "old_password", required = true) String oldPassword, @NotNull @ApiParam(value = "The new password of the user", required = true) @Valid @RequestParam(value = "new_password", required = true) String newPassword) {
        String accept = request.getHeader("Accept");

        //assertedUser
        //oldPassword
        //newPassword

        if (oldPassword == null || oldPassword.isEmpty() || newPassword == null || oldPassword.isEmpty()) {
            log.warn("Invalid data has been provided in input oldpass:" + oldPassword + " newpass: " + newPassword);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //rettrieve the user from the backend
        //check if the password hash maches the oldpassword hash
        //then update if it matches or error

        DefaultApi apiAccess = new DefaultApi();

        ApiResponse<User> T1 = null;

        try {
            T1 = apiAccess.userIdGetWithHttpInfo(assertedUser);//backend exception
        } catch (ApiException ex) {
            log.error("Could not retrieve the asserted user object using userIdGet, errorstatuscode: " + ex.getCode(), ex);
        }

        if (T1.getStatusCode() == 404) {
            log.error("could not find the asserteduser: " + assertedUser + " errorsttatuscode: " + T1.getStatusCode());
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else if (T1.getStatusCode() == 500) {
            log.error("could not retrieve the asserteduser: " + assertedUser + " errorsttatuscode: " + T1.getStatusCode());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        User backUserItem = T1.getData();
        log.info("backUserItem: "+backUserItem.toString());
        //TODO add salt and hash before checking oldpassword matches the password hash of the user

        if (BCrypt.checkpw(oldPassword,backUserItem.getPasswordHash())) {

            //TODO add a new salt and rehash before updating new password
            String hashed = BCrypt.hashpw(newPassword,BCrypt.gensalt(12));
            backUserItem.setPasswordHash(hashed);


            ApiResponse T2 = null;
            try {log.info("backuseritem id: "+backUserItem.getId()+"assertedUser: "+assertedUser);
                T2 = apiAccess.userIdPutWithHttpInfo(backUserItem, assertedUser);//backend exception
            } catch (ApiException ex) {
                log.error("Could not update the user using userIdPut, errorstatuscode: " + ex.getCode(), ex);
                if(T2.getStatusCode() == 500){
                    log.error("failed tto update the user, errorstatuscode: "+T2.getStatusCode());
                }
            }

            log.info("successfully completed the method");
            return new ResponseEntity<Void>(HttpStatus.OK);

        } else {
            log.error("The old password and new password of the user does not match");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
