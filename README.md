# 🏨 Hotel Management API

A simple yet powerful RESTful API built using **Spring Boot**, designed to manage hotel data efficiently. It includes features to list all hotels, delete a hotel, and find the top 3 nearest hotels in a given city.

---

## 🚀 Features

- **Get All Hotels** – Retrieve all available hotels from the database.
- **Delete a Hotel** – Remove a hotel record by its unique ID.
- **Top 3 Nearest Hotels** – Fetch the top 3 nearest hotels in a specific city sorted by distance.

---

## 📦 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 / MySQL** (configurable)
- **Maven**
- **Lombok**

---

## 🧠 API Endpoints

### 🔍 Get All Hotels

- **URL:** `/hotels`
- **Method:** `GET`
- **Response:** Returns a list of all hotel records.

---

### 🗑️ Delete a Hotel

- **URL:** `/hotels/{id}`
- **Method:** `DELETE`
- **Path Variable:** `id` – ID of the hotel to be deleted
- **Response:** `204 No Content` on successful deletion

---

### 📍 Get Top 3 Nearest Hotels in a City

- **URL:** `/search/{cityId}?sortBy=distance`
- **Method:** `GET`
- **Path Variable:** `cityId` – ID of the city
- **Query Param:** `sortBy=distance` (optional if default sorting is by distance)
- **Response:** List of top 3 hotels sorted by proximity

---

## 🧪 Running Locally

1. **Clone the repository**
   git clone https://github.com/your-username/hotel-management-api.git
   
   cd hotel-management-api
