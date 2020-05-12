package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.api;

import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiException;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.ApiResponse;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.api.DefaultApi;
import edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioTotalValue;
import org.threeten.bp.OffsetDateTime;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioAggregateData;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-07T21:36:00.664Z[GMT]")
@Controller
public class PortfolioAggregateforGraphApiController implements PortfolioAggregateforGraphApi {

    private static final Logger log = LoggerFactory.getLogger(PortfolioAggregateforGraphApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PortfolioAggregateforGraphApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<PortfolioAggregateData> portfolioAggregateforGraphGet(@ApiParam(value = "Id of the portfolio") @Valid @RequestParam(value = "portfolioId", required = false) Long portfolioId,@ApiParam(value = "The start date of the analytic calculation") @Valid @RequestParam(value = "startDate", required = false) OffsetDateTime startDate,@ApiParam(value = "The end date of the analytic calculation") @Valid @RequestParam(value = "endDate", required = false) OffsetDateTime endDate) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            log.info("PortfolioAggregateforGraphGet has ben called in the MW");
            if(portfolioId == null || portfolioId ==0){
                return new ResponseEntity<PortfolioAggregateData>(HttpStatus.BAD_REQUEST);
            }
            DefaultApi apiAccess  = new DefaultApi();

            //making hte call to the backend
            ApiResponse<edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model.PortfolioAggregateData> retrievedPortfolioAnalytics = null;

            try{
                retrievedPortfolioAnalytics = apiAccess.analyticPortfolioAggregateGetWithHttpInfo(portfolioId,startDate,endDate);
            } catch (ApiException ex){
                log.error("An error has occured trying to retrieve portfolio analytics form the BE using analytic portfolio aggregate get, errorstatuscode: "+ex.getCode());
                return new ResponseEntity<PortfolioAggregateData>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            PortfolioAggregateData returnPortfolioAnalytics = new PortfolioAggregateData();

            returnPortfolioAnalytics.setPortfolioId(retrievedPortfolioAnalytics.getData().getPortfolioId());
            returnPortfolioAnalytics.setName(retrievedPortfolioAnalytics.getData().getName());
            returnPortfolioAnalytics.setStartDate(retrievedPortfolioAnalytics.getData().getStartDate());
            returnPortfolioAnalytics.setEndDate(retrievedPortfolioAnalytics.getData().getEndDate());

            double i = 0;
            double twelveEMA = 0;
            double twentySixEMA = 0;
            double multiplier = 0;
            double simpleMovingAverage = 0;

            for(PortfolioTotalValue item: retrievedPortfolioAnalytics.getData().getPortfolioValueByDate()){
                double startPrice = retrievedPortfolioAnalytics.getData().getPortfolioValueByDate().get(0).getPortfolioValue();
                edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTotalValue toBeAdded = new edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTotalValue();
                toBeAdded.setPortfolioId(item.getPortfolioId());
                toBeAdded.setDate(item.getDate());
                toBeAdded.setPortfolioValue(item.getPortfolioValue());
                if(retrievedPortfolioAnalytics.getData().getPortfolioValueByDate().indexOf(item) != 0){
                    toBeAdded.setPercentIncrease(((item.getPortfolioValue()-startPrice)/startPrice)*100);
                } else {
                    toBeAdded.setPercentIncrease(0D);
                }

                //MACD code from this point on

                if(i<26){
                    simpleMovingAverage = simpleMovingAverage + item.getPortfolioValue();
                }

                if(i == 11){
                    twelveEMA = Math.floor((simpleMovingAverage / 12) * 1000000) / 1000000;
                }

                if(i>11){
                    multiplier = 2 / (i + 1);
                    twelveEMA = ((item.getPortfolioValue() - twelveEMA) * (multiplier)) + twelveEMA;
                }

                if(i == 25){
                    twentySixEMA = (Math.floor((simpleMovingAverage / 26) * 1000000) / 1000000);
                    toBeAdded.setMacdIndexValue(twelveEMA - twentySixEMA);
                }

                if(i > 25 ){
                    twentySixEMA = ((item.getPortfolioValue()- twentySixEMA) * (multiplier)) + twentySixEMA;
                    toBeAdded.setMacdIndexValue(twelveEMA - twentySixEMA);
                }
                i++;
            returnPortfolioAnalytics.addPortfolioValueByDateItem(toBeAdded);
            }

            log.info("AssetAggregateforGraphApiController has executed succesfully");
            return new ResponseEntity<PortfolioAggregateData>(returnPortfolioAnalytics,HttpStatus.OK);

        }
        return new ResponseEntity<PortfolioAggregateData>(HttpStatus.NOT_IMPLEMENTED);
    }

}
