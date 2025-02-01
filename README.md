## 프로젝트 소개
스프링 부트와 JPA를 활용한 온라인 쇼핑몰 API 서버입니다.

## 기술 스택
- Java 17
- Spring Boot 3.4.1
- Spring Data JPA
- H2 Database
- Gradle
- Lombok

## 주요 기능

### 회원 관리
- 회원 가입/조회
- 중복 회원 검증

### 상품 관리 
- 상품 등록/수정/조회
- 재고 관리

### 주문 관리
- 상품 주문/취소
- 주문 조회
- 재고 수량 관리

## API 엔드포인트

### 회원 API
```
POST /members/join - 회원가입
GET /members - 회원 목록 조회
GET /members/{memberId} - 회원 단건 조회
```

### 상품 API
```
POST /items/new - 상품 등록
GET /items - 상품 목록 조회 
GET /items/{itemId} - 상품 단건 조회
PUT /items/{itemId}/edit - 상품 수정
```

### 주문 API
```
POST /order - 상품 주문
GET /orders - 주문 목록 조회
POST /orders/{orderId}/cancel - 주문 취소
```

## 개발 환경 설정

1. H2 데이터베이스 설치 및 실행
2. application.yml 설정
3. 프로젝트 실행
```bash
./gradlew bootRun
```

## 테스트
JUnit을 사용한 단위 테스트와 통합 테스트가 구현되어 있습니다.
- MemberServiceTest
- OrderServiceTest  
- ItemServiceTest

## 예외 처리
전역 예외 처리기를 통해 다음과 같은 예외들을 처리합니다:
- 재고 부족 예외 (NotEnoughStockException)
- 중복 회원 예외 (IllegalStateException) 
- 유효성 검사 예외 (MethodArgumentNotValidException)

## CORS 설정
프론트엔드 개발을 위한 CORS 설정이 되어있습니다.