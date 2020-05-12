package edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import edu.albany.icsi418.fa19.teamy.middleware.FrontEndServer.model.AccessRole;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Access levels of the users
 */
@ApiModel(description = "Access levels of the users")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-11-16T01:20:46.908Z[GMT]")
public class AccessLevel   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("role")
  private AccessRole role = null;

  @JsonProperty("name")
  private String name = null;

  public AccessLevel id(Long id) {
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

  public AccessLevel role(AccessRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public AccessRole getRole() {
    return role;
  }

  public void setRole(AccessRole role) {
    this.role = role;
  }

  public AccessLevel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Plain language name of the role
   * @return name
  **/
  @ApiModelProperty(example = "Administrator", value = "Plain language name of the role")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AccessLevel accessLevel = (AccessLevel) o;
    return Objects.equals(this.id, accessLevel.id) &&
        Objects.equals(this.role, accessLevel.role) &&
        Objects.equals(this.name, accessLevel.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, role, name);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AccessLevel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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
