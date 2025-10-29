<template>
  <div class="modal-wrap">
    <div class="modal-container">
      <h3>게시글 작성</h3>
      <form @submit.prevent="submitPost" enctype="multipart/form-data">
        <label>제목</label>
        <input v-model="title" type="text" required>
        <label>내용</label>
        <textarea v-model="content" required></textarea>
        <label>사진 업로드</label>
        <div class="upload-row">
            <template v-for="(fileObj, idx) in previewList" :key="fileObj.id">
                <div class="image-thumb">
                <img :src="fileObj.url" alt="미리보기">
                <button class="thumb-remove-btn" @click="removeFile(idx)" type="button">&times;</button>
                </div>
            </template>
        <label class="upload-label-box">
        <img src="./asset/upload.png" alt="사진업로드">
        <input type="file" @change="onFileChange" multiple>
        </label>
        </div>
        <div class="modal-btn">
          <button type="button" @click="closeModal">취소</button>
          <button type="submit" >등록</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';

const userStore =  useUserStore();
const token = userStore.token;

const title = ref('');
const content = ref('');
const files = ref([]);
const previewList = ref([]);
const emit = defineEmits(['close', 'post-success']);

let fileId = 0;

function closeModal() {
  emit('close');
}

    function onFileChange(event) {
    //   files.value = event.target.files;
        const fileList = event.target.files;
    // 여러 장 한 번에 업로드도 지원
    for (let i = 0; i < fileList.length; i++) {
        const file = fileList[i];
        const reader = new FileReader();
        const curId = Date.now() + '-' + (fileId++);

        reader.onload = e => {
        previewList.value.push({
            id: curId,
            file: file,
            url: e.target.result
        });
        // files.value도 동기화
        files.value = previewList.value.map(obj => obj.file);
        };
        reader.readAsDataURL(file);
    }
}

    function removeFile(idx) {
    previewList.value.splice(idx, 1);
    files.value = previewList.value.map(obj => obj.file);
    }

    async function submitPost() {
    const formData = new FormData();
    formData.append('title', title.value);
    formData.append('content', content.value);
    for (const file of files.value) {
        formData.append('multiFiles', file);
    }

    try {
        await axios.post('http://localhost:8000/main-client/crewboards/insert', formData, {
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'multipart/form-data'
        }
        });
        emit('post-success');
    } catch (error) {
        alert('게시글 등록 실패: ' + error);
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
  height: 600px;
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

.upload-row {
  display: flex;
  align-items: center;
  gap: 14px;
  margin-bottom: 14px;
}
.image-thumb {
  width: 65px; height: 65px;
  border-radius: 8px;
  overflow: hidden;
  border: 1.3px solid #ddd;
  display: flex; align-items: center; justify-content: center;
  background: #faf9f7;
  position: relative;
  margin-right: 3px;
}
.image-thumb img {
    width: 65px; height: 65px;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.thumb-remove-btn {
  position: absolute;
  top: -8px; right: -8px;
  width: 20px; height: 20px;
  border: none;
  border-radius: 50%;
  background: #fff;
  color: #333;
  font-size: 18px;
  cursor: pointer;
  box-shadow: 0 1px 4px rgba(0,0,0,0.09);
  z-index: 2;
  padding: 0;
}
.upload-label-box {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 68px; height: 68px;
  border: 2px solid #eee;
  border-radius: 12px;
  background: #fafcff;
  position: relative;
  cursor: pointer;
}

.upload-label-box img {
  width: 30px; height: 30px;
  opacity: 0.7;
}

input[type="file"] { display: none; }


.upload-label-box {
  display: inline-block;
  width: 80px; height: 80px;
  border: 2px solid #eee;
  border-radius: 12px;
  background: #fafcff;
  margin-bottom: 10px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.upload-label-box img {
  position: absolute;
  top: 50%; left: 50%;
  width: 20px; height: 20px;
  transform: translate(-50%, -50%);
  opacity: 0.5;
}

.modal-btn {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 5px;
}
button[type="submit"] {
  background: #00c774;
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

