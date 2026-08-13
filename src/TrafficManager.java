import java.util.Random;

public class TrafficManager {

    private Random random;


    public TrafficManager() {

        random = new Random();
    }


    // Generate random traffic
    public TrafficLevel generateTraffic() {

        int value = random.nextInt(4);

        return TrafficLevel.values()[value];
    }


    // Apply random traffic to all roads
    public void updateTraffic(Graph graph) {

        for (Location location :
                graph.getAllLocations()) {

            for (Road road :
                    graph.getRoads(
                            location.getId())) {

                road.setTrafficLevel(
                        generateTraffic()
                );
            }
        }
    }


    // Display traffic information
    public void displayTraffic(Graph graph) {

        System.out.println(
                "\n===== CURRENT TRAFFIC ====="
        );


        for (Location location :
                graph.getAllLocations()) {

            for (Road road :
                    graph.getRoads(
                            location.getId())) {

                System.out.println(

                        road.getSource().getName()

                        + " → "

                        + road.getDestination().getName()

                        + " | Traffic: "

                        + road.getTrafficLevel()

                        + " | Travel Time: "

                        + String.format(
                                "%.1f",
                                road.getTrafficAdjustedTime()
                        )

                        + " min"
                );
            }
        }
    }
}