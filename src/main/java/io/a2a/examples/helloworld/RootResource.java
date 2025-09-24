package io.a2a.examples.helloworld;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;
import java.util.List;

@Path("/")
public class RootResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getRoot() {
        return Map.of(
            "name", "A2A HelloWorld Agent",
            "description", "A simple A2A agent for testing connections on Heroku",
            "version", "1.0.0",
            "status", "UP",
            "protocolVersion", "0.3.0",
            "endpoints", Map.of(
                "agent_discovery", List.of(
                    "/agent",
                    "/agent/extendedCard", 
                    "/agent/authenticatedExtendedCard",
                    "/agent/health"
                ),
                "jsonrpc", "/jsonrpc",
                "documentation", "https://github.com/bfalkowski/start-a2a-helloworld"
            ),
            "a2a_protocol_methods", List.of(
                "agent.discover",
                "agent.info",
                "agent.getCapabilities",
                "agent.getSkills",
                "agent.health",
                "agent.status"
            ),
            "custom_methods", List.of(
                "greeting",
                "echo",
                "heroku-info"
            ),
            "usage", Map.of(
                "a2a_discovery_example", Map.of(
                    "method", "POST",
                    "url", "/jsonrpc",
                    "body", Map.of(
                        "jsonrpc", "2.0",
                        "method", "agent.discover",
                        "params", Map.of(),
                        "id", 1
                    )
                ),
                "custom_method_example", Map.of(
                    "method", "POST",
                    "url", "/jsonrpc",
                    "body", Map.of(
                        "jsonrpc", "2.0",
                        "method", "greeting",
                        "params", Map.of(),
                        "id", 2
                    )
                )
            )
        );
    }
}
