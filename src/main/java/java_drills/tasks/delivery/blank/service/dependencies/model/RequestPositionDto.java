package java_drills.tasks.delivery.blank.service.dependencies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPositionDto {
  @JsonProperty("servicePositionId")
  private String servicePositionId;
  @JsonProperty("requestPositionId")
  private String requestPositionId;
  @JsonProperty("material")
  private Long material;
  @JsonProperty("materialName")
  private String materialName;
  @JsonProperty("reserveObject")
  private String reserveObject;
  @JsonProperty("quantity")
  private Integer quantity;
  @JsonProperty("price")
  private java.math.BigDecimal price;
  @JsonProperty("items")
  @Valid
  private List<ItemDto> items = null;
}
