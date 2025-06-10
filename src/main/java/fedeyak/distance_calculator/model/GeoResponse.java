package fedeyak.distance_calculator.model;


import com.fasterxml.jackson.annotation.*;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponse {
    @JsonProperty("response")
    private Response response;

    public Response getResponse() { return response; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty("GeoObjectCollection")
        private GeoObjectCollection geoObjectCollection;

        public GeoObjectCollection getGeoObjectCollection() {
            return geoObjectCollection;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoObjectCollection {
        @JsonProperty("metaDataProperty")
        private Object metaDataProperty;

        @JsonProperty("featureMember")
        private List<FeatureMember> featureMember;

        public List<FeatureMember> getFeatureMember() {
            return featureMember;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeatureMember {
        @JsonProperty("GeoObject")
        private GeoObject geoObject;

        public GeoObject getGeoObject() {
            return geoObject;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoObject {
        @JsonProperty("metaDataProperty")
        private Object metaDataProperty;

        @JsonProperty("Point")
        private Point point;

        public Point getPoint() {
            return point;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Point {
        @JsonProperty("pos")
        private String pos;

        public String getPos() {
            return pos;
        }
    }
}
//    private Response response;
//    public Response getResponse() { return response; }
//
//    public static class Response {
//        private GeoObjectCollection GeoObjectCollection;
//        public GeoObjectCollection getGeoObjectCollection() { return GeoObjectCollection; }
//    }
//
//    public static class GeoObjectCollection {
//        private List<FeatureMember> featureMember;
//        public List<FeatureMember> getFeatureMember() { return featureMember; }
//    }
//
//    public static class FeatureMember {
//        private GeoObject GeoObject;
//        public GeoObject getGeoObject() { return GeoObject; }
//    }
//
//    public static class GeoObject {
//        private Point Point;
//        public Point getPoint() { return Point; }
//    }
//
//    public static class Point {
//        private String pos;
//        public String getPos() { return pos; }
//    }
//}
