import {getRequest} from "./api";

export const initMenu = (router, store) => {
  if (store.state.routes.length > 0) {
    return;
  }
  getRequest('/system/menu').then(data => {
    if (data) {
      let fmtRoutes = formatRoutes(data); //对后端传来的路由格式化
      router.addRoutes(fmtRoutes);
      store.commit('initRoutes', fmtRoutes);
      console.log(fmtRoutes);
    }
  })
}
export const formatRoutes = (routes) => {
  let fmRoutes = [];
  routes.forEach(router => {
    let {
      path,
      component,
      name,
      iconCls,
      children
    } = router;
    if (children && children instanceof Array) {
      children = formatRoutes(children);
    }
    let fmRouter = {
      path: path,
      name: name,
      iconCls: iconCls,
      children: children,
      component(resolve) {
        if (component.startsWith("Home")) {
          require(['../views/' + component + '.vue'], resolve);
        } else if (component.startsWith("Admin")) {
          require(['../views/Admin/' + component + '.vue'], resolve);
        }
      }
    }
    fmRoutes.push(fmRouter);
  })
  return fmRoutes;
}
