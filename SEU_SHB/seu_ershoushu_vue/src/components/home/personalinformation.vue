<template>
  <div>
  <el-form>
    <h3 class="personalinformation">个人信息</h3>
    <el-form-item>
      用户名 <el-button plain style="width: 50%">{{ user.username }}</el-button>
    </el-form-item>
    <el-form-item>
      地址 <el-button plain style="width: 50%">{{ user.address }}</el-button>
    </el-form-item>
    <el-form-item>
      电话 <el-button plain style="width: 50%">{{ user.phonenumber }}</el-button>
    </el-form-item>
    <el-form-item>
      余额 <el-button plain style="width: 50%">{{ user.account }}</el-button>
    </el-form-item>
    <el-form-item >
      <el-button type="primary" style="width: 20%;background: #505458;border: none" @click="editInfo">修改个人信息</el-button>
      <el-button type="primary" style="width: 20%;background: #505458;border: none" @click="editPwd">修改账号信息</el-button>
    </el-form-item>
    <info-edit-form @onSubmit="loadUserInfo()" ref="edit"></info-edit-form>
    <password-edit-form @onSubmit="loadUserInfo()" ref="editPwd"></password-edit-form>
  </el-form>
  </div>
</template>

<script>
import InfoEditForm from './InfoEditForm'
import PasswordEditForm from './PasswordEditForm'
export default {
  name: 'personalinformation',
  components: {InfoEditForm, PasswordEditForm},
  data () {
    return {
      user: {}
    }
  },
  mounted: function () {
    this.loadUserInfo()
  },
  methods: {
    loadUserInfo () {
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      var url = 'user/' + username + '/info'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.user = resp.data
        }
      })
    },
    editInfo () {
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        address: this.user.address,
        phonenumber: this.user.phonenumber
      }
    },
    editPwd () {
      this.$refs.editPwd.dialogFormVisible = true
      this.$refs.editPwd.form = {
        username: this.user.username,
        password: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
