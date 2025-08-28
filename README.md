# 📚 Simple Java Tutorial App

A beginner-friendly tutorial application that demonstrates how to create a full-stack web application with:
- **Frontend**: Simple HTML/CSS/JavaScript
- **Backend**: Java with Spring Boot
- **Database**: PostgreSQL

This app allows you to add users through a web form and see them stored in a PostgreSQL database.

## 🌐 Live Demo

**[🚀 Visit Simple Java Tutorial App](http://localhost:3000)**

![Simple Java Tutorial App](./app-screenshot.png)

## 📋 What You'll Learn

- How to create a REST API with Java Spring Boot
- How to connect Java to a PostgreSQL database
- How to create a simple frontend that communicates with a backend
- How to handle form submissions and display data
- Basic CRUD operations (Create, Read, Delete)

## 🛠️ Prerequisites

Before running this application, make sure you have the following installed:

### Required Software:
1. **Java 11 or higher**
   - Download from: https://adoptium.net/
   - Check if installed: `java -version`

2. **Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi
   - Check if installed: `mvn -version`

3. **PostgreSQL 12 or higher**
   - Download from: https://www.postgresql.org/download/
   - Check if installed: `psql --version`

4. **Python 3** (for running the frontend)
   - Usually pre-installed on Mac/Linux
   - Windows: Download from https://python.org
   - Check if installed: `python3 --version`

## 🗄️ Database Setup

### Step 1: Start PostgreSQL Service

**On Windows:**
```bash
# Start PostgreSQL service
net start postgresql-x64-14
```

**On Mac (with Homebrew):**
```bash
# Start PostgreSQL service
brew services start postgresql
```

**On Linux (Ubuntu/Debian):**
```bash
# Start PostgreSQL service
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

### Step 2: Create Database and User

Open your terminal and connect to PostgreSQL:

```bash
# Connect to PostgreSQL as superuser
sudo -u postgres psql

# Or on Windows/Mac:
psql -U postgres
```

Once connected to PostgreSQL, run these commands:

```sql
-- Create a new database for our tutorial
CREATE DATABASE tutorial_db;

-- Create a new user for our application
CREATE USER tutorial_user WITH PASSWORD 'tutorial_password';

-- Grant all privileges on the database to our user
GRANT ALL PRIVILEGES ON DATABASE tutorial_db TO tutorial_user;

-- Exit PostgreSQL
\q
```

### Step 3: Verify Database Connection

Test that you can connect with the new user:

```bash
psql -h localhost -U tutorial_user -d tutorial_db
```

Enter the password `tutorial_password` when prompted. If successful, you'll see the PostgreSQL prompt.

## 🚀 Running the Application

### Local Development Setup

#### Step 1: Clone/Download the Project

If you haven't already, make sure you have all the project files in a folder called `java-tutorial-app`.

#### Step 2: Start the Backend (Java Spring Boot)

Open a terminal and navigate to the backend directory:

```bash
cd java-tutorial-app/backend
```

Install dependencies and start the backend server:

```bash
# Install dependencies and compile the project
mvn clean install

# Start the backend server
mvn spring-boot:run
```

You should see output like:
```
🚀 Backend server is running on http://localhost:8080
```

**Keep this terminal open** - the backend server needs to keep running.

#### Step 3: Start the Frontend

Open a **new terminal** and navigate to the frontend directory:

```bash
cd java-tutorial-app/frontend/public
```

Start the frontend server:

```bash
# Start the frontend server
python3 -m http.server 3000
```

You should see output like:
```
Serving HTTP on 0.0.0.0 port 3000 (http://0.0.0.0:3000/) ...
```

#### Step 4: Open the Application

Open your web browser and go to:
```
http://localhost:3000
```

You should see the Simple Tutorial App interface!

---

## ☁️ Cloud Deployment

### Deployment Platform Options

#### ✅ **Render.com (Recommended)**
- **Best for:** Full-stack Java Spring Boot applications
- **Pros:** Native Java support, free PostgreSQL database, easy GitHub integration
- **Cons:** Cold starts on free tier
- **Cost:** Free tier available

#### ✅ **Railway**
- **Best for:** Full-stack applications with simple deployment
- **Pros:** Excellent developer experience, automatic deployments
- **Cons:** Limited free tier
- **Cost:** Usage-based pricing

#### ✅ **Heroku**
- **Best for:** Traditional deployment with extensive add-ons
- **Pros:** Mature platform, lots of documentation
- **Cons:** No free tier anymore
- **Cost:** Paid plans only

#### ❌ **Vercel (NOT Suitable)**
- **Why not:** Vercel is designed for frontend applications and serverless functions only
- **Limitation:** Cannot run full Java Spring Boot applications
- **Alternative:** Use Vercel for frontend + separate backend hosting

### Deploying to Render.com (Step-by-Step)

#### Prerequisites
1. **GitHub Account** - Push your code to a GitHub repository
2. **Render.com Account** - Sign up at https://render.com

#### Step 1: Prepare Your Code for Deployment

1. **Push to GitHub:**
```bash
cd java-tutorial-app
git init
git add .
git commit -m "Initial commit - Java tutorial app"
git branch -M main
git remote add origin https://github.com/yourusername/java-tutorial-app.git
git push -u origin main
```

2. **Update Frontend API URL:**
Edit `frontend/public/script.js` and replace `localhost:8080` with your future backend URL:
```javascript
// Change this line:
const API_BASE_URL = 'http://localhost:8080/api/users';
// To this (you'll get the actual URL after backend deployment):
const API_BASE_URL = 'https://your-backend-name.onrender.com/api/users';
```

#### Step 2: Deploy PostgreSQL Database

1. **Login to Render.com** and click "New +"
2. **Select "PostgreSQL"**
3. **Configure Database:**
   - **Name:** `tutorial-database` (or your preferred name)
   - **Database:** `tutorial_db`
   - **User:** `tutorial_user`
   - **Region:** Choose closest to your location
   - **Plan:** Free (or paid for better performance)
4. **Click "Create Database"**
5. **Save Connection Details:** Note the External Database URL provided

#### Step 3: Deploy Backend (Spring Boot)

1. **Click "New +" → "Web Service"**
2. **Connect GitHub Repository:**
   - Select your `java-tutorial-app` repository
   - **Branch:** `main`
3. **Configure Service:**
   - **Name:** `tutorial-backend` (or your preferred name)
   - **Region:** Same as your database
   - **Branch:** `main`
   - **Root Directory:** `backend`
   - **Runtime:** `Java`
   - **Build Command:** `./mvnw clean install`
   - **Start Command:** `java -jar target/simple-tutorial-backend-1.0.0.jar`
4. **Environment Variables:**
   Click "Advanced" and add these environment variables:
   ```
   SPRING_DATASOURCE_URL=<your-database-external-url>
   SPRING_DATASOURCE_USERNAME=<your-database-username>
   SPRING_DATASOURCE_PASSWORD=<your-database-password>
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
   ```
5. **Click "Create Web Service"**

#### Step 4: Deploy Frontend

1. **Update Frontend API URL:**
   Replace the API URL in `frontend/public/script.js` with your backend URL:
   ```javascript
   const API_BASE_URL = 'https://tutorial-backend.onrender.com/api/users';
   ```

2. **Create Frontend Service:**
   - **Click "New +" → "Static Site"**
   - **Connect same GitHub repository**
   - **Name:** `tutorial-frontend`
   - **Branch:** `main`
   - **Root Directory:** `frontend/public`
   - **Build Command:** (leave empty)
   - **Publish Directory:** `.`

3. **Click "Create Static Site"**

#### Step 5: Test Your Deployment

1. **Access your frontend** at the provided Render URL
2. **Test adding users** through the web interface
3. **Verify data persistence** by refreshing the page

### Deployment Troubleshooting

#### Common Issues:

**Backend won't start:**
- Check build logs for Maven errors
- Verify environment variables are set correctly
- Ensure database is running and accessible
- **"mvn: command not found" error:** Use `./mvnw clean install` instead of `mvn clean install` (Maven wrapper is included in this project)

**Frontend can't connect to backend:**
- Verify API URL in `script.js` matches your backend URL
- Check CORS settings in Spring Boot (already configured in this project)
- Ensure backend is running and healthy

**Database connection failed:**
- Double-check database credentials
- Verify database is in the same region as your backend
- Check firewall/network settings

---

## 🐘 Database Management with pgAdmin

### Installing pgAdmin on macOS

#### Option 1: Homebrew (Recommended)
```bash
# Install Homebrew if you don't have it
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install pgAdmin
brew install --cask pgadmin4
```

#### Option 2: Direct Download
1. Visit https://www.pgadmin.org/download/pgadmin-4-macos/
2. Download the latest version
3. Install the .dmg file

### Connecting to Local Database

#### Step 1: Start Local PostgreSQL
```bash
cd java-tutorial-app
docker-compose up -d postgres
```

#### Step 2: Configure pgAdmin Connection

1. **Open pgAdmin** (Applications → pgAdmin 4)
2. **Create Server Connection:**
   - Right-click "Servers" in the left panel
   - Select "Create" → "Server..."

3. **General Tab:**
   - **Name:** `Tutorial Local Database` (or any descriptive name)

4. **Connection Tab:**
   - **Host name/address:** `localhost`
   - **Port:** `5432`
   - **Maintenance database:** `tutorial_db`
   - **Username:** `tutorial_user`
   - **Password:** `tutorial_password`
   - **Save password:** ✅ (optional, for convenience)

5. **Click "Save"**

#### Step 3: Navigate to Your Data

Once connected, expand the tree:
```
Servers 
└── Tutorial Local Database
    └── Databases
        └── tutorial_db
            └── Schemas
                └── public
                    └── Tables
                        └── users
```

#### Step 4: View and Edit Data

**To view all users:**
1. Right-click on "users" table
2. Select "View/Edit Data" → "All Rows"
3. Your user data will appear in a spreadsheet-like interface

**To run custom queries:**
1. Right-click on "tutorial_db"
2. Select "Query Tool"
3. Write SQL queries like:
```sql
-- View all users
SELECT * FROM users;

-- Count total users
SELECT COUNT(*) FROM users;

-- Find users by email domain
SELECT * FROM users WHERE email LIKE '%@example.com';
```

### Connecting to Cloud Database (Render.com)

#### Step 1: Get Database Credentials
1. **Login to Render.com**
2. **Go to your PostgreSQL database**
3. **Copy connection details:**
   - External Database URL
   - Username
   - Password
   - Database name

#### Step 2: Configure pgAdmin for Cloud Database

1. **Create new server connection** in pgAdmin
2. **General Tab:**
   - **Name:** `Tutorial Cloud Database`

3. **Connection Tab:**
   - **Host:** Extract from your database URL (e.g., `dpg-xxxxx-a.oregon-postgres.render.com`)
   - **Port:** `5432`
   - **Database:** `tutorial_db`
   - **Username:** From Render dashboard
   - **Password:** From Render dashboard
   - **SSL Mode:** `Require`

4. **Click "Save"**

### Database Management Tips

#### Backup Your Data
```sql
-- Export users to CSV (run in Query Tool)
COPY users TO '/path/to/backup/users.csv' DELIMITER ',' CSV HEADER;
```

#### Monitor Database Performance
- Use pgAdmin's "Dashboard" tab to monitor connections and activity
- Check "Statistics" tab for table sizes and query performance

#### Common SQL Operations
```sql
-- Add a new user manually
INSERT INTO users (name, email) VALUES ('Manual User', 'manual@example.com');

-- Update user information
UPDATE users SET name = 'Updated Name' WHERE id = 1;

-- Delete specific user
DELETE FROM users WHERE email = 'unwanted@example.com';

-- Reset auto-increment ID
ALTER SEQUENCE users_id_seq RESTART WITH 1;
```

### pgAdmin Troubleshooting

#### Connection Issues:

**"Connection refused":**
- Verify PostgreSQL is running: `docker ps`
- Check if port 5432 is available: `lsof -i :5432`
- Try `127.0.0.1` instead of `localhost`

**"Authentication failed":**
- Double-check username and password
- Verify credentials in `docker-compose.yml`
- Ensure database exists: `docker-compose logs postgres`

**"SSL connection required" (for cloud databases):**
- Set SSL Mode to "Require" in connection settings
- For Render.com, SSL is mandatory for external connections

#### Performance Issues:

**Slow queries:**
- Add indexes for frequently searched columns
- Use EXPLAIN ANALYZE to understand query performance
- Consider connection pooling for high-traffic applications

**Connection limits:**
- Free tier databases have connection limits
- Close unused connections in pgAdmin
- Monitor active connections in Dashboard

## 🎯 How to Use the App

1. **Add a User**: 
   - Fill in the "Name" and "Email" fields
   - Click "Add User"
   - You should see a success message

2. **View Users**: 
   - All users are automatically displayed below the form
   - Each user shows their ID, name, and email

3. **Delete a User**: 
   - Click the "🗑️ Delete" button next to any user
   - Confirm the deletion in the popup

4. **Refresh the List**: 
   - Click "🔄 Refresh List" to reload users from the database

## 🔧 Troubleshooting

### Backend Won't Start

**Problem**: `Connection refused` or database errors

**Solution**: 
1. Make sure PostgreSQL is running: `sudo systemctl status postgresql`
2. Check database credentials in `backend/src/main/resources/application.properties`
3. Verify you can connect manually: `psql -h localhost -U tutorial_user -d tutorial_db`

### Frontend Can't Connect to Backend

**Problem**: "Error connecting to server" messages

**Solution**:
1. Make sure the backend is running on port 8080
2. Check the browser console for CORS errors
3. Verify the API URL in `frontend/public/script.js` is correct

### Port Already in Use

**Problem**: `Port 8080 is already in use`

**Solution**:
1. Kill the process using the port: `lsof -ti:8080 | xargs kill -9`
2. Or change the port in `application.properties`: `server.port=8081`

### Maven Build Fails

**Problem**: Maven can't download dependencies

**Solution**:
1. Check your internet connection
2. Clear Maven cache: `mvn clean`
3. Try: `mvn dependency:resolve`

## 📁 Project Structure

```
java-tutorial-app/
├── backend/                          # Java Spring Boot backend
│   ├── src/main/java/com/tutorial/
│   │   ├── Application.java          # Main application entry point
│   │   ├── User.java                 # User entity (database model)
│   │   ├── UserRepository.java       # Database operations interface
│   │   └── UserController.java       # REST API endpoints
│   ├── src/main/resources/
│   │   └── application.properties    # Database and server configuration
│   └── pom.xml                       # Maven dependencies and build config
├── frontend/                         # Simple HTML/CSS/JS frontend
│   ├── public/
│   │   ├── index.html               # Main HTML page
│   │   ├── styles.css               # CSS styling
│   │   └── script.js                # JavaScript for API calls
│   └── package.json                 # Frontend project info
└── README.md                        # This file
```

## 🎓 Learning Points

### Backend (Java Spring Boot):

1. **Application.java**: Entry point that starts the Spring Boot server
2. **User.java**: Entity class that maps to database table
3. **UserRepository.java**: Interface for database operations (Spring Data JPA)
4. **UserController.java**: REST API endpoints that handle HTTP requests
5. **application.properties**: Configuration for database connection

### Frontend (HTML/CSS/JavaScript):

1. **index.html**: Structure of the web page
2. **styles.css**: Styling and layout
3. **script.js**: JavaScript that makes API calls to the backend

### Database (PostgreSQL):

1. **Automatic table creation**: Spring Boot creates the `users` table automatically
2. **CRUD operations**: Create, Read, Delete operations through the API
3. **Data persistence**: Data is stored permanently in PostgreSQL

## 🔄 API Endpoints

The backend provides these REST API endpoints:

- `GET /api/users` - Get all users
- `POST /api/users` - Create a new user
- `GET /api/users/{id}` - Get a specific user by ID
- `DELETE /api/users/{id}` - Delete a user by ID

## 🎨 Customization Ideas

Once you understand the basics, try these modifications:

1. **Add more fields**: Add age, phone number, or address to the User entity
2. **Add validation**: Implement email format validation
3. **Add search**: Create an endpoint to search users by name
4. **Improve UI**: Add more styling or use a CSS framework
5. **Add editing**: Implement user update functionality

## 📚 Next Steps

After completing this tutorial, you might want to learn:

1. **Spring Security**: Add authentication and authorization
2. **Frontend Frameworks**: Try React, Vue, or Angular instead of vanilla JavaScript
3. **Testing**: Write unit tests for your backend
4. **Deployment**: Deploy your app to cloud platforms like Heroku or AWS
5. **Advanced Database**: Learn about relationships, indexes, and migrations

## 🆘 Getting Help

If you run into issues:

1. Check the console output for error messages
2. Look at the browser's developer console (F12)
3. Verify all services are running (PostgreSQL, backend, frontend)
4. Check that all ports are available (5432 for PostgreSQL, 8080 for backend, 3000 for frontend)

## 📄 License

This project is for educational purposes. Feel free to use and modify as needed for learning!

---

**Happy Learning! 🎉**
