package com.ih.ailh.service;

import com.ih.ailh.dto.GenerateMaterialRequestDTO;
import com.ih.ailh.dto.GenerateQuizRequestDTO;
import com.ih.ailh.dto.MaterialDTO;
import com.ih.ailh.dto.QuizDTO;
import com.ih.ailh.model.AIInteraction;
import com.ih.ailh.model.User;
import com.ih.ailh.repository.AIInteractionRepository;
import com.ih.ailh.repository.UserRepository;
import com.ih.ailh.model.Course;
import com.ih.ailh.model.Material;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AIService {
    private final AIInteractionRepository aiInteractionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final WebClient.Builder webClientBuilder;

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${openai.endpoint}")
    private String openAiEndpoint;

    @Value("${openai.model}")
    private String openAiModel;

    public MaterialDTO generateMaterial(GenerateMaterialRequestDTO request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String prompt = formatMaterialPrompt(request);
        Map<String, Object> response = callOpenAI(prompt);

        String content = extractContent(response);
        int tokensUsed = extractTokensUsed(response);

        // Save AI interaction
        AIInteraction interaction = new AIInteraction();
        interaction.setUser(user);
        interaction.setAiModel(openAiModel);
        interaction.setInteractionType(AIInteraction.InteractionType.MATERIAL_GENERATION);
        interaction.setPrompt(prompt);
        interaction.setTokensUsed(tokensUsed);
        aiInteractionRepository.save(interaction);

        // Create material DTO
        MaterialDTO materialDTO = new MaterialDTO();
        materialDTO.setCourseId(request.getCourseId());
        materialDTO.setTitle(extractTitle(content));
        materialDTO.setContent(content);
        materialDTO.setContentType(Material.ContentType.AI_GENERATED);
        materialDTO.setDifficultyLevel(request.getDifficultyLevel());
        materialDTO.setAiModelUsed(openAiModel);
        materialDTO.setEstimatedTimeMinutes(estimateReadingTime(content));

        return materialDTO;
    }

    public QuizDTO generateQuiz(GenerateQuizRequestDTO request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String prompt = formatQuizPrompt(request);
        Map<String, Object> response = callOpenAI(prompt);

        String content = extractContent(response);
        int tokensUsed = extractTokensUsed(response);

        // Save AI interaction
        AIInteraction interaction = new AIInteraction();
        interaction.setUser(user);
        interaction.setAiModel(openAiModel);
        interaction.setInteractionType(AIInteraction.InteractionType.QUIZ_GENERATION);
        interaction.setPrompt(prompt);
        interaction.setTokensUsed(tokensUsed);
        aiInteractionRepository.save(interaction);

        // Parse quiz content and create QuizDTO
        return parseQuizContent(content, request.getMaterialId(), request.getDifficultyLevel());
    }

    public String generateDiagram(String content, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String prompt = "Create a mermaid diagram that visualizes the key concepts from this content: " + content;
        Map<String, Object> response = callOpenAI(prompt);

        String diagramCode = extractContent(response);
        int tokensUsed = extractTokensUsed(response);

        // Save AI interaction
        AIInteraction interaction = new AIInteraction();
        interaction.setUser(user);
        interaction.setAiModel(openAiModel);
        interaction.setInteractionType(AIInteraction.InteractionType.VISUALIZATION);
        interaction.setPrompt(prompt);
        interaction.setTokensUsed(tokensUsed);
        aiInteractionRepository.save(interaction);

        return diagramCode;
    }

    public String analyzeProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get user progress data (this would involve querying progress repository)
        String progressData = "{}"; // Simplified for this example

        String prompt = "Analyze this student's progress data and provide recommendations: " + progressData;
        Map<String, Object> response = callOpenAI(prompt);

        String analysis = extractContent(response);
        int tokensUsed = extractTokensUsed(response);

        // Save AI interaction
        AIInteraction interaction = new AIInteraction();
        interaction.setUser(user);
        interaction.setAiModel(openAiModel);
        interaction.setInteractionType(AIInteraction.InteractionType.PROGRESS_ANALYSIS);
        interaction.setPrompt(prompt);
        interaction.setTokensUsed(tokensUsed);
        aiInteractionRepository.save(interaction);

        return analysis;
    }

    private Map<String, Object> callOpenAI(String prompt) {
        WebClient webClient = webClientBuilder.baseUrl(openAiEndpoint)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiApiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openAiModel);

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", "You are an educational content assistant."),
                Map.of("role", "user", "content", prompt)
        );
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", 1000);
        requestBody.put("temperature", 0.7);

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    private String formatMaterialPrompt(GenerateMaterialRequestDTO request) {
        return String.format("Create an educational material on %s for %s level students. %s",
                request.getTopic(),
                request.getDifficultyLevel().toString().toLowerCase(),
                request.getAiPreferences() != null ? request.getAiPreferences() : "");
    }

    private String formatQuizPrompt(GenerateQuizRequestDTO request) {
        return String.format("Create a quiz with %d multiple-choice questions at %s difficulty level. Format as JSON with questions and answers.",
                request.getNumberOfQuestions(),
                request.getDifficultyLevel().toString().toLowerCase());
    }

    private String extractContent(Map<String, Object> response) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }

    private int extractTokensUsed(Map<String, Object> response) {
        Map<String, Object> usage = (Map<String, Object>) response.get("usage");
        return (Integer) usage.get("total_tokens");
    }

    private String extractTitle(String content) {
        // Extract the first line as title (simplified)
        String[] lines = content.split("\n");
        return lines[0].replaceAll("^#+ ", "").trim();
    }

    private int estimateReadingTime(String content) {
        int wordCount = content.split("\\s+").length;
        return Math.max(1, wordCount / 200); // Assuming 200 words per minute
    }

    private QuizDTO parseQuizContent(String content, Long materialId, Course.DifficultyLevel difficultyLevel) {
        // This would parse the JSON content into QuizDTO (simplified for now)
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setMaterialId(materialId);
        quizDTO.setTitle("AI Generated Quiz");
        quizDTO.setDescription("Quiz generated by AI");
        quizDTO.setAiGenerated(true);
        quizDTO.setDifficultyLevel(difficultyLevel);

        // Parse JSON and create questions/answers
        // This is a simplified version

        return quizDTO;
    }
}