<template>
            <article class="card chart">
  <div class="chart-wrap">
    <div class="chart-title">
      <slot name="title" ></slot>
    </div>

    <div ref="chartDiv" class="chart-box"></div>
  </div>
  </article>
</template>


<script setup>
    import { onMounted, ref, defineProps, watch, onBeforeUnmount, nextTick  } from 'vue';

    let myChart;
    let option;

    const props = defineProps({
        cumId : {type:Number},
        xAxis : {type:Array, default: () => []},
        yAxis : {type:Array, default: () => []},
    });
    const chartDiv = ref(null);
    
    function buildOption(cumId, xAxis, yAxis) {
        return {
            tooltip: {
                trigger: 'axis', // 🔹 축 기준으로 호버 시 표시
                axisPointer: {
                    type: 'cross' // 🔹 십자선 커서
                },
                backgroundColor: 'rgba(50,50,50,0.8)',
                borderColor: '#333',
                textStyle: {
                    color: '#fff'
                },
                formatter: (params) => {
                    // params: 현재 마우스 위치의 데이터 정보 배열
                    const item = params[0]; // 단일 시리즈라 0번째 사용
                    return `
                        <b>${item.axisValue}</b><br/>
                        y: ${item.data}
                    `;
                }
            },
            xAxis: {
                type: 'category',
                data: xAxis
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: yAxis,
                    type: 'line',
                    color: '#1DDB16', //색상 코드
                    // smooth: true,     // 🔹 라인을 부드럽게
                    symbol: 'circle', // 🔹 데이터 점 표시
                    symbolSize: 6,    // 🔹 점 크기
                    lineStyle: {
                        width: 2
                    }
                }
            ]
        };
    }

    function ensureChart() {
        if (!myChart && chartDiv.value) {
            myChart = echarts.init(chartDiv.value, null, { renderer: 'canvas', useDirtyRect: false });
            window.addEventListener('resize', myChart.resize);
        }
    }

    function render() {
        ensureChart();
        if (!myChart) return;
        const opt = buildOption(props.cumId, props.xAxis,props.yAxis);
        myChart.clear();               //  이전 상태 깨끗이
        myChart.setOption(opt, true);  //  notMerge=true: 완전 교체
        myChart.resize();
    }

    onMounted(() => {
        nextTick(() => setTimeout(() => {
            render();                    //  처음에도 현재 props로 렌더
        }, 360));
    });

    watch(
        () => [props.cumId, props.xAxis,props.yAxis],
        () => render(),
        { deep: true, immediate: true }        //  처음 값과 깊은 변경 모두 반영
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