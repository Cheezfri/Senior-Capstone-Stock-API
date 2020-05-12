package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.AccessLevel;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.UserSignup;
import io.swagger.annotations.ApiParam;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class UserSignupApiController implements UserSignupApi {

    private static final Logger log = LoggerFactory.getLogger(UserSignupApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserSignupApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> userSignupPost(@ApiParam(value = "Filled out user signup form", required = true) @Valid @RequestBody UserSignup signupForm) {
        String accept = request.getHeader("Accept");

        //signupForm is the front end object that is recieved
        //reformat it to the backend model and crate it in the database

        if (signupForm.getFirstName() == null || signupForm.getFirstName().isEmpty() || signupForm.getLastName() == null || signupForm.getLastName().isEmpty() || signupForm.getPassword() == null || signupForm.getPassword().isEmpty()) {
            log.warn("Invalid Data: ", signupForm);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //email format validiy checking

       /* if (!signupForm.getEmail().matches(Utility.EMAIL_REGEX)) {
            log.warn("Regex not matching for the email" + signupForm.getEmail());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } */


        DefaultApi apiAccess = new DefaultApi();

        ApiResponse<List<User>> T1 = null;
        try {
            //ideally the first item of the list is the exact username
            T1 = apiAccess.usersGetWithHttpInfo(signupForm.getEmail());
            if (T1.getStatusCode() != 404) {
                // This should 404 to show no users already with that email....
                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
            }
        } catch (ApiException ex) { // Thrown for calls returning < 200 or >= 400
            if (ex.getCode() != 404) {
                log.error("API Exception querying for dupe new accounts: " + ex.getCode(), ex);
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // If we got to here, we got the 404 on dupe check an can continue,
        log.info("No user with matching username is found in the backend");

        User backUserFormat = new User();
        backUserFormat.setFirstName(signupForm.getFirstName());
        backUserFormat.setLastName(signupForm.getLastName());
        backUserFormat.setEmail(signupForm.getEmail());
        backUserFormat.setLocalCurrency(signupForm.getLocalCurrency());

        //TODO
        //Have to set a random string as salt
        backUserFormat.setSalt("temp");

        //TODO
        //Have to bcrypt the password after attaching the salt
        String hashed = BCrypt.hashpw(signupForm.getPassword(),BCrypt.gensalt(12));
        backUserFormat.setPasswordHash(hashed);

        backUserFormat.setLocked(false);

        //need to call the backend for an array of accesslevels and then set the access level according
        ApiResponse<List<AccessLevel>> T2 = null;
        try {
            T2 = apiAccess.accessLevelsGetWithHttpInfo();
        } catch (ApiException ex) {
            log.error("failed to retrieve access levels,errorstatuscode: " + ex.getCode());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        for (AccessLevel ar : T2.getData()) {
            if ("USER".equalsIgnoreCase(ar.getRole().getValue())) {
                backUserFormat.setAccessLevel(ar);
            }
        }

        if (backUserFormat.getAccessLevel() == null) {
            log.error("Unable to find user access role");
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //if execution has reached this point, then it would mean that the user is successfull created

        //will recieve a new user object when a user is posted to the dattabase, no need to returnn it to front end for signup

        ApiResponse<User> T3 = null;
        try {
            T3 = apiAccess.userPostWithHttpInfo(backUserFormat);
        } catch (ApiException ex) {
            log.error("posting the object failed,errorstatuscode: " + ex.getCode());
            return new ResponseEntity<>(HttpStatus.resolve(ex.getCode()));
        }

        log.info("successfully completed the method");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}