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
public class RecipientDto {
  @JsonProperty("fullName")
  private String fullName;
  @JsonProperty("primaryPhone")
  private String primaryPhone;
  @JsonProperty("secondaryPhone")
  private String secondaryPhone;
}
