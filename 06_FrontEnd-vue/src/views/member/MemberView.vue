<template>

  <!-- 대시보드를 좌/우 2열로 나누는 최상위 컨테이너 -->
  <section class="dash">
        <section class="pane-left">
            <article class="card">
                <h3 class="card-title">개인 정보</h3>
                <!-- 네 정보 리스트 등 -->
                <ul class="info-list">
                    <li><strong>이름</strong><span>홍길동</span></li>
                    <li><strong>Email</strong><span>me@gmail.com</span></li>
                    <li><strong>닉네임</strong><span>산신령</span></li>
                    <li><strong>점수</strong><span>55</span></li>
                    <li><strong>등급</strong><span>새싹이</span></li>
                    <li><strong>가입일</strong><span>2025-01-01</span></li>
                </ul>
                <div class="actions">
                    <button class="btn">비밀번호 변경</button>
                    <button class="btn ghost">신고내역 조회</button>
                </div>
            </article>

            <article class="card">
                <h3 class="card-title">개인 정보</h3>
                <!-- 네 정보 리스트 등 -->
                <ul class="info-list">
                    <li><strong>산</strong>
                        <select class="ui-select" v-model="selectedMountain" @change="changeMountain">
                            <option v-for="mt in mountains" :value="mt">
                                {{ mt.frtrlNm }}
                            </option>
                        </select>
                    </li>
                    <li><strong>코스</strong>
                        <select class="ui-select" v-model="selectedCourse" @change="changeCourse" >
                            <option v-for="course in selectedMountain.course" :value="course">
                                {{ course.placeNm }}
                            </option>
                        </select>
                    </li>
                </ul>
                <div class="actions">
                    <button class="btn">등산 시작</button>
                    <button class="btn ghost">등산 완료</button>
                </div>
            </article>
        </section>
        
        <section class="pane-right">
                <EchartDonut 
                :hsaStamp="hsaStamp" :totalStamp="totalStamp" 
                v-slot:title>
                    <h2 class="slot-title">흭득 도장</h2>
                </EchartDonut>

                <EchartLine
                :hsaStamp="hsaStamp" :totalStamp="totalStamp" 
                v-slot:title>
                    <h2 class="slot-title">월별 등산 기록</h2>
                </EchartLine>
        </section>  
    </section>  
</template>

<script setup>
    import { onMounted, ref, reactive } from 'vue';
    import EchartDonut from '@/components/echart/EchartDonut.vue';
    import EchartLine from '@/components/echart/EchartLine.vue';
    const apiKey = import.meta.env.VITE_API_KEY;
    console.log('apiKey:',apiKey);
    const hsaStamp = reactive([]);
    const totalStamp = reactive([]);
    const selectedMountain = ref({});
    const selectedCourse = ref({});
    const mountains = reactive([
        {
            frtrlNm: '관악산',
            frtrlId: '0000000010',
            course: [
                {
                    poiId: '0000002790',
                    placeNm: '연주대'
                },
                {
                    poiId: '0000002991',
                    placeNm: '삼성산 정상'
                },
                {
                    poiId: '0000003143',
                    placeNm: '태양산'
                },
                {
                    poiId: '0000003347',
                    placeNm: '관모봉'
                },
            ]
        },
        {
            frtrlNm: '북한산',
            frtrlId: '0000000047',
            course: [
                {
                    poiId: '0000014336',
                    placeNm: '형제봉'
                },
                {
                    poiId: '0000014339',
                    placeNm: '향로봉'
                },
                {
                    poiId: '0000014415',
                    placeNm: '기자봉'
                },
                {
                    poiId: '0000014597',
                    placeNm: '북한산 정상(백운대)'
                },
            ]
        }
    ]);
    
    const changeMountain = () => {
        console.log(selectedMountain.value);
    }
    
    const changeCourse = () => {
        console.log(selectedCourse.value);
    }
    
    for(let i = 0; i < 100; i++){
        if(i % 3 == 0){
            hsaStamp.push(i);
        }
        totalStamp.push(i);
    }
    console.log('산정보:',mountains);
    console.log(hsaStamp.length, totalStamp.length);

