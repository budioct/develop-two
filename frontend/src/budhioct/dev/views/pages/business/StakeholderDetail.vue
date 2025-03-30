<script setup>
import {ref, computed, onMounted, onUnmounted} from 'vue';
import {useRoute} from "vue-router";
import {detailStakeholder, distribute} from '../../../services/apiService.js';
import StakeholderModal from "../../../components/modal/StakeholderModal.vue";
import {useNotification} from "../../../constants/notifications.js";

const route = useRoute();
const stackholderId = route.params.id;
const stakeholderDetail = ref({
  id: null,
  subholdingGroupAffiliate: '',
  address: '',
  contact: '',
  stock: {
    id: null,
    ownership: '',
    stock_amount: null,
  },
  officialAgents: [],
  createdAt: '',
  updatedAt: ''
});
const isMobile = computed(() => window.innerWidth <= 768);
const totalStock = computed(() =>
    stakeholderDetail.value.officialAgents.reduce((total, data) => total + data.stock_amount_gas, 0)
);
const isLoading = ref(true);
const isModalOpen = ref(false);
const modalMode = ref("distribute");
const selectedStakeholderDetail = ref(null);

function setStakeholderDetail(data) {
  //totalStock.value = data.officialAgents.reduce((total, data) => total + data.stock_amount_gas, 0);
  stakeholderDetail.value = {
    no: data.no,
    id: data.id,
    subholdingGroupAffiliate: data.subholdingGroupAffiliate,
    address: data.address,
    contact: data.contact,
    stock: {
      id: data.stock.id,
      ownership: data.stock.ownership,
      stock_amount: data.stock.stock_amount
    },
    officialAgents: data.officialAgents.map((agent, index) => ({
      no: index + 1,
      id: agent.id,
      agentName: agent.agentName,
      address: agent.address,
      stock_owner_id: agent.stock_owner_id,
      stock_amount_gas: agent.stock_amount_gas
    })),
    createdAt: data.createdAt,
    updatedAt: data.updatedAt
  };
}

