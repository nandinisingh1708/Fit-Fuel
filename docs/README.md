# FitFuel – Calorie Calculator & Personalized Meal Planner

## 🎯 Project Overview

**FitFuel** is a full-stack web application designed to help users achieve their fitness and health goals through intelligent calorie tracking and personalized meal planning. It bridges the gap between fitness aspirations and practical dietary management by providing users with data-driven insights into their caloric intake and personalized meal recommendations.

## 🌍 Real-World Problem & Solution

### Problem
- Users struggle to track their calorie intake manually across multiple meals throughout the day
- Generic meal plans don't account for individual metabolic rates, fitness goals, and dietary preferences
- Lack of integrated tools that combine calorie tracking with personalized meal planning
- Difficulty in maintaining consistency with fitness goals due to inadequate dietary guidance

### Solution
FitFuel solves these challenges by offering:
- **Automated calorie calculation** with meal logging and tracking
- **Personalized meal recommendations** based on individual metrics (age, weight, height, activity level, fitness goals)
- **Centralized user profiles** that store health metrics and preferences
- **Interactive dashboard** for monitoring daily caloric intake and meal history
- **User authentication & data security** ensuring privacy of health information

---

## ✨ Key Features

### 1. **User Authentication & Personalization**
   - Secure signup and login with JWT-based authentication
   - User profile creation with personal health metrics:
     - Age, weight, height, gender
     - Activity level (sedentary, lightly active, moderately active, very active, extremely active)
     - Fitness goals (weight loss, maintenance, weight gain)
   - Password encryption and secure session management

### 2. **Calorie Calculation Engine**
   - Automatic BMR (Basal Metabolic Rate) calculation based on user metrics
   - TDEE (Total Daily Energy Expenditure) estimation using activity level
   - Per-meal calorie computation
   - Daily caloric intake tracking and goal monitoring
   - Visual progress indicators showing consumed vs. recommended calories

### 3. **Meal Planning & Logging**
   - Add, edit, and delete meal entries with:
     - Meal name and description
     - Calorie values
     - Category (Breakfast, Lunch, Dinner, Snack)
     - Quantity tracking
     - Date and time logging
   - Meal history with filtering by date and category
   - Calorie accumulation throughout the day

### 4. **Responsive User Interface**
   - Mobile-first design with responsive breakpoints
   - Dark mode and light mode support
   - Interactive navigation and smooth animations
   - Real-time calorie counter updates
   - Glass-morphism design patterns for modern aesthetics
   - Intuitive forms and data visualization

### 5. **Secure Data Storage**
   - Persistent user profiles in SQL database
   - Encrypted password storage
   - Meal history retention and retrieval
   - Scalable database architecture

---

## 🏗️ Technical Architecture

### **Frontend Stack**
- **HTML5**: Semantic markup for accessibility
- **CSS3**: Advanced styling with:
  - CSS variables for theming
  - Flexbox and Grid layouts
  - Animations and transitions
  - Responsive media queries
  - Glass-morphism UI effects
- **JavaScript (Vanilla)**: Core interactivity with:
  - DOM manipulation
  - API communication (fetch)
  - Event handling
  - Form validation
  - Local storage for session management

### **Backend Stack**
- **Java 11**: Core application logic
- **Spring Boot 2.7.0**: Framework for rapid development
  - Spring Web MVC: RESTful API endpoints
  - Spring Data JPA: ORM and database abstraction
  - Spring Security: Authentication and authorization
- **JWT (JSON Web Tokens)**: Stateless authentication mechanism

### **Database Stack**
- **MySQL 8.0**: Relational database management
- **Hibernate ORM**: Object-relational mapping
- **JPA Entities**: User, Meal, Workout models

### **Key Dependencies**
```xml
- spring-boot-starter-web (REST API development)
- spring-boot-starter-data-jpa (Database operations)
- spring-boot-starter-security (Authentication/Authorization)
- mysql-connector-java (MySQL driver)
- jjwt (JWT handling)
- lombok (Boilerplate reduction)
```

