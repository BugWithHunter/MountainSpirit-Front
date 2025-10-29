<template>
    <div class="member-stats">
      <!-- 충성도 / 로그인 비교 / 회원 상태 -->
      <div class="stats-row">
        <div class="chart-card">
          <canvas id="loyaltyChart"></canvas>
        </div>
  
        <div class="chart-card">
          <canvas id="activityChart"></canvas>
        </div>
  
        <div class="chart-card">
          <canvas id="statusChart"></canvas>
        </div>
      </div>
  
      <!-- 로그인 시간대 분포 -->
      <div class="stats-row">
        <div class="chart-card">
          <h2>시간대별 로그인 분포</h2>
          <canvas id="loginTimeChart"></canvas>
        </div>
      </div>
    </div>
  </template>
  
  
  <script setup>
  import { onMounted, ref } from 'vue';
  import { Chart, registerables } from 'chart.js';
  Chart.register(...registerables);
  
  const token = import.meta.env.VITE_TEMP_TOKEN;
  
  // 데이터 불러오기
  const fetchStats = async () => {
    try {
      const res = await fetch('http://localhost:8000/main-client/admin/memberloginstats', {
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json',
        },
      });
  
      if (!res.ok) {
        console.error(`HTTP Error: ${res.status}`);
        const text = await res.text();
        console.warn('서버 응답:', text);
        return;
      }
  
      const data = await res.json();
      if (!data.loginStats || !data.memberStatusRatio) {
        console.error('⚠️ 데이터 구조가 올바르지 않음:', data);
        return;
      }
  
      renderCharts(data);
    } catch (err) {
      console.error('통계 데이터 불러오기 실패:', err);
    }
  };
  
  // Chart.js 차트 렌더링
  const renderCharts = (data) => {
    const loginStats = data.loginStats[0];
  
    // 충성도 게이지 (반원 도넛)
new Chart(document.getElementById('loyaltyChart'), {
  type: 'doughnut',
  data: {
    labels: ['충성도', '남은 비율'],
    datasets: [
      {
        data: [loginStats.loyaltyRate, 100 - loginStats.loyaltyRate],
        backgroundColor: ['#4bc0c0', '#e0e0e0'],
        borderWidth: 0,
      },
    ],
  },
  options: {
    // 🔥 애니메이션 제대로 보이게: responsive를 false로 고정해줄게
    // (responsive: true는 mount 직후 리사이즈 한 번 더 그리면서 애니메이션을 날려버린다)
    responsive: false,
    maintainAspectRatio: false,

    circumference: 180,
    rotation: -90,
    cutout: '70%',

    animation: {
      duration: 1200,          // 애니메이션 시간
      easing: 'easeOutBounce', // 효과
    },

    plugins: {
      legend: { display: false },
      tooltip: { enabled: false },
      // title은 차트 바깥쪽이라 사실 중앙 텍스트랑은 별개지만, 살리고 싶으면 둬도 됨
      title: {
        display: true,
        text: `충성도 ${loginStats.loyaltyRate}%`,
        color: '#333',
        font: { size: 18, weight: 'bold' },
        padding: 20,
      },
    },
  },

  // ✅ 이건 options.plugins가 아니라 차트 최상위의 plugins 배열이어야 함
  plugins: [
    {
      id: 'centerText',
      beforeDraw(chart) {
        const { ctx, chartArea: { width, height } } = chart;
        ctx.save();
        ctx.font = 'bold 20px Pretendard';
        ctx.fillStyle = '#333';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        // 반원이라 살짝 아래쪽이 자연스럽다
        ctx.fillText(
          `충성도 ${loginStats.loyaltyRate}%`,
          width / 2,
          height / 1.1
        );
        ctx.restore();
      },
    },
  ],
});

  
    // 최근 7일 / 30일 로그인 비율 비교
    new Chart(document.getElementById('activityChart'), {
  type: 'bar',
  data: {
    labels: ['최근 7일', '최근 30일'],
    datasets: [
      {
        label: '로그인 회원 수',
        data: [loginStats.login7d, loginStats.login30d],
        backgroundColor: ['#36a2eb', '#ffcd56'],
      },
      {
        label: '로그인 비율 (%)',
        data: [loginStats.recent7Ratio, loginStats.recent30Ratio],
        backgroundColor: ['#4bc0c0', '#ff9f40'],
      },
    ],
  },
  options: {
    responsive: false,            // 🔄 맞춰주기
    maintainAspectRatio: false,
    animation: {
      duration: 1000,
      easing: 'easeOutQuart',
    },
    scales: {
      y: { beginAtZero: true },
    },
    plugins: {
      title: {
        display: true,
        text: '로그인 횟수 및 비율',
      },
      legend: {
        position: 'bottom',
      },
    },
  },
});

  
    // 로그인 시간대 분포 (라인 차트)
    new Chart(document.getElementById('loginTimeChart'), {
      type: 'line',
      data: {
        labels: data.loginTimeDistribution.map((t) => t.loginTimeRange),
        datasets: [
          {
            label: '로그인 횟수',
            data: data.loginTimeDistribution.map((t) => t.loginCount),
            borderColor: '#9966ff',
            backgroundColor: 'rgba(153, 102, 255, 0.2)',
            tension: 0.3,
            fill: true,
            pointRadius: 5,
            pointHoverRadius: 7,
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
  
    // 회원 상태 비율 (도넛)
    new Chart(document.getElementById('statusChart'), {
      type: 'doughnut',
      data: {
        labels: data.memberStatusRatio.map((s) => s.status),
        datasets: [
          {
            data: data.memberStatusRatio.map((s) => s.count),
            backgroundColor: ['#ff6384', '#4bc0c0', '#ffcd56', '#9966ff', '#36a2eb'],
          },
        ],
      },
      options: {
        plugins: {
          legend: { position: 'bottom' },
          title: {
            display: true,
            text: '회원 상태별 비율',
          },
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
  gap: 28px;
  align-items: center;
}

/* 한 줄에 3개의 카드 */
.stats-row {
  display: flex;
  justify-content: center;
  align-items: stretch;
  gap: 24px;
  width: 100%;
}

/* 각 카드 스타일 */
.chart-card {
  flex: 1;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 350px;
  transition: transform 0.2s ease;
}


/* 시간대별 차트는 넓게 */
.chart-card.wide {
  flex: 1 1 100%;
  min-height: 420px;
}

/* 제목 */
h3 {
  text-align: center;
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 12px;
  color: #333;
}

/* 차트 크기 고정 */
.chart-card canvas {
  flex-grow: 1;
  width: 100%;
  height: 360px !important;
  max-height: 320px;
  margin-top: 5px;
}



  </style>
  