package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Recipe;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AIMealPlanService implements  IAIMealPlanService{
    private static final String OLLAMA_URL = "http://localhost:11434/api/chat";
    private static final String MODEL = "llama3.2";
    private final ObjectMapper objectMapper;
    private final HttpClient client;

    public AIMealPlanService(ObjectMapper objectMapper, HttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    public String generateMealsPlan() {
        String answer = " ";
        String prompt = buildPrompt(RecipeService.recipeList);
        try {
            answer = ask(prompt);
            System.out.println(answer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    private String buildPrompt(Set<Recipe> recipes) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                Create a 7-day meal plan using only the recipes below.
                Include breakfast, lunch, and dinner for each day.
                Do not invent recipes that are not in the list.
                
                Available recipes:
                """);

        for (Recipe recipe : recipes) {
            prompt.append(recipe.toString()).append("\n");
        }

        return prompt.toString();
    }

    private String ask(String prompt) throws InterruptedException {
        try {
            String jsonRequest = objectMapper.writeValueAsString(
                    Map.of(
                            "model", MODEL,
                            "messages", List.of(
                                    Map.of(
                                            "role", "user",
                                            "content", prompt
                                    )
                            ),
                            "stream", false
                    )
            );

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

            return content.asText();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
