# 🌳산신령🌳
<img width="300" height="300" alt="Image" src="https://github.com/user-attachments/assets/5bebdea1-dcbf-44de-8cf8-d2a072a3ef31" />

## 팀원

|<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/3200b60c-20c6-495d-87b8-a4fadff9827a" /> | <img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/a2ad6646-42f2-48ef-bc32-5d8de4721925" /> | <img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/6bb12f82-7f31-4383-825e-ba5c7e5e64be" /> |<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/10fa156a-1f3f-4d71-a2d7-944723c7c26d" /> | <img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/ef2e5d74-09ed-4d47-b1b7-34ae8a607d19" />|
|:---:|:---:|:---:|:---:|:---:|
|강형규|강지현|정건일|윤서진|민수현|

## 목차
[1. 프로젝트 소개](#1-프로젝트-소개)

[2. DDD](#2-DDD)

[3. 설계](#3-설계)

[4. 테스트](#4-테스트)

[5. 문서](#5-문서)

[6. 기술 스택](#6-기술-스택)

[7. 동료 평가](#7-동료-평가)

## 1. 🍃프로젝트 소개
### 기획 의도
 코로나19 이후 2023년 상반기까지 북한산, 계룡산, 치악산 등 도심권 국립공원의 탐방객 수가 지난해 같은 기간 대비 평균 약 21% 증가했고, 코로나 기간 동안 도심 근교 산을 찾는 주말 유동 인구가 급증한 것으로 나타났습니다. 또한 20~30대 중심의 새로운 등산 문화가 확산되면서 등산 인구가 다시 늘고 있으며, 등산 활동은 코로나19 시기 안전한 야외 활동으로 인기를 끌었습니다. 등산뿐 아니라 아웃도어 활동 전반에 대한 관심과 참여가 크게 상승한 상황입니다.</br>
 이처럼 코로나 이후 등산에 대한 수요가 늘어남에 따라 산과 코스에 대한 정보를 쉽게 찾을 수 있고, 자신의 등산 기록을 관리할 수 있는 사이트를 기획했습니다. 코스별 난이도에 따라 자신의 능력에 맞는 코스를 선택할 수 있고, 완등 시 받을 수 있는 코스별 도장을 모으며 등산을 완료했을 때의 또 다른 재미를 경험할 수 있습니다. 여러 사람들과 함께 등산하고 싶다면 크루를 모집할 수 있고, 더 즐거운 등산을 위해 산과 회원 두가지의 랭킹 시스템을 제공합니다.
### 주요 기능
- 산, 등산로 정보 검색 조회
- 코스 완등 시 도장 획득
- 특정 산에 대한 코스 도장을 모두 획득하면 산 도장 획득
- 도장과 완등 횟수를 통한 랭킹 기능
- 여러 사람들과 함께 등산하기 위해 크루를 모집하거나 크루에 가입할 수 있음
- 자유 게시판을 통해 다른 회원들과 등산에 관련된 정보 공유
- 크루 게시판을 통해 같은 크루에 소속된 사람들과 소통

## 2. 📄DDD

## 3. 🔧설계
### 3-1. 논리 모델링

### 3-2. 물리 모델링
<img width="5800" height="2842" alt="Image" src="https://github.com/user-attachments/assets/355e9759-ce68-4b22-8df7-ee1539663bb7" />

### 3-3. MSA 아키텍처

### 3-4. API 명세
<details>
  <summary>Member Service</summary>
</details>
<details>
  <summary>Main Service</summary>
</details>

### 3-5. 로그인 토큰


## 4. 🚀테스트

### 4-1. 단위 테스트
<details>
  <summary>단위 테스트 결과</summary>
</details>

### 4-2. API 테스트

<details>
  <summary>회원 테스트</summary>
</details>
  
<details>
  <summary>산, 코스 테스트</summary>
</details>

<details>
  <summary>게시판 테스트</summary>
</details>

<details>
  <summary>크루 테스트</summary>
</details>

<details>
  <summary>신고 테스트</summary>
</details>


## 5. 📗문서

### 5-1. 요구사항 명세서
<a href="https://docs.google.com/spreadsheets/d/1VBYRhzfoiGNax0MA7ngQUardj3lY4btuP-MUCo3M21k/edit?usp=sharing" target="_blank">🪝요구사항 명세서</a>


### 5-2. WBS
<a href="https://docs.google.com/spreadsheets/d/1VBYRhzfoiGNax0MA7ngQUardj3lY4btuP-MUCo3M21k/edit?usp=sharing" target="_blank">📆WBS</a>
<img width="1142" height="554" alt="Image" src="https://github.com/user-attachments/assets/3b304632-4b70-4566-adee-c7e18e61afee" />

## 6. 🛸기술 스택
- DB

![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
- 프레임워크

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring](https://img.shields.io/badge/spring_security-%6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring](https://img.shields.io/badge/spring_boot-%6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
- 버전 관리

![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
- 테스트

![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

## 7. ✨동료 평가
- 강형규

|이름|평가|
|:---:|---|
|강지현| |
|정건일| |
|윤서진| |
|민수현| |
<br>

- 강지현

|이름|평가|
|:---:|---|
|강형규| |
|정건일| |
|윤서진| |
|민수현| |
<br>

- 정건일

|이름|평가|
|:---:|---|
|강형규| |
|강지현| |
|윤서진| |
|민수현| |
<br>

- 윤서진

|이름|평가|
|:---:|---|
|강형규| |
|강지현| |
|정건일| |
|민수현| |
<br>

- 민수현

|이름|평가|
|:---:|---|
|강형규| |
|강지현| |
|정건| |
|윤서진| |
<br>
