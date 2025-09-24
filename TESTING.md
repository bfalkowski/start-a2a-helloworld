# A2A Agent Testing Guide

This document explains what happens when you test an A2A (Agent-to-Agent) HelloWorld agent using the provided test scripts.

## Overview

The test scripts perform a comprehensive evaluation of an A2A agent using native A2A protocol methods via JSON-RPC communication. This ensures the agent is functioning correctly and can communicate with other agents in an A2A ecosystem using the standard A2A protocol.

## A2A Protocol Testing

The updated test script now uses native A2A protocol methods instead of simple HTTP GET requests. This provides a more realistic test of how A2A agents actually communicate in production environments.

## Test Flow Breakdown

### 1. A2A Agent Card Test (`GET /agent`)

**What it tests:** A2A-compliant agent card discovery

**Request:**
```http
GET https://your-agent.herokuapp.com/agent
```

**What happens:**
- The agent returns a properly formatted A2A agent card containing:
  - Agent metadata (name, description, version, URL)
  - Capabilities (streaming, push notifications, etc.)
  - Available skills with examples
  - Protocol version and transport information

**Expected Response:**
```json
{
  "name": "HelloWorld A2A Agent",
  "description": "A simple A2A agent for testing connections on Heroku",
  "url": "https://your-agent.herokuapp.com",
  "version": "1.0.0",
  "capabilities": {
    "streaming": false,
    "pushNotifications": false,
    "stateTransitionHistory": false
  },
  "skills": [
    {
      "id": "greeting",
      "name": "Greeting",
      "description": "Returns a greeting message",
      "tags": ["greeting"],
      "examples": ["hello", "hi"]
    }
  ],
  "protocolVersion": "0.3.0",
  "preferredTransport": "JSONRPC"
}
```

**Why this matters:** This endpoint serves as the "front door" for agent discovery. Other agents can use this to understand what capabilities are available and how to communicate with this agent.

### 2. A2A Protocol Methods (JSON-RPC)

The test script now tests standard A2A protocol methods via JSON-RPC calls:

#### A2A Agent Discovery (`agent.discover`)

**What it tests:** Standard A2A agent discovery protocol

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "agent.discover",
  "params": {},
  "id": 1
}
```

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "error": {
    "message": "Method not found: agent.discover",
    "code": -32601
  },
  "id": 1
}
```

**Why this matters:** Tests standard A2A protocol methods. In a full A2A implementation, this would return agent discovery information.

#### A2A Agent Capabilities (`agent.getCapabilities`)

**What it tests:** Agent capability discovery

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "agent.getCapabilities",
  "params": {},
  "id": 2
}
```

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "error": {
    "message": "Method not found: agent.getCapabilities",
    "code": -32601
  },
  "id": 2
}
```

**Why this matters:** Tests the A2A capability discovery protocol. In a full implementation, this would return detailed capability information.

### 3. Custom Agent Methods (JSON-RPC)

The test script also tests the agent's custom methods that are actually implemented:

#### Greeting Method (`greeting`)

**What it tests:** Basic agent communication

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "greeting",
  "params": {},
  "id": 6
}
```

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "id": 6,
  "result": {
    "message": "Hello from A2A HelloWorld Agent running on Heroku!"
  }
}
```

**Why this matters:** Tests the agent's ability to respond to basic communication requests.

#### Echo Method (`echo`)

**What it tests:** Parameter handling and data processing

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "echo",
  "params": {"message": "Hello from A2A test!"},
  "id": 7
}
```

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "id": 7,
  "result": {
    "echo": {
      "message": "Hello from A2A test!"
    }
  }
}
```

**Why this matters:** Tests the agent's ability to process and return structured data.

#### Heroku Info Method (`heroku-info`)

**What it tests:** Environment-specific information retrieval

**Request:**
```http
POST https://your-agent.herokuapp.com/jsonrpc
Content-Type: application/json

{
  "jsonrpc": "2.0",
  "method": "heroku-info",
  "params": {},
  "id": 8
}
```

**Expected Response:**
```json
{
  "jsonrpc": "2.0",
  "id": 8,
  "result": {
    "heroku_environment": {
      "port": "12158",
      "dyno": "web.1",
      "release_version": null,
      "slug_commit": null
    }
  }
}
```

**Why this matters:** Tests the agent's ability to provide environment-specific information, useful for debugging and monitoring.

## Test Results Interpretation

### Success Indicators
- ✅ A2A agent card returns proper schema with all required fields
- ✅ Standard A2A protocol methods return appropriate responses (even if not implemented)
- ✅ Custom JSON-RPC methods work correctly
- ✅ Agent card contains proper capabilities, skills, and protocol information

### Expected Behavior
- **A2A Protocol Methods**: May return "Method not found" errors (normal for simple demo)
- **Custom Methods**: Should return successful responses with proper data
- **Agent Card**: Must contain all A2A-required fields for client compatibility

### Common Issues
- ❌ **Connection Refused**: Agent not running or wrong URL
- ❌ **404 Not Found**: Endpoint doesn't exist
- ❌ **500 Internal Error**: Agent has a bug
- ❌ **Invalid JSON**: Response format is incorrect
- ❌ **Schema Mismatch**: Agent card doesn't match A2A specification

### Troubleshooting Steps
1. **Check URL**: Ensure the agent URL is correct and accessible
2. **Verify Deployment**: Confirm the agent is deployed and running
3. **Check Logs**: Look at Heroku logs for error messages
4. **Test Manually**: Use curl to test individual endpoints
5. **Validate Schema**: Ensure agent card matches A2A specification

## Summary

The updated test script provides a comprehensive evaluation of an A2A agent using native A2A protocol methods:

- **A2A Compliance**: Tests proper agent card schema and discovery endpoints
- **Protocol Methods**: Tests standard A2A protocol methods via JSON-RPC
- **Custom Methods**: Tests agent-specific functionality
- **Error Handling**: Verifies proper JSON-RPC error responses
- **Schema Validation**: Ensures compatibility with A2A clients

This approach provides a more realistic test of how A2A agents actually communicate in production environments, ensuring the agent is ready for integration with other A2A-compliant systems.
