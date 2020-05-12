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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class UserForgotPasswordApiController implements UserForgotPasswordApi {

    private static final Logger log = LoggerFactory.getLogger(UserForgotPasswordApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserForgotPasswordApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> userForgotPasswordPost(@NotNull @ApiParam(value = "The email of the user making the request", required = true) @Valid @RequestParam(value = "email", required = true) String email) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("Userforgottpassword has been called");

            if (email == null || email.isEmpty()) {
                log.warn("Invalid input data, email: " + email);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            /*if (!email.matches(Utility.EMAIL_REGEX)) {
                log.warn("The email is of invalid format, email: " + email);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            } */


            DefaultApi apiAccess = new DefaultApi();


            ApiResponse<List<User>> T1 = null;

            try {
                T1 = apiAccess.usersGetWithHttpInfo(email);
            } catch (ApiException ex) {
                log.error("Could not retrieve user witth matching email using usersGet,errorstatuscode " + ex.getCode(), ex);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


            if (T1.getStatusCode() == 404 || T1.getStatusCode() == 500) {
                log.error("Could not retrieve users from the backend using userGet , errorstatuscode: " + T1.getStatusCode() + " with parameter: " + email);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            User temp = T1.getData().get(0);
            //temp is now the required user


            //TODO random needs to be set to a random string
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
            StringBuilder sb = new StringBuilder(8);

            for (int i = 0; i < 8; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index = (int)(AlphaNumericString.length() * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            //TODO new salt and hash routine
            String hashed = BCrypt.hashpw(sb.toString(),BCrypt.gensalt(12));
            temp.setPasswordHash(hashed);

            ApiResponse T2 = null;
            try {
                log.info("backuseritem id: "+temp.getId());
                    T2 = apiAccess.userIdPutWithHttpInfo(temp, temp.getId());
            } catch (ApiException ex) {
                log.error("Cannot update the user password usin userIdPut, errorstatuscode: " + ex.getCode(), ex);
            }

            if (T2.getStatusCode() == 404 || T2.getStatusCode() == 304 || T2.getStatusCode() == 500) {
                log.error("could not update the user in backend using userPut , errorstatuscode: " + T2.getStatusCode());
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            log.info("The userforgotmethod has succesfully executed");
            return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);

        }

        log.info("The userforgot method execution reached the not implementted part of the code");
        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

}
