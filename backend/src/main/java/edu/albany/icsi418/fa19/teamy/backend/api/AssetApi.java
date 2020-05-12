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
@Api(value = "asset", description = "the asset API")
public interface AssetApi {

    @ApiOperation(value = "", nickname = "assetIdGet", notes = "Retrieve the information about a specific asset", response = Asset.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Asset record found", response = Asset.class),
        @ApiResponse(code = 404, message = "Record not found in the system"),
        @ApiResponse(code = 500, message = "An error occurred processing this request") })
    @RequestMapping(value = "/asset/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Asset> assetIdGet(@ApiParam(value = "The unique identifier of a asset",required=true) @PathVariable("id") Long id);

}
