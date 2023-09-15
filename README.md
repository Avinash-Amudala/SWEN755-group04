# SWEN755-group04
# Server Monitoring System

A simple Java-based application that simulates monitoring server connections. The application has a simulated server waiting for client connections, and it uses heartbeats to monitor the server's status.

## Architecture Overview

- **Critical Server Process (CSP)**:
   - Waits for client connections.
   - For each connection, it sends a welcome message.
   - Has a random failure mechanism to simulate server crashes.
- **Heartbeat Manager (HM)**:
   - Monitors the CSP by regularly expecting "heartbeats" from it.
   - If no heartbeat is received within a certain time frame, it assumes the CSP has crashed and raises an alert.
- **Heartbeat Sender (HS)**:
   - A part of CSP.
   - Sends regular heartbeats to the HM.

## Prerequisites

- Java 

## Getting Started

### Compiling

To compile the code, navigate to the directory containing the source files and run:

```bash
javac ServerMonitorSystem.java CriticalServerProcess.java HeartbeatManager.java
```

### Running

After compilation, run the system using:
```bash
java ServerMonitorSystem
```
