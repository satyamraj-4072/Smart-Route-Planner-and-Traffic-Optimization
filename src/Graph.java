import java.util.*;

public class Graph {

    private Map<String, Location> locations;
    private Map<String, List<Road>> roads;

    public Graph() {
        locations = new HashMap<>();
        roads = new HashMap<>();
    }

    // Add a location to the graph
    public void addLocation(Location location) {

        locations.put(location.getId(), location);

        roads.putIfAbsent(
                location.getId(),
                new ArrayList<>()
        );
    }

    // Add a road between two locations
    public void addRoad(
            String sourceId,
            String destinationId,
            double distance,
            double travelTime) {

        Location source = locations.get(sourceId);
        Location destination = locations.get(destinationId);

        if (source == null || destination == null) {
            throw new IllegalArgumentException(
                    "Location does not exist."
            );
        }

        Road road = new Road(
                source,
                destination,
                distance,
                travelTime
        );

        roads.get(sourceId).add(road);
    }

    // Get roads connected to a location
    public List<Road> getRoads(String locationId) {

        return roads.getOrDefault(
                locationId,
                new ArrayList<>()
        );
    }

    // Get a location
    public Location getLocation(String id) {

        return locations.get(id);
    }

    // Get all locations
    public Collection<Location> getAllLocations() {

        return locations.values();
    }
}