<template>
  <div class="dashboard-editor-container">
    <!--<github-corner class="github-corner" />-->
    <panel-group />
    <el-row style="background:#ffffff;padding:0px 16px 0;margin-bottom:32px;">
      <div style="font-size: large">近七天订单数与期望订单数</div>
    <line-chart :chart-data="lineChartData"/>
    </el-row>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8" :span="16" :offset="2">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8" :span="16" :offset="2">
        <div class="chart-wrapper">
          <book-pie-chart />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './adminComponents/PanelGroup'
import LineChart from './adminComponents/LineChart'
import PieChart from './adminComponents/PieChart'
import RaddarChart from './adminComponents/RaddarChart'
import BarChart from './adminComponents/BarChart'
import BookPieChart from './adminComponents/BookPieChart'
const lineChartData = {
  expectedData: [4, 2, 1, 3, 3, 4, 2],
  actualData: []
}

export default {
  name: 'DashboardAdmin',
  components: {
    // GithubCorner,
    PanelGroup,
    LineChart,
    PieChart,
    RaddarChart,
    BarChart,
    BookPieChart
  },
  data () {
    return {
      lineChartData: lineChartData
    }
  },
  created () {
    this.getActualData()
  },
  methods: {
    getActualData () {
      var url = '/order/weekly'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.lineChartData.actualData = resp.data
        }
      })
    }
  }
}

</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: #f6f6f6;
  position: relative;
  margin: -20px 0 0 -10px;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
