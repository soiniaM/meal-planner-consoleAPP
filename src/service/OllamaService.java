package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.MealPlanResponse;
import utils.Utilities;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OllamaService {
    private static final String OLLAMA_URL = "http://localhost:11434/api/chat";
    private static final String MODEL = "llama3.2";
    private final ObjectMapper objectMapper= new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();


    public String ask(String prompt) throws InterruptedException {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", MODEL,
                    "messages", List.of(
                            Map.of(
                                    "role", "user",
                                    "content", prompt
                            )
                    ),
                    "stream", false
            );

            String jsonRequest = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OLLAMA_URL))
                    .timeout(Duration.ofMinutes(3))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new IOException(
                        "Ollama returned HTTP status: "
                                + response.statusCode()
                );
            }

            JsonNode root = objectMapper.readTree(response.body());
            JsonNode content = root.path("message").path("content");

            if (!content.isTextual()) {
                throw new IOException(
                        "Ollama response did not contain message.content"
                );
            }
            return  content.asText();
            
        } catch (IOException e) {
            throw new RuntimeException( "Failed to communicate with Ollama or parse its response",e);
        }
    }
}
