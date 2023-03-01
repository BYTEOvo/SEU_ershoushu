<template>
  <div>
    <div class ="bottom"><img src="../../assets/返回箭头.png" style="width:30px;height: 30px" @click="back"/></div>
    <el-header style="width: 88%;margin-left: 6%;border-radius: 40px;background-color: transparent;font-size: large;margin-top: 70px">
      {{ book.title }}</el-header>
    <el-container style="margin-left:15%;width: 70%; border-radius: 40px;background-color: white;margin-top: 30px">
      <el-aside width="200px" style="background-color: white;border-radius: 40px">
        <div><div class="demo-image">
          <div class="block" >
            <el-image
              style="width: 130px; height: 190px;position: center;margin-top: 10px;"
              :src=book.cover></el-image>
          </div>
        </div></div>
      </el-aside>
      <el-container>
        <h2 class="简介" style="font-size: small">{{ book.synopsis }}</h2>
        <el-main style="border-radius: 40px;background-color: white">
          <div><el-descriptions class="margin-top" title="" :column="4" direction="vertical">
            <el-descriptions-item label="新旧程度"><el-rate
              v-model="value"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value}">
            </el-rate></el-descriptions-item><el-descriptions-item label="作者">{{ book.title }}</el-descriptions-item>
            <el-descriptions-item label="卖方">{{ book.username }}</el-descriptions-item>
            <el-descriptions-item label="分类">
              <el-tag>{{ book.category.name }}</el-tag>
            </el-descriptions-item>
          </el-descriptions></div>
          <div style="text-align: left;color:red;font-size: large;font-family: 楷体">东东价:{{ book.price }}</div>
        </el-main>
        <el-footer>
          <div><el-row>
            <el-button type="primary" @click="createOrder">立即购买</el-button>
          </el-row></div> </el-footer>
      </el-container></el-container></div>
</template>
<script>
export default {
  name: 'Order',
  data () {
    return {
      book: {
        cover: '',
        title: '',
        author: '',
        synopsis: '',
        category: {
          id: '',
          name: ''},
        price: '',
        newness: '',
        username: ''
      },
      value: 0
    }
  },
  created () {
    this.loadBook()
  },
  beforeCreate () {
    document.body.setAttribute('style', 'background:#EAECF1')
  },
  methods: {
    loadBook () {
      const id = this.$route.query.id
      var url = '/books/' + id
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.book = resp.data
          this.value = resp.data.newness
        }
      })
    },
    createOrder () {
      this.$axios.post('/orders', {
        seller: this.book.username,
        buyer: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username,
        book: this.book,
        begin: new Date(),
        end: ''
      }).then(resp => {
        if (resp && resp.data.code === 200) {
          this.$alert('购买成功', '提示', {
            confirmButtonText: '确定'
          })
          this.$router.replace('/library')
        } else {
          this.$alert(resp.data.message, '提示', {
            confirmButtonText: '确定'
          })
        }
      })
    },
    back () {
      this.$router.go(-1)
    }
  }
}
</script>
<style>
.bottom{
  min-width: 100%;
  height: 10px;
  position: relative;
  display:flex;
  justify-content:flex-start;
}
</style>
