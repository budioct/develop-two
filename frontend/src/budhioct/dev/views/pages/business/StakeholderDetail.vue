<script setup>
import {onMounted, ref, computed} from 'vue';
import {useRoute, useRouter} from "vue-router";
import {detailStakeholder} from '../../../services/apiService.js';

const route = useRoute();
const router = useRouter();

const stackholderId = route.params.id;
const stakeholderDetail = ref({
  id: null,
  subholdingGroupAffiliate: '',
  address: '',
  contact: '',
  stock: {
    id: null,
    ownership: '',
    stock_amount: 0
  },
  officialAgents: [],
  createdAt: '',
  updatedAt: ''
});
const isMobile = computed(() => window.innerWidth <= 768);

function setStakeholderDetail(data) {
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
    const response = await detailStakeholder(stackholderId);
    if (response.status === 200) {
      setStakeholderDetail(response.data.data);
    }
  } catch (error) {
    console.error('Error fetching stakeholder data:', error);
  }
}

onMounted(async () => {
  await fetchStakeholderDetail();
});

</script>

<template>
  <div class="container py-5">
    <header class="mb-1 border-bottom">
      <div>
        <span class="fs-4">Stakeholder Detail</span>
      </div>
    </header>

    <!-- Info Stakeholder -->
    <div class="card p-3 mb-4 mt-3 bg-light">
      <h4 class="fw-bold">{{ stakeholderDetail.subholdingGroupAffiliate }}</h4>
      <p><strong>Address:</strong> {{ stakeholderDetail.address }}</p>
      <p><strong>Contact:</strong> {{ stakeholderDetail.contact }}</p>
      <p><strong>Main Stock:</strong> {{ stakeholderDetail.stock?.stock_amount ?? 0 }} ({{ stakeholderDetail.stock?.ownership ?? 'Unknown' }})</p>

    </div>

    <!-- Mode Desktop (Tabel) -->
    <div v-if="!isMobile">
      <h5 class="mb-3">Official Agents</h5>
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>#</th>
            <th>Agent Name</th>
            <th>Address</th>
            <th>Stock Gas</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="agent in stakeholderDetail.officialAgents" :key="agent.id">
            <td>{{ agent.no }}</td>
            <td>{{ agent.agentName }}</td>
            <td>{{ agent.address }}</td>
            <td>{{ agent.stock_amount_gas }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Mode Mobile (Card/Grid) -->
    <div v-else>
      <h5 class="mb-3">Official Agents</h5>
      <div class="row">
        <div class="col-12 col-md-6 mb-3" v-for="agent in stakeholderDetail.officialAgents" :key="agent.id">
          <div class="card p-3 shadow-sm">
            <h6 class="fw-bold">{{ agent.agentName }}</h6>
            <p><strong>Address:</strong> {{ agent.address }}</p>
            <p><strong>Stock Gas:</strong> {{ agent.stock_amount_gas }}</p>
          </div>
        </div>
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
</style>