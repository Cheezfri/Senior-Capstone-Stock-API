/*
 * BackEndApi
 * This api is connects the middleware and backend of a portfolio management web application
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package edu.albany.icsi418.fa19.teamy.middleware.BackEndClient.model;

import java.util.Objects;
import java.util.Arrays;
import io.swagger.v3.oas.annotations.media.Schema;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * category of the asset
 */
@JsonAdapter(AssetCategory.Adapter.class)
public enum AssetCategory {
  STOCK("STOCK"),
  BOND("BOND"),
  CRYPTO("CRYPTO"),
  CURRENCY("CURRENCY");

  private String value;

  AssetCategory(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static AssetCategory fromValue(String text) {
    for (AssetCategory b : AssetCategory.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }

  public static class Adapter extends TypeAdapter<AssetCategory> {
    @Override
    public void write(final JsonWriter jsonWriter, final AssetCategory enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public AssetCategory read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return AssetCategory.fromValue(String.valueOf(value));
    }
  }
}
