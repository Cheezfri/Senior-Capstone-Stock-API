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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-26T18:04:30.614Z[GMT]")
@Controller
public class UserChangeCurrencyApiController implements UserChangeCurrencyApi {

    private static final Logger log = LoggerFactory.getLogger(UserChangeCurrencyApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserChangeCurrencyApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> userChangeCurrencyPost(@NotNull @ApiParam(value = "The id of the user making the request", required = true) @Valid @RequestParam(value = "asserted_user", required = true) Long assertedUser, @NotNull @ApiParam(value = "The new currency of the user", required = true) @Valid @RequestParam(value = "new_currency", required = true) String newCurrency) {
        String accept = request.getHeader("Accept");

        //get the user from backend
        //update the currency
        //put the user

        DefaultApi apiAccess = new DefaultApi();

        ApiResponse<User> userToChange = null;
        try {
            userToChange = apiAccess.userIdGetWithHttpInfo(assertedUser);
        } catch (ApiException ex) {
            log.error("Could not retrieve the asserted user object using userIdGet, errorstatuscode: " + ex.getCode(), ex);
            if (ex.getCode() == 404) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            } else if (ex.getCode() == 500) {
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        userToChange.getData().setLocalCurrency(newCurrency);

        ApiResponse userChangeResponse = null;
        try {
            userChangeResponse = apiAccess.userIdPutWithHttpInfo(userToChange.getData(), assertedUser);//backend exception
        } catch (ApiException ex) {
            log.error("Could not update the user using userIdPut, errorstatuscode: " + ex.getCode(), ex);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (userChangeResponse.getStatusCode() == 200) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            log.error("Could not update the user properly, error status code: " + userChangeResponse.getStatusCode());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
