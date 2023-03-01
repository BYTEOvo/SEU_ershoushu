<template>
  <el-table
    :data="orders"
    style="width: 100%">
    <el-table-column type="expand">
      <template v-slot="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="书名">
            <span>{{props.row.book.title}}</span>
          </el-form-item>
          <el-form-item label="封面">
            <el-image style="width: 100px; height: 100px" :src="props.row.book.cover" fit="contain"></el-image>
          </el-form-item>
          <el-form-item label="作者">
            <span>{{props.row.book.author}}</span>
          </el-form-item>
          <el-form-item label="新旧程度">
            <span>{{props.row.book.newness}}</span>
          </el-form-item>
          <el-form-item label="买家">
            <span>{{props.row.buyer}}</span>
          </el-form-item>
          <el-form-item label="收货地址">
            <span>{{props.row.receive}}</span>
          </el-form-item>
          <el-form-item label="价格">
            <span>{{props.row.book.price}}</span>
          </el-form-item>
          <el-form-item label="当前状态">
            <span>{{props.row.state}}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column
      prop="begin"
      label="订单生成时间" :formatter="formatBeginDate"
      width="150">
    </el-table-column>
    <el-table-column
      prop="end"
      label="订单结束时间" :formatter="formatEndDate"
      width="150">
    </el-table-column>
    <el-table-column
      prop="buyer"
      label="买家"
      width="130">
    </el-table-column>
    <el-table-column
      prop="receive"
      label="收货地址"
      width="150">
    </el-table-column>
    <el-table-column
      prop="book.price"
      label="价格"
      width="90">
    </el-table-column>
    <el-table-column
      prop="state"
      label="当前状态"
      width="150">
    </el-table-column>
    <el-table-column
      label="操作"
      width="150">
      <template v-slot:default="scope">
      <el-button type="primary" :disabled="scope.row.state !== '未寄出'" @click="sendBook(scope.row)" v-if="scope.row.state !== '已完成' && scope.row.state !== '已退款'">已发货</el-button>
        <el-tag v-if="scope.row.state === '已完成'">订单已完成</el-tag>
        <el-tag v-if="scope.row.state === '已退款'">订单已退款</el-tag>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  inject: ['reload'],
  name: 'SellList',
  data () {
    return {
      orders: []
    }
  },
  mounted: function () {
    this.loadOrders()
  },
  methods: {
    formatBeginDate (value) {
      this.value1 = new Date(value.begin)
      let dateValue = this.$moment(this.value1).format('YYYY-MM-DD')
      return dateValue
    },
    formatEndDate (value) {
      this.value1 = new Date(value.end)
      var value1 = new Date(value.begin)
      var value2 = new Date(value.end)
      let dateValue
      if (value1 > value2) { dateValue = null } else { dateValue = this.$moment(this.value1).format('YYYY-MM-DD') }
      return dateValue
    },
    loadOrders () {
      var _this = this
      var username = window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username
      var url = 'user/' + username + '/orders/sell'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.orders = resp.data
        }
      })
    },
    sendBook (order) {
      this.$axios.post('/send', {
        id: order.id,
        begin: order.begin,
        end: order.end,
        book: {
          id: order.book.id,
          category: {id: order.book.category.id, name: order.book.category.name},
          newness: order.book.newness,
          username: order.book.username,
          title: order.book.title,
          author: order.book.author,
          checked: order.book.checked,
          cover: order.book.cover,
          synopsis: order.book.synopsis,
          price: order.book.price,
          sold: order.book.sold},
        buyer: order.buyer,
        receive: order.receive,
        seller: order.seller,
        send: order.send,
        state: order.state
      })
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$message({
              type: 'success',
              message: '发货成功!'
            })
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '发货失败！'
            })
          }
        })
    }
  }
}
</script>
<style scoped>

</style>
