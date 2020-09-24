<template>
    <div style="
			display: inline-block;
			height: 100%;
			width: 0;
			vertical-align: middle;
		">
      <img alt="图片加载中...." :src="src" style="vertical-align: middle;">
    </div>
</template>

<script>

  export default {
    props: ['instanceId'],
    name: 'index',
    data() {
      return {
        src: null
      }
    },
    watch: {
      instanceId: {
        immediate: true,
        handler(newVal) {
          if (newVal) {
            this.src=process.env.VUE_APP_BASE_API+'/process/read-resource?pProcessInstanceId=' + newVal
          }else {
            this.src='';
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>
