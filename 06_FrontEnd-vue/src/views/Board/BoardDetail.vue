<template>
    <div class="board-detail">
    <h1>자유 게시글</h1>
    <div class="board-detail__header">
        <table>
            <thead>
                <tr class="board-detail__title">
                    <th colspan="3">{{ post.title }}</th>
                </tr>
            </thead>
            <tbody>
                <tr class="board-detail__meta">
                    <td>글번호 {{ post.id }}</td>
                    <td>작성일 {{ post.createDate?.slice(0, 10) }}</td>
                    <td>작성자번호 {{ post.cumId }}</td>                    
                </tr>
            </tbody>
        </table>
    <!-- <h2 class="board-detail__title">{{ post.title }}</h2>
    <div class="board-detail__meta">
        <span>글번호 {{ post.id }}</span>
        <span>작성일 {{ post.createDate?.slice(0, 10) }}</span>
        <span>작성자번호 {{ post.cumId }}</span> -->
        <div class="board-detail__report-dropdown">
        <button @click="toggleReportMenu" class="report-btn">
            신고
            <span :class="{'arrow-up': showReportMenu, 'arrow-down': !showReportMenu}"></span>
        </button>
        <div v-if="showReportMenu" class="report-menu">
            <ul>
            <li v-for="type in reportTypes" :key="type" @click="reportType(type)">
                {{ type }}
            </li>
            </ul>
        </div>
        </div>
    </div>
    <!-- </div> -->
    <div class="board-detail__content">
    <p>{{ post.content }}</p>
    <div v-if="post.thumbnailDTOList && post.thumbnailDTOList.length > 0" class="thumbnail-list">
        <img
        v-for="thumb in post.thumbnailDTOList"
        :key="thumb.thumbnail"
        :src="`/thumbnails/${thumb.thumbnail}`"
        alt="썸네일"
        class="thumbnail-img"
        />
    </div>
    </div>
    <div class="board-detail__footer">
    <button class="icon-btn" @click="like">
        <img src="./asset/fullheart.png" alt="좋아요" style="width:20px; height:20px;">
    </button>
    <button class="icon-btn" @click="comment">
        <img src="./asset/comment.png" alt="댓글" style="width:20px; height:20px;">
    </button>
    </div>
    <!-- (수정/삭제 버튼 구현 예시, 작성자만 보임) -->
    <div v-if="Number(currentUserId) === post.cumId" class="author-actions">
    <button @click="editPost">
        <img src="./asset/edit.png" alt="수정" style="width:20px; height:20px;">
    </button>
    <button @click="deletePost">
        <img src="./asset/delete.png" alt="삭제" style="width:20px; height:20px;">
    </button>
    </div>
    </div>
</template>


<script setup>
    import { ref, onMounted } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import axios from 'axios'

    const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrYW5nMTIxMzFAZXhhbXBsZS5jb20iLCJhdXRoIjpbIlJPTEVfQURNSU4iLCJST0xFX01FTUJFUiJdLCJ1c2VybmFtZSI6IuqwlTEyMTQiLCJpZCI6MjE0LCJiaXJ0aCI6IjE5ODYtMDMtMDgiLCJtZW1TdHNJZCI6MSwiZXhwIjoxNzYxNTM3ODE0fQ.ke7AS_JDlbghNlbuog_HSK45jYwMccDDULPe3tAaOnkkpoHvJ5DSRyIuWndlVPik8umeTHsBQVzNyk8k7Sb2eg";


    const route = useRoute();
    const router = useRouter()
    const postId = route.params.postId;
    const post = ref({});

    const showReportMenu = ref(false);
    const reportTypes = [
        '성희롱', '사기', '협박', '욕설', '도배글', '혐오콘텐츠'
    ]

    function toggleReportMenu() {
        showReportMenu.value = !showReportMenu.value
    }
    function reportType(type) {

        // 신고 상세 페이지 컴포넌트로 이동
        router.push({ name: 'reportForm', params: { postId, type } })
    }

onMounted(async () => {
    try {
        const response = await axios.get(
        `http://localhost:8000/main-client/boards/list/${route.params.postId}/select`,
        {
            headers: {
            Authorization: `Bearer ${token}`,
            },
        }
        )
        post.value = response.data[0];
        console.log(post.value);
    } catch (error) {
        console.error('게시글 상세 조회 실패:', error)
    }
})


    const currentUserId = ref(null);

    onMounted(() => {
    currentUserId.value = getCurrentUserId();
    console.log("현재 로그인한 사용자 ID:", currentUserId.value);
    });

    function getCurrentUserId() {
    if (!token) return null;
    try {
        const base64Payload = token.split('.')[1];
        const payload = JSON.parse(atob(base64Payload));
        return payload.id;
    } catch (e) {
        console.error("토큰 파싱 실패:", e);
        return null;
    }
    }
</script>

<style scoped>
.board-detail {
    max-width: 1200px;
    margin-left: auto;
    margin-right: auto;
}
table {
    width: 1200px;
    border-collapse: collapse;
    margin-left: auto;
    margin-right: auto;
}
th, td {
    padding: 12px;
    border-bottom: 1px solid #ddd;
    text-align: left;
}
thead {
    font-size: 25px;
    width: 1200px;
    height: 80px;
    border-top: 2px solid #A1A1A1;
    border-bottom: 1px solid #A1A1A1;
    background: #E4E4E4;
}
tbody {
    font-size: 18px;
    height: 40px;
    color: #888;
    padding: 6px 5px 4px 0;
    border: none;
    text-align: left;
}
h1 {
    text-align: left;
    margin: 32px 0 24px 0;
    margin-left: auto;
    margin-right: auto;
}
div {
    margin: 12px 0;
}

.board-detail__report-dropdown {
  display: inline-block;
  vertical-align: middle;
  margin-left: 10px;
  position: relative;
}

.report-btn {
    display: flex;
  justify-content: flex-end;
  background: none;
  border: none;
  color: #888;
  font-size: 14px;
  cursor: pointer;
  padding: 4px 10px;
  margin-left: auto;
  margin-right: auto;
}

.report-menu {
  position: absolute;
  top: 28px;
  left: 0;
  background: #fff;
  border: 1px solid #ececec;
  border-radius: 8px;
  min-width: 110px;
  z-index: 99;
  box-shadow: 0 3px 12px rgba(0,0,0,0.08);
  padding: 4px 0;
}
.report-menu ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
.report-menu li {
  padding: 9px 17px;
  font-size: 14px;
  color: #232323;
  cursor: pointer;
}
.report-menu li:hover {
  background: #f1f1f8;
}

.board-detail__content {
  max-width: 1200px;
  margin: 0 auto 0 auto;
  padding: 20px 18px 28px 18px;
  font-size: 20px;
  line-height: 1.65;
  border-bottom: 1px solid #dcdcdc;
  border-radius: 0 0 5px 5px;
  margin-left: auto;
  margin-right: auto;
}

.thumbnail-list {
  margin-top: 16px;
}
.thumbnail-img {
  max-width: 160px;
  border-radius: 7px;
  border: 1px solid #ececec;
  margin-right: 8px;
}
.board-detail__footer {
  max-width: 1200px;
  margin: 0 auto 0 auto;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px 18px 0 18px;
  background: white;
  margin-left: auto;
  margin-right: auto;
}

.icon-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0 6px;
  transition: opacity 0.14s;
  opacity: 0.80;
}
.icon-btn:hover { opacity: 1; }

.author-actions {
  max-width: 650px;
  margin: 0 auto 36px auto;
  display: flex;
  justify-content: flex-end;
  gap: 6px;
  padding: 10px 18px 0 18px;
  background: white;
  margin-left: auto;
  margin-right: auto;
}


</style>