package java_drills.tasks.delivery.blank.service;

import com.ibm.icu.text.RuleBasedNumberFormat;
import java_drills.tasks.delivery.blank.service.dependencies.RequestOnDeliveryCloningMapper;
import java_drills.tasks.delivery.blank.service.dependencies.model.ItemDto;
import java_drills.tasks.delivery.blank.service.dependencies.model.RequestOnDeliveryDto;
import java_drills.tasks.delivery.blank.service.dependencies.model.RequestPositionDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryBlankCreationService {

  private final RequestOnDeliveryCloningMapper requestOnDeliveryCloningMapper;

  private static final RuleBasedNumberFormat RULE_NUMBER_FORMAT = new RuleBasedNumberFormat(Locale.forLanguageTag("ru"),
      RuleBasedNumberFormat.SPELLOUT);
  private static final String NAME_CURRENCY = " рублей, ";
  private static final String NAME_PENNY = " копеек";
  private static final Integer FRACTIONAL_PART = 100;

  public List<RequestOnDeliveryDto> createBlanks(RequestOnDeliveryDto requestOnDelivery) {
    return splitByWarehouse(requestOnDelivery).values().stream()
        .map(requestPositions -> {
          var fullPrice = calculatedFullPrice(requestPositions);
          var newBlank = requestOnDeliveryCloningMapper.cloneWithoutRequestPositions(requestOnDelivery);
          newBlank.setFullQuantity(calculateFullQuantity(requestPositions));
          newBlank.setItemsFullPrice(fullPrice);
          newBlank.setItemsFullPriceText(convertPriseToString(fullPrice));
          newBlank.setRequestPositionList(requestPositions);
          return newBlank;
        })
        .collect(Collectors.toList());
  }

  private Map<String, List<RequestPositionDto>> splitByWarehouse(RequestOnDeliveryDto requestOnDeliveryDto) {
    return requestOnDeliveryDto.getRequestPositionList().stream()
        .collect(Collectors.toMap(
            RequestPositionDto::getReserveObject,
            requestPosition -> {
              var servicePositionId = "1";
              requestPosition.setServicePositionId(servicePositionId);

              var items = requestPosition.getItems();
              var resultList = new ArrayList<RequestPositionDto>();
              if (CollectionUtils.isNotEmpty(items)) {
                resultList.add(requestPosition);
                resultList.addAll(convertItemsToRequestPositions(servicePositionId, items, requestPosition.getReserveObject()));
                return resultList;
              }

              resultList.add(requestPosition);
              return resultList;
            },
            (list1, list2) -> {
              var lastServicePositionId = list1.stream()
                  .map(RequestPositionDto::getServicePositionId)
                  .mapToInt(Integer::parseInt)
                  .max()
                  .orElse(0);

              var requestPosition = list2.get(0);
              var nextServicePositionId = lastServicePositionId + 1;
              requestPosition.setServicePositionId(String.valueOf(nextServicePositionId));

              var items = requestPosition.getItems();
              if (CollectionUtils.isNotEmpty(items)) {
                list1.add(requestPosition);
                list1.addAll(convertItemsToRequestPositions(String.valueOf(nextServicePositionId), items, requestPosition.getReserveObject()));

                return list1;
              }

              list1.add(requestPosition);
              return list1;
            }

        ));
  }

  private List<RequestPositionDto> convertItemsToRequestPositions(String firstPartOfServicePositionId,
      List<ItemDto> items,
      String reserveObject) {
    int counter = 1;
    var resultList = new ArrayList<RequestPositionDto>(items.size());
    for (ItemDto item : items) {
      var requestPositionDto = RequestPositionDto.builder()
          .servicePositionId(firstPartOfServicePositionId + "." + counter++)
          .material(item.getMaterial())
          .materialName(item.getMaterialName())
          .reserveObject(reserveObject)
          .quantity(item.getQuantity())
          .build();

      resultList.add(requestPositionDto);
    }

    return resultList;
  }

  private BigDecimal calculatedFullPrice(List<RequestPositionDto> requestPositionList) {
    return requestPositionList
        .stream()
        .map(RequestPositionDto::getPrice)
        .filter(Objects::nonNull)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private Integer calculateFullQuantity(List<RequestPositionDto> requestPositionList) {
    int result = 0;
    for (var requestPosition : requestPositionList) {
      if (CollectionUtils.isEmpty(requestPosition.getItems())) {
        result += requestPosition.getPrice() == null
            ? 0
            : requestPosition.getQuantity();
      } else {
        result += requestPosition.getItems().stream()
            .map(ItemDto::getQuantity)
            .filter(Objects::nonNull)
            .mapToInt(q -> q)
            .sum();
      }
    }

    return result;
  }

  private String convertPriseToString(BigDecimal fullPrice) {
    var kop = fullPrice.subtract(new BigDecimal(fullPrice.toBigInteger()));
    var multiply = kop.multiply(BigDecimal.valueOf(FRACTIONAL_PART));
    var rub = fullPrice.subtract(kop);
    var result = RULE_NUMBER_FORMAT.format(rub);
    return result + NAME_CURRENCY + multiply.intValue() + NAME_PENNY;
  }
}