async function fetchStakeholderDetail() {
  try {
    isLoading.value = true;
    const response = await detailStakeholder(stackholderId);
    if (response.status === 200) {
      setStakeholderDetail(response.data.data);
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

async function openModal(mode, agent = null) {
  modalMode.value = mode;
  selectedStakeholderDetail.value = agent ? { ...agent } : null;
  await fetchStakeholderDetail();
  isModalOpen.value = true;
}

async function handleSave(data) {
  try {
    const payload = {
      sourceStockId: stakeholderDetail.value.id,
      targetStockId: data.targetStockId,
      amount: data.amount,
    };
    if (modalMode.value === 'distribute') {
      const response = await distribute(payload.sourceStockId, payload.targetStockId, payload.amount);
      if (response.status === 200) {
        await fetchStakeholderDetail();
        useNotification.success("Success", "Distribusi Berhasil.");
      }
    }
    isModalOpen.value = false;
  } catch (error) {
    console.error('Error saving users:', error);
    useNotification.error("Error", "Terjadi kesalahan saat menyimpan data.");
  }
}

function updateIsMobile() {
  isMobile.value = window.innerWidth <= 768;
}

onMounted(async () => {
  await fetchStakeholderDetail();
  window.addEventListener("resize", updateIsMobile);
});

onUnmounted(() => {
  window.removeEventListener("resize", updateIsMobile);
});

</script>

<template>
  <div class="container py-5">
    <header class="mb-1 border-bottom">
      <div>
        <span class="fs-4">Stakeholder Detail</span>
      </div>
    </header>

    <div v-if="stakeholderDetail.stock?.stock_amount === 0" class="mt-2">
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>SUBHOLDING!</strong> provider out of stock.
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    </div>

    <div class="row align-items-start">
      <div class="col">
        <!-- Info Stakeholder -->
        <div class="card p-1 mb-1 mt-1 bg-light">
          <h5 class="fw-bold">{{ stakeholderDetail.subholdingGroupAffiliate }}</h5>
          <p><strong>Address:</strong> {{ stakeholderDetail.address }}</p>
          <p><strong>Contact:</strong> {{ stakeholderDetail.contact }}</p>
          <template v-if="stakeholderDetail.stock?.stock_amount > -1 && stakeholderDetail.stock?.stock_amount <= 5000">
            <p><strong>Main Stock:</strong>&nbsp;
              <span class="badge text-bg-danger">{{ stakeholderDetail.stock?.stock_amount ?? 0 }}</span>
              ({{ stakeholderDetail.stock?.ownership ?? 'Unknown' }})
            </p>
          </template>
          <template v-else-if="stakeholderDetail.stock?.stock_amount >= 5001 && stakeholderDetail.stock?.stock_amount <= 10000">
            <p><strong>Main Stock:</strong>&nbsp;
              <span class="badge text-bg-warning">{{ stakeholderDetail.stock?.stock_amount ?? 0 }}</span>
              ({{ stakeholderDetail.stock?.ownership ?? 'Unknown' }})
            </p>
          </template>
          <template v-else>
            <p><strong>Main Stock:</strong> {{ stakeholderDetail.stock?.stock_amount ?? 0 }} ({{ stakeholderDetail.stock?.ownership ?? 'Unknown' }})</p>
          </template>
        </div>
      </div>
      <div class="col">
        <!-- Info Affiliate -->
        <div class="card p-1 mb-1 mt-1 bg-light">
          <h5 class="fw-bold">Affiliate Subholding</h5>
          <p><strong>List Total Affiliate:</strong> {{ stakeholderDetail.officialAgents.length }} </p>
          <p><strong>Total Stock Gas Affiliate:</strong> {{ totalStock }}</p>
        </div>
      </div>
    </div>

    <!-- Loader -->
    <div v-if="isLoading" class="d-flex justify-content-center my-4">
      <div class="spinner-border text-success" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- Mode Desktop (Tabel) -->
    <div v-if="!isMobile">
      <header class="mb-1 mt-3 border-bottom">
        <div>
          <span class="fs-5">Official Agents</span>
        </div>
      </header>
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>#</th>
            <th>Agent Name</th>
            <th>Address</th>
            <th>Stock Gas</th>
            <th v-if="stakeholderDetail.stock?.stock_amount !== 0">Action</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="agent in stakeholderDetail.officialAgents" :key="agent.id">
            <td>{{ agent.no }}</td>
            <td>{{ agent.agentName }}</td>
            <td>{{ agent.address }}</td>
            <td>{{ agent.stock_amount_gas }}</td>
            <td v-if="stakeholderDetail.stock?.stock_amount !== 0">
              <button class="btn btn-outline-primary btn-sm" @click="openModal('distribute', agent)">Distribute</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Mode Mobile (Card/Grid) -->
    <div v-else>
      <header class="mb-1 mt-3 border-bottom">
        <div>
          <span class="fs-5">Official Agents</span>
        </div>
      </header>
      <div class="row">
        <div class="col-12 col-md-6 mb-3" v-for="agent in stakeholderDetail.officialAgents" :key="agent.id">
          <div class="card p-3 shadow-sm">
            <h6 class="fw-bold">{{ agent.agentName }}</h6>
            <p><strong>Address:</strong> {{ agent.address }}</p>
            <p><strong>Stock Gas:</strong> {{ agent.stock_amount_gas }}</p>
            <p v-if="stakeholderDetail.stock?.stock_amount !== 0"><button class="btn btn-outline-primary btn-sm" @click="openModal('distribute', agent)">Distribute</button></p>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <StakeholderModal
        :isVisible="isModalOpen"
        :mode="modalMode"
        :stakeholderData="selectedStakeholderDetail"
        :subholdingStock="stakeholderDetail.stock.stock_amount"
        @save="handleSave"
        @close="isModalOpen = false"
    />

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