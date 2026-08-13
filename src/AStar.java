import java.util.*;

public class AStar {

    public RouteResult findShortestRoute(
            Graph graph,
            String sourceId,
            String destinationId) {

        Map<String, Double> gScore = new HashMap<>();
        Map<String, Double> fScore = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<Node> openSet =
                new PriorityQueue<>(
                        Comparator.comparingDouble(Node::getScore)
                );

        // Initialize scores
        for (Location location : graph.getAllLocations()) {
            gScore.put(
                    location.getId(),
                    Double.POSITIVE_INFINITY
            );

            fScore.put(
                    location.getId(),
                    Double.POSITIVE_INFINITY
            );
        }

        // Starting location
        gScore.put(sourceId, 0.0);

        fScore.put(
                sourceId,
                heuristic(graph, sourceId, destinationId)
        );

        openSet.add(
                new Node(
                        sourceId,
                        fScore.get(sourceId)
                )
        );

        // A* search
        while (!openSet.isEmpty()) {

            Node current = openSet.poll();

            String currentId =
                    current.getLocationId();

            if (currentId.equals(destinationId)) {
                break;
            }

            for (Road road : graph.getRoads(currentId)) {

                String neighborId =
                        road.getDestination().getId();

                double tentativeGScore =
                        gScore.get(currentId)
                        + road.getTrafficAdjustedTime();

                if (tentativeGScore
                        < gScore.get(neighborId)) {

                    previous.put(
                            neighborId,
                            currentId
                    );

                    gScore.put(
                            neighborId,
                            tentativeGScore
                    );

                    double estimatedScore =
                            tentativeGScore
                            + heuristic(
                                    graph,
                                    neighborId,
                                    destinationId
                            );

                    fScore.put(
                            neighborId,
                            estimatedScore
                    );

                    openSet.add(
                            new Node(
                                    neighborId,
                                    estimatedScore
                            )
                    );
                }
            }
        }

        // No route found
        if (!gScore.containsKey(destinationId)
                || gScore.get(destinationId)
                == Double.POSITIVE_INFINITY) {

            return null;
        }

        // Reconstruct route
        List<String> pathIds =
                new ArrayList<>();

        String currentId = destinationId;

        while (currentId != null) {

            pathIds.add(currentId);

            currentId =
                    previous.get(currentId);
        }

        Collections.reverse(pathIds);

        // Convert IDs to Location objects
        List<Location> route =
                new ArrayList<>();

        double totalDistance = 0;

        for (int i = 0;
             i < pathIds.size();
             i++) {

            Location location =
                    graph.getLocation(
                            pathIds.get(i)
                    );

            route.add(location);

            if (i < pathIds.size() - 1) {

                String from =
                        pathIds.get(i);

                String to =
                        pathIds.get(i + 1);

                for (Road road :
                        graph.getRoads(from)) {

                    if (road.getDestination()
                            .getId()
                            .equals(to)) {

                        totalDistance +=
                                road.getDistance();

                        break;
                    }
                }
            }
        }

        return new RouteResult(
                route,
                totalDistance,
                gScore.get(destinationId)
        );
    }

    /*
     * Simple heuristic.
     *
     * Since our current locations do not yet
     * contain GPS coordinates, we use 0.
     *
     * This makes A* behave similarly to
     * Dijkstra while keeping the A* structure.
     *
     * Later we will add latitude/longitude
     * and use geographical distance here.
     */
    private double heuristic(
            Graph graph,
            String currentId,
            String destinationId) {

        return 0.0;
    }

    private static class Node {

        private String locationId;
        private double score;

        public Node(
                String locationId,
                double score) {

            this.locationId = locationId;
            this.score = score;
        }

        public String getLocationId() {
            return locationId;
        }

        public double getScore() {
            return score;
        }
    }
}