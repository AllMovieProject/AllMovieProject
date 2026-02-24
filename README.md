#  AllMovie

최근 OTT 서비스의 확산과 관람 문화 변화로 영화관 산업은 점점 어려움을 겪고 있습니다.
2025년 기준 국내 영화관 연간 관객 수는 약 1억 명 수준까지 감소한 것으로 분석됩니다.
본 프로젝트는 영화관이 지닌 공간적·경험적 가치를 다시 조명하고, 영화관 문화에 작게나마 긍정적인 기여를 하는 것을 목표로 합니다.
---

##  프로젝트 개요

- **목적**
  영화관 이용객의 관람/예매 경험을 개선하고,
  관리자 및 직원이 효율적으로 운영할 수 있는 관리 기능을 제공하는 것을 목표로 합니다.
- **기획 배경**
  최근 OTT 서비스 확산과 관람 문화 변화로 인해
  영화관 산업 전반이 침체되고 있는 상황에서,
  영화관의 경험적 가치를 보완할 수 있는 웹 서비스를 기획하였습니다.
- **대상 사용자**
  - 영화관 이용객
  - 영화관 관리자 및 직원
- **개발 기간**
  2025.12.15 ~ 2026.01.30
- **개발 인원**
  3명

---

##  기술 스택

### Frontend
- Vue3
- Pinia
- JSP

### Backend
- Java 17
- Spring Boot

### Database
- Oracle RDBMS

### Infra / Tool
- AWS EC2
- Git / GitHub
- Jenkins

---
## 기술 스택

### Frontend
- Vue 3 (CDN 방식, JSP 내 구성)
- Pinia (전역 상태 관리)
- JavaScript / CSS
- Axios

### Backend
- Spring Boot
- Spring Data JPA
- Spring AI
- REST API

### Database
- Oracle

### DevOps / Tool
- GitHub
- Jenkins (CI/CD)

---

## 프로젝트 특징

- JSP 환경에서 Vue(CDN) + Pinia를 결합하여 점진적으로 SPA 구조를 구성
- 프런트엔드 상태를 Pinia 스토어로 통합 관리하여 컴포넌트 간 데이터 전달 구조 개선
- 프런트엔드와 백엔드 분리 구조(Spring REST API)로 역할 분담 명확화
- 영화 스케줄, 좌석 정보 등 사용자 선택에 따라 동적으로 변경되는 UI 구현

---

## 주요 기능

- 영화 목록 / 상세 정보 조회
- 지역 및 영화관 선택
- 상영 스케줄 조회
- 좌석 선택 및 예매
- 좌석 중복 예매 방지 로직
- 마이페이지 내 예매 관리

---

## 기술적 경험

- Vue(CDN) + Pinia를 사용해 JSP 환경에서도 전역 상태 관리 구조 설계
- 프런트엔드에서 사용자 편의에 맞게 데이터 가공,  
  백엔드에서는 검증 및 비즈니스 로직 처리
- REST API 기반으로 프런트엔드-백엔드 분리 아키텍처 경험
- Jenkins를 이용한 빌드 및 배포 자동화(CI/CD 파이프라인 구성)

---

## 아쉬운 점 및 개선 방향

- Spring Security를 적용하지 못해 사용자 인증 기반 기능을 구현하지 못한 점
- 추후 개선 계획
  - Spring Security 적용을 통한 사용자 인증 및 권한 처리
  - 프런트엔드 캐싱 전략 개선
  - 좌석 선택 알고리즘 개선

---

## 포트폴리오

> https://www.canva.com/design/DAG7Jyysa44/uqUZ8wVXx6HmIn4OHa6XNw/edit?utm_content=DAG7Jyysa44&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

---

