import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/Login";
import Home from "../components/Home";
import Main from "../components/Main";

Vue.use(Router)

export const fixedRouter = [
  {
    path: '/',
    component: Login,
    name: 'Login'
  },
  {
    path: '/tologin',
    component: Login,
    name: 'Login'
  },
  {
    path: '',
    component: Home, //整体页面的布局(包含左侧菜单跟主内容区域)
    children: [{
      path: 'main',
      component: Main,
      meta: {
        title: '首页', //菜单名称
        roles: ['ADMIN', 'admin'], //当前菜单哪些角色可以看到
        icon: 'el-icon-info' //菜单左侧的icon图标
      }
    }]
  },
]

export default new Router({
  routes: fixedRouter
})
