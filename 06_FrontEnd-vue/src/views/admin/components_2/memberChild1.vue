<template>
    <div>
      <MemberInfo v-if="view === 'memberInfo'" />
      <MemberLogin v-else-if="view === 'memberLogin'" />
      <MemberGrade v-else-if="view === 'memberGrade'" />
    </div>
</template>

<script setup>
import { defineProps } from 'vue';
import MemberInfo from '../components_3/memberInfo.vue';
import MemberLogin from '../components_3/memberLogin.vue';
import MemberGrade from '../components_3/memberGrade.vue';

const props = defineProps({
  view: String
});
</script>

<style scoped>

</style>