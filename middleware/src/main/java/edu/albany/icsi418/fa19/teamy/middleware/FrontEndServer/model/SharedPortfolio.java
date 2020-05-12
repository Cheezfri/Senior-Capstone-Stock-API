package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.HoldingAsset;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.PortfolioRights;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Object that contains a portfolio model and a sharedwith user
 */
@ApiModel(description = "Object that contains a portfolio model and a sharedwith user")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class SharedPortfolio   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("deleted")
  private Boolean deleted = null;

  @JsonProperty("ownerUserId")
  private Long ownerUserId = null;

  @JsonProperty("ownerEmail")
  private String ownerEmail = null;

  @JsonProperty("sharedWithUserEmail")
  private String sharedWithUserEmail = null;

  @JsonProperty("portfolioRights")
  private PortfolioRights portfolioRights = null;

  public SharedPortfolio id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Unique Database Primary Key
   * @return id
  **/
  @ApiModelProperty(example = "1", value = "Unique Database Primary Key")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SharedPortfolio name(String name) {
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

  public SharedPortfolio deleted(Boolean deleted) {
    this.deleted = deleted;
    return this;
  }

  /**
   * Get deleted
   * @return deleted
  **/
  @ApiModelProperty(example = "false", value = "")
  
    public Boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public SharedPortfolio ownerUserId(Long ownerUserId) {
    this.ownerUserId = ownerUserId;
    return this;
  }

  /**
   * Get ownerUserId
   * @return ownerUserId
  **/
  @ApiModelProperty(example = "12345", value = "")
  
    public Long getOwnerUserId() {
    return ownerUserId;
  }

  public void setOwnerUserId(Long ownerUserId) {
    this.ownerUserId = ownerUserId;
  }

  public SharedPortfolio ownerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
    return this;
  }

  /**
   * Get ownerEmail
   * @return ownerEmail
  **/
  @ApiModelProperty(example = "Portfolio A", value = "")

    public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }

  public SharedPortfolio sharedWithUserEmail(String sharedWithUserEmail) {
    this.sharedWithUserEmail = sharedWithUserEmail;
    return this;
  }

  /**
   * Get sharedWithUserEmail
   * @return sharedWithUserEmail
  **/
  @ApiModelProperty(example = "sharedwithme@email.com", value = "")
  
    public String getSharedWithUserEmail() {
    return sharedWithUserEmail;
  }

  public void setSharedWithUserEmail(String sharedWithUserEmail) {
    this.sharedWithUserEmail = sharedWithUserEmail;
  }

  public SharedPortfolio portfolioRights(PortfolioRights portfolioRights) {
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
    SharedPortfolio sharedPortfolio = (SharedPortfolio) o;
    return Objects.equals(this.id, sharedPortfolio.id) &&
        Objects.equals(this.name, sharedPortfolio.name) &&
        Objects.equals(this.deleted, sharedPortfolio.deleted) &&
        Objects.equals(this.ownerUserId, sharedPortfolio.ownerUserId) &&
        Objects.equals(this.ownerEmail, sharedPortfolio.ownerEmail) &&
        Objects.equals(this.sharedWithUserEmail, sharedPortfolio.sharedWithUserEmail) &&
        Objects.equals(this.portfolioRights, sharedPortfolio.portfolioRights);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, deleted, ownerUserId, ownerEmail, sharedWithUserEmail, portfolioRights);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SharedPortfolio {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    deleted: ").append(toIndentedString(deleted)).append("\n");
    sb.append("    ownerUserId: ").append(toIndentedString(ownerUserId)).append("\n");
    sb.append("    ownerEmail: ").append(toIndentedString(ownerEmail)).append("\n");
    sb.append("    sharedWithUserEmail: ").append(toIndentedString(sharedWithUserEmail)).append("\n");
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
