# Smart Route Planner and Traffic Optimization

## Traffic Optimization & Route Planning System

Smart Route Planner and Traffic Optimization is a Java-based route planning system that finds the best route between different locations by considering both **road distance** and **traffic conditions**.

The project demonstrates how graph-based algorithms can be combined with dynamic traffic conditions to provide an intelligent route recommendation.

The system provides a simple web-based interface where users can select a starting location, destination, and routing algorithm. The Java backend processes the request, calculates the optimal route, and returns the result to the web interface.

---

## Project Overview

The Smart Route Planner models a small road network consisting of five locations:

- Delhi
- Ghaziabad
- Noida
- Meerut
- Gurgaon

The locations are connected using roads with predefined distances and normal travel times.

The system dynamically assigns traffic conditions to the roads. Traffic levels affect the actual travel time of each road.

The routing algorithms then use these traffic-adjusted travel times to find the best route.

For example, a route that is shorter in distance may not always be the fastest route if it has heavy or severe traffic.

This makes the project a simple demonstration of **traffic-aware route optimization**.

---

## Features

### 1. Route Planning

Users can select:

- Starting location
- Destination
- Routing algorithm

The system then calculates the optimal route.

### 2. Traffic-Aware Routing

The system considers different traffic levels on every road.

Traffic levels include:

- LOW
- MEDIUM
- HIGH
- SEVERE

Each traffic level increases the normal travel time by a specific multiplier.

### 3. Dijkstra Algorithm

Dijkstra's shortest-path algorithm is used to calculate the route with the lowest traffic-adjusted travel time.

### 4. A* Algorithm

The project also implements the A* pathfinding algorithm.

The current implementation uses a zero heuristic because the sample locations do not contain geographical coordinates. Therefore, A* produces results comparable to Dijkstra while maintaining the A* algorithm structure.

### 5. Alternative Route

The system displays an alternative route so that users can compare the selected optimal route with another possible route.

### 6. Time Saved

The system calculates how much time can be saved by selecting the optimal route instead of the alternative route.

### 7. Traffic Information

The web interface displays the current traffic condition and traffic-adjusted travel time for each road.

### 8. Algorithm Comparison

The system can compare the results produced by Dijkstra and A*.

The comparison displays:

- Route
- Distance
- Estimated travel time
- Algorithm used

---

## Technologies Used

### Backend

- Java
- Java Collections Framework
- Java HTTP Server
- Graph Data Structure
- Dijkstra Algorithm
- A* Algorithm

### Frontend

- HTML5
- CSS3
- JavaScript

### Development Tools

- Visual Studio Code
- Git
- GitHub
- Live Server

---

## Traffic Model

Each road has:

- Source location
- Destination location
- Distance
- Normal travel time
- Traffic level

The actual travel time is calculated using:

```text
Traffic Adjusted Time =
Normal Travel Time × Traffic Multiplier
```

The traffic multipliers used in the project are:

| Traffic Level | Multiplier |
|---|---:|
| LOW | 1.0 |
| MEDIUM | 1.5 |
| HIGH | 2.0 |
| SEVERE | 3.0 |

For example, if a road normally takes 40 minutes and has SEVERE traffic:

```text
40 × 3.0 = 120 minutes
```

Therefore, the routing algorithm considers 120 minutes as the effective travel time for that road.

---

## Road Network

The project currently contains the following road connections:

| Source | Destination | Distance | Normal Travel Time |
|---|---|---:|---:|
| Delhi | Ghaziabad | 25 km | 30 min |
| Delhi | Meerut | 50 km | 55 min |
| Ghaziabad | Noida | 15 km | 20 min |
| Ghaziabad | Meerut | 30 km | 35 min |
| Noida | Gurgaon | 35 km | 40 min |
| Meerut | Gurgaon | 45 km | 50 min |

Traffic conditions are dynamically assigned to these roads when the Java server starts.

---

## How the System Works

The application follows this flow:

```text
User
  |
  v
Web Interface
  |
  v
Java HTTP Server
  |
  v
Traffic Manager
  |
  v
Traffic-Adjusted Road Network
  |
  v
Dijkstra / A* Algorithm
  |
  v
Optimal Route
  |
  v
JSON Response
  |
  v
Web Interface
```

### Step 1: User Selects Locations

The user selects:

- Starting location
- Destination

Example:

```text
Starting Location: Delhi
Destination: Gurgaon
```

### Step 2: User Selects an Algorithm

The user can select:

- Dijkstra
- A* Algorithm

### Step 3: Traffic is Updated

The Java backend assigns traffic conditions to the roads.

Example:

