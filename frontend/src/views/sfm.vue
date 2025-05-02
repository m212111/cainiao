<template>
  <div class="back">
    <el-button class="back-button" :icon="ArrowLeft" @click="$router.back()"></el-button>

  </div>
  <div class="sfm-page">
    
    <div v-if="identityCode" class="barcode-container">
      <svg id="barcode"></svg>
    </div>
    <div v-else-if="errorMessage" class="error">
      {{ errorMessage }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, nextTick } from 'vue';
import { useUserStore } from '@/stores/user';
import axios from 'axios';
import { ArrowLeft } from '@element-plus/icons-vue';
import JsBarcode from 'jsbarcode';

const userStore = useUserStore();
const token = ref(userStore.token);
const identityCode = ref('');
const errorMessage = ref('');

// 查询身份码（通过 token）
const fetchIdentityCode = async () => {
  try {
    const response = await axios.get('/api/getSfmByToken', {
      headers: {
        Authorization: `Bearer ${token.value}`,
      },
    });

    if (response.data.type === 'success') {
      identityCode.value = response.data.identityCode;
      errorMessage.value = '';
    } else {
      identityCode.value = '';
      errorMessage.value = response.data.message || '查询失败';
    }
  } catch (error) {
    identityCode.value = '';
    errorMessage.value = '请求失败：' + error.message;
  }
};

// 监听 identityCode 的变化并生成条形码
watch(identityCode, async (newCode) => {
  if (newCode) {
    await nextTick(); // 等待 DOM 更新完成
    JsBarcode("#barcode", newCode, {
      format: "CODE128",
      lineColor: "#000",
      width: 2,
      height: 100,
      displayValue: true,
    });
  }
});

// 页面加载时自动查询身份码
onMounted(() => {
  if (token.value) {
    fetchIdentityCode();
  } else {
    errorMessage.value = '未登录，请先登录后再查看身份码';
  }
});
</script>

<style scoped>
.back {
  position: absolute;
  top: 16px;
  left: 16px;
  z-index: 10;
}

.back-button {
  font-size: 16px;
}

.sfm-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f9f9f9;
}

.barcode-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 16px;
}

.error {
  margin-top: 16px;
  font-size: 18px;
  color: #f44336;
}
</style>