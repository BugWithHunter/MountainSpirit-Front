<template>
    <div class="modal-bg">
        <div class="write-post-modal">
            <form @submit.prevent="onSubmit">
                <label>제목</label>
                <input v-model="title" type="text" required />

                <label>내용</label>
                <textarea v-model="content" rows="5" required></textarea>

                <label>사진 업로드</label>
                <div class="upload-box" @click="onImageClick">
                    <input type="file" ref="fileInput" @change="handleFile" style="display:none"/>
                    <img src="./asset/upload.png" alt="업로드" class="upload-icon""/>
                </div>

                <button class="submit-btn" @click="submitPost">등록</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';


const userStore =  useUserStore();
const token = userStore.token;

const title = ref('');
const content = ref('');
const imageFile = ref(null);

async function submitPost() {


    const formData = new FormData();
    formData.append('title', title.value);
    formData.append('content', content.value);
    formData.append('image', imageFile.value);

    try {
        const response = await axios.post(
        'http://localhost:8000/main-client/boards/insert',
        formData, // FormData 객체
        {
            headers: {
            Authorization: `Bearer ${token}`,
            }
        }
        );
        console.log('서버 응답:', response.data);
    } catch (error) {
        if (error.response) {
        // 서버가 에러 메시지를 보낸 경우
        console.error('업로드 실패:', error.response.data);
        } else {
        // 네트워크 등 기타 오류
        console.error('업로드 실패:', error.message);
        }
    }
    

}
    function onImageClick() {
    fileInput.value.click();
}

    // 이미지 파일 입력 핸들러
    function handleFile(e) {
    imageFile.value = e.target.files[0];
    }
</script>

<style scoped>
.modal-bg {
    position: fixed;
    left: 0; top: 0; right: 0; bottom: 0;
    background: #f6f6f6;
    min-width: 100vw;
    min-height: 100vh;
    z-index: 998;
    display: flex;
    align-items: center;
    justify-content: center;
}
.write-post-modal {
    background: #fff;
    border-radius: 17px;
    box-shadow: 0 2px 8px #e7e7e7;
    padding: 35px 29px 38px 29px;
    width: 410px;
    min-height: 440px;
    position: relative;
}
.modal-title {
    font-size: 16px;
    color: #bbb;
    margin-bottom: 18px;
}
label {
    font-size: 18px;
    font-weight: 600;
    margin-bottom: 5px;
    margin-top: 12px;
    display: block;
}
input[type="text"],
textarea {
    width: 90%;
    border-radius: 10px;
    border: 1px solid #c5c5c5;
    background: #fff;
    margin-bottom: 12px;
    margin-top: 2px;
    padding: 8px 16px;
    font-size: 17px;
    resize: none;
    outline: none;
}
.upload-box {
    width: 88px; height: 88px;
    border: 2px solid #ececec;
    border-radius: 11px;
    margin-bottom: 18px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}
.upload-icon {
    width: 20px;
    height: 20px;
    opacity: 0.5;
}
.submit-btn {
    float: right;
    background: #222;
    color: #fff;
    border: none;
    border-radius: 7px;
    font-size: 16px;
    padding: 7px 24px;
    margin-top: 10px;
    cursor: pointer;
    transition: background 0.15s;
}
.submit-btn:hover {
    background: #111;
}
</style>