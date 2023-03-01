<template>
  <el-container class="nr" style="background-color:white;">
    <el-main>
      <user-books class="books-area" ref="booksArea"></user-books>
    </el-main>
  </el-container>
</template>

<script>
import UserBooks from './UserBook'

export default {
  name: 'managebook',
  components: {UserBooks}
}
</script>

<style scoped>

</style>
