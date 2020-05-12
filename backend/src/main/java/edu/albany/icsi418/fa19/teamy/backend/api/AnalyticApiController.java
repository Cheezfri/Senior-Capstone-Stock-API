package edu.albany.icsi418.fa19.teamy.backend.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.albany.icsi418.fa19.teamy.backend.api.converters.LibraryOffsetDateTimeToNativeConverter;
import edu.albany.icsi418.fa19.teamy.backend.api.converters.StringToOffsetDateTimeConverter;
import edu.albany.icsi418.fa19.teamy.backend.models.analytics.AssetAggregateData;
import edu.albany.icsi418.fa19.teamy.backend.models.analytics.PortfolioAggregateData;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.Asset;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceData;
import edu.albany.icsi418.fa19.teamy.backend.models.asset.AssetPriceDataApiModel;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.Portfolio;
import edu.albany.icsi418.fa19.teamy.backend.models.portfolio.PortfolioTransaction;
import edu.albany.icsi418.fa19.teamy.backend.respositories.AssetPriceDataRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioRepository;
import edu.albany.icsi418.fa19.teamy.backend.respositories.PortfolioTransactionRepository;
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
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-03T03:07:32.449Z[GMT]")
@Controller
public class AnalyticApiController implements AnalyticApi {

    private static final Logger log = LoggerFactory.getLogger(AnalyticApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    PortfolioTransactionRepository portfolioTransactionRepository;

    @Autowired
    AssetPriceDataRepository assetPriceDataRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public AnalyticApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<AssetAggregateData> analyticAssetAggregateGet(@ApiParam(value = "Id of the portfolio") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId, @ApiParam(value = "The start date of the analytic calculation") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate, @ApiParam(value = "The end date of the analytic calculation") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");

        AssetAggregateData assetAggregateData = new AssetAggregateData();
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);
        if (portfolio == null) {
            log.error("Portfolio " + portfolioId + " not found.");
            return new ResponseEntity<AssetAggregateData>(HttpStatus.NOT_FOUND);
        }

        assetAggregateData.setName(portfolio.getName());
        assetAggregateData.setPortfolioId(portfolioId);

        // General process. Obtain List<Txns> for this portfolio.
        // Establish a List<Asset> and Hashmap of <AssetId, List<AssetPriceData>>
        // Walk that list cronologically from txn[i] to (i-1).
        // At each txn[i], that date (incl) to the date of txn[i+1] (excl)
        // pull all the AssetData for the date range into the hashmaps & ensure each
        // Asset is in List<Asset>. Note date of txn[0] for start date, and last date
        // for AssetPriceData as endDate

        List<PortfolioTransaction> txns = portfolioTransactionRepository.findPortfolioTransactionsByPortfolio_Id(portfolioId);
        Collections.sort(txns);

        if (txns == null || txns.size() == 0) { // Handle no txn case
            assetAggregateData.setStartDate(startDate);
            assetAggregateData.setEndDate(endDate);
            return new ResponseEntity<>(assetAggregateData, HttpStatus.OK);
        }

        StringToOffsetDateTimeConverter converter = new StringToOffsetDateTimeConverter();
        LibraryOffsetDateTimeToNativeConverter libConverter = new LibraryOffsetDateTimeToNativeConverter();

        List<Asset> assets = new ArrayList<>();
        HashMap<Asset, Double> holdings = new HashMap<>();
        HashMap<String, List<AssetPriceDataApiModel>> data = new HashMap<>();

        // We can make the assumption that any Asset in a Txn will be getting data as we will have held it for
        // some non-zero amount of time.

        txns.forEach(txn -> {
            if (!assets.contains(txn.getAsset())) {
                assets.add(txn.getAsset());
            }
        });

        assetAggregateData.setAssetsInPortfolio(assets);

        OffsetDateTime firstStartDate = converter.convert(txns.get(0).getDateTime().toString());
        if (startDate != null) {
            assetAggregateData.setStartDate(startDate.toEpochSecond() > firstStartDate.toEpochSecond()
                    ? startDate : firstStartDate);
        } else {
            assetAggregateData.setStartDate(firstStartDate);
        }

        if (txns.size() == 1) {
            // Only 1 txn, process from startDate to endDate
            holdings.put(txns.get(0).getAsset(), txns.get(0).getQuantity());

            List<AssetPriceData> priceData = assetPriceDataRepository.
                    findAssetPriceDatasByAsset_IdAndDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(
                            txns.get(0).getAsset().getId(), libConverter.convert(assetAggregateData.getStartDate()),
                            libConverter.convert(assetAggregateData.getEndDate()));
            data.put(Long.toString(txns.get(0).getAsset().getId()), priceData
                    .stream().map(assetPriceData -> assetPriceData.toApiModel()).collect(Collectors.toList()));

            assetAggregateData.setAssetValueHashMap(data);

            if (priceData.size() > 0) {
                assetAggregateData.setEndDate(converter.convert(priceData.get(priceData.size() - 1).getDateTime().toString()));
            } else {
                assetAggregateData.setEndDate(endDate); // May be null here if no data & no end date provided....
            }

            return new ResponseEntity<>(assetAggregateData, HttpStatus.OK);
        }

        for (int i = 0; i < txns.size(); ++i) {
            PortfolioTransaction txni = txns.get(i); // Save typing later
            PortfolioTransaction txni1 = null; //Handle the last txn by not assuming this will be valid

            if (i < txns.size() - 1) { // If not last txn, set this.
                txni1 = txns.get(i + 1);
            }

            java.time.OffsetDateTime thisStartDate = txni.getDateTime();
            java.time.OffsetDateTime thisEndDate = null;

            if (txni1 != null) { // If not last txn
                thisEndDate = txni1.getDateTime().minus(Duration.ofDays(1));
            } else { // if last txn
                if (endDate != null) { // .. and we are given an end date
                    thisEndDate = libConverter.convert(endDate);
                } else { // if not, now is the end.
                    thisEndDate = java.time.OffsetDateTime.now();
                }
            }

            if (endDate != null && thisStartDate.compareTo(libConverter.convert(endDate)) > 0) {
                // If this txn starts after the specified end date, bail
                break;
            }

            if (endDate != null && thisEndDate.compareTo(libConverter.convert(endDate)) > 0) {
                // If we have an end date and this txn ends after the end date of the request
                thisEndDate = libConverter.convert(endDate);
            }

            if (holdings.containsKey(txni.getAsset())) {
                double newQty = holdings.get(txni.getAsset()).doubleValue() + txni.getQuantity();
                if (newQty != 0d) {
                    holdings.replace(txni.getAsset(), newQty);
                } else {
                    holdings.remove(txni.getAsset());
                }
            } else {
                holdings.put(txni.getAsset(), txni.getQuantity());
            }


            for (Asset asset : holdings.keySet()) {
                List<AssetPriceData> priceData = assetPriceDataRepository.
                        findAssetPriceDatasByAsset_IdAndDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(
                                asset.getId(), thisStartDate, thisEndDate);
                if (data.containsKey(asset.getId())) {
                    data.get(asset.getId()).addAll(priceData
                            .stream().map(assetPriceData -> assetPriceData.toApiModel()).collect(Collectors.toList()));
                } else {
                    data.put(Long.toString(asset.getId()), priceData
                            .stream().map(assetPriceData -> assetPriceData.toApiModel()).collect(Collectors.toList()));
                }

                // Little hack - assetpricedata should always be ordered most recent last...
                if (endDate == null && priceData.size() > 0
                        && (assetAggregateData.getEndDate() == null || assetAggregateData.getEndDate().compareTo(
                        converter.convert(priceData.get(priceData.size() - 1).getDateTime().toString())) < 0)) {
                    assetAggregateData.setEndDate(converter.convert(
                            priceData.get(priceData.size() - 1).getDateTime().toString()));
                }
            }
        }

        assetAggregateData.setAssetValueHashMap(data);
        if (endDate != null) {
            assetAggregateData.setEndDate(endDate);
        }

        return new ResponseEntity<AssetAggregateData>(assetAggregateData, HttpStatus.OK);

    }

