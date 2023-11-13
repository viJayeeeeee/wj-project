<script>
import SideMenu from './SideMenu'
import Books from './Books'

export default {
  name: 'AppLibrary',
  components: {SideMenu, Books},
  data() {
    return {
      bodyLoading: false
    }
  },
  methods: {
    listByCategory() {
      this.bodyLoading = true
      var _this = this
      setTimeout(() => {
        var cid = this.$refs.sideMenu.cid
        var url = 'categories/' + cid + '/books'
        this.$axios.get(url).then(resp => {
          if (resp && resp.status === 200) {
            _this.$refs.booksArea.books = resp.data
          }
        })
        _this.bodyLoading = false
      }, 1500)
    }
  }
}
</script>

<template>
  <el-container>
    <el-aside style="width: 200px;margin-top: 20px">
      <switch></switch>
      <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main>
      <books class="books-area" ref="booksArea" v-loading.body="bodyLoading"></books>
    </el-main>
  </el-container>
</template>

<style scoped>
.books-area {
  width: 990px;
  margin-left: auto;
  margin-right: auto;
}
</style>
