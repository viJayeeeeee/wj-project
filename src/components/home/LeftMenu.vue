<script>
export default {
  name: 'LeftMenu',
  data() {
    return {
      editableTabsValue: '1',
      editableTabs: [{
        title: '最近消息',
        name: 'latestNews',
        content: '暂无'
      }, {
        title: '关于我们',
        name: 'aboutUs',
        content: '这是一个前后端分离项目，前端是基于vue+element-ui在的单页面项目（webpack），后端主框架为Spring boot，数据库所属MySQL。'
      }],
      tabIndex: 2
    }
  },
  methods: {
    beforeLeave(currentName, oldName) {
      let _this = this
      if (currentName === 'add') {
        _this.handleTabsEdit('add', 'add')
        return false
      } else {
        this.editableTabsValue = currentName
      }
    },
    handleTabsEdit(targetName, action) {
      if (action === 'add') {
        console.log('add page')
        let newTabName = ++this.tabIndex + ''
        this.editableTabs.push({
          title: '新页面' + this.tabIndex,
          name: newTabName,
          content: '页面' + this.tabIndex + ' ：' + '从数据库加载控件（未实现）'
        })
        this.editableTabsValue = newTabName
      }
      if (action === 'remove') {
        console.log('remove page!')
        let tabs = this.editableTabs
        let activeName = this.editableTabsValue
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1]
              if (nextTab) {
                activeName = nextTab.name
              }
            }
          })
        }
        this.editableTabsValue = activeName
        this.editableTabs = tabs.filter(tab => tab.name !== targetName)
      }
    }
  },
  mounted() {
    this.editableTabsValue = 'latestNews'
  }
}
</script>

<template>
  <div>
    <el-tabs v-model="editableTabsValue"
             type="border-car"
             @edit="handleTabsEdit"
             tab-position="left"
             :before-leave="beforeLeave"
             stretch
             style="margin: 1% 0.5%;font-size: 20px !important;">
      <el-tab-pane
        :key="item.name"
        v-for="(item) in editableTabs"
        :label="item.title"
        :name="item.name"
        closable
        style="margin: 2% 3%"
      >
        {{ item.content }}
      </el-tab-pane>
      <el-tab-pane key="add" name="add">
        <span slot="label" style="font-size:42px;font-weight:bold;color: deepskyblue">+</span>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<style>
</style>
