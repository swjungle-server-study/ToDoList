## TODO LIST 설계

**1. 요구사항 분석 및 기능 목록 도출:**

- **핵심 기능**:
  - TODO 항목 생성 (Create)
  - TODO 항목 수정 (Update)
  - TODO 항목 삭제 (Delete)
  - TODO 항목 단건 조회 (Read - One)
  - TODO 항목 전체 조회 (Read - All)

**2. 도메인 모델링:**

- **Todo:**
  - id (Long)
  - title (String)
  - content (String)
  - completed (Boolean)

**3. RESTful API 설계:**

| HTTP Method | URI                 | 기능               | 요청 Body 예시 (JSON)                                  |
| ----------- | -------------------- | ------------------ | ----------------------------------------------------- |
| POST        | /api/todos           | TODO 항목 생성       | { "title": "장보기", "content": "우유, 계란, 야채 구매" } |
| GET         | /api/todos           | TODO 항목 전체 조회   |                                                       |
| GET         | /api/todos/{id}      | TODO 항목 단건 조회   |                                                       |
| PUT         | /api/todos/{id}      | TODO 항목 수정       | { "title": "장보기 완료", "completed": true }           |
| DELETE      | /api/todos/{id}      | TODO 항목 삭제       |                                                       |

**4. 클린 코드 및 객체지향 설계 원칙:**

*   **SOLID 원칙 준수:** 단일 책임 원칙, 개방-폐쇄 원칙, 리스코프 치환 원칙, 인터페이스 분리 원칙, 의존 역전 원칙
*   **네이밍 규칙 준수:** 명확하고 의미 있는 이름 사용
*   **함수/메서드 분리:** 작고 응집력 있는 단위로 분리
*   **주석 작성:** 필요한 경우에만 간결하고 명확하게 작성
*   **테스트 주도 개발 (TDD):** JUnit, Mockito를 활용하여 테스트 코드 작성
