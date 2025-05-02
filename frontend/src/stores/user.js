// src/stores/user.js
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore('user', () => {
  const username = ref(localStorage.getItem('username') || '');
  const token = ref(localStorage.getItem('token') || '');

  const login = (name, tk) => {
    username.value = name;
    token.value = tk;
    localStorage.setItem('username', name);
    localStorage.setItem('token', tk);
  };

  const logout = () => {
    username.value = '';
    token.value = '';
    localStorage.removeItem('username');
    localStorage.removeItem('token');
  };

  return {
    username,
    token,
    login,
    logout,
  };
});
