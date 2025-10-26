<template>
    <div class="member-rank-stats">
      <h2>회원 등급 통계</h2>
  
      <div class="charts-row">
        <!-- 1️⃣ 등급별 회원 수 -->
        <div class="chart-card">
          <h3>등급별 회원 수</h3>
          <canvas id="memberCountChart"></canvas>
        </div>
  
        <!-- 2️⃣ 등급별 평균 점수 -->
        <div class="chart-card">
          <h3>등급별 평균 점수</h3>
          <canvas id="avgScoreChart"></canvas>
        </div>
  
        <!-- 3️⃣ 등급별 평균 등반 횟수 -->
        <div class="chart-card">
          <h3>등급별 평균 등반 횟수</h3>
          <canvas id="avgClimbCntChart"></canvas>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import { Chart, registerables } from 'chart.js';
  Chart.register(...registerables);
  
  const rankStats = ref([]);
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  // ✅ 데이터 가져오기
  const fetchRankStats = async () => {
    try {
      const res = await fetch('http://localhost:8000/main-client/admin/memberrankstats', {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json',
        },
      });
  
      if (!res.ok) {
        console.error(`❌ HTTP Error: ${res.status}`);
        return;
      }
  
      const data = await res.json();
      rankStats.value = data;
      renderCharts(data);
    } catch (err) {
      console.error('등급 통계 불러오기 실패:', err);
    }
  };
  
  // ✅ 차트 렌더링 함수
  const renderCharts = (data) => {
    const labels = data.map((item) => item.levelRange);
  
    // 1️⃣ 등급별 회원 수
    new Chart(document.getElementById('memberCountChart'), {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: '회원 수',
            data: data.map((item) => item.memberCount),
            backgroundColor: '#7bc5f7',
          },
        ],
      },
      options: {
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } },
        responsive: true,
        maintainAspectRatio: false,
      },
    });
  
    // 2️⃣ 등급별 평균 점수
    new Chart(document.getElementById('avgScoreChart'), {
      type: 'line',
      data: {
        labels,
        datasets: [
          {
            label: '평균 점수',
            data: data.map((item) => item.avgScore),
            borderColor: '#f77b72',
            backgroundColor: '#ffb3b3',
            fill: true,
            tension: 0.3,
          },
        ],
      },
      options: {
        plugins: { legend: { position: 'bottom' } },
        scales: { y: { beginAtZero: true } },
        responsive: true,
        maintainAspectRatio: false,
      },
    });
  
    // 3️⃣ 등급별 평균 등반 횟수
    new Chart(document.getElementById('avgClimbCntChart'), {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: '평균 등반 횟수',
            data: data.map((item) => item.avgClimbCnt),
            backgroundColor: '#a2e19a',
          },
        ],
      },
      options: {
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true } },
        responsive: true,
        maintainAspectRatio: false,
      },
    });
  };
  
  onMounted(fetchRankStats);
  </script>
  
  <style scoped>
  .member-rank-stats {
    padding: 24px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  h2 {
    font-size: 1.8rem;
    font-weight: bold;
    margin-bottom: 24px;
    color: #003366;
  }
  
  .charts-row {
    display: flex;
    gap: 24px;
    width: 100%;
    justify-content: center;
    align-items: stretch;
  }
  
  .chart-card {
    flex: 1;
    background: white;
    border-radius: 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 15px;
    min-height: 420px;
    display: flex;
    flex-direction: column;
  }
  
  .chart-card h3 {
    margin-bottom: 8px;
    font-weight: 600;
    font-size: 1.1rem;
    text-align: center;
  }
  
  .chart-card canvas {
    flex-grow: 1;
    width: 100%;
    height: 320px !important;
  }
  </style>
  