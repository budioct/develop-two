<script>
import { ref, computed } from 'vue';
import Pagination from './Pagination.vue';

export default {
  components: { Pagination },
  props: {
    columns: Array, // { key: 'name', label: 'Nama' }
    data: Array,
    perPage: { type: Number, default: 10 },
    loading: { type: Boolean, default: false }
  },
  setup(props, { slots }) {
    const search = ref('');
    const currentPage = ref(1);

    // Computed Properties
    const filteredData = computed(() => {
      return props.data.filter(row =>
          Object.values(row).some(value => {
            if (Array.isArray(value)) {
              return value.some(item =>
                  String(item).toLowerCase().includes(search.value.toLowerCase())
              );
            }
            return String(value).toLowerCase().includes(search.value.toLowerCase());
          })
      );
    });

    const paginatedData = computed(() => {
      const start = (currentPage.value - 1) * props.perPage;
      return filteredData.value.slice(start, start + props.perPage);
    });

    const totalPages = computed(() => {
      return Math.ceil(filteredData.value.length / props.perPage);
    });

    const changePage = (page) => {
      currentPage.value = page;
    };

    const hasActions = computed(() => !!slots.actions);

    return {
      search,
      currentPage,
      filteredData,
      paginatedData,
      totalPages,
      hasActions,
      changePage
    };
  }
};
</script>

<template>
  <div>
    <!-- Input Search -->
    <div class="row mt-3 mb-3">
      <div class="col-sm-6">
        <div class="input-group">
          <input
              v-model="search"
              class="form-control mb-1 col-md-5 col-sm-5"
              placeholder="Search..."
              style="border-radius: 10px"
          />
        </div>
      </div>
    </div>

    <!-- Tabel -->
    <div class="table-responsive text-center">

      <!-- Loader -->
      <div v-if="loading" class="d-flex justify-content-center my-4">
        <div class="spinner-border text-success" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
      </div>

      <table class="table table-hover">
        <thead>
        <tr>
          <th v-for="column in columns" :key="column.key">
            {{ column.label }}
          </th>
          <th v-if="hasActions">Aksi</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(row, index) in paginatedData" :key="row.id">
          <td v-for="column in columns" :key="column.key">
            <template v-if="Array.isArray(row[column.key])">
              {{ row[column.key].join(', ') }}
            </template>
            <template v-else>
              {{ row[column.key] }}
            </template>
          </td>
          <td v-if="hasActions">
            <slot name="actions" :row="row"></slot>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination -->
    <Pagination
        :currentPage="currentPage"
        :totalPages="totalPages"
        @pageChange="changePage"
    />

  </div>
</template>

<style scoped>

</style>