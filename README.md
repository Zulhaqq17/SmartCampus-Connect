# SmartCampus Connect — BITP3123 DISTRIBUTED APPLICATION DEVELOPMENT PROJECT

https://youtu.be/Cjnr8aCaOD8 -- >> YOUTUBE PRESENTATION LINK

SmartCampus Connect is a campus operations platform designed using a distributed microservices architecture. The system spans 5 independent modular subsystems running across various network topologies, orchestrating interactions using REST/JSON, Enterprise SOAP/XML, and multi-threaded raw TCP Sockets.

---

## 🏗️ System Architecture & Network Topology

The application decouples its domains into dedicated processing nodes to maximize fault isolation and systemic scalability:

| Module / Service Name | Protocol / Architecture Style | Network Port | Database / Persistence Strategy |
| :--- | :--- | :--- | :--- |
| **Student Service** | REST API (JSON payloads) | `8080` | JPA Hibernate / Relational DB |
| **Enrolment Service** | REST API + TCP Pipeline | `8081` | JPA Hibernate / Relational DB |
| **Notification Service**| Multi-threaded TCP Sockets | `9999` | In-Memory Task Queue Structure |
| **Library Service** | Contract-First SOAP (XML) | `8082` | JAXB Core Marshalling Runtime |
| **Reporting Service** | Gateway Data Aggregator | `8083` | Read-Only Stream Compute Engine |

---

## 📦 Detailed Module & Function Documentation

### 1. Student Service (`Port 8080`)
* **Purpose:** Acts as the single source of truth for identity management across the campus ecosystem.
* **Core Functions:**
  * `POST /api/students`: Registers a new student profile into the system (Captures `id`, `name`, `email`, and academic `programme`).
  * `GET /api/students`: Exposes a structured JSON collection array of all active student profiles for downstream ingestion.

### 2. Enrolment Service (`Port 8081`)
* **Purpose:** Orchestrates course registrations and validates inter-service status rules.
* **Core Functions:**
  * `POST /api/enrolments`: Evaluates student enrollment eligibility.
  * **Event Pipeline:** Upon a successful enrollment state generation, this module shifts communication styles, spinning up a raw TCP socket client to broadcast an asynchronous signal out to port `9999`.

### 3. Notification Service (`Port 9999`)
* **Purpose:** An asynchronous, high-throughput notification broadcast worker engine.
* **Core Functions:**
  * **Multi-threaded Sockets:** Runs a persistent background `ServerSocket` worker thread framework that remains open to intercept inbound TCP network streams.
  * Whenever the Enrolment Service drops a success signal onto port `9999`, this module spins up an isolated worker thread to print real-time alert logs out to the console interface without blocking core API requests.

### 4. Library Service (`Port 8082`)
* **Purpose:** Handles academic asset tracking using formal, contract-backed transactions.
* **Core Functions:**
  * **Contract-First Validation:** Leverages an ironclad `library.xsd` schema layout to validate payload structures *prior* to processing.
  * `POST /ws`: Intercepts inbound XML `<soapenv:Envelope>` payloads containing a `BookBookRequest`. It executes structural unmarshalling logic and automatically responds with a mock receipt tracking object returning a transaction code of `777` along with a status payload of `CONFIRMED`.
  * **WSDL Discovery:** Dynamically generates and publishes its system contract manual directly at `http://localhost:8082/ws/library.wsdl`.

### 5. Reporting / Analytics Service (`Port 8083`)
* **Purpose:** Implements a read-only data aggregation layer that pulls operational insights across multiple processing endpoints.
* **Core Functions:**
  * `GET /api/reports/enrolments-by-programme`: Uses Spring `RestTemplate` to make parallel HTTP calls, fetching raw student arrays from port `8080` and enrollment logs from port `8081`.
  * **Compute Logic:** Cross-references data sets using targeted Hash Map lookups, filters by successful enrollment markers, and compiles a clean, aggregated metric view grouped by academic discipline.

---

## 🏃‍♂️ Step-by-Step Ecosystem Execution Order

To run the system without triggering connection-refused errors, start the services in this precise sequence:

1. **Notification Service** (`NotificationServer.java`) — Must be online first to listen for socket connections.
2. **Student Service** (`StudentServiceApplication.java`) — Initializes profile routing entries.
3. **Enrolment Service** (`EnrolmentServiceApplication.java`) — Hooks into both the database and the notification pipeline.
4. **Library Service** (`LibraryServiceApplication.java`) — Exposes the enterprise XML endpoints. 
   *(Note: If binding classes are missing, execute a `Maven install` on the project to trigger code generation from the XSD).*
5. **Reporting Service** (`ReportingServiceApplication.java`) — Turns on the aggregation node.