---

## 📱 Frontend Implementation

### **User Interface Structure**

#### **Landing/Home Page** (`index.html`)
- Hero section introducing FitFuel
- Key features overview
- Call-to-action buttons for signup/login
- Responsive navigation bar with theme toggle
- Floating animated elements for visual engagement

#### **Meal Tracking Dashboard** (`meals.html`)
- Daily calorie counter with progress visualization
- Meal input form for logging foods
- Meal history list with edit/delete options
- Category-based meal organization
- Real-time calorie accumulation display

#### **Workout Tracking** (`workouts.html`)
- Workout logging interface
- Calorie burn calculation
- Exercise history and progress tracking
- Activity level management

### **JavaScript Functionality**
- **Form Handling**: Real-time validation and submission
- **API Communication**: Fetch requests to backend endpoints
  - User registration/login
  - Meal CRUD operations
  - User profile updates
- **State Management**: Local state for UI updates
- **User Experience**:
  - Loading states and error handling
  - Confirmation dialogs for destructive actions
  - Toast notifications for user feedback
  - Client-side data validation

### **Responsive Design**
- Mobile-first approach
- Breakpoints: 480px, 768px, 1024px, 1280px
- Touch-friendly button sizes (min 44x44px)
- Flexible layouts using CSS Grid and Flexbox
- Optimized images and lazy loading

---

## ⚙️ Backend Implementation

### **REST API Endpoints**

#### **Authentication Endpoints** (`/api/auth`)
```
POST /api/auth/signup
- Register new user with health metrics
- Input: username, email, password, age, weight, height, gender, activityLevel
- Returns: User profile and authentication status

POST /api/auth/signin
- Authenticate user and issue JWT token
- Input: username, password
- Returns: JWT token with user details
```

#### **User Management** (`/api/users`)
```
GET /api/users/{id}
- Retrieve user profile with personalized data

PUT /api/users/{id}
- Update user profile and health metrics

GET /api/users
- List all users (admin functionality)
```

#### **Meal Management** (`/api/meals`)
```
POST /api/meals
- Create new meal entry
- Input: name, calories, description, category, date, quantity
- Returns: Created meal object

GET /api/meals
- Retrieve all meals for authenticated user

GET /api/meals/{id}
- Get specific meal details

PUT /api/meals/{id}
- Update meal information

DELETE /api/meals/{id}
- Remove meal entry from history

GET /api/meals/daily-summary
- Get daily calorie totals and analysis
```

### **Core Java Classes**

#### **Models** (`model/`)
- **User**: User entity with:
  - Authentication credentials
  - Personal metrics (age, weight, height, gender)
  - Activity level and fitness goals
  - Timestamps (created, updated)
  - Implements UserDetails for Spring Security

- **Meal**: Meal entity with:
  - Name, calories, description
  - Category classification
  - Date and quantity tracking
  - Image URL for food visualization

- **Workout**: Exercise tracking entity
- **BaseEntity**: Abstract parent with ID and timestamps

#### **Services** (`service/`)
- **UserService**: User registration, profile management
- **MealService**: Meal CRUD operations, daily summaries
- **UserDetailsServiceImpl**: Spring Security integration for user authentication

#### **Controllers** (`controller/`)
- **AuthController**: Handles login/signup and JWT generation
- **UserController**: User profile endpoints
- **MealController**: Meal management endpoints

#### **Security** (`security/`)
- **JwtUtils**: JWT token generation and validation
- **JwtAuthenticationFilter**: Request filtering and token validation
- **JwtAuthEntryPoint**: Unauthorized access handling
- **WebSecurityConfig**: Spring Security configuration with:
  - CORS settings
  - Session management
  - Filter chain configuration
  - Password encoding (BCrypt)

#### **Repositories** (`repository/`)
- **UserRepository**: JPA queries for user operations
- **MealRepository**: Meal data access layer

### **Personalization Algorithm**

The system achieves personalization through:

