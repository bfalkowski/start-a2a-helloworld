# A2A Agent Testing Guide

This document explains what happens when you test an A2A (Agent-to-Agent) HelloWorld agent using the provided test scripts.

## Overview

The test scripts perform a comprehensive evaluation of an A2A agent by testing multiple endpoints and communication protocols. This ensures the agent is functioning correctly and can communicate with other agents in an A2A ecosystem.

## Test Flow Breakdown

### 1. Root Endpoint Test (`GET /`)

**What it tests:** Basic agent information and endpoint discovery

**Request:**
```http
GET https://your-agent.herokuapp.com/
```

**What happens:**
- The agent returns a JSON response containing:
  - Agent name and description
  - Available endpoints (REST and JSON-RPC)
  - Usage examples
  - Status information

**Expected Response:**
```json
{
  "name": "A2A HelloWorld Agent",
  "description": "A simple A2A agent for testing connections on Heroku",
  "version": "1.0.0",
  "status": "UP",
  "endpoints": {
    "agent_discovery": ["/agent/card", "/agent/extendedCard", "/agent/authenticatedExtendedCard", "/agent/health"],
    "jsonrpc": "/jsonrpc"
  },
  "usage": {
    "jsonrpc_example": {
      "method": "POST",
      "url": "/jsonrpc",
      "body": {
        "jsonrpc": "2.0",
        "method": "greeting",
        "params": {},
        "id": 1
      }
    }
  }
}
```

**Why this matters:** This endpoint serves as the "front door" for agent discovery. Other agents can use this to understand what capabilities are available.

### 2. Agent Card Test (`GET /agent/card`)

**What it tests:** Standard A2A agent card format

**Request:**
```http
GET https://your-agent.herokuapp.com/agent/card
```

**What happens:**
- The agent returns its standardized "business card" containing:
  - Agent identity information
  - Communication protocol details
  - Available capabilities/methods

**Expected Response:**
```json
{
  "name": "HelloWorld A2A Agent (Heroku)",
  "description": "A simple A2A agent for testing connections on Heroku",
  "version": "1.0.0",
  "transport": {
    "type": "JSON-RPC",
    "endpoint": "/jsonrpc"
  },
  "capabilities": ["greeting", "echo", "heroku-info"]
}
```

**Why this matters:** This follows the A2A protocol standard for agent discovery. Other agents use this to understand how to communicate with this agent.

### 3. Health Check Test (`GET /agent/health`)

**What it tests:** Agent availability and system status

**Request:**
```http
GET https://your-agent.herokuapp.com/agent/health
```

**What happens:**
- The agent returns its current health status
- Includes platform information and timestamp

**Expected Response:**
```json
{
  "status": "UP",
  "platform": "Heroku",
  "timestamp": 1758320221773
}
```

**Why this matters:** Health checks are essential for monitoring agent availability in production A2A ecosystems.

### 4. JSON-RPC Greeting Test (`POST /jsonrpc`)

**What it tests:** Basic JSON-RPC communication

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "greeting",
  "params": {},
  "id": 1
}
```

**What happens:**
- The agent processes the JSON-RPC 2.0 request
- Matches the method "greeting" to its handler
- Returns a standardized JSON-RPC response

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "result": {
    "message": "Hello from A2A HelloWorld Agent running on Heroku!"
  },
  "id": 1
}
```

**Why this matters:** This tests the core A2A communication protocol. JSON-RPC is how agents actually talk to each other.

### 5. JSON-RPC Echo Test (`POST /jsonrpc`)

**What it tests:** Parameter passing and data handling

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "echo",
  "params": {"message": "Hello from test script!"},
  "id": 2
}
```

**What happens:**
- The agent receives parameters in the request
- Processes and echoes them back
- Tests data serialization/deserialization

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "result": {
    "echo": {
      "message": "Hello from test script!"
    }
  },
  "id": 2
}
```

**Why this matters:** Real A2A communication involves passing data between agents. This tests that capability.

### 6. JSON-RPC Heroku Info Test (`POST /jsonrpc`)

**What it tests:** Environment-specific information retrieval

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "heroku-info",
  "params": {},
  "id": 3
}
```

**What happens:**
- The agent accesses Heroku environment variables
- Returns deployment-specific information
- Tests environment awareness

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "result": {
    "heroku_environment": {
      "dyno": "web.1",
      "port": "59715",
      "release_version": null,
      "slug_commit": null
    }
  },
  "id": 3
}
```

**Why this matters:** Agents often need to be aware of their deployment environment for proper operation.

## Test Script Behavior

### Bash Script (`test-agent.sh`)
- Tests all endpoints sequentially
- Uses `curl` for HTTP requests
- Uses `jq` for JSON formatting (falls back to raw output)
- Provides emoji indicators for visual feedback
- Shows comprehensive output for debugging

### Python Script (`get-agent-card.py`)
- Focuses on core functionality (card, health, greeting)
- Uses `requests` library for HTTP
- Clean JSON output with error handling
- Good for integration into other tools

### Node.js Script (`get-agent-card.js`)
- Uses built-in Node.js modules (no dependencies)
- Similar functionality to Python script
- Good for environments where external packages aren't available

## What Success Looks Like

A successful test run indicates:

1. **Agent is running** - All HTTP requests return 200 OK
2. **Agent follows A2A protocol** - Returns proper agent card format
3. **JSON-RPC works** - Can send and receive structured messages
4. **Agent is healthy** - Health check passes
5. **Agent is discoverable** - Root endpoint provides useful information

## Common Issues and Troubleshooting

### Connection Refused
- **Cause:** Agent not running or wrong URL
- **Fix:** Check URL and ensure agent is deployed

### 404 Not Found
- **Cause:** Wrong endpoint path
- **Fix:** Verify agent is using correct route mappings

### JSON Parse Error
- **Cause:** Agent returning non-JSON response
- **Fix:** Check agent logs for errors

### Timeout
- **Cause:** Agent taking too long to respond
- **Fix:** Check agent performance and resource usage

## A2A Protocol Compliance

This agent implements the following A2A protocol standards:

- **Agent Discovery:** Standard `/agent/card` endpoint
- **Health Monitoring:** Standard `/agent/health` endpoint  
- **Communication:** JSON-RPC 2.0 protocol
- **Error Handling:** Proper JSON-RPC error responses
- **CORS Support:** Cross-origin requests enabled

## Integration with Other Agents

Once tested and verified, this agent can:

1. **Be discovered** by other agents via the agent card
2. **Receive requests** from other agents via JSON-RPC
3. **Send requests** to other agents using the same protocol
4. **Participate** in multi-agent workflows and conversations

The test scripts ensure all these capabilities are working correctly before the agent is used in production A2A ecosystems.