```text
Delhi → Ghaziabad : HIGH
Delhi → Meerut    : LOW
Ghaziabad → Noida : SEVERE
Noida → Gurgaon   : LOW
Meerut → Gurgaon  : LOW
```

### Step 4: Travel Time is Calculated

The system calculates traffic-adjusted travel time for every road.

### Step 5: Route Algorithm Runs

The selected algorithm searches the graph and finds the route with the lowest total travel time.

### Step 6: Result is Returned

The Java server returns the route, distance, travel time, and algorithm information to the web interface.

---

## Project Structure

The project is organized into two main folders: `src` for the Java backend and `web` for the frontend.

```text
SmartRoutePlannerandTrafficOptimization/
│
├── src/
│   ├── AStar.java
│   ├── Dijkstra.java
│   ├── Graph.java
│   ├── Location.java
│   ├── Node.java
│   ├── Road.java
│   ├── RouteResult.java
│   ├── RouteServer.java
│   ├── TrafficLevel.java
│   └── TrafficManager.java
│
└── web/
    ├── index.html
    ├── script.js
    └── style.css
```

### `src` Folder

The `src` folder contains the Java backend and routing logic.

Important components include:

- **RouteServer.java** – Starts the Java HTTP server and handles route requests.
- **Graph.java** – Represents the road network.
- **Location.java** – Represents locations in the graph.
- **Road.java** – Represents connections between locations.
- **Dijkstra.java** – Implements Dijkstra's shortest-path algorithm.
- **AStar.java** – Implements the A* pathfinding algorithm.
- **TrafficManager.java** – Manages traffic conditions.
- **TrafficLevel.java** – Defines the traffic levels and multipliers.
- **RouteResult.java** – Stores the calculated route result.
- **Node.java** – Supports priority-based route processing.

### `web` Folder

The `web` folder contains the frontend interface.

- **index.html** – Creates the user interface.
- **style.css** – Controls the appearance and layout.
- **script.js** – Communicates with the Java backend and displays route results.

> The ZIP backup file is not included in the project structure.

---

## Main Java Components

### RouteServer.java

`RouteServer.java` creates the Java HTTP server and exposes the route API.

The server runs on:

```text
http://localhost:8080
```

The route endpoint is:

```text
/route
```

The server receives the source, destination, and selected algorithm from the web interface.

---

### Graph.java

`Graph.java` represents the road network.

It manages:

- Locations
- Roads
- Connections between locations

---

### Location.java

`Location.java` represents a location in the road network.

Each location has:

- Location ID
- Location name

Example:

```text
A → Delhi
B → Ghaziabad
C → Noida
D → Meerut
E → Gurgaon
```

---

### Road.java

`Road.java` represents a connection between two locations.

It stores:

- Source
- Destination
- Distance
- Normal travel time
- Traffic level

It also calculates traffic-adjusted travel time.

---

### TrafficLevel.java

`TrafficLevel.java` defines the available traffic levels:

```text
LOW
MEDIUM
HIGH
SEVERE
```

Each traffic level has a multiplier that affects travel time.

---

### TrafficManager.java

`TrafficManager.java` manages traffic conditions.

It randomly assigns traffic levels to the roads whenever the server starts.

This allows different route calculations to demonstrate how changing traffic conditions can affect route selection.

---

### Dijkstra.java

`Dijkstra.java` implements Dijkstra's shortest-path algorithm.

The algorithm uses traffic-adjusted travel time as the route cost.

Therefore, the selected route is based on the fastest travel time rather than simply the shortest geographical distance.

---

### AStar.java

`AStar.java` implements the A* pathfinding algorithm.

The current version uses:

```text
Heuristic = 0
```

because the sample locations do not currently contain latitude and longitude coordinates.

With a zero heuristic, A* behaves similarly to Dijkstra while maintaining the structure required for future geographical optimization.

---

### RouteResult.java

`RouteResult.java` stores the result generated by the routing algorithms.

It contains:

- Selected route
- Total distance
- Total travel time

---

## Frontend Components

### index.html

Provides the user interface.

Users can select:

- Starting location
- Destination
- Routing algorithm

It also displays:

- Optimal route
- Distance
- Estimated travel time
- Alternative route
- Time saved
- Current traffic conditions
- Algorithm comparison

---

### style.css

Provides the visual design of the application.

It controls:

- Layout
- Colors
- Buttons
- Forms
- Route result cards
- Traffic indicators
- Overall presentation

---

### script.js

Connects the frontend with the Java backend.

It:

