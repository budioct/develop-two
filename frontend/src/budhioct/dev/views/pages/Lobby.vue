<script setup>
import {useAuthStore} from "../../stores/authStore.js";

const authStore = useAuthStore();

const isLoggedIn = authStore.isAuthenticated;
const token = authStore.token;

</script>

<template>
  <div class="container py-5">
    <h1>Login Status</h1>
    <p v-if="isLoggedIn">You are logged in! 🎉</p>
    <p v-else>You are not logged in.</p>

    <div v-if="token">
      <h3>Your Token:</h3>
      <p>{{ token }}</p>
    </div>
  </div>
</template>

<style scoped>

</style>