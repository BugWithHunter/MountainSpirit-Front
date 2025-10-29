<template>
<div class="totalcomment">
    <h2>댓글</h2>
    <button id="writeButton" @click.stop="modalOpen">
        <img src="@/views/Board/asset/more.png" alt="댓글 작성" style="width:20px; height:20px;">
    </button>
        <div class="comment-list-container">
    <div v-for="comment in commentList" :key="comment.id" class="comment-item">
        <div class="comment-meta">
        작성자번호 {{ comment.cumId }}
        <span class="comment-date">{{ comment.createDate }}</span>
        <!-- (수정/삭제 버튼 구현 예시, 작성자만 보임) -->
      <div v-if="Number(currentUserId) === comment.cumId" class="author-actions">
      <button  @click.stop="reModalOpen(comment.id, comment.content)">
          <img src="@/views/Board/asset/edit.png" alt="수정" style="width:15px; height:15px;">
      </button>
      <button @click="deleteComment(comment.id)">
          <img src="@/views/Board/asset/delete.png" alt="삭제" style="width:15px; height:15px;">
      </button>
      </div>
        <!-- 신고 -->
        <div class="board-detail__report-dropdown">
                    <button @click="toggleReportMenu(comment.id)" class="report-btn">
                        신고
                        <span :class="{'arrow-up': showReportMenu, 'arrow-down': !showReportMenu}"></span>
                    </button>
                    <div v-if="showReportMenu === comment.id" class="report-menu">
                        <ul>
                        <li v-for="type in reportTypes" :key="type" @click="reportType(type)">
                            {{ type }}
                        </li>
                        </ul>
                    </div>
            </div>
        </div>
        <div class="comment-content">{{ comment.content }}</div>
      </div>
    </div>
    <!-- 모달창 -->
    <div class="modal-wrap" v-show="modalCheck">
    <div class="modal-container">
        <h3>댓글 작성</h3>
        <form @submit.prevent="writeComment">
        <textarea v-model="content" required></textarea>
        <div class="modal-btn">
            <button @click.stop="modalOpen" type="button">취소</button>
            <button type="submit">등록</button>
        </div>
        </form>
    </div>
    </div>
    <!-- 수정 모달창 -->
    <div class="modal-wrap" v-show="modalCheck2">
    <div class="modal-container">
        <h3>댓글 수정</h3>
        <form @submit.prevent="rewriteComment">
        <textarea v-model="content" placeholder=content required></textarea>
        <div class="modal-btn">
            <button @click.stop="reModalOpen" type="button">취소</button>
            <button type="submit">수정</button>
        </div>
        </form>
    </div>
    </div>

    </div>
</template>

