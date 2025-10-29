import { createApp } from 'vue'
import App from './App.vue'
import indexRoutes from './router/index.routes.js'
import { createPinia } from 'pinia';             // Pinia 컨테이너 생성 함수
import piniaPersistedState from 'pinia-plugin-persistedstate'

const app = createApp(App);

const pinia = createPinia();                     // 2) Pinia 루트 인스턴스 생성
pinia.use(piniaPersistedState);


app.use(pinia);                                  // 3) 앱에 Pinia 연결
app.use(indexRoutes);
app.mount('#app');
