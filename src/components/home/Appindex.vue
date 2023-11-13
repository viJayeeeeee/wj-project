<template>
  <div>
    Hello World!
    <button @click="getNowTime">getTime</button>
  </div>
</template>

<script>
export default {
  name: 'AppIndex',
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
  }
}
</script>

<style scoped>

</style>
