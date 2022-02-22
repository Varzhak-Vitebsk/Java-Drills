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
public class ItemDto {
  @JsonProperty("servicePositionId")
  private String servicePositionId;
  @JsonProperty("material")
  private Long material;
  @JsonProperty("materialName")
  private String materialName;
  @JsonProperty("quantity")
  private Integer quantity;
}