</script>
<style scoped>
    .dash {
        display: flex;      /* 가로 배치 */
        gap: 16px;          /* 좌/우 간격 */
        align-items: stretch;
        padding: 12px;
        width: 100%;              /* 가로 전체 사용 */
        min-height: 80vh;         /* 보기 좋게 높이 확보 */
    }
    /* ─────────────────────────────────────────────
   왼쪽 카드(개인 정보)만 보기 좋게 개선
   - 카드 형태(배경/테두리/둥근 모서리/그림자)
   - 타이틀/리스트/버튼 간 간격 정리
   - 각 행은 "라벨  컬럼 | 값  컬럼" 구조의 그리드로 정돈
   ───────────────────────────────────────────── */

/* 왼쪽 패널 자체를 "카드"처럼 보이게 */
.pane-left {
  flex: 4 1 0;                    /* (기존) 좌우 비율 유지 */
  min-width: 0;
  display: flex;
  flex-direction: column;

  gap: 16px;                      /* 타이틀/리스트/버튼 사이 간격 */
}

.pane-left > .card {

  /* 카드 느낌 */
  background: #fff;               /* 하얀 배경 */
  border: 1px solid #e5e7eb;      /* 연한 테두리 */
  border-radius: 12px;            /* 둥근 모서리 */
  box-shadow: 0 1px 6px rgba(0,0,0,.06); /* 부드러운 그림자 */

  /* 내부 여백과 요소 간격 */
  padding: 16px 20px;             /* 카드 안쪽 여백 */
  gap: 16px;                      /* 타이틀/리스트/버튼 사이 간격 */
}

/* 카드 타이틀: 위아래 간격/크기 살짝 키움 */
.card-title {
  margin: 4px 0 6px;              /* 위 4px, 아래 6px */
  text-align: left;               /* 왼쪽 정렬(원하면 center로) */
  font-size: 16px;                /* 살짝 크게 */
  font-weight: 700;
  color: #0f172a;                 /* 진한 남색계열 */
}

/* 정보 리스트 전체 컨테이너 */
.info-list {
  list-style: none;               /* 기본 점 표시 제거 */
  margin: 0;                      /* 바깥 여백 제거 */
  padding: 0;                     /* 안쪽 여백 제거 */

  /* 2열 그리드: "라벨 120px | 값(auto)" */
  display: grid;
  grid-template-columns: 120px 1fr; /* 왼쪽 라벨 고정폭, 오른쪽 값 가변 */
  row-gap: 10px;                  /* 행 간격(세로) */
  column-gap: 12px;               /* 열 간격(가로) */

  /* 위/아래 구분선 살짝 */
  border-top: 1px solid #f1f5f9;
  padding-top: 8px;
}

/* 각 행(li)은 그리드의 한 줄로 동작하도록 컨텐츠만 표시 */
.info-list li {
  display: contents;              /* li 자체 상자는 없애고 자식만 그리드에 배치 */
  font-size: 16px;                /* 살짝 크게 */
}

/* 라벨(왼쪽) */
.info-list li strong {
  grid-column: 1;                 /* 1열(라벨) */
  align-self: center;             /* 수직 가운데 */
  color: #475569;                 /* 회색 톤 */
  font-weight: 600;
  white-space: nowrap;            /* 줄바꿈 방지 */
}

/* 값(오른쪽) */
.info-list li span {
  grid-column: 2;                 /* 2열(값) */
  align-self: center;             /* 수직 가운데 */
  color: #111827;                 /* 진한 블랙계열 */
  justify-self: end;              /* 오른쪽 정렬(숫자/날짜 정렬에 좋음) */
}

/* 라벨/값 사이를 각 행 아래 보더 라인 추가 (옵션)*/
.info-list li strong::after {
  content: '';
  grid-column: 1 / span 2;
  display: block;
  height: 1px;
  background: #f8fafc;
  margin-top: 25px;
}

/* 버튼 묶음: 정렬/간격/높이 통일 */
.actions {
  display: flex;
  gap: 10px;                      /* 버튼 사이 간격 */
  margin-top: 4px;                /* 리스트와 버튼 사이 간격 */
  flex-wrap: wrap;                /* 좁을 때 줄바꿈 허용 */
  justify-content: center;        /*  버튼을 중앙 정렬 */
}

/* 버튼 공통 */
.btn {
  height: 36px;                   /* 동일 높이 */
  padding: 0 12px;                /* 좌우 여백 */
  border-radius: 8px;             /* 둥근 버튼 */
  background:#00c774;             
  color:#fff;
  border: 0;
  cursor: pointer;
  display: inline-flex;
  align-items: center;            /* 텍스트 수직 중앙 */
  gap: 6px;                       /* 아이콘/텍스트 간격 대비 */
  box-shadow: 0 1px 2px rgba(0,0,0,.04);
}

