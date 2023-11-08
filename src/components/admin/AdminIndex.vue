<script>
import AdminMenu from '@/components/admin/AdminMenu.vue'
import Header from '@/components/admin/Header.vue'

export default {
  name: 'AdminIndex',
  components: {AdminMenu, Header},
  data() {
    return {
      dialogVisible: false
    }
  },
  mounted() {
    console.log('nothing on Page:AdminIndex')
  }
}
</script>

<template>
  <el-container id="admin-body">
    <el-header style="z-index: 1;height: 80px;margin-left: -20px">
      <Header style="position: absolute;width: 98%;"></Header>
    </el-header>
    <!--<el-row style="height: 100%;">-->
    <el-container>
      <el-aside style="height: 112%;margin-top: -80px;width: 250px">
        <admin-menu></admin-menu>
      </el-aside>
      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
#admin-body {
  height: 95vh;
}
</style>
