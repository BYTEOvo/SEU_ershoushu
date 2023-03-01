<template>
  <el-table
    :data="users"
    style="width: 100%">
    <el-table-column
      prop="username"
      label="用户名"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="地址"
      width="180">
    </el-table-column>
    <el-table-column
      prop="phonenumber"
      label="电话号码">
    </el-table-column>
    <el-table-column
      prop="account"
      label="余额">
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: 'UserControl',
  data () {
    return {
      users: []
    }
  },
  mounted: function () {
    this.loadUsers()
  },
  methods: {
    loadUsers () {
      this.$axios.get('/users').then(resp => {
        if (resp && resp.status === 200) {
          this.users = resp.data
        }
      })
    }
  }
}
</script>
<style scoped>

</style>
