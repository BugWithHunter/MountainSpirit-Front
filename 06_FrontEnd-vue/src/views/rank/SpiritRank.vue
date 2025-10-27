

<template>
  <section class="dash" :class="{ full: !isCollapsed }">
    <!-- ⬅️ 좌측: 지도 + 하단 차트 카드 -->
    <div class="left-col">
      <!-- 지도 카드 -->
      <article class="card map-card" :class="{ collapsed: isCollapsed }">
        <div ref="mapContainer" class="map-box"></div>
      </article>

      <!-- 하단: ECharts 카드(껍데기) -->
      <article class="card chart-card">
        <div class="chart-placeholder">
          <!-- 나중에 ECharts 인스턴스 붙일 영역 -->
          <!-- <div class="chart-canvas" :disaple="isCollapsed"> -->
          <div v-if="isCollapsed" class="chart-canvas" >
                <EchartDonut 
                    :chartItems="chartItems" :radius="radius">
                </EchartDonut> 
        </div>
        
        </div>
      </article>
    </div>

    <!-- 우측: 상세 리스트(지도가 축소될 때 표시) -->
    <aside class="right-col" v-show="isCollapsed">
      <article class="card">
        <header class="card-header center">
          <h2 class="title-lg">{{ panelTitle }}</h2>
        </header>  

        <ul class="kv-list">
          <li v-for="(row, i) in selectedDetailRows" :key="i">
            <strong>{{ row.label }}</strong>
            <span>{{ row.value }}</span>
          </li>
        </ul>
      </article>
    </aside>
  </section>
</template>

<script setup>
import { onMounted, ref, reactive, nextTick } from 'vue'
import EchartDonut from '@/components/echart/EchartDonut.vue'

//--------------------------
let map;
// 각 마커를 순회하며 커스텀 오버레이 생성
const overlays = [] // 추후 전체 접근용
const chartEl = ref(null)              // (차트 붙일 엘리먼트 자리)
const isCollapsed = ref(false)         // 지도 축소 여부 (우측 패널 토글)
const panelTitle = ref('북한산')        // 오른쪽 카드 타이틀
const selectedDetailRows = ref([]);



const detailRows = ref([[
        { label: '아구몬', value: '산신령' ,count : 300},
        { label: '퍼피몬', value: '봉우리 마스터' ,count : 200},
        { label: '그레이몬', value: '봉우리 마스터' ,count : 180},
        { label: '쟈디몬', value: '봉우리 마스터' ,count : 100},
        { label: '엔젤몬', value: '봉우리 마스터' ,count : 100},
        { label: '가루몬', value: '봉우리 마스터' ,count : 100},
    ],
    [
        { label: '꼬부기', value: '산신령' ,count : 600},
        { label: '파이리', value: '봉우리 마스터' ,count : 550},
        { label: '거북왕', value: '봉우리 마스터' ,count : 530},
        { label: '리자몽', value: '봉우리 마스터' ,count : 350},
    ]])


// 도넛 차트 -------------
let chartItems = ref([]);
let radius = ref(['40%' ,'60%']);

//-------------



function collapseMap(mountain) {
    
    const newCenter = new kakao.maps.LatLng(mountain.lat, mountain.lng);

    if (!isCollapsed.value) {
        const desiredLevel = 8;
        isCollapsed.value = true;

        map.setLevel(desiredLevel, { anchor: newCenter, animate: { duration: 300 } });
        const newSize = sizeForLevel(desiredLevel)
        overlays.forEach(({ markerEl }) => {
            markerEl.style.width = `${newSize}px`
            markerEl.style.height = `${newSize}px`
    })

    } 
    
    // 센터로 이동
    nextTick(() => setTimeout(() => {
        map.relayout();
        map.panTo(newCenter);// 최종 중앙 확정
      }, 360));

}
//--------------------------


const mapContainer = ref(null)   // 지도 DOM 참조

//  여러 마커 좌표 (배열로 관리)
const markerData = [
  { lat: 37.69883, lng: 127.01547, img: '/khg3.png', mountainNm: '도봉산' , member : null},
  { lat: 37.658657, lng: 126.978056, img: '/khg4.jpg', mountainNm: '북한산' , member : detailRows.value[1] },
  { lat: 37.445044, lng: 126.964223, img: '/khg2.jpg', mountainNm: '관악산' , member : detailRows.value[0]},

  { lat: 37.752705, lng: 127.333923, img: '/khg.png', mountainNm: '축령산' , member : null},
  { lat: 36.361424, lng: 127.210292, img: '/khg2.jpg', mountainNm: '계룡산' , member : null},
  { lat: 35.388214, lng: 127.034392, img: '/khg2.jpg', mountainNm: '강천산', member : null },
  { lat: 36.649026, lng: 127.96407, img: '/khg2.jpg', mountainNm: '대야산', member : null }
]

