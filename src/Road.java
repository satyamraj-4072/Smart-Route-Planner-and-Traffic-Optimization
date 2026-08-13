public class Road {

    private Location source;
    private Location destination;

    private double distance;
    private double travelTime;

    private TrafficLevel trafficLevel;


    public Road(
            Location source,
            Location destination,
            double distance,
            double travelTime) {

        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.travelTime = travelTime;

        // Default traffic
        this.trafficLevel = TrafficLevel.LOW;
    }


    public Location getSource() {
        return source;
    }


    public Location getDestination() {
        return destination;
    }


    public double getDistance() {
        return distance;
    }


    public double getTravelTime() {
        return travelTime;
    }


    public TrafficLevel getTrafficLevel() {
        return trafficLevel;
    }


    public void setTrafficLevel(
            TrafficLevel trafficLevel) {

        this.trafficLevel = trafficLevel;
    }


    // Calculate actual travel time
    // considering traffic
    public double getTrafficAdjustedTime() {

        return travelTime
                * trafficLevel.getMultiplier();
    }
}