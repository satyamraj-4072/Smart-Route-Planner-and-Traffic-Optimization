import java.util.List;

public class RouteResult {

    private List<Location> route;
    private double totalDistance;
    private double totalTime;

    public RouteResult(
            List<Location> route,
            double totalDistance,
            double totalTime) {

        this.route = route;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
    }

    public List<Location> getRoute() {
        return route;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void display() {

        System.out.println("\n===== ROUTE RESULT =====");

        System.out.print("Route: ");

        for (int i = 0; i < route.size(); i++) {

            System.out.print(
                    route.get(i).getName()
            );

            if (i < route.size() - 1) {
                System.out.print(" → ");
            }
        }

        System.out.println();

        System.out.println(
                "Distance: "
                + totalDistance
                + " km"
        );

        System.out.println(
                "Travel Time: "
                + totalTime
                + " minutes"
        );
    }
}