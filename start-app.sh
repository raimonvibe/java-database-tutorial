#!/bin/bash


echo "🚀 Starting Simple Tutorial App..."
echo "=================================="

command_exists() {
    command -v "$1" >/dev/null 2>&1
}

port_in_use() {
    lsof -i :$1 >/dev/null 2>&1
}

echo "📋 Checking prerequisites..."

if ! command_exists java; then
    echo "❌ Java is not installed. Please install Java 11 or higher."
    exit 1
fi

if ! command_exists mvn; then
    echo "❌ Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

if ! command_exists python3; then
    echo "❌ Python 3 is not installed. Please install Python 3."
    exit 1
fi

echo "✅ All prerequisites are installed!"

echo "🗄️ Checking PostgreSQL..."
if ! command_exists psql; then
    echo "⚠️ PostgreSQL client not found. You can use Docker instead."
    echo "Run: docker-compose up -d postgres"
else
    if pg_isready -h localhost -p 5432 >/dev/null 2>&1; then
        echo "✅ PostgreSQL is running!"
    else
        echo "⚠️ PostgreSQL is not running. Starting with Docker..."
        if command_exists docker-compose; then
            docker-compose up -d postgres
            echo "⏳ Waiting for PostgreSQL to start..."
            sleep 10
        else
            echo "❌ Please start PostgreSQL manually or install Docker."
            exit 1
        fi
    fi
fi

if port_in_use 8080; then
    echo "❌ Port 8080 is already in use. Please stop the service using this port."
    exit 1
fi

if port_in_use 3000; then
    echo "❌ Port 3000 is already in use. Please stop the service using this port."
    exit 1
fi

echo "🔧 Starting backend server..."
cd backend
mvn clean install -q
if [ $? -ne 0 ]; then
    echo "❌ Failed to build backend. Please check the error messages above."
    exit 1
fi

mvn spring-boot:run > ../backend.log 2>&1 &
BACKEND_PID=$!
echo "✅ Backend started (PID: $BACKEND_PID)"

echo "⏳ Waiting for backend to start..."
sleep 15

if ! port_in_use 8080; then
    echo "❌ Backend failed to start. Check backend.log for errors."
    kill $BACKEND_PID 2>/dev/null
    exit 1
fi

echo "🎨 Starting frontend server..."
cd ../frontend/public
python3 -m http.server 3000 > ../../frontend.log 2>&1 &
FRONTEND_PID=$!
echo "✅ Frontend started (PID: $FRONTEND_PID)"

sleep 3

echo ""
echo "🎉 Application started successfully!"
echo "=================================="
echo "📱 Frontend: http://localhost:3000"
echo "🔧 Backend API: http://localhost:8080/api/users"
echo "🗄️ Database: PostgreSQL on localhost:5432"
echo ""
echo "📋 Process IDs:"
echo "   Backend: $BACKEND_PID"
echo "   Frontend: $FRONTEND_PID"
echo ""
echo "📝 Logs:"
echo "   Backend: backend.log"
echo "   Frontend: frontend.log"
echo ""
echo "🛑 To stop the application:"
echo "   kill $BACKEND_PID $FRONTEND_PID"
echo "   or run: ./stop-app.sh"
echo ""
echo "🌐 Open http://localhost:3000 in your browser to use the app!"

echo "$BACKEND_PID" > .backend.pid
echo "$FRONTEND_PID" > .frontend.pid
