import {createRouter, createWebHistory} from 'vue-router'
import Cookies from "js-cookie"
import type {RouteLocationNormalized, NavigationGuardNext} from 'vue-router'

const verifyLogin = (to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
    if (!isLoggedIn()) {
        next({
            path: '/login',
            query: {redirect: to.fullPath}
        })
    } else {
        next()
    }
}

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/login",
            name: "login",
            component: () => import('../views/Login.vue')
        },
        {
            path: "/register",
            name: "register",
            component: () => import('../views/Register.vue')
        },
        {
            path: "/",
            name: "app",
            component: () => import('../views/Main.vue'),
            beforeEnter: verifyLogin,
            children: [
                {
                    path: "/",
                    redirect: "/index"
                },
                {
                    path: "/index",
                    name: "首页",
                    component: () => import('../views/Index.vue')
                },
                {
                    path: "/emp-manager",
                    name: "员工管理",
                    component: () => import('../views/EmpView.vue')
                },
                {
                    path: "/dept-manager",
                    name: "部门管理",
                    component: () => import('../views/DeptView.vue')
                },
                {
                    path: "/sal-manager",
                    name: "工资管理",
                    component: () => import('../views/SalView.vue')
                },
            ]
        },
    ]
})


const isLoggedIn = (): boolean => {
    let loggedIn = Cookies.get("isLoggedIn");
    if (loggedIn === undefined || loggedIn === "false") {
        return false
    } else {
        return true
    }

};

export default router
