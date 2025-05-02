<template>
  <el-card>
    <div slot="header">
      <span>用户管理</span>
      <el-button @click="fetchUsers" size="small" style="float: right;">刷新</el-button>
    </div>
    <el-table :data="users" style="width: 100%">
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="角色" prop="role" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button type="danger" size="small" @click="deleteUser(row.username)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import { ref } from "vue";
import { ElTable, ElTableColumn, ElButton, ElCard } from "element-plus";
import axios from "axios";

export default {
  components: { ElTable, ElTableColumn, ElButton, ElCard },
  setup() {
    const users = ref([]);

    
    const fetchUsers = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/admin/users");
        users.value = response.data;
      } catch (error) {
        console.error("获取用户失败:", error);
      }
    };

   
    const deleteUser = async (username) => {
      try {
        await axios.delete(`/api/admin/user/${username}`);
        fetchUsers();
      } catch (error) {
        console.error("删除用户失败:", error);
      }
    };

    
    fetchUsers();

    return { users, fetchUsers, deleteUser };
  }
};
</script>
