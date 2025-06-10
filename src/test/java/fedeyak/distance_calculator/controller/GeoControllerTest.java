package fedeyak.distance_calculator.controller;

import fedeyak.distance_calculator.model.AddressRequest;
import fedeyak.distance_calculator.model.Point;
import fedeyak.distance_calculator.service.GeoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoControllerTest {

    @Mock
    private GeoService geoService;

    @InjectMocks
    private GeoController geoController;

    @Test
    void addAddress_shouldCallService() {
        AddressRequest request = new AddressRequest();
        request.setAddress("Москва");

        Point expectedPoint = new Point(55.7558, 37.6176);
        when(geoService.geocode("Москва")).thenReturn(expectedPoint);

        Point result = geoController.addAddress(request);

        assertEquals(expectedPoint, result);
        verify(geoService).geocode("Москва");
    }

    @Test
    void getAll_shouldReturnServiceData() {
        Map<String, Point> expectedMap = Map.of(
                "Москва", new Point(55.7558, 37.6176),
                "Санкт-Петербург", new Point(59.9343, 30.3351)
        );

        when(geoService.getAll()).thenReturn(expectedMap);

        Map<String, Point> result = geoController.getAll();

        assertEquals(expectedMap, result);
    }
}