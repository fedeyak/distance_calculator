package fedeyak.distance_calculator.service;

import fedeyak.distance_calculator.mapper.*;
import fedeyak.distance_calculator.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private GeoMapper geoMapper;

    @Mock
    private GeoUtils geoUtils;

    @InjectMocks
    private GeoService geoService;

    private final String testAddress = "Москва, Красная площадь";
    private final String testApiResponse = "{\"response\":{\"GeoObjectCollection\":{\"featureMember\":[{\"GeoObject\":{\"Point\":{\"pos\":\"37.6176 55.7558\"}}}]}}}";
    private final Point testPoint = new Point(55.7558, 37.6176);

    @Test
    void geocode_shouldReturnCachedValue() {
        geoService.getAll().put(testAddress, testPoint);
        Point result = geoService.geocode(testAddress);
        assertEquals(testPoint, result);
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    void geocode_shouldCallApiForNewAddress() throws Exception {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(testApiResponse);
        when(objectMapper.readValue(eq(testApiResponse), eq(GeoResponse.class))).thenReturn(new GeoResponse());
        when(geoMapper.geoResponseToPoint(any(), any())).thenReturn(testPoint);

        Point result = geoService.geocode(testAddress);

        assertEquals(testPoint, result);
        assertTrue(geoService.getAll().containsKey(testAddress));
        verify(restTemplate).getForObject(contains("geocode=" + testAddress), eq(String.class));
    }

    @Test
    void geocode_shouldThrowExceptionWhenApiFails() {
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenThrow(new RuntimeException("API error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            geoService.geocode(testAddress);
        });

        assertTrue(exception.getMessage().contains("Ошибка геокодирования"));
    }
}
