<template>
  <div class="modal-wrap">
    <div class="modal-container">
        <h3>게시글 수정</h3>
        <form @submit.prevent="editPost" enctype="multipart/form-data">
            <label>제목</label>
            <input v-model="title" type="text" required>
            <label>내용</label>
            <textarea v-model="content" required></textarea>
            <div class="modal-btn">
                <button type="button" @click="closeModal">취소</button>
                <button type="submit" >수정</button>
            </div>
        </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useRouter } from 'vue-router';

const userStore =  useUserStore();
const token = userStore.token;

const router = useRouter();

const props = defineProps({
  postId: { type: [String, Number], required: true },
  initTitle: String,
  initContent: String,
});

const title = ref(props.initTitle);
const content = ref(props.initContent);
const emit = defineEmits(['close', 'edit-success']);

function closeModal() {
  emit('close');
}


    async function editPost() {
    const formData = new FormData();
    formData.append('title', title.value);
    formData.append('content', content.value);

    try {
        await axios.put(`http://localhost:8000/main-client/noticeboards/modify/${props.postId}`, formData, {
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'multipart/form-data'
        }
        });
        emit('edit-success');
        emit('close');
    } catch (error) {
        alert('게시글 수정 실패: ' + error);
    }

    }
</script>

<style scoped>
.modal-wrap {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0,0,0,0.15);
  z-index: 1000;
}
.modal-container {
  background: #fff;
  border-radius: 15px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.07);
  padding: 28px 30px 24px 30px;
  width: 550px;
  min-width: 320px;
  height: 500px;
}
h3 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 18px;
  font-weight: 600;
}
label {
  font-size: 15px;
  margin-top: 8px;
  margin-bottom: 4px;
  display: block;
}
input[type="text"] {
  width: 100%;
  border: 1.5px solid #ddd;
  border-radius: 10px;
  padding: 8px 10px;
  font-size: 15px;
  margin-bottom: 10px;
  box-sizing: border-box;
  outline: none;
}
textarea {
  width: 100%;
  min-height: 170px;
  border: 1.5px solid #ddd;
  border-radius: 10px;
  padding: 10px 14px;
  font-size: 15px;
  margin-bottom: 14px;
  resize: vertical;
  outline: none;
  box-sizing: border-box;
}


.modal-btn {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 5px;
}
button[type="submit"] {
  background: #222;
  color: #fff;
  border: none;
  border-radius: 5px;
  padding: 7px 18px;
  font-size: 15px;
  cursor: pointer;
  margin-left: 2px;
  transition: background 0.15s;
}
button[type="submit"]:hover {
  background: #444;
}
button[type="button"] {
  background: #fff;
  color: #444;
  border: 1px solid #bbb;
  border-radius: 5px;
  padding: 7px 15px;
  font-size: 15px;
  cursor: pointer;
  transition: border 0.15s, background 0.12s;
}
button[type="button"]:hover {
  border-color: #888;
}
</style>

