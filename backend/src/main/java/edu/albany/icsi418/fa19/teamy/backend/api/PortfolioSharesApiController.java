package edu.albany.icsi418.fa19.teamy.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShare;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioShareApiModel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioShareRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class PortfolioSharesApiController implements PortfolioSharesApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioSharesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PortfolioShareRepository portfolioShareRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioSharesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<PortfolioShareApiModel>> portfolioSharesGet(@ApiParam(value = "Id of the portfolio to which the portfolioShare belongs") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId, @ApiParam(value = "Id of the user with whom the portfolio is shared") @Valid @RequestParam(value = "sharedUserId", required = false) Long sharedUserId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            List<PortfolioShare> portfolioShareList = null;

            if ((portfolioId == null || portfolioId <= 0) && (sharedUserId == null || sharedUserId <= 0)) {
                portfolioShareList = portfolioShareRepository.findAll();
            } else if ((portfolioId == null || portfolioId <= 0) && (sharedUserId != null && sharedUserId > 0)) {
                portfolioShareList = portfolioShareRepository.findPortfolioSharesBySharedWith_Id(sharedUserId);
            } else if ((portfolioId != null && portfolioId > 0) && (sharedUserId == null || sharedUserId <= 0)) {
                portfolioShareList = portfolioShareRepository.findPortfolioSharesByPortfolio_Id(portfolioId);
            } else if ((portfolioId != null && portfolioId > 0) && (sharedUserId != null && sharedUserId > 0)) {
                portfolioShareList = portfolioShareRepository.findPortfolioSharesByPortfolio_IdAndSharedWith_Id(portfolioId, sharedUserId);
            }

            if (portfolioShareList != null && !portfolioShareList.isEmpty()) {
                List<PortfolioShareApiModel> portfolioShareApiModelList = new ArrayList<>();
                for (PortfolioShare portfolioShare : portfolioShareList) {
                    portfolioShareApiModelList.add(portfolioShare.toApiModel());
                }
                return new ResponseEntity<List<PortfolioShareApiModel>>(portfolioShareApiModelList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<PortfolioShareApiModel>>(HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity<List<PortfolioShareApiModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
