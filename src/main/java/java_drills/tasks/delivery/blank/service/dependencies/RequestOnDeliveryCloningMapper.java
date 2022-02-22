package java_drills.tasks.delivery.blank.service.dependencies;

import java_drills.tasks.delivery.blank.service.dependencies.model.RequestOnDeliveryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.DeepClone;

@Mapper(mappingControl = DeepClone.class, config = MapStructConfig.class)
public interface RequestOnDeliveryCloningMapper {

  @Mapping(target = "requestPositionList", ignore = true)
  RequestOnDeliveryDto cloneWithoutRequestPositions(RequestOnDeliveryDto requestOnDelivery);
}
