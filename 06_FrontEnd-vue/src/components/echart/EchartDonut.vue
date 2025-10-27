<template>
    <article class="card chart">
        <!-- 차트 카드 래퍼 (relative로 기준점 제공) -->
        <div class="chart-wrap">
            <!-- 중앙 상단 타이틀: 부모에서 내려준 슬롯(title) -->
            <div class="chart-title">
                <slot name="title" ></slot>
            </div>
            <!-- 실제 차트가 들어갈 박스 -->
            <div ref="chartDiv" class="chart-box"></div>
        </div>
  </article>
</template>

<script setup>
 import { onMounted, onBeforeUnmount, ref, defineProps, watch, nextTick } from 'vue';

let myChart;
let option;
const props = defineProps({
  chartItems : {type:Array, default: () => []},
  radius     : {type:Array, default: () => ['48%','72%']},
});
const chartDiv = ref(null);

function ensureChart() {
  if (!myChart && chartDiv.value) {
    myChart = echarts.init(chartDiv.value, null, { renderer: 'canvas', useDirtyRect: false });
    window.addEventListener('resize', myChart.resize);
  }
}

function buildOption(items, radius) {
  const data = (items ?? []).map(d => ({ name: d.name, value: d.value }));
  const isEmpty = data.length === 0;
  return {
    tooltip: { trigger: 'item', show: false },
    legend: { show:false },
    series: [{
      type: 'pie',
      radius: Array.isArray(radius) ? radius : [radius],   // 반지름 정규화
      center: ['50%','52%'],
      left: 8, right: 8, top: 8, bottom: 8,
      avoidLabelOverlap: false,
      minShowLabelAngle: 1,
      itemStyle: { borderRadius: 10, borderColor: '#fff', borderWidth: 2 },
      label: { show: !isEmpty, position: 'outside', formatter: '{b}\n{c}', fontSize: 18, color: '#333' },
      labelLayout: { moveOverlap: 'shiftY', hideOverlap: false },
      emphasis: { label: { show: !isEmpty, formatter: '{b}\n{c}', fontSize: 24, fontWeight: 'bold', color: '#111' }, scale: false },
      labelLine: { show: !isEmpty, length: 40, length2: 30, lineStyle: { width: 3, color: '#666' } },
      silent: isEmpty,                    // 빈 상태면 상호작용 비활성화
      animation: !isEmpty,                // 빈 → 데이터 전환시 깔끔
      data: isEmpty ? [{ value: 1, name: 'empty', itemStyle: { color: '#ddd' } }] : data
    }]
  };
}

function render() {
  ensureChart();
  if (!myChart) return;
  const opt = buildOption(props.chartItems, props.radius);
  myChart.clear();               // ✅ 이전 상태 깨끗이
  myChart.setOption(opt, true);  // ✅ notMerge=true: 완전 교체
  myChart.resize();
}

onMounted(() => {
  nextTick(() => setTimeout(() => {
   render();                    // ✅ 처음에도 현재 props로 렌더
  }, 360));
});

watch(
  () => [props.chartItems, props.radius],
  () => render(),
  { deep: true, immediate: true }        // ✅ 처음 값과 깊은 변경 모두 반영
);

onBeforeUnmount(() => {
  if (myChart) {
    window.removeEventListener('resize', myChart.resize);
    myChart.dispose();
    myChart = null;
  }
});
</script>

<style scoped>
.chart-box{ width:100%; height:100%; min-height:240px; padding-top:28px; }
</style>


<style scoped>

    /* 차트 래퍼: 기준 포지션 */
    .chart-wrap {
        position: relative;         /* absolute 자식의 기준 */
        display: flex;
        flex-direction: column;
        height: 100%;
    }

    /* 중앙 상단 타이틀 (겹치지 않게 위쪽 고정) */
    .chart-title {
        position: absolute;         /* 부모(.chart-wrap)를 기준으로 */
        top: 6px;                   /* 위에서 6px */
        left: 50%;                  /* 가로 중앙 기준점 */
        transform: translateX(-50%);/* 정확히 중앙 정렬 */
        z-index: 2;                 /* 차트 위로 */
        pointer-events: none;       /* 타이틀이 마우스 이벤트 가로채지 않도록 */
    }

    /* 차트 박스: 타이틀 높이만큼 padding-top을 줘서 겹침 방지 */
    .chart-box {
        flex: 1 1 auto;
        width: 100%;
        height: 100%;
        min-height: 240px;          /* 필요 시 최소 높이 */
        padding-top: 28px;          /* 타이틀 높이만큼 여백 */
    }

    /* ④ 카드 공통 스타일 (선택) */
    .card {
        background: #fff;
        border: 1px solid #e5e7eb;
        border-radius: 10px;
        padding: 8px 12px;
        box-shadow: 0 1px 6px rgba(0,0,0,.06);
    }

    .card.chart {
        flex: 1 1 0;              /* 위/아래 동일 높이 */
        display: flex;
        flex-direction: column;
    }
</style>