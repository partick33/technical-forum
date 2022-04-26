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
        欢迎进入调度中心界面
      </p>
    </a-layout-header>
    <a-layout-content :style="{ padding: '0 50px', marginTop: '64px' }">
      <a-table 
      :columns="columns" 
      :data-source="dataSource" 
      :rowKey="(record,index)=>{return index}"
      bordered>
        <template
          v-for="col in ['jobName', 'jobGroupName', 'jobStatus', 'cron']"
          #[col]="{ text, record }"
          :key="col"
        >
          <div>
            <a-input
              v-if="editableData[record.key]"
              v-model:value="editableData[record.key][col]"
              style="margin: -5px 0"
            />
            <template v-else>
              {{ text }}
            </template>
          </div>
        </template>
        <template #operation="{ record }">
          <div class="editable-row-operations">
            <span v-if="editableData[record.key]">
              <a @click="save(record.key)">保存</a>
              <a-popconfirm title="是否取消修改" @confirm="cancel(record.key)">
                <a style="padding-left: 30px">取消</a>
              </a-popconfirm>
            </span>
            <span v-else>
              <a @click="edit(record.key)">修改</a>
            </span>
            <span style="padding-left: 30px">
              <a @click="start(record.key)">启动</a>
            </span>
            <span style="padding-left: 30px">
              <a @click="pause(record.key)">停止</a>
            </span>
          </div>
        </template>
      </a-table>
    </a-layout-content>

    <a-layout-footer :style="{ textAlign: 'center' }">
      partick@2022
    </a-layout-footer>
  </a-layout>
</template>

<script>
import { cloneDeep } from "lodash-es";
import { defineComponent, onMounted, ref, reactive } from "vue";
import axios from "axios";
import { message } from "ant-design-vue";
export default defineComponent({
  name: "job",
  setup() {
    let url = "http://localhost:8700";

    const columns = [
      {
        title: "任务名称",
        dataIndex: "jobName",
        width: "15%",
      },
      {
        title: "任务组名称",
        dataIndex: "jobGroupName",
        width: "15%",
      },
      {
        title: "任务状态",
        dataIndex: "jobStatus",
        width: "10%",
      },
      {
        title: "cron表达式",
        dataIndex: "cron",
        width: "20%",
        slots: {
          customRender: "cron",
        },
      },
      {
        title: "操作",
        dataIndex: "operation",
        slots: {
          customRender: "operation",
        },
      },
    ];

    const dataSource = ref();
    const editableData = reactive({});

    const edit = (key) => {
      editableData[key] = cloneDeep(
        dataSource.value.filter((item) => key === item.key)[0]
      );
    };

    const save = (key) => {
      Object.assign(
        dataSource.value.filter((item) => key === item.key)[0],
        editableData[key]
      );
      axios.post(url + "/job/updateJob", editableData[key]).then((resp) => {
        message.success(resp.data.message);
      });
      delete editableData[key];
    };

    const cancel = (key) => {
      delete editableData[key];
    };

    const getJobs = () => {
      axios.get(url + "/job/queryAllJob").then((resp) => {
        dataSource.value = resp.data.content;
      });
    };

    const start = (key) => {
      editableData[key] = cloneDeep(
        dataSource.value.filter((item) => key === item.key)[0]
      );
      axios.post(url + "/job/startJob", editableData[key]).then((resp) => {
        message.success(resp.data.message);
      });
      delete editableData[key];
      getJobs();
    };

    const pause = (key) => {
      editableData[key] = cloneDeep(
        dataSource.value.filter((item) => key === item.key)[0]
      );
      axios.post(url + "/job/pauseJob", editableData[key]).then((resp) => {
        message.success(resp.data.message);
      });
      delete editableData[key];
      getJobs();
    };

    onMounted(() => {
      getJobs();
    });

    return {
      dataSource,
      columns,
      editingKey: "",
      editableData,
      edit,
      save,
      cancel,
      start,
      pause,
    };
  },
});
</script>

<style>
</style>