<template>
  <section>
    <div class="box">
      <div class="square" style="--i:0;"></div>
      <div class="square" style="--i:1;"></div>
      <div class="square" style="--i:2;"></div>
      <div class="square" style="--i:3;"></div>
      <div class="square" style="--i:4;"></div>
      <div class="square" style="--i:5;"></div>

      <div class="container">
        <div class="form">
          <svg t="1745561571445" class="icon" viewBox="0 0 2919 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9506" width="200" height="200"><path d="M364.097901 307.321663h72.176792c-23.673663 96.276277-110.541307 167.733228-214.137663 167.733228C100.524356 475.054891 1.926337 376.649505 1.622178 255.168634v-32.859248C1.632317 100.544634 100.321584 1.855366 222.13703 1.855366c103.545663 0 190.362614 71.345426 214.08697 167.550733h-72.227485C342.522931 111.930297 287.105267 70.970297 222.106614 70.970297c-83.623287 0-151.410059 67.786772-151.440475 151.349228v32.311762c0.030416 83.562455 67.827327 151.349228 151.440475 151.349228 65.089901 0 120.568396-41.061386 141.991287-98.648713z m1245.021782 236.736634c121.815446 0 220.555406 98.729822 220.555406 220.464158v32.281347c0 121.764752-98.750099 220.45402-220.555406 220.45402-121.582257 0-220.190416-98.395248-220.514851-219.876119v-32.859248c0-121.734337 98.73996-220.464158 220.514851-220.464158z m151.440475 252.745505v-32.261069h-0.020277c0-83.592871-67.817188-151.369505-151.420198-151.369505-83.643564 0-151.430337 67.776634-151.430336 151.369505v32.261069c0 83.613149 67.786772 151.369505 151.430336 151.369505 83.60301 0 151.420198-67.756356 151.420198-151.369505h0.020277z m-696.056396-321.779327V1.875644h69.084515v473.148831h-69.084515z m72.835802 69.054099l182.190891 473.169109h-74.113267l-146.401584-380.056079-119.73703 311.012119h165.472317l26.613861 69.04396H878.510891l182.221307-473.169109h76.607366zM697.658297 879.139485v-473.169109h69.084515v473.169109h-69.084515z m72.845941 69.064238l26.593584 69.04396H667.28301l26.603723-69.04396h76.617505zM511.695842 336.926416L640.679287 1.885782h74.174099L612.42297 267.841901h165.533149l26.583445 69.084515H511.695842zM749.568 1.885782h74.143683l128.993584 335.040634h-74.153821L749.568 1.885782z m-376.000634 542.213069h69.064238v473.169109h-69.064238L70.666139 651.973703v365.294257H1.632317v-473.169109h69.307564l302.637624 364.878575v-364.888713z m2233.972594 73.433981v157.138376h312.32V998.957624l-47.235802 18.300198V822.008396h-288.849108a23.683802 23.683802 0 0 1-23.622971-23.704079V570.246337h133.099723v-26.177901h47.316911v26.177901h133.160554v145.286336l-47.316911 18.350891V617.532832H2607.550099z m-538.461782 93.954534V664.180594h369.968159v47.316911h-369.95802z m29.523644-67.624554V617.54297h-47.367604v-47.296633h47.367604v-26.157624h47.357465v26.157624h216.256634v-26.157624h47.367604v26.157624h47.357465v47.296633h-47.357465v26.319842h-47.367604V617.54297h-216.256634v26.319842h-47.357465z m0 158.64903v-70.726971h47.367604v70.726971h-47.357466z m263.624237 0v-70.726971h47.347327v70.726971h-47.347327z m-131.751287 0.770534v-71.497505h47.256079v71.497505h-47.256079z m295.956278 163.961664l18.350891-47.854258h280.981544v47.854258H2526.441188z m-87.597624-126.195327l18.523247 47.316911h-61.551524l50.652514 128.892198h-50.855287l-50.652515-128.892198h-67.21901v128.892198h-47.306772V888.365624h-68.334257l-49.608238 128.892198h-50.753901l49.658931-128.892198h-60.608634l18.229228-47.316911h161.406733v-19.466139h47.31691v19.466139h161.102575z m254.428515-109.263842v-87.283326h47.31691v87.283326h-47.31691z" fill="#1B70E0" p-id="9507"></path></svg>
          <form @submit.prevent="login">
            <div class="inputBx">
              <label for="username"><i class="fas fa-user-circle"></i></label>
              <input
                type="text"
                id="username"
                v-model="form.username"
                placeholder="用户名"
                required
              />
            </div>

            <div class="inputBx password">
              <label for="password"><i class="fas fa-key"></i></label>
              <input
                type="password"
                id="password"
                v-model="form.password"
                placeholder="密码"
                required
              />
            </div>

            <div class="inputBx">
              <input type="submit" value="登 录" />
            </div>
          </form>

          <p>还没有账号? <router-link to="/register">立即注册</router-link></p>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import axios from 'axios';

