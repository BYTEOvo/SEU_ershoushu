<template>
  <div>
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
          <el-form-item label="卖家">
            <span>{{props.row.seller}}</span>
          </el-form-item>
          <el-form-item label="发货地址">
            <span>{{props.row.send}}</span>
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
      prop="seller"
      label="卖家"
      width="130">
    </el-table-column>
    <el-table-column
      prop="send"
      label="发货地址"
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
      label="操作" width="300px">
      <template v-slot:default="scope">
        <el-button size="medium" type="primary" v-if="scope.row.state === '已寄出'" @click="receiveBook(scope.row)">确认收货</el-button>
        <el-button size="medium" type="primary" v-if="scope.row.state === '未寄出' || scope.row.state === '已寄出'" @click="dialogFormVisible = true" >申请退货退款</el-button>
        <el-button size="medium" type="primary" v-if="scope.row.state === '等待寄回'" @click="sendBookBack(scope.row)" >确认寄回</el-button>
        <el-tag v-if="scope.row.state === '已完成'">订单已完成</el-tag>
        <el-tag v-if="scope.row.state === '已退款'">订单已退款</el-tag>
        <el-dialog :append-to-body="true" title="退货界面" :visible.sync="dialogFormVisible">
          <el-form>
            <el-form-item label="请简述退货理由" :label-width="formLabelWidth">
              <el-input v-model="form.content" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="createComplaint(scope.row)">确 定</el-button>
          </div>
        </el-dialog>
      </template>
    </el-table-column>
  </el-table>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  name: 'BuyList',
  data () {
    return {
      orders: [],
      dialogFormVisible: false,
      form: {
        content: ''
      },
      formLabelWidth: '120px'
    }
  },
  mounted: function () {
    this.loadOrders()
  },
  methods: {
    Appeal () {
      this.$router.replace('/Appeal')
    },
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
      var url = 'user/' + username + '/orders/buy'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          _this.orders = resp.data
        }
      })
    },
    receiveBook (order) {
      this.$axios.post('/receive', {
        id: order.id,
        begin: order.begin,
        end: new Date(),
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
              message: '收货成功!'
            })
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '收货失败!'
            })
          }
        })
    },
    // eslint-disable-next-line camelcase
    createComplaint (m_order) {
      this.$axios.post('/complaint/create', {
        username: window.localStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.localStorage.getItem('user' || '[]')).username,
        content: this.form.content,
        time: new Date(),
        state: '未审核',
        order: {id: m_order.id,
          begin: m_order.begin,
          book: {
            id: m_order.book.id,
            category: {id: m_order.book.category.id, name: m_order.book.category.name},
            newness: m_order.book.newness,
            username: m_order.book.username,
            title: m_order.book.title,
            author: m_order.book.author,
            checked: m_order.book.checked,
            cover: m_order.book.cover,
            synopsis: m_order.book.synopsis,
            price: m_order.book.price,
            sold: m_order.book.sold},
          buyer: m_order.buyer,
          receive: m_order.receive,
          seller: m_order.seller,
          send: m_order.send,
          state: m_order.state
        }
      })
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$message({
              type: 'success',
              message: '申诉提交成功!'
            })
            this.dialogFormVisible = false
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '申诉提交失败!'
            })
          }
        })
    },
    sendBookBack (order) {
      this.$axios.post('/resend', {
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
          sold: order.book.sold
        },
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
              message: '寄回成功!'
            })
            this.dialogFormVisible = false
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '寄回失败!'
            })
          }
        })
    }
  }
}
</script>

<style>
.demo-table-expand {
font-size: 0;
}
.demo-table-expand label {
width: 90px;
color: #99a9bf;
}
.demo-table-expand .el-form-item {
margin-right: 0;
margin-bottom: 0;
width: 50%;
}
</style>
