<template>
    <div class="crew-stats">
      <!-- 상단 통계 카드 -->
      <div class="stats-cards">
        <div class="card">
          <h3>전체 크루 수</h3>
          <p>{{ stats.totalCrewCount }} 개</p>
        </div>
        <div class="card">
          <h3>이번 달 신규 크루</h3>
          <p>{{ stats.newCrewCount }} 개</p>
        </div>
        <div class="card">
          <h3>활동 중 크루</h3>
          <p>{{ stats.activeCrewCount }} 개</p>
        </div>
      </div>
  
      <!-- 월별 그래프 -->
      <div class="chart-box">
        <canvas id="crewMonthlyChart"></canvas>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { Chart, registerables } from "chart.js";
  Chart.register(...registerables);
  
  const stats = ref({
    totalCrewCount: 0,
    newCrewCount: 0,
    activeCrewCount: 0,
    monthlyCrewStats: []
  });
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  const fetchCrewStats = async () => {
    try {
      const res = await fetch("http://localhost:8000/main-client/admin/crewstats", {
        headers: { Authorization: token, "Content-Type": "application/json" },
      });
      const data = await res.json();
      stats.value = data;
      renderMonthlyChart(data.monthlyCrewStats);
    } catch (err) {
      console.error("크루 통계 불러오기 실패:", err);
    }
  };
  
  const renderMonthlyChart = (monthlyData) => {
    const ctx = document.getElementById("crewMonthlyChart");
    if (!ctx) return;
  
    // 최신월이 위로 오게 정렬되어 있으니 순서 반대로 표시
    const sorted = [...monthlyData].reverse();
  
    new Chart(ctx, {
      type: "bar",
      data: {
        labels: sorted.map((d) => d.month),
        datasets: [
          {
            label: "월별 생성 크루 수",
            data: sorted.map((d) => d.monthlyCrewCount),
            backgroundColor: "rgba(54,162,235,0.6)",
            borderColor: "rgba(54,162,235,1)",
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: false,
        maintainAspectRatio: false,
        animation: {
        duration: 1200, 
        easing: "easeOutQuart", 
    },
        scales: {
          y: {
            beginAtZero: true,
            ticks: { stepSize: 1 },
          },
        },
        plugins: {
          legend: { display: false },
      title: {
        display: true,
        text: "월별 크루 생성 추이",
        font: { size: 18 },
        padding: { top: 10, bottom: 20 },
      }
        },
      },
    });
  };
  
  onMounted(fetchCrewStats);
  </script>
  
  <style scoped>
  .crew-stats {
    padding: 30px;
    text-align: center;
  }
  
  .stats-cards {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-bottom: 40px;
  }
  
  .card {
    background: #fff;
    border-radius: 10px;
    padding: 20px 30px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    width: 200px;
  }
  
  .card h3 {
    font-size: 16px;
    color: #333;
  }
  
  .card p {
    font-size: 22px;
    font-weight: bold;
    color: #007bff;
    margin-top: 5px;
  }
  
  .chart-box {
    width: 1200px;
    height: 600px;
    margin: 0 auto;
    background: #fff;
    padding: 20px;
    padding-top: 120px;
    padding-bottom: 130px;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: center; 
    align-items: center;
   }
  
  .chart-box h3 {
    font-size: 18px;
    color: #444;
    margin-bottom: 20px;
  }

  canvas {
  width: 100% !important;
  height: 580px !important;
  display: block;
  margin: 0 auto;
}


  
  </style>
  