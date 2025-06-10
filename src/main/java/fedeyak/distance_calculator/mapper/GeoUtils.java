package fedeyak.distance_calculator.mapper;

import fedeyak.distance_calculator.model.GeoResponse;
import org.springframework.stereotype.Component;

@Component
public class GeoUtils {
    public double extractLatitude(GeoResponse response) {
        if (response == null || response.getResponse() == null ||
                response.getResponse().getGeoObjectCollection() == null ||
                response.getResponse().getGeoObjectCollection().getFeatureMember() == null ||
                response.getResponse().getGeoObjectCollection().getFeatureMember().isEmpty()) {
            throw new RuntimeException("Не удалось получить координаты из ответа");
        }

        String pos = response.getResponse()
                .getGeoObjectCollection()
                .getFeatureMember().get(0)
                .getGeoObject()
                .getPoint()
                .getPos();
        return Double.parseDouble(pos.split(" ")[1]);
    }

    public double extractLongitude(GeoResponse response) {
        String pos = response.getResponse()
                .getGeoObjectCollection()
                .getFeatureMember().get(0)
                .getGeoObject()
                .getPoint()
                .getPos();
        return Double.parseDouble(pos.split(" ")[0]);
    }
}
