# 📦 智能快递驿站管理系统（仿菜鸟驿站）

 本项目是一个模拟 **菜鸟驿站快递管理系统** 的全栈应用，涵盖前端用户操作界面、扫码出库系统、后端身份验证及包裹管理逻辑。  
 This project was developed as part of the graduation design for YunCheng Vocational And Technical University.
 
 New Version with SQL https://drive.google.com/file/d/1majUzWMYt7BkFPmd4wDAzZbL-W9qmU40/view?usp=sharing
## 🧩 技术栈

### 前端
- **Vue 3** + **Vite**
- **Element Plus**：UI 组件库
- **html5-qrcode**：摄像头扫码识别


### 后端
- **Java 24**
- **Spring Boot**
- 原生数组模拟数据库，无持久化
- 简单身份验证机制

---



## 🖥️ 前端功能概览

### 1. 🏠 主界面
- 模仿菜鸟驿站风格布局
- 用户登录/注册功能
- 包裹到件、出库、查询模块界面
![主界面截图](image15.png)
### 2. 📷 快递站出库机器
- 识别快递单上的：
  - **身份码（SFM 开头）**
  - **快递条形码（如 YD12345678）**
- 实时识别结果展示，异常信息红色提示
- 后端验证身份和包裹信息

---

## 🧪 后端功能概览
- 用户与快递的增删改查
- 身份码 + 快递码双重校验
- 检查是否为当前用户领取自己的包裹
- 成功后更新内存包裹状态并返回结果
- 提供 api 接口供前端调用

---
