import groovy.json.JsonSlurper

class WorldCities extends DataGen {

    static class Coordinates<K extends Comparable<K>, J extends Comparable<J>>
            implements Comparable<Coordinates<K, J>>
    {
        K lat
        J lon

        public K returnLat()
        {
            return lat
        }

        public J returnLon()
        {
            return lon
        }

        public boolean equals(Coordinates<K,J> that)
        {
            return this.compareTo(that) == 0
        }

        public boolean isEqual(float lat_in, float lon_in)
        {
            return lat_in == (float) lat && lon_in == (float) lon
        }

        public boolean isNear(float lat_in, float lon_in, float distance)
        {
            return coordDistance(lat, lon, lat_in, lon_in) < distance
        }

        public int compareTo(Coordinates<K,J> that) {

            int cmp = this.lat.compareTo(that.lat);

            if (cmp == 0)
            {
                cmp = this.lon.compareTo(that.lon);
            }

            return cmp;
        }
    }

    static double coordDistance(Float latitude1, Float longitude1, Float latitude2, Float longitude2)
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

        // Whatever you do, do not put a primitive type into Coordinates (like float)
        // Equality comparisons with two primitive floats do not work so well
        cities.each { k, v ->
            coordsCity.put(new Coordinates(lat:(Float)v.lat, lon:(Float)v.lon), (String)v.city)
        }

        return coordsCity
    }


    public static void main(String [] args)
    {
        //Test Coordinates Class
        def testcord1 = new Coordinates(lat:1.0F, lon:2.0F)
        def testcord2 = new Coordinates(lat:1.00, lon:2.0000)
        def testcord3 = new Coordinates(lat:3.0F, lon:4.0F)
        def testcord4 = new Coordinates(lat:3.0F, lon:1.0F)

        assert testcord1 == testcord2
        assert testcord1 != testcord3
        assert testcord1.compareTo(testcord2) == 0
        assert testcord1.compareTo(testcord3) == -1
        assert testcord3.compareTo(testcord4) == 1

        timeit("Preparing Cities...",1,1,0)
        {
            importWorldCities()
        }

        def cities =  importWorldCities() as HashMap<Coordinates, String>

        timeit("Inserting Cities into SimpleBinaryTree")
        {
            def citiesbst = new SimpleBinaryTree<Coordinates, String>()
            cities.each {k,v ->
                citiesbst.insert((Coordinates) k, (String) v)
            }

            assert citiesbst.size() == 661
            assert citiesbst.maxDepth() == 19
            assert citiesbst.minDepth() == 3
            assert citiesbst.returnValues().contains("Portland")
            assert citiesbst.returnValues().contains("London")
            assert citiesbst.returnValues().contains("Beijing")

            //The distance between Portland and San Francisco is approximately 860km
            assert coordDistance(45.517F, -122.667F, 37.783333F, -122.416667F).round(2) == 860.19
        }

        def citiesbst = new SimpleBinaryTree<Coordinates, String>()
        cities.each {k,v ->
            citiesbst.insert((Coordinates) k, (String) v)
        }

        timeit.call("Testing Lookup in Cities BST", 1, 1, 0) {

            def portland = new Coordinates(lat:45.517F, lon:-122.667F)
            def testcity = new Coordinates(lat: 45.517F, lon:-133.123F)
            def testcity2 = new Coordinates(lat: 45.517F, lon:-133.123F)
            def longyearbyennew = new Coordinates(lat: 78.217F, lon:15.55F)

            citiesbst.insert(testcity, "TestCity")

            assert citiesbst.lookup(testcity2)
            assert citiesbst.lookup(longyearbyennew)
            assert citiesbst.lookup(portland)

        }

        timeit.call("Finding Portland", 1, 1, 0)
        {
            def portland = new Coordinates(lat:45.517F, lon:-122.667F)
            println "Found: " + citiesbst.get(portland)
        }

        timeit.call("Print All Cities", 1, 1, 0)
        {
            citiesbst.returnKeyValues().each { k, v ->
                println "City: " + v + " Lat: " + k.lat + " Lon: " + k.lon
            }
        }

        timeit("Finding Cities Within 2000km of Portland...",1,1,0)
        {
            citiesbst.returnKeyValues().sort{it.value.toLowerCase()}.each {k, v ->
                if (k.isNear(45.517F,-122.667F, 2000.0F))
                    println coordDistance(45.517F,-122.667F, (Float) k.lat, (Float) k.lon).round(1) + " kilometers from PDX: " + v
            }
        }

        int traversals = 0

        timeit("Count Number of Traversals",1,1,0)
        {
            traversals = 0

            for (Coordinates s : citiesbst.returnKeys())
            {
                traversals += citiesbst.getTraversals(s)
            }
        }

        println()
        println "BST Max Depth: " + citiesbst.maxDepth()
        println "BST Min Depth: " + citiesbst.minDepth()
        println "Traversals to Find All Cities: " + traversals
        println "Average Traversals Per City: " + (traversals/citiesbst.size()).setScale(2,1)

    }
}
