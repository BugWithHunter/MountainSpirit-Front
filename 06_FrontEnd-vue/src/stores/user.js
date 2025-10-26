import { defineStore } from "pinia";
import { ref, readonly, computed } from 'vue'

export const useUserStore = defineStore('user', () =>{
    
    //state 내부 변수
    
    const _name         = ref('');
    const _email        = ref('');
    const _roles        = ref([]);
    const _profile      = ref('');
    const _token        = ref('');
    const _isLoggedIn   = ref(false)
    
    // state 읽기전용
    const name          = readonly(_name);
    const email         = readonly(_email);
    const roles         = readonly(_roles);
    const profile       = readonly(_profile);
    const token         = readonly(_token);
    const isLoggedIn    = readonly(_isLoggedIn);

    // getter


    // Setter
    function logIn({username, userEmail, profilePath, authorities}) {
        _name.value = username;
        _email.value = userEmail;
        _profile.value = profilePath;
        _roles.value = authorities;
        _isLoggedIn.value = true
    }
    
    function logOut({username, userEmail, profilePath, authorities}) {
        _name.value = '';
        _email.value = '';
        _profile.value = '';
        _roles.value = [];
        _isLoggedIn.value = false
    }


    // return (public)

    return {
        name,
        email,
        roles,
        profile,
        token,
        isLoggedIn,

        logIn,
        logOut
    }

})