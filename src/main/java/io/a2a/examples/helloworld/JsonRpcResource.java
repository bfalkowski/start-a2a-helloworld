package io.a2a.examples.helloworld;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;
import java.util.HashMap;

@Path("/jsonrpc")
public class JsonRpcResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> handleJsonRpc(Map<String, Object> request) {
        String method = (String) request.get("method");
        Object params = request.get("params");
        Object id = request.get("id");

        Object result;
        switch (method) {
            // Standard A2A Protocol Methods
            case "agent.discover":
                result = Map.of(
                    "name", "HelloWorld A2A Agent",
                    "description", "A simple A2A agent for testing connections on Heroku",
                    "version", "1.0.0",
                    "protocolVersion", "0.3.0",
                    "capabilities", Map.of(
                        "streaming", false,
                        "pushNotifications", false,
                        "stateTransitionHistory", false
                    ),
                    "skills", java.util.List.of(
                        Map.of("id", "greeting", "name", "Greeting", "description", "Returns a greeting message"),
                        Map.of("id", "echo", "name", "Echo", "description", "Echoes back the provided parameters"),
                        Map.of("id", "heroku-info", "name", "Heroku Info", "description", "Returns Heroku environment information")
                    )
                );
                break;
            case "agent.info":
                result = Map.of(
                    "name", "HelloWorld A2A Agent",
                    "description", "A simple A2A agent for testing connections on Heroku",
                    "version", "1.0.0",
                    "url", "https://a2a-helloworld-1dd6ef1d53ae.herokuapp.com",
                    "protocolVersion", "0.3.0"
                );
                break;
            case "agent.getCapabilities":
                result = Map.of(
                    "streaming", false,
                    "pushNotifications", false,
                    "stateTransitionHistory", false,
                    "supportedTransports", java.util.List.of("JSONRPC"),
                    "supportedProtocols", java.util.List.of("A2A-0.3.0")
                );
                break;
            case "agent.getSkills":
                result = java.util.List.of(
                    Map.of(
                        "id", "greeting",
                        "name", "Greeting",
                        "description", "Returns a greeting message",
                        "tags", java.util.List.of("greeting"),
                        "examples", java.util.List.of("hello", "hi")
                    ),
                    Map.of(
                        "id", "echo",
                        "name", "Echo",
                        "description", "Echoes back the provided parameters",
                        "tags", java.util.List.of("echo"),
                        "examples", java.util.List.of("echo test")
                    ),
                    Map.of(
                        "id", "heroku-info",
                        "name", "Heroku Info",
                        "description", "Returns Heroku environment information",
                        "tags", java.util.List.of("info", "heroku"),
                        "examples", java.util.List.of("info")
                    )
                );
                break;
            case "agent.health":
                result = Map.of(
                    "status", "UP",
                    "platform", "Heroku",
                    "timestamp", System.currentTimeMillis(),
                    "version", "1.0.0"
                );
                break;
            case "agent.status":
                result = Map.of(
                    "status", "UP",
                    "uptime", "running",
                    "lastHealthCheck", System.currentTimeMillis(),
                    "activeConnections", 0
                );
                break;
            // Custom Methods
            case "greeting":
                result = Map.of("message", "Hello from A2A HelloWorld Agent running on Heroku!");
                break;
            case "echo":
                result = Map.of("echo", params);
                break;
            case "heroku-info":
                Map<String, Object> herokuInfo = new HashMap<>();
                herokuInfo.put("dyno", System.getenv("DYNO"));
                herokuInfo.put("release_version", System.getenv("HEROKU_RELEASE_VERSION"));
                herokuInfo.put("slug_commit", System.getenv("HEROKU_SLUG_COMMIT"));
                herokuInfo.put("port", System.getenv("PORT"));
                result = Map.of("heroku_environment", herokuInfo);
                break;
            default:
                return Map.of(
                    "jsonrpc", "2.0",
                    "error", Map.of(
                        "code", -32601,
                        "message", "Method not found: " + method
                    ),
                    "id", id
                );
        }

        return Map.of(
            "jsonrpc", "2.0",
            "result", result,
            "id", id
        );
    }
}
