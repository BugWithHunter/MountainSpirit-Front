<template>
  <nav class="pagination-container">
    <button
      class="pagination-arrow"
      :disabled="currentPage === 1"
      @click="changePage(currentPage - 1)">
      &larr; Previous
    </button>

    <template v-for="page in visiblePages" :key="page">
      <span
        v-if="page === '...'"
        class="pagination-ellipsis">
        ...
      </span>
      <button
        v-else
        :class="['pagination-page', { active: page === currentPage }]"
        @click="changePage(page)">
        {{ page }}
      </button>
    </template>

    <button
      class="pagination-arrow"
      :disabled="currentPage === lastPage"
      @click="changePage(currentPage + 1)">
      Next &rarr;
    </button>
  </nav>
</template>

<script setup>
import { computed } from 'vue'
const props = defineProps({
  currentPage: Number,
  startPage: Number,
  endPage: Number,
  lastPage: Number
})
const emit = defineEmits(['page-change'])

const changePage = (page) => {
  if (page > 0 && page <= props.lastPage && page !== props.currentPage) {
    emit('page-change', page)
  }
}

const visiblePages = computed(() => {
  const pages = []
  const { currentPage, lastPage } = props

  if (lastPage <= 7) {
    for (let i = 1; i <= lastPage; i++) pages.push(i)
  } else {
    if (currentPage <= 4) {
      pages.push(1, 2, 3, 4, 5, '...', lastPage)
    } else if (currentPage >= lastPage - 3) {
      pages.push(1, '...', lastPage - 4, lastPage - 3, lastPage - 2, lastPage - 1, lastPage)
    } else {
      pages.push(1, '...', currentPage - 1, currentPage, currentPage + 1, '...', lastPage)
    }
  }
  return pages
})
</script>

<style scoped>
.pagination-container {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 20px;
}

.pagination-arrow {
  background: none;
  border: none;
  color: #888;
  cursor: pointer;
  font-size: 15px;
  padding: 6px 10px;
}

.pagination-arrow[disabled] {
  color: #ddd;
  cursor: not-allowed;
}

.pagination-page {
  background: none;
  border: none;
  padding: 6px 12px;
  cursor: pointer;
  font-size: 16px;
  color: #333;
  border-radius: 6px;
  transition: background 0.2s;
}

.pagination-page.active {
  background: #222;
  color: #fff;
}

.pagination-ellipsis {
  padding: 0 6px;
  color: #bbb;
  font-size: 16px;
}
</style>
