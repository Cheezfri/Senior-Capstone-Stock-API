package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioApiModel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class PortfoliosApiController implements PortfoliosApi {

    private static final Logger log = LoggerFactory.getLogger(PortfoliosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfoliosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<PortfolioApiModel>> portfoliosGet(@ApiParam(value = "The unique id of the user") @Valid @RequestParam(value = "userId", required = false) Long userId, @ApiParam(value = "Name of the portfolio") @Valid @RequestParam(value = "portfolioName", required = false) String portfolioName) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            List<Portfolio> portfolioList = null;

            if (userId == null && portfolioName == null) {
                portfolioList = portfolioRepository.findAll();
            } else if (userId == null && portfolioName != null) {
                portfolioList = portfolioRepository.findPortfoliosByName(portfolioName);
            } else if (userId != null && portfolioName == null) {
                portfolioList = portfolioRepository.findPortfoliosByOwner_Id(userId);
            } else if (userId != null && portfolioName != null) {
                portfolioList = portfolioRepository.findPortfoliosByOwner_IdAndName(userId, portfolioName);
            }

            if (portfolioList != null && !portfolioList.isEmpty()) {
                ArrayList<PortfolioApiModel> portfolioApiModelList = new ArrayList<>();
                for (Portfolio portfolio : portfolioList)
                    portfolioApiModelList.add(portfolio.toApiModel());
                    return new ResponseEntity<List<PortfolioApiModel>>(portfolioApiModelList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<PortfolioApiModel>>(HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity<List<PortfolioApiModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