//  줌 레벨별 크기 매핑 함수
function sizeForLevel(level) {
  const baseLevel = 8
  const baseSize = 40
  const step = 8
  const size = baseSize + (baseLevel - level) * step
  return Math.max(32, Math.min(size, 128))
}

//  마커용 DOM 엘리먼트 생성 함수
function createMarkerElement(initialSize, data) {
  const el = document.createElement('div')
  el.className = 'circle-marker'
  el.style.width = `${initialSize}px`
  el.style.height = `${initialSize}px`
  el.style.borderRadius = '50%'
  el.style.overflow = 'hidden'
  el.style.border = '3px solid #fff'
  el.style.boxShadow = '0 2px 8px rgba(0,0,0,.25)'
  el.style.transition = 'width .12s ease, height .12s ease, transform .15s ease'

  const img = document.createElement('img')
  img.src = data.img
  img.alt = data.name
  img.style.width = '100%'
  img.style.height = '100%'
  img.style.objectFit = 'cover'
  img.style.display = 'block'

  el.appendChild(img)

  //  hover 이벤트: 살짝 확대
  el.addEventListener('mouseenter', () => {
    el.style.transform = 'scale(1.75)'
  })
  el.addEventListener('mouseleave', () => {
    el.style.transform = 'scale(1.0)'
  })

  //  click 이벤트: 콘솔 + alert
  el.addEventListener('click', () => {
    selectedDetailRows.value = data.member;
    panelTitle.value = data.mountainNm;

    if( chartItems.value.length > 0)
        chartItems.value = [];

    console.log('selectedDetailRows 확인:', selectedDetailRows.value);

    if(selectedDetailRows.value !== null) {            
        console.log(selectedDetailRows.value);


    // stamps.push({name: '흭득', value : 0 });
    // stamps.push({name: '미흭득', value: 0});
    // radius.push('40%');
    // radius.push('65%');

        selectedDetailRows.value.forEach((data) => {
            chartItems.value.push({
                name: data.label,
                value: data.count
            })
        });

        console.log('chartItems 확인:',  chartItems);
    } 
  })

  return el
}

onMounted(() => {
  // 지도 옵션
  const center = new window.kakao.maps.LatLng(35.94699, 127.09598)
  map = new window.kakao.maps.Map(mapContainer.value, {
    center,
    level: 12
  })


  markerData.forEach(data => {
    const level = map.getLevel()
    const size = sizeForLevel(level)
    const markerEl = createMarkerElement(size, data)

    markerEl.addEventListener('click', (e) => {
     e.stopPropagation();       // 지도의 click과 중복 방지
     collapseMap(data);    // 오른쪽 패널 타이틀 업데이트 + 축소
   })

    const overlay = new window.kakao.maps.CustomOverlay({
      position: new window.kakao.maps.LatLng(data.lat, data.lng),
      content: markerEl,
      xAnchor: 0.5,
      yAnchor: 1.0,
      map
    })

    overlays.push({ overlay, markerEl })
  })

    // 줌 이벤트: 모든 마커 크기 동기 변경
    window.kakao.maps.event.addListener(map, 'zoom_changed', () => {
            const level = map.getLevel()
            const newSize = sizeForLevel(level)
            overlays.forEach(({ markerEl }) => {
            markerEl.style.width = `${newSize}px`
            markerEl.style.height = `${newSize}px`
        })
    })


})
</script>

<style>
/* ====== 레이아웃 ====== */
.dash{
  display: grid;
  grid-template-columns: 2fr 1.1fr;
  gap: 16px;
  padding: 0 16px 16px;   
  width: 100%;
  flex: 1 1 auto;         /* 메인에서 남는 공간을 모두 차지 */
  min-height: 0;          /* 내부 스크롤 시 수축 허용 */
}
.left-col{ 
    display:flex; 
    flex-direction:column; 
    gap:16px; 
}

.right-col{ 
    display:flex; 
    flex-direction:column; 
    gap:16px; 
}

/* ====== 카드 공통 ====== */
.card{
  background:#fff;
  border:1px solid #e5e7eb;
  border-radius:12px;
  box-shadow:0 1px 6px rgba(0,0,0,.06);
  padding:14px;
}
.card-header{
  display:flex; align-items:center; justify-content:space-between;
  margin-bottom:8px;
}
.card-header.center{ justify-content:center; }
.title-lg{ margin-bottom:15; font-size:40px; font-weight:800; }

