package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;


import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AccessLevel;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AccessRole;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class UserSearchApiController implements UserSearchApi {

    private static final Logger log = LoggerFactory.getLogger(UserSearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserSearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<User>> userSearchGet(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser,@ApiParam(value = "Email address of a user to search") @Valid @RequestParam(value = "email", required = false) String email) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            //this is totally an admin functionality for now
            //if emailid is an empty string, then will return all the users in the system
            //if email id is specific then will return only one user

            try {
                DefaultApi apiAccess = new DefaultApi();
                ApiResponse<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User> T1 = apiAccess.userIdGetWithHttpInfo(assertedUser);
                if(T1.getStatusCode() == 404 || T1.getStatusCode() == 500){
                    log.error("Could not retrieve the asserted user, errorstatuscode: "+T1.getStatusCode()+" with id: "+ assertedUser);
                    throw new ApiException(T1.getStatusCode(),"An error has occured");
                }
                edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User tempUser = T1.getData();
                if (tempUser.getAccessLevel().getRole().getValue().equals("ADMIN")) {
                    log.info("The person searching for users is an Admin");

                    ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User>> T2 = apiAccess.usersGetWithHttpInfo(email);
                    if(T2.getStatusCode() == 404 || T2.getStatusCode() == 500){
                        log.info("could not retrieve any users, errorstatuscode: "+T2.getStatusCode()+" with the email:"+ email);
                        throw new ApiException(T2.getStatusCode(),"An Error has occured");
                    }
                    List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User> tempUsers = T2.getData();

                    List<User> frontReturnableUsers = new ArrayList();

                    for (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.User item : tempUsers) {
                        User tempFront = new User();
                        tempFront.setId(item.getId());
                        tempFront.setLocalCurrency(item.getLocalCurrency());
                        tempFront.setLocked(item.isLocked());
                        tempFront.setEmail(item.getEmail());
                        tempFront.setLastName(item.getLastName());
                        tempFront.setFirstName(item.getFirstName());

                        AccessLevel intermediate = new AccessLevel();
                        intermediate.setName(item.getAccessLevel().getName());
                        intermediate.setId(item.getAccessLevel().getId());
                        if (item.getAccessLevel().getRole().getValue().equals("USER")) {
                            intermediate.setRole(AccessRole.USER);
                        } else if (item.getAccessLevel().getRole().getValue().equals("ADMIN")) {
                            intermediate.setRole(AccessRole.ADMIN);

                        } else if (item.getAccessLevel().getRole().getValue().equals("SUPPORT")) {
                            intermediate.setRole(AccessRole.SUPPORT);
                        }
                        tempFront.setAccesslevel(intermediate);
                        frontReturnableUsers.add(tempFront);
                    }

                    log.info("successfully completed the method and returned the users");
                    return new ResponseEntity<List<User>>(frontReturnableUsers, HttpStatus.OK);
                }
                // return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"firstName\" : \"John\",\n  \"lastName\" : \"Doe\",\n  \"accesslevel\" : {\n    \"role\" : \"USER\",\n    \"name\" : \"Administrator\",\n    \"id\" : 1\n  },\n  \"id\" : 1,\n  \"localCurrency\" : \"Yen\",\n  \"locked\" : true,\n  \"email\" : \"user@example.com\"\n}, {\n  \"firstName\" : \"John\",\n  \"lastName\" : \"Doe\",\n  \"accesslevel\" : {\n    \"role\" : \"USER\",\n    \"name\" : \"Administrator\",\n    \"id\" : 1\n  },\n  \"id\" : 1,\n  \"localCurrency\" : \"Yen\",\n  \"locked\" : true,\n  \"email\" : \"user@example.com\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (ApiException a) {
                log.error("API Exception Code: " + a.getCode(), a);
                if (a.getCode() == 500) {
                    return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
                } else if (a.getCode() == 404) {
                    return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
