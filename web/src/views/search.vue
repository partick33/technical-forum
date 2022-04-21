<template>
  <a-layout>
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
      <div class="logo" />
      <a-menu
        theme="dark"
        mode="horizontal"
        v-model:selectedKeys="selectedKeys"
        :style="{ lineHeight: '64px' }"
      >
        <p
          style="
            position: absolute;
            top: 15px;
            font-size: 20px;
            line-height: 30px;
            left: 100px;
            color: white;
          "
        >
          欢迎进入搜索界面
        </p>
        <a-input-search
          v-model:value="value"
          style="position: absolute; top: 15px; right: 550px; width: 300px"
          placeholder="请输入文章标题"
          enter-button
          @search="queryLikeTitle(value)"
        />
      </a-menu>
    </a-layout-header>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
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
    </a-layout-content>
    <a-layout-footer :style="{ textAlign: 'center' }">
      partick@2022
    </a-layout-footer>
  </a-layout>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const bodystyle = {
  height: "480px",
  overflow: "hidden",
  overflowY: "scroll",
};
export default defineComponent({
  name: "search",
  setup() {
    const router = useRouter();
    const articleInfo = ref();
    const articleDetailTitle = ref();
    const articleDetailText = ref();
    const value = ref("");
    const visible = ref(false);
    const articleDetail = ref();

    let url = "http://localhost:8700";

    const showModal = (id, title) => {
      getArticleDetail(id);
      visible.value = true;
    };

    const handleOk = () => {
      visible.value = false;
    };

    const queryLikeTitle = (value) => {
      value = value;
      axios
        .get(url + "/esArticle/queryLikeTitle", { params: { title: value } })
        .then((resp) => {
          articleInfo.value = resp.data.content;
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

    onMounted(() => {
      queryLikeTitle(router.currentRoute.value.params);
    });
    return {
      value,
      articleInfo,
      articleDetail,
      articleDetailTitle,
      articleDetailText,
      visible,
      queryLikeTitle,
      getArticleDetail,
      showModal,
      handleOk,
    };
  },
});
</script>

<style>
</style>
