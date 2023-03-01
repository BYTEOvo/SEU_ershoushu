<template>
  <div>
    <el-dialog
      title="添加图书类别"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="图书类别" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" ></el-input>
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
  name: 'CategoryEditForm',
  data () {
    return {
      dialogFormVisible: false,
      form: {
        id: '',
        name: ''
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    clear () {
      this.form = {
        id: '',
        name: ''
      }
    },
    onSubmit () {
      this.$axios
        .post('/category', {
          id: this.form.id,
          name: this.form.name
        }).then(resp => {
          if (resp.data.code === 200) {
            this.$alert('添加成功', '提示', {
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

</style>
