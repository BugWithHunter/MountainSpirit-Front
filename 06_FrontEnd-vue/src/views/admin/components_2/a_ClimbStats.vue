<template>
    <div class="climb-stats">
      <div class="charts-row">
        <div class="chart-box">
          <h3>스탬프 · 즐겨찾기</h3>
          <canvas id="stampChart"></canvas>
        </div>
        <div class="chart-box">
          <h3>등반 시작</h3>
          <canvas id="startChart"></canvas>
        </div>
        <div class="chart-box">
          <h3>등반 종료</h3>
          <canvas id="endChart"></canvas>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { onMounted } from "vue";
  import { Chart, registerables } from "chart.js";
  Chart.register(...registerables);
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  const fetchData = async () => {
    try {
      const res = await fetch("http://localhost:8000/main-client/admin/climbstats", {
        headers: { Authorization: token, "Content-Type": "application/json" },
      });
      const data = await res.json();
      renderStampChart(data.totalStampBookmark[0]);
      renderStartChart(data.climbStartState);
      renderEndChart(data.climbEndState);
    } catch (err) {
      console.error(err);
    }
  };
  
  const chartOptions = { responsive: false, maintainAspectRatio: false };
  
  const renderStampChart = (s) =>
    new Chart(document.getElementById("stampChart"), {
      type: "doughnut",
      data: {
        labels: ["산 스탬프", "코스 스탬프", "산 북마크", "코스 북마크"],
        datasets: [
          {
            data: [
              s.totalMountainStampCount,
              s.totalCourseStampCount,
              s.totalMountainBookmarkCount,
              s.totalCourseBookmarkCount,
            ],
            backgroundColor: [
              "rgba(75,192,192,0.6)",
              "rgba(153,102,255,0.6)",
              "rgba(255,205,86,0.6)",
              "rgba(255,99,132,0.6)",
            ],
          },
        ],
      },
      options: chartOptions,
    });
  
  const renderStartChart = (data) =>
    new Chart(document.getElementById("startChart"), {
      type: "bar",
      data: {
        labels: data.map((d) => d.timeRange),
        datasets: [
          {
            label: "시작 횟수",
            data: data.map((d) => d.climbCount),
            backgroundColor: "rgba(54,162,235,0.6)",
          },
        ],
      },
      options: chartOptions,
    });
  
  const renderEndChart = (data) =>
    new Chart(document.getElementById("endChart"), {
      type: "bar",
      data: {
        labels: data.map((d) => d.timeRange),
        datasets: [
          {
            label: "종료 횟수",
            data: data.map((d) => d.climbCount),
            backgroundColor: "rgba(255,159,64,0.6)",
          },
        ],
      },
      options: chartOptions,
    });
  
  onMounted(fetchData);
  </script>
  
  <style scoped>
.climb-stats {
  padding: 30px;
  text-align: center;
}

/* 가로 정렬 및 균등 간격 */
.charts-row {
  display: flex;
  justify-content: center;
  align-items: stretch;
  gap: 30px;
  flex-wrap: nowrap;
}

/* 카드 형태의 차트 박스 */
.chart-box {
  width: 800px;    
  height: 450px;     
  background: #fff;
  border-radius: 10px;
  padding: 40px 30px;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.15);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
}

/* 제목 스타일 */
.chart-box h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 25px;
  color: #2c3e50;
}

/* 차트 크기 확대 */
canvas {
  width: 100%;
  height: 400px; /* 그래프 영역확보 */
  display: block;
  margin: 0 auto;
}
</style>

  