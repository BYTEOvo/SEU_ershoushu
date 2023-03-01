<template>
  <el-table
    :data="complaints"
    style="width: 100%">
    <el-table-column
      prop="username"
      label="申诉人"
      width="90">
    </el-table-column>
    <el-table-column
    prop="order.id"
    label="订单编号"
    width="90">
  </el-table-column>
    <el-table-column
      prop="order.book.title"
      label="图书名称"
      width="120">
    </el-table-column>
    <el-table-column
      prop="order.begin" :formatter="formatBeginDate"
      label="订单生成时间"
      width="150">
    </el-table-column>
    <el-table-column
      prop="time" :formatter="formatTimeDate"
      label="申诉日期"
      width="150">
    </el-table-column>
    <el-table-column
        prop="order.book.price"
        label="交易金额"
        width="80">
    </el-table-column>
    <el-table-column
      prop="content"
      label="退货理由"
      width="150">
    </el-table-column>
    <el-table-column
      label="查看订单详情"
      width="110">
      <template v-slot="scope">
      <el-row>
        <el-button @click="handleDrawerOpen(scope.row)" type="info" icon="el-icon-view" circle></el-button>
      </el-row>
      <el-drawer
      title="订单详情"
      :append-to-body="true"
      :modal-append-to-body="false"
      :visible.sync="drawer"
      :with-header="false"
      :destroy-on-close="true">
        <el-descriptions title="订单详情表" direction="vertical" :column="2" border>
          <el-descriptions-item label="买家">{{ content.order.buyer }}</el-descriptions-item>
          <el-descriptions-item label="卖家">{{ content.order.seller }}</el-descriptions-item>
          <el-descriptions-item label="订单生成时间" :span="2" >{{ formatBeginDate(content) }}</el-descriptions-item>
          <el-descriptions-item label="申诉提交时间" :span="2" >{{ formatTimeDate(content) }}</el-descriptions-item>
          <el-descriptions-item label="发货地址" :span="2">{{ content.order.send }}</el-descriptions-item>
          <el-descriptions-item label="收货地址" :span="2">{{ content.order.receive }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag size="medium ">{{ content.order.state }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="订单金额">{{ content.order.book.price }}</el-descriptions-item>
        </el-descriptions>
    </el-drawer>
      </template>
    </el-table-column>
    <el-table-column
      prop="state"
      label="申诉状态"
      width="120">
    </el-table-column>
    <el-table-column
      label="通过申诉请求"
      width="180">
      <template v-slot:default="scope">
      <el-row>
        <el-button type="success" icon="el-icon-success" v-if="scope.row.state === '未审核'" circle @click="handleComplaint(scope.row)"></el-button>
        <el-button type="danger" icon="el-icon-error" v-if="scope.row.state === '未审核'" circle @click="rejectedComplaint(scope.row)"></el-button>
        <el-button size="medium" type="primary" v-if="scope.row.order.state === '已寄回'" @click="receiveBook(scope.row)" >确认收到</el-button>
        <el-tag v-if="scope.row.order.state === '已退款'">已退款，申诉已关闭</el-tag>
        <el-tag v-if="scope.row.order.state === '已完成'">订单已完成，申诉已关闭</el-tag>
        <el-tag v-if="scope.row.order.state === '等待寄回'">申诉已通过，等待寄回</el-tag>
      </el-row>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  inject: ['reload'],
  name: 'HandleComplaint',
  data () {
    return {
      complaints: [],
      drawer: false,
      content: {
        id: '',
        username: '',
        content: '',
        time: '',
        state: '',
        order: {
          id: '',
          begin: '',
          book: {
            id: '',
            category: {id: '', name: ''},
            newness: '',
            username: '',
            title: '',
            author: '',
            checked: '',
            cover: '',
            synopsis: '',
            price: '',
            sold: ''
          },
          buyer: '',
          receive: '',
          seller: '',
          send: '',
          state: ''
        }
      }
    }
  },
  mounted: function () {
    this.loadComplaint()
  },
  methods: {
    handleDrawerOpen (value) {
      this.drawer = true
      this.content = value
    },
    formatBeginDate (value) {
      this.value1 = new Date(value.order.begin)
      let dateValue = this.$moment(this.value1).format('YYYY-MM-DD')
      return dateValue
    },
    formatTimeDate (value) {
      this.value1 = new Date(value.time)
      let dateValue = this.$moment(this.value1).format('YYYY-MM-DD')
      return dateValue
    },
    loadComplaint () {
      var url = '/complaint'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.complaints = resp.data
        }
      })
    },
    handleComplaint (complaint) {
      this.$axios.post('/complaint/pass', {
        id: complaint.id,
        username: complaint.username,
        content: complaint.content,
        time: complaint.time,
        state: '已通过',
        order: {id: complaint.order.id,
          begin: complaint.order.begin,
          book: {
            id: complaint.order.book.id,
            category: {id: complaint.order.book.category.id, name: complaint.order.book.category.name},
            newness: complaint.order.book.newness,
            username: complaint.order.book.username,
            title: complaint.order.book.title,
            author: complaint.order.book.author,
            checked: complaint.order.book.checked,
            cover: complaint.order.book.cover,
            synopsis: complaint.order.book.synopsis,
            price: complaint.order.book.price,
            sold: complaint.order.book.sold},
          buyer: complaint.order.buyer,
          receive: complaint.order.receive,
          seller: complaint.order.seller,
          send: complaint.order.send,
          state: complaint.order.state
        }
      })
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$message({
              type: 'success',
              message: '申诉通过成功!'
            })
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '申诉通过失败!'
            })
          }
        })
    },
    receiveBook (complaint) {
      this.$axios.post('/complaint/receive', {
        id: complaint.id,
        username: complaint.username,
        content: complaint.content,
        time: complaint.time,
        state: '已通过',
        order: {id: complaint.order.id,
          begin: complaint.order.begin,
          end: new Date(),
          book: {
            id: complaint.order.book.id,
            category: {id: complaint.order.book.category.id, name: complaint.order.book.category.name},
            newness: complaint.order.book.newness,
            username: complaint.order.book.username,
            title: complaint.order.book.title,
            author: complaint.order.book.author,
            checked: complaint.order.book.checked,
            cover: complaint.order.book.cover,
            synopsis: complaint.order.book.synopsis,
            price: complaint.order.book.price,
            sold: complaint.order.book.sold},
          buyer: complaint.order.buyer,
          receive: complaint.order.receive,
          seller: complaint.order.seller,
          send: complaint.order.send,
          state: complaint.order.state
        }
      })
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$message({
              type: 'success',
              message: '确认收回书籍!'
            })
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '收回书籍失败!'
            })
          }
        })
    },
    rejectedComplaint (complaint) {
      this.$axios.post('/complaint/unpassable', {
        id: complaint.id,
        username: complaint.username,
        content: complaint.content,
        time: complaint.time,
        state: '未通过',
        order: {id: complaint.order.id,
          begin: complaint.order.begin,
          end: new Date(),
          book: {
            id: complaint.order.book.id,
            category: {id: complaint.order.book.category.id, name: complaint.order.book.category.name},
            newness: complaint.order.book.newness,
            username: complaint.order.book.username,
            title: complaint.order.book.title,
            author: complaint.order.book.author,
            checked: complaint.order.book.checked,
            cover: complaint.order.book.cover,
            synopsis: complaint.order.book.synopsis,
            price: complaint.order.book.price,
            sold: complaint.order.book.sold},
          buyer: complaint.order.buyer,
          receive: complaint.order.receive,
          seller: complaint.order.seller,
          send: complaint.order.send,
          state: complaint.order.state
        }
      })
        .then(resp => {
          if (resp && resp.status === 200) {
            this.$message({
              type: 'success',
              message: '申诉驳回成功!'
            })
            this.reload()
          } else {
            this.$message({
              type: 'info',
              message: '申诉驳回失败!'
            })
          }
        })
    }
  }
}
</script>
<style scoped>

</style>
