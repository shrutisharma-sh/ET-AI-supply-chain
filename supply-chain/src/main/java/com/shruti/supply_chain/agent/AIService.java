package com.shruti.supply_chain.agent;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;

@Service


public class AIService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    public String getDecision(String prompt) {

        try {
            return callAI(prompt);
        } catch (Exception e) {
            e.printStackTrace();


            return fallbackDecision(prompt);
        }
    }

    private String callAI(String prompt) throws Exception {

//

        String finalPrompt = "You are a supply chain AI agent. " +
                "Based on the issue, suggest ONLY ONE action from: reorder, escalate, switch supplier, mark priority.\n\n" +
                prompt;

        URL url = new URL("https://api-inference.huggingface.co/models/bigscience/bloom-560m");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String body = "{ \"inputs\": \"" + finalPrompt.replace("\"", "\\\"") + "\" }";

        OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();

        BufferedReader br;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder responseBuilder = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
        }

        String response = responseBuilder.toString();
        System.out.println("RAW AI RESPONSE: " + response);

        return extractDecision(response);
    }


    private String extractDecision(String response) {

        if (response.contains("generated_text")) {
            String text = response.replaceAll(".*generated_text\":\"|\".*", "").toLowerCase();

            if (text.contains("reorder")) return "reorder";
            if (text.contains("switch")) return "switch supplier";
            if (text.contains("escalate")) return "escalate";
            if (text.contains("priority")) return "mark priority";
        }

        return "mark priority"; // safe default
    }


    private String fallbackDecision(String prompt) {

        prompt = prompt.toLowerCase();

        if (prompt.contains("delayed")) return "escalate";
        if (prompt.contains("shipped")) return "mark priority";
        if (prompt.contains("placed")) return "reorder";

        return "mark priority";
    }
}