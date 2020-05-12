package edu.albany.icsi418.fa19.teamy.backend.api;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransaction;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransactionApiModel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioRepository;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class PortfolioTransactionApiController implements PortfolioTransactionApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioTransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private PortfolioTransactionRepository portfolioTransactionRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private AssetRepository assetRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioTransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    
    public ResponseEntity<Void> portfolioTransactionIdDelete(@ApiParam(value = "The unique id of the portfolioTransaction",required=true) @PathVariable("id") Long id) {

            PortfolioTransaction portfolioTransactionToDelete = portfolioTransactionRepository.findById(id).orElse(null);

            if (portfolioTransactionToDelete != null) {
                portfolioTransactionRepository.delete(portfolioTransactionToDelete);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
 
    }

    public ResponseEntity<PortfolioTransactionApiModel> portfolioTransactionIdGet(@ApiParam(value = "The unique id of the PortfolioTransaction",required=true) @PathVariable("id") Long id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            Optional<PortfolioTransaction> portfolioTransactionOpt = portfolioTransactionRepository.findById(id);
            if (portfolioTransactionOpt.isPresent()) {
                PortfolioTransactionApiModel portfolioTransaction = portfolioTransactionOpt.get().toApiModel();
                return new ResponseEntity<PortfolioTransactionApiModel>(portfolioTransaction, HttpStatus.OK);
            } else {
                return new ResponseEntity<PortfolioTransactionApiModel>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<PortfolioTransactionApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Void> portfolioTransactionIdPut(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioTransactionApiModel portfolioTransactionToUpdate,@ApiParam(value = "The unique id of the transaction that is updated",required=true) @PathVariable("id") Long id) {

        PortfolioTransaction updatedPortfolioTransaction = portfolioTransactionRepository.findById(id).orElse(null);

        if (updatedPortfolioTransaction != null) {

            updatedPortfolioTransaction.setId(portfolioTransactionToUpdate.getId());

            Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioTransactionToUpdate.getPortfolioId());
            if (portfolio.isPresent()) {
                updatedPortfolioTransaction.setPortfolio(portfolio.get());
            } else {
                log.error("Portfolio transaction belongs to not found, id: " + portfolioTransactionToUpdate.getPortfolioId());
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            updatedPortfolioTransaction.setType(portfolioTransactionToUpdate.getType());

            Optional<Asset> asset = assetRepository.findById(portfolioTransactionToUpdate.getAssetId());
            if (asset.isPresent()) {
                updatedPortfolioTransaction.setAsset(asset.get());
            } else {
                log.error("Asset belonging to transaction not found, id: " + portfolioTransactionToUpdate.getAssetId());
                return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            updatedPortfolioTransaction.setDateTime(portfolioTransactionToUpdate.getDateTime());
            updatedPortfolioTransaction.setQuantity(portfolioTransactionToUpdate.getQuantity());
            updatedPortfolioTransaction.setPrice(portfolioTransactionToUpdate.getPrice());

            portfolioTransactionRepository.save(updatedPortfolioTransaction);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<PortfolioTransactionApiModel> portfolioTransactionPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody PortfolioTransactionApiModel portfolioTransactionToCreate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            PortfolioTransaction createdPortfolioTransaction = new PortfolioTransaction();

            Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioTransactionToCreate.getPortfolioId());
            if (portfolio.isPresent()) {
                createdPortfolioTransaction.setPortfolio(portfolio.get());
            } else {
                log.error("Portfolio transaction belongs to not found, id: " + portfolioTransactionToCreate.getPortfolioId());
                return new ResponseEntity<PortfolioTransactionApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            createdPortfolioTransaction.setType(portfolioTransactionToCreate.getType());

            Optional<Asset> asset = assetRepository.findById(portfolioTransactionToCreate.getAssetId());
            if (asset.isPresent()) {
                createdPortfolioTransaction.setAsset(asset.get());
            } else {
                log.error("Asset belonging to transaction not found, id: " + portfolioTransactionToCreate.getAssetId());
                return new ResponseEntity<PortfolioTransactionApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            createdPortfolioTransaction.setDateTime(portfolioTransactionToCreate.getDateTime());
            createdPortfolioTransaction.setQuantity(portfolioTransactionToCreate.getQuantity());
            createdPortfolioTransaction.setPrice(portfolioTransactionToCreate.getPrice());

            portfolioTransactionRepository.save(createdPortfolioTransaction);

            return new ResponseEntity<PortfolioTransactionApiModel>(createdPortfolioTransaction.toApiModel(), HttpStatus.OK);

        }

        return new ResponseEntity<PortfolioTransactionApiModel>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
