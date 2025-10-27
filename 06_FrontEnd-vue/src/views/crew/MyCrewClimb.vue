    <template>
    <div class="my-climb-record-container">
        <h2 class="page-title">내 크루 등산 목록</h2>

        <!-- 일정 카드 목록 -->
        <div class="climb-list">
        <div
            v-for="(item, index) in myClimbData"
            :key="index"
            class="climb-card"
        >

            <!-- 일정 정보 -->
            <div class="climb-info">
            등산 시작일: {{ item.crewClimbRecord?.crewClimbStartDate }}<br />
            </div>

            <!-- 산 이름 -->
            <div class="mountain-name">{{ item.mountain?.frtrlNm }}</div>

            <!-- 상태 + 버튼 -->
            <div class="status-box">
            <span
                class="status"
                :class="{
                success: item.crewClimbHistoryIsSucceed === 'Y',
                fail: item.crewClimbHistoryIsSucceed === 'N',
                planned: item.crewClimbHistoryIsSucceed === '\u0000'
                }">
                등산 {{ isSucceed(item.crewClimbHistoryIsSucceed) }}
            </span>
            <button @click="climbInfo(item.crewClimbRecord.climbId,item.crewClimbHistoryIsSucceed)" class="detail-btn">상세 보기</button>
            </div>
        </div>
        </div>
    </div>
    </template>

    <script setup>
    import axios from 'axios'
    import { onMounted, ref } from 'vue'
    import { useRouter } from 'vue-router';
    const router = useRouter();

    const myClimbData = ref({
        id:0,
        crewClimbHistoryIsSucceed:'',
        crewMemberId:'',
        crewClimbRecord:{},
        mountain:{}
    })
    const climbInfo = (climbId,state)=>{
        console.log(climbId,state);
        if(state === 'Y' || state === 'N'){
            router.push(`/crew/my-climb-board/${climbId}/${state}`);
        }
        else{
            router.push(`/crew/climb-board/${climbId}`);
        }
    }
    const isSucceed = (flag)=>{
        if(flag==='Y')return "성공";
        else if(flag==='N')return "실패";
        else return "예정";
    }

    onMounted(async () => {
    const response = await axios.get(
        'http://localhost:8000/main-client/crew-climb-board/climb-board-mylist/25',
        {
            headers:{"Authorization":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nOTk5OTk5QGV4YW1wbGUuY29tIiwiYXV0aCI6WyJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwleyCsOyLoOuguSIsImlkIjoyMTcsImJpcnRoIjoiMTk4Ni0wMy0wOCIsIm1lbVN0c0lkIjoxLCJleHAiOjE3NjE1Njc3OTl9.hrkEktZ_X20kC-eju4Yx63eItDilxt5-2Fi0AjtGx6Xlryc9SQ8rYmwEFJ3Neiuj8GgLwHynCdPokZXlt1IZAA"}
        }
    )
    myClimbData.value = response.data
    console.log(myClimbData.value)
    })
    </script>

    <style scoped>
    .my-climb-record-container {
    width: 900px;
    margin: 40px auto;
    background: #fff;
    }

    .page-title {
    font-size: 20px;
    font-weight: 700;
    margin-bottom: 20px;
    }

    .climb-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
    }

    .climb-card {
    display: grid;
    grid-template-columns: 2fr 2fr 1fr 1fr;
    align-items: center;
    background: #fafafa;
    padding: 15px;
    border: 1px solid #ddd;
    border-radius: 10px;
    gap: 10px;
    }

    .climb-title {
    font-weight: 700;
    font-size: 16px;
    }

    .climb-info {
    font-size: 13px;
    color: #555;
    line-height: 1.4;
    }

    .mountain-name {
    font-weight: 600;
    text-align: center;
    }

    .status-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 5px;
    }

    .status {
    font-size: 13px;
    padding: 4px 8px;
    border-radius: 6px;
    font-weight: 600;
    }

    .status.success {
    background: #d9f8e2;
    color: #1a7f37;
    }

    .status.planned {
    background: #e7f0ff;
    color: #2f6fed;
    }

    .status.fail {
    background: #ffe0e0;
    color: #d32f2f;
    }

    .detail-btn {
    background: #fff;
    border: 1px solid #ccc;
    border-radius: 6px;
    padding: 4px 10px;
    font-size: 13px;
    cursor: pointer;
    transition: 0.2s;
    }

    .detail-btn:hover {
    background: #f0f0f0;
    }
    </style>
