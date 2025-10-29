

<template>
  <section class="dash">
    <!-- ⬅️ 좌측: 지도 + 하단 차트 카드 -->
    <div class="left-col">
      <!-- 지도 카드 -->
      <article class="card map-card" :class="{ collapsed: isCollapsed }">
        <header class="card-header">
          <h3>지도</h3>
          <button class="btn ghost" v-if="isCollapsed" @click="expandMap">지도 크게</button>
        </header>
        <div ref="mapContainer" class="map-box"></div>
        <small class="hint">지도를 클릭하면 축소되며 상세가 열립니다.</small>
      </article>

      <!-- 하단: ECharts 카드(껍데기) -->
      <article class="card chart-card">
        <header class="card-header">
          <h3>월별 등산 기록</h3>
        </header>
        <div class="chart-placeholder">
          <!-- 나중에 ECharts 인스턴스 붙일 영역 -->
          <div class="chart-canvas" ref="chartEl">
            <span class="muted">여기에 ECharts 렌더링</span>
          </div>
        </div>
      </article>
    </div>

    <!-- ➡️ 우측: 상세 리스트(지도가 축소될 때 표시) -->
    <aside class="right-col" v-show="isCollapsed">
      <article class="card">
        <header class="card-header center">
          <h2 class="title-lg">{{ panelTitle }}</h2>
        </header>

        <ul class="kv-list">
          <li v-for="(row, i) in detailRows" :key="i">
            <strong>{{ row.label }}</strong>
            <span>{{ row.value }}</span>
          </li>
        </ul>
      </article>
    </aside>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'

//--------------------------
const chartEl = ref(null)              // (차트 붙일 엘리먼트 자리)
const isCollapsed = ref(false)         // 지도 축소 여부 (우측 패널 토글)
const panelTitle = ref('북한산')        // 오른쪽 카드 타이틀
const detailRows = ref([
  { label: '야구몬', value: '산신령' },
  { label: '퍼피몬', value: '봉우리 마스터' },
  { label: '그레이몬', value: '봉우리 마스터' },
  { label: '쟈디몬', value: '봉우리 마스터' },
  { label: '엔젤몬', value: '봉우리 마스터' },
  { label: '가루몬', value: '봉우리 마스터' },
])




//--------------------------


const mapContainer = ref(null)   // 지도 DOM 참조

// ✅ 여러 마커 좌표 (배열로 관리)
const markerData = [
  { lat: 37.69883, lng: 127.01547, img: '/khg3.png', name: '도봉산' },
  { lat: 37.658657, lng: 126.978056, img: '/khg4.jpg', name: '북한산' },
  { lat: 37.445044, lng: 126.964223, img: '/khg2.jpg', name: '관악산' },

  { lat: 37.752705, lng: 127.333923, img: '/khg.png', name: '축령산' },
  { lat: 36.361424, lng: 127.210292, img: '/khg2.jpg', name: '계룡산' },
  { lat: 35.388214, lng: 127.034392, img: '/khg2.jpg', name: '강천산' },
  { lat: 36.649026, lng: 127.96407, img: '/khg2.jpg', name: '대야산' }
]

// ✅ 줌 레벨별 크기 매핑 함수
function sizeForLevel(level) {
  const baseLevel = 10
  const baseSize = 50
  const step = 8
  const size = baseSize + (baseLevel - level) * step
  return Math.max(32, Math.min(size, 128))
}

// ✅ 마커용 DOM 엘리먼트 생성 함수
function createMarkerElement(initialSize, imgSrc, name) {
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
  img.src = imgSrc
  img.alt = name
  img.style.width = '100%'
  img.style.height = '100%'
  img.style.objectFit = 'cover'
  img.style.display = 'block'

  el.appendChild(img)

  // ✅ hover 이벤트: 살짝 확대
  el.addEventListener('mouseenter', () => {
    el.style.transform = 'scale(1.75)'
  })
  el.addEventListener('mouseleave', () => {
    el.style.transform = 'scale(1.0)'
  })

  // ✅ click 이벤트: 콘솔 + alert
  el.addEventListener('click', () => {
    console.log(`${name} 마커 클릭!`)
    
  })

  return el
}

onMounted(() => {
  // 지도 옵션
  const center = new window.kakao.maps.LatLng(37.54699, 127.09598)
  const map = new window.kakao.maps.Map(mapContainer.value, {
    center,
    level: 8
  })

  // ✅ 각 마커를 순회하며 커스텀 오버레이 생성
  const overlays = [] // 추후 전체 접근용

  markerData.forEach(data => {
    const level = map.getLevel()
    const size = sizeForLevel(level)
    const markerEl = createMarkerElement(size, data.img, data.name)

    const overlay = new window.kakao.maps.CustomOverlay({
      position: new window.kakao.maps.LatLng(data.lat, data.lng),
      content: markerEl,
      xAnchor: 0.5,
      yAnchor: 1.0,
      map
    })

    overlays.push({ overlay, markerEl })
  })

  // ✅ 줌 이벤트: 모든 마커 크기 동기 변경
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
  display:grid;
  grid-template-columns: 2fr 1.1fr;  /* 좌:우 비율 */
  gap:16px;
  padding:16px;
}
.left-col{ display:flex; flex-direction:column; gap:16px; }
.right-col{ display:flex; flex-direction:column; gap:16px; }

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
.title-lg{ margin:0; font-size:22px; font-weight:800; }

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
.map-box{ width:100%; height:100%; border-radius:8px; overflow:hidden; }
.hint{ color:#6b7280; font-size:12px; }

/* ====== 차트 카드(껍데기) ====== */
.chart-card{ min-height: 280px; display:flex; flex-direction:column; }
.chart-placeholder{
  flex:1; display:flex; align-items:center; justify-content:center;
  background: #fafafa; border:1px dashed #e5e7eb; border-radius:10px;
}
.chart-canvas{ width:100%; height:100%; display:flex; align-items:center; justify-content:center; }
.muted{ color:#9ca3af; }

/* ====== 오른쪽 리스트 카드 ====== */
.kv-list{
  list-style:none; margin:0; padding:0;
  display:grid; grid-template-columns: 140px 1fr; row-gap:10px; column-gap:12px;
}
.kv-list li{ display:contents; }
.kv-list strong{ grid-column:1; align-self:center; color:#6b7280; font-weight:700; }
.kv-list span{ grid-column:2; align-self:center; color:#111827; }

/* ====== 원형 마커 예시 ====== */
:global(.circle-marker){
  width:56px; height:56px; border-radius:50%; overflow:hidden;
  border:3px solid #fff; box-shadow:0 2px 8px rgba(0,0,0,.25);
  transition: transform .15s ease;
}
:global(.circle-marker img){
  display:block; width:100%; height:100%; object-fit:cover;
}
:global(.circle-marker:hover){ transform: scale(1.08); }

/* ====== 반응형(옵션) ====== */
@media (max-width: 1024px){
  .dash{ grid-template-columns: 1fr; }
  .right-col{ order:3; }
}
</style>
