# 스프링 실기 연습

난이도별 Spring Boot 실기 문제를 풀고, 오답 노트와 모의고사로 복습하는 학습 앱입니다.

## 기능
- 문제 48개 (초급/중급/고급), 유형은 코드 작성, 오류 찾기, 빈칸 채우기
- Spring 어노테이션 자동완성이 되는 코드 에디터 (Monaco)
- 빈칸 문제 자동 채점, 나머지는 모범 답안과 체크리스트로 자가 채점
- 오답 노트, 즐겨찾기, 랜덤 문제, 모의고사(문제당 5분), 주제별 정답률
- AI 코드 리뷰 (Claude API, 선택)

## 필요한 것
| 항목 | 내용 |
|---|---|
| JDK 21 | `java -version`으로 확인 |
| Git | 리포지토리를 받을 때 필요 |
| 최신 브라우저 | Chrome, Edge 등 |
| 인터넷 연결 | 에디터(Monaco)와 글꼴을 CDN에서 불러옴 |

Maven은 설치하지 않아도 됩니다. 포함된 `mvnw`가 알아서 내려받습니다.

## 실행
```bash
git clone https://github.com/0jin2931/Spring-Boot-Practice.git
cd Spring-Boot-Practice
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```
브라우저에서 http://localhost:8080/index.html 을 엽니다.

## 설정
- **포트:** 기본 8080. 바꾸려면 `application.properties`에 `server.port=8081`을 추가합니다.
- **로컬 전용:** `server.address=127.0.0.1`로 외부 접속을 막아 두었습니다. 이 앱의 AI 엔드포인트에는 인증이 없으니, 다른 기기에 열 때는 주의하세요.
- **AI 코드 리뷰(선택):** 서버를 켜기 전에 환경변수 `ANTHROPIC_API_KEY`를 설정합니다. 없어도 다른 기능은 모두 동작합니다.
  - PowerShell: `$env:ANTHROPIC_API_KEY="키"`
  - macOS/Linux: `export ANTHROPIC_API_KEY=키`
- **학습 기록:** 브라우저 localStorage에 저장되므로 브라우저를 바꾸면 사라집니다.

## 문제 추가하기
`src/main/resources/static/problems.json`에 항목을 추가합니다.

| 필드 | 설명 |
|---|---|
| `id`, `level`, `topic`, `title`, `desc`, `hint`, `solution`, `checklist` | 모든 유형 공통 |
| `type` | `fix`(오류 찾기) 또는 `blank`(빈칸). 생략하면 코드 작성 |
| `starter` | 오류 찾기 유형의 버그가 있는 시작 코드 |
| `code`, `answers` | 빈칸 유형. `code`에 `____`로 빈칸을 두고, `answers`에 빈칸 순서대로 정답을 씁니다. 정답이 여러 개면 `A\|B`로 구분합니다. |

## 코드를 수정할 때 권장하는 도구
- VS Code: Extension Pack for Java, Spring Boot Extension Pack
- IntelliJ IDEA: 기본 기능으로 충분합니다.
