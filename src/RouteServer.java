import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteServer {

    private static Graph graph;
    private static TrafficManager trafficManager;


    public static void main(String[] args) throws Exception {

        // ==============================
        // CREATE ROAD NETWORK
        // ==============================

        graph = createGraph();


        // ==============================
        // CREATE TRAFFIC MANAGER
        // ==============================

        trafficManager =
                new TrafficManager();


        // Generate traffic once
        trafficManager.updateTraffic(graph);


        // Display traffic
        trafficManager.displayTraffic(graph);


        // ==============================
        // CREATE SERVER
        // ==============================

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8080),
                        0
                );


        server.createContext(
                "/route",
                RouteServer::handleRoute
        );


        server.setExecutor(null);


        server.start();


        System.out.println(
                "===================================="
        );

        System.out.println(
                "Smart Route Planner Server Started"
        );

        System.out.println(
                "Traffic initialized successfully"
        );

        System.out.println(
                "===================================="
        );
    }


    // ==========================================
    // CREATE GRAPH
    // ==========================================

    private static Graph createGraph() {

        Graph graph = new Graph();


        // Locations

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


        // Roads

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


        return graph;
    }


    // ==========================================
    // HANDLE ROUTE
    // ==========================================

    private static void handleRoute(
            HttpExchange exchange)
            throws IOException {


        // ==============================
        // CORS
        // ==============================

        exchange.getResponseHeaders()
                .set(
                        "Access-Control-Allow-Origin",
                        "*"
                );

        exchange.getResponseHeaders()
                .set(
                        "Access-Control-Allow-Methods",
                        "GET, OPTIONS"
                );

        exchange.getResponseHeaders()
                .set(
                        "Access-Control-Allow-Headers",
                        "Content-Type"
                );


        // ==============================
        // OPTIONS
        // ==============================

        if (exchange.getRequestMethod()
                .equalsIgnoreCase("OPTIONS")) {

            exchange.sendResponseHeaders(
                    204,
                    -1
            );

            exchange.close();

            return;
        }


        try {


            // ==============================
            // GET PARAMETERS
            // ==============================

            String query =
                    exchange.getRequestURI()
                            .getQuery();


            Map<String, String> params =
                    parseQuery(query);


            String source =
                    params.get("source");


            String destination =
                    params.get("destination");


            String algorithm =
                    params.get("algorithm");


            // ==============================
            // VALIDATE
            // ==============================

            if (source == null
                    || destination == null
                    || algorithm == null) {

                sendResponse(
                        exchange,
                        400,
                        "{\"error\":\"Missing parameters\"}"
                );

                return;
            }


            if (source.equals(destination)) {

                sendResponse(
                        exchange,
                        400,
                        "{\"error\":\"Source and destination must be different\"}"
                );

                return;
            }


            // ==============================
            // CALCULATE SELECTED ROUTE
            // ==============================

            RouteResult selectedRoute =
                    calculateRoute(
                            source,
                            destination,
                            algorithm
                    );


            if (selectedRoute == null) {

                sendResponse(
                        exchange,
                        404,
                        "{\"error\":\"No route found\"}"
                );

                return;
            }


            // ==============================
            // FIND ALTERNATIVE ROUTE
            // ==============================

            RouteResult alternativeRoute =
                    findAlternativeRoute(
                            source,
                            destination,
                            selectedRoute
                    );


            // ==============================
            // BUILD JSON
            // ==============================

            String routeJson =
                    buildRouteJson(
                            selectedRoute
                    );


            String alternativeRouteJson =
                    "null";


            double alternativeDistance = 0;
            double alternativeTime = 0;
            double timeSaved = 0;


            if (alternativeRoute != null) {

                alternativeRouteJson =
                        buildRouteJson(
                                alternativeRoute
                        );


                alternativeDistance =
                        alternativeRoute
                                .getTotalDistance();


                alternativeTime =
                        alternativeRoute
                                .getTotalTime();


                timeSaved =
                        alternativeTime
                        - selectedRoute
                                .getTotalTime();
            }


            // ==============================
            // TRAFFIC
            // ==============================

            String trafficJson =
                    buildTrafficJson();


            // ==============================
            // RESPONSE
            // ==============================

            String json =
                    "{"

                    + "\"route\":"
                    + routeJson

                    + ","

                    + "\"distance\":"
                    + selectedRoute
                            .getTotalDistance()

                    + ","

                    + "\"time\":"
                    + selectedRoute
                            .getTotalTime()

                    + ","

                    + "\"algorithm\":\""
                    + algorithm
                    + "\""

                    + ","

                    + "\"alternativeRoute\":"
                    + alternativeRouteJson

                    + ","

                    + "\"alternativeDistance\":"
                    + alternativeDistance

                    + ","

                    + "\"alternativeTime\":"
                    + alternativeTime

                    + ","

                    + "\"timeSaved\":"
                    + timeSaved

                    + ","

                    + "\"traffic\":"
                    + trafficJson

                    + "}";


            sendResponse(
                    exchange,
                    200,
                    json
            );


        } catch (Exception e) {


            e.printStackTrace();


            sendResponse(
                    exchange,
                    500,
                    "{\"error\":\"Server error\"}"
            );
        }
    }


    // ==========================================
    // CALCULATE ROUTE
    // ==========================================

    private static RouteResult calculateRoute(
            String source,
            String destination,
            String algorithm) {


        if (algorithm.equalsIgnoreCase(
                "dijkstra")) {


            Dijkstra dijkstra =
                    new Dijkstra();


            return dijkstra.findShortestRoute(
                    graph,
                    source,
                    destination
            );
        }


        if (algorithm.equalsIgnoreCase(
                "astar")) {


            AStar aStar =
                    new AStar();


            return aStar.findShortestRoute(
                    graph,
                    source,
                    destination
            );
        }


        return null;
    }


    // ==========================================
    // FIND ALTERNATIVE ROUTE
    // ==========================================

    private static RouteResult findAlternativeRoute(
            String source,
            String destination,
            RouteResult selectedRoute) {


        List<Location> selectedPath =
                selectedRoute.getRoute();


        if (selectedPath.size() < 2) {

            return null;
        }


        /*
         * We temporarily increase the cost
         * of the first road in the selected
         * route.
         *
         * This forces Dijkstra to search
         * for another possible path.
         */

        String firstSource =
                selectedPath
                        .get(0)
                        .getId();


        String firstDestination =
                selectedPath
                        .get(1)
                        .getId();


        Road selectedRoad = null;


        for (Road road :
                graph.getRoads(firstSource)) {


            if (road.getDestination()
                    .getId()
                    .equals(firstDestination)) {

                selectedRoad = road;

                break;
            }
        }


        if (selectedRoad == null) {

            return null;
        }


        TrafficLevel originalTraffic =
                selectedRoad
                        .getTrafficLevel();


        // Temporarily make this road
        // extremely expensive

        selectedRoad.setTrafficLevel(
                TrafficLevel.SEVERE
        );


        Dijkstra dijkstra =
                new Dijkstra();


        RouteResult alternative =
                dijkstra.findShortestRoute(
                        graph,
                        source,
                        destination
                );


        // Restore original traffic

        selectedRoad.setTrafficLevel(
                originalTraffic
        );


        // If no alternative exists

        if (alternative == null) {

            return null;
        }


        // Check whether route is actually
        // different

        if (sameRoute(
                selectedRoute,
                alternative)) {

            return null;
        }


        return alternative;
    }


    // ==========================================
    // CHECK SAME ROUTE
    // ==========================================

    private static boolean sameRoute(
            RouteResult first,
            RouteResult second) {


        List<Location> firstRoute =
                first.getRoute();


        List<Location> secondRoute =
                second.getRoute();


        if (firstRoute.size()
                != secondRoute.size()) {

            return false;
        }


        for (int i = 0;
             i < firstRoute.size();
             i++) {


            if (!firstRoute
                    .get(i)
                    .getId()
                    .equals(
                            secondRoute
                                    .get(i)
                                    .getId()
                    )) {

                return false;
            }
        }


        return true;
    }


    // ==========================================
    // BUILD ROUTE JSON
    // ==========================================

    private static String buildRouteJson(
            RouteResult result) {


        StringBuilder json =
                new StringBuilder();


        json.append("[");


        for (int i = 0;
             i < result.getRoute().size();
             i++) {


            json.append("\"");


            json.append(
                    result.getRoute()
                            .get(i)
                            .getName()
            );


            json.append("\"");


            if (i <
                    result.getRoute().size() - 1) {

                json.append(",");
            }
        }


        json.append("]");


        return json.toString();
    }


    // ==========================================
    // BUILD TRAFFIC JSON
    // ==========================================

    private static String buildTrafficJson() {


        List<String> entries =
                new ArrayList<>();


        for (Location location :
                graph.getAllLocations()) {


            for (Road road :
                    graph.getRoads(
                            location.getId())) {


                String entry =
                        "{"

                        + "\"source\":\""
                        + road.getSource()
                                .getName()
                        + "\""

                        + ","

                        + "\"destination\":\""
                        + road.getDestination()
                                .getName()
                        + "\""

                        + ","

                        + "\"level\":\""
                        + road.getTrafficLevel()
                        + "\""

                        + ","

                        + "\"time\":"
                        + road.getTrafficAdjustedTime()

                        + ","

                        + "\"distance\":"
                        + road.getDistance()

                        + "}";


                entries.add(entry);
            }
        }


        return "[" +
                String.join(",", entries)
                + "]";
    }


    // ==========================================
    // PARSE QUERY
    // ==========================================

    private static Map<String, String> parseQuery(
            String query) {


        Map<String, String> params =
                new HashMap<>();


        if (query == null) {

            return params;
        }


        String[] pairs =
                query.split("&");


        for (String pair : pairs) {


            String[] keyValue =
                    pair.split("=", 2);


            if (keyValue.length == 2) {


                String key =
                        URLDecoder.decode(
                                keyValue[0],
                                StandardCharsets.UTF_8
                        );


                String value =
                        URLDecoder.decode(
                                keyValue[1],
                                StandardCharsets.UTF_8
                        );


                params.put(
                        key,
                        value
                );
            }
        }


        return params;
    }


    // ==========================================
    // SEND RESPONSE
    // ==========================================

    private static void sendResponse(
            HttpExchange exchange,
            int statusCode,
            String response)
            throws IOException {


        byte[] bytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );


        exchange.getResponseHeaders()
                .set(
                        "Content-Type",
                        "application/json"
                );


        exchange.sendResponseHeaders(
                statusCode,
                bytes.length
        );


        try (OutputStream output =
                     exchange.getResponseBody()) {

            output.write(bytes);
        }
    }
}