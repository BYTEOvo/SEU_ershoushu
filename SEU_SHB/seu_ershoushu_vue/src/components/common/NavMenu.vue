<template>
  <el-menu
    class="topmune"
    :default-active="'/index'"
    router
    mode="horizontal"
    background-color="transparent"
    text-color="#222"
    active-text-color="red"
    style="min-width: 1300px">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
    </el-menu-item>
    <i class="el-icon-switch-button" v-on:click="logout" style="float:right;font-size: 25px;color: #222;padding: 10px"></i>
    <span style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold;background-color:transparent">东大二手书交易平台</span>
  </el-menu>
</template>

<script>
export default {
  name: 'NavMenu',
  data () {
    return {
      navList: [
        {name: '/library', navItem: '图书市场'},
        {name: '', navItem: ''}
      ]
    }
  },
  beforeCreate () {
    document.body.setAttribute('style', 'background:#EAECF1')
  },
  created: function () {
    this.getRole()
  },
  methods: {
    logout () {
      var _this = this
      this.$axios.get('/logout').then(resp => {
        if (resp.data.code === 200) {
          // 前后端状态保持一致
          _this.$store.commit('logout')
          this.$alert('登出成功', '提示', {
            confirmButtonText: '确定'
          })
          _this.$router.replace('/login')
        }
      })
    },
    getRole () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      var url = 'user/' + username + '/role'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          var role = resp.data.role
          if (role === 'admin') {
            this.navList[1].navItem = '管理中心'
            this.navList[1].name = '/dashboard'
          } else {
            this.navList[1].navItem = '个人中心'
            this.navList[1].name = '/managebook'
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.topmune{
  width:88%;
  margin-left: 6%;
}
a{
  text-decoration: none;
}

.el-icon-switch-button {
  cursor: pointer;
  outline:0;
}

span {
  pointer-events: none;
}
</style>
