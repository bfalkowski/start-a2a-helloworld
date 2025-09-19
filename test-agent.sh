#!/bin/bash

# A2A HelloWorld Agent Test Script
# Tests the deployed agent endpoints

# Check if URL is provided
if [ $# -eq 0 ]; then
    echo "❌ Error: Please provide the agent URL as a command line argument"
    echo "Usage: $0 <agent-url>"
    echo "Example: $0 https://your-agent.herokuapp.com"
    exit 1
fi

AGENT_URL="$1"

echo "🤖 Testing A2A HelloWorld Agent at $AGENT_URL"
echo "=================================================="

# Test root endpoint
echo "📋 Root endpoint (/):"
curl -s "$AGENT_URL/" | jq '.' 2>/dev/null || curl -s "$AGENT_URL/"
echo -e "\n"

# Test agent card
echo "🎴 Agent Card (/agent/card):"
curl -s "$AGENT_URL/agent/card" | jq '.' 2>/dev/null || curl -s "$AGENT_URL/agent/card"
echo -e "\n"

# Test health endpoint
echo "💚 Health Check (/agent/health):"
curl -s "$AGENT_URL/agent/health" | jq '.' 2>/dev/null || curl -s "$AGENT_URL/agent/health"
echo -e "\n"

# Test JSON-RPC greeting
echo "👋 JSON-RPC Greeting:"
curl -s -X POST "$AGENT_URL/jsonrpc" \
  -H "Content-Type: application/json" \
  -d '{"jsonrpc":"2.0","method":"greeting","params":{},"id":1}' | jq '.' 2>/dev/null || \
curl -s -X POST "$AGENT_URL/jsonrpc" \
  -H "Content-Type: application/json" \
  -d '{"jsonrpc":"2.0","method":"greeting","params":{},"id":1}'
echo -e "\n"

# Test JSON-RPC echo
echo "🔄 JSON-RPC Echo:"
curl -s -X POST "$AGENT_URL/jsonrpc" \
  -H "Content-Type: application/json" \
  -d '{"jsonrpc":"2.0","method":"echo","params":{"message":"Hello from test script!"},"id":2}' | jq '.' 2>/dev/null || \
curl -s -X POST "$AGENT_URL/jsonrpc" \
  -H "Content-Type: application/json" \
  -d '{"jsonrpc":"2.0","method":"echo","params":{"message":"Hello from test script!"},"id":2}'
echo -e "\n"

# Test JSON-RPC heroku-info
echo "☁️  JSON-RPC Heroku Info:"
curl -s -X POST "$AGENT_URL/jsonrpc" \
  -H "Content-Type: application/json" \
  -d '{"jsonrpc":"2.0","method":"heroku-info","params":{},"id":3}' | jq '.' 2>/dev/null || \
curl -s -X POST "$AGENT_URL/jsonrpc" \
  -H "Content-Type: application/json" \
  -d '{"jsonrpc":"2.0","method":"heroku-info","params":{},"id":3}'
echo -e "\n"

echo "✅ All tests completed!"
