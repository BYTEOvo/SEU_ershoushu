<template>
  <div>
    <div>
      <el-form :inline="true" @submit.native.prevent>
        <el-form-item>
          <el-button type="primary" round @click="editCategory">增加书籍类别</el-button>
        </el-form-item>
        <el-form-item>
          <el-input
            class="searchinput"
            placeholder="通过书名或作者搜索..."
            prefix-icon="el-icon-search"
            size="middle"
            style="width: 200px;margin-right: 10px;"
            v-model="keywords">
          </el-input>
          <el-button type="primary" round @click="searchResult">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-dropdown>
  <span class="el-dropdown-link">
    书籍状态<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
            <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item @click.native="showUnCheck">未审核</el-dropdown-item>
              <el-dropdown-item @click.native="showCheck">已审核</el-dropdown-item>
              <el-dropdown-item @click.native="showUnsold">未售出</el-dropdown-item>
              <el-dropdown-item @click.native="showSold">已售出</el-dropdown-item>
            </el-dropdown-menu>
            </el-dropdown>
        </el-form-item>
        <el-form-item>
          <el-dropdown @mouseover.native="getCategoryList">
  <span class="el-dropdown-link">
    书籍类别<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
            <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item v-for="item in categorys.slice().reverse()" v-text="item.name" :key="item.id" @click.native="listByCategory(item.id)"></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" round @click="fresh">刷新</el-button>
        </el-form-item>
        <category-edit-form @Onsubmit="getCategoryList" ref="editC"></category-edit-form>
      </el-form>
    </div>
    <el-table
      :data="books"
      style="width: 100%">
      <el-table-column
        prop="title"
        label="图书名称"
        width="100">
      </el-table-column>
      <el-table-column
        prop="author"
        label="作者"
        width="100">
      </el-table-column>
      <el-table-column
        prop="category.name"
        label="类别"
        width="100">
      </el-table-column>
      <el-table-column
        prop="synopsis"
        label="简介"
        width="180">
      </el-table-column>
      <el-table-column
        prop="price"
        label="价格"
        width="90">
      </el-table-column>
      <el-table-column
        prop="username"
        label="卖家"
        width="90">
      </el-table-column>
      <el-table-column label="是否审核" width="150">
        <template v-slot:default="scope">
          <el-button :type="scope.row.checked?'success':'danger'">{{scope.row.checked?'已审核':'未审核'}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="是否出售"
        width="150">
        <template v-slot:default="scope">
        <el-button :type="scope.row.sold?'info':'primary'">{{ scope.row.sold?'已出售':'未出售'}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="审核通过"
        width="150">
        <template v-slot:default="scope">
          <el-button type="primary" icon="el-icon-edit" :disabled="scope.row.checked?true:false" circle @click="check(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import CategoryEditForm from './CategoryEditForm'

export default {
  inject: ['reload'],
  name: 'BookControl',
  components: {CategoryEditForm},
  data () {
    return {
      categorys: [],
      value1: '',
      books: [],
      keywords: '',
      selected: []
    }
  },
  mounted: function () {
    this.loadBooks()
  },
  methods: {
    loadBooks () {
      var _this = this
      this.$axios.get('/books').then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data
        }
      })
    },
    searchResult () {
      var _this = this
      this.$axios
        .post('/search?keywords=' + this.keywords, {
        }).then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
          }
        })
    },
    getCategoryList () {
      this.$axios.get('/category').then(resp => {
        if (resp && resp.status === 200) {
          this.categorys = resp.data
        }
      })
    },
    listByCategory (id) {
      var _this = this
      var url = 'categories/' + id + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data
        }
      })
    },
    fresh () {
      location.reload()
    },
    check (book) {
      this.$confirm('是否审核通过该书籍?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'message'
      }).then(() => {
        this.$axios
          .post('/books', {
            id: book.id,
            cover: book.cover,
            title: book.title,
            author: book.author,
            synopsis: book.synopsis,
            category: {
              id: book.category.id,
              name: book.category.name
            },
            price: book.price,
            newness: book.newness,
            checked: 1,
            sold: 0,
            username: book.username
          }).then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'success',
                message: '审核成功!'
              })
              this.reload()
            }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消审核'
        })
      })
    },
    showUnsold () {
      this.$axios.get('/books/unsold').then(resp => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    },
    showSold () {
      this.$axios.get('/books/sold').then(resp => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    },
    showUnCheck () {
      this.$axios.get('/books/unchecked').then(resp => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    },
    showCheck () {
      this.$axios.get('/books/checked').then(resp => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    },
    editCategory () {
      this.$refs.editC.dialogFormVisible = true
      this.$refs.editC.form = {
        id: '',
        name: ''
      }
    }
  }
}
</script>
<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
