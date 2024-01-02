<template>
  <div>
    <left-menu class="left-menu"></left-menu>
    <div id="jsi-flying-fish-container" class="container"></div>
  </div>
</template>

<script>
import LeftMenu from '@/components/home/LeftMenu'
import RENDERER from '@/components/home/renderer'

export default {
  name: 'AppIndex',
  components: {LeftMenu},
  methods: {
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
  },
  mounted() {
    RENDERER.init()
  }
}
</script>

<style scoped>
.left-menu {
  margin-top: 20px;
}

.container {
  width: 100%;
  height: 22%;
  position: fixed;
  z-index: -1;
  opacity: 0.37;
  bottom: 0;
  left: 0;
  background: #409eff;
}
</style>
