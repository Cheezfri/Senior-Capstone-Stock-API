package edu.albany.icsi418.fa19.teamy.backend.api;

import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceDataApiModel;
import org.threeten.bp.OffsetDateTime;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Api(value = "assetPriceDatas", description = "the assetPriceDatas API")
public interface AssetPriceDatasApi {

    @ApiOperation(value = "", nickname = "assetPriceDatasGet", notes = "Get the asset price data of an asset/assets", response = AssetPriceData.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "asset record found", response = AssetPriceData.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/assetPriceDatas",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<AssetPriceDataApiModel>> assetPriceDatasGet(@ApiParam(value = "unique key of assetpricedata") @Valid @RequestParam(value = "assetId", required = false) Long assetId, @ApiParam(value = "to get the assetprice data starting from a particular date") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate, @ApiParam(value = "to get the assetprice data till a particular date") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate);

}
