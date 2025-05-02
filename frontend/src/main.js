import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import ElementPlus from 'element-plus'
import { createPinia } from 'pinia';
import 'element-plus/dist/index.css'
createApp(App)
  .use(router)
  .use(ElementPlus)
  .use(createPinia())
  .mount('#app');
