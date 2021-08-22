<template>
  <div>
    <a-table
      :columns="columns"
      :row-key="record => record.id"
      :data-source="data"
      :pagination="pagination"
      :loading="loading"
      @change="handleTableChange"
    >
    </a-table>
  </div>
</template>

<script>
import { userList } from '@/api/admin'

const columns = [
  {
    title: '用户名',
    dataIndex: 'username',
    width: '20%'
  },
  {
    title: '登陆次数',
    dataIndex: 'logintimes',
    width: '20%'
  },
  {
    title: '最后登陆地址',
    dataIndex: 'lastloginaddr'
  },
  {
    title: '是否锁定',
    dataIndex: 'locked'
  }
]

export default {
  name: `UserListSummary`,
  data () {
    return {
      data: [],
      pagination: {
        size: 'small',
        defaultCurrent: 1, // 默认当前页数
        defaultPageSize: 5, // 默认当前页显示数据的大小
        total: 0, // 总数，必须先有
        showSizeChanger: true,
        pageSizeOptions: ['5', '10', '15', '20'], // 指定每页可以显示多少条
        // 显示条数改变后的处理
        onShowSizeChange: (current, pageSize) => {
          this.pagination.defaultCurrent = 1
          this.pagination.defaultPageSize = pageSize
          // this.handleTableChange(this.pagination); // 显示列表的接口名称
        }
      },
      loading: false,
      columns
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    handleTableChange (pagination, filters, sorter) {
      console.log(pagination)
      const pager = { ...this.pagination }
      pager.current = pagination.current
      this.pagination = pager
      this.fetch({
        rows: pagination.pageSize,
        page: pagination.current,
        ...filters
      })
    },
    fetch (params = {}) {
      this.loading = true
      userList({
        rows: this.pagination.pageSize, // 向后端请求的每页大小
        page: this.pagination.current, // 向后端请求的页码
        ...params
      }).then(res => {
        const pagination = { ...this.pagination }
        // console.log(res)
        this.data = res.data.list
        this.pagination.total = res.data.total
        this.pagination.current = res.data.pageNum // 当前页数
        this.pagination.pageSize = res.data.pageSize
        // Read total count from server
        // pagination.total = data.totalCount;
        this.loading = false
        this.pagination = pagination
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>
