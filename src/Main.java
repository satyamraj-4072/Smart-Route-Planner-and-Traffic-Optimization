public class Main {

    public static void main(String[] args) {

        System.out.println(
                "=========================================="
        );

        System.out.println(
                " Smart Route Planner & Traffic Optimization"
        );

        System.out.println(
                "=========================================="
        );


        // =====================================
        // CREATE GRAPH
        // =====================================

        Graph graph = new Graph();


        // =====================================
        // LOCATIONS
        // =====================================

        graph.addLocation(
                new Location("A", "Delhi")
        );

        graph.addLocation(
                new Location("B", "Ghaziabad")
        );

        graph.addLocation(
                new Location("C", "Noida")
        );

        graph.addLocation(
                new Location("D", "Meerut")
        );

        graph.addLocation(
                new Location("E", "Gurgaon")
        );


        // =====================================
        // ROADS
        // =====================================

        graph.addRoad(
                "A", "B",
                25,
                30
        );

        graph.addRoad(
                "A", "D",
                50,
                55
        );

        graph.addRoad(
                "B", "C",
                15,
                20
        );

        graph.addRoad(
                "B", "D",
                30,
                35
        );

        graph.addRoad(
                "C", "E",
                35,
                40
        );

        graph.addRoad(
                "D", "E",
                45,
                50
        );


        // =====================================
        // TRAFFIC
        // =====================================

        TrafficManager trafficManager =
                new TrafficManager();

        trafficManager.updateTraffic(
                graph
        );

        trafficManager.displayTraffic(
                graph
        );


        // =====================================
        // DIJKSTRA
        // =====================================

        System.out.println(
                "\n===== DIJKSTRA ====="
        );

        Dijkstra dijkstra =
                new Dijkstra();

        RouteResult dijkstraResult =
                dijkstra.findShortestRoute(
                        graph,
                        "A",
                        "E"
                );

        if (dijkstraResult != null) {

            dijkstraResult.display();

        } else {

            System.out.println(
                    "No route found."
            );
        }


        // =====================================
        // A*
        // =====================================

        System.out.println(
                "\n===== A* ====="
        );

        AStar aStar =
                new AStar();

        RouteResult aStarResult =
                aStar.findShortestRoute(
                        graph,
                        "A",
                        "E"
                );

        if (aStarResult != null) {

            aStarResult.display();

        } else {

            System.out.println(
                    "No route found."
            );
        }
    }
}