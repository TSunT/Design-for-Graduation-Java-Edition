<template>
  <page-header-wrapper>
    <template v-slot:content>
      <div class="page-header-content">
        <div class="avatar">
          <a-avatar size="large" :src="userAvatar" />
        </div>
        <div class="content">
          <div class="content-title">
            {{ timeFix }}，{{ nickname }}<span class="welcome-text">，{{ welcome }}</span>
          </div>
          <div>前端工程师 | 蚂蚁金服 - 某某某事业群 - VUE平台</div>
        </div>
      </div>
    </template>
    <template v-slot:extraContent>
      <div class="extra-content">
        <div class="stat-item">
          <a-statistic title="项目数" :value="56" />
        </div>
        <div class="stat-item">
          <a-statistic title="团队内排名" :value="8" suffix="/ 24" />
        </div>
        <div class="stat-item">
          <a-statistic title="项目访问" :value="2223" />
        </div>
      </div>
    </template>

    <div>
      <a-row :gutter="24">
        <a-col :xl="16" :lg="24" :md="24" :sm="24" :xs="24">
          <a-card :loading="loading" title="部门动态" :bordered="false" style="margin-bottom: 24px">
            <a-list>
              <a-list-item :key="index" v-for="(item, index) in activities">
                <a-list-item-meta>
                  <div slot="title">
                    <!--<span>{{ item.newstitle }}</span>&nbsp;-->
                    <a href="#" @click="toDepNewsDetail(`${item.id}`)" >{{ item.newstitle }}</a>
                  </div>
                  <div slot="description">{{ item.newsdate }}</div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
            <a-pagination simple :default-current="activitiesPagination.currentPage" :total="activitiesPagination.total" :pageSize="activitiesPagination.pageSize" style="float: right"/>
          </a-card>
          <a-card :loading="loading" title="我的代办" :bordered="false">
            <div class="members">
              <a-row>
                <a-col :span="12" v-for="(item, index) in teams" :key="index">
                  <a>
                    <!--<a-avatar size="small" :src="item.avatar" />-->
                    <span class="member">{{ item.name }}</span>
                  </a>
                </a-col>
              </a-row>
            </div>
          </a-card>
        </a-col>
        <a-col
          style="padding: 0 12px"
          :xl="8"
          :lg="24"
          :md="24"
          :sm="24"
          :xs="24">
          <a-card
            title="快速开始 / 便捷导航"
            style="margin-bottom: 24px"
            :bordered="false"
            :body-style="{ padding: 0 }"
          >
            <div class="item-group">
              <a>操作一</a>
              <a>操作二</a>
              <a>操作三</a>
              <a>操作四</a>
              <a>操作五</a>
              <a>操作六</a>
              <a-button size="small" type="primary" ghost icon="plus">添加</a-button>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </page-header-wrapper>
</template>

<script>
import { timeFix } from '@/utils/util'
import { mapState } from 'vuex'
import { PageHeaderWrapper } from '@ant-design-vue/pro-layout'
import { Radar } from '@/components'

import { getDepNewsByUser } from '@/api/workplace'

