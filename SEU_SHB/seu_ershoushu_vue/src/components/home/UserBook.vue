<template>
  <div>
    <el-row style="height: 840px;">
      <search-bar @onSearch="searchResult" ref="searchBar"></search-bar>
      <el-tooltip effect="dark" placement="right"
                  v-for="item in books.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                  :key="item.id">
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.title}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{item.author}}</span> /
        </p>
        <p slot="content" style="width: 300px" class="synopsis">{{item.synopsis}}</p>
        <el-card style="width: 135px;margin-bottom: 20px;height: 270px;float: left;margin-right: 15px;position: relative" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover" @click="editBook(item)">
            <img :src="item.cover" alt="封面">
          </div>
          <div style="color: #be7e64;font-size: 15px;text-align: left;position: absolute" class="price">￥{{item.price}}</div>
          <div class="info">
            <div class="title">
              <a href="">{{item.title}}</a>
            </div>
            <i class="el-icon-delete" @click="deleteBook(item.id)"></i>
          </div>
          <div class="author">{{item.author}}</div>
        </el-card>
      </el-tooltip>
      <edit-form @onSubmit="loadUserBooks()" ref="edit"></edit-form>
    </el-row>
    <el-row>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pagesize"
        :total="books.length">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
import EditForm from '../library/EditForm'
import SearchBar from '../library/SearchBar'
export default {
  inject: ['reload'],
  name: 'UserBooks',
  components: {EditForm, SearchBar},
  data () {
    return {
      books: [],
      currentPage: 1,
      pagesize: 17
    }
  },
  mounted: function () {
    this.loadUserBooks()
  },
  methods: {
    loadUserBooks () {
      var _this = this
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      var url = 'user/' + username + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.books = resp.data
        }
      })
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)
    },
    searchResult () {
      var _this = this
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      var url = '/' + username + '/search?keywords=' + this.$refs.searchBar.keywords
      this.$axios
        .get(url, {
        }).then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
          }
        })
    },
    deleteBook (id) {
      this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios
          .post('/delete', {id: id}).then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'success',
                message: '已删除'
              })
              this.loadUserBooks()
              this.reload()
            }
          })
      }
      ).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
      // alert(id)
    },
    editBook (item) {
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
        id: item.id,
        cover: item.cover,
        title: item.title,
        author: item.author,
        synopsis: item.synopsis,
        category: {
          id: item.category.id.toString(),
          name: item.category.name
        },
        price: item.price,
        newness: item.newness,
        checked: 0,
        sold: 0
      }
    }
  }
}
</script>
<style scoped>

.cover {
  width: 115px;
  height: 172px;
  margin-bottom: 7px;
  overflow: hidden;
  cursor: pointer;
}

img {
  width: 115px;
  height: 172px;
  /*margin: 0 auto;*/
}

.title {
  font-size: 14px;
  text-align: left;
}

.author {
  color: #333;
  width: 102px;
  font-size: 13px;
  margin-bottom: 6px;
  text-align: left;
}

.synopsis {
  display: block;
  line-height: 17px;
}

.el-icon-delete {
  cursor: pointer;
  float: right;
}

.switch {
  display: flex;
  position: absolute;
  left: 780px;
  top: 25px;
}

a {
  text-decoration: none;
}

.price{
  bottom: 0;
}

a:link, a:visited, a:focus {
  color: #3377aa;
}

</style>
