<template>
  <body id="a">
  <div id="building"></div>
  <el-form class="l" label-position="left" label-width="0px">
    <el-form-item style="">
       <el-button type="info" plain style="width:150px;margin-bottom:15px;height:50px" v-on:click="login">登录</el-button>
      <el-button type="info" plain style="width:150px;margin-bottom:15px;height:50px" v-on:click="register">注册</el-button>
    </el-form-item>
  </el-form>
  </body>
</template>
<script>
export default {
  methods: {
    login () {
      this.$router.replace('/login')
    },
    register () {
      this.$router.replace('/register')
    }
  }
}
</script>
<style>

#building{
  background:url("../assets/background1.jpg");
  left: 0;
  top: 0;
  width:100%;
  z-index:-1;
  height:100%;
  position:fixed;
  background-size: cover;
  }
.l{
  top:60%;
  left:40%;
  position: absolute;
}

</style>
