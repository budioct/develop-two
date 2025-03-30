<script setup>
import {ref, computed, onMounted, onUnmounted} from 'vue';
import {useRoute, useRouter} from "vue-router";
import {detailSubAgent} from '../../../services/apiService.js';
import {useNotification} from "../../../constants/notifications.js"

const router = useRouter();
const route = useRoute();
const subAgentId = route.params.id;
const subAgentDetail = ref({
  id: null,
  subAgentName: '',
  address: '',
  stock: {
    id: null,
    ownership: '',
    stock_amount: null,
  },
  officialAgent: {
    id: null,
    agentName: '',
    address: '',
    stock_owner_id: null,
    stock_amount_gas: null,
    subholdingGroupAffiliate: '',
  },
  createdAt: '',
  updatedAt: ''
});
const isMobile = computed(() => window.innerWidth <= 768);
// const totalStock = computed(() =>
//     subAgentDetail.value.subAgentName.reduce((total, data) => total + data.stock_amount_gas, 0)
// );
const isLoading = ref(true);
const isModalOpen = ref(false);
const modalMode = ref("distribute");
const selectedSubAgentDetail = ref(null);

function setSubAgentDetail(data) {
  subAgentDetail.value = {
    id: data.id,
    subAgentName: data.subAgentName,
    address: data.address,
    stock: {
      id: data.stock.id,
      ownership: data.stock.ownership,
      stock_amount: data.stock.stock_amount
    },
    officialAgent: {
      id: data.officialAgent.id,
      agentName: data.officialAgent.agentName,
      address: data.officialAgent.address,
      stock_owner_id: data.officialAgent.stock_owner_id,
      stock_amount_gas: data.officialAgent.stock_amount_gas,
      subholdingGroupAffiliate: data.officialAgent.subholdingGroupAffiliate,
    },
    createdAt: data.createdAt,
    updatedAt: data.updatedAt
  }
}

async function fetchSubAgentDetail() {
  try {
    isLoading.value = true;
    const response = await detailSubAgent(subAgentId);
    if (response.status === 200) {
      setSubAgentDetail(response.data.data);
    } else {
      useNotification.error("Error", "Gagal mengambil data stakeholder.");
    }
  } catch (error) {
    console.error('Error fetching stakeholder data:', error);
    useNotification.error("Error", "Terjadi kesalahan dalam mengambil data.");
  } finally {
    isLoading.value = false;
  }
}

onMounted(async () => {
  await fetchSubAgentDetail();
});

async function goTo(id) {
  await router.push({name: 'offagent-detail', params: {id: id}});
}

</script>

<template>
  <div class="container py-5">
    <header class="mb-1 border-bottom">
      <div>
        <span class="fs-4">Sub Agents Detail</span>
      </div>
    </header>

    <div class="row align-items-start">
      <div class="col">
        <!-- Info Official Agents -->
        <div class="card p-1 mb-1 mt-1 bg-light">
          <h5 class="fw-bold">{{ subAgentDetail.subAgentName }}</h5>
          <p><strong>Address:</strong> {{ subAgentDetail.officialAgent.address }}</p>
          <div class="row">
            <div class="col">
              <p><strong>Sub Agent:</strong> {{ subAgentDetail.officialAgent.agentName }}</p>
            </div>
            <div class="col" v-if="subAgentDetail.stock?.stock_amount <= 5 || subAgentDetail.stock?.stock_amount <= 12">
              <p><button class="btn btn-outline-primary btn-sm" @click="goTo(subAgentDetail.officialAgent.id)">Request Re-Stock</button></p>
            </div>
          </div>
          <template v-if="subAgentDetail.stock?.stock_amount > 0 && subAgentDetail.stock?.stock_amount <= 8">
            <p><strong>Main Stock:</strong>&nbsp;
              <span class="badge text-bg-danger">{{ subAgentDetail.stock.stock_amount }}</span> ({{ subAgentDetail.stock.ownership ?? 'Unknown' }})
            </p>
          </template>
          <template v-else-if="subAgentDetail.stock?.stock_amount >= 9 && subAgentDetail.stock?.stock_amount <= 13">
            <p><strong>Main Stock:</strong>&nbsp;
              <span class="badge text-bg-warning">{{ subAgentDetail.stock.stock_amount }}</span> ({{ subAgentDetail.stock.ownership ?? 'Unknown' }})
            </p>
          </template>
          <template v-else>
            <p><strong>Main Stock:</strong> {{ subAgentDetail.stock.stock_amount }} ({{ subAgentDetail.stock.ownership ?? 'Unknown' }})</p>
          </template>
        </div>
      </div>
      <div class="col">
        <!-- Info Affiliate -->
        <div class="card p-1 mb-1 mt-1 bg-light">
          <h5 class="fw-bold">Info Sales</h5>
          <p><strong>List Total Sales:</strong> {{ "0" }} </p>
          <p><strong>Total Stock Gas of Sales:</strong> {{ "0" }}</p>
          <p><strong>Total Amount of Sales Money:</strong> {{ "0" }}</p>
          <p><strong>Profit of Sales:</strong> {{ "0" }}</p>
        </div>
      </div>
    </div>

    <!-- Loader -->
    <div v-if="isLoading" class="d-flex justify-content-center my-4">
      <div class="spinner-border text-success" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

  </div>
</template>

<style scoped>
.py-5 {
  padding-top: 5px !important;
  padding-bottom: 2px !important;
}

.table-responsive {
  overflow-x: auto;
}

.card {
  border-radius: 8px;
}

p {
  margin-bottom: 0 !important;
}
</style>