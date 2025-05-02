import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import list from '../views/list.vue';
import homepage from '../views/homepage.vue';
import component from 'element-plus/es/components/tree-select/src/tree-select-option.mjs';
import send from '../views/send.vue';
import { pa } from 'element-plus/es/locales.mjs';
import pac  from '../views/packa1ge.vue';
import global from '../views/global.vue';
import sfm from '../views/sfm.vue';
import scan from '../views/scan.vue';
const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/list',
    name: 'list',
    component: list
  },
  {
    path:'/home',
    name:homepage,
    component: homepage
  },
  {
    path:'/send',
    name:send,
    component:send
  },{
    path:'/pac',
    name:pac,
    component:pac
  },{
    path:'/global',
    name:global,  
    component:global
  },{
    path:'/scan', 
    name:scan,
    component:scan
  },{
    path:'/sfm',
    name:sfm,
    component:sfm
  }

];

const router = createRouter({
  // 使用 import.meta.env.BASE_URL 来替代 process.env.BASE_URL
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;
