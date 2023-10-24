import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'ant-design-vue/dist/reset.css';
import Antd from 'ant-design-vue';
import axios from "axios";

axios.defaults.baseURL = import.meta.env.VITE_BASE_URL
const app = createApp(App)
app.use(router)
app.use(Antd)
app.mount('#app')
