<template>
  <div>
    <div>
      <el-form :inline="true" @submit.native.prevent>
        <el-form-item>
          <el-input
            class="searching"
            placeholder="通过书名/买家/卖家搜索..."
            prefix-icon="el-icon-search"
            size="middle"
            style="width: 200px;margin-right: 10px;"
            v-model="keywords">
          </el-input>
          <el-button type="primary" round @click="searchResult">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-dropdown >
  <span class="el-dropdown-link">
    订单类型<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
            <el-dropdown-menu slot="dropdown" >
              <el-dropdown-item ></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" round @click="fresh">刷新</el-button>
        </el-form-item>
      </el-form>
    </div>
  <el-table
    :data="orders.slice().reverse()"
    style="width: 100%">
    <el-table-column
    prop="id"
    label="订单编号"
    width="60">
  </el-table-column>
    <el-table-column
      prop="book.title"
      label="书名"
      width="110">
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
      prop="buyer"
      label="买家"
      width="130">
    </el-table-column>
    <el-table-column
      prop="send"
      label="发货地址"
      width="150">
    </el-table-column>
    <el-table-column
      prop="receive"
      label="收货地址"
      width="150">
    </el-table-column>
    <el-table-column
      prop="book.price"
      label="订单价格"
      width="90">
    </el-table-column>
    <el-table-column
      prop="state"
      label="当前状态"
      width="150">
    </el-table-column>
    <!--<el-table-column
      label="操作"
      width="180">
      <template v-slot:default="scope">
      <el-row>
        <el-button type="primary" icon="el-icon-edit" :disabled="scope.row.state !== '申诉中'" circle></el-button>
      </el-row>
      </template>
    </el-table-column>-->
  </el-table>
  </div>
</template>

<script>
export default {
  name: 'OrderControl',
  data () {
    return {
      orders: [],
      keywords: ''
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
      this.$axios.get('/allorder').then(resp => {
        if (resp && resp.status === 200) {
          _this.orders = resp.data
        }
      })
    },
    searchResult () {
      var _this = this
      this.$axios
        .post('/order/search?keywords=' + this.keywords, {}).then(resp => {
          if (resp && resp.status === 200) {
            _this.orders = resp.data
          }
        })
    },
    fresh () {
      this.$router.go(0)
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
