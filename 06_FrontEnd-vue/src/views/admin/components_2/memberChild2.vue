<template>
  <div>
    <MemberInfoState v-if="view === 'memberInfo'" />
    <MemberLoginState v-else-if="view === 'memberLogin'" />
    <MemberGradeState v-else-if="view === 'memberGrade'" />
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import MemberInfoState from '../components_3/memberInfoState.vue';
import MemberLoginState from '../components_3/memberLoginState.vue';
import MemberGradeState from '../components_3/memberGradeState.vue';

const props = defineProps({
view: String
});
</script>

<style scoped>

</style>