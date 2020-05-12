package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.Asset;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AssetProportion;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.HoldingAsset;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-20T03:09:25.605Z[GMT]")
@Controller
public class AssetProportionsApiController implements AssetProportionsApi {

    private static final Logger log = LoggerFactory.getLogger(AssetProportionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AssetProportionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<AssetProportion>> assetProportionsGet(@NotNull @ApiParam(value = "Portfolio for which the transaction was made", required = true) @Valid @RequestParam(value = "portfolioId", required = true) Long portfolioId,@NotNull @ApiParam(value = "The sorting order in which the data is required", required = true) @Valid @RequestParam(value = "SortBy", required = true) String sortBy) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("AssetProportions ins called in MW");

                //portfolioId, SortBy are the parameters, -- sorting is currently done in the fronttend,so parameter ignored

                if(portfolioId == null || portfolioId ==0){
                    return new ResponseEntity<List<AssetProportion>>(HttpStatus.BAD_REQUEST);
                }

                DefaultApi apiAccess = new DefaultApi();
                //this is the object finally returned to tthe frontend
                List<AssetProportion> returnObjectList = new ArrayList<AssetProportion>();

            List<HoldingAsset> holdingAssets = null;
            try {
                    holdingAssets = Utility.getHoldingAssets(portfolioId);
                } catch (ApiException ex){
                    log.error("An error has occured retrieving holding assets errorstatuscode: "+ex.getCode(),ex);
                    return new ResponseEntity<List<AssetProportion>>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            if(holdingAssets.isEmpty()){
                log.info("Holdingassets is empty");
                return new ResponseEntity<List<AssetProportion>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }


                double totalNetPrice =0;
                double totalQuantity =0;
                for(HoldingAsset item: holdingAssets){
                    totalNetPrice = totalNetPrice + Math.abs(item.getNetValue());
                    //log.info("TotalNetPrice: "+totalNetPrice);
                    totalQuantity = totalQuantity + Math.abs(item.getQuantity());
                    //log.info("TotalNetQuantity: "+totalQuantity);
                }
                //log.info("EndTotalNetPrice: "+totalNetPrice);
                //log.info("EndTotalNetQuantity: "+totalQuantity);


                for(HoldingAsset item:holdingAssets){
                    AssetProportion returnObject = new AssetProportion();
                    ApiResponse<Asset> assetInfo = null;
                    try {
                        assetInfo = apiAccess.assetIdGetWithHttpInfo(item.getOwnedAssetId());
                    } catch (ApiException ex){
                        log.info("Could not retrieve the asset info from the be using assetidget, errorstatuscode: "+ex.getCode(),ex);
                        if(ex.getCode() == 404){
                            return new ResponseEntity<List<AssetProportion>>(HttpStatus.NOT_FOUND);
                        }
                        if(ex.getCode() == 500){
                            return new ResponseEntity<List<AssetProportion>>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }

                    //log.info("The asset info is retrieved, assetId: "+item.getOwnedAssetId());
                    //log.info("assetInfo.getdata: "+assetInfo.getData().toString());
                    Asset holdingAssetInfo = assetInfo.getData();
                    //log.info("holdingassetInfo: "+holdingAssetInfo.toString());
                    returnObject.setAssetName(holdingAssetInfo.getName());
                    returnObject.setAssetTicker(holdingAssetInfo.getTicker());
                    returnObject.setAssetType(holdingAssetInfo.getCategory().getValue());
                    returnObject.setAssetNetPrice(item.getNetValue());
                    returnObject.setAssetQuantity(item.getQuantity());
                    returnObject.setAssetNetPriceProportion((Math.abs(item.getNetValue())/totalNetPrice)*100);
                    returnObject.setAssetQuantityProportion((Math.abs(item.getQuantity())/totalQuantity)*100);
                    returnObjectList.add(returnObject);
                }
                return new ResponseEntity<List<AssetProportion>>(returnObjectList, HttpStatus.OK);
        }

        return new ResponseEntity<List<AssetProportion>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