const router = useRouter();
const userStore = useUserStore();

const form = ref({
  username: '',
  password: '',
});

const login = async () => {
  try {
    const response = await axios.post('/api/login', form.value);

    const { type, message, token: receivedToken } = response.data;

    if (type === 'success') {
      userStore.login(form.value.username, receivedToken);
      ElMessage.success(message || '登录成功');
      router.push('/home');
    } else {
      ElMessage.error(message || '登录失败');
    }
  } catch (error) {
    console.error('登录失败:', error);
    ElMessage.error('网络或服务器错误，请稍后重试');
  }
};

// 修复移动端 100vh 滑动空白问题
const setVh = () => {
  const vh = window.innerHeight * 0.01;
  document.documentElement.style.setProperty('--vh', `${vh}px`);
};

onMounted(() => {
  setVh();
  window.addEventListener('resize', setVh);
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', setVh);
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=El+Messiri:wght@700&display=swap");

* {
  margin: 0;
  padding: 0;
  font-family: "El Messiri", sans-serif;
}

body {
  background: #031323;
  overflow: hidden;
}

section {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: calc(var(--vh, 1vh) * 100); /* 替代100vh */
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradient 10s ease infinite;
  overflow: hidden;
}
@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.box {
  position: relative;
}

.square {
  position: absolute;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  box-shadow: 0 25px 45px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.15);
  border-radius: 15px;
  animation: square 10s linear infinite;
  animation-delay: calc(-1s * var(--i));
}

@keyframes square {
  0%,
  100% {
    transform: translateY(-20px);
  }

  50% {
    transform: translateY(20px);
  }
}

.square:nth-child(1) {
  width: 100px;
  height: 100px;
  top: -15px;
  right: -45px;
}

.square:nth-child(2) {
  width: 150px;
  height: 150px;
  top: 105px;
  left: -125px;
  z-index: 2;
}

.square:nth-child(3) {
  width: 60px;
  height: 60px;
  bottom: 85px;
  right: -45px;
  z-index: 2;
}

.square:nth-child(4) {
  width: 50px;
  height: 50px;
  bottom: 35px;
  left: -95px;
}

.square:nth-child(5) {
  width: 50px;
  height: 50px;
  top: -15px;
  left: -25px;
}

.square:nth-child(6) {
  width: 85px;
  height: 85px;
  top: 165px;
  right: -155px;
  z-index: 2;
}

.container {
  position: relative;
  padding: 50px;
  width: 260px;
  min-height: 380px;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  border-radius: 10px;
  box-shadow: 0 25px 45px rgba(0, 0, 0, 0.2);
}

.container::after {
  content: "";
  position: absolute;
  top: 5px;
  right: 5px;
  bottom: 5px;
  left: 5px;
  border-radius: 5px;
  pointer-events: none;
  background: linear-gradient(to bottom, rgba(255, 255, 255, 0.1) 0%, rgba(255, 255, 255, 0.1) 2%);
}

.form {
  position: relative;
  width: 100%;
  height: 100%;
}

h2 {
  color: #fff;
  letter-spacing: 2px;
  margin-bottom: 30px;
}

.inputBx {
  position: relative;
  width: 100%;
  margin-bottom: 20px;
}

.inputBx input {
  width: 80%;
  outline: none;
  border: none;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background: rgba(255, 255, 255, 0.2);
  padding: 8px 10px;
  padding-left: 40px;
  border-radius: 15px;
  color: #fff;
  font-size: 16px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

.fas {
  position: absolute;
  top: 13px;
  left: 13px;
}

label {
  position: absolute;
  top: 10px;
  left: 10px;
  font-size: 18px;
  color: rgba(255, 255, 255, 0.6);
}

input[type="submit"] {
  width: 100%;

  color: #fff;
  border: none;
  border-radius: 10px;
  padding: 10px;
  cursor: pointer;
  font-weight: bold;
}



p {
  color: #fff;
  font-size: 15px;
  margin-top: 5px;
}

a {
  color: #fff;
}

a:hover {
  background-color: #000;
  background-image: linear-gradient(to right, #434343 0%, black 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
</style>
