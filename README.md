# SWEN755-group04
## Server Monitoring System

A simple Java-based application that simulates a server monitoring system using heartbeats. The primary server awaits client connections, and a secondary server stands by ready to take over in the event of a primary server failure.

### Architecture Overview

- **Critical Server Process (CSP)**:
   - Waits for client connections on port 8080.
   - Sends a welcome message for each connection.
   - Uses a random failure mechanism to simulate server crashes.
   - Periodically sends heartbeats to the Heartbeat Manager.
   - Periodically creates checkpoints.
   - In the event of a failure, a secondary server is started to take over.

- **Secondary Server**:
   - Remains in standby mode.
   - Periodically checks for checkpoints.
   - If a checkpoint is found, it restores the state and takes over as the active server, listening on port 8081.

- **Heartbeat Manager (HM)**:
   - Monitors the heartbeats from the CSP.
   - If heartbeats are missing for a certain duration, it assumes the CSP has failed and raises an alert.

### Prerequisites

- Java JDK

### Getting Started

#### Compiling

To compile the code, navigate to the directory containing the source files and run:

```bash
javac src/*.java
```
### Running

1. **Start the Primary Server:**
   ```bash
   java src.PrimaryServerMain
   ```
   2. **Optionally Start the Secondary Server**:
   
   Although the secondary server will automatically initiate upon the primary server's failure, you can manually start it with:
   ```bash
   java src.SecondaryServerMain
Simulating a Client Connection:
Use this command to simulate a client trying to connect to the server:
bash
Copy code
java src.Client
Expected Behavior
Upon initiating the primary server, a message should appear indicating it has begun and is listening on port 8080.
A message will display the client's IP address when a client connects.
A failure message will emerge if the primary server experiences a random failure. The secondary server will then initiate, restoring from the latest checkpoint.
The secondary server will start accepting client connections on port 8081 after its initiation.
Troubleshooting
Ensure no other processes occupy ports 8080 or 8081 if you encounter port binding issues, such as "Address already in use".
Confirm the Java JDK version is installed and correctly set up.
