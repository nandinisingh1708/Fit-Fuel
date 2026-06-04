# FitFuel - Installation & Setup Guide

## System Requirements

### Minimum Requirements
- **OS**: Windows 10+, macOS 10.14+, Ubuntu 18.04+
- **RAM**: 4 GB minimum (8 GB recommended)
- **Storage**: 2 GB available space
- **Browser**: Chrome 90+, Firefox 88+, Safari 14+, Edge 90+

### Development Requirements
- **Java**: JDK 11 or higher
- **Maven**: 3.6.0 or higher
- **MySQL**: 8.0 or higher
- **Node.js**: 14.0+ (optional, for frontend build tools)
- **Git**: 2.0 or higher

---

## Installation Guide

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/fitfuel.git
cd fitfuel
```

### Step 2: Database Setup

#### Windows
1. Install MySQL 8.0+ from [mysql.com](https://mysql.com)
2. Open MySQL Command Line or MySQL Workbench
3. Create database and user:

```sql
CREATE DATABASE fitfuel;
CREATE USER 'fitfuel'@'localhost' IDENTIFIED BY 'fitfuel123';
GRANT ALL PRIVILEGES ON fitfuel.* TO 'fitfuel'@'localhost';
FLUSH PRIVILEGES;
```

#### macOS (using Homebrew)
```bash
brew install mysql
brew services start mysql
mysql_secure_installation  # Follow prompts

# Then run SQL commands above
mysql -u root -p
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install mysql-server
sudo mysql_secure_installation  # Follow prompts

# Then run SQL commands
sudo mysql -u root -p
```

### Step 3: Backend Setup

#### Configure Database Connection

1. Navigate to `src/main/resources/`
2. Open `application.properties`
3. Update database credentials:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/fitfuel?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=fitfuel
spring.datasource.password=fitfuel123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create  # Creates tables on startup
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT Configuration
app.jwtSecret=MySecretKeyForJWT123456789MySecretKeyForJWT123456789  # Change this!
app.jwtExpirationInMs=86400000  # 24 hours
```

#### Build and Run Backend

```bash
# Navigate to project root
cd /path/to/fitfuel

# Build with Maven
mvn clean install

# Run the application
mvn spring-boot:run

# Or run the JAR file
java -jar target/calorie-calculator-0.0.1-SNAPSHOT.jar
```

**Backend will start on:** `http://localhost:8080`

Check logs for:
```
Tomcat started on port(s): 8080 (http)
```

### Step 4: Frontend Setup

#### Option A: Using a Simple HTTP Server

```bash
# Navigate to project root where HTML files are
cd /path/to/fitfuel

# Using Python 3
python -m http.server 5500

# Using Python 2
python -m SimpleHTTPServer 5500

# Using Node.js (if installed)
npx http-server -p 5500
```

**Frontend will be available at:** `http://localhost:5500`

#### Option B: Using VS Code Live Server

1. Install "Live Server" extension in VS Code
2. Right-click on `index.html`
3. Select "Open with Live Server"
4. Browser opens automatically (default: port 5500)

#### Option C: Using a Web Server (Production-ready)

**Apache:**
```bash
# Copy files to Apache web root
cp -r /path/to/fitfuel/* /var/www/html/fitfuel/

# Apache automatically serves on http://localhost
```

**Nginx:**
```bash
# Install and configure
sudo apt-get install nginx

# Create config file
sudo nano /etc/nginx/sites-available/fitfuel
```

Add configuration:
```nginx
server {
    listen 5500;
    server_name localhost;
    
    root /path/to/fitfuel;
    index index.html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
}
```

Enable and restart:
```bash
sudo ln -s /etc/nginx/sites-available/fitfuel /etc/nginx/sites-enabled/
sudo systemctl restart nginx
```

---

## Configuration

### Backend Configuration Details

#### JWT Secret Key
The app uses JWT for stateless authentication. Change the secret key in production:

```properties
# In application.properties
app.jwtSecret=YourVeryLongSecretKeyThatIsHardToGuess1234567890YourVeryLongSecretKey
```

Generate a strong secret:
```bash
openssl rand -base64 32
```

#### CORS Configuration
By default, CORS is configured for:
- `http://localhost:5500`
- `http://localhost:3000`
- `http://127.0.0.1:5500`

To add more origins, edit `WebSecurityConfig.java`:

```java
.allowedOrigins(
    "http://localhost:5500",
    "http://localhost:3000",
    "http://127.0.0.1:5500",
    "https://yourdomain.com"  // Add your domain
)
```

#### Database Dialect
For different databases, update Hibernate dialect in `application.properties`:

```properties
# PostgreSQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect
spring.datasource.url=jdbc:postgresql://localhost:5432/fitfuel

# SQLServer
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=fitfuel
```

### Frontend Configuration

#### API Base URL
By default, frontend expects backend at `http://localhost:8080/api`

To change, edit `js/utils.js`:

```javascript
class ApiClient {
    constructor(baseUrl = 'http://your-backend-url:8080/api') {
        this.baseUrl = baseUrl;
        // ...
    }
}
```

Or set environment variable in browser console:
```javascript
localStorage.setItem('apiBaseUrl', 'https://yourdomain.com/api');
```

---

## Testing the Setup

### 1. Test Backend API

```bash
# Test health endpoint
curl http://localhost:8080/api/health

# Register new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser@example.com",
    "password": "TestPassword123",
    "email": "testuser@example.com"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser@example.com",
    "password": "TestPassword123"
  }'
```

### 2. Test Frontend

1. Open `http://localhost:5500` in browser
2. You should see FitFuel homepage
3. Click "Sign Up" and create account
4. Login with credentials
5. Try logging a meal
6. Check dashboard for data

