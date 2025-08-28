#!/bin/bash


echo "🛑 Stopping Simple Tutorial App..."
echo "================================="

kill_process() {
    if [ -f "$1" ]; then
        PID=$(cat "$1")
        if kill -0 "$PID" 2>/dev/null; then
            echo "🔴 Stopping $2 (PID: $PID)..."
            kill "$PID"
            sleep 2
            if kill -0 "$PID" 2>/dev/null; then
                echo "⚠️ Force killing $2..."
                kill -9 "$PID"
            fi
            echo "✅ $2 stopped"
        else
            echo "ℹ️ $2 was not running"
        fi
        rm -f "$1"
    else
        echo "ℹ️ No $2 PID file found"
    fi
}

kill_process ".backend.pid" "Backend"

kill_process ".frontend.pid" "Frontend"

echo "🧹 Cleaning up any remaining processes..."

BACKEND_PIDS=$(lsof -ti:8080 2>/dev/null)
if [ ! -z "$BACKEND_PIDS" ]; then
    echo "🔴 Killing remaining backend processes..."
    echo "$BACKEND_PIDS" | xargs kill -9 2>/dev/null
fi

FRONTEND_PIDS=$(lsof -ti:3000 2>/dev/null)
if [ ! -z "$FRONTEND_PIDS" ]; then
    echo "🔴 Killing remaining frontend processes..."
    echo "$FRONTEND_PIDS" | xargs kill -9 2>/dev/null
fi

if [ -f "backend.log" ]; then
    rm backend.log
fi

if [ -f "frontend.log" ]; then
    rm frontend.log
fi

echo ""
echo "✅ Application stopped successfully!"
echo "🗄️ PostgreSQL is still running (if you want to stop it: docker-compose down)"
