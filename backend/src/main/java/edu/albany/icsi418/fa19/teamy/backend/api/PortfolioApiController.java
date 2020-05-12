package edu.albany.icsi418.fa19.teamy.backend.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioApiModel;
import edu.albany.icsi418.fa19.teamy.backend.models.user.User;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioRepository;
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

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class PortfolioApiController implements PortfolioApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> portfolioIdDelete(@ApiParam(value = "The unique identifier of a portfolio",required=true) @PathVariable("id") Long id) {
        Optional<Portfolio> portfolioToDelete = portfolioRepository.findById(id);
        if (portfolioToDelete.isPresent()) {
            portfolioRepository.delete(portfolioToDelete.get());
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PortfolioApiModel> portfolioIdGet(@ApiParam(value = "The unique identifier of a portfolio",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Optional<Portfolio> portfolioOpt = portfolioRepository.findById(id);
            if (portfolioOpt.isPresent()) {
                return new ResponseEntity<PortfolioApiModel>(portfolioOpt.get().toApiModel(), HttpStatus.OK);
            } else {
                return new ResponseEntity<PortfolioApiModel>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<PortfolioApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Void> portfolioIdPut(@ApiParam(value = "", required = true) @Valid @RequestBody PortfolioApiModel portfolioToUpdate, @ApiParam(value = "The unique identifier of a portfolio", required = true) @PathVariable("id") Long id) {

        Portfolio updatedPortfolio = portfolioRepository.findById(id).orElse(null);
        if (updatedPortfolio != null) {

            updatedPortfolio.setId(portfolioToUpdate.getId());
            updatedPortfolio.setName(portfolioToUpdate.getName());
            updatedPortfolio.setDeleted(portfolioToUpdate.isDeleted());
            updatedPortfolio.setSerialNumber(portfolioToUpdate.getSerialNumber());
            updatedPortfolio.setType(portfolioToUpdate.getType());

            Optional<User>  owner = userRepository.findById(portfolioToUpdate.getOwnerUserId());
            if (!owner.isPresent()) {
                log.error("Owner not found at id: " + portfolioToUpdate.getOwnerUserId());
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                updatedPortfolio.setOwner(owner.get());
            }

            portfolioRepository.save(updatedPortfolio);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<PortfolioApiModel> portfolioPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioApiModel portfolioToCreate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            Portfolio createdPortfolio = new Portfolio();

            createdPortfolio.setName(portfolioToCreate.getName());
            createdPortfolio.setDeleted(portfolioToCreate.isDeleted());
            createdPortfolio.setSerialNumber(portfolioToCreate.getSerialNumber());
            createdPortfolio.setType(portfolioToCreate.getType());

            log.info("Incoming owner Id: " + portfolioToCreate.getOwnerUserId());
            Optional<User>  owner = userRepository.findById(portfolioToCreate.getOwnerUserId());
            if (!owner.isPresent()) {
                log.info(portfolioToCreate.toString());
                log.info(String.valueOf(portfolioToCreate));
                log.error("Owner not found at id: " + portfolioToCreate.getOwnerUserId());
                return new ResponseEntity<PortfolioApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                createdPortfolio.setOwner(owner.get());
                log.info("Created Portfolio Owner Id: " + createdPortfolio.getOwner().getId());
            }

            portfolioRepository.save(createdPortfolio);

            return new ResponseEntity<PortfolioApiModel>(createdPortfolio.toApiModel(), HttpStatus.OK);

        }

        return new ResponseEntity<PortfolioApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
