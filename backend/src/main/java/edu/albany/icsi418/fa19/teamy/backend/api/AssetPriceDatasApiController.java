package edu.albany.icsi418.fa19.teamy.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.models.DateTimeConversion;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceDataApiModel;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetPriceDataRepository;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.threeten.bp.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-10T19:37:20.931Z[GMT]")
@Controller
public class AssetPriceDatasApiController implements AssetPriceDatasApi {

    private static final Logger log = LoggerFactory.getLogger(AssetPriceDatasApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AssetPriceDataRepository assetPriceDataRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AssetPriceDatasApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<AssetPriceDataApiModel>> assetPriceDatasGet(@ApiParam(value = "unique key of assetpricedata") @Valid @RequestParam(value = "assetId", required = false) Long assetId, @ApiParam(value = "to get the assetprice data starting from a particular date") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate, @ApiParam(value = "to get the assetprice data till a particular date") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            List<AssetPriceData> assetPriceDataList = null;

            if (assetId == null && startDate == null && endDate == null) {
                assetPriceDataList = assetPriceDataRepository.findByOrderByDateTimeAsc();
            } else if (assetId == null && startDate == null && endDate != null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByDateTimeBeforeOrderByDateTimeAsc(DateTimeConversion.toJavaOffsetDateTime(endDate));
            } else if (assetId == null && startDate != null && endDate == null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByDateTimeAfterOrderByDateTimeAsc(DateTimeConversion.toJavaOffsetDateTime(startDate));
            } else if (assetId == null && startDate != null && endDate != null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(DateTimeConversion.toJavaOffsetDateTime(startDate), DateTimeConversion.toJavaOffsetDateTime(endDate));
            } else if (assetId != null && startDate == null && endDate == null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByAsset_IdOrderByDateTimeAsc(assetId);
            } else if (assetId != null && startDate == null && endDate != null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByAsset_IdAndDateTimeBeforeOrderByDateTimeAsc(assetId, DateTimeConversion.toJavaOffsetDateTime(endDate));
            } else if (assetId != null && startDate != null && endDate == null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByAsset_IdAndDateTimeAfterOrderByDateTimeAsc(assetId, DateTimeConversion.toJavaOffsetDateTime(startDate));
            } else if (assetId != null && startDate != null && endDate != null) {
                assetPriceDataList = assetPriceDataRepository.findAssetPriceDatasByAsset_IdAndDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(assetId, DateTimeConversion.toJavaOffsetDateTime(startDate), DateTimeConversion.toJavaOffsetDateTime(endDate));
            }

            if (assetPriceDataList != null && !assetPriceDataList.isEmpty()) {
                List<AssetPriceDataApiModel> assetPriceDataApiModelList  = new ArrayList<AssetPriceDataApiModel>();
                for (AssetPriceData assetPriceData : assetPriceDataList) {
                    assetPriceDataApiModelList.add(assetPriceData.toApiModel());
                }
                return new ResponseEntity<List<AssetPriceDataApiModel>>(assetPriceDataApiModelList, HttpStatus.OK);

            } else {
                return new ResponseEntity<List<AssetPriceDataApiModel>>(HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<List<AssetPriceDataApiModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
