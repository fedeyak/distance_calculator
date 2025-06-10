package fedeyak.distance_calculator.mapper;


import org.mapstruct.*;

import fedeyak.distance_calculator.model.*;

@Mapper(componentModel = "spring", uses = GeoUtils.class)
public interface GeoMapper {

    @Mapping(target = "latitude", expression = "java(geoUtils.extractLatitude(response))")
    @Mapping(target = "longitude", expression = "java(geoUtils.extractLongitude(response))")
    Point geoResponseToPoint(GeoResponse response, @Context GeoUtils geoUtils);
}