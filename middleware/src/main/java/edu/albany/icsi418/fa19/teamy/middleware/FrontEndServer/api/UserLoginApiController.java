package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AccessLevel;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AccessRole;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class UserLoginApiController implements UserLoginApi {

    private static final Logger log = LoggerFactory.getLogger(UserLoginApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserLoginApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<User> userLoginPost(@NotNull @ApiParam(value = "Username of the user", required = true) @Valid @RequestParam(value = "username", required = true) String username,@NotNull @ApiParam(value = "Password of the user", required = true) @Valid @RequestParam(value = "password", required = true) String password) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {


                if(username == null || username.isEmpty() || password == null || password.isEmpty()){
                    log.warn("Invalid data has been provided as input: username: "+username+" password: "+ password);
                    throw new ApiException(500, " Invalid data provided/Bad request");
                }

                /*if (!username.matches(Utility.EMAIL_REGEX)) {
                    log.warn("The email format is invalid, email: "+username );
                    throw new ApiException(500, " Invalid data provided/Bad request");
                } */
                //create a new backendmodel user list to store o/p of usersGet


                DefaultApi apiAccess = new DefaultApi();


                //ideally the first item of the list is the exact username
                ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User>> T1 = apiAccess.usersGetWithHttpInfo(username);
                if(T1.getStatusCode() == 404 || T1.getStatusCode() == 500){
                    log.error("Cannot retrieve any users with the given username from the backend, errorstatuscode:"+T1.getStatusCode()+" username: "+ username);
                    throw new ApiException(T1.getStatusCode(),"An error has occured");
                }

                List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User>tempusers = T1.getData();

                if(tempusers.isEmpty()){
                    log.error("The returned list of user that match with this email is empty, username: "+username);
                    throw new edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException(404, "Record not found in the system");
                }
                edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User temp = tempusers.get(0);

                //TODO Password parameter hashing after adding salt

                if (username.equalsIgnoreCase(temp.getEmail()) && BCrypt.checkpw(password, temp.getPasswordHash())) {
                    User frontUserFormat = new User();
                    frontUserFormat.setId(temp.getId());
                    frontUserFormat.setFirstName(temp.getFirstName());
                    frontUserFormat.setLastName(temp.getLastName());
                    frontUserFormat.setEmail(temp.getEmail());
                    frontUserFormat.setLocked(temp.isLocked());
                    frontUserFormat.setLocalCurrency(temp.getLocalCurrency());

                    AccessLevel intermediate = new AccessLevel();

                    intermediate.setId(temp.getAccessLevel().getId());
                    intermediate.setName(temp.getAccessLevel().getName());


                    if ("USER".equalsIgnoreCase(temp.getAccessLevel().getRole().getValue())) {
                        intermediate.setRole(AccessRole.USER);
                    } else if ("ADMIN".equalsIgnoreCase(temp.getAccessLevel().getRole().getValue())) {
                        intermediate.setRole(AccessRole.ADMIN);
                    } else if ("SUPPORT".equalsIgnoreCase(temp.getAccessLevel().getRole().getValue())) {
                        intermediate.setRole(AccessRole.SUPPORT);

                    }
                    frontUserFormat.setAccesslevel(intermediate);

                    log.info("successfully completed the method and the user has logged in");
                    return new ResponseEntity<User>(frontUserFormat, HttpStatus.OK);


                } else {
                    log.warn("The username and password do not match actualusername: "+temp.getEmail()+" actualpassword: "+temp.getPasswordHash());
                    throw new ApiException(500, "An error occurred processing this request");
                }
            } catch (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException a) {

                log.error("API Exception Code: " + a.getCode(), a);
                if (a.getCode() == 500) {
                    return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
                } else if (a.getCode() == 404) {
                    return new ResponseEntity<User>(HttpStatus.NOT_FOUND);

                }
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

}
