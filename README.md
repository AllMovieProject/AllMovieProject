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

**배포 URL**: [https://sist-all-movie.duckdns.org](https://sist-all-movie.duckdns.org)

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

##  프로젝트 구조

-project-root

┣ AllMovieProject

┃ ┣ src

┃ ┗ build.gradle

┣ README.md


---

##  주요 기능

### 사용자 기능
- 회원가입 / 로그인
- 영화 예매 및 취소
- 매점 구매 및 취소
- 공지사항 확인, 단체 관람 문의

### 관리자 기능
- 매점 재고 관리
- 주문 관리
- 공지사항 CRUD

---

##  화면 구성 (UI)

### 영화 예매
<table>
  <tr>
    <th>스케줄 페이지 (초기 화면)</th>
    <th>스케줄 페이지 (스케줄 O)</th>
  </tr>
  <tr>
    <td><img width="600" height="804" alt="Image" src="" /></td>
    <td><img width="600" height="804" alt="Image" src="" /></td>
  </tr>
  <tr>
    <td><img width="600" height="804" alt="Image" src="" /></td>
    <td><img width="600" height="804" alt="Image" src="" /></td>
  </tr>
  <tr>
    <th>좌석 페이지</th>
    <th>결제 페이지</th>
  </tr>
  <tr>
    <td><img width="600" height="804" alt="Image" src="" /></td>
    <td><img width="600" height="804" alt="Image" src="" /></td>
  </tr>
  <tr>
    <td><img width="600" height="804" alt="Image" src="" /></td>
    <td><img width="600" height="804" alt="Image" src="" /></td>
  </tr>
</table>

>  추후 스크린샷 추가 권장

---

##  ERD / DB 설계

- 주요 테이블 설명
- 테이블 간 관계

<table>
  <tr>
    <th>영화 예매 DB 관계도</th>
  </tr>
  <tr>
    <td><img width="600" height="804" alt="Image" src="https://github.com/user-attachments/assets/259bb5b1-640e-40af-8764-ea881773dd20" /></td>
  </tr>
</table>
---

##  API 설계 (선택)

| Method | URL | 설명 |
|--------|-----|------|
| GET | /api | |

---
