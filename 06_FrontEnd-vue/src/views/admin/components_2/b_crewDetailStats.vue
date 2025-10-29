<template>
    <div class="crew-detail-stats">
      <!-- 1. 상단 요약 카드 -->
      <div class="stats-cards">
        <div class="card" v-for="(value, key) in filteredSummary" :key="key">
         <h3>{{ formatLabel(key) }}</h3>
         <p>{{ formatValue(key, value) }}</p>
      </div>
      </div>
  
      <!-- 2~4. 차트 영역 -->
      <div class="charts-row">
        <div class="chart-box">
          <h3>회원에 대한 크루 소속 비율</h3>
          <br/><br/>
          <div class="chart-canvas-wrapper">
            <canvas id="affiliationChart"></canvas>
          </div>
        </div>
  
        <div class="chart-box">
          <h3>크루 상태 비율</h3>
          <br/><br/>
          <div class="chart-canvas-wrapper">
            <canvas id="stateRateChart"></canvas>
          </div>
        </div>
  
        <div class="chart-box">
          <h3>크루등반 성공률</h3>
          <br/><br/>
          <div class="chart-canvas-wrapper">
            <canvas id="climbSuccessChart"></canvas>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from "vue";
  import { Chart, registerables } from "chart.js";
  Chart.register(...registerables);

  const filteredSummary = computed(() => {
  const copy = { ...stats.value.countSummary };
  delete copy.activeCrewMemberCount;
  return copy;
});

  const baseChartOptions = {
  responsive: false,               //  반응형 (필수)
  maintainAspectRatio: false,     //  컨테이너 높이에 맞춤
  animation: {
    duration: 1200,               //  1.2초 애니메이션
    easing: "easeOutQuart",       //  부드러운 등장
  },
  plugins: {
    legend: { position: "bottom" },
  },
};

  
  const stats = ref({
    countSummary: {},
    memberAffiliation: {},
    memberStateRates: {},
    climbSuccessRate: {},
  });
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  const fetchCrewDetailStats = async () => {
    try {
      const res = await fetch("http://localhost:8000/main-client/admin/crewdetailstats", {
        headers: { Authorization: token, "Content-Type": "application/json" },
      });
      const data = await res.json();
      stats.value = data;
  
      renderAffiliationChart(data.memberAffiliation);
      renderStateRateChart(data.memberStateRates);
      renderClimbSuccessChart(data.climbSuccessRate);
    } catch (err) {
      console.error("크루 상세 통계 불러오기 실패:", err);
    }
  };
  
  // 라벨 포맷
  const formatLabel = (key) => {
    const map = {
      totalCrewMemberCount: "전체 크루 구성원 수",
      activeCrewMemberCount: "활동 중 크루구성원 수",
      totalCrewClimbBoardCount: "등반 게시글 수",
      totalCrewApplyCount: "크루 신청 수",
      totalCrewMemberHistoryCount: "크루구성원 히스토리 수",
    };
    return map[key] || key;
  };

  const formatValue = (key, value) => {
    // 항목별 단위 지정
    if (key === "totalCrewMemberCount") return `${value} 명`;
   return `${value} 개`;
  };
  
  // 소속 비율
  const renderAffiliationChart = (data) => {
    new Chart(document.getElementById("affiliationChart"), {
      type: "doughnut",
      data: {
        labels: ["소속 회원", "미소속 회원"],
        datasets: [
          {
            data: [data.affiliatedRate, data.unaffiliatedRate],
            backgroundColor: ["rgba(54,162,235,0.6)", "rgba(255,99,132,0.6)"],
          },
        ],
      },
      options: baseChartOptions,
    });
  };
  
  // 가입/탈퇴/강퇴 비율
  const renderStateRateChart = (data) => {
    new Chart(document.getElementById("stateRateChart"), {
      type: "pie",
      data: {
        labels: ["가입", "탈퇴", "강퇴"],
        datasets: [
          {
            data: [data.joinedRate, data.quitedRate, data.bannedRate],
            backgroundColor: [
              "rgba(75,192,192,0.7)",
              "rgba(255,205,86,0.7)",
              "rgba(255,99,132,0.7)",
            ],
          },
        ],
      },
      options: baseChartOptions,
    });
  };
  
  //  등반 성공률
  const renderClimbSuccessChart = (data) => {
    new Chart(document.getElementById("climbSuccessChart"), {
      type: "doughnut",
      data: {
        labels: ["성공", "실패"],
        datasets: [
          {
            data: [data.successRate, 100 - data.successRate],
            backgroundColor: ["rgba(153,102,255,0.7)", "rgba(201,203,207,0.4)"],
            cutout: "70%",
          },
        ],
      },
      options: {
        ...baseChartOptions,
        plugins: {
          ...baseChartOptions.plugins,
          tooltip: {
            callbacks: {
              label: (ctx) =>
                ctx.label === "성공"
                  ? `성공률: ${data.successRate}% (${data.successCount}/${data.totalInstances})`
                  : `실패: ${100 - data.successRate}%`,
            },
          },
        },
      },
    });
  };
  
  onMounted(fetchCrewDetailStats);
  </script>
  
  <style scoped>
  .crew-detail-stats {
    padding: 30px;
    text-align: center;
  }
  
  /* 상단 카드 */
  .stats-cards {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    gap: 15px;
    margin-bottom: 40px;
  }
  
  .card {
    background: #fff;
    padding: 10px 25px;
    border-radius: 10px;
    width: 200px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  }
  
  .card h3 {
    font-size: 15px;
    color: #333;
  }
  
  .card p {
    font-size: 20px;
    color: #007bff;
    font-weight: bold;
    margin-top: 8px;
  }
  
  /* 차트 박스들을 가로 정렬 */
  .charts-row {
    display: flex;
    justify-content: center;
    flex-wrap: wrap; /* 화면 작으면 자동 줄바꿈 */
    gap: 20px;
  }
  
  .chart-box {
    flex: 1 1 30%; /* 최소 너비 30%로 반응형 */
    max-width: 400px;
    height: 480px;
    background: #fff;
    border-radius: 10px;
    padding: 25px;
    padding-bottom: 150px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    position: relative;
  }
  
  .chart-box h3 {
    font-size: 16px;
    margin-bottom: 10px;
    color: #444;
  }
  
  canvas {
    width: 100% !important;
    height: 280px !important;
  }
  </style>
  