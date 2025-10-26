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
              <template v-if="userStore.isLoggedIn">  <!--로그아웃-->
                <li><RouterLink to="/" @click="userStore.logOut">{{ loginMenu }}</RouterLink></li>
              </template>
              <template v-else> <!--로그인-->
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

    const userStore = useUserStore();
    
    const showLoginMenu = ref(false);
    const showSubmenu = ref(false);
    console.log('userStore.isLoggedIn:',userStore.isLoggedIn);
    // 파생값은 computed를 사용 (값이 변하면 계산을 다시해서 반환하며 변하지 않으면 캐싱된 데이터 반환)
    const loginMenu = computed(() => (userStore.isLoggedIn ? '로그아웃' : '로그인'))

    const pageTitle = ['크루', '랭킹', '게시판', '산 목록'];

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


</style>