export default {
  name: 'Workplace',
  components: {
    PageHeaderWrapper,
    Radar
  },
  data () {
    return {
      timeFix: timeFix(),
      avatar: '',
      user: {},

      projects: [],
      loading: true,
      radarLoading: false,
      activities: [],
      teams: [],

      pageSize: 10,
      activitiesPagination: {
        pageSize: 10,
        total: 0,
        currentPage: 1
      }
      // data
      // axis1Opts: {
      //   dataKey: 'item',
      //   line: null,
      //   tickLine: null,
      //   grid: {
      //     lineStyle: {
      //       lineDash: null
      //     },
      //     hideFirstLine: false
      //   }
      // },
      // axis2Opts: {
      //   dataKey: 'score',
      //   line: null,
      //   tickLine: null,
      //   grid: {
      //     type: 'polygon',
      //     lineStyle: {
      //       lineDash: null
      //     }
      //   }
      // },
      // scale: [
      //   {
      //     dataKey: 'score',
      //     min: 0,
      //     max: 80
      //   }
      // ],
      // axisData: [
      //   { item: '引用', a: 70, b: 30, c: 40 },
      //   { item: '口碑', a: 60, b: 70, c: 40 },
      //   { item: '产量', a: 50, b: 60, c: 40 },
      //   { item: '贡献', a: 40, b: 50, c: 40 },
      //   { item: '热度', a: 60, b: 70, c: 40 },
      //   { item: '引用', a: 70, b: 50, c: 40 }
      // ],
      // radarData: []
    }
  },
  computed: {
    ...mapState({
      nickname: state => state.user.name,
      welcome: state => state.user.welcome,
      userAvatar: state => state.user.avatar
    }),
    // currentUser () {
    //   return {
    //     name: 'Serati Ma',
    //     avatar: 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png'
    //   }
    // },
    userInfo () {
      return this.$store.getters.userInfo
    }
  },
  created () {
    this.user = this.userInfo
    this.avatar = this.userInfo.avatar

    // getRoleList().then(res => {
    //   // console.log('workplace -> call getRoleList()', res)
    // })
    //
    // getServiceList().then(res => {
    //   // console.log('workplace -> call getServiceList()', res)
    // })
  },
  mounted () {
    this.getActivity()
  },
  methods: {
    getProjects () {

    },
    getActivity () {
      getDepNewsByUser({
        page: this.activitiesPagination.currentPage,
        size: this.activitiesPagination.pageSize
      }).then(res => {
        if (res.data) {
          this.activities = res.data.list
          this.activitiesPagination.total = res.data.total
          this.activitiesPagination.currentPage = res.data.pageNum
          this.activitiesPagination.pageSize = res.data.pageSize
          this.loading = false
        }
      })
    },
    toDepNewsDetail (id) {
      this.$router.push(`/dashboard/depNewsView/${id}`)
    }
  }
}
</script>

<style lang="less" scoped>
@import './Workplace.less';

.project-list {
  .card-title {
    font-size: 0;

    a {
      color: rgba(0, 0, 0, 0.85);
      margin-left: 12px;
      line-height: 24px;
      height: 24px;
      display: inline-block;
      vertical-align: top;
      font-size: 14px;

      &:hover {
        color: #1890ff;
      }
    }
  }

  .card-description {
    color: rgba(0, 0, 0, 0.45);
    height: 44px;
    line-height: 22px;
    overflow: hidden;
  }

  .project-item {
    display: flex;
    margin-top: 8px;
    overflow: hidden;
    font-size: 12px;
    height: 20px;
    line-height: 20px;

    a {
      color: rgba(0, 0, 0, 0.45);
      display: inline-block;
      flex: 1 1 0;

      &:hover {
        color: #1890ff;
      }
    }

    .datetime {
      color: rgba(0, 0, 0, 0.25);
      flex: 0 0 auto;
      float: right;
    }
  }

  .ant-card-meta-description {
    color: rgba(0, 0, 0, 0.45);
    height: 44px;
    line-height: 22px;
    overflow: hidden;
  }
}

.item-group {
  padding: 20px 0 8px 24px;
  font-size: 0;

  a {
    color: rgba(0, 0, 0, 0.65);
    display: inline-block;
    font-size: 14px;
    margin-bottom: 13px;
    width: 25%;
  }
}

.members {
  a {
    display: block;
    margin: 12px 0;
    line-height: 24px;
    height: 24px;

    .member {
      font-size: 14px;
      color: rgba(0, 0, 0, 0.65);
      line-height: 24px;
      max-width: 100px;
      vertical-align: top;
      margin-left: 12px;
      transition: all 0.3s;
      display: inline-block;
    }

    &:hover {
      span {
        color: #1890ff;
      }
    }
  }
}

.mobile {
  .project-list {
    .project-card-grid {
      width: 100%;
    }
  }

  .more-info {
    border: 0;
    padding-top: 16px;
    margin: 16px 0 16px;
  }

  .headerContent .title .welcome-text {
    display: none;
  }
}
</style>
