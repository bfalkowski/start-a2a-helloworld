# A2A HelloWorld Agent

A self-contained A2A (Agent-to-Agent) HelloWorld agent for connectivity demos. This agent can be deployed to Heroku and provides JSON-RPC endpoints for agent communication.

## Features

- **A2A Protocol Compliance**: Full A2A (Agent-to-Agent) protocol support with proper agent card schema
- **Agent Discovery**: REST endpoints for agent card and health checks following A2A standards
- **JSON-RPC Communication**: Full JSON-RPC 2.0 support for agent-to-agent communication
- **Heroku Ready**: Optimized for Heroku deployment with proper port binding and CORS
- **Quarkus Framework**: Fast, lightweight Java framework for microservices
- **A2A Java SDK**: Uses official A2A Java SDK for proper schema compliance

## Available Endpoints

### A2A Agent Discovery
- `GET /` - Root endpoint with agent information and available endpoints
- `GET /agent` - A2A-compliant agent card (primary discovery endpoint)
- `GET /agent/extendedCard` - Extended agent card information
- `GET /agent/authenticatedExtendedCard` - Authenticated agent card information
- `GET /agent/health` - Health check endpoint

### JSON-RPC Communication
- `POST /jsonrpc` - JSON-RPC 2.0 endpoint for A2A protocol methods

#### Available JSON-RPC Methods:
- `greeting` - Returns a hello message
- `echo` - Echoes back the provided parameters
- `heroku-info` - Returns Heroku environment information

#### A2A Protocol Methods (via JSON-RPC):
- `agent.discover` - Agent discovery with capabilities and skills
- `agent.info` - Basic agent information (name, version, URL, protocol)
- `agent.getCapabilities` - Detailed capability information (streaming, transports, protocols)
- `agent.getSkills` - Available skills with descriptions, tags, and examples
- `agent.health` - Health status with timestamp and platform info
- `agent.status` - Runtime status with uptime and connection info

## Local Development

### Prerequisites
- Java 17+
- Maven 3.6+

### Running Locally
```bash
# Build the project
mvn clean package

# Run the application
java -jar target/quarkus-app/quarkus-run.jar
```

The application will be available at `http://localhost:8080`

## Heroku Deployment

### Prerequisites
- Heroku CLI installed
- Git repository with Heroku remote configured

### Deploy to Heroku
```bash
# Add Heroku remote (replace with your app name)
heroku git:remote -a your-app-name

# Deploy
git push heroku main
```

### Heroku Configuration
The application automatically:
- Uses the `PORT` environment variable provided by Heroku
- Binds to `0.0.0.0` for external access
- Enables CORS for cross-origin requests
- Uses Java 17 runtime

## Testing

The repository includes comprehensive test scripts and documentation:

- **[TESTING.md](TESTING.md)** - Detailed explanation of what happens during testing
- **Test Scripts** - Ready-to-use scripts for testing any deployed agent

### Quick Test Scripts

#### Bash Script (A2A Protocol Testing)
```bash
# Test agent using native A2A protocol methods (URL required)
./test-agent.sh https://your-agent.herokuapp.com
```

#### Python Script (Clean & Simple)
```bash
# Test agent (URL required)
python3 get-agent-card.py https://your-agent.herokuapp.com
```

#### Node.js Script (No Dependencies)
```bash
# Test agent (URL required)
node get-agent-card.js https://your-agent.herokuapp.com
```

### Manual API Testing

#### A2A Agent Discovery
```bash
# Get A2A-compliant agent card
curl https://your-app.herokuapp.com/agent

# Health check
curl https://your-app.herokuapp.com/agent/health
```

#### A2A Protocol Methods (JSON-RPC)
```bash
# A2A Agent Discovery (returns full agent info with capabilities and skills)
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "agent.discover",
    "params": {},
    "id": 1
  }'

# A2A Agent Capabilities (returns streaming, transports, protocols)
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "agent.getCapabilities",
    "params": {},
    "id": 2
  }'

# A2A Agent Skills (returns available skills with descriptions)
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "agent.getSkills",
    "params": {},
    "id": 3
  }'

# A2A Agent Health (returns health status and platform info)
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "agent.health",
    "params": {},
    "id": 4
  }'

# Custom Greeting method
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "greeting",
    "params": {},
    "id": 5
  }'
```

## Project Structure

```
├── src/main/java/io/a2a/examples/helloworld/
│   ├── AgentCardResource.java    # Agent discovery endpoints
│   ├── JsonRpcResource.java      # JSON-RPC communication
│   └── RootResource.java         # Root endpoint
├── src/main/resources/
│   └── application.properties    # Quarkus configuration
├── test-agent.sh                 # Bash test script
├── get-agent-card.py             # Python test script
├── get-agent-card.js             # Node.js test script
├── TESTING.md                    # Detailed testing documentation
├── pom.xml                       # Maven configuration
├── procfile                      # Heroku process definition
├── system.properties             # Java version specification
└── app.json                      # Heroku app configuration
```

## Technology Stack

- **Java 17** - Programming language
- **Quarkus 3.2.9** - Java framework
- **Maven** - Build tool
- **Jakarta REST** - REST API framework
- **JSON-RPC 2.0** - Agent communication protocol
- **A2A Java SDK 0.3.0.Alpha1** - Official A2A protocol implementation

## License

This project is provided as a demo/template for A2A agent development.
