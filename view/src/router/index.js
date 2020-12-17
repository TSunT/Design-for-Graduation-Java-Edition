import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/Login";
import AdminHome from "../views/Admin/AdminHome";
import Home from "../components/Home";
import Main from "../components/Main";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },{
      path: '/home',
      name: 'Home',
      component: Home
    }
  ]
})
