<template>
  <div>
    <el-row style="margin: 18px 0 0 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard'}">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/content/book'}">内容管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/content/article'}">文章管理</el-breadcrumb-item>
        <el-breadcrumb-item>编辑器</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-row>
      <el-input
        v-model="article.articleTitle"
        style="margin: 10px 0;font-size: 18px;"
        placeholder="请输入标题"></el-input>
    </el-row>
    <el-row style="height: calc(100vh - 140px);">
      <mavon-editor
        v-model="article.articleContentMd"
        style="height: 100%;"
        ref=md
        @save="saveArticles"
        fontSize="26px">
        <button type="button" class="op-icon el-icon-document" :title="'摘要/封面'" slot="left-toolbar-after"
                @click="dialogVisible = true"></button>
      </mavon-editor>
      <el-dialog
        :visible.sync="dialogVisible"
        width="30%">
        <el-divider content-position="left">摘要</el-divider>
        <el-input
          type="textarea"
          v-model="article.articleAbstract"
          rows="6"
          maxlength="255"
          show-word-limit></el-input>
        <el-divider content-position="left">封面</el-divider>
        <div style="margin-top: 20px">
          <el-input v-model="article.articleCover" autocomplete="off" placeholder="图片 URL"></el-input>
          <img-upload @onUpload="uploadImg" ref="imgUpload" style="margin-top: 5px"></img-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
    </el-row>
  </div>
</template>

<script>
import ImgUpload from '../../library/ImgUpload'

export default {
  name: 'Editor',
  components: {ImgUpload},
  data() {
    return {
      article: {},
      dialogVisible: false
    }
  },
  mounted() {
    if (this.$route.params.article) {
      this.article = this.$route.params.article
    }
  },
  methods: {
    saveArticles(value, render) {
      // value 是 md，render 是 html
      // console.log(value === '')
      if (render === '') {
        this.$message({
          type: 'warning',
          message: '正文不能为空！'
        })
        return 0
      }
      this.$confirm('是否保存并发布文章?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          this.$axios
            .put('/admin/content/article', {
              id: this.article.id,
              articleTitle: this.article.articleTitle,
              articleContentMd: value,
              articleContentHtml: render,
              articleAbstract: this.article.articleAbstract,
              articleCover: this.article.articleCover,
              articleDate: this.getNowTime()
            }).then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'info',
                message: '已保存成功'
              })
            }
          })
        }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消发布'
        })
      })
    },
    uploadImg() {
      this.article.articleCover = this.$refs.imgUpload.url
    },
    getNowTime() {
      const nowDate = new Date()
      const yy = nowDate.getFullYear()
      const mm = nowDate.getMonth() + 1 < 10 ? '0' + nowDate.getMonth() + 1 : nowDate.getMonth() + 1
      const dd = nowDate.getDay() < 10 ? '0' + nowDate.getDay() : nowDate.getDay()
      const HH = nowDate.getHours() < 10 ? '0' + nowDate.getHours() : nowDate.getHours()
      const MM = nowDate.getMinutes() < 10 ? '0' + nowDate.getMinutes() : nowDate.getMinutes()
      const ss = nowDate.getSeconds() < 10 ? '0' + nowDate.getSeconds() : nowDate.getSeconds()
      return yy + '-' + mm + '-' + dd + ' ' + HH + '-' + MM + '-' + ss
    }
  }
}
</script>
