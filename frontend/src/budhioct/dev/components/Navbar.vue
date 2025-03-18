<script setup>
import {computed, onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "../stores/authStore.js";
import {logout} from "../services/apiService.js";
import {useNotification} from "../constants/notifications.js";

const router = useRouter();
const authStore = useAuthStore();
const isAuthenticated = computed(() => authStore.isAuthenticated);


async function logoutClick() {
  try {
    await logout();
  } catch (error) {
    console.error("Headers:", error.response?.headers);
    console.error("Logout failed:", error.response?.data?.message || error.message);
  } finally {
    authStore.clearToken();
    await router.push({name: "login"});
    useNotification.success("Success", "Berhasil melakukan logout.");
  }
}

</script>

<template>
  <div class="container">
    <div class="row">
      <nav class="navbar navbar-expand-lg navbar-light bg-light rounded w-100">
        <div class="container-fluid">
          <!-- Brand or Logo -->
          <div v-if="isAuthenticated">
            <router-link :to="{ name: 'lobby' }" class="navbar-brand">Lobby</router-link>
          </div>
          <!-- Toggle button for mobile view -->
          <button
              class="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarResponsive"
              aria-controls="navbarResponsive"
              aria-expanded="false"
              aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <!-- Navbar links -->
          <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item" v-if="isAuthenticated">
                <router-link :to="{ name: 'home' }" class="nav-link">Home</router-link>
              </li>
              <li class="nav-item" v-if="isAuthenticated">
                <router-link :to="{ name: 'warung' }" class="nav-link">Warung</router-link>
              </li>
            </ul>

            <!-- Right-aligned items -->
            <div class="d-flex align-items-center">
              <div class="nav-item" v-if="isAuthenticated">
                <button @click="logoutClick" type="button" class="btn btn-outline-danger btn-sm">Logout</button>
              </div>
            </div>
          </div>
        </div>
      </nav>
    </div>
  </div>
</template>

<style scoped>

</style>