1. **BMR Calculation** (Harris-Benedict Formula)
   ```
   BMR = 10 × weight(kg) + 6.25 × height(cm) - 5 × age(years) ± 5 (gender-dependent)
   ```

2. **TDEE Estimation** (BMR × Activity Factor)
   ```
   - Sedentary: BMR × 1.2
   - Lightly Active: BMR × 1.375
   - Moderately Active: BMR × 1.55
   - Very Active: BMR × 1.725
   - Extremely Active: BMR × 1.9
   ```

3. **Personalized Meal Recommendations**
   - Baseline: Daily caloric allowance = TDEE (maintenance)
   - Weight loss: TDEE - 500 cal (0.5 kg/week loss)
   - Weight gain: TDEE + 500 cal (0.5 kg/week gain)
   - Macro distribution: 40% carbs, 30% protein, 30% fats (adjustable by goal)

4. **User Input Parameters Used**
   - Age: Affects BMR calculations
   - Weight & Height: Primary BMR determinants
   - Gender: Metabolic rate modifier
   - Activity Level: Daily energy expenditure factor
   - Fitness Goals: Caloric surplus/deficit adjustment

---

## 💾 Database Schema

### **Users Table**
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) UNIQUE NOT NULL,
  email VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(120) NOT NULL,
  full_name VARCHAR(100),
  age INT,
  weight DOUBLE,
  height DOUBLE,
  gender VARCHAR(10),
  activity_level VARCHAR(50),
  fitness_goal VARCHAR(50),
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);
```

### **Meals Table**
```sql
CREATE TABLE meals (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  calories DOUBLE NOT NULL,
  description TEXT,
  category VARCHAR(50),
  date DATE NOT NULL,
  quantity INT DEFAULT 1,
  image_url VARCHAR(255),
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

### **Workouts Table**
```sql
CREATE TABLE workouts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  calories_burned DOUBLE,
  duration_minutes INT,
  date DATE NOT NULL,
  user_id BIGINT NOT NULL,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

## 🔒 Security Implementation

- **Password Encryption**: BCrypt hashing with salt
- **JWT Authentication**:
  - Stateless token-based authentication
  - 24-hour token expiration
  - Secure secret key (64+ characters)
  - Token validation on protected endpoints
- **CORS Configuration**:
  - Allowed origins: localhost:3000, localhost:5500, 127.0.0.1:5500
  - Methods: GET, POST, PUT, DELETE
  - Headers: Content-Type, Authorization
- **Input Validation**:
  - Server-side validation for all inputs
  - Size constraints on fields
  - Email format validation
  - Required field enforcement

---

## 🎓 Technical Skills Demonstrated

### **Full-Stack Development**
- Complete end-to-end application from database to user interface
- Understanding of MVC architecture pattern
- Client-server communication patterns
- Async programming with Promises and Fetch API

### **Backend Development**
- Spring Boot framework expertise
- Microservices-ready architecture
- RESTful API design principles
- Exception handling and error responses
- Request/response mapping
- Dependency injection and IoC concepts

### **Frontend Development**
- HTML5 semantic structure
- Advanced CSS with modern features
- Vanilla JavaScript DOM manipulation
- Responsive design implementation
- User experience optimization
- Accessibility considerations

### **Database Management**
- Relational database design
- Entity-relationship modeling
- JPA/Hibernate ORM patterns
- Query optimization
- Data integrity constraints
- Foreign key relationships

### **Software Engineering Practices**
- Version control (Git)
- Code organization and modularity
- Design patterns (MVC, Singleton, Factory)
- SOLID principles application
- Security best practices
- Testing and validation

---

## 🏥 User Benefits & Impact

### **Health & Fitness Benefits**
1. **Awareness**: Users gain understanding of their caloric intake and energy expenditure
2. **Goal Achievement**: Data-driven approach to weight management (loss, gain, or maintenance)
3. **Consistency**: Regular tracking promotes habit formation and accountability
4. **Personalization**: Recommendations tailored to individual needs, not generic plans

### **Practical Benefits**
1. **Time Saving**: Eliminates manual calculation and planning
2. **Accessibility**: Available anywhere with internet connection
3. **Data Retention**: Historical records for progress analysis
4. **Privacy**: Secure storage of sensitive health information

### **Long-Term Impact**
- Improved dietary choices through informed decision-making
- Better fitness outcomes through proper caloric management
- Development of sustainable healthy habits
- Confidence in health management through data-backed insights

---

## 🚀 Getting Started

### **Prerequisites**
- Java 11 or higher
- MySQL 8.0 or higher
- Node.js (if using a frontend build tool, optional)
- Maven 3.6+

### **Installation**

1. **Clone and navigate to project**
   ```bash
   cd "calorie calculator"
   ```

2. **Configure Database**
   ```bash
   # Update application.properties with your MySQL credentials
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build Backend**
   ```bash
   mvn clean install
   ```

4. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access Frontend**
   - Open `index.html` in a browser
   - Or use a local development server (Live Server, http-server, etc.)

### **API Base URL**
```
http://localhost:8080/api
```

---

## 📊 Project Structure

```
calorie-calculator/
├── src/
│   ├── main/
│   │   ├── java/com/calorie/calculator/
│   │   │   ├── controller/        # REST API endpoints
│   │   │   ├── service/           # Business logic
│   │   │   ├── model/             # JPA entities
│   │   │   ├── repository/        # Database access
│   │   │   ├── security/          # JWT & Spring Security
│   │   │   ├── payload/           # DTOs for request/response
│   │   │   └── CalorieCalculatorApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/                  # Unit & integration tests
│       └── resources/
│           └── application-test.properties
├── index.html                     # Home page
├── meals.html                     # Meal tracking page
├── workouts.html                  # Workout tracking page
├── pom.xml                        # Maven dependencies
└── README.md                      # Documentation

---

## 🔄 Data Flow

```
User Browser
    ↓ (HTML form submission)
Frontend (HTML/CSS/JS)
    ↓ (Fetch API call with JWT)
REST API (Spring Boot)
    ↓ (JPA queries)
Database (MySQL)
    ↓ (Results)
Service Layer (Business Logic)
    ↓ (Response mapping)
REST API (JSON response)
    ↓ (JavaScript processing)
Frontend (Dynamic Updates)
    ↓ (DOM manipulation)
User Browser (Updated UI)
```

---

## 🧪 Testing

### **Unit Tests** (`src/test/java/`)
- `UserServiceTest`: User registration and profile management
- `WebSecurityConfigTest`: Security configuration validation
- `JwtSecurityIntegrationTest`: JWT token generation and validation

### **Running Tests**
```bash
mvn test
```

---

## 📈 Future Enhancements

1. **Advanced Analytics**
   - Weekly/monthly calorie trends
   - Macro nutrient breakdown
   - Body composition tracking

2. **Social Features**
   - Friend connections
   - Meal sharing and recommendations
   - Leaderboards and challenges

3. **Integrations**
   - Fitness wearable device sync (Apple Watch, Fitbit)
   - Nutrition database API integration
   - Calendar integration

4. **Mobile App**
   - Native iOS/Android application
   - Offline functionality
   - Push notifications for goals

5. **AI Features**
   - Personalized meal suggestions using ML
   - Predictive goal achievement analysis
   - Intelligent macro recommendations

---

## 📝 License

This project is open-source and available for educational and personal use.

---

## 👨‍💻 Author

**Nandini Singh**  
Full-Stack Developer | Health Tech Enthusiast

---

## � Deployment Guide

### **Development Environment Setup**

#### **Windows**
```bash
# Install Java 11
# Download from https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

# Install MySQL Server
# Download from https://dev.mysql.com/downloads/mysql/

# Clone repository
git clone <repository-url>
cd "calorie calculator"

# Build with Maven
mvn clean install

# Run application
mvn spring-boot:run
```

#### **macOS**
```bash
# Install with Homebrew
brew install java11
brew install mysql

# Start MySQL service
brew services start mysql

# Clone and run
git clone <repository-url>
cd "calorie calculator"
mvn clean install
mvn spring-boot:run
```

#### **Linux (Ubuntu/Debian)**
```bash
# Install Java 11
sudo apt update
sudo apt install openjdk-11-jdk

# Install MySQL
sudo apt install mysql-server

# Start MySQL
sudo systemctl start mysql

# Clone and run
git clone <repository-url>
cd "calorie calculator"
mvn clean install
mvn spring-boot:run
```

### **Production Deployment**

#### **Option 1: Deploy to AWS**
```bash
# 1. Build JAR file
mvn clean package

# 2. Upload to EC2 instance
scp -i key.pem target/calorie-calculator-0.0.1-SNAPSHOT.jar ec2-user@your-instance:/home/ec2-user/

# 3. SSH into instance and run
ssh -i key.pem ec2-user@your-instance
java -jar calorie-calculator-0.0.1-SNAPSHOT.jar

# 4. Keep running with nohup
nohup java -jar calorie-calculator-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
```

#### **Option 2: Deploy with Docker**
```bash
# Create Dockerfile
FROM openjdk:11-jre-slim
COPY target/calorie-calculator-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build and run
docker build -t fitfuel:1.0 .
docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/calorie_calculator fitfuel:1.0
```

#### **Option 3: Deploy to Heroku**
```bash
# Install Heroku CLI
# https://devcenter.heroku.com/articles/heroku-cli

# Login to Heroku
heroku login

# Create app
heroku create fitfuel-app

# Set environment variables
heroku config:set spring.datasource.url=your-db-url
heroku config:set spring.datasource.username=your-username
heroku config:set spring.datasource.password=your-password
heroku config:set app.jwtSecret=your-secret-key

# Deploy
git push heroku main
```

#### **Option 4: Deploy to DigitalOcean**
```bash
# Create droplet with Ubuntu 20.04
# SSH into droplet

# Install dependencies
apt update && apt upgrade -y
apt install -y openjdk-11-jdk mysql-server nginx

# Clone repository
git clone <repository-url>
cd "calorie calculator"

# Build JAR
mvn clean package

# Run with systemd
sudo tee /etc/systemd/system/fitfuel.service << EOF
[Unit]
Description=FitFuel Application
After=network.target

[Service]
Type=simple
User=fitfuel
WorkingDirectory=/home/fitfuel/app
ExecStart=/usr/lib/jvm/java-11-openjdk-amd64/bin/java -jar calorie-calculator-0.0.1-SNAPSHOT.jar
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Start service
sudo systemctl start fitfuel
sudo systemctl enable fitfuel
```

### **Environment Configuration**

Create `application-prod.properties`:
```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database (Production)
spring.datasource.url=jdbc:mysql://prod-db-server:3306/calorie_calculator?useSSL=true&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate

# Security
app.jwtSecret=${JWT_SECRET}
app.jwtExpirationMs=${JWT_EXPIRATION}

# CORS
cors.allowed.origins=https://yourdomain.com,https://www.yourdomain.com
cors.allowed.methods=GET,POST,PUT,DELETE,OPTIONS
cors.allow.credentials=true

# Logging
logging.level.root=INFO
logging.level.com.calorie.calculator=DEBUG
logging.file.name=/var/log/fitfuel/app.log
```

### **SSL/HTTPS Setup**

```bash
# Using Let's Encrypt with Certbot
sudo apt install certbot python3-certbot-nginx
sudo certbot certonly --standalone -d yourdomain.com

# Add to application.properties
server.ssl.key-store=file:/etc/letsencrypt/live/yourdomain.com/keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
```

---

## 🔧 Troubleshooting Guide

### **Common Issues & Solutions**

#### **Issue 1: MySQL Connection Error**
```
Error: Communication link failure (nested exception is java.net.ConnectException)
```

**Solution:**
```bash
# Check if MySQL is running
mysql -u root -p

# If not running, start it
# Windows: net start MySQL80
# macOS: brew services start mysql
# Linux: sudo systemctl start mysql

# Update connection properties
# Ensure database exists:
CREATE DATABASE IF NOT EXISTS calorie_calculator;

# Verify credentials in application.properties
spring.datasource.username=root
spring.datasource.password=your_password
```

#### **Issue 2: Port 8080 Already in Use**
```
Address already in use: bind
```

**Solution:**
```bash
# Find process using port 8080
# Windows: netstat -ano | findstr :8080
# macOS/Linux: lsof -i :8080

# Kill the process or change port in application.properties
server.port=8081
```

#### **Issue 3: JWT Token Validation Fails**
```
Error: Invalid JWT token
```

**Solution:**
```bash
# Ensure JWT secret is set correctly (>64 characters recommended)
app.jwtSecret=yourSecretKeyShouldBeLongAndSecureAtLeast64CharactersLong1234567890

# Verify token expiration is reasonable
app.jwtExpirationMs=86400000  # 24 hours in milliseconds

# Check token is included in Authorization header: Bearer <token>
```

#### **Issue 4: CORS Error in Frontend**
```
Access to XMLHttpRequest has been blocked by CORS policy
```

**Solution:**
```properties
# Update application.properties
cors.allowed.origins=http://localhost:3000,http://localhost:5500,http://127.0.0.1:5500
cors.allowed.methods=GET,POST,PUT,DELETE,OPTIONS
cors.allowed.headers=Content-Type,Authorization
cors.allow.credentials=true
```

#### **Issue 5: Hibernate DDL-Auto Errors**
```
Error executing DDL "create table users..."
```

**Solution:**
```properties
# Set appropriate ddl-auto value
spring.jpa.hibernate.ddl-auto=create  # Fresh start (development only)
spring.jpa.hibernate.ddl-auto=update  # Add new columns (safe)
spring.jpa.hibernate.ddl-auto=validate  # Check schema matches entities (production)
```

#### **Issue 6: Frontend Cannot Connect to Backend**
```
Failed to fetch from http://localhost:8080/api/...
```

**Solution:**
```javascript
// Check API URL in frontend code
const API_URL = 'http://localhost:8080/api';

// Ensure backend is running on correct port
// Verify CORS is properly configured
// Check browser DevTools Network tab for actual request
```

#### **Issue 7: Password Encoding Issues**
```
Error: Encoded password does not look like BCrypt
```

**Solution:**
```java
// Ensure PasswordEncoder bean is configured in WebSecurityConfig
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

// Re-register users after fixing configuration
```

### **Debug Mode**

Enable detailed logging:

```properties
# application-debug.properties
logging.level.root=DEBUG
logging.level.com.calorie.calculator=TRACE
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Log SQL queries and parameters
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Run with debug profile:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=debug"
```

### **Performance Troubleshooting**

```properties
# Connection pooling
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000

# Lazy loading to avoid N+1 queries
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true

# Batch processing
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

---

## 📚 Detailed API Documentation

### **Authentication Endpoints**

#### **Register User**
```
POST /api/auth/signup
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePassword123!",
  "fullName": "John Doe",
  "age": 28,
  "weight": 75.5,
  "height": 180,
  "gender": "MALE",
  "activityLevel": "MODERATELY_ACTIVE"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "age": 28,
  "weight": 75.5,
  "height": 180,
  "gender": "MALE",
  "activityLevel": "MODERATELY_ACTIVE",
  "createdAt": "2026-02-01T10:30:00Z",
  "updatedAt": "2026-02-01T10:30:00Z"
}
```

**Error Response (400 Bad Request):**
```json
{
  "error": "Error: Username is already taken!"
}
```

---

#### **Login User**
```
POST /api/auth/signin
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "SecurePassword123!"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "john_doe",
  "email": "john@example.com"
}
```

**Usage with Token:**
```javascript
const token = response.data.token;
const headers = {
  'Authorization': `Bearer ${token}`,
  'Content-Type': 'application/json'
};

// Use in all subsequent requests
fetch('http://localhost:8080/api/meals', { headers });
```

---

### **User Management Endpoints**

#### **Get User Profile**
```
GET /api/users/{id}
Authorization: Bearer <token>
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "age": 28,
  "weight": 75.5,
  "height": 180,
  "gender": "MALE",
  "activityLevel": "MODERATELY_ACTIVE",
  "dailyCalorieGoal": 2450,
  "createdAt": "2026-02-01T10:30:00Z",
  "updatedAt": "2026-02-01T10:30:00Z"
}
```

---

#### **Update User Profile**
```
PUT /api/users/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "weight": 73.0,
  "activityLevel": "VERY_ACTIVE",
  "fullName": "John Doe Updated"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe Updated",
  "age": 28,
  "weight": 73.0,
  "height": 180,
  "gender": "MALE",
  "activityLevel": "VERY_ACTIVE",
  "updatedAt": "2026-02-01T14:22:00Z"
}
```

---

### **Meal Management Endpoints**

#### **Add Meal**
```
POST /api/meals
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Grilled Chicken Salad",
  "calories": 450.0,
  "description": "Grilled chicken breast with mixed greens",
  "category": "LUNCH",
  "date": "2026-02-01",
  "quantity": 1,
  "imageUrl": "https://example.com/images/salad.jpg"
}
```

**Response (201 Created):**
```json
{
  "id": 101,
  "name": "Grilled Chicken Salad",
  "calories": 450.0,
  "description": "Grilled chicken breast with mixed greens",
  "category": "LUNCH",
  "date": "2026-02-01",
  "quantity": 1,
  "imageUrl": "https://example.com/images/salad.jpg",
  "createdAt": "2026-02-01T12:30:00Z"
}
```

---

#### **Get User's Meals**
```
GET /api/meals
Authorization: Bearer <token>
```

**Query Parameters:**
- `date`: Filter by date (format: YYYY-MM-DD)
- `category`: Filter by category (BREAKFAST, LUNCH, DINNER, SNACK)
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)

