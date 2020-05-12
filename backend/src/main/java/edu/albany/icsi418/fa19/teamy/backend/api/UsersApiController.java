package edu.albany.icsi418.fa19.teamy.backend.api;

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
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserRepository userRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<User>> usersGet(@ApiParam(value = "Email address of user") @Valid @RequestParam(value = "email", required = false) String email) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<User> userList = null;
            if (email != null) {
                 userList = userRepository.findUsersByEmail(email);
            } else {
                userList = userRepository.findAll();
            }
            if (userList != null && !userList.isEmpty()) {
                return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<User>>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
