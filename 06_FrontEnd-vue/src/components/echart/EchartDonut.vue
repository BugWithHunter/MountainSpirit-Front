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
                tooltip: {
                    trigger: 'item', 
                    show: false
                },
                legend: {
                    top: '5%',
                    left: 'center',
                    show: false
                },
                textStyle: { // 툴팁 안의 텍스트 크기/색 등 커스텀
                fontSize: 20,
                color: '#222'
                },
                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: true,                   // 기본적으로 라벨 표시
                            position: 'outside',          // 조각 바깥쪽에 표시 (옆에 보이게)
                            // {b}=이름, {c}=값, {d}=퍼센트
                            // 필요에 따라 "{b}: {c}" 또는 "{b}\n{c} ({d}%)" 등으로 변경 가능
                            formatter: '{b}\n{c}',
                            fontSize: 18,                 // 기본 라벨 글씨 크기
                            color: '#333',
                            // (선택) 라벨을 더 예쁘게 하고 싶으면 rich 텍스트 사용 가능
                            // formatter: '{name|{b}} {val|{c}}',
                            // rich: { name:{fontSize:16,color:'#555'}, val:{fontSize:16,fontWeight:'bold',color:'#111'} }
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold',
                                // 예: '{b}\n{c} ({d}%)'
                                formatter: '{b}\n{c}',
                                fontSize: 24,               // 🔥 hover 시 크게
                                fontWeight: 'bold',
                                color: '#111'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            { value: props.hsaStamp.length, name: '흭득', itemStyle: { color: '#ABF200' } },
                            { value: remainStamp, name: '미흭득', itemStyle: { color: '#BDBDBD' } }
                        ]
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