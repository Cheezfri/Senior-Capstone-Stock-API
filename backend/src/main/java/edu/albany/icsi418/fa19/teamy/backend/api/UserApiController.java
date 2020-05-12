package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.user.User;
import edu.albany.icsi418.fa19.teamy.backend.respositories.UserRepository;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserRepository userRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    // TODO: Implement method
    public ResponseEntity<Void> userIdDelete(@ApiParam(value = "The unique id of the user",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<User> userIdGet(@ApiParam(value = "The unique id of the user",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isPresent()) {
                return new ResponseEntity<User>(userOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Void> userIdPut(@ApiParam(value = "", required = true) @Valid @RequestBody User userToUpdate, @ApiParam(value = "The unique id of the user", required = true) @PathVariable("id") Long id) {

        User updatedUser = userRepository.findById(id).orElse(null);

        if (updatedUser != null) {

            updatedUser.setEmail(userToUpdate.getEmail());
            updatedUser.setFirstName(userToUpdate.getFirstName());
            updatedUser.setLastName(userToUpdate.getLastName());
            updatedUser.setAccessLevel(userToUpdate.getAccessLevel());
            updatedUser.lock(userToUpdate.isLocked());
            updatedUser.setLocalCurrency(userToUpdate.getLocalCurrency());
            updatedUser.setPasswordHash(userToUpdate.getPasswordHash());
            updatedUser.setSalt(userToUpdate.getSalt());

            userRepository.save(updatedUser);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

    }


    public ResponseEntity<User> userPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User userToCreate) {
        String accept = request.getHeader("Accept");
        log.info("accept: " + accept);
        if (accept != null && accept.contains("application/json")) {

            User createdUser = new User();
            createdUser.setEmail(userToCreate.getEmail());
            createdUser.setFirstName(userToCreate.getFirstName());
            createdUser.setLastName(userToCreate.getLastName());
            createdUser.setAccessLevel(userToCreate.getAccessLevel());
            createdUser.lock(userToCreate.isLocked());
            createdUser.setLocalCurrency(userToCreate.getLocalCurrency());
            createdUser.setPasswordHash(userToCreate.getPasswordHash());
            createdUser.setSalt(userToCreate.getSalt());

            userRepository.save(createdUser);

            return new ResponseEntity<User>(createdUser, HttpStatus.OK);

        }

        return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
