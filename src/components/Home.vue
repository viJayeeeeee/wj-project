<script>
import NavMenu from './common/NavMenu.vue'
export default {
  name: 'Home',
  components: {NavMenu}
}
</script>

<template>
  <div>
    <nav-menu></nav-menu>
    <router-view/>
  </div>
</template>

<style scoped>

</style>
