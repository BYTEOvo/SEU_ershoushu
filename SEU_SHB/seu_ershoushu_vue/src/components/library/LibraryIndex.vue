<template>
  <el-container class="nr" style="background-color:white;">
    <el-aside style="width: 200px;margin-top: 20px">
      <SideMenu @indexSelect="listByCategory" ref="sideMenu"></SideMenu>
    </el-aside>
    <el-main>
      <books class="books-area" ref="booksArea"></books>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import Books from './Books'

export default {
  name: 'AppLibrary',
  components: {Books, SideMenu},
  methods: {
    listByCategory () {
      var _this = this
      var cid = this.$refs.sideMenu.cid
      var url = 'categories/' + cid + '/library/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.$refs.booksArea.currentPage = 1
          _this.$refs.booksArea.books = resp.data
        }
      })
    }
  }
}
</script>

<style scoped>
.el-container{
  width:88%;
  margin-left: 6%;
}
.nr{
  margin-top: 10px;
  border-radius: 40px;
}
.books-area {
  width: 990px;
  margin-left: auto;
  margin-right: auto;
}
</style>