1. Reads the user's selections.
2. Sends a request to the Java server.
3. Receives the JSON response.
4. Displays the calculated route.
5. Displays distance and estimated travel time.
6. Displays traffic conditions.
7. Displays alternative route information.
8. Displays algorithm comparison information.

---

## Running the Project

### Requirements

Make sure the following are installed:

- Java JDK 17 or later
- Visual Studio Code
- Modern web browser
- Live Server extension for VS Code

---

### Step 1: Open the Project

Open the following folder in Visual Studio Code:

```text
SmartRoutePlannerandTrafficOptimization
```

---

### Step 2: Compile the Java Code

Open the VS Code terminal.

Move into the `src` directory:

```powershell
cd src
```

Compile the Java files:

```powershell
javac *.java
```

---

### Step 3: Start the Java Server

Run:

```powershell
java RouteServer
```

If the server starts successfully, the terminal displays:

```text
====================================
Smart Route Planner Server Started
Open: http://localhost:8080/route
Traffic initialized successfully
====================================
```

---

### Step 4: Start the Frontend

Open the `web` folder in Visual Studio Code.

Open:

```text
index.html
```

Right-click the file and select:

```text
Open with Live Server
```

The application will open in your browser.

The frontend communicates with the Java backend running on:

```text
http://localhost:8080
```

---

## Using the Application

1. Start the Java server.
2. Open the frontend using Live Server.
3. Select a starting location.
4. Select a destination.
5. Select Dijkstra or A*.
6. Click **Find Best Route**.
7. Review the calculated result.

The application displays:

- Optimal route
- Distance
- Estimated travel time
- Alternative route
- Time saved
- Current traffic conditions
- Algorithm comparison

---

## Example Output

For example, the system may produce:

```text
Optimal Route

Delhi → Meerut → Gurgaon

Distance: 95.0 km

Estimated Time: 105.0 min

Algorithm: Dijkstra
```

The result can change depending on the traffic conditions generated when the Java server starts.

---

## API Endpoint

The Java backend provides a route API.

Example request:

```text
http://localhost:8080/route?source=A&destination=E&algorithm=dijkstra
```

The API returns JSON similar to:

```json
{
  "route": ["Delhi", "Ghaziabad", "Noida", "Gurgaon"],
  "distance": 75.0,
  "time": 125.0,
  "algorithm": "dijkstra"
}
```

---

## Dijkstra vs A*

### Dijkstra

Dijkstra explores the graph based on the currently known shortest travel cost.

Advantages:

- Simple to understand
- Works with non-negative edge weights
- Suitable for the current traffic-based graph

### A*

A* uses a heuristic to guide the search toward the destination.

In the current project:

```text
Heuristic = 0
```

Therefore, A* produces results comparable to Dijkstra.

A future version can use actual geographical coordinates and a distance-based heuristic to make A* more efficient.

---

## Why Traffic Optimization is Important

A route with the shortest distance is not always the fastest route.

For example:

```text
Route 1:
75 km
160 minutes

Route 2:
95 km
105 minutes
```

Although Route 2 is longer by distance, it is faster because Route 1 has heavier traffic.

Therefore, the system prioritizes **traffic-adjusted travel time** when selecting the optimal route.

---

## Limitations

This project is designed as an educational demonstration of route planning and traffic optimization.

Current limitations include:

- The road network is predefined.
- Only five locations are currently available.
- Traffic conditions are randomly generated.
- The application does not use real-time traffic APIs.
- Locations do not currently contain GPS coordinates.
- The A* heuristic is currently zero.
- The system does not use real map data.

---

## Future Enhancements

The project can be extended with:

- Real-time traffic APIs
- OpenStreetMap integration
- GPS coordinates
- A geographical A* heuristic
- More locations and roads
- Interactive maps
- Real-time traffic updates
- User-defined locations
- Route visualization
- Historical traffic analysis
- Database integration
- Mobile application support
- Machine learning-based traffic forecasting

---

## Learning Objectives

This project demonstrates practical use of:

- Graph data structures
- Object-oriented programming
- Java Collections
- Priority Queues
- Dijkstra's algorithm
- A* pathfinding
- HTTP communication
- REST-style API concepts
- JSON responses
- JavaScript Fetch API
- HTML and CSS
- Dynamic traffic modeling
- Git and GitHub project management

---

## Conclusion

Smart Route Planner and Traffic Optimization demonstrates how graph algorithms can be used to solve real-world route planning problems.

Instead of selecting a route only according to distance, the system considers traffic-adjusted travel time and dynamically selects a more efficient route.

By combining Java, graph algorithms, traffic modeling, and a web-based interface, the project provides a practical demonstration of intelligent traffic-aware route planning.

---

## Author

**Satyam Raj**
