# 💍 Jewelry Bazar

An elegant and full-featured jewelry shop application built with a modern tech stack. This project supports both frontend and backend, making it easy to manage jewelry brands, schemes, and store availability.

---

## 📦 Tech Stack

### 🔹 Backend
- Java 21
- Spring Boot
- MongoDB

### 🔹 Frontend
- React 

---

## 🚀 Features

- 📋 View available jewelry brands
- 📊 Explore gold schemes, eligibility, and offers
- 🌐 RESTful APIs to manage backend data
- 🗃️ MongoDB integration for brand/scheme data
- 🔐 Grace period and validation for user payments
- 🧾 Swagger API documentation

---

## ⚙️ Setup Instructions

### 🔹 Clone the Repo

    - git clone https://github.com/pranailikith14/jewelry-bazar.git
    - cd jewelry-bazar

🔹Backend Setup
    
    - cd backend
    - ./mvnw spring-boot:run

---

## 🧪 Running the Application with Profiles

To run the backend with the `dev` profile (which loads `application-dev.properties`):

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"

📦 API Endpoints
Method	Endpoint	Description
 - GET	/api/shops	Get all jewelry shops
 - POST	/api/shops/filter-by-brands	Filter shops by brand list (3 unique required)

🧪 Development Status
  - Brand listing from MongoDB
  - Grace period & early withdrawal logic
  - Swagger integration
  - CI/CD with Jenkins (in progress)
  - Deployment to AWS Dev server

🤝 Contributions
Feel free to raise issues or contribute via pull requests. Make sure to follow standard branching (feature/*, bugfix/*) and code formatting rules.

🙋‍♂️ Author
Made with ❤️ by Save The Tigers

