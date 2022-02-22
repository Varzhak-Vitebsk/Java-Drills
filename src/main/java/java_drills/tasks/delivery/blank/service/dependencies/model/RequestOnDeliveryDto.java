package java_drills.tasks.delivery.blank.service.dependencies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestOnDeliveryDto {
    @JsonProperty("orderNumber")
    private Long orderNumber;
    @JsonProperty("barCodeNumber")
    private Long barCodeNumber;
    @JsonProperty("deliveryServicePhoneNumber")
    private String deliveryServicePhoneNumber;
    @JsonProperty("deliveryServiceHotLinePhoneNumber")
    private String deliveryServiceHotLinePhoneNumber;
    @JsonProperty("serviceRequestNumber")
    private Long serviceRequestNumber;
    @JsonProperty("serviceRequestCreationDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime serviceRequestCreationDate;
    @JsonProperty("creationObject")
    private String creationObject;
    @JsonProperty("recipient")
    private RecipientDto recipient;
    @JsonProperty("deliveryAddress")
    private RequestOnDeliveryAddressDto deliveryAddress;
    @JsonProperty("deliveryDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate deliveryDate;
    @JsonProperty("deliveryInterval")
    private String deliveryInterval;
    @JsonProperty("fullQuantity")
    private Integer fullQuantity;
    @JsonProperty("itemsFullPrice")
    private BigDecimal itemsFullPrice;
    @JsonProperty("itemsFullPriceText")
    private String itemsFullPriceText;
    @JsonProperty("employeeName")
    private String employeeName;
    @JsonProperty("company")
    private CompanyDto company;
    @JsonProperty("salesReceiptId")
    private String salesReceiptId;
    @JsonProperty("requestPositionList")
    @Valid
    private List<RequestPositionDto> requestPositionList = null;
}
