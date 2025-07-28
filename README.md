# 💼 JP Morgan Midas Core – Software Engineering Simulation

A Spring Boot microservice designed as part of the **J.P. Morgan Chase & Co. Software Engineering Virtual Experience Program** hosted on [Forage](https://www.theforage.com/).  
This simulation mimics a simplified financial transaction processing engine, focusing on real-time data streaming, in-memory persistence, external API integration, and RESTful communication.

---

## 📌 Project Overview

**Midas Core** serves as the central backend service of a financial platform, capable of:

- Receiving and validating financial transactions
- Publishing transaction messages to a Kafka topic
- Listening to and processing Kafka messages
- Integrating with an external Incentive API
- Updating and persisting user balances using H2 Database
- Exposing REST APIs for publishing transactions and retrieving balances

---

## 🔧 Tech Stack

| Layer            | Tools / Frameworks                     |
|------------------|----------------------------------------|
| Language         | Java 17                                |
| Framework        | Spring Boot                            |
| Messaging        | Apache Kafka                           |
| Database         | H2 (in-memory)                         |
| ORM              | Spring Data JPA                        |
| Build Tool       | Maven                                  |
| API Client       | RestTemplate                           |
| API Protocol     | REST (HTTP GET, POST)                  |
| Testing          | Postman / Curl                         |

---

## 🚀 Setup Instructions

### 🔁 Prerequisites


- Java 17+
- Maven
- Apache Kafka (running locally)
- External Incentive API JAR (provided in the simulation)

### 📦 Clone & Build


```bash
git clone https://github.com/NaveenDeveloperR/jp-morgan-midas-core.git
cd jp-morgan-midas-core
mvn clean install
```
### ▶️ Start Services (Windows Instructions)

---

#### 1️⃣ Start Zookeeper

Navigate to your Kafka directory and run:

```bash
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```
2️⃣ Start Kafka Broker
Open a new terminal window (still in the Kafka directory), and run:

```bash
.\bin\windows\kafka-server-start.bat .\config\server.properties
```
3️⃣ Start the Incentive API (Mock JAR)
Navigate to the folder where the transaction-incentive-api.jar is located and run:


```bash
java -jar services/transaction-incentive-api.jar --server.port=8081

```
This mock service simulates an external API that provides incentive values.

4️⃣ Run the Midas Core Application
From the root of your cloned project, run:

```bash
mvn spring-boot:run
```

The backend will start on: http://localhost:33400

## 🎯 Objective of the Simulation

• Integrate **Apache Kafka** to publish and consume transaction data.  
• Use **Spring Data JPA** with **H2** for in-memory persistence.  
• Fetch **incentive bonuses** from an external **REST API**.  
• Expose **REST endpoints** to interact with the system.


