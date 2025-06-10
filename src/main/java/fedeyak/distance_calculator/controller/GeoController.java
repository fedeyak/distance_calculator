package fedeyak.distance_calculator.controller;

import fedeyak.distance_calculator.model.*;
import fedeyak.distance_calculator.service.GeoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/geo")
public class GeoController {
    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @PostMapping("/add")
    public Point addAddress(@RequestBody AddressRequest request) {
        return geoService.geocode(request.getAddress());
    }

    @GetMapping("/all")
    public Map<String, Point> getAll() {
        return geoService.getAll();
    }
}
