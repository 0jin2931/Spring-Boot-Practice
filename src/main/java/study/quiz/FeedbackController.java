package study.quiz;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class FeedbackController {
    record Req(String title, String desc, String code, String solution) {}

    private final RestClient http = RestClient.create();

    @PostMapping("/api/feedback")
    public Map<String, String> feedback(@RequestBody Req r) {
        String key = System.getenv("ANTHROPIC_API_KEY");
        if (key == null || key.isBlank()) {
            return Map.of("text", "ANTHROPIC_API_KEY 환경변수를 설정한 뒤 서버를 다시 실행하세요.");
        }
        String prompt = """
                Spring Boot 실기 문제를 푸는 학생의 코드를 리뷰해 주세요. 한국어로, 10줄 이내로 간결하게.
                정답 여부, 틀린 부분, 개선점 순서로 답하세요. 모범 답안을 그대로 알려주지는 말고 힌트 위주로 설명하세요.

                [문제] %s
                %s

                [학생 코드]
                %s

                [모범 답안]
                %s
                """.formatted(r.title(), r.desc(), cut(r.code()), cut(r.solution()));
        try {
            Map<?, ?> res = http.post().uri("https://api.anthropic.com/v1/messages")
                    .header("x-api-key", key)
                    .header("anthropic-version", "2023-06-01")
                    .body(Map.of("model", "claude-sonnet-5-5", "max_tokens", 1000,
                            "messages", List.of(Map.of("role", "user", "content", prompt))))
                    .retrieve().body(Map.class);
            Map<?, ?> first = (Map<?, ?>) ((List<?>) res.get("content")).get(0);
            return Map.of("text", (String) first.get("text"));
        } catch (Exception e) {
            return Map.of("text", "AI 호출 실패: " + e.getMessage());
        }
    }

    private static String cut(String s) {
        return s == null ? "" : s.substring(0, Math.min(s.length(), 6000));
    }
}