/* 서브 버튼(옅은 스타일) */
.btn.ghost {
  background:#E6E6E6;
  border:1px solid #d1d5db;
  color:#111827;
}

/* ===================================================== */
/* select 테그 css */
/* =================== 커스텀 셀렉트 공통 스타일 =================== */
/* .info-list는 display:grid 이고 li는 display:contents 이므로,
   select가 "값(오른쪽) 컬럼"에 정확히 들어가도록 grid 위치를 지정한다. */
.info-list .ui-select {
  grid-column: 2;                  /* 2열(값 칼럼)에 배치 */
  justify-self: stretch;           /* 가로로 꽉 차도록 */
  align-self: center;              /* 세로 가운데 정렬 */
  min-width: 0;                    /* 그리드 수축 시 텍스트 잘림 방지 */
}

/* 실질적인 UI 스타일 시작 */
.ui-select {
  appearance: none;                /* 브라우저 기본 화살표 제거(크롬/엣지/사파리) */
  -moz-appearance: none;           /* 파이어폭스 기본 화살표 제거 */
  -webkit-appearance: none;        /* 웹킷 계열 기본 화살표 제거 */

  width: 100%;                     /* 그리드 셀 가로 채우기 */
  height: 40px;                    /* 보기 좋은 높이 */
  padding: 0 40px 0 12px;          /* 오른쪽은 커스텀 화살표 공간 확보, 왼쪽 여백 */
  border: 1px solid #d1d5db;       /* 은은한 테두리(#d1d5db ≈ gray-300) */
  border-radius: 8px;              /* 둥근 모서리 */
  background-color: #ffffff;       /* 흰 배경 */
  font-size: 14px;                 /* 기본 글자 크기 */
  line-height: 40px;               /* 높이와 동일하여 수직 중앙정렬 효과 */
  color: #111827;                  /* 진한 텍스트(#111827 ≈ gray-900) */

  /* 커스텀 드롭다운 화살표 (inline SVG) */

  background-repeat: no-repeat;     /* 화살표 반복 없음 */
  background-position: right 12px center; /* 오른쪽 12px, 세로 중앙 */
  background-size: 16px 16px;      /* 화살표 크기 */

  transition: border-color .15s ease, box-shadow .15s ease, background-color .15s ease; /* 상호작용 부드럽게 */
  cursor: pointer;                 /* 손가락 포인터 */
}

/* Hover 상태: 테두리를 조금 진하게 */
.ui-select:hover {
  border-color: #9ca3af;           /* gray-400 */
}

/* Focus 상태: 접근성 좋은 포커스 링 */
.ui-select:focus {
  outline: none;                   /* 기본 아웃라인 제거 */
  border-color: #111827;           /* 테두리 진하게 */
  box-shadow: 0 0 0 3px rgba(17, 24, 39, 0.15); /* 진한 그레이 링(부드럽게) */
}

/* 비활성화 상태 */
.ui-select:disabled {
  background-color: #f3f4f6;       /* 회색 배경(gray-100) */
  color: #9ca3af;                  /* 텍스트 연함(gray-400) */
  border-color: #e5e7eb;           /* 테두리 연함(gray-200) */
  cursor: not-allowed;             /* 금지 커서 */
}

/* 옵션 텍스트 줄바꿈 방지(긴 텍스트 대비) */
.ui-select option {
  white-space: nowrap;             /* 한 줄 유지 */
}


/* ===================================================== */
/* 우측 카드 css 요소 */
    .pane-right {
        flex: 6 1 0;              /* 남은 공간을 동일 비율로 분배 */
        min-width: 0;             /* flex 아이템의 수축 허용 (길게 늘어남 방지) */
        display: flex;
        flex-direction: column;
    }

    /* ③ 오른쪽은 세로로 2장 카드 */
    .pane-right {
        gap: 5px;
    }
    

    /* ⑤ h2 기본 마진이 커서 겹치므로 초기화 */
    .slot-title {
        margin: 0;
        font-size: 25px;
        font-weight: 700;
        color: #111827;
        text-align: center;  /* 중앙 정렬 (슬롯 내부에서 필요시) */
    }
</style>