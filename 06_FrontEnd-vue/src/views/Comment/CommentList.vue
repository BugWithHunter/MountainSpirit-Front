<template>
    <div>

    </div>
</template>

<script setup>
    import axios from 'axios';
    import { ref, onMounted } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import { useUserStore } from '@/stores/user';

const userStore =  useUserStore();
const token = userStore.token;

const route = useRoute();
const router = useRouter();
const postId = route.params.postId;
const commentList = ref([]);

const fetchData = async () => {
    try {
        const response = await axios.get(`localhost:8000/main-client/${postId}/comments/read`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
        });
        commentList.value = response.data;
        pagingInfo.value = response.data.pagingInfo;
    } catch (error) {
        console.error('데이터 불러오기 실패', error)
    }
};

</script>

<style scoped>

</style>