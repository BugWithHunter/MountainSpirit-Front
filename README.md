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
![산신령 (3)](https://github.com/user-attachments/assets/067d9ec8-3c31-45d4-b471-b18c7a66eaea)

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

### 3-6. 외부 API
<img width="2670" height="1054" alt="image" src="https://github.com/user-attachments/assets/5a3bc476-6fcd-4df2-a2bc-e943cba7821f" />

- 산 DB - 한국등산트레킹지원센터_100대명산 목록정보 서비스
- 코스 DB - 한국등산트레킹지원센터_100대명산 봉우리POI정보 서비스


## 4. 🚀테스트

### 4-1. 단위 테스트
<details>
  <summary>단위 테스트 결과</summary>
  <img width="1649" height="539" alt="image" src="https://github.com/user-attachments/assets/a373cd72-7ca9-4c5a-ac62-85aeb66b951c" />

</details>

### 4-2. API 테스트

<details>
  <summary>회원 테스트</summary>
    <details>
      <summary>회원 가입</summary>
  - 이미 가입된 회원
  <img width="884" height="272" alt="Image" src="https://github.com/user-attachments/assets/61251d57-bf54-4ee8-8c68-c82c878a718e" /><br>

  - 필수 입력 정보 누락
  <img width="829" height="247" alt="Image" src="https://github.com/user-attachments/assets/49ba5eb0-3a33-4246-9826-aaf74df3a7d7" /><br>

  - 블랙리스트로 접근 제한 회원 재가입을 하려고 했지만 가입거절
  <img width="772" height="288" alt="Image" src="https://github.com/user-attachments/assets/6da7b70b-9050-46da-af25-61b3127a84e6" /><br>

  - 회원 가입 완료
  <img width="821" height="366" alt="Image" src="https://github.com/user-attachments/assets/05598a2a-d59a-4463-9933-5594f18a1a7c" /><br>
    </details>

    
    <details>
      <summary>로그인(인증 완료시 JWT 토큰 발행)</summary>
  - 비밀번호 or 아이디 오 입력 
  <img width="835" height="192" alt="Image" src="https://github.com/user-attachments/assets/da4068d5-a343-469f-8c02-0f452a338595" /><br>

  - 탈퇴한 회원
  <img width="327" height="182" alt="Image" src="https://github.com/user-attachments/assets/0979a970-0231-43e9-967a-883f43c3b359" /><br>

  - 신고가 승인되어 접속 제한이 걸린 회원
  <img width="524" height="240" alt="Image" src="https://github.com/user-attachments/assets/7b473a5c-1db9-4901-a0f8-2b813d285df9" /><br>

  - 휴먼 상태 회원
  <img width="359" height="194" alt="Image" src="https://github.com/user-attachments/assets/5b3cdc73-da9d-4cad-8e1f-26ab47045574" /><br>

  - 블랙리스트 회원
  <img width="402" height="184" alt="Image" src="https://github.com/user-attachments/assets/6db12f36-01b1-4815-b529-9deeb91b1b78" /><br>

  - 연속적으로 일정 횟수 암호 오 입력으로 접속제한
  <img width="572" height="152" alt="Image" src="https://github.com/user-attachments/assets/47c298cf-5b21-4c6d-8897-0af7ec032c39" /><br>

  - 로그인 성공
  <img width="631" height="376" alt="Image" src="https://github.com/user-attachments/assets/2bd52ec0-beff-45c2-a33f-6efece4b23a6" /><br>
    </details>

    <details>
      <summary>등산 인증</summary>
      - 등산 시작
      <img width="1014" height="250" alt="Image" src="https://github.com/user-attachments/assets/bae03c4f-8ca9-490e-a0d9-026127af8fc5" /><br>
      - 등산 완료
      <img width="385" height="638" alt="Image" src="https://github.com/user-attachments/assets/e49ecabe-f3ea-4ad1-b7f4-49adcaacaf96" /><br>
    </details>
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
  <details>
  
  <summary>크루</summary>
  
  - 전체 크루 목록 조회
  <img width="718" height="908" alt="스크린샷 2025-09-30 183905" src="https://github.com/user-attachments/assets/3fcb9f50-f95c-49e6-91a0-b0b2ed573b96" />

  - 특정 크루 목록 조회
  <img width="696" height="906" alt="스크린샷 2025-09-30 183935" src="https://github.com/user-attachments/assets/40ca098c-3796-41e4-accd-73c4743b4f55" />

  - 크루 생성
  <img width="1860" height="895" alt="스크린샷 2025-09-30 184252" src="https://github.com/user-attachments/assets/d470e8c8-1676-4cf4-826c-e8783fb7a85c" />

  - 크루 수정
  <img width="1861" height="872" alt="스크린샷 2025-09-30 184443" src="https://github.com/user-attachments/assets/58170305-f912-448c-8b86-f7a38cdce37b" />

  - 크루 삭제
  <img width="1864" height="854" alt="스크린샷 2025-09-30 184458" src="https://github.com/user-attachments/assets/5c5e92c6-e1f4-4f40-bcbd-bf04d3892b9f" />

</details>
  <details>
  <summary>크루 구성원</summary>

  - 크루 가입 신청
  <img width="1864" height="882" alt="스크린샷 2025-09-30 184814" src="https://github.com/user-attachments/assets/9721988b-868f-43d5-a324-9cff62eb519d" />

  - 크루 가입 신청 목록
  <img width="703" height="910" alt="스크린샷 2025-09-30 184830" src="https://github.com/user-attachments/assets/8becda4f-69ec-4bb9-a851-6de10cb8d772" />

  - 크루 가입 신청 취소
  <img width="1853" height="904" alt="스크린샷 2025-09-30 184859" src="https://github.com/user-attachments/assets/20914b0d-fb37-40d5-ba85-900cf2ad7e42" />

  - 크루 가입 신청 승인
  <img width="706" height="904" alt="스크린샷 2025-09-30 185343" src="https://github.com/user-attachments/assets/627e900b-e9cb-488a-b468-747f4edb2f9a" />
  <img width="641" height="578" alt="스크린샷 2025-09-30 185215" src="https://github.com/user-attachments/assets/884d28aa-0e5e-412b-a2c7-16afe9182186" />

  - 크루 가입 신청 거절
  <img width="699" height="895" alt="스크린샷 2025-09-30 185451" src="https://github.com/user-attachments/assets/a0e10d81-65e6-48dc-a3d4-ec9a223ebd70" />
  <img width="700" height="588" alt="스크린샷 2025-09-30 185500" src="https://github.com/user-attachments/assets/58be6d37-9cb7-4e5c-99c0-9516aea3643a" />

  - 크루원 목록 조회
  <img width="701" height="860" alt="스크린샷 2025-09-30 190003" src="https://github.com/user-attachments/assets/9edbbe5a-88fa-4313-95a6-2c3250ed12cb" />

  - 크루 탈퇴
  <img width="1867" height="866" alt="스크린샷 2025-09-30 190747" src="https://github.com/user-attachments/assets/7fc17dcf-5dbb-4cec-b3a4-91f90d61a1de" />

  - 크루원 등급 수정
  <img width="1866" height="891" alt="스크린샷 2025-09-30 190901" src="https://github.com/user-attachments/assets/0ef04b71-90d3-4aae-bbb5-2b04995fe4f3" />

  - 크루원 추방
  <img width="1860" height="875" alt="스크린샷 2025-09-30 191009" src="https://github.com/user-attachments/assets/9e275858-d531-4276-a540-816960d39ae5" />
  <img width="1027" height="618" alt="스크린샷 2025-09-30 191039" src="https://github.com/user-attachments/assets/b30f9b04-679d-4506-877e-b129ca9564df" />
  
</details>
  <details>
  <summary>크루 등산</summary>
  
  - 전체 크루 등산 일정 목록 조회
  <img width="708" height="893" alt="스크린샷 2025-09-30 185554" src="https://github.com/user-attachments/assets/ab4ee968-3a1b-4a4a-a3ba-e0793c233e1e" />

  - 특정 크루 등산 일정 조회
  <img width="707" height="795" alt="스크린샷 2025-09-30 185608" src="https://github.com/user-attachments/assets/7a2674e9-3a46-4585-88bb-9f325c088c84" />

  - 크루 등산 일정 등록
  <img width="1862" height="858" alt="스크린샷 2025-09-30 185733" src="https://github.com/user-attachments/assets/9a741e34-ac8a-48b0-b482-c01ef28a2759" />

  - 크루 등산 일정 수정
  <img width="1859" height="887" alt="스크린샷 2025-09-30 185841" src="https://github.com/user-attachments/assets/7c9d5723-270a-4fea-ae00-365a68ffd210" />

  - 크루 등산 일정 삭제
  <img width="1860" height="862" alt="스크린샷 2025-09-30 185914" src="https://github.com/user-attachments/assets/890a24cb-1159-474f-ad38-e974a4de3295" />
  
  
</details>
</details>

<details>
  <summary>신고 테스트</summary>
  - 댓글 생성후
  <img width="1920" height="1080" alt="55" src="https://github.com/user-attachments/assets/f8665cf7-9772-48a2-9e35-2e9b05aa254e" />

  - 조건 만족시
<img width="1920" height="1080" alt="56" src="https://github.com/user-attachments/assets/4afd0fed-e1eb-4564-90ff-c17c946e3a5b" />

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
