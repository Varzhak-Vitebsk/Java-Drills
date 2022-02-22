package java_drills.tasks.delivery.blank.service.dependencies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestOnDeliveryAddressDto {
  @JsonProperty("cityAndStreet")
  private String cityAndStreet;
  @JsonProperty("house")
  private String house;
  @JsonProperty("entrance")
  private String entrance;
  @JsonProperty("floor")
  private String floor;
  @JsonProperty("apartment")
  private String apartment;
  @JsonProperty("postcode")
  private String postcode;
  @JsonProperty("room")
  private String room;
  @JsonProperty("intercomCode")
  private String intercomCode;
  @JsonProperty("hasLift")
  private Boolean hasLift;
  @JsonProperty("subwayStation")
  private String subwayStation;
}
