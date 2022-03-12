<template>
  <div id="components-form-demo-advanced-search">
    <a-form class="ant-advanced-search-form" :form="form" @submit="handleSearch">
      <a-row :gutter="24">
        <a-col
          v-for="(item,index) in queryParamArray"
          :key="index"
          :span="8"
          :style="{ display: index < count ? 'block' : 'none' }"
        >
          <a-form-item :label="item.label">
            <a-input
              v-decorator="[
                item.filedName,
                {
                  rules: [
                    {
                      required: false,
                      message: 'Input something!',
                    },
                  ],
                },
              ]"
              placeholder="placeholder"
            />
          </a-form-item>
        </a-col>
        <a-col :span="24" :style="{ textAlign: 'right' }">
          <a-button type="primary" html-type="submit">
            Search
          </a-button>
          <a-button :style="{ marginLeft: '8px' }" @click="handleReset">
            Clear
          </a-button>
          <a :style="{ marginLeft: '8px', fontSize: '12px' }" @click="toggle">
            Collapse <a-icon :type="expand ? 'up' : 'down'" />
          </a>
        </a-col>
      </a-row>
    </a-form>
    <div class="search-result-list">
      <a-table
        :columns="columns"
        :row-key="record => record.id"
        :data-source="data"
        :pagination="pagination"
        :loading="loading"
        @change="handleTableChange"
      >
        <template slot="operation" slot-scope="text, record">
          <div class="editable-row-operations">
            <span>
              <a-button type="primary" size="small" @click="() => edit(record.id)">
                编辑
              </a-button>
            </span>
            <span style="margin-left: 6px">
              <a-popconfirm title="Sure to cancel?" @confirm="() => cancel(record.id)">
                <a-button type="danger" size="small">删除</a-button>
              </a-popconfirm>
            </span>
          </div>
        </template>

      </a-table>
    </div>
  </div>
</template>

<script>
import { getDepPage } from '@/api/dep'

const queryParamArray = [
  {
    filedName: `name`,
    label: `部门名`,
    value: ``
  },
  {
    filedName: `depcode`,
    label: `部门编码`,
    value: ``
  }
]

const columns = [
  {
    title: '部门名',
    dataIndex: 'name',
    width: '20%'
  },
  {
    title: '部门编码',
    dataIndex: 'depcode',
    width: '20%'
  },
  {
    title: '操作',
    dataIndex: 'operation',
    scopedSlots: { customRender: 'operation' }
  }
]

export default {
  name: `DepListSummary`,
  data () {
    return {
      queryParamArray: [],
      expand: false,
      defaultFiledCount: queryParamArray.length,
      form: this.$form.createForm(this, { name: 'advanced_search' }),
      data: [],
      pagination: {
        size: 'small',
        defaultCurrent: 1, // 默认当前页数
        defaultPageSize: 10, // 默认当前页显示数据的大小
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
      queryParams: {},
      columns
    }
  },
  computed: {
    count () {
      return this.expand ? 2 : 1
    }
  },
  mounted () {
    this.queryParamArray = queryParamArray
    this.fetch()
  },
  methods: {
    handleTableChange (pagination, filters, sorter) {
      // console.log(pagination)
      const pager = { ...this.pagination }
      pager.current = pagination.current
      this.pagination = pager
      this.fetch({
        size: pagination.pageSize,
        page: pagination.current,
        ...filters
      })
    },
    fetch (params = {}) {
      this.loading = true
      getDepPage({
        size: this.pagination.pageSize, // 向后端请求的每页大小
        page: this.pagination.current, // 向后端请求的页码
        ...params,
        ...this.queryParams
      }).then(res => {
        const pagination = { ...this.pagination }
        // console.log(res)
        this.data = res.data.list
        pagination.total = res.data.total
        pagination.current = res.data.pageNum // 当前页数
        pagination.pageSize = res.data.pageSize
        // Read total count from server
        // pagination.total = data.totalCount;
        this.loading = false
        this.pagination = pagination
      }).catch(err => {
        console.log(err)
      })
    },
    handleSearch (e) {
      e.preventDefault()
      this.form.validateFields((error, values) => {
        console.log('error', error)
        // console.log('Received values of form: ', values)
        this.queryParams = values
        this.fetch()
      })
    },

    handleReset () {
      this.form.resetFields()
    },

    toggle () {
      this.expand = !this.expand
    },
    edit (id) {
      // console.log(id)
      this.$router.push(`/dashboard/deplist/depprofile/${id}`)
    },
    cancel (id) {
      console.log(id)
    }
  }
}
</script>

<style scoped>
.ant-advanced-search-form {
  padding: 24px;
  background: #fbfbfb;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
}

.ant-advanced-search-form .ant-form-item {
  display: flex;
}

.ant-advanced-search-form .ant-form-item-control-wrapper {
  flex: 1;
}

#components-form-demo-advanced-search .ant-form {
  max-width: none;
}
#components-form-demo-advanced-search .search-result-list {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 6px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 80px;
}
</style>
