<template>
  <el-card>
    <div slot="header">
      <span>快递管理</span>
      <el-button @click="fetchPackages" size="small" style="float: right;">刷新</el-button>
    </div>
    <el-table :data="packages" style="width: 100%">
      <el-table-column label="快递ID" prop="id" />
      <el-table-column label="快递公司" prop="courier" />
      <el-table-column label="取件码" prop="pickupCode" />
      <el-table-column label="寄件人" prop="sender" />
      <el-table-column label="收件人" prop="receiver" />
      <el-table-column label="站点" prop="station" />
      <el-table-column label="取件状态" :formatter="statusFormatter" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button
            type="primary"
            size="small"
            @click="updateStatus(row.id, !row.pickedUp)"
          >
            {{ row.pickedUp ? '标记未出库' : '标记已出库' }}
          </el-button>
          <el-button type="danger" size="small" @click="deletePackage(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import { ref } from "vue";
import { ElMessage } from "element-plus";
import axios from "axios";

const packages = ref([]);


const fetchPackages = async () => {
  try {
    const response = await axios.get("/api/admin/packages");
    console.log("Fetched packages:", response.data); 
    packages.value = response.data;
  } catch (error) {
    console.error("获取快递失败:", error);
    ElMessage.error("获取快递失败，请稍后重试");
  }
};


const updateStatus = async (id, pickedUp) => {
  try {
    const response = await axios.patch(`/api/admin/package/${id}/status`, { isPickedUp: pickedUp });
    const { type, message } = response.data;

    if (type === "success") {
      ElMessage.success(message || "状态更新成功");
      fetchPackages(); 
    } else {
      ElMessage.error(message || "状态更新失败");
    }
  } catch (error) {
    console.error("更新快递状态失败:", error);
    ElMessage.error("更新快递状态失败，请稍后重试");
  }
};


const deletePackage = async (id) => {
  try {
    const response = await axios.delete(`/api/admin/package/${id}`);
    const { type, message } = response.data;

    if (type === "success") {
      ElMessage.success(message || "删除成功");
      fetchPackages(); // 刷新数据
    } else {
      ElMessage.error(message || "删除失败");
    }
  } catch (error) {
    console.error("删除快递失败:", error);
    ElMessage.error("删除快递失败，请稍后重试");
  }
};

// 快递状态格式化
const statusFormatter = (row) => {
  return row.pickedUp ? "已出库" : "未出库"; // 使用 pickedUp 字段
};


fetchPackages();
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=El+Messiri:wght@700&display=swap");

* {
  margin: 0;
  padding: 0;
  font-family: "El Messiri", sans-serif;
}

.el-card {
  margin: 20px;
}

.el-button {
  margin-left: 5px;
}
</style>