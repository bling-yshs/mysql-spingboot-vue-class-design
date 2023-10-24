<template>
  <div id="loginContainer">
    <div id="loginMain">
      <div class="title">
        账号注册
      </div>
      <a-form
          :model="formState"
          name="normal_register"
          class="login-form"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
      >
        <a-form-item
            name="username"
            :rules="[{ required: true, message: '请输入用户名' } ]"
        >
          <a-input size="large" v-model:value="formState.username" placeholder="用户名" allowClear>
            <template #prefix>
              <UserOutlined class="site-form-item-icon"/>
            </template>
          </a-input>
        </a-form-item>
        
        <a-form-item
            name="password"
            :rules="[{ required: true, message: '请输入密码' },{
              validator: confirmPasswordValidator
            }]"
        >
          <a-input-password size="large" v-model:value="formState.password" placeholder="密码" allowClear>
            <template #prefix>
              <LockOutlined class="site-form-item-icon"/>
            </template>
          </a-input-password>
        </a-form-item>
        
        <a-form-item
            name="confirmPassword"
            :rules="[{validator: confirmPasswordValidator}]"
        >
          <a-input-password size="large" v-model:value="formState.confirmPassword" placeholder="确认密码" allowClear>
            <template #prefix>
              <LockOutlined class="site-form-item-icon"/>
            </template>
          </a-input-password>
        </a-form-item>
        
        <a-form-item>
          <a-button :disabled="disabled" type="primary" html-type="submit" class="login-form-button"
                    style="margin-right: 50px">
            注册
          </a-button>
          已有账号？
          <a-button type="default" @click="()=>{router.push('/login')}">点击登录</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive, computed} from 'vue';
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue';
import axios from "axios";
import {useRouter} from 'vue-router';
import {message} from "ant-design-vue";
import type {Rule} from 'ant-design-vue/es/form';


const confirmPasswordValidator = async (_rule: Rule, value: string) => {
  if (value === '') {
    return Promise.reject('请再次输入密码');
  } else if (value !== formState.password) {
    return Promise.reject("两次输入的密码不一致");
  } else {
    return Promise.resolve();
  }
};


interface FormState {
  username: string;
  password: string;
  confirmPassword: string;
}

const formState = reactive<FormState>({
  username: '',
  password: '',
  confirmPassword: '',
});

const router = useRouter();

const onFinish = (values: any) => {
  axios.post("/user/register", values).then(response => {
    if (response.data.code === 200) {
      router.push("/login")
    } else {
      console.log(response.data.message)
      message.error(response.data.message)
    }
  })
};

const onFinishFailed = (errorInfo: any) => {
  console.log('Failed:', errorInfo);
};

const disabled = computed(() => {
  return !(formState.username && formState.password && formState.confirmPassword && (formState.password === formState.confirmPassword));
});
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