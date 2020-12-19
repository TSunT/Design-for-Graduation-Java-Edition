import {getRequest} from "./api";

export const initMenu = (router, store) => {
  if (store.state.routes.length > 0) {
    return;
  }
  getRequest('/menu/navigate').then(data => {
    if (data) {
      let fmtRoutes = formatRoutes(data);
      console.log(fmtRoutes);
      router.addRoutes(fmtRoutes);
      store.commit('initRoutes', fmtRoutes);
    }
  })
}

export const formatRoutes = (routes) => {
  const fmRoutes = [];
  routes.forEach(router => {
    let {
      path,
      component,
      name,
      iconCls,
      children
    } = router;
    // let childrenarry =[];
    // if (children && children instanceof Array) {
    //   childrenarry.push(formatRoutes(children))
    // }
    if (children && children instanceof Array) {
      children = formatRoutes(children);
    }
    if(!(path === null && path.length === 0)){
      let fmRouter = {
        path: path,
        name: name,
        iconCls: iconCls,
        children: children,
        component: (resolve)=>{
          if (component.startsWith("Home")) {
            require(['../components/' + component + '.vue'], resolve);
          } else if (component.startsWith("Admin")) {
            require(['../views/Admin/' + component + '.vue'], resolve);
          }
        }
      }
      if (fmRouter.path===''){
        alert("fmroutes path null: "+fmRoutes.length+" route: "+fmRouter.path+" child: "+fmRouter.children);
      }else {
        fmRoutes.push(fmRouter);
      }
      console.log("fmroutes len: "+fmRoutes.length+" route: "+fmRouter.path+" child: "+fmRouter.children);
    }
  })
  return fmRoutes;
}
