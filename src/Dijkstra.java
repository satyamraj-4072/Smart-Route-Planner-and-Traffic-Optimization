import java.util.*;

public class Dijkstra {

    public RouteResult findShortestRoute(
            Graph graph,
            String sourceId,
            String destinationId) {


        Map<String, Double> distances =
                new HashMap<>();


        Map<String, String> previous =
                new HashMap<>();


        PriorityQueue<Node> queue =
                new PriorityQueue<>(
                        Comparator.comparingDouble(
                                Node::getDistance
                        )
                );


        // Initialize distances
        for (Location location :
                graph.getAllLocations()) {

            distances.put(
                    location.getId(),
                    Double.POSITIVE_INFINITY
            );
        }


        // Source = 0
        distances.put(sourceId, 0.0);

        queue.add(
                new Node(
                        sourceId,
                        0.0
                )
        );


        // Dijkstra
        while (!queue.isEmpty()) {

            Node current =
                    queue.poll();


            String currentId =
                    current.getLocationId();


            // Ignore outdated entries
            if (current.getDistance()
                    > distances.get(currentId)) {

                continue;
            }


            // Destination reached
            if (currentId.equals(
                    destinationId)) {

                break;
            }


            // Explore neighboring roads
            for (Road road :
                    graph.getRoads(currentId)) {


                String neighborId =
                        road.getDestination()
                                .getId();


                // IMPORTANT:
                // Use traffic-adjusted time
                double roadTime =
                        road.getTrafficAdjustedTime();


                double newDistance =
                        distances.get(currentId)
                        + roadTime;


                // Better route
                if (newDistance
                        < distances.get(neighborId)) {


                    distances.put(
                            neighborId,
                            newDistance
                    );


                    previous.put(
                            neighborId,
                            currentId
                    );


                    queue.add(
                            new Node(
                                    neighborId,
                                    newDistance
                            )
                    );
                }
            }
        }


        // No route
        if (!distances.containsKey(
                destinationId)
                ||
                distances.get(destinationId)
                == Double.POSITIVE_INFINITY) {

            return null;
        }


        // Reconstruct route
        List<String> pathIds =
                new ArrayList<>();


        String currentId =
                destinationId;


        while (currentId != null) {

            pathIds.add(currentId);

            currentId =
                    previous.get(currentId);
        }


        Collections.reverse(pathIds);


        // Convert IDs to locations
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


            // Calculate distance
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
                distances.get(
                        destinationId
                )
        );
    }


    private static class Node {

        private String locationId;
        private double distance;


        public Node(
                String locationId,
                double distance) {

            this.locationId =
                    locationId;

            this.distance =
                    distance;
        }


        public String getLocationId() {

            return locationId;
        }


        public double getDistance() {

            return distance;
        }
    }
}