<script setup>
    import axios from 'axios';
    import { ref, onMounted } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import { useUserStore } from '@/stores/user';

    const userStore =  useUserStore();
    const token = userStore.token;
    const currentUserId = userStore.userId;

    const route = useRoute();
    const router = useRouter();
    const type = route.params.type;
    const postId = route.params.postId;
    const commentList = ref([]);
    const modalCheck = ref(false);
    const modalCheck2 = ref(false);
    const content = ref('');
    const showReportMenu = ref(false);
    const reportTypes = [
        '성희롱', '사기', '협박', '욕설', '도배글', '혐오콘텐츠'
    ]


    const fetchData = async () => {
        console.log(route.params.postId);
        console.log(type);
        try {
            const response = await axios.get(`http://localhost:8000/main-client/${route.params.postId}/comments/read`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
            });
            commentList.value = response.data;
        } catch (error) {
            console.error('데이터 불러오기 실패', error)
        }
    };

    onMounted(() => {
            fetchData();
        });


    const deleteComment = async (commentId) => {
      try {
        console.log(commentId);
            const response = await axios.delete(`http://localhost:8000/main-client/comments/${commentId}/delete`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
            });
        } catch (error) {
            console.error('댓글 삭제 실패', error)
        }
        fetchData();
    }

    const modalOpen = () => {
        modalCheck.value = !modalCheck.value;
    }

    const editingCommentId = ref(null);

    const reModalOpen = (id, writtenContent) => {
      editingCommentId.value = id;
      content.value = writtenContent;
      modalCheck2.value = !modalCheck2.value;
    }

    function toggleReportMenu(commentId) {
        showReportMenu.value = showReportMenu.value === commentId ? null : commentId;
    }
    function reportType(type) {

        // 신고 상세 페이지 컴포넌트로 이동
        router.push({ name: 'reportForm', params: { postId, type } })
    }

    const writeComment = async () => {
        let requestBody = { content: content.value };
        console.log(content.value);

        // type 값에 따라 적절한 property 확장
        if (type == 1) {
            requestBody.postId = postId;
        } else if (type == 2) {
            requestBody.crewPostId = postId;
        } else if (type == 3) {
            requestBody.noticePostId = postId;
        }

        console.log("postId:", postId);
        console.log("넣은값:",requestBody.postId);
        try {
        const response = await axios.post(
        'http://localhost:8000/main-client/boards/comment',
        requestBody,
        {
            headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
            },
        }
        )
        console.log("결과:", response.data);
    } catch (error) {
        console.error('댓글작성 실패:', error)
    }
    modalCheck.value = false;
    fetchData();
    }

    const rewriteComment = async () => {
      const formData = new FormData();
      formData.append('content', content.value);

      try {
        const response = await axios.put(`http://localhost:8000/main-client/comments/${editingCommentId.value}/modify`, 
        formData, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      } catch (error) {
        console.error('댓글수정 실패:', error)
    }
    modalCheck2.value = false;
    fetchData();
    }
</script>

<style scoped>
.totalcomment {
    width: 1000px;
    margin-left: auto;
    margin-right: auto;
}
/* 댓글 전체 리스트 박스 */
.comment-list-container {
  margin-top: 16px;
  border-radius: 20px;
  border: 1.5px solid #e3e3e3;
  background: #fff;
  padding: 12px 0;
  box-shadow: 0 2px 4px rgba(50,50,50,0.03);
  margin-bottom: 30px;
}

#writeButton {
    background: none;
    border: none;
    display: flex;
    margin-left: auto;
}

/* 각 댓글 */
.comment-item {
  width: 1000px;
  padding: 18px 28px 10px 28px;
  border-bottom: 1px solid #f0f0f0;
}

.comment-item:last-child {
  border-bottom: none;
}

/* 작성자 번호, 작성일 한 줄 */
.comment-meta {
  display: flex;
  align-items: center;
  color: #444;
  font-size: 15px;
  font-weight: 600;
  gap: 12px;
  margin-bottom: 5px;
}

/* 작성일은 옅은 색 */
.comment-date {
  font-weight: 400;
  color: #bababa;
  font-size: 13px;
}

/* 댓글 내용 */
.comment-content {
  font-size: 15px;
  color: #23272a;
  margin-left: 2px;
}

/* 댓글 헤더 (예: '댓글') */
.comment-header {
  font-size: 21px;
  font-weight: bold;
  margin-bottom: 10px;
  margin-top: 5px;
}

@media (max-width: 500px) {
  .comment-item {
    padding: 12px 6px 7px 14px;
  }
  .comment-list-container {
    padding: 6px 0;
  }
}

.modal-wrap {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.1);
}

.modal-container h3 {
  font-size: 15px;
  margin-bottom: 12px;
  margin-left: 5%;
  width: 90%;
  text-align: left;
  font-weight: 600;
}
.modal-container textarea {
  width: 90%;
  height: 110px;
  display: flex;
    margin-left: auto;
    margin-right: auto;
}


.modal-container {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  background: #fff;
  border-radius: 10px;
  padding: 20px;
}

.modal-btn {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

.modal-btn button {
  padding: 6px 12px;
  border-radius: 6px;
  border: 1px solid #ccc;
  cursor: pointer;
}

.modal-btn button:last-child {
  background: #00c774;
  color: #fff;
  border: none;
}

.modal-btn button:last-child:hover {
  background: #005fcc;
}
.board-detail__report-dropdown {
  display: flex;
  vertical-align: middle;
  margin-left: auto;
  position: relative;
}

.report-btn {
    display: flex;
  justify-content: flex-end;
  background: none;
  border: none;
  color: #888;
  font-size: 12px;
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
.author-actions {
  display: flex;
  vertical-align: middle;
  margin-right: auto;
  position: relative;
  justify-content: flex-end;
  gap: 6px;
  background: white;
}
button {
  background: none;
  border: none;
}
</style>