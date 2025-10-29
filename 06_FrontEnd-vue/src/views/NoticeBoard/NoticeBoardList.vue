<template>
    <div>
    <h1>공지사항 게시글 전체</h1>
    <div class="searchBar">
        <select v-model="searchType">
            <option value="title">제목</option>
            <option value="content">내용</option>
            <option value="contentOrTitle">제목+내용</option>
        </select>
        <input v-model="searchQuery" placeholder="검색어를 입력하세요" />
        <button id="searchButton" @click="onSearch">
            <img src="./asset/search.png" alt="검색" style="width:20px; height:20px;">
        </button>
        <button id="writeButton" @click="openWriteModal">
            <img src="./asset/more.png" alt="게시글 추가" style="width:20px; height:20px;">
        </button>

        <WritePost v-if="showWriteModal" @close="showWriteModal = false" @post-success="onPostSuccess" />
    </div>
    <table>
        <thead>
            <tr>
            <th>NO.</th>
            <th>제목</th>
            <th>작성일</th>
            </tr>
        </thead>
        <tbody>
        <tr v-for="post in postList" :key="post.id">
            <td>{{ post.id }}</td>
            <td @click="goToDetail(post.id)" class="post-title">
                {{ post.title }}
            </td>
            <td>{{ post.createDate }}</td>
        </tr>
        </tbody>
    </table>
    <div class="pagination-wrapper">
            <pagination
            v-if="!isSearching && pagingInfo"
            :current-page="pagingInfo.currentPage"
            :start-page="pagingInfo.startPage"
            :end-page="pagingInfo.endPage"
            :last-page="pagingInfo.lastPage"
            @page-change="fetchBoardList"
            />
        </div>
    </div>
</template>

<script setup>
    import axios from 'axios';
    import { ref, onMounted } from 'vue';
    import { useRouter } from 'vue-router';
    import Pagination from '@/views/Board/Pagination.vue';
    import { useUserStore } from '@/stores/user';
    import WritePost from './WritePost.vue';

const userStore =  useUserStore();
const token = userStore.token;
    
    const router = useRouter();
    const postList = ref([]);
    const pagingInfo = ref({
        currentPage: 1,
        startPage: 1,
        endPage: 1,
        lastPage: 1
    });

    const searchType = ref('title');
    const searchQuery = ref('');
    const isSearching = ref(false);

    // 데이터 가져오기
    const fetchBoardList = async (page = 1) => {
    isSearching.value = false;
    try {
        const response = await axios.get(`http://localhost:8000/main-client/noticeboards/list?page=${page}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
        });
        postList.value = response.data.boardList;
        pagingInfo.value = response.data.pagingInfo;
    } catch (error) {
        console.error('데이터 불러오기 실패', error)
    }
};

const showWriteModal = ref(false);
function openWriteModal() {
  showWriteModal.value = true;
}

function onPostSuccess() {
  showWriteModal.value = false;
  fetchBoardList(1);
}

    // 제목 클릭 시 이동 함수
    const goToDetail = (postId) => {
        console.log('함수 실행됨..', postId);
        router.push(`/noticeboards/detail/${postId}`)
    }

    onMounted(() => {
        console.log(token);
        if (!token) {
            alert('로그인하세요');
        }
        fetchBoardList(1);
    });


// // 검색 함수
const onSearch = async () => {
    isSearching.value = true;
    try {
        console.log(searchType.value);
        console.log(searchQuery.value);
        const response = await axios.get(`http://localhost:8000/main-client/noticeboards/search?type=${searchType.value}&keyword=${searchQuery.value}`,
        {
            headers: {
            Authorization: `Bearer ${token}`
            }
        }
        );
        // 결과 처리: boardList에 검색 결과 저장
        postList.value = response.data;
    } catch (error) {
        console.error('검색 실패', error);
    }
};
</script>


<style scoped>
table {
    width: 100%;
    border-collapse: collapse;
}
th, td {
    padding: 12px;
    border-bottom: 1px solid #ddd;
    text-align: left;
}
thead {
    background: #fafafa;
}
h1 {
    text-align: center;
    margin: 32px 0 24px 0;
}
div {
    margin: 12px 0;
}
.searchBar select {
    border: none;
    border-radius: 20px;       /* 버튼처럼 둥글게 */
    background: #f1f1f1;
    padding: 8px 18px 8px 14px;
    font-size: 14px;
    appearance: none;          /* 기본 브라우저 스타일 제거 */
    -webkit-appearance: none;
    -moz-appearance: none;
    outline: none;
    margin-right: 4px;
    height: 36px;
    cursor: pointer;
}
.post-title {
    cursor: pointer;
}

/* 커스텀 화살표 넣기 */
.searchBar {
    position: relative;
    display: flex;
    gap: 8px; 
}
.searchBar select {
    width: 100px;
}
.searchBar::after {
    content: '▼';
    position: absolute;
    left: 77px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 11px;
    color: #999;
    pointer-events: none;
}

/* 검색창 디자인 */

.searchBar input {
    border: none;
    border-radius: 18px;
    background: #f3f3f3;
    padding: 0 22px;
    font-size: 17px;
    height: 38px;
    margin-left: 0;
    outline: none;
    min-width: 160px;
    font-weight: 500;
}

.searchBar input::placeholder {
    color: #c3c3c3;
}

#searchButton {
    border: none;
    background: #f3f3f3;
    border-radius: 20px;
    width: 38px;
    height: 38px;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    padding: 0;
}
#writeButton {
    background: none;
    cursor: pointer;
    border: none;
    margin-left: auto;
}
.pagination-wrapper {
    display: flex;
    justify-content: center;
    margin: 40px 0 0 0;
}
</style>