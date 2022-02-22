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
public class CompanyDto {
  @JsonProperty("shortName")
  private String shortName;
  @JsonProperty("inn")
  private String inn;
  @JsonProperty("kpp")
  private String kpp;
  @JsonProperty("address")
  private String address;
  @JsonProperty("phone")
  private String phone;
  @JsonProperty("carrierName")
  private String carrierName;
}
