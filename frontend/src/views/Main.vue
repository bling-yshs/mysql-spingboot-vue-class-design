<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider v-model:collapsed="collapsed" collapsible collapsedWidth="80" breakpoint="lg"
    >
      <a-menu :selectedKeys="[$route.path]" theme="dark" mode="inline">
        <a-menu-item key="/emp-manager">
          <pie-chart-outlined/>
          <span>员工管理</span>
          <RouterLink to="/emp-manager"/>
        </a-menu-item>
        <a-menu-item key="/dept-manager">
          <desktop-outlined/>
          <span>部门管理</span>
          <RouterLink to="/dept-manager"/>
        </a-menu-item>
<!--        <a-menu-item key="/sal-manager">-->
<!--          <desktop-outlined/>-->
<!--          <span>工资管理</span>-->
<!--          <RouterLink to="/sal-manager"/>-->
<!--        </a-menu-item>-->
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-content style="margin: 1vh 2vh 0 2vh">
        <a-breadcrumb class="breadcrumb">
          <a-breadcrumb-item>
            <RouterLink to="/">
              <home-outlined/>
            </RouterLink>
          </a-breadcrumb-item>
          <a-breadcrumb-item v-if="$route.name!='首页'">{{ $route.name }}</a-breadcrumb-item>
        </a-breadcrumb>
        <div :style="{ padding: '2vh', background: '#fff', minHeight: '85vh' , borderRadius:'1rem'}">
          <RouterView/>
        </div>
      </a-layout-content>
      <a-layout-footer style="text-align: center">
        Made by Zhuang Jingyu
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {
  PieChartOutlined,
  DesktopOutlined,
} from '@ant-design/icons-vue';
import {ref} from 'vue';

const onBreakpoint = (broken: boolean) => {
  console.log(broken)
}

const collapsed = ref<boolean>(false);

import {HomeOutlined} from "@ant-design/icons-vue";

function f() {
  console.log("cicke")
}
</script>
<style scoped>

.ant-layout-footer {
  padding: 2vh;
}

.breadcrumb {
  padding: 0.5rem;
  border-radius: 0.5rem;
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}

.acc {
  border-radius: 1rem;
}
</style>
