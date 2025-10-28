<template>

  <!-- 대시보드를 좌/우 2열로 나누는 최상위 컨테이너 -->
  <section class="dash">
        <section class="pane-left">
            <article class="card">
                <h3 class="card-title">개인 정보</h3>
                <!-- 네 정보 리스트 등 -->
                <ul class="info-list">
                    <li><strong>이름</strong><span>{{ userInfo.memName }}</span></li>
                    <li><strong>Email</strong><span>{{ userInfo.email }}</span></li>
                    <li><strong>닉네임</strong><span>{{ userInfo.nickname }}</span></li>
                    <li><strong>점수</strong><span>{{ userInfo.score }}</span></li>
                    <li><strong>등급</strong><span>{{ userInfo.rankName }}</span></li>
                    <li><strong>가입일</strong><span>{{ userInfo.signInDate }}</span></li>
                </ul>
                <div class="actions">
                    <button class="btn" @click="openModal('1213','12345',true)">비밀번호 변경</button>
                    <button class="btn ghost" @click="goToProtest">신고내역 조회</button>
                    <button class="btn ghost" @click="goToProtestConfirm">이의신청 확인</button>
                </div>
            </article>

            <article class="card">
                <h3 class="card-title">개인 정보</h3>
                <!-- 네 정보 리스트 등 -->
                <ul class="info-list">
                    <li><strong>산</strong>
                        <select class="ui-select" v-model="selectedMountain" @change="changeMountain">
                            <option v-for="mt in mountainInfo" :value="mt">
                                {{ mt.frtrlNm }}
                            </option>
                        </select>
                    </li>
                    <li><strong>코스</strong>
                        <select class="ui-select" v-model="selectedCourse" @change="changeCourse" >
                            <option v-for="course in selectedMountain.courses" :value="course">
                                {{ course.placeNm }}
                            </option>
                        </select>
                    </li>
                </ul>
                <div class="actions">
                    <button class="btn" @click="startClimb"  :disabled="!selectedCourse || !buttonflag">등산 시작</button>
                    <button class="btn ghost" @click="endClimb"  :disabled="!selectedCourse || buttonflag">등산 완료</button>
                </div>
            </article>
        </section>
        
        <section class="pane-right">
                <EchartDonut 
                :chartItems="stamps" :radius="stampRadius"
                v-slot:title>
                    <h2 class="slot-title">도장 현황</h2>
                </EchartDonut>

                <EchartLine
                :cumId="monthlyRecord.cumId" :xAxis="monthlyRecord.xaxis"  :yAxis="monthlyRecord.yaxis"
                v-slot:title>
                    <h2 class="slot-title">월별 등산 기록</h2>
                </EchartLine>
        </section>  
    </section>  

    
   <!-- 모달 컴포넌트 -->
  <BaseModal
        v-model:open="modal.open"
        :title="modal.title"
        :message="modal.message"
        :confirmText="'확인'"
        :hasFunction="modal.hasFunction"
        @confirm="ModalConfirm"
    > </BaseModal>
</template>

