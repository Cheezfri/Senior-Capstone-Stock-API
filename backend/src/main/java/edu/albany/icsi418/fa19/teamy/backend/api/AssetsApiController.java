package edu.albany.icsi418.fa19.teamy.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetRepository;
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
public class AssetsApiController implements AssetsApi {

    private static final Logger log = LoggerFactory.getLogger(AssetsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AssetRepository assetRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AssetsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }
    public ResponseEntity<List<Asset>> assetsGet(@ApiParam(value = "To perform search against the asset ticker") @Valid @RequestParam(value = "tickerSearch", required = false) String tickerSearch,@ApiParam(value = "To perform search against the asset name") @Valid @RequestParam(value = "nameSearch", required = false) String nameSearch) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<Asset> assetList = null;

            if (tickerSearch == null && nameSearch == null) {
                assetList = assetRepository.findAll();
            } else if (tickerSearch == null && nameSearch != null) {
                assetList = assetRepository.findAssetsByName(nameSearch);
            } else if (tickerSearch != null && nameSearch == null) {
                assetList = assetRepository.findAssetsByTicker(tickerSearch);
            } else if (tickerSearch != null && nameSearch != null) {
                assetList = assetRepository.findAssetsByNameAndTicker(tickerSearch, nameSearch);
            }

            if (assetList != null && !assetList.isEmpty()) {
                return new ResponseEntity<List<Asset>>(assetList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Asset>>(HttpStatus.NOT_FOUND);
            }
        }

        return new ResponseEntity<List<Asset>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
