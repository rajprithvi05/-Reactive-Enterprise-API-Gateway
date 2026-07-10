# 🛡️ Reactive Enterprise API Gateway

A high-throughput, reactive API Gateway engineered to manage traffic, enforce edge security, and provide fault tolerance for distributed microservices. 

This project acts as a "Front Desk" reverse proxy, utilizing a non-blocking architecture to dynamically route, rate-limit, and load-balance incoming HTTP traffic before it reaches backend systems.

---

## 🚀 Features at a Glance
* **Edge Security:** A custom Global Pre-Filter enforces stateless token authentication, rejecting unauthorized traffic (401) before it consumes system resources.
* **Distributed Rate Limiting:** Utilizes a **Redis**-backed Token Bucket algorithm to throttle excessive requests (429) and mitigate API abuse/DDoS vectors.
* **High Availability Load Balancing:** Implements a client-side Round-Robin algorithm to evenly distribute traffic across multiple active backend instances.
* **Fault Tolerance:** **Resilience4J** Circuit Breakers actively monitor network health, providing a 4-second timeout threshold and an asynchronous emergency fallback page during downstream outages.
* **Header Transformation:** Mid-flight payload manipulation to inject custom headers into requests and responses.

---

## 🛠️ Tech Stack
* **Java 21 / Spring Boot 3**
* **Spring WebFlux / Netty** (Reactive, Event-Loop Server)
* **Spring Cloud Gateway** (Routing Engine)
* **Redis** (In-memory data structure store)
* **Maven** (Build Automation)

---

## 💻 How to Run This Project Locally

To run this gateway on your local machine, ensure you have the following installed:
1. **Java Development Kit (JDK) 21** or higher.
2. **Redis Server** (Must be running locally on the default port `6379`).

### Step 1: Start Redis
Before booting the Java application, ensure your local Redis server is active. The Gateway relies on Redis to calculate the rate-limiting math in real-time.

### Step 2: Clone and Boot the Gateway
Open your terminal (PowerShell recommended for Windows) and run the following commands:

```powershell
# Clone the repository
git clone [https://github.com/YourUsername/your-repo-name.git](https://github.com/YourUsername/your-repo-name.git)

# Navigate into the project directory
cd your-repo-name

# Clean the build and start the Spring Boot application
.\mvnw clean spring-boot:run

The application will boot up on http://localhost:8080.

🧪 How to Test the Architecture
Because this Gateway enforces strict edge security, navigating to localhost:8080 in a standard web browser will result in a 401 Unauthorized error.

To test the system, use an API client like Talend API Tester, Postman, or PowerShell.

Option 1: Testing via API Client (Recommended)
Open your API Client and set the request type to GET.

Enter the target URL: http://localhost:8080/todos/1

Add the following to the Headers section:

Key: Authorization

Value: Bearer my-secret-token

Click Send.

Option 2: Testing via PowerShell
PowerShell
Invoke-WebRequest -Uri "http://localhost:8080/todos/1" -Headers @{"Authorization"="Bearer my-secret-token"}


🔍 Expected Behaviors
By repeating the test request, you can actively observe the distributed system mechanics:

Load Balancing: Press "Send" multiple times. You will see the JSON response completely change structures as the Gateway mathematically alternates your traffic between two distinct backend APIs (jsonplaceholder.typicode.com and dummyjson.com).

Rate Limiting: Press "Send" rapidly (6+ times in under a second). The Gateway will sever the connection and return a 429 Too Many Requests error to protect the backend. Check the response headers to see the live Redis math (X-RateLimit-Remaining).

Edge Security: Remove the Authorization header and send a request. The Gateway will instantly drop the connection with a 401 Unauthorized.

Data Transformation: Check the response headers of any successful request to find X-Superpower: Gateway-Activated, which was secretly injected mid-flight by the Gateway's mutation filters.
