<template>
<el-container style="width: 88%;margin-left: 6%;margin-top: 10px;border-radius: 40px;background-color: white">
  <el-aside style="width: 200px ;border-radius: 40px">
    <el-menu default-active="/managebook" @select="menuClick">
      <el-menu-item index="/managebook" style="border-radius: 40px">
        <i class="el-icon-menu"></i>
        <span slot="title">管理图书</span>
      </el-menu-item>
      <el-menu-item index="/personalinformation" style="border-radius: 40px">
        <i class="el-icon-menu"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>
      <el-menu-item index="/orderlist/buy" style="border-radius: 40px">
        <i class="el-icon-menu"></i>
        <span slot="title">我买的书</span>
      </el-menu-item>
      <el-menu-item index="/orderlist/sell" style="border-radius: 40px">
        <i class="el-icon-menu"></i>
        <span slot="title">我卖的书</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
  <el-main>
    <router-view/>
  </el-main>
</el-container>
</template>

<script>
export default {
  name: 'Appindex',
  methods: {
    menuClick (index) {
      this.$router.push(index)
    }
  }
}
</script>

<style scoped>

</style>
