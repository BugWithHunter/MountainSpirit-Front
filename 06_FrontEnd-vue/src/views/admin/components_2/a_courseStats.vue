<template>
    <div class="course-stats">
      <div class="charts-row">
        <div class="chart-box">
          <h3>코스 난이도별 분포</h3>
          <canvas id="difficultyChart"></canvas>
        </div>
        <div class="chart-box">
          <h3>월별 신규 코스 등록 현황</h3>
          <canvas id="monthChart"></canvas>
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
      const res = await fetch("http://localhost:8000/main-client/admin/coursestats", {
        headers: { Authorization: token, "Content-Type": "application/json" },
      });
      const data = await res.json();
      renderDifficultyChart(data.courseDifficultyState);
      renderMonthChart(data.courseRegistMonth);
    } catch (e) {
      console.error(e);
    }
  };
  
  const chartOptions = { responsive: false, maintainAspectRatio: false };
  
  const renderDifficultyChart = (data) => {
    new Chart(document.getElementById("difficultyChart"), {
      type: "bar",
      data: {
        labels: data.map((d) => `난이도 ${d.difficulty}`),
        datasets: [
          {
            label: "코스 수",
            data: data.map((d) => d.courseCount),
            backgroundColor: "rgba(75,192,192,0.6)",
          },
        ],
      },
      options: chartOptions,
    });
  };
  
  const renderMonthChart = (data) => {
    new Chart(document.getElementById("monthChart"), {
      type: "line",
      data: {
        labels: data.map((m) => m.month || "기타"),
        datasets: [
          {
            label: "신규 등록 코스 수",
            data: data.map((m) => m.newCourseCount),
            borderColor: "rgba(255,99,132,0.8)",
            backgroundColor: "rgba(255,99,132,0.2)",
            tension: 0.3,
          },
        ],
      },
      options: chartOptions,
    });
  };
  
  onMounted(fetchData);
  </script>
  
  <style scoped>
  .course-stats {
    padding: 30px;
    text-align: center;
  }
  
  /* 차트들을 가로로 정렬 */
  .charts-row {
    display: flex;
    justify-content: center;
    align-items: flex-start;
    gap: 30px;
    flex-wrap: nowrap;
  }
  
  .chart-box {
    height: 450px;
    background: #fff;
    position: relative;
    border-radius: 10px;
    padding: 50px 34px 50px;
    box-shadow: 0 3px 8px rgba(0, 0, 0, 0.15);
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  
  .chart-box h3 {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 20px;
    color: #2c3e50;
  }
  
  /* 차트 크기 크게 (비율 유지) */
  canvas {
    width: 100%;
    height: 380px;
    display: block;
    margin: 0 auto;
  }
  </style>
  

  