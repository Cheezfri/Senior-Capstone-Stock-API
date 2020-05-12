package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioRights;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * How a portfolio is shared
 */
@ApiModel(description = "How a portfolio is shared")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class PortfolioShare   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("portfolioId")
  private Long portfolioId = null;

  @JsonProperty("sharedWithUserId")
  private Long sharedWithUserId = null;

  @JsonProperty("portfolioRights")
  private PortfolioRights portfolioRights = null;

  public PortfolioShare id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "123", value = "")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PortfolioShare portfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
    return this;
  }

  /**
   * Get portfolioId
   * @return portfolioId
  **/
  @ApiModelProperty(value = "")
  
    public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public PortfolioShare sharedWithUserId(Long sharedWithUserId) {
    this.sharedWithUserId = sharedWithUserId;
    return this;
  }

  /**
   * Get sharedWithUserId
   * @return sharedWithUserId
  **/
  @ApiModelProperty(example = "12345", value = "")
  
    public Long getSharedWithUserId() {
    return sharedWithUserId;
  }

  public void setSharedWithUserId(Long sharedWithUserId) {
    this.sharedWithUserId = sharedWithUserId;
  }

  public PortfolioShare portfolioRights(PortfolioRights portfolioRights) {
    this.portfolioRights = portfolioRights;
    return this;
  }

  /**
   * Get portfolioRights
   * @return portfolioRights
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public PortfolioRights getPortfolioRights() {
    return portfolioRights;
  }

  public void setPortfolioRights(PortfolioRights portfolioRights) {
    this.portfolioRights = portfolioRights;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortfolioShare portfolioShare = (PortfolioShare) o;
    return Objects.equals(this.id, portfolioShare.id) &&
        Objects.equals(this.portfolioId, portfolioShare.portfolioId) &&
        Objects.equals(this.sharedWithUserId, portfolioShare.sharedWithUserId) &&
        Objects.equals(this.portfolioRights, portfolioShare.portfolioRights);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, portfolioId, sharedWithUserId, portfolioRights);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortfolioShare {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    portfolioId: ").append(toIndentedString(portfolioId)).append("\n");
    sb.append("    sharedWithUserId: ").append(toIndentedString(sharedWithUserId)).append("\n");
    sb.append("    portfolioRights: ").append(toIndentedString(portfolioRights)).append("\n");
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
