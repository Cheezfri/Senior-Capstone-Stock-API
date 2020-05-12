package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.user.AccessLevel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AccessLevelRepository;
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
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class AccessLevelApiController implements AccessLevelApi {

    private static final Logger log = LoggerFactory.getLogger(AccessLevelApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AccessLevelRepository accessLevelRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AccessLevelApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<AccessLevel> accessLevelIdGet(@ApiParam(value = "The unique id of the access level",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Optional<AccessLevel> accessLevelOpt = accessLevelRepository.findById(id);
            if (accessLevelOpt.isPresent()) {
                return new ResponseEntity<AccessLevel>(accessLevelOpt.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<AccessLevel>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
