## 🏨 Hotel Management API

A RESTful Spring Boot API for managing hotel data with core functionalities including:

* 🔍 **Get All Hotels** – Retrieve a complete list of available hotels.
* 🗑️ **Delete Hotel** – Remove a hotel by its ID.
* 📍 **Get Top 3 Nearest Hotels in a City** – Fetch the top 3 nearest hotels in a specified city, sorted by distance.

### 🔧 Tech Stack

* Java 17
* Spring Boot
* H2 / MySQL (configurable)
* JPA/Hibernate
* Maven

### 🚀 Endpoints Overview

GET    /hotels                     → List all hotels  
DELETE /hotels/{id}               → Delete a specific hotel  
GET    /search/{cityId}?sortBy=distance  → Get top 3 nearest hotels in a city

### 🧪 Testing

Includes JUnit/MockMvc-based test cases to validate core functionalities.
