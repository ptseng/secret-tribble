import groovy.json.JsonSlurper

class WorldCities extends DataGen {

    static class Coordinates
    {
        float lat
        float lon

        public boolean isEqual(float lat_in, float lon_in)
        {
            return (lat_in == lat && lon_in == lon)
        }

        public boolean isNear(float lat_in, float lon_in, float distance)
        {
            return coordDistance(lat, lon, lat_in, lon_in) < distance
        }
    }

    static double coordDistance(float latitude1, float longitude1, float latitude2, float longitude2)
    {
        latitude1 = Math.toRadians(latitude1)
        longitude1 = Math.toRadians(longitude1)
        latitude2 = Math.toRadians(latitude2)
        longitude2 = Math.toRadians(longitude2)

        return 6371 * Math.acos(
                Math.sin(latitude1) * Math.sin(latitude2)
                        + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude2 - longitude1))
    }

    public static Object importWorldCities()
    {
        String jsonp = new File("cities.jsonp").text
        String json = jsonp.substring(jsonp.indexOf("(") + 1, jsonp.lastIndexOf(")"))
        def cities = new JsonSlurper().parseText(json)

        Map<Coordinates, String> coordsCity = [:]

        cities.each { k, v ->
            coordsCity.put(new Coordinates(lat:v.lat, lon:v.lon), v.city)
        }

        return coordsCity
    }


    public static void main(String [] args)
    {
        timeit("Preparing Cities...",1,1,0)
        {
            importWorldCities() as HashMap<Coordinates, String>
        }

        def cities =  importWorldCities() as HashMap<Coordinates, String>

        timeit("Finding Portland...",1,1,0)
        {
            cities.each {k, v ->
                if (k.isEqual(45.517F,-122.667F))
                    println "Found " + v
            }
        }

        timeit("Finding Cities Within 2000km of Portland...",1,1,0)
        {
            cities.each {k, v ->
                if (k.isNear(45.517F,-122.667F, 2000.0F))
                    println "Miles " + coordDistance(45.517F,-122.667F, k.lat, k.lon).round(1) + " from PDX: " + v
            }
        }

        assert cities.size() == 661
        assert cities.containsValue("Portland")
        assert cities.containsValue("London")
        assert cities.containsValue("Beijing")

        //The distance between Portland and San Francisco is approximately 860km
        assert coordDistance(45.517F, -122.667F, 37.783333F, -122.416667F).round(2) == 860.19
    }
}
