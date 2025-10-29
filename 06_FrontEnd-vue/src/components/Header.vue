<template>
    <nav class="navbar">

        <div class="logo image">
            
            <RouterLink to="/"><img src="/logo.png" alt="산신령"></RouterLink>
        </div>

        <div class="nav container"
                @mouseover="showSubmenu = true" 
                @mouseleave="showSubmenu = false"
      >
            <ul class="menu">
                <li v-for="title in pageTitle" class="menu-item">{{ title }}</li>
            </ul>
        </div>

        <div class="profile image"  
          @mouseover="showLoginMenu = true"
          @mouseleave="showLoginMenu = false">
            <template v-if="userStore.profile">
                <img class="profile-image" :src="userStore.profile"  >
            </template>
            <template v-else>
                <img class="profile-image" src="/notLogin.png"  >
            </template>
            <ul class="login-submenu" :class="{ visible: showLoginMenu }">
              <template v-if="userStore.isLoggedIn">  <!--로그인 상태-->
                <li><RouterLink to="/" @click="userStore.logOut">{{ loginMenu }}</RouterLink></li>
                <li><button class="link-btn" @click="openFilePicker">프로필 변경</button></li>
                <input
                  ref="fileInput"
                  type="file"
                  accept="image/*"
                  class="sr-only-file"
                  @change="onFileSelected"
                  tabindex="-1"
                  aria-hidden="true"
                />
              </template>
              <template v-else> <!--로그아웃 상태-->
                <li><RouterLink :to="{name : 'member-login'}">{{ loginMenu }}</RouterLink></li>
              </template>
                <li v-if="userStore.isLoggedIn">
                    <RouterLink to="/member-view">마이페이지</RouterLink>
                </li>
                <li v-else>
                    <RouterLink to="/member/signUp">회원 가입</RouterLink>
                </li>

                
                
                <!-- <li><RouterLink to="/member-view" >마이페이지</RouterLink></li> -->
            </ul>
        </div>

    <MenuExtention :showSubmenu="showSubmenu"
                @mouseover="showSubmenu = true" 
                @mouseleave="showSubmenu = false"></MenuExtention>
    </nav>
</template>

<script setup>
    import {RouterLink } from 'vue-router';
    import MenuExtention from '@/components/MenuExtention.vue';
    import {ref, watch, computed} from 'vue';
    import { useUserStore } from '@/stores/user';
    import axios from 'axios';

    const userStore = useUserStore();
    
    const showLoginMenu = ref(false);
    const showSubmenu = ref(false);
    console.log('userStore.isLoggedIn:',userStore.isLoggedIn);
    // 파생값은 computed를 사용 (값이 변하면 계산을 다시해서 반환하며 변하지 않으면 캐싱된 데이터 반환)
    const loginMenu = computed(() => (userStore.isLoggedIn ? '로그아웃' : '로그인'))
    const pageTitle = ['크루', '랭킹', '게시판', '산 목록'];


const fileInput = ref(null)
const uploading = ref(false)

function openFilePicker() {
  fileInput.value?.click()
}

async function onFileSelected(e) {
  const input = e.target
  const file = input.files?.[0]
  input.value = '' // 같은 파일 다시 선택 가능하게 초기화

  if (!file) return
  if (!file.type.startsWith('image/')) return alert('이미지 파일만 업로드 가능해요.')
  if (file.size > 5 * 1024 * 1024) return alert('5MB 이하만 업로드 가능합니다.')

  try {
    uploading.value = true
    const form = new FormData()
    form.append('singleFile', file)

    const res = await axios.post(
      `http://localhost:8000/member-client/member/Profile/${userStore.userId}`,
      form,
      {
        headers: {
          // 'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${userStore.token}`,
        },
      }
    )

    const { httpStatus, result } = res.data ?? {}
    const { responseData } = result ?? {}
    const {
      urlPath,
      successUpload,
      dirPath,
      filePath,
      exceptionMessage,
    } = responseData ?? {}

    console.log('업로드 응답:', { httpStatus, successUpload, urlPath, dirPath, filePath, exceptionMessage })

    if (httpStatus !== 200 || !successUpload || !urlPath) {
      alert('프로필 변경 실패: ' + (exceptionMessage || '알 수 없는 오류'))
      return
    }

    userStore.changeProfile('');
    setTimeout(async() => {
      await userStore.changeProfile(urlPath)
    },300)
  } catch (err) {
    console.error(err)
    alert('업로드 실패 :' , err)
  } finally {
    uploading.value = false
  }
}



</script>

<style scoped>
  nav {
    width: 100%;
    text-align: center;
    margin-top: 1rem;
    font-size: 20px;
    background-color: white;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2); 
    /* display : flex; */
  }

.nav.container {
  position: relative;
  flex: 1; /* 남은 공간을 전부 차지해서 중앙 정렬 쉽게 */
  display: flex;
  justify-content: center; /* 메뉴 항목 중앙 */
}


  /* 메뉴 */
.menu {
  display: flex;
  gap: 50px;
  list-style: none;
  margin: 0;
  padding: 0;
  font-weight: bold;
  font-size: 20px;
  cursor: pointer;
}

.menu .menu-item {
  display: flex;
  width: 150px;
  text-decoration: none;
}

/* 상단 메뉴바 */
.navbar {
  position: relative;
  display: flex;
  justify-content: space-between; /* 좌: 로고 | 중: 메뉴 | 우: 프로필 */
  align-items: center;
  padding: 10px 80px; /* 양쪽 여백 동일하게 */
  background-color: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2);
}

.nav.container {
  position: relative;    /*  이걸 기준으로 하위 메뉴를 절대 배치 */
  margin-left: 10rem;
}



  /* img {
    display: flex;
    font-size : 15px;
  } */

.logo img,
.profile img {
  width: 60px;   /*  동일 크기 */
  height: auto;
}

.logo,
.profile {
  flex: 0 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.profile-image {
  width: 100%;
  height: 100%;
  object-fit: cover;      /*  이미지가 꽉 차게 비율 유지하며 채움 */
}

div.profile {
    width: 60px;          /* 원하는 크기로 설정 */
    height: 60px;
    border-radius: 50%;     /*  정사각형을 완전한 원으로 만듦 */
    overflow: hidden;       /*  이미지가 밖으로 삐져나오지 않게 자름 */
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f5f5f5; /* 배경색 (선택사항) */
}


/* 로그인 하위 메뉴 */
.login-submenu {
  position: absolute;
  top: 100%;
  /* right: -55%; */
  background-color: white;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.1);
  list-style: none;
  margin: 0;
  padding: 10px 0;
  min-width: 150px;
  opacity: 0;
  visibility: hidden;
  transition: all 0.6s ease;
  border-radius: 4px;
  text-align: center;
  
  z-index: 20000;
}

.login-submenu.visible {
  opacity: 1;
  visibility: visible;
}

.login-submenu a {
  text-decoration: none;
}

.login-submenu li {
  padding: 8px 20px;
}

.login-submenu li:hover {
  background-color: #f7f1ff;
}

.disabled-link {
  color: #bbb;
  cursor: not-allowed;
  pointer-events: none; /* 👈 클릭 자체 불가능하게 함 */
}

.link-btn {
  background: none;
  border: none;
  color: #337ab7;
  font: inherit;
  cursor: pointer;     /* 👈 손 모양 */
  padding: 0;
  text-align: left;
}

/* 화면/레이아웃에 안 보이지만 DOM에는 존재하도록 */
.sr-only-file {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border: 0;
}
</style>