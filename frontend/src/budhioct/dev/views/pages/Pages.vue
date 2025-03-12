<script setup>
</script>

<template>
  <h1>Pages</h1>
  <router-link :to="{ name: 'home' }">Home</router-link> &nbsp; &nbsp; &nbsp;
  <router-link :to="{ name: 'warung' }">Warung</router-link>
  <br><br>
  <!-- page content -->
  <router-view></router-view>
  <!-- /page content -->
</template>

<style scoped>

</style>