**Example:**
```
GET /api/meals?date=2026-02-01&category=LUNCH
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 101,
      "name": "Grilled Chicken Salad",
      "calories": 450.0,
      "category": "LUNCH",
      "date": "2026-02-01",
      "quantity": 1,
      "createdAt": "2026-02-01T12:30:00Z"
    },
    {
      "id": 102,
      "name": "Spaghetti Carbonara",
      "calories": 650.0,
      "category": "LUNCH",
      "date": "2026-02-01",
      "quantity": 1,
      "createdAt": "2026-02-01T12:45:00Z"
    }
  ],
  "pageable": {
    "size": 20,
    "number": 0,
    "totalElements": 2,
    "totalPages": 1
  }
}
```

---

#### **Get Daily Calorie Summary**
```
GET /api/meals/daily-summary
Authorization: Bearer <token>
```

**Query Parameters:**
- `date`: Date to summarize (format: YYYY-MM-DD)

**Response (200 OK):**
```json
{
  "date": "2026-02-01",
  "totalCaloriesConsumed": 1820.0,
  "dailyGoal": 2450.0,
  "caloriesRemaining": 630.0,
  "percentageOfGoal": 74.29,
  "mealBreakdown": {
    "BREAKFAST": 420.0,
    "LUNCH": 800.0,
    "DINNER": 550.0,
    "SNACK": 50.0
  },
  "mealsCount": 4
}
```

