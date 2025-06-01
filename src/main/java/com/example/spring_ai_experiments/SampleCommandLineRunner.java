package com.example.spring_ai_experiments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleCommandLineRunner implements CommandLineRunner {

  @Autowired
  private OllamaChatModel ollamaChatModel;

  @Override
  public void run(String... args) {
    String prompt = "Explain how photosynthesis works in simple terms.";
    log.info("###   Prepare for calling LLM");
    try {
      ChatClient chatClient = ChatClient.builder(ollamaChatModel).build();
      String response = chatClient.prompt(prompt).call().content();
      log.info("###   AI Response: {}", response);
    } catch (Exception e) {
      log.error("###   Failed to call LLM", e);
    }
  }
}
