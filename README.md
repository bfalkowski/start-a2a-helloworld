# A2A HelloWorld Agent

A self-contained A2A (Agent-to-Agent) HelloWorld agent for connectivity demos. This agent can be deployed to Heroku and provides JSON-RPC endpoints for agent communication.

## Features

- **Agent Discovery**: REST endpoints for agent card and health checks
- **JSON-RPC Communication**: Full JSON-RPC 2.0 support for agent-to-agent communication
- **Heroku Ready**: Optimized for Heroku deployment with proper port binding and CORS
- **Quarkus Framework**: Fast, lightweight Java framework for microservices

## Available Endpoints

### Agent Discovery
- `GET /` - Root endpoint with agent information and available endpoints
- `GET /agent/card` - Basic agent card information
- `GET /agent/extendedCard` - Extended agent card information
- `GET /agent/authenticatedExtendedCard` - Authenticated agent card information
- `GET /agent/health` - Health check endpoint

### JSON-RPC Communication
- `POST /jsonrpc` - JSON-RPC 2.0 endpoint

#### Available JSON-RPC Methods:
- `greeting` - Returns a hello message
- `echo` - Echoes back the provided parameters
- `heroku-info` - Returns Heroku environment information

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

## Usage Examples

### Quick Test Scripts

The repository includes ready-to-use test scripts that can test any deployed agent:

#### Bash Script (Most Comprehensive)
```bash
# Test agent (URL required)
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

#### Agent Discovery
```bash
# Get agent information
curl https://your-app.herokuapp.com/agent/card

# Health check
curl https://your-app.herokuapp.com/agent/health
```

#### JSON-RPC Communication
```bash
# Greeting method
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "greeting",
    "params": {},
    "id": 1
  }'

# Echo method
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "echo",
    "params": {"message": "Hello World"},
    "id": 2
  }'

# Heroku info method
curl -X POST https://your-app.herokuapp.com/jsonrpc \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "method": "heroku-info",
    "params": {},
    "id": 3
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

## License

This project is provided as a demo/template for A2A agent development.
