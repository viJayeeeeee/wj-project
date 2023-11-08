// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import store from './store'

axios.defaults.baseURL = 'http://localhost:8443/api'
axios.defaults.withCredentials = true

// 全局注册，之后可在其他组件中通过 this.$axios 发送数据
Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(ElementUI)
/* eslint-disable no-new */
router.beforeEach((to, from, next) => {
  if (store.state.username && to.path.startsWith('/admin')) {
    console.log('main.js: first-beforeEach')
    console.log(store.state.username)
    initAdminMenu(router, store)
  }
  // if (store.state.username && to.path.startsWith('/login')) {
  //   next({
  //     path: 'admin/dashboard'
  //   })
  // }

  if (to.meta.requireAuth) {
    if (store.state.username) {
      axios.get('/authentication').then(resp => {
        if (resp) {
          next()
        }
      })
    } else {
      next({
        path: 'login',
        query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
})
const initAdminMenu = (router, store) => {
  if (store.state.adminMenus.length > 0) {
    return
  }
  axios.get('/menu').then(res => {
    if (res && res.status === 200) {
      console.log(res.data)
      // console.log(res.data.result)
      var fmtRoutes = formatRoutes(res.data)
      router.addRoutes(fmtRoutes)
      store.commit('initAdminMenu', fmtRoutes)
    }
  })
}

const formatRoutes = (routes) => {
  let fmtRoutes = []
  routes.forEach(route => {
    if (route.children) {
      route.children = formatRoutes(route.children)
    }
    let fmtRoute = {
      path: route.path,
      component: resolve => {
        require(['./components/admin/' + route.component + '.vue'], resolve)
      },
      name: route.name,
      nameZh: route.nameZh,
      iconCls: route.iconCls,
      meta: {
        requireAuth: true
      },
      children: route.children
    }
    fmtRoutes.push(fmtRoute)
  })
  return fmtRoutes
}

new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
