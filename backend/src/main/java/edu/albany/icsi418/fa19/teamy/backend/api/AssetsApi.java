package edu.albany.icsi418.fa19.teamy.backend.api;

import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
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
@Api(value = "assets", description = "the assets API")
public interface AssetsApi {

    @ApiOperation(value = "", nickname = "assetsGet", notes = "Get all the assets in the system/a specific asset based on search string", response = Asset.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "list of assets", response = Asset.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/assets",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Asset>> assetsGet(@ApiParam(value = "To perform search against the asset ticker") @Valid @RequestParam(value = "tickerSearch", required = false) String tickerSearch, @ApiParam(value = "To perform search against the asset name") @Valid @RequestParam(value = "nameSearch", required = false) String nameSearch);

}
