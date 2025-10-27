<template>
    <div class="member-stats">

      <div class="stats-row">
        <!-- 회원수 + 성별비율 -->
        <div class="stats-column">
          <div class="stat-card">
            <h2>총 회원 수 &nbsp; {{ totalMembers }}명</h2>
          </div>
  
          <div class="chart-card">
            <h3>성별 비율</h3>
            <canvas id="genderChart"></canvas>
          </div>
        </div>
  
        <!-- 연령 분포 -->
        <div class="chart-card">
          <h3>연령 분포</h3>
          <canvas id="ageChart"></canvas>
        </div>
  
        <!-- 월별 신규 회원 -->
        <div class="chart-card">
          <h3>월별 신규 회원</h3>
          <canvas id="monthlyChart"></canvas>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import { Chart, registerables } from 'chart.js';
  Chart.register(...registerables);
  
  const totalMembers = ref(0);
  const stats = ref({});
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  // 데이터 불러오기
  const fetchStats = async () => {
    try {
      const res = await fetch('http://localhost:8000/main-client/admin/memberinfostats', {
        headers: {
          'Authorization': token, // 이미 Bearer 포함
          'Content-Type': 'application/json',
        },
      });
  
      if (!res.ok) {
        console.error(`❌ HTTP Error: ${res.status}`);
        const text = await res.text();
        console.warn('서버 응답:', text);
        return;
      }
  
      const data = await res.json();
      if (!data || !data.genderRatio) {
        console.error('⚠️ 통계 데이터 구조가 올바르지 않습니다:', data);
        return;
      }
  
      stats.value = data;
      totalMembers.value = data.totalMembers;
      renderCharts(data);
    } catch (err) {
      console.error('통계 데이터 불러오기 실패:', err);
    }
  };
  
  // Chart.js 렌더링
  const renderCharts = (data) => {
    // 성별 비율
    new Chart(document.getElementById('genderChart'), {
      type: 'doughnut',
      data: {
        labels: data.genderRatio.map((g) => (g.gender === 'F' ? '여성' : '남성')),
        datasets: [
          {
            data: data.genderRatio.map((g) => g.count),
            backgroundColor: ['#ff99cc', '#66b3ff'],
          },
        ],
      },
      options: {
        plugins: {
          legend: { position: 'bottom' },
        },
      },
    });
  
    // 연령 분포
    new Chart(document.getElementById('ageChart'), {
      type: 'bar',
      data: {
        labels: data.ageDistribution.map((a) => a.ageGroup),
        datasets: [
          {
            label: '회원 수',
            data: data.ageDistribution.map((a) => a.count),
            backgroundColor: '#82caff',
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: { beginAtZero: true },
        },
        plugins: {
          legend: { display: false },
        },
      },
    });
  
    // 월별 신규 회원
    new Chart(document.getElementById('monthlyChart'), {
      type: 'line',
      data: {
        labels: data.monthlyNewMembers.map(
          (m) => `${m.year}.${String(m.month).padStart(2, '0')}`
        ),
        datasets: [
          {
            label: '신규 회원 수',
            data: data.monthlyNewMembers.map((m) => m.newMembers),
            borderColor: '#ff6384',
            backgroundColor: '#ffb3c6',
            fill: true,
            tension: 0.3,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: { beginAtZero: true },
        },
        plugins: {
          legend: { position: 'bottom' },
        },
      },
    });
  };
  
  onMounted(fetchStats);
  </script>
  
  <style scoped>
  .member-stats {
    padding: 24px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  
  .title {
    font-size: 1.8rem;
    font-weight: bold;
    margin-bottom: 24px;
  }
  
  /* 전체 가로 3분할 레이아웃 */
  .stats-row {
    display: flex;
    gap: 24px;
    width: 100%;
    justify-content: center;
    align-items: stretch; /* 세로 높이 동일 */
  }
  
  /* 각 영역 */
  .stats-column,
  .chart-card {
    flex: 1;
    background: white;
    border-radius: 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    padding: 15px;
    min-height: 420px;
    max-height: 600px;
    display: flex;
    flex-direction: column;
  }
  
  /* 첫 번째 영역 내부 구조 */
  .stats-column {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

  .chart-card {
    min-height: 100px;
  }
  
  
  /* 회원 수 카드 */
  .stat-card {
    background: linear-gradient(135deg, #dff6ff, #b8e8fc);
    max-height: 70px;
    border-radius: 12px;
    text-align: center;
  }
  
  .total {
    font-size: 2.5rem;
    font-weight: bold;
    color: #003366;
  }
  
  /* 그래프 카드 내에서 차트 영역을 고정 */
.chart-card canvas {
  flex-grow: 1;           /* 차트가 카드 내부 공간을 꽉 채움 */
  width: 100%;
  height: 300px !important; /* 고정 높이 (overflow 방지) */
  max-height: 320px;
  margin-top: 10px;
}
  /* 제목 */
  h3 {
    margin-bottom: 8px;
    font-weight: 600;
    font-size: 1.1rem;
  }
  
  </style>
  