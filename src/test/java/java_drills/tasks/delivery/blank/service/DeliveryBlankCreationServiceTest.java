package java_drills.tasks.delivery.blank.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java_drills.tasks.delivery.blank.service.dependencies.RequestOnDeliveryCloningMapperImpl;
import java_drills.tasks.delivery.blank.service.dependencies.model.RequestOnDeliveryDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.ResourceUtils;

import java_drills.tasks.delivery.blank.service.dependencies.RequestOnDeliveryCloningMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryBlankCreationServiceTest {

  private final RequestOnDeliveryCloningMapper mapper = new RequestOnDeliveryCloningMapperImpl();
  private final DeliveryBlankCreationService service = new DeliveryBlankCreationService(mapper);

  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @ParameterizedTest(name = "{2}")
  @MethodSource("prepareDataForCreateBlanksEqualToModel")
  void createBlanks_EqualToModel(RequestOnDeliveryDto source, List<RequestOnDeliveryDto> model, String name) {
    assertIterableEquals(model, service.createBlanks(source));
  }

  private static Stream<Arguments> prepareDataForCreateBlanksEqualToModel() throws IOException {
    return Stream.of(
            Arguments.of(loadResource("Request_on_delivery_several_items_same_warehouse.json"),
                    loadResourceCollection("Request_on_delivery_several_items_same_warehouse_model.json"),
                    "Several items same warehouse"),
            Arguments.of(loadResource("Request_on_delivery_several_items_different_warehouse.json"),
                    loadResourceCollection("Request_on_delivery_several_items_different_warehouse_model.json"),
                    "Several items different warehouse")
    );
  }

  private static RequestOnDeliveryDto loadResource(String name) throws IOException {
    return objectMapper.readValue(
            ResourceUtils.getFile("classpath:"+ name),
            RequestOnDeliveryDto.class);
  }

  private static List<RequestOnDeliveryDto> loadResourceCollection(String name) throws IOException {
    return objectMapper.readValue(
            ResourceUtils.getFile("classpath:"+ name),
            new TypeReference<>() {
            });
  }

}