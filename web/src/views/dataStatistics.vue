<!--
 * @Author: your name
 * @Date: 2022-04-21 11:50:43
 * @LastEditTime: 2022-04-21 18:21:52
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \web\src\views\dataStatistics.vue
-->
<template>
  <a-layout>
    <a-layout-header :style="{ position: 'fixed', zIndex: 1, width: '100%' }">
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
        欢迎进入数据分析界面
      </p>
    </a-layout-header>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
      <div
        id="articleCount"
        style="padding-top: 20px; height: 500px; width: 100%"
      ></div>
      <div
        id="keyWordCount"
        style="padding-top: 50px; height: 500px; width: 100%"
      ></div>
    </a-layout-content>

    <a-layout-footer :style="{ textAlign: 'center' }">
      partick@2022
    </a-layout-footer>
  </a-layout>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import * as echarts from "echarts";
import axios from "axios";

export default defineComponent({
  name: "dataStatistics",
  setup() {
    let url = "http://localhost:8700";

    const initKeyWordEcharts = (source) => {
      let chartDom = document.getElementById("keyWordCount");
      let myChart = echarts.init(chartDom);
      let option;
      option = {
        title: {
          text: "文章标题前十排行榜",
          textStyle: {
            fontSize: 20,
          },
          left: "center",
        },
        dataset: [
          {
            dimensions: ["keyWord", "docCount"],
            source: source,
          },
          {
            transform: {
              type: "sort",
              config: { dimension: "docCount", order: "desc" },
            },
          },
        ],
        xAxis: {
          type: "category",
          axisLabel: { interval: 0, rotate: 15 },
          nameTextStyle: {
            fontSize: 10,
            color: "#fff",
          },
        },
        yAxis: {},
        series: {
          type: "bar",
          encode: { x: "keyWord", y: "docCount" },
          datasetIndex: 1,
        },
      };

      option && myChart.setOption(option);
    };

    const getKeyWordCount = () => {
      axios.get(url + "/esArticle/queryTitleKeyWordCount").then((resp) => {
        initKeyWordEcharts(resp.data.content);
      });
    };

    const initArticleCount = (data) => {
      var chartDom = document.getElementById("articleCount");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: "论坛文章数据统计",
          textStyle: {
            fontSize: 20,
          },
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          top: "bottom",
        },
        series: [
          {
            name: "论坛名字",
            type: "pie",
            radius: "50%",
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };

      option && myChart.setOption(option);
    };

    const getArticleCount = () => {
      axios.get(url + "/esArticle/queryEsArticleCountByForum").then((resp) => {
        initArticleCount(resp.data.content);
      });
    };

    onMounted(() => {
      getKeyWordCount();
      getArticleCount();
    });
  },
});
</script>

<style>
</style>
