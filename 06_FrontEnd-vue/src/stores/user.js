// stores/user.js
import { defineStore } from 'pinia'
import { ref, readonly } from 'vue'

export const useUserStore = defineStore(
  'user',
  () => {

    // ----- 외부 노출: 
    const name          = ref('');
    const userId        = ref(0);
    const email         = ref('');
    const roles         = ref([]);
    const profile       = ref('');
    const token         = ref('');
    const isLoggedIn    = ref(false); 
    const crewId        = ref(0);
    
    // ----- Actions -----
    function logIn({ userName, userEmail, profilePath, authorities,userId : uid, crewId : crew, }) {
      name.value = userName
      email.value = userEmail
      profile.value = profilePath
      roles.value = authorities
      userId.value = uid
      crewId.value = crew;
      isLoggedIn.value = true
    }
    
    function logOut() {
      name.value = ''
      email.value = ''
      profile.value = ''
      roles.value = []
      token.value = ''
      userId.value = 0
      crewId.value = 0;
      isLoggedIn.value = false
    }

    function setToken(jwtToken) {
        token.value = jwtToken; 
    }

    function changeProfile(newProfile) {
        profile.value = newProfile;
    }

    function changeCrew(id) {
        crewId.value = id;
    }

    // ----- 반환 -----
    return {
      // 외부 노출용 readonly state
      name, email, roles, profile, token, isLoggedIn, userId,crewId,
      // actions
      logIn, logOut, changeProfile , setToken, changeCrew
    }
  },
  {
    persist: {
      enabled: true, //storage 저장유무
      storage: sessionStorage,
      paths: [
        'name',
        'email',
        'roles',
        'profile',
        'token',
        'isLoggedIn',
        'userId'
      ]
    }
  }
)
