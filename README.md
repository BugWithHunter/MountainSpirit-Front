# 🌳산신령🌳
<img width="300" height="300" alt="Image" src="https://github.com/user-attachments/assets/5bebdea1-dcbf-44de-8cf8-d2a072a3ef31" />

## 팀원

|<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/3200b60c-20c6-495d-87b8-a4fadff9827a" /> | <img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/a2ad6646-42f2-48ef-bc32-5d8de4721925" /> | <img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/6bb12f82-7f31-4383-825e-ba5c7e5e64be" /> |<img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/10fa156a-1f3f-4d71-a2d7-944723c7c26d" /> | <img width="150" height="150" alt="Image" src="https://github.com/user-attachments/assets/ef2e5d74-09ed-4d47-b1b7-34ae8a607d19" />|
|:---:|:---:|:---:|:---:|:---:|
|강형규|강지현|정건일|윤서진|민수현|

## 목차
[1. 🍃프로젝트 소개](#1-프로젝트-소개)

[2. 📄DDD](#2-DDD)

[3. 🔧설계](#3-설계)

[4. 🚀테스트](#4-테스트)

[5. 📗문서](#5-문서)

[6. 🛸기술 스택](#6-기술-스택)

[7. ✨동료 평가](#7-동료-평가)

## 1. 🍃프로젝트 소개
### 기획 의도
<img width="650" height="900" alt="image" src="https://github.com/user-attachments/assets/be676684-4665-4bc0-a28b-7556af5e8e04" /><br>

코로나 이후 등산에 대한 수요가 늘어남에 따라 산과 코스에 대한 정보를 쉽게 찾을 수 있고, 자신의 등산 기록을 관리할 수 있는 사이트를 기획했습니다. 코스별 난이도에 따라 자신의 능력에 맞는 코스를 선택할 수 있고, 완등 시 받을 수 있는 코스별 도장을 모으며 등산을 완료했을 때의 또 다른 재미를 경험할 수 있습니다. 여러 사람들과 함께 등산하고 싶다면 크루를 모집할 수 있고, 더 즐거운 등산을 위해 산과 회원 두가지의 랭킹 시스템을 제공합니다.
### 주요 기능
- 산, 등산로 정보 검색 조회
- 코스 완등 시 도장 획득
- 특정 산에 대한 코스 도장을 모두 획득하면 산 도장 획득
- 도장과 완등 횟수를 통한 랭킹 기능
- 여러 사람들과 함께 등산하기 위해 크루를 모집하거나 크루에 가입할 수 있음
- 자유 게시판을 통해 다른 회원들과 등산에 관련된 정보 공유
- 크루 게시판을 통해 같은 크루에 소속된 사람들과 소통

## 2. 📄DDD
![산신령 (2)](https://github.com/user-attachments/assets/4f74f7ce-a075-4a3a-a715-c9f735529783)

## 3. 🔧설계
### 3-1. MSA 시퀀스 다이어그램
<img width="1321" height="1560" alt="image" src="https://github.com/user-attachments/assets/29236dfa-6ff4-4cb7-a1fe-71b1629ec417" />

### 3-2. 논리 모델링
<img width="2156" height="2044" alt="image" src="https://github.com/user-attachments/assets/db46cfd4-8ed1-441e-b392-2aebfa9c6783" />

### 3-3. 물리 모델링
<img width="5800" height="2842" alt="Image" src="https://github.com/user-attachments/assets/355e9759-ce68-4b22-8df7-ee1539663bb7" />

### 3-4. MSA 아키텍처
<img width="1919" height="1006" alt="image" src="https://github.com/user-attachments/assets/f4e0ef09-9a58-4e5d-8c98-7d755f2f277b" />


### 3-5. API 명세
<details>
  <summary>Member Service</summary>
 <img width="1610" height="1756" alt="image" src="https://github.com/user-attachments/assets/6370c764-00da-425b-88a6-a8724ffcba24" />

</details>
<details>
  <summary>Main Service</summary>
 <img width="1542" height="1760" alt="image" src="https://github.com/user-attachments/assets/b95cb5d4-8c39-426e-9c19-5f1eba985b5b" />

</details>



## 4. 🚀테스트

### 4-1. 단위 테스트
<details>
  <summary>단위 테스트 결과</summary>
  <img width="1649" height="539" alt="image" src="https://github.com/user-attachments/assets/a373cd72-7ca9-4c5a-ac62-85aeb66b951c" />

</details>

### 4-2. API 테스트

<details>
  <summary>회원 테스트</summary>
</details>
  
<details>
  <summary>산, 코스 테스트</summary>
 <details>
  <summary>🐢산 & 코스 전체 조회, 검색 조회</summary>
  
  - 산 전체 조회
  <img width="1743" height="879" alt="Image" src="https://github.com/user-attachments/assets/77619dc4-6891-439b-b0ac-efbe73ded4cb" /><br>
  
  - 산 검색 조회
<img width="1731" height="862" alt="image" src="https://github.com/user-attachments/assets/2ee6a6c9-9e22-4f8a-9205-fe2efde9fa1c" /><br>
  
  - 코스 전체 조회
<img width="1741" height="858" alt="image" src="https://github.com/user-attachments/assets/84ba9ac0-9f15-481b-a5f1-100c62de5dc6" /><br>

  - 코스 검색 조회
<img width="1742" height="866" alt="image" src="https://github.com/user-attachments/assets/d42e2fd1-86f1-4d8d-ac09-32c38dc2da35" /><br>

 </details>
 <details>
  <summary>🐢산 후기 조회/등록/수정/삭제</summary>
  
  - 산 후기 조회
<img width="1722" height="862" alt="image" src="https://github.com/user-attachments/assets/8ec0402d-23fe-46e3-bc5b-a1f479f75330" /><br>

  - 산 후기 등록
<img width="1758" height="869" alt="image" src="https://github.com/user-attachments/assets/352a2775-5bbd-4cef-ab41-ca75e0e8268c" /><br>

  - 산 후기 수정
<img width="1747" height="865" alt="image" src="https://github.com/user-attachments/assets/0716de3a-b287-45bc-8723-6768b908b151" /><br>

  - 산 후기 삭제
<img width="1760" height="873" alt="image" src="https://github.com/user-attachments/assets/1a75ddb0-9367-4987-9b64-2301292f9bdc" /><br>

 </details>
 
 <details>
  <summary>🐢코스 후기 조회/등록/수정/삭제</summary>
  
  - 코스 후기 조회
  <img width="1745" height="867" alt="image" src="https://github.com/user-attachments/assets/159fe14f-b48a-40bc-87a2-3e39232ebafe" /><br>
  
  - 코스 후기 등록
<img width="1745" height="871" alt="image" src="https://github.com/user-attachments/assets/b92e39da-5d23-4c04-b885-c0b4733d2226" /><br>

  - 코스 후기 수정
<img width="1729" height="880" alt="image" src="https://github.com/user-attachments/assets/5e446068-304e-4993-b889-2e64ce12c649" /><br>

  - 코스 후기 삭제
<img width="1738" height="874" alt="image" src="https://github.com/user-attachments/assets/6992b188-dc9c-44bf-8b80-ca59070b42ed" /><br>

 </details>
 
 <details>
  <summary>🐢산 북마크 조회/등록/삭제</summary>
  
  - 산 북마크 조회
<img width="1745" height="870" alt="image" src="https://github.com/user-attachments/assets/e596c21e-9efe-46d5-978b-706fda3da6df" /><br>

  - 산 북마크 등록
<img width="1738" height="870" alt="image" src="https://github.com/user-attachments/assets/a70dae32-b81a-4d4e-b152-76fe7ad4093a" /><br>

  - 산 북마크 삭제
<img width="1742" height="860" alt="image" src="https://github.com/user-attachments/assets/3aee5893-1932-4c1a-942c-c01c998276c6" /><br>

 </details>
 
 <details>
  <summary>🐢코스 북마크 조회/등록/삭제</summary>
  
  - 코스 북마크 조회
  <img width="1742" height="864" alt="image" src="https://github.com/user-attachments/assets/194ab119-b161-4283-b748-caa824bb41dc" /><br>

  - 코스 북마크 등록
   <img width="1735" height="874" alt="image" src="https://github.com/user-attachments/assets/3b1e4b78-df1a-4b2d-b0af-43874830a040" /><br>

  - 코스 북마크 삭제
<img width="1735" height="874" alt="image" src="https://github.com/user-attachments/assets/3708343f-2d90-40f4-aa8e-bee30ab791a6" />

 </details>
 
 <details>
  <summary>🐢산 & 코스 도장 조회</summary>
  
  - 산 도장 조회
  <img width="1750" height="877" alt="image" src="https://github.com/user-attachments/assets/2155669c-5233-4035-8587-b717340355e5" /><br>

  - 코스 도장 조회
<img width="1753" height="879" alt="image" src="https://github.com/user-attachments/assets/ae14d49a-a77c-407e-9c82-011822d61400" /><br>
 </details>
 
</details>

<details>
  <summary>게시판 테스트</summary>
  <details>
    <summary>게시판 CRUD</summary>
    
  - 게시판 목록 조회

  <img width="500" height="530" alt="Image" src="https://github.com/user-attachments/assets/3f8c4103-b0d0-4007-ab90-f6e22c2f415d" /><br>
  
  - 게시글 상세 조회
  <img width="1689" height="778" alt="Image" src="https://github.com/user-attachments/assets/3d5fd86e-e837-484e-b582-87214d5e903e" /><br>
    
  - 게시글 검색

  <img width="570" height="442" alt="Image" src="https://github.com/user-attachments/assets/0ddf4afa-7f57-46f2-9447-987781fa0c14" /><br>
  
  - 게시글 작성
  <img width="1188" height="722" alt="Image" src="https://github.com/user-attachments/assets/a07bd7b3-b49f-402c-b251-dbbacd9a39e3" /><br>
  <img width="1344" height="778" alt="Image" src="https://github.com/user-attachments/assets/f77819d7-29a9-4ab4-b825-9cf78bebc6ca" /><br>
    
  - 게시글 수정
  <img width="1705" height="812" alt="Image" src="https://github.com/user-attachments/assets/dc815581-5160-451e-a728-568589fb07dc" /><br>

  - 게시글 이미지 추가
  <img width="1720" height="832" alt="Image" src="https://github.com/user-attachments/assets/7bc08a7f-085e-4af8-b88e-3ab5bb3de223" /><br>
    
  - 게시글 이미지 삭제
  <img width="1709" height="825" alt="Image" src="https://github.com/user-attachments/assets/5391f7e3-f500-4fc3-820a-f8073e4beea4" /><br>

  - 게시글 삭제
  <img width="1594" height="656" alt="Image" src="https://github.com/user-attachments/assets/796e5f35-0ae4-4b71-a223-12c22a11755a" /><br>
    
  </details>

  <details>
    <summary>좋아요, 댓글 테스트</summary><br>
    
  - 좋아요 등록/삭제
  <img width="1696" height="833" alt="Image" src="https://github.com/user-attachments/assets/6bd49951-1d1f-4da3-b1ba-67379419e070" /><br>

  - 댓글 조회

  - 댓글 작성
  <img width="1690" height="826" alt="Image" src="https://github.com/user-attachments/assets/8ec361f4-b854-46f0-b171-4cb8231d8255" /><br>

  - 댓글 수정
   <img width="1712" height="817" alt="Image" src="https://github.com/user-attachments/assets/22eee574-78a0-413d-a97f-eb564c229852" /><br>

  - 댓글 삭제
    
  </details>
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

<img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white">

- 개발 툴

<div> 
<img src="https://img.shields.io/badge/Spring-236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/Apache-D22128?style=for-the-badge&logo=apache&logoColor=white">
<img src="https://img.shields.io/badge/Apache_Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">
<img src="https://img.shields.io/badge/intelliJ_IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
</div>

- 버전 관리

<div>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
</div>

- 테스트

<div>
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
<img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white">
</div>

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
