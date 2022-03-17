<template>
  <!-- hidden PageHeaderWrapper title demo -->
  <page-header-wrapper :title="false" :content="$t('form.basic-form.basic.description')">
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
      <a-form>
        <a-tabs default-active-key="1" tab-position="left">
          <a-tab-pane key="1" :tab="$t('dep-profile-tab1.title')">
            <a-form-item
              :label="$t('dep-profile.depname')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                name="depname"
                v-model="dep.depname"
                :placeholder="$t('form.basic-form.title.placeholder')" />
            </a-form-item>
            <a-form-item
              :label="$t('dep-profile.depcode')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                name="depcode"
                v-model="dep.depcode"
                :placeholder="$t('form.basic-form.title.placeholder')" />
            </a-form-item>
            <a-form-item
              :label="$t('dep-profile.parentid')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              <a-select
                show-search
                :value="dep.parentid"
                name="parentid"
                placeholder="input search text"
                style="width: 200px"
                :default-active-first-option="false"
                :show-arrow="false"
                :filter-option="false"
                :not-found-content="null"
                @search="handleSearch"
                @change="handleParentNodeChange"
              >
                <a-select-option v-for="d in parentNode" :key="d.value">
                  {{ d.text }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-tab-pane>
          <a-tab-pane key="2" :tab="$t('dep-profile-tab2.title')">
            <a-button type="primary" icon="plus" style="margin: 5px" @click="addBulletin()">
              {{ $t('dep-profile-news.add') }}
            </a-button>
            <a-table
              :columns="depNewsColumns"
              :row-key="record => record.id"
              :data-source="newsDataList"
              :pagination="pagination"
              :loading="loading"
              @change="handleTableChange"
            >
              <template slot="operation" slot-scope="text, record">
                <div class="editable-row-operations">
                  <span>
                    <a-button type="primary" size="small" @click="() => editnews(record.id)">
                      编辑
                    </a-button>
                  </span>
                  <span style="margin-left: 6px">
                    <a-popconfirm title="Sure to cancel?" @confirm="() => cancelnews(record.id)">
                      <a-button type="danger" size="small">删除</a-button>
                    </a-popconfirm>
                  </span>
                </div>
              </template>
            </a-table>
          </a-tab-pane>
        </a-tabs>
        <a-form-item
          :wrapperCol="{ span: 24 }"
          style="text-align: center"
        >
          <a-button @click="handleSubmit" type="primary">{{ $t('form.basic-form.form.submit') }}</a-button>
          <a-button style="margin-left: 8px">{{ $t('form.basic-form.form.save') }}</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { getOneDepProfile, getDepForSearchParentNode, getDepNewsPage } from '@/api/dep'

const depNewsColumns = [
  {
    title: '标题',
    dataIndex: 'newstitle',
    width: '20%'
  },
  {
    title: '日期',
    dataIndex: 'newsdate',
    width: '20%'
  },
  {
    title: '操作',
    dataIndex: 'operation',
    scopedSlots: { customRender: 'operation' }
  }
]

export default {
  name: 'DepProfile',
  data () {
    return {
      dep: {
        id: -1,
        depname: '',
        depcode: '',
        parentid: ''
      },
      newsDataList: [],
      userRoles: [],
      parentNode: [],
      loading: false,
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
      newsParams: {
        name: ''
      },
      depNewsColumns
    }
  },
  mounted () {
    this.dep.id = this.$route.params.id
    if (this.dep.id) {
      this.fetchProfile(this.dep.id)
      this.fetchNewsList()
    }
  },
  methods: {
    // handler
    handleSubmit () {
     /*
        saveUserInfo(this.user).then(res => {
          // console.log(res)
          if (res.status === 200) {
            this.$message.success(`成功保存${res.data}条记录`)
            this.$router.push('/dashboard/userlist')
          } else {
            this.$message.error(`保存失败`)
          }
        }).catch(e => {
          console.log(e)
          this.$message.error(`保存失败`)
        })
      */
    },
    // fetchProfile
    fetchProfile () {
      getOneDepProfile({
        id: parseInt(this.dep.id)
      }).then(res => {
        if (res.data) {
          this.dep.depname = res.data.name
          this.dep.depcode = res.data.depcode
          this.dep.parentid = res.data.parentid
        }
      }).catch(e => {
        this.$message.error('请求失败，Request failed!')
      })
    },
    handleSearch (value) {
      // console.log(value)
      if (value === '') return
      getDepForSearchParentNode(value).then(res => {
        this.parentNode = []
        if (res.data) {
          res.data.forEach(opt => {
            this.parentNode.push({
              value: opt.id,
              text: opt.name
            })
          })
        }
      })
    },
    handleParentNodeChange (value) {
      console.log(value)
      this.dep.parentid = value
    },
    handleTableChange (pagination, filters, sorter) {
      // console.log(pagination)
      const pager = { ...this.pagination }
      pager.current = pagination.current
      this.pagination = pager
      this.fetchNewsList({
        size: pagination.pageSize,
        page: pagination.current,
        ...filters
      })
    },
    fetchNewsList () {
      this.loading = true
      getDepNewsPage({
        size: this.pagination.pageSize, // 向后端请求的每页大小
        page: this.pagination.current, // 向后端请求的页码
        depid: this.dep.id,
        ...this.newsParams,
        ...this.queryParams
      }).then(res => {
        const pagination = { ...this.pagination }
        // console.log(res)
        this.newsDataList = res.data.list
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
    toEditPage (depid, newsid) {
      this.$router.push(`/dashboard/deplist/depnewsedit/${depid}/${newsid}`)
    },
    addBulletin () {
      this.toEditPage(this.dep.id, 'null')
    },
    editnews (id) {
      this.toEditPage(this.dep.id, id)
    },
    cancelnews (id) {
      console.log(id)
    }
  }
}
</script>