    public ResponseEntity<PortfolioAggregateData> analyticPortfolioAggregateGet(@ApiParam(value = "Id of the portfolio") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId,@ApiParam(value = "The start date of the analytic calculation") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate,@ApiParam(value = "The end date of the analytic calculation") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");

        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElse(null);
        if (portfolio == null) {
            log.error("Portfolio " + portfolioId + " not found.");
            return new ResponseEntity<PortfolioAggregateData>(HttpStatus.NOT_FOUND);
        }

        AssetAggregateData assetAggregateData = analyticAssetAggregateGet(portfolioId, startDate, endDate).getBody();

        PortfolioAggregateData portfolioAggregateData = new PortfolioAggregateData();
        portfolioAggregateData.setPortfolioId(portfolioId);
        portfolioAggregateData.setName(portfolio.getName());

        // General process. Obtain List<Txns> for this portfolio.
        // Establish a List<Asset> and Hashmap of <AssetId, List<AssetPriceData>>
        // Walk that list cronologically from txn[i] to (i-1).
        // At each txn[i], that date (incl) to the date of txn[i+1] (excl)
        // pull all the AssetData for the date range into the hashmaps & ensure each
        // Asset is in List<Asset>. Note date of txn[0] for start date, and last date
        // for AssetPriceData as endDate

        List<PortfolioTransaction> txns = portfolioTransactionRepository.findPortfolioTransactionsByPortfolio_Id(portfolioId);
        Collections.sort(txns);

        if (txns == null || txns.size() == 0) { // Handle no txn case
            portfolioAggregateData.setStartDate(startDate);
            portfolioAggregateData.setEndDate(endDate);
            return new ResponseEntity<PortfolioAggregateData>(portfolioAggregateData, HttpStatus.OK);
        }

        StringToOffsetDateTimeConverter converter = new StringToOffsetDateTimeConverter();
        LibraryOffsetDateTimeToNativeConverter libConverter = new LibraryOffsetDateTimeToNativeConverter();

        List<Asset> assets = assetAggregateData.getAssetsInPortfolio();
        HashMap<Asset, Double> holdings = new HashMap<>();
        Map<String, List<AssetPriceDataApiModel>> data = assetAggregateData.getAssetValueHashMap();

        OffsetDateTime firstStartDate = converter.convert(txns.get(0).getDateTime().toString());
        if (startDate != null) {
            portfolioAggregateData.setStartDate(startDate.toEpochSecond() > firstStartDate.toEpochSecond()
                    ? startDate : firstStartDate);
        } else {
            portfolioAggregateData.setStartDate(firstStartDate);
        }

        // TODO: From here
        if (txns.size() == 1) {
            // Only 1 txn, process from startDate to endDate
            holdings.put(txns.get(0).getAsset(), txns.get(0).getQuantity());

            List<AssetPriceData> priceData = assetPriceDataRepository.
                    findAssetPriceDatasByAsset_IdAndDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(
                            txns.get(0).getAsset().getId(), libConverter.convert(assetAggregateData.getStartDate()),
                            libConverter.convert(assetAggregateData.getEndDate()));
            data.put(Long.toString(txns.get(0).getAsset().getId()), priceData
                    .stream().map(assetPriceData -> assetPriceData.toApiModel()).collect(Collectors.toList()));

            assetAggregateData.setAssetValueHashMap(data);

            if (priceData.size() > 0) {
                assetAggregateData.setEndDate(converter.convert(priceData.get(priceData.size() - 1).getDateTime().toString()));
            } else {
                assetAggregateData.setEndDate(endDate); // May be null here if no data & no end date provided....
            }

            return new ResponseEntity<>(portfolioAggregateData, HttpStatus.OK);
        }

        for (int i = 0; i < txns.size(); ++i) {
            PortfolioTransaction txni = txns.get(i); // Save typing later
            PortfolioTransaction txni1 = null; //Handle the last txn by not assuming this will be valid

            if (i < txns.size() - 1) { // If not last txn, set this.
                txni1 = txns.get(i + 1);
            }

            java.time.OffsetDateTime thisStartDate = txni.getDateTime();
            java.time.OffsetDateTime thisEndDate = null;

            if (txni1 != null) { // If not last txn
                thisEndDate = txni1.getDateTime().minus(Duration.ofDays(1));
            } else { // if last txn
                if (endDate != null) { // .. and we are given an end date
                    thisEndDate = libConverter.convert(endDate);
                } else { // if not, now is the end.
                    thisEndDate = java.time.OffsetDateTime.now();
                }
            }

            if (endDate != null && thisStartDate.compareTo(libConverter.convert(endDate)) > 0) {
                // If this txn starts after the specified end date, bail
                break;
            }

            if (endDate != null && thisEndDate.compareTo(libConverter.convert(endDate)) > 0) {
                // If we have an end date and this txn ends after the end date of the request
                thisEndDate = libConverter.convert(endDate);
            }

            if (holdings.containsKey(txni.getAsset())) {
                double newQty = holdings.get(txni.getAsset()).doubleValue() + txni.getQuantity();
                if (newQty != 0d) {
                    holdings.replace(txni.getAsset(), newQty);
                } else {
                    holdings.remove(txni.getAsset());
                }
            } else {
                holdings.put(txni.getAsset(), txni.getQuantity());
            }


            for (Asset asset : holdings.keySet()) {
                List<AssetPriceData> priceData = assetPriceDataRepository.
                        findAssetPriceDatasByAsset_IdAndDateTimeAfterAndDateTimeBeforeOrderByDateTimeAsc(
                                asset.getId(), thisStartDate, thisEndDate);
                if (data.containsKey(asset.getId())) {
                    data.get(asset.getId()).addAll(priceData
                            .stream().map(assetPriceData -> assetPriceData.toApiModel()).collect(Collectors.toList()));
                } else {
                    data.put(Long.toString(asset.getId()), priceData
                            .stream().map(assetPriceData -> assetPriceData.toApiModel()).collect(Collectors.toList()));
                }

                // Little hack - assetpricedata should always be ordered most recent last...
                if (endDate == null && priceData.size() > 0
                        && (assetAggregateData.getEndDate() == null || assetAggregateData.getEndDate().compareTo(
                        converter.convert(priceData.get(priceData.size() - 1).getDateTime().toString())) < 0)) {
                    assetAggregateData.setEndDate(converter.convert(
                            priceData.get(priceData.size() - 1).getDateTime().toString()));
                }
            }
        }

        assetAggregateData.setAssetValueHashMap(data);
        if (endDate != null) {
            assetAggregateData.setEndDate(endDate);
        }

        return new ResponseEntity<PortfolioAggregateData>(portfolioAggregateData, HttpStatus.OK);

    }

}
