public class RouteComparison {

    private final RouteResult selectedRoute;
    private final RouteResult alternativeRoute;


    public RouteComparison(
            RouteResult selectedRoute,
            RouteResult alternativeRoute) {

        this.selectedRoute =
                selectedRoute;

        this.alternativeRoute =
                alternativeRoute;
    }


    public RouteResult getSelectedRoute() {

        return selectedRoute;
    }


    public RouteResult getAlternativeRoute() {

        return alternativeRoute;
    }


    public double getTimeSaved() {

        if (selectedRoute == null
                || alternativeRoute == null) {

            return 0;
        }


        return alternativeRoute.getTotalTime()
                - selectedRoute.getTotalTime();
    }


    public boolean isFaster() {

        return getTimeSaved() > 0;
    }
}