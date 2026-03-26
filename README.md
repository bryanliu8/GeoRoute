# Dynamic Traffic Routing and Congestion Simulation System

## 📌 Overview

This project implements a **dynamic traffic simulation system** that models how traffic flows through a road network under congestion. The system represents roads as a **directed graph** and simulates how travel demand distributes across multiple routes over time.

Instead of simulating individual vehicles, the system uses an **edge-centric flow model**, allowing efficient large-scale simulation of traffic behavior.

---

## 🎯 Objectives

* Generate a large-scale road network (10,000 nodes)
* Simulate traffic demand between origin-destination (OD) pairs
* Implement congestion-aware routing using shortest path algorithms
* Model traffic flow using probabilistic (logit-based) path selection
* Analyze system performance using metrics like travel time and throughput
* Observe convergence toward traffic equilibrium

---

## 🧠 Core Concepts

### Graph Model

* **Vertices** → intersections (2D coordinates)
* **Edges** → road segments (directed)

Each edge stores:

* length
* capacity
* free-flow travel time
* current flow
* previous flow
* travel time
* smoothed travel time

---

### Traffic Model

* Traffic is modeled as **continuous flow**, not discrete vehicles
* Each tick generates **OD pairs with demand**
* Demand is distributed across multiple paths using a **logit model**

---

### Routing

* Uses **Dijkstra’s algorithm** with dynamic edge weights
* Computes **top-3 candidate paths** via repeated runs with slight perturbations

---

### Congestion Model

Travel time increases with congestion using the BPR function:

[
T_e = T_0 (1 + 0.15 (f/c)^4)
]

To stabilize routing:

[
\tilde{T}_e(t) = 0.2T_e(t) + 0.8\tilde{T}_e(t-1)
]

---

## 🔁 Simulation Workflow

Each simulation tick:

1. Save previous edge flows
2. Reset flow accumulators
3. Generate OD demand
4. Group OD pairs by origin
5. Run Dijkstra per origin
6. Compute candidate paths
7. Split demand using logit model
8. Accumulate flow on edges
9. Update travel times (BPR)
10. Apply smoothing
11. Record metrics
12. Check convergence

---

## 📊 Metrics

The system evaluates:

* **Average Travel Time** (flow-weighted)
* **Throughput** (total flow)
* **Congestion Level**
* **Saturated Edge Ratio**
* **Convergence Rate**

---

## 🏗️ Architecture

### Data Layer

* `Graph`
* `Vertex`
* `Edge`
* `ODPair`
* `Path`
* `Snapshot`

### Logic Layer

* `Router` (Dijkstra + path generation)
* `FlowAllocator` (logit splitting)
* `DemandGenerator`

### Control Layer

* `TrafficSimulator`

### Metrics

* `MetricsCollector`

### UI

* `MapRenderer` (JavaFX)

---

## 📂 Project Structure

```
model/
    Graph.java
    Vertex.java
    Edge.java
    Path.java
    ODPair.java

simulation/
    TrafficSimulator.java
    Router.java
    FlowAllocator.java
    DemandGenerator.java

metrics/
    MetricsCollector.java
    Snapshot.java

ui/
    MapRenderer.java
```

---

## ⚙️ Implementation Details

* Language: **Java**
* Data Structures: **Adjacency List Graph**
* Algorithm Complexity:

  * Dijkstra: `O(E log V)`
  * Per tick: ~2 million operations

---

## 🚀 How to Run

1. Generate the graph
2. Initialize simulation parameters
3. Start simulation loop
4. Observe:

   * traffic flow distribution
   * congestion patterns
   * convergence behavior

---

## ⚠️ Notes & Assumptions

* Traffic is modeled as **continuous flow**, not discrete vehicles
* Road network is **synthetically generated**
* Intersection constraints are simplified for performance
* Routing uses **approximate k-shortest paths**

---

## 📈 Expected Outcome

* Emergent congestion patterns
* Load balancing across multiple routes
* Stabilization of traffic flow over time
* Insight into network performance under demand

---

## 🧩 Future Improvements

* Real map data integration
* More advanced k-shortest path algorithms
* Real-time interactive controls
* Multi-class traffic (cars, buses, etc.)
* Signal/traffic light simulation

---

## 📜 License

This project is for academic and educational purposes.
