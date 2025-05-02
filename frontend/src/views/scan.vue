<template>
  <div class="scanner-page">
    <!-- 摄像头选择 -->
    <el-select v-model="selectedDeviceId" placeholder="选择摄像头" @change="startScan">
      <el-option
        v-for="device in videoDevices"
        :key="device.id"
        :label="device.label || '摄像头'"
        :value="device.id"
      />
    </el-select>

    <!-- 扫描状态展示 -->
    <div>快递码: <span :style="{ color: normalCode ? 'black' : 'gray' }">{{ normalCode || '未识别' }}</span></div>
    <div>身份码: <span :style="{ color: sfmCode ? 'black' : 'gray' }">{{ sfmCode || '未识别' }}</span></div>

    <!-- 视频扫描区域 -->
    <div id="reader" class="video-wrapper"></div>

    <!-- 提交结果提示条 -->
    <div v-if="feedbackMessage" :class="['feedback-bar', feedbackType]">
      {{ feedbackMessage }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { Html5Qrcode } from 'html5-qrcode';
import axios from "axios";

const videoDevices = ref([]); // 摄像头设备列表
const selectedDeviceId = ref(''); // 当前选择的摄像头 ID
const sfmCode = ref(null); // 身份码
const normalCode = ref(null); // 快递码
const feedbackMessage = ref(''); // 提交结果消息
const feedbackType = ref(''); // 提交结果类型 ('success' 或 'error')
let html5QrCode = null; // Html5Qrcode 实例
let submitting = false; // 防止重复提交

const startScan = async () => {
  // 重置状态
  sfmCode.value = null;
  normalCode.value = null;
  feedbackMessage.value = '';
  feedbackType.value = '';
  submitting = false;

  // 停止之前的扫描
  if (html5QrCode) {
    await html5QrCode.stop();
  }

  html5QrCode = new Html5Qrcode("reader");

  try {
    // 获取摄像头分辨率
    const stream = await navigator.mediaDevices.getUserMedia({
      video: { deviceId: selectedDeviceId.value },
    });
    const settings = stream.getVideoTracks()[0].getSettings();
    const videoWidth = settings.width || 640;
    const videoHeight = settings.height || 480;

    // 开始扫描
    await html5QrCode.start(
  { deviceId: selectedDeviceId.value },
  {
    fps: 60, // 增加帧率提高扫描速度
    qrbox: { width: videoWidth, height: videoHeight }, // 根据实际需求调整扫描框大小
  },
  (decodedText) => {
    // 如果已经识别过这个码，忽略
    if (decodedText === sfmCode.value || decodedText === normalCode.value) return;

    // 判断类型并存储
    if (decodedText.startsWith("SFM") && !sfmCode.value) {
      sfmCode.value = decodedText;
    } else if (!decodedText.startsWith("SFM") && !normalCode.value) {
      normalCode.value = decodedText;
    }

    // 直接在扫描到第一个码后开始尝试识别第二个码
    if (sfmCode.value && normalCode.value && !submitting) {
      submitting = true;
      verifyAndPickup(sfmCode.value, normalCode.value);

      // 重置状态
      setTimeout(() => {
        sfmCode.value = null;
        normalCode.value = null;
        submitting = false;
        feedbackMessage.value = '';
        feedbackType.value = '';
      }, 1500);
    }
  },
);
  } catch (e) {
    console.error("摄像头启动失败：", e.message);
  }
};

const verifyAndPickup = async (sfm, normal) => {
  try {
    const res = await axios.post('/api/scan-pickup', {
      identityCode: sfm,
      packageId: normal,
    });

    if (res.data.type === 'success') {
      feedbackMessage.value = res.data.message;
      feedbackType.value = 'success';
    } else {
      feedbackMessage.value = res.data.message;
      feedbackType.value = 'error';
    }
  } catch (e) {
    feedbackMessage.value = '请求失败：' + e.message;
    feedbackType.value = 'error';
  }
};

onMounted(async () => {
  // 获取摄像头设备列表
  const devices = await Html5Qrcode.getCameras();
  videoDevices.value = devices.map((d) => ({
    id: d.id,
    label: d.label || '摄像头',
  }));

  if (devices.length) {
    selectedDeviceId.value = devices[0].id;
    await startScan();
  }
});

onBeforeUnmount(async () => {
  // 停止扫描
  if (html5QrCode) {
    await html5QrCode.stop();
    html5QrCode = null;
  }
});
</script>

<style scoped>
.scanner-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  height: 100vh;
  background: #f9f9f9;
  box-sizing: border-box;
}

.video-wrapper {

  width: 100%;
  max-width: 640px;
  height: 480px;
  background: #000;
  margin: 12px 0;
}

/* 提交结果条样式 */
.feedback-bar {
  width: 100%;
  max-width: 640px;
  text-align: center;
  font-size: 18px;
  padding: 12px;
  margin-top: 12px;
  border-radius: 8px;
  font-weight: bold;
}

.feedback-bar.success {
  background-color: #d2f8d2;
  color: #1b5e20;
}

.feedback-bar.error {
  background-color: #fddede;
  color: #b71c1c;
}
</style>