---

#### **Update Meal**
```
PUT /api/meals/{id}
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Grilled Chicken Salad with Dressing",
  "calories": 480.0,
  "description": "Updated description"
}
```

**Response (200 OK):**
```json
{
  "id": 101,
  "name": "Grilled Chicken Salad with Dressing",
  "calories": 480.0,
  "description": "Updated description",
  "category": "LUNCH",
  "date": "2026-02-01",
  "updatedAt": "2026-02-01T14:00:00Z"
}
```

---

#### **Delete Meal**
```
DELETE /api/meals/{id}
Authorization: Bearer <token>
```

**Response (204 No Content)**

---

### **Error Responses**

#### **Unauthorized (401)**
```json
{
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

#### **Forbidden (403)**
```json
{
  "error": "Forbidden",
  "message": "You don't have permission to access this resource"
}
```

#### **Not Found (404)**
```json
{
  "error": "Not Found",
  "message": "Meal with id 999 not found"
}
```

#### **Validation Error (400)**
```json
{
  "error": "Bad Request",
  "message": "Calories must be greater than 0",
  "timestamp": "2026-02-01T10:30:00Z"
}
```

---

## 📊 API Usage Examples

### **JavaScript/Fetch**
```javascript
// 1. Register
const registerResponse = await fetch('http://localhost:8080/api/auth/signup', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: 'john_doe',
    email: 'john@example.com',
    password: 'SecurePassword123!',
    fullName: 'John Doe',
    age: 28,
    weight: 75.5,
    height: 180,
    gender: 'MALE',
    activityLevel: 'MODERATELY_ACTIVE'
  })
});

