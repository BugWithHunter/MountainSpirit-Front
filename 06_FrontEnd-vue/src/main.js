import { createApp } from 'vue'
import App from './App.vue'
import indexRoutes from './router/index.routes.js'

const app = createApp(App);
app.use(indexRoutes);
app.mount('#app');
