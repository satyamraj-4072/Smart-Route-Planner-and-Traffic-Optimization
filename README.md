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

The system can display an alternative route so that users can compare the selected optimal route with another possible route.

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