// 2. Login
const loginResponse = await fetch('http://localhost:8080/api/auth/signin', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: 'john_doe',
    password: 'SecurePassword123!'
  })
});

const { token } = await loginResponse.json();

// 3. Add Meal
const mealResponse = await fetch('http://localhost:8080/api/meals', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify({
    name: 'Grilled Chicken Salad',
    calories: 450.0,
    category: 'LUNCH',
    date: '2026-02-01',
    description: 'Healthy lunch option'
  })
});

// 4. Get Daily Summary
const summaryResponse = await fetch(
  'http://localhost:8080/api/meals/daily-summary?date=2026-02-01',
  {
    headers: { 'Authorization': `Bearer ${token}` }
  }
);

const summary = await summaryResponse.json();
console.log(`Calories consumed: ${summary.totalCaloriesConsumed}/${summary.dailyGoal}`);
```

### **cURL Examples**
```bash
# Register
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "SecurePassword123!",
    "fullName": "John Doe",
    "age": 28,
    "weight": 75.5,
    "height": 180,
    "gender": "MALE",
    "activityLevel": "MODERATELY_ACTIVE"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePassword123!"
  }'

# Add Meal (replace TOKEN with actual token)
curl -X POST http://localhost:8080/api/meals \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer TOKEN" \
  -d '{
    "name": "Grilled Chicken Salad",
    "calories": 450.0,
    "category": "LUNCH",
    "date": "2026-02-01"
  }'

