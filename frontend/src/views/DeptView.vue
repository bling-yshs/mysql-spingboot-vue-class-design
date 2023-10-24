<template>

  <a-table
      bordered
      ref="e"
      :columns="deptTableColumns"
      :data-source="deptTableDataSource"
      :loading="isLoading"
  >
    <template #bodyCell="{column,record}">
      <template v-if="column.dataIndex==='edit'">
        <a-space size="middle">
          <a-button type="primary" @click="editRow(record)">修改</a-button>
        </a-space>
      </template>
    </template>
  </a-table>
  <div style="text-align: right">
    <a-button type="primary" @click="()=>isAddNewDeptOpen=true">新增部门</a-button>
  </div>

  <a-modal v-model:open="isAddNewDeptOpen" title="新增员工" @cancel="()=>isAddNewDeptOpen=false"
           @ok="handleAddNewDeptOk"
           ok-text="确认"
           cancel-text="取消">
    <a-form
        ref="newDeptFormRef"
        :rules="rules"
        :model="newDeptFormState"
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 10 }"
        autocomplete="off"
    >
      <a-form-item
          label="部门编号"
          name="deptId"
      >
        <a-input-number v-model:value="newDeptFormState.deptId"/>
      </a-form-item>

      <a-form-item
          label="部门名称"
          name="deptName"
      >
        <a-input v-model:value="newDeptFormState.deptName"/>
      </a-form-item>

      <a-form-item
          label="部门主管"
          name="deptManagerName"
      >

        <a-select @click="getAllEmployeeNameList" v-model:value="newDeptFormState.deptManagerName">
          <a-select-option v-for="item in allEmployeeNameList" :value="item"/>
        </a-select>
      </a-form-item>

    </a-form>
  </a-modal>

  <a-modal v-model:open="isOpenRowEdit" title="修改部门信息" @cancel="()=>isOpenRowEdit=false" @ok="handleRowEditOk"
           ok-text="确认"
           cancel-text="取消">
    <a-form
        ref="editFormRef"
        :rules="rules"
        :model="editFormState"
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 10 }"
        autocomplete="off"
    >
      <a-form-item
          label="部门名称"
          name="deptName"
      >
        <a-input v-model:value="editFormState.deptName"/>
      </a-form-item>

      <a-form-item
          label="部门主管"
          name="deptManagerName"
      >
        <a-select
            @click="getDeptEmpNameList(editFormState.deptId)" v-model:value="editFormState.deptManagerName"
        >
          <a-select-option v-for="item in deptEmpNameList" :value="item"/>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";
import type {Rule} from "ant-design-vue/es/form";
import {message} from "ant-design-vue";

/**
 * 表格状态
 */

let isLoading = ref<boolean>(true)

/**
 * 新增部门
 */

const handleAddNewDeptOk = () => {
  newDeptFormRef.value
      .validate()
      .then(() => {
            axios.post("/department/add", newDeptFormState.value).then(value => {
              if (value.data.code === 200) {
                message.success("添加成功")
                getList()
              } else {
                message.error(value.data.message)
              }
              //清空表单
              newDeptFormState.value = {
                deptId: "",
                deptName: "",
                deptManagerName: ""
              }
              isAddNewDeptOpen.value = false
            })
          }
      )
}

let isAllNameGetting = ref<boolean>(false)
const getAllEmployeeNameList = () => {
  axios.get("/employee/getAllEmployeeNameList").then(value => {
    allEmployeeNameList.value = value.data.data
  })
}

let allEmployeeNameList = ref<Array<string>>([])

let newDeptFormRef = ref()
let newDeptFormState = ref<Department>({
  deptId: "",
  deptName: "",
  deptManagerName: ""
})

let isAddNewDeptOpen = ref<boolean>(false)


/**
 * 修改行
 */

let deptEmpNameList = ref<Array<string>>([])

// 得到部门下所有员工的名称
const getDeptEmpNameList = (deptId: string) => {
  axios.get(`/department/getDepartmentEmployeeNameList?deptId=${deptId}`
  ).then(value => {
    deptEmpNameList.value = value.data.data
  })
}

const rules: Record<string, Rule[]> = {
  deptId: [
    {required: true, message: '请输入部门编号', trigger: 'blur'},
    {type: 'number', min: 1, message: '部门编号必须为正数', whitespace: true, trigger: ['change', 'blur']},
  ],
  deptName: [
    {required: true, message: '请输入部门名称', trigger: ['change', 'blur']},
  ],
  deptManagerName: [
    {required: true, message: '请选择部门主管', trigger: ['change', 'blur']},
  ]
}

let editFormRef = ref()

let editFormState = ref<Department>({
  deptId: "",
  deptName: "",
  deptManagerName: ""
})


let isOpenRowEdit = ref<boolean>(false)

const editRow = (record: Department) => {
  Object.assign(editFormState.value, record)
  isOpenRowEdit.value = true
}

const handleRowEditOk = () => {
  editFormRef.value
      .validate()
      .then(() => {
            axios.put("/department/update", editFormState.value).then(value => {
              if (value.data.code === 200) {
                getList()
              }
            })
          }
      )
  isOpenRowEdit.value = false
}

/**
 * 表格列
 */
type Column = {
  align: string;
  title: string;
  dataIndex: string;
  width?: string;
}

let deptTableColumns = ref<Array<Column>>([
  {
    align: "center",
    title: "部门编号",
    dataIndex: "deptId"
  },
  {
    align: "center",
    title: "部门名称",
    dataIndex: "deptName"
  },
  {
    align: "center",
    title: "部门主管",
    dataIndex: "deptManagerName"
  },
  {
    align: 'center',
    title: '操作',
    dataIndex: 'edit',
    width: "10%",
  },
])

/**
 * 表格数据源
 */
interface Department {
  deptId: string;
  deptName: string;
  deptManagerName: string;
}

let deptTableDataSource = ref<Array<Department>>([])

onMounted(() => {
  getList()
})

const getList = () => {
  isLoading.value = true
  axios.get("/department/getList").then(value => {
        if (value.data.code === 200) {
          let deptList = value.data.data as Department[];
          deptTableDataSource.value = deptList
          isLoading.value = false
        }
      }
  )
}
</script>