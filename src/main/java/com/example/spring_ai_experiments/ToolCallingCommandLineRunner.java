package com.example.spring_ai_experiments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ToolCallingCommandLineRunner implements CommandLineRunner {

  @Autowired
  private ChatClient.Builder chatClientBuilder;

  @Override
  public void run(String... args) {
    String prompt = "What day is tomorrow?";
    log.info("###   Prepare for calling LLM");
    try {
      ChatClient chatClient = chatClientBuilder.build();
      String response = chatClient.prompt()
          .user(prompt)
          .tools(new DateTimeTools())
          .call()
          .content();
      log.info("###   AI Response: {}", response);
    } catch (Exception e) {
      log.error("###   Failed to call LLM", e);
    }
  }

  private static class DateTimeTools {
    @Tool(description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime() {
      return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

  }
}
