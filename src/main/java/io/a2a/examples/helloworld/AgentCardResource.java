package io.a2a.examples.helloworld;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.a2a.spec.AgentCard;
import io.a2a.spec.AgentCapabilities;
import io.a2a.spec.AgentSkill;

@Path("/agent")
public class AgentCardResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AgentCard getAgentCard() {
        return new AgentCard.Builder()
                .name("HelloWorld A2A Agent")
                .description("A simple A2A agent for testing connections on Heroku")
                .url("https://a2a-helloworld-1dd6ef1d53ae.herokuapp.com")
                .version("1.0.0")
                .documentationUrl("https://github.com/bfalkowski/start-a2a-helloworld")
                .capabilities(new AgentCapabilities.Builder()
                        .streaming(false)
                        .pushNotifications(false)
                        .stateTransitionHistory(false)
                        .build())
                .defaultInputModes(Collections.singletonList("text"))
                .defaultOutputModes(Collections.singletonList("text"))
                .skills(List.of(
                        new AgentSkill.Builder()
                                .id("greeting")
                                .name("Greeting")
                                .description("Returns a greeting message")
                                .tags(Collections.singletonList("greeting"))
                                .examples(List.of("hello", "hi"))
                                .build(),
                        new AgentSkill.Builder()
                                .id("echo")
                                .name("Echo")
                                .description("Echoes back the provided parameters")
                                .tags(Collections.singletonList("echo"))
                                .examples(List.of("echo test"))
                                .build(),
                        new AgentSkill.Builder()
                                .id("heroku-info")
                                .name("Heroku Info")
                                .description("Returns Heroku environment information")
                                .tags(List.of("info", "heroku"))
                                .examples(List.of("info"))
                                .build()
                ))
                .protocolVersion("0.3.0")
                .preferredTransport("JSONRPC")
                .build();
    }

    @GET
    @Path("/extendedCard")
    @Produces(MediaType.APPLICATION_JSON)
    public AgentCard getExtendedAgentCard() {
        return getAgentCard();
    }

    @GET
    @Path("/authenticatedExtendedCard")
    @Produces(MediaType.APPLICATION_JSON)
    public AgentCard getAuthenticatedExtendedAgentCard() {
        return getAgentCard();
    }

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> health() {
        return Map.of(
            "status", "UP",
            "platform", "Heroku",
            "timestamp", System.currentTimeMillis()
        );
    }
}
