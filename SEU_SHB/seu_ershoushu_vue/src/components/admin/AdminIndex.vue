<template>
  <el-container style="width: 88%;margin-left: 6%;margin-top: 10px;border-radius: 40px;background-color: white">
    <el-aside style="width: 200px ;border-radius: 40px">
      <el-menu default-active="/dashboard" @select="menuClick" >
        <el-menu-item index="/dashboard" style="border-radius: 40px">
          <i class="el-icon-menu"></i>
          <span slot="title">网站信息总览</span>
        </el-menu-item>
        <el-menu-item index="/bookcontrol" style="border-radius: 40px">
          <i class="el-icon-menu"></i>
          <span slot="title">图书信息管理</span>
        </el-menu-item>
        <el-menu-item index="/usercontrol" style="border-radius: 40px">
          <i class="el-icon-menu"></i>
          <span slot="title">用户信息管理</span>
        </el-menu-item>
        <el-menu-item index="/ordercontrol" style="border-radius: 40px">
          <i class="el-icon-menu"></i>
          <span slot="title">订单信息管理</span>
        </el-menu-item>
        <el-menu-item index="/handlecomplaint" style="border-radius: 40px">
          <i class="el-icon-menu"></i>
          <span slot="title">申诉处理</span>
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
  name: 'AdminIndex',
  beforeCreate () {
    document.body.setAttribute('style', 'background:#EAECF1')
  },
  methods: {
    menuClick (index) {
      this.$router.push(index)
    }
  }
}
</script>

<style scoped>

</style>
