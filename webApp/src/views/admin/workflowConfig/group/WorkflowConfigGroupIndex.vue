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
      <a-button type="primary" @click="showGroupConfig"><a-icon type="plus" />{{ $t('workflow.config.addUser') }}</a-button>
      <a-drawer
        title="Configuration a group"
        :width="720"
        :visible="visible"
        :body-style="{ paddingBottom: '80px' }"
        @close="onClose"
      >
        <a-form layout="vertical">
          <a-row :gutter="16">
            <a-col :span="24">
              <a-form-item label="Group ID">
                <a-input
                  v-model="editGroup.id"
                  placeholder="Please enter Group ID"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="12">
              <a-form-item label="Group Name">
                <a-input
                  v-model="editGroup.name"
                  placeholder="Please enter Group name"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="Group Type">
                <a-input
                  v-model="editGroup.type"
                  placeholder="Please enter Group Type"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="16">
            <a-col :span="24">
              <a-form-item label="Group Membership">
                <a-transfer
                  :dataSource="allUserList"
                  showSearch
                  :targetKeys="selectedUser"
                  :render="item => item.title"
                  @change="handleMemberShipChange"
                  :list-style="{
                    width: '300px',
                    height: '600px',
                  }"
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
        :row-key="record => record.firstName"
        :data-source="data"
        :pagination="pagination"
        :loading="loading"
        @change="handleTableChange"
      >
        <template slot="operation" slot-scope="text, record">
          <div class="editable-row-operations">
            <span>
              <a-button type="primary" size="small" @click="() => getOneGroupInfo(record.id)">编辑</a-button>
            </span>
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
import { getWfGroupPage, deleteOneWfGroup, addWfGroup, getAllUser, getOneGroupInfo } from '@/api/workflowConfig'
import { getUserAllList } from '@/api/admin'

/*  const queryParamArray = [
  {
    filedName: `name`,
    label: this.$t('workflow.config.group.name'),
    value: ``
  },
  {
    filedName: `type`,
    label: this.$t('workflow.config.group.type'),
    value: ``
  }
]

const columns = [
  {
    title: this.$t('workflow.config.group.name'),
    dataIndex: 'name',
    width: '20%'
  },
  {
    title: 'lastName',
    dataIndex: this.$t('workflow.config.group.type'),
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
] */

export default {
  name: `WorkflowConfigGroupIndex`,
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
      columns: null,
      editGroup: {
        id: '',
        name: '',
        type: ''
      },
      visible: false,
      allUserList: [],
      selectedUser: [],
      userSearch: ''
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
        filedName: `groupName`,
        label: this.$t('workflow.config.group.name'),
        value: ``
      },
      {
        filedName: `type`,
        label: this.$t('workflow.config.group.type'),
        value: ``
      }
    ]
    this.columns = [
      {
        title: this.$t('workflow.config.group.name'),
        dataIndex: 'name',
        width: '20%'
      },
      {
        title: 'lastName',
        dataIndex: this.$t('workflow.config.group.type'),
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
    getAllUser().then(res => {
      if (res.data) {
        const tempList = res.data
        tempList.forEach(user => {
          this.allUserList = [...this.allUserList, { key: user.id, title: user.firstName }]
        })
      }
    })
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
      getWfGroupPage({
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
      deleteOneWfGroup({ id: id })
    },
    showGroupConfig () {
      this.visible = true
    },
    onClose () {
      this.visible = false
    },
    onSubmit () {
      addWfGroup({
        editGroup: this.editGroup,
        membershipList: this.selectedUser
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
    },
    handleMemberShipChange (data) {
      console.log(data)
      this.selectedUser = data
    },
    getOneGroupInfo (groupId) {
      getOneGroupInfo({
        groupId: groupId
      }).then(res => {
        if (res.data) {
          this.editGroup = res.data.editGroup
          this.selectedUser = res.data.membershipList
          this.visible = true
        } else {
          this.$message.error('请求失败，Request failed!')
        }
      }).catch(e => {
        console.log(e)
        this.$message.error('请求失败，Request failed!')
      })
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