/* ====== 버튼 ====== */
.btn{
  height:32px; padding:0 10px; border-radius:8px; border:0; cursor:pointer;
  background:#111827; color:#fff;
}
.btn.ghost{ background:#fff; color:#111827; border:1px solid #d1d5db; }

/* ====== 지도 카드 ====== */
.map-card{
  transition: height .32s ease, transform .32s ease, padding .32s ease;
  height: 420px;                /* 기본 높이 */
  overflow:hidden;
}
.map-card.collapsed{
  height: 240px;                /* 축소 높이 */
  transform: translateZ(0);     /* GPU 가속 힌트(부드러운 트랜지션) */
}
.map-box{
  width:100%; height:100%;
  border-radius:8px; overflow:hidden;
  transition: transform .32s ease; /* 지도 자체 스케일 애니메이션 */
}
.map-card.collapsed .map-box{
  transform: scale(.96);           /* 축소 시 살짝 줄어든 느낌 */
}
.hint{ color:#6b7280; font-size:12px; }

/* ====== 차트 카드(껍데기) ====== */
.chart-card{ min-height: 280px; display:flex; flex-direction:column; }
.chart-placeholder{
  flex:1 1 auto; 
  display:flex; 
  align-items:center; 
  justify-content:center;
  background: #fafafa; 
  border:1px dashed #e5e7eb; 
  border-radius:10px;
}
.chart-canvas{ width:100%; height:100%; display:flex; align-items:center; justify-content:center; }
.muted{ color:#9ca3af; }

/* ====== 오른쪽 리스트 카드 ====== */
.kv-list{
  list-style:none; margin:0; padding:0;
  display:grid; grid-template-columns: 140px 1fr;
   row-gap:10px; column-gap:12px;
   
}
.kv-list li{ display:contents;  }
.kv-list strong{ 
    grid-column:1; 
    align-self:center; 
    color:#6b7280; 
    font-weight:700; 
    margin-top: 25px;
    font-size: 20px;
}
.kv-list span { 
    grid-column:2;
    align-self:center;
    color:#111827;
    font-size: 20px;
    justify-self: end;              /* 오른쪽 정렬(숫자/날짜 정렬에 좋음) */
}

/* ====== 원형 마커 예시 ====== */
.circle-marker{
  width:56px; height:56px; border-radius:50%; overflow:hidden;
  border:3px solid #fff; 
  box-shadow:0 2px 8px rgba(0,0,0,.25);
  transition: transform .15s ease;
}
.circle-marker img{
  display:block; width:100%; height:100%; object-fit:cover;
}
.circle-marker:hover{ transform: scale(1.08); }

/* === (추가) 기본 높이 보장 === */
html, body, #app { height: 100%; }

/* === (추가) 처음 상태: 지도만 화면 꽉 채우기 === */
.dash.full{
  grid-template-columns: 1fr;   /* 한 컬럼만 */
  gap: 0;
  padding: 0;                   /* 바깥 여백 제거 */
}

.dash.full .map-card{
  height: calc(90vh - 20px);               /* 뷰포트 꽉 채움 */
  border-radius: 0;
  border: 0;
  box-shadow: none;
}

.dash.full .map-box{
  border-radius: 0;
}



.dash.full .chart-card{ display: none; }/* 하단 차트 숨김 */

/* 축소 상태에서는 2열 레이아웃을 유지하면서 좌/우 모두 높이 꽉 채우기 */
.dash:not(.full) {
  grid-template-columns: 2fr 1.1fr;  /* 기존 비율 유지 */
  gap: 5px;
  padding: 16px;
}

/* 좌측 컬럼이 세로로 가득 차도록 */
.dash:not(.full) .left-col {
  height: calc(90vh - 32px); /* padding 16px*2 보정 */
  min-height: 0;              /* flex 아이템 수축 허용 */
  display: flex;
  flex-direction: column;
}

/* 지도 카드는 축소 상태에서 화면의 일정 비율만 차지 (원하면 값 조절) */
.dash:not(.full) .map-card {
  height: 50%;               /* ← 지도 비율 (예: 50% 화면 높이) */
  flex: 0 0 auto;
}

/* 차트 카드는 나머지 높이를 전부 채움 */
.dash:not(.full) .chart-card {
  flex: 1 1 auto;
  min-height: 0;              /* overflow 컨텐츠 있을 때 수축 가능 */
  height: 100%;
}

/* 지도 내부 박스는 카드 높이를 꽉 채우게 */
.dash:not(.full) .map-box { height: 100%; }

/* 우측 패널 카드도 화면 높이를 꽉 채움 + 스크롤 */
.dash:not(.full) .right-col > .card {
  height: calc(90vh - 32px);
  overflow: auto;
}

.chart-canvas > * { width:100%; height:100%; }

/* ====== 반응형(옵션) ====== */
@media (max-width: 1024px){
  .dash{ grid-template-columns: 1fr; }
  .right-col{ order:3; }
}


</style>
