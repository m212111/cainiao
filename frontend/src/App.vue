<template>
  <div id="app">
    <router-view></router-view>  <!-- 路由视图，显示登录或注册页面 -->
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
/* 在这里你可以添加一些全局的样式 */
body {
  margin: 0;
  padding: 0;
  font-family: 'El Messiri', sans-serif;
}
</style>
