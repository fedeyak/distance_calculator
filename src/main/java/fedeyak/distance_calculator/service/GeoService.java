package fedeyak.distance_calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fedeyak.distance_calculator.mapper.*;
import fedeyak.distance_calculator.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeoService {

    private static final String API_KEY = "043f584e-7b48-49ea-934c-7ddb572e7dbd";
    private final Map<String, Point> storage = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GeoMapper geoMapper;
    private final GeoUtils geoUtils;

    public GeoService(GeoMapper geoMapper, GeoUtils geoUtils) {
        this.geoMapper = geoMapper;
        this.geoUtils = geoUtils;
    }

    public Point geocode(String address) {
        if (storage.containsKey(address)) {
            return storage.get(address);
        }

        String url = UriComponentsBuilder.fromHttpUrl("https://geocode-maps.yandex.ru/1.x/")
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", address)
                .queryParam("format", "json")
                .toUriString();

        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            System.out.println("Raw response from Yandex API:");
            System.out.println(rawResponse);

            String json = restTemplate.getForObject(url, String.class);
            GeoResponse response = objectMapper.readValue(json, GeoResponse.class);
            Point point = geoMapper.geoResponseToPoint(response, geoUtils);
            storage.put(address, point);
            return point;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка геокодирования", e);
        }
    }

    public Map<String, Point> getAll() {
        return storage;
    }
}
