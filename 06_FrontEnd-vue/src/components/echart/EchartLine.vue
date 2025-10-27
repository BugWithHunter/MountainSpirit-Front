<template>
            <article class="card chart">
  <!-- ✅ 차트 카드 래퍼 (relative로 기준점 제공) -->
  <div class="chart-wrap">
    <!-- ✅ 중앙 상단 타이틀: 부모에서 내려준 슬롯(title) -->
    <div class="chart-title">
      <slot name="title" ></slot>
    </div>

    <!-- ✅ 실제 차트가 들어갈 박스 -->
    <div ref="chartDiv" class="chart-box"></div>
  </div>
  </article>
</template>


<script setup>
    import { onMounted, ref, defineProps, watch} from 'vue';

    const props = defineProps({
        hsaStamp: { type: Array },
        totalStamp: { type: Array }
    })

    const chartDiv = ref(null);
    const remainStamp = props.totalStamp.length - props.hsaStamp.length;
    
    onMounted(() => {
            // 차트를 그릴 div 테그 선택
            var myChart = echarts.init(chartDiv.value, null, {
                renderer: 'canvas',
                useDirtyRect: false
            });
            var app = {};
            var option;

             option = {
            xAxis: {
                type: 'category',
                data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    data: [10, 5, 3, 7, 0, 1, 2, 15, 2, 3, 4, 1,],
                    type: 'line',
                    color: '#1DDB16' //색상 코드
                }
            ]
        };

            if (option && typeof option === 'object') {
                myChart.setOption(option);
            }

            window.addEventListener('resize', myChart.resize);
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