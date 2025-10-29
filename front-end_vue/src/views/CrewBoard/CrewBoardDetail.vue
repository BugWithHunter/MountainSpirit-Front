<template>
    <div class="board-detail">
    <h2>크루 게시글</h2>
        <!-- (수정/삭제 버튼 구현 예시, 작성자만 보임) -->
    <div v-if="Number(currentUserId) === post.cumId" class="author-actions">
    <button @click="openEditModal">
        <img src="./asset/edit.png" alt="수정" style="width:20px; height:20px;">
    </button>
    <EditPost
    v-if="showEditModal"
    :post-id="post.id"
    :init-title="post.title"
    :init-content="post.content"
    @close="showEditModal = false"
    @edit-success="handleEditSuccess"
    />
    <button @click="deletePost(post.id)">
        <img src="./asset/delete.png" alt="삭제" style="width:20px; height:20px;">
    </button>
    </div>
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
        <div class="board-detail__report-dropdown">
        <button @click="toggleReportMenu(post.cumId)" class="report-btn">
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
    <div class="board-detail__content">
    <p>{{ post.content }}</p>
    <div v-if="post.thumbnailDTOList && post.thumbnailDTOList.length > 0" class="thumbnail-list">
        <img
        v-for="thumb in post.thumbnailDTOList"
        :key="thumb.thumbnail"
        :src="`/boardImage/${thumb.thumbnail}`"
        alt="썸네일"
        class="thumbnail-img"
        />
    </div>
    </div>
    <div class="board-detail__footer">
    <button class="icon-btn" @click="likes(post.cumId)">
        <img v-if="!isLiked" src="./asset/defaultHeart.png" alt="좋아요" style="width:20px; height:20px;">
        <img v-if="isLiked" src="./asset/fullheart.png" alt="좋아요" style="width:20px; height:20px;">
    </button>
    <button @click="goToCommentList(postId)" class="icon-btn">
        <img src="./asset/comment.png" alt="댓글" style="width:20px; height:20px;">
    </button>
    </div>
    </div>
</template>


<script setup>
    import { ref, onMounted } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import axios from 'axios';
    import { useUserStore } from '@/stores/user';
    import EditPost from './EditPost.vue';

    const userStore =  useUserStore();
    const token = userStore.token;

    const showEditModal = ref(false);
    function openEditModal() {
        showEditModal.value = true;
    }

    function handleEditSuccess() {
        showEditModal.value = false;
        fetchPostDetail();
    }

    const route = useRoute();
    const router = useRouter();
    const postId = route.params.postId;
    const post = ref({});

    const showReportMenu = ref(false);
    const reportTypes = [
        '성희롱', '사기', '협박', '욕설', '도배글', '혐오콘텐츠'
    ]

    
    function toggleReportMenu(writter) {
        if (userStore.userId == writter) return;
        showReportMenu.value = !showReportMenu.value
    }
    function reportType(type) {

        // 신고 상세 페이지 컴포넌트로 이동
        router.push({ name: 'reportForm', params: { postId, type } })
    }

async function fetchPostDetail() {
  try {
    const response = await axios.get(
      `http://localhost:8000/main-client/crewboards/list/${route.params.postId}/select`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    post.value = response.data[0];
    console.log(post.value);
  } catch (error) {
    console.error('게시글 상세 조회 실패:', error)
  }
}

// 마운트시 호출
onMounted(fetchPostDetail);

    const currentUserId = ref(null);

    onMounted(() => {
    currentUserId.value = userStore.userId;
    console.log("현재 로그인한 사용자 ID:", currentUserId.value);
    });

    // 댓글 조회 컴포넌트로 이동
    const goToCommentList = () => {
        router.push({ 
            name: 'commentList',
            params: { 
                type: 2,
                postId: route.params.postId },
        })
    }

    const isLiked = ref(false);

    // 좋아요 기능
    const likes = async (writter) => {
        console.log(userStore.token);
        console.log(userStore.userId);
        console.log(writter);

        if (userStore.userId == writter) return;

        try {
            console.log(postId);
            const response = await axios.get(
            `http://localhost:8000/main-client/crewboards/${postId}/select/likes`,
            {
            headers: {
                Authorization: `Bearer ${token}`
            }
            });
            console.log(response.data);
            if (response.data == "좋아요 등록") {
                isLiked.value = true;
            } else {
                isLiked.value = false;
            }
        } catch (e) {
            // 네트워크/에러시 기본값: 해제(default)
            isLiked.value = false;
            console.error('좋아요 상태 변경 실패:', e);
        }
    }


    const deletePost = async () => {
        try {
            const response = await axios.put(`http://localhost:8000/main-client/crewboards/delete/${postId}`,
            {}, {
            headers: {
                Authorization: `Bearer ${token}`
            }
            }
            )
        } catch (error) {
        console.error('게시글 삭제 실패:', error);
    }
    router.push("/crewboards/list");
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
h2 {
    text-align: left;
    margin: 32px 0 24px 0;
    margin-left: auto;
    margin-right: auto;
}
div {
    margin: 12px 0;
}
button {
    background: none;
    border: none;
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
  max-width: 1200px;
  margin: 0 auto 20px auto;
  display: flex;
  justify-content: flex-end;
  gap: 6px;
  padding: 10px 18px 0 18px;
  background: white;
  margin-left: auto;
  margin-right: auto;
}


</style>