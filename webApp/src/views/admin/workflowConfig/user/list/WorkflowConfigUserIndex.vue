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
      <a-button type="primary" @click="showAddUserDrawer"><a-icon type="plus" />{{ $t('workflow.config.addUser') }}</a-button>
      <a-drawer
        title="Create a new account"
        :width="720"
        :visible="visible"
        :body-style="{ paddingBottom: '80px' }"
        @close="onClose"
      >
        <a-form :form="addUserform" layout="vertical" hide-required-mark>
          <a-row :gutter="16">
            <a-col :span="12">
              <a-form-item label="User ID">
                <a-select
                  show-search
                  :value="userIdSelected"
                  placeholder="input search text"
                  style="width: 200px"
                  :default-active-first-option="false"
                  :show-arrow="false"
                  :filter-option="false"
                  :not-found-content="null"
                  @search="handleSearchUser"
                  @change="handleChangeUser"
                >
                  <a-select-option v-for="d in userSelectList" :key="d.id">
                    {{ d.username }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="In Workflow Name">
                <a-input
                  :value="inWorflowName"
                  placeholder="Please enter user name"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
        <div
          :style="{
            position: 'absolute',
            right: 0,
            bottom: 0,
            width: '100%',
            borderTop: '1px solid #e9e9e9',
            padding: '10px 16px',
            background: '#fff',
            textAlign: 'right',
            zIndex: 1,
          }"
        >
          <a-button :style="{ marginRight: '8px' }" @click="onClose">
            Cancel
          </a-button>
          <a-button type="primary" @click="onSubmit">
            Submit
          </a-button>
        </div>
      </a-drawer>
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
            <!--<span>
              <a-button type="primary" size="small" @click="() => edit(record.id)">编辑</a-button>
            </span>-->
            <span style="margin-left: 6px">
              <a-popconfirm title="Sure to cancel?" @confirm="() => cancel(record.id)">
                <a-button type="danger" size="small">{{ $t('form.basic.deleteBtn') }}</a-button>
              </a-popconfirm>
            </span>
          </div>
        </template>

      </a-table>
    </div>
  </div>
</template>

<script>
import { getWorkFlowUserList, deleteOneWfUser, addWfUser } from '@/api/workflowConfig'
import { getUserAllList } from '@/api/admin'

// const queryParamArray = [
//   {
//     filedName: `firstName`,
//     value: ``
//   },
//   {
//     filedName: `lastName`,
//     value: ``
//   }
// ]
//
// const columns = [
//   {
//     title: this.$t('workflow.config.user.firstname'),
//     dataIndex: 'firstName',
//     width: '20%'
//   },
//   {
//     title: this.$t('workflow.config.user.firstname'),
//     dataIndex: 'lastName',
//     width: '20%'
//   },
//   {
//     title: 'email',
//     dataIndex: 'email',
//     width: '20%'
//   },
//   {
//     title: '操作',
//     dataIndex: 'operation',
//     scopedSlots: { customRender: 'operation' }
//   }
// ]

export default {
  name: `workflowConfigUserIndex`,
  data () {
    return {
      queryParamArray: [],
      expand: false,
      defaultFiledCount: 0,
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
      columns: [],
      addUserform: this.$form.createForm(this),
      visible: false,
      userSelectList: [],
      userIdSelected: '',
      userSearch: '',
      inWorflowName: ''
    }
  },
  computed: {
    count () {
      return this.expand ? 2 : 1
    }
  },
  mounted () {
    this.queryParamArray = [
      {
        filedName: `firstName`,
        label: this.$t('workflow.config.user.firstname'),
        value: ``
      },
      {
        filedName: `lastName`,
        label: this.$t('workflow.config.user.lastname'),
        value: ``
      }
    ]
    this.columns = [
      {
        title: this.$t('workflow.config.user.firstname'),
        dataIndex: 'firstName',
        width: '20%'
      },
      {
        title: this.$t('workflow.config.user.firstname'),
        dataIndex: 'lastName',
        width: '20%'
      },
      {
        title: 'email',
        dataIndex: 'email',
        width: '20%'
      },
      {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' }
      }
    ]
    this.defaultFiledCount = this.queryParamArray.length
    this.fetch()
  },
  methods: {
    handleTableChange (pagination, filters, sorter) {
      console.log(pagination)
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
      getWorkFlowUserList({
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
         console.log('Received values of form: ', values)
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
      this.$router.push(`/systemConfiguration/rolelist/rolesource/${id}`)
    },
    cancel (id) {
      console.log(id)
      deleteOneWfUser({ id: id })
    },
    showAddUserDrawer () {
      this.visible = true
    },
    onClose () {
      this.visible = false
    },
    onSubmit () {
      addWfUser({
        id: this.userIdSelected,
        firstName: this.inWorflowName
      }).then(res => {
        console.log(res.data)
      })
      this.visible = false
    },
    handleSearchUser (value) {
      getUserAllList({ username: value }).then(res => {
        if (res.data) this.userSelectList = res.data
      })
    },
    handleChangeUser (data) {
      this.userIdSelected = data
      this.userSelectList.forEach(user => {
        if (user.id === data) {
          this.inWorflowName = user.staffname
        }
      })
      // this.inWorflowName = opt.componentOptions.children[0].elm.data.trim()
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
  padding: 8px;
}
</style>
