# Living Simulation – Concurrent Ecosystem Simulator

A Java-based ecosystem simulation where multiple species move, reproduce, and compete for resources in a grid-based world. Predators can eliminate weaker species, and territories enforce population limits to prevent overcrowding.


The project has **two concurrency implementations**:

- **Thread-based**
  - Each animal runs concurrently as a thread.
  - Synchronization ensures safe interactions:
    - `locks` and `synchronized` blocks
    - `semaphores` for lifecycle control
    - `wait/notify` for coordination


- **Process-based**
  - Each animal runs in a separate JVM process and acts independently.
  - The parent process coordinates animals via input/output streams:
    - send occasional commands or signals
    - receive status updates from each animal


---

## Quick Start

### Compile
```bash
javac -d bin -sourcepath src $(find src/project -name "*.java")
```

### Run

```bash
# Thread-based
java -cp bin project.thread.main.BigBang

# Process-based
java -cp bin project.process.main.BigBang
```


#### Input parameters:

| Parameter | Description |
|-----------|------------|
| r         | Number of species |
| s         | Initial population per species |
| n         | World height (grid size) |
| m         | World width (grid size) |
| k         | Maximum residents per territory |
| t         | Print interval (display frequency) |


# Simulation Overview

- Animals move to adjacent territories each cycle.
- Species reproduce according to cycles and territory capacity.
- Predators eliminate weaker species in neighboring territories.
- Overpopulated territories trigger starvation.