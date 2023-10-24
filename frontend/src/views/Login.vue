<template>
  <div id="loginContainer">
    <div id="loginMain">
      <div class="title">
        企业员工管理系统
      </div>
      <a-form
          :model="formState"
          name="normal_login"
          class="login-form"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
      >
        <a-form-item
            name="username"
            :rules="[{ required: true, message: '请输入用户名' }]"
        >
          <a-input size="large" v-model:value="formState.username" placeholder="用户名" allowClear>
            <template #prefix>
              <UserOutlined class="site-form-item-icon"/>
            </template>
          </a-input>
        </a-form-item>
        
        <a-form-item
            name="password"
            :rules="[{ required: true, message: '请输入密码' }]"
        >
          <a-input-password size="large" v-model:value="formState.password" placeholder="密码" allowClear>
            <template #prefix>
              <LockOutlined class="site-form-item-icon"/>
            </template>
          </a-input-password>
        </a-form-item>
        
        <a-form-item>
          <a-form-item name="remember" no-style>
            <a-checkbox v-model:checked="formState.remember">自动登录</a-checkbox>
          </a-form-item>
        </a-form-item>
        
        <a-form-item>
          <a-button type="primary" html-type="submit" class="login-form-button"
                    style="margin-right: 50px">
            登录
          </a-button>
          没有账号？
          <a-button type="default" @click="()=>{router.push('/register')}">点击注册</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>
<script lang="ts" setup>
import {reactive, computed} from 'vue';
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue';
import axios from "axios";
import Cookies from "js-cookie"
import {message} from 'ant-design-vue';
import {useRouter} from 'vue-router';

interface FormState {
  username: string;
  password: string;
  remember: boolean;
}

const formState = reactive<FormState>({
  username: '',
  password: '',
  remember: true,
});


const router = useRouter();

const onFinish = (values: any) => {
  axios.post("/user/login", values).then(response => {
    if (response.data.code === 200) {
      Cookies.set("isLoggedIn", "true")
      router.push("/index")
    } else {
      message.error(response.data.message)
    }
  })
};

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};
</script>
<style scoped>
#loginContainer {
  height: 100vh;
  background-color: #f0f2f5;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background-image: url("../assets/background01.svg");
  background-size: 100% 100%;
}

#loginMain {
  margin-top: 28vh;
}

.title {
  font-weight: bold;
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
</style>