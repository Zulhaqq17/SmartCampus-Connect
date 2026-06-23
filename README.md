# SmartCampus-Connect
BITP3123 DISTRIBUTED APPLICATION DEVELOPMENT PROJECT

Salam

To run this project you need to create a directory first called "SmartCampus-Connect" or anything u want to call it.

after that open terminal/cmd on that directory and paste this line
"git clone https://github.com/Zulhaqq17/SmartCampus-Connect.git"

---

Note Part ni aku AI je hahah

Before running the applications, ensure the following ports are free on your local machine:

| Service Name | Protocol / Style | Port / Destination | Core Function |
| :--- | :--- | :--- | :--- |
| **Student Service** | REST API (JSON) | `8080` | Manages student profile records |
| **Enrolment Service** | REST API (JSON) | `8081` | Validates courses & pipes data to TCP |
| **Notification Service**| TCP Socket Server | `9999` | Multi-threaded background alert queue |
| **Library Service** | SOAP Web Service (XML)| `8082` | Processes strict book checkout contracts |
| **Reporting Service** | REST Data Aggregator | `8083` | Polls data and compiles analytics |

---

### Prerequisites
* **Java Development Kit (JDK):** Version 17 (Required)
* **IDE:** Eclipse IDE for Enterprise Java Developers
* **Build Tool:** Maven (Included in Spring Boot wrappers)

### Execution Order
For the ecosystem to link together seamlessly without throwing connection refusal errors, boot up the backend nodes in this exact sequence:

1. **Start the Notification Service first:**
   * Open `NotificationServer.java` $\rightarrow$ Right-click $\rightarrow$ *Run As* $\rightarrow$ *Java Application*.
   * Verify console output logs: `--- Campus Notification Service Starting on Port 9999 ---`

2. **Start the Core Transaction Services:**
   * Open `StudentServiceApplication.java` $\rightarrow$ *Run As* $\rightarrow$ *Java Application*. (Port 8080)
   * Open `EnrolmentServiceApplication.java` $\rightarrow$ *Run As* $\rightarrow$ *Java Application*. (Port 8081)

3. **Start the SOAP Enterprise Service:**
   * Open `LibraryServiceApplication.java` $\rightarrow$ *Run As* $\rightarrow$ *Java Application*. (Port 8082)
   * *Note:* If you get compilation errors on data objects, right-click the project $\rightarrow$ *Run As* $\rightarrow$ *Maven install* to trigger the XSD code generator.

4. **Start the Aggregator:**
   * Open `ReportingServiceApplication.java` $\rightarrow$ *Run As* $\rightarrow$ *Java Application*. (Port 8083)

---

## 🧪 Postman Testing Guide

### 1. Verification of Contracts
* **SOAP WSDL Link:** Open `http://localhost:8082/ws/library.wsdl` in a browser to check the XML contract page.

### 2. Testing the students url
* **Method:** `GET`, `POST`
* **URL:**  `http://localhost:8080/api/students`
* *Note:* To do `POST` click body -> change to raw and JSON
* *Note:* for `GET` just change the option next to the url XD

### 3. Testing the enrolments
http://localhost:8081/api/enrolments
* *Note:* same stuff as students url
* *Note:* to return enrolment on report analytics do `POST` here first

### 4. Testing the Analytics Gateway
* **Method:** `GET`
* **URL:** `http://localhost:8083/api/reports/enrolments-by-programme`
* *Note:* If the output returns an empty json block `{}`, ensure you have sent a successful `POST` request to the Enrolment Service first to seed active data into the runtime tables!



