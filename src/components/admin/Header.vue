<script>
import {resetRouter} from '@/router'

export default {
  name: 'Header',
  methods: {
    logout() {
      const _this = this
      this.$axios.get('/logout').then(resp => {
        if (resp && resp.data.code === 200) {
          console.log('Header.vue: exit')
          _this.$store.commit('logout')
          _this.$router.replace('/login')
          // 路由清空
          resetRouter()
        }
      }).catch(failResponse => {
      })
    }
  }
}
</script>

<template>
  <el-card class="admin-header">
    <a href="/index">
      <img src="../../assets/icon2.png" alt="" width="615" style="float: left;margin-top: -5px;">
    </a>
    <span style="font-size: 32px;font-weight: bold;position:absolute;left: 100px">白  卷</span>
    <i class="el-icon-switch-button" v-on:click="logout" style="font-size: 40px;float: right"></i>
  </el-card>
</template>

<style>
.admin-header {
  height: 80px;
  opacity: 0.85;
  line-height: 40px;
  min-width: 900px;
}

.el-icon-switch-button {
  cursor: pointer;
  outline: 0;
}
</style>
