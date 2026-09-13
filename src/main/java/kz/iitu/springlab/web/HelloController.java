package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting(
                "Hello, " + name + "!",
                owner,
                LocalDateTime.now()
        );
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(
                owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors()
        );
    }

    // Individual assignment - Variant 7
    @GetMapping("/wordcount")
    public WordCount wordCount(@RequestParam(defaultValue = "") String text) {
        String trimmed = text.trim();

        if (trimmed.isEmpty()) {
            return new WordCount(0, 0, "");
        }

        String[] words = trimmed.split("\\s+");

        String longestWord = words[0];

        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        return new WordCount(
                words.length,
                text.length(),
                longestWord
        );
    }

    public record Greeting(
            String message,
            String owner,
            LocalDateTime timestamp
    ) {
    }

    public record Info(
            String owner,
            String javaVersion,
            int cpuCores
    ) {
    }

    public record WordCount(
            int wordCount,
            int characterCount,
            String longestWord
    ) {
    }
}