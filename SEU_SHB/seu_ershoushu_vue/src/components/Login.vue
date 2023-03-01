<template>
  <body id="poster">
  <div class="background">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
  </div>
<!--      <el-container>-->
<!--        <el-header>-->
<!--          <h1 style="text-align: center;font-size:40px;color:black">欢迎使用东大二手书交易平台</h1>-->
<!--        </el-header>-->
<!--      </el-container>-->

  <el-form class="login-container" label-position="left"
           label-width="0px" >
    <h3 class="login_title">系统登录</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username"
                auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item>
      <el-input type="password" v-model="loginForm.password"
                auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%;background: #505458;border: none" v-on:click="login">登录</el-button>
    </el-form-item>
    <el-form-item>
    <a href="register">注册</a>
    </el-form-item>
  </el-form>
  </body>
</template>
<script>

export default {
  name: 'Login',
  data () {
    return {
      imgSrc: require('../assets/background1.jpg'),
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      var _this = this
      console.log(this.$store.state)
      this.$axios
        .post('/login', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            // var data = this.loginForm
            _this.$store.commit('login', _this.loginForm)
            var path = this.$route.query.redirect
            this.$router.replace({path: path === '/' || path === undefined ? '/library' : path})
          } else {
            this.$alert(successResponse.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
        .catch(failResponse => {
        })
    }
  }
}

</script>

<style>
a{font-size:15px}
body {
  margin:-5px  0px;
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
