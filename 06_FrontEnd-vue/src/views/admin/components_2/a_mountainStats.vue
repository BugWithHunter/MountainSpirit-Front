<template>
  <div class="mountain-stats">
    <div class="charts-row">
      <div class="chart-box">
        <h3>지역별 산 개수</h3>
        <canvas id="regionChart" ></canvas>
      </div>
      <div class="chart-box">
        <h3>인기 산 TOP 5</h3>
        <canvas id="topMountainChart" ></canvas>
      </div>
      <div class="chart-box">
        <h3>고도 구간별 등반 수</h3>
        <canvas id="altitudeChart" ></canvas>
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
    const res = await fetch("http://localhost:8000/main-client/admin/mountainstats", {
      headers: { Authorization: token, "Content-Type": "application/json" },
    });
    const data = await res.json();
    renderRegionChart(data.mountainAllNumber);
    renderTopMountainChart(data.mountainTop);
    renderAltitudeChart(data.climbAltitude);
  } catch (err) {
    console.error(err);
  }
};

const baseOptions = {
  responsive: false,
  maintainAspectRatio: false,
  animation: {
    duration: 1200,
    easing: "easeOutQuart",
  },
  plugins: { legend: { position: "bottom" } },
};

const renderRegionChart = (regionData) => {
  new Chart(document.getElementById("regionChart"), {
    type: "bar",
    data: {
      labels: regionData.map((r) => r.regionName),
      datasets: [
        {
          label: "산 개수",
          data: regionData.map((r) => r.mountainCount),
          backgroundColor: "rgba(54,162,235,0.6)",
        },
      ],
    },
    options: baseOptions,
  });
};

const renderTopMountainChart = (topData) => {
  new Chart(document.getElementById("topMountainChart"), {
    type: "bar",
    data: {
      labels: topData.map((m) => m.mountainName),
      datasets: [
        {
          label: "등반 횟수",
          data: topData.map((m) => m.climbCount),
          backgroundColor: "rgba(255,99,132,0.6)",
        },
      ],
    },
    options: {
      ...baseOptions,
      indexAxis: "y",
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          beginAtZero: true, // 최소값 0부터
          ticks: { stepSize: 1 }, // 등반횟수가 적을 때 간격 고정
        },
        y: { ticks: { autoSkip: false } },
      },
    },
  });
};

const renderAltitudeChart = (altData) => {
  new Chart(document.getElementById("altitudeChart"), {
    type: "doughnut",
    data: {
      labels: altData.map((a) => a.heightRange),
      datasets: [
        {
          data: altData.map((a) => a.climbCount),
          backgroundColor: [
            "rgba(75,192,192,0.6)",
            "rgba(255,205,86,0.6)",
            "rgba(153,102,255,0.6)",
          ],
        },
      ],
    },
    options: baseOptions,
  });
};

onMounted(fetchData);
</script>

<style scoped>
.mountain-stats {
  padding: 20px;
  text-align: center;
}

.charts-row {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.chart-box {
  width: 500px;
  height: 400px;
  background: #fff;
  position: relative;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.chart-box h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 10px;
  color: #2c3e50;
}

canvas {
  width: 100% !important;   /* ✅ 부모 영역에 맞춤 */
  height: 350px !important; /* ✅ 원하는 세로 크기 */
  display: block;
  margin: 0 auto;
}
</style>
