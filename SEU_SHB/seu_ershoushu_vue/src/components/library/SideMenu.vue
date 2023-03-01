<template>
  <el-menu
    class="categories"
    default-active="0"
    @select="handleSelect"
    active-text-color="red">
    <el-menu-item
      v-for="item in categoryList.slice().reverse()"
      :key="item.id"
      :index="item.id.toString()"
      :title="item.name">
      <i class="el-icon-menu"></i>
      {{item.name}}
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'SideMenu',
  data () {
    return {
      categoryList: [],
      cid: ''
    }
  },
  created: function () {
    this.getCategoryList()
  },
  methods: {
    handleSelect (key, keyPath) {
      this.cid = key
      this.$emit('indexSelect')
    },
    getCategoryList () {
      this.$axios.get('/category').then(resp => {
        if (resp && resp.status === 200) {
          this.categoryList = resp.data
        }
      })
    }
  }
}
</script>

<style scoped>
.categories {
  position: fixed;
  margin-left: 50%;
  left: -600px;
  top: 150px;
  width: 200px;
}
</style>
