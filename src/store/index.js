import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    username: window.localStorage.getItem('username') == null ? '' : JSON.parse(window.localStorage.getItem('username' || '[]')),
    adminMenus: []
  },
  mutations: {
    login(state, data) {
      console.log('store->index.js:')
      console.log(data)
      state.username = data
      window.localStorage.setItem('username', JSON.stringify(data))
    },
    logout(state) {
      console.log('logout:bye bye!')
      state.username = ''
      window.localStorage.removeItem('username')
      state.adminMenus = []
    },
    initAdminMenu(state, menus) {
      state.adminMenus = menus
    }
  }
})