# Get Daily Summary
curl http://localhost:8080/api/meals/daily-summary?date=2026-02-01 \
  -H "Authorization: Bearer TOKEN"
```

---

## �📧 Contact & Support

For questions, issues, or feedback, please reach out through the project's issue tracker or contact the development team.

---

## 📚 Complete Documentation

FitFuel includes comprehensive documentation for users, developers, and administrators:

### User Documentation
- **[USER_GUIDE.md](USER_GUIDE.md)** - Complete user guide with getting started, features, settings, and FAQ

### Developer Documentation
- **[INSTALLATION.md](INSTALLATION.md)** - Installation guide for Windows, macOS, and Linux
- **[API_INTEGRATION.md](API_INTEGRATION.md)** - Complete API reference with 21+ endpoints

### Configuration
- **.env.example** - Environment configuration template
- **Dockerfile** - Multi-stage Docker build
- **docker-compose.yml** - Complete stack orchestration
- **nginx.conf** - Web server configuration
- **.github/workflows/deploy.yml** - CI/CD pipeline

---

## 🚀 Quick Start with Docker

```bash
git clone https://github.com/yourusername/fitfuel.git
cd fitfuel
docker-compose up -d
# Frontend: http://localhost:5500
```

---

**FitFuel** - Transforming how people manage their health, one meal at a time. 🚀

*Last Updated: 2024-11-30 | Version: 1.0.0*