### 3. Check Database

```bash
# Connect to MySQL
mysql -u fitfuel -p
# Enter password: fitfuel123

# Select database
USE fitfuel;

# View tables
SHOW TABLES;

# Check users table
SELECT * FROM users;

# Check meals table
SELECT * FROM meals;
```

---

## Troubleshooting

### Issue: "Database connection refused"

**Solution:**
1. Verify MySQL is running:
   ```bash
   # Windows
   services.msc  # Look for MySQL80
   
   # macOS
   brew services list
   
   # Linux
   sudo systemctl status mysql
   ```

2. Check credentials in `application.properties`
3. Verify database exists: `SHOW DATABASES;`

### Issue: "Port 8080 already in use"

**Solution:**
1. Find process using port:
   ```bash
   # macOS/Linux
   lsof -i :8080
   
   # Windows
   netstat -ano | findstr :8080
   ```

2. Kill the process or change port in `application.properties`:
   ```properties
   server.port=8081
   ```

### Issue: "CORS error in browser"

**Solution:**
1. Ensure backend is running on correct port
2. Check CORS configuration in `WebSecurityConfig.java`
3. Verify frontend is making requests to correct API URL
4. Clear browser cache and cookies

### Issue: "JWT token expired"

**Solution:**
1. User needs to login again
2. Token expires after 24 hours
3. For shorter expiry, edit in `application.properties`:
   ```properties
   # 1 hour (in milliseconds)
   app.jwtExpirationInMs=3600000
   ```

### Issue: "Meals not saving"

**Solution:**
1. Check browser console for errors (F12 → Console)
2. Verify user is authenticated (check localStorage for token)
3. Check MySQL database has meals table: `DESC meals;`
4. Check backend logs for SQL errors

### Issue: "Changes not reflecting"

**Solution:**
1. Clear browser cache: Ctrl+Shift+Delete (Chrome)
2. Hard refresh: Ctrl+Shift+R or Cmd+Shift+R
3. Check if backend compiled changes: `mvn clean install`
4. Restart backend: Stop and rerun `mvn spring-boot:run`

---

## Development Workflow

### File Structure
```
fitfuel/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/calorie/calculator/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── security/
│   │   │       └── payload/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── dashboard.html
├── meals.html
├── workouts.html
├── progress.html
├── nutrition.html
├── settings.html
├── index.html
├── css/
│   ├── style.css
│   └── enhancements.css
├── js/
│   └── utils.js
├── pom.xml
└── README.md
```

### Making Changes

#### Backend Changes
1. Edit Java files in `src/main/java/`
2. Recompile: `mvn clean install`
3. Restart: Stop current process, run `mvn spring-boot:run`
4. Test with curl or Postman

#### Frontend Changes
1. Edit HTML/CSS/JS files
2. Browser auto-reloads with Live Server
3. Check browser console (F12) for errors
4. Test API integration with backend

### Git Workflow
```bash
# Create feature branch
git checkout -b feature/my-feature

# Make changes and commit
git add .
git commit -m "Add my feature"

# Push to remote
git push origin feature/my-feature

# Create pull request on GitHub
```

---

## Building for Production

### Backend

```bash
# Build optimized JAR
mvn clean package -DskipTests

# Run production JAR
java -jar target/calorie-calculator-0.0.1-SNAPSHOT.jar
```

### Frontend

1. Minify CSS and JavaScript (optional):
```bash
npm install -g csso-cli terser
csso css/style.css -o css/style.min.css
terser js/utils.js -o js/utils.min.js
```

2. Update HTML to use minified files:
```html
<link rel="stylesheet" href="css/style.min.css">
<script src="js/utils.min.js"></script>
```

3. Compress images (optional):
```bash
# Using ImageMagick
convert image.jpg -quality 80 image.jpg
```

### Environment Variables
Create `.env` file in project root:

```properties
# Database
DB_URL=jdbc:mysql://localhost:3306/fitfuel
DB_USERNAME=fitfuel
DB_PASSWORD=fitfuel123

# JWT
JWT_SECRET=YourVeryLongSecretKey1234567890
JWT_EXPIRATION=86400000

# CORS
CORS_ORIGINS=https://yourdomain.com,https://app.yourdomain.com

# Server
SERVER_PORT=8080
```

---

## Deployment

### Docker Deployment
See `Dockerfile` and `docker-compose.yml` in project root

### Cloud Platforms
- **Heroku**: See HEROKU_DEPLOY.md
- **AWS EC2**: See AWS_DEPLOY.md
- **DigitalOcean**: See DIGITALOCEAN_DEPLOY.md

---

## IDE Setup

### VS Code
```bash
# Extensions to install
- Java Extension Pack
- Spring Boot Extension Pack
- REST Client

# Settings in settings.json
{
  "java.home": "/path/to/jdk11",
  "maven.executable.path": "/path/to/maven/bin/mvn"
}
```

### IntelliJ IDEA
1. Import Maven project: File → Open → Select `pom.xml`
2. Wait for index to build
3. Create Run Configuration:
   - Type: Spring Boot
   - Main class: `CalorieCalculatorApplication`
   - VM options: `-Dspring.profiles.active=dev`

### Eclipse
1. Import Existing Maven Projects
2. Right-click project → Run As → Spring Boot App

---

## Getting Help

- **Documentation**: See README.md
- **API Reference**: See API_INTEGRATION.md
- **User Guide**: See USER_GUIDE.md
- **Issues**: GitHub Issues tab
- **Discussions**: GitHub Discussions tab

---

*Last Updated: 2024-11-30*
*Version: 1.0.0*
