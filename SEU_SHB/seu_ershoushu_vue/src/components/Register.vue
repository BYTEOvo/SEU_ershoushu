<template>
  <body id="paper">
  <div class="background">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
  <el-form class="login-container" label-position="left"
           label-width="0px" v-loading="loading">
    <h3 class="login_title">用户注册</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="text" v-model="loginForm.address"
                auto-complete="off" placeholder="地址"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="text" v-model="loginForm.phonenumber"
                auto-complete="off" placeholder="手机号码"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="register">注册</el-button>
    </el-form-item>
    <el-form-item>
    <a href="login">已有帐号？登录</a>
    </el-form-item>
  </el-form>
  </body>
</template>
<script>

export default{
  name: 'register',
  data () {
    return {
      checked: true,
      imgSrc: require('../assets/background1.jpg'),
      loginForm: {
        username: '',
        password: '',
        address: '',
        phonenumber: ''
      },
      loading: false
    }
  },
  methods: {
    register () {
      var _this = this
      this.$axios
        .post('/register', {
          username: this.loginForm.username,
          password: this.loginForm.password,
          address: this.loginForm.address,
          phonenumber: this.loginForm.phonenumber,
          account: 1000
        })
        .then(resp => {
          if (resp.data.code === 200) {
            this.$alert('注册成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$router.replace('/login')
          } else {
            this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
        .catch(failResponse => {})
    }
  }
}
</script>
<style>
a{font-size:15px}
body{
  margin: -5px 0px;
}
.background {
  left: 0;
  top: 0;
  width:100%;
  height:100%;  /**宽高100%是为了图片铺满屏幕 */
  z-index:-1;
  position: absolute;
}
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 15% auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
::-webkit-scrollbar {
  width: 10px;

}

::-webkit-scrollbar-thumb {
  background-color: #eaecf1;
  border-radius: 100px;
}
</style>
