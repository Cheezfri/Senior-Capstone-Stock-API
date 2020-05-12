package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioTotalValue;
import java.util.ArrayList;
import java.util.List;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * The aggregate value of a portfolio from start to end date
 */
@ApiModel(description = "The aggregate value of a portfolio from start to end date")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-12-07T21:36:00.664Z[GMT]")
public class PortfolioAggregateData   {
  @JsonProperty("portfolioId")
  private Long portfolioId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("startDate")
  private OffsetDateTime startDate = null;

  @JsonProperty("endDate")
  private OffsetDateTime endDate = null;

  @JsonProperty("portfolioValueByDate")
  @Valid
  private List<PortfolioTotalValue> portfolioValueByDate = null;

  public PortfolioAggregateData portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

  /**
   * Unique Database Primary Key
   * @return portfolioId
  **/
  @ApiModelProperty(example = "1", value = "Unique Database Primary Key")
  
    public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public PortfolioAggregateData name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(example = "Portfolio A", value = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PortfolioAggregateData startDate(OffsetDateTime startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
  **/
  @ApiModelProperty(example = "1996-12-19T16:39:57-08:00", value = "")
  
    @Valid
    public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  public PortfolioAggregateData endDate(OffsetDateTime endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
  **/
  @ApiModelProperty(example = "1996-12-19T16:39:57-08:00", value = "")
  
    @Valid
    public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(OffsetDateTime endDate) {
    this.endDate = endDate;
  }

  public PortfolioAggregateData portfolioValueByDate(List<PortfolioTotalValue> portfolioValueByDate) {
    this.portfolioValueByDate = portfolioValueByDate;
    return this;
  }

  public PortfolioAggregateData addPortfolioValueByDateItem(PortfolioTotalValue portfolioValueByDateItem) {
    if (this.portfolioValueByDate == null) {
      this.portfolioValueByDate = new ArrayList<PortfolioTotalValue>();
    }
    this.portfolioValueByDate.add(portfolioValueByDateItem);
    return this;
  }

  /**
   * The array consists of the total portfolio value on each date from start to end
   * @return portfolioValueByDate
  **/
  @ApiModelProperty(value = "The array consists of the total portfolio value on each date from start to end")
      @Valid
    public List<PortfolioTotalValue> getPortfolioValueByDate() {
    return portfolioValueByDate;
  }

  public void setPortfolioValueByDate(List<PortfolioTotalValue> portfolioValueByDate) {
    this.portfolioValueByDate = portfolioValueByDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortfolioAggregateData portfolioAggregateData = (PortfolioAggregateData) o;
    return Objects.equals(this.portfolioId, portfolioAggregateData.portfolioId) &&
        Objects.equals(this.name, portfolioAggregateData.name) &&
        Objects.equals(this.startDate, portfolioAggregateData.startDate) &&
        Objects.equals(this.endDate, portfolioAggregateData.endDate) &&
        Objects.equals(this.portfolioValueByDate, portfolioAggregateData.portfolioValueByDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portfolioId, name, startDate, endDate, portfolioValueByDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortfolioAggregateData {\n");
    
    sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    portfolioValueByDate: ").append(toIndentedString(portfolioValueByDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
