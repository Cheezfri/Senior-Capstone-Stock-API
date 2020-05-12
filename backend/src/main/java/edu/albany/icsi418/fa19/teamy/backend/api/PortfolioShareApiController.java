package edu.albany.icsi418.fa19.teamy.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShare;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShareApiModel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioShareRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.UserRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class PortfolioShareApiController implements PortfolioShareApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioShareApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PortfolioShareRepository portfolioShareRepository;
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private UserRepository userRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioShareApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioShareIdDelete(@ApiParam(value = "The unique identifier of a portfolioShare",required=true) @PathVariable("id") Long id) {
        PortfolioShare portfolioShareToDelete = portfolioShareRepository.findById(id).orElse(null);
        if (portfolioShareToDelete != null) {
            portfolioShareRepository.delete(portfolioShareToDelete);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PortfolioShareApiModel> portfolioShareIdGet(@ApiParam(value = "The unique identifier of a portfolioShare",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            PortfolioShare portfolioShareToGet = portfolioShareRepository.findById(id).orElse(null);
            if (portfolioShareToGet != null) {
                return new ResponseEntity<PortfolioShareApiModel>(portfolioShareToGet.toApiModel(), HttpStatus.OK);
            } else {
                return new ResponseEntity<PortfolioShareApiModel>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<PortfolioShareApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Void> portfolioShareIdPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioShareApiModel portfolioShareToUpdate,@ApiParam(value = "The unique identifier of a portfolioShare",required=true) @PathVariable("id") Long id) {
        PortfolioShare testId = portfolioShareRepository.findById(id).orElse(null);
        if (testId == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        PortfolioShare updatedPortfolioShare =  portfolioShareToUpdate.toBaseModel(portfolioRepository, userRepository);
        portfolioShareRepository.save(updatedPortfolioShare);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<PortfolioShareApiModel> portfolioSharePost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioShareApiModel portfolioShareToCreate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("Shared portfolio ID: " + portfolioShareToCreate.getPortfolioId());
            log.info("User shared with ID: " + portfolioShareToCreate.getSharedWithUserId());
            PortfolioShare createdPortfolioShare = portfolioShareToCreate.toBaseModel(portfolioRepository, userRepository);
            log.info("Shared portfolio ID: " + createdPortfolioShare.getPortfolio().getId());
            log.info("User shared with ID: " + createdPortfolioShare.getSharedWith().getId());
            portfolioShareRepository.save(createdPortfolioShare);
            return new ResponseEntity<PortfolioShareApiModel>(portfolioShareToCreate, HttpStatus.OK);
        }

        return new ResponseEntity<PortfolioShareApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