<script setup>
    import { onMounted, ref, reactive, watch } from 'vue';
    import EchartDonut from '@/components/echart/EchartDonut.vue';
    import EchartLine from '@/components/echart/EchartLine.vue';
    import { useRouter } from 'vue-router';
    import { useUserStore } from '@/stores/user';
    import axios from 'axios';
    import BaseModal from '@/components/BaseModal.vue' // 경로는 프로젝트에 맞게

    const goToProtest = () => {
      router.push('/member-view/memberProtest');
    }

    const goToProtestConfirm = () => {
        router.push('/member-view/protestConfirm');
    }

    const tttt = ref('');
    const router = useRouter();
    const userStore = useUserStore();
    const hasStamp = ref([]);
    const courses = ref([]);
    const mountains = ref([]);
    const userInfo = ref({});
    const monthlyRecord = ref({});
    const notCompleteClimbing = ref([]);

    //로그인 안하고 url 치고 들어오는거 막기 위함
    if(userStore.token === '' || userStore.token === null){
        router.push("/");
    }

    const modal = reactive({
        open: false,
        title: '알림',
        message: '',
        confirmText: '확인',
        hasFunction: false  
    })

    function ModalConfirm() {
        console.log('ModalConfirm 실행 됨')
        console.log('모달 컨펌:' , tttt.value);
        // router.push('/member/login');
    }

    async function onSubmit() {
        openModal('테스트 모달', '알림', true);
    }

    function openModal(msg, title = '알림', hasFunction = false) {
        modal.title = title;
        modal.message = msg;
        modal.open = true;
        modal.hasFunction = hasFunction;
    }

    // 등산 시작, 완료 활성화 상태값(30일 이내 동일 한 코스로 완료 되지 않은 등산 기록 있을 경우 등산 시작 비활 성화)
    const buttonflag = ref(false);

    const stamps = ref([]);
    const stampRadius = reactive([]);
    const hsaStamp = reactive([]);
    const totalStamp = reactive([]);
    const selectedMountain = ref({});
    const selectedCourse = ref(null);

    // 산과 > 코스 (코스를 품은 산 목록)
    const mountainInfo = ref([]);
    
    watch(() => selectedCourse.value, ()=> {
        if(notCompleteClimbing.value.some((x) => x.poiId === selectedCourse.value?.poiId)) {
            buttonflag.value = false;
        }
        else{
            buttonflag.value = true;
        }
    });
    // })

    onMounted(async() => {
        try {
            // ① 병렬로 요청을 시작 (순서 상관 없음)
            const tasks = [];
            tasks.push(axios.get('http://localhost:8000/main-client/search/course',{headers: {Authorization: `Bearer ${userStore.token}`} }));
            tasks.push(axios.get('http://localhost:8000/main-client/search/mountain',{headers: {Authorization: `Bearer ${userStore.token}`} }));
            tasks.push(axios.get(`http://localhost:8000/main-client/climb-history/climbing-by-status?userId=${userStore.userId}&status=N`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            
            // tasks.push(axios.get(`http://localhost:8000/main-client/stamp/corse-stamp/${userStore.userId}`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            // tasks.push(axios.get(`http://localhost:8000/member-client/member/member-info/${userStore.userId}`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            
            // tasks.push(axios.get(`http://localhost:8000/main-client/climb-history/climbing-by-status?userId=${userStore.userId}&status=Y`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            
            // 그래프 데이터, 유저 정보(등산 완료시 갱신되는 데이터 통신은 replaceData() 함수로 실행)
            replaceData();
            // ② 모든 요청이 끝날 때까지 기다림 (모두 끝나면 배열로 반환됨)
            const resAll = await Promise.all([...tasks]);

            // ③ 각각 결과 사용
            console.log('courses:', resAll[0].data);
            console.log('mountains:', resAll[1].data);
            console.log('notCompleteClimbing',resAll[2].data )
            // console.log('hasStamp:', resAll[0].data);
            // console.log('memberInfo:', resAll[3].data);

            courses.value     = resAll[0].data;
            mountains.value   = resAll[1].data;
            notCompleteClimbing.value = resAll[2].data;
            // hasStamp.value      = resAll[0].data;
            // userInfo.value      = resAll[3].data;

            // 도넛 차트에 들어갈 데이터
            stamps.value.push({name: '보유', value:hasStamp.value.length });
            stamps.value.push({name: '미보유', value: courses.value.length - hasStamp.value.length});
            stampRadius.push('40%');
            stampRadius.push('65%');

            // 산 + 코스 join
            // ① frtrlId 별로 코스들을 미리 묶어둠
            const courseMap = new Map();
            console.log('courseMap:',courseMap);

            // courses 배열 순회  
            for (const c of courses.value) {
                if (!courseMap.has(c.frtrlId)) {
                    courseMap.set(c.frtrlId, []);
                }

                courseMap.get(c.frtrlId).push(c);
            }

            // ② mountains 배열을 돌면서 courseMap에서 꺼내 병합
            mountainInfo.value = mountains.value.map(m => {
                const {frtrlId, frtrlNm } = m;
                return{
                    frtrlId,
                    frtrlNm,
                    courses: courseMap.get(m.frtrlId) || [] // 없으면 빈 배열
                }

            });

            // console.log('코스 + 산 머지:', mountainInfo.value);

        } catch (err) {
            // ④ 하나라도 실패하면 여기로 옴
            console.error('요청 중 에러 발생:', err);
        }
    })

    const changeMountain = () => {
        selectedCourse.value = null;
        console.log(selectedMountain.value);
    }
    
    const changeCourse = () => {
        console.log(selectedCourse.value);
    }



    const startClimb = async () => {
        const res = await axios.post(`http://localhost:8000/main-client/climb-history/climbing`,
                    {
                        'cumId' : userStore.userId,
                        "frtrlId" : selectedCourse.value.frtrlId,     
                        "poiId" : selectedCourse.value.poiId,         
                        "stateCode" : "N"
                    }
                    ,{headers: {Authorization: `Bearer ${userStore.token}`} 
                })
        console.log('등산 시작결과:', res.data);

        // 정상으로 아직 미완료 등산 목록에 추가
        if(res.data.httpStatus === 200) {
            notCompleteClimbing.value.push({
                 frtrlId: selectedCourse.value.frtrlId, poiId : selectedCourse.value.poiId
            })

            console.log('등산 정상 시작 완료 후:', notCompleteClimbing.value);
            buttonflag.value = false;
        } else {
            //등산 시작 실패 에러메시지 모달창 출력
            openModal(res.data.message, '등산 시작 버튼');
        }
    }

    const endClimb = async() => {
        const res = await axios.put(`http://localhost:8000/main-client/climb-history/climbing`,
                    {
                        'cumId' : userStore.userId,
                        "frtrlId" : selectedCourse.value.frtrlId,     
                        "poiId" : selectedCourse.value.poiId,         
                        "stateCode" : "N"
                    }
                    ,{headers: {Authorization: `Bearer ${userStore.token}`} 
                })

        console.log('등산 완료결과:', res.data);
        
        if(res.data.httpStatus === 200) {
            
            // 등산 완료 후 미완료 리스트에서 제거
            const index = notCompleteClimbing.value.findIndex(x => x.poiId == res.data.result.member.poiId);
            if(index !== -1){
                notCompleteClimbing.value.splice(index,1);
            }

            replaceData();
            console.log('등반 정상 완료 후:', notCompleteClimbing.value);
            buttonflag.value = true;

        } else {
            openModal(res.data.message, '등산 완료 버튼');
        }
    }


    // 등산 완료후 그래프, 회원 정보 갱신
    const replaceData = async() => {
        
            const tasks = [];
            tasks.push(axios.get(`http://localhost:8000/main-client/stamp/corse-stamp/${userStore.userId}`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            tasks.push(axios.get(`http://localhost:8000/member-client/member/member-info/${userStore.userId}`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            tasks.push(axios.get(`http://localhost:8000/main-client/climb-history/monthly-record/${userStore.userId}`,{headers: {Authorization: `Bearer ${userStore.token}`} }));
            
            const resAll = await Promise.all([...tasks]);


            console.log('hasStamp:', resAll[0].data);
            console.log('memberInfo:', resAll[1].data);
            console.log('monthlyRecord:', resAll[2].data);

            hasStamp.value      = resAll[0].data;
            userInfo.value      = resAll[1].data;
            monthlyRecord.value = resAll[2].data;

            console.log('월별 등산기록 회원아이디:',monthlyRecord.value.cumId);
            console.log('월별 등산기록 xAxis:',monthlyRecord.value.xaxis);
            console.log('월별 등산기록 yAxis:',monthlyRecord.value.yaxis);

            // 도넛 차트에 들어갈 데이터
            stamps.value = [] ;
            stamps.value.push({name: '보유', value:hasStamp.value.length });
            stamps.value.push({name: '미보유', value: courses.value.length - hasStamp.value.length});
    }

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
  background:rgba(3, 232, 253, 0.423);
  border:1px solid #d1d5db;
  color:#111827;
}

.btn:disabled{
    background-color: #9ca3af;
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