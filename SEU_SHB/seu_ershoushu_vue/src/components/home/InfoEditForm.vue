<template>
  <div>
    <el-dialog
      title="修改个人信息"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="地址" :label-width="formLabelWidth" prop="address">
          <el-input v-model="form.address" autocomplete="off" placeholder="东南大学某校区某宿舍"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth" prop="phonenumber">
          <el-input v-model="form.phonenumber" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'InfoEditForm',
  data () {
    return {
      dialogFormVisible: false,
      form: {
        address: '',
        number: ''
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    clear () {
      this.form = {
        address: '',
        number: ''
      }
    },
    onSubmit () {
      this.$axios
        .post('/user/info', {
          address: this.form.address,
          phonenumber: this.form.phonenumber,
          role: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
        }).then(resp => {
          if (resp.data.code === 200) {
            this.$alert('修改成功', '提示', {
              confirmButtonText: '确定'
            })
            this.dialogFormVisible = false
            this.$emit('onSubmit')
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

<style scoped>
.el-icon-circle-plus-outline {
  margin: 50px 0 0 20px;
  font-size: 100px;
  float: left;
  cursor: pointer;
}
</style>
