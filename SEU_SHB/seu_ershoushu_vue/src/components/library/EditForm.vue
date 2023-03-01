<template>
  <div>
    <i class="el-icon-circle-plus-outline"  @click="dialogFormVisible = true"></i>
    <el-dialog
      title="添加/修改图书"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="书名" :label-width="formLabelWidth" prop="title">
          <el-input v-model="form.title" autocomplete="off" placeholder="不加《》"></el-input>
        </el-form-item>
        <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="封面" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.cover" autocomplete="off" placeholder="图片 URL"></el-input>
          <img-upload @onUpload="uploadImg" ref="imgUpload"></img-upload>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth" prop="synopsis">
          <el-input type="textarea" v-model="form.synopsis" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类" :label-width="formLabelWidth" prop="cid">
          <el-select v-model="form.category" placeholder="请选择分类" value-key="id" @focus="getCategoryList" @change="$forceUpdate()">
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.name"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" :label-width="formLabelWidth" prop="price">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新旧程度" :label-width="formLabelWidth" prop="price">
          <el-input v-model="form.newness" autocomplete="off" placeholder="为您的书籍的新旧情况打分，范围为0-5分"></el-input>
        </el-form-item>
        <el-form-item prop="id" style="height: 0">
          <el-input type="hidden" v-model="form.id" autocomplete="off"></el-input>
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
import ImgUpload from './ImgUpload'

export default {
  name: 'EditForm',
  components: {ImgUpload},
  data () {
    return {
      categoryList: [],
      dialogFormVisible: false,
      form: {
        id: '',
        title: '',
        author: '',
        cover: '',
        synopsis: '',
        category: {
          id: '',
          name: ''
        },
        price: '',
        newness: '',
        checked: 0,
        sold: 0
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    clear () {
      this.$refs.imgUpload.clear()
      // eslint-disable-next-line no-unused-expressions
      this.categoryList = undefined
      this.categoryList = []
      this.form = {
        id: '',
        title: '',
        author: '',
        cover: '',
        synopsis: '',
        category: {
          id: '',
          name: ''
        },
        price: '',
        newness: '',
        checked: 0,
        sold: 0
      }
    },
    getCategoryList () {
      this.$axios.get('/category').then(resp => {
        if (resp && resp.status === 200) {
          this.categoryList = resp.data
        }
      })
    },
    onSubmit () {
      this.$axios
        .post('/books', {
          id: this.form.id,
          cover: this.form.cover,
          title: this.form.title,
          author: this.form.author,
          synopsis: this.form.synopsis,
          category: {
            id: this.form.category.id,
            name: this.form.category.name},
          price: this.form.price,
          newness: this.form.newness,
          checked: 0,
          sold: 0,
          username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
        }).then(resp => {
          if (resp && resp.status === 200) {
            this.dialogFormVisible = false
            this.$emit('onSubmit')
            window.location.reload(true)
          }
        })
    },
    uploadImg () {
      this.form.cover = this.$refs.imgUpload.url
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
