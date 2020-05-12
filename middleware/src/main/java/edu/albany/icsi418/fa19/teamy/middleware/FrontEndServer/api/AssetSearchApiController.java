package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.Asset;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetCategory;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class AssetSearchApiController implements AssetSearchApi {

    private static final Logger log = LoggerFactory.getLogger(AssetSearchApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AssetSearchApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Asset>> assetSearchGet(@ApiParam(value = "The string used to find matching assets") @Valid @RequestParam(value = "searchString", required = false) String searchString) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            //searchstring only parameter, returns either matching assets or all assets

            List<Asset> frontReturnableAssets = new ArrayList<Asset>();
            DefaultApi apiAccess = new DefaultApi();

            //basic idea is to search for matching tickers and assets against the searchString

            ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset>> tickerAssets = null;
            try {
                tickerAssets = apiAccess.assetsGetWithHttpInfo(searchString, null);
            } catch (ApiException ex) {
                log.error("Could not retrieve the assets using the search string, errorstatus code: " + ex.getCode(), ex);
                return new ResponseEntity<List<Asset>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            log.info("The search results have been retrieved, no of assets returned from BE using tickersearch: "+tickerAssets.getData().size());

            for (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset item : tickerAssets.getData()) {
                Asset frontAsset = new Asset();
                frontAsset.setId(item.getId());
                if (item.getCategory().getValue().equals("STOCK")) {
                    frontAsset.setCategory(AssetCategory.STOCK);
                } else if (item.getCategory().getValue().equals("BOND")) {
                    frontAsset.setCategory(AssetCategory.BOND);
                } else if (item.getCategory().getValue().equals("CRYPTO")) {
                    frontAsset.setCategory(AssetCategory.CRYPTO);
                } else if (item.getCategory().getValue().equals("CURRENCY")) {
                    frontAsset.setCategory(AssetCategory.CURRENCY);
                }
                frontAsset.setDescription(item.getDescription());
                frontAsset.setName(item.getName());
                frontAsset.setTicker(item.getTicker());

                frontReturnableAssets.add(frontAsset);
            }
            
//            ApiResponse<List<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset>> nameAssets = null;
//            try {
//                nameAssets = apiAccess.assetsGetWithHttpInfo(null, searchString);
//            } catch (ApiException ex) {
//                log.error("Could not retrieve the assets using the search string, errorstatus code: " + ex.getCode(), ex);
//                return new ResponseEntity<List<Asset>>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//
//            log.info("The search results have been retrieved, no of assets returned from BE using Namesearch: "+nameAssets.getData().size());
//
//            for (edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset item : nameAssets.getData()) {
//                Asset frontAsset = new Asset();
//                frontAsset.setId(item.getId());
//                //item.getCategory().getValue().equals("STOCK")
//                if ("STOCK".equals(item.getCategory().getValue())) {
//                    frontAsset.setCategory(AssetCategory.STOCK);
//                } else if ("BOND".equals(item.getCategory().getValue())) {
//                    frontAsset.setCategory(AssetCategory.BOND);
//                } else if ("CRYPTO".equals(item.getCategory().getValue())) {
//                    frontAsset.setCategory(AssetCategory.CRYPTO);
//                } else if ("CURRENCY".equals(item.getCategory().getValue())) {
//                    frontAsset.setCategory(AssetCategory.CURRENCY);
//                }
//                frontAsset.setDescription(item.getDescription());
//                frontAsset.setName(item.getName());
//                frontAsset.setTicker(item.getTicker());
//
//                frontReturnableAssets.add(frontAsset);
//            }

            log.info("The search results are now stored in frontReturnableAssets, arraysize: "+frontReturnableAssets.size());
            return new ResponseEntity<List<Asset>>(frontReturnableAssets, HttpStatus.OK);
        }
        return new ResponseEntity<List<Asset>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
