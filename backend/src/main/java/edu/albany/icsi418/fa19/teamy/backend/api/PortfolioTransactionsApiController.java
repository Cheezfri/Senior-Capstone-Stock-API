package edu.albany.icsi418.fa19.teamy.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransaction;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransactionApiModel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioTransactionRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class PortfolioTransactionsApiController implements PortfolioTransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioTransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PortfolioTransactionRepository portfolioTransactionRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioTransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<PortfolioTransactionApiModel>> portfolioTransactionsGet(@ApiParam(value = "Portfolio for which the transaction was made") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            List<PortfolioTransaction> portfolioTransactionList = null;
            if (portfolioId == null || portfolioId == 0) {
                portfolioTransactionList = portfolioTransactionRepository.findAll();
            } else {
                portfolioTransactionList = portfolioTransactionRepository.findPortfolioTransactionsByPortfolio_Id(portfolioId);
                Collections.sort(portfolioTransactionList);
            }

            if (portfolioTransactionList != null && !portfolioTransactionList.isEmpty()) {
                ArrayList<PortfolioTransactionApiModel> portfolioTransactionsApiModelList = new ArrayList<>();
                for (PortfolioTransaction portTran : portfolioTransactionList) {
                    portfolioTransactionsApiModelList.add(portTran.toApiModel());
                }
                return new ResponseEntity<List<PortfolioTransactionApiModel>>(portfolioTransactionsApiModelList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<PortfolioTransactionApiModel>>(HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity<List<PortfolioTransactionApiModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
