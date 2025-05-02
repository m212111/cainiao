<template>
    <div class="international-send">
        <div @click="goBack" class="header-content"><svg
          t="1745812297880"
          class="icon"
          viewBox="0 0 1024 1024"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="2598"
          width="40"
          height="40"
        >
          <path
            d="M675.328 117.717333A425.429333 425.429333 0 0 0 512 85.333333C276.352 85.333333 85.333333 276.352 85.333333 512s191.018667 426.666667 426.666667 426.666667 426.666667-191.018667 426.666667-426.666667c0-56.746667-11.093333-112-32.384-163.328a21.333333 21.333333 0 0 0-39.402667 16.341333A382.762667 382.762667 0 0 1 896 512c0 212.074667-171.925333 384-384 384S128 724.074667 128 512 299.925333 128 512 128c51.114667 0 100.8 9.984 146.986667 29.12a21.333333 21.333333 0 0 0 16.341333-39.402667z m-324.693333 373.013334l174.464-174.485334a21.141333 21.141333 0 0 0-0.192-29.973333 21.141333 21.141333 0 0 0-29.973334-0.192l-208.256 208.256a20.821333 20.821333 0 0 0-6.122666 14.976c0.042667 5.418667 2.133333 10.837333 6.314666 14.997333l211.178667 211.2a21.141333 21.141333 0 0 0 29.973333 0.213334 21.141333 21.141333 0 0 0-0.213333-29.973334l-172.629333-172.629333 374.997333 2.602667a20.693333 20.693333 0 0 0 21.034667-21.034667 21.482667 21.482667 0 0 0-21.333334-21.333333l-379.242666-2.624z"
            fill="#3D3D3D"
            p-id="2599"
          ></path>
        </svg><span>国际/港澳台寄件</span></div>
     
  
      <el-image
        :src=banner
        fit="cover"
        class="banner"
      ></el-image>
  
      <div class="card-container">
        <el-card class="send-card" shadow="always">
          <div class="card-content">
            <div class="text-area">
              <div>中国内地 <span class="arrow">→</span> 港澳台</div>
            </div>
            <el-button type="primary" @click="goSend">去寄件</el-button>
          </div>
        </el-card>
  
        <el-card class="send-card" shadow="always">
          <div class="card-content">
            <div class="text-area">
              <div>中国内地 <span class="arrow">→</span> 国际</div>
            </div>
            <el-button type="primary" @click="goSend">去寄件</el-button>
          </div>
        </el-card>
      </div>
    </div>
  </template>
  
  <script setup>
  import banner from '../assets/icons/banner.png';
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  
  const goBack = () => {
    router.back()
  }
  
  const goSend = () => {
    router.push('/send') 
  }
  </script>
  
  <style scoped>
  .header-content {
  display: flex;
  align-items: center;
}

  .international-send {
    background: #f5f5f5;
    min-height: 100vh;
  }
  
  .banner {
    width: 100%;
    height: 150px;
    object-fit: cover;
    margin-bottom: 20px;
  }
  
  .card-container {
    padding: 0 16px;
    display: flex;
    flex-direction: column;
    gap: 16px;
  }
  
  .send-card {
    border-radius: 10px;
  }
  
  .card-content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    
  }

  
  .text-area {
    font-size: 18px;
    font-weight: bold;
  }
  
  .arrow {
    margin: 0 8px;
    color: #409EFF;
  }
  .el-button {
    max-width:75px;
  }
  </style>
  