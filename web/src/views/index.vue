<template>
  <a-layout>
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo" />
      <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys"
        :style="{ lineHeight: '64px' }"
        @select="clickMenu(this.selectedKeys)"
      >
        <a-menu-item v-for="forum in forums" :key="forum.forumId">{{
          forum.forumName
        }}</a-menu-item>

        <a-menu-item>
          <router-link key="dataStatistics" to="/dataStatistics">
            数据分析
          </router-link>
        </a-menu-item>

        <a-input-search
          v-model:value="value"
          style="position: absolute; top: 15px; right: 500px; width: 300px"
          placeholder="请输入文章标题"
          enter-button
          @search="routeToSearch(value)"
        />

        <a-button
          type="danger"
          style="position: absolute; top: 15px; right: 340px"
          @click="putForumCategoryInfo"
          >同步博客分类信息</a-button
        >
        <a-button
          type="danger"
          style="position: absolute; top: 15px; right: 180px"
          @click="putArticleInfo"
          >同步文章概要信息</a-button
        >
        <a-button
          type="danger"
          style="position: absolute; top: 15px; right: 20px"
          @click="putArticleDetail"
          >同步文章详细信息</a-button
        >
      </a-menu>
    </a-layout-header>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
      <a-tabs v-model:activeKey="activeKey" @change="getArticleInfo(activeKey)">
        <a-tab-pane
          v-for="forumCategory in forumCategories"
          :key="forumCategory.forumNavId"
          :tab="forumCategory.forumNav"
          @ok="handleOk"
        >
          <a-list item-layout="horizontal" :data-source="articleInfo">
            <template #renderItem="{ item }">
              <a-list-item>
                <a-list-item-meta>
                  <template #title>
                    <a @click="showModal(item.articleInfoId, item.title)"
                      ><p>标题：{{ item.title }}</p></a
                    >
                  </template>
                  <template #description>
                    {{ item.summary }}
                    <p style="margin-bottom: 2px">文章地址：</p>
                    <a :href="item.url">{{ item.url }}</a>
                  </template>
                  <template> </template>
                </a-list-item-meta>
              </a-list-item>
            </template>
          </a-list>
          <a-modal
            v-model:visible="visible"
            :title="articleDetailTitle"
            :body-style="bodystyle"
            width="80%"
            @ok="handleOk"
          >
            <div v-html="articleDetailText"></div>
          </a-modal>
        </a-tab-pane>
      </a-tabs>
    </a-layout-content>
    <a-layout-footer :style="{ textAlign: 'center' }">
      partick@2022
    </a-layout-footer>
  </a-layout>
</template>
<script>
import { defineComponent, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

import { message } from "ant-design-vue";
import axios from "axios";

const bodystyle = {
  height: "480px",
  overflow: "hidden",
  overflowY: "scroll",
};
export default defineComponent({
  name: "index",
  setup() {
    let url = "http://localhost:8700";
    const forums = ref();
    const forumCategories = ref();
    const articleInfo = ref();
    const articleDetail = ref();
    const articleDetailTitle = ref();
    const articleDetailText = ref();
    const visible = ref(false);
    const value = ref("");
    const router = useRouter();

    const showModal = (id, title) => {
      getArticleDetail(id);
      visible.value = true;
    };

    const handleOk = () => {
      visible.value = false;
    };

    const getForums = () => {
      axios.get(url + "/forum/getForums").then((resp) => {
        let content = resp.data.content;
        forums.value = content;
      });
    };
    const clickMenu = (selectKey) => {
      getCategoryFormForumId(selectKey);
    };
    const getCategoryFormForumId = (selectKey) => {
      forumCategories.value = "";
      axios
        .get(url + "/forumCategory/getForumCategoryByForumId/" + selectKey)
        .then((resp) => {
          let content = resp.data.content;
          forumCategories.value = content;
          getArticleInfo(forumCategories.value[0].forumNavId);
        });
    };
    const getArticleInfo = (activeKey) => {
      axios
        .get(url + "/article/selectByForumNavId/" + activeKey)
        .then((resp) => {
          let content = resp.data.content;
          articleInfo.value = content;
        });
    };
    const getArticleDetail = (articleDetailId) => {
      axios
        .get(url + "/article/selectDetailById/" + articleDetailId)
        .then((resp) => {
          let content = resp.data.content;
          articleDetail.value = content;
          articleDetailTitle.value = articleDetail.value.title;
          articleDetailText.value = articleDetail.value.text;
        });
    };

    const putForumCategoryInfo = () => {
      axios.get(url + "/forumCategory/putForumCategoryInfo");
      message.success("正在同步，请稍后");
    };

    const putArticleInfo = () => {
      axios.get(url + "/article/putArticleInfo");
      message.success("正在同步，请稍后");
    };

    const putArticleDetail = () => {
      axios.get(url + "/article/putArticleDetail");
      message.success("正在同步，请稍后");
    };

    const routeToSearch = (value) => {
      router.push({
        name: "search",
        params: { value },
      });
    };

    const firstShowData = () => {
      axios.get(url + "/forum/getForums").then((resp) => {
        let content = resp.data.content;
        forums.value = content;
        axios
          .get(
            url +
              "/forumCategory/getForumCategoryByForumId/" +
              forums.value[0].forumId
          )
          .then((resp) => {
            let content = resp.data.content;
            forumCategories.value = content;
            getArticleInfo(forumCategories.value[0].forumNavId);
          });
      });
    };
    onMounted(() => {
      firstShowData();
    });
    return {
      forums,
      forumCategories,
      articleInfo,
      visible,
      articleDetail,
      articleDetailTitle,
      articleDetailText,
      value,
      showModal,
      handleOk,
      clickMenu,
      getArticleInfo,
      putForumCategoryInfo,
      putArticleInfo,
      putArticleDetail,
      routeToSearch,
    };
  },
});
</script>
<style>
</style>