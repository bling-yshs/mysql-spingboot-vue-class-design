<template>
  <a-space style="margin: 0 0 1rem 0" class="horizontally">
    <a-space-compact block>
      <a-select
          ref="select"
          v-model:value="currentSelectValue"
          style="min-width: 7rem"
          :options="options1"
          @focus="focus"
          @change="handleChange"
      />
      <a-input v-model:value="inputValue" placeholder="请输入内容"
               style="min-width: 20rem"/>
      <a-button type="primary" @click="getSpecifiedList">查询</a-button>
      <a-button type="dashed" @click="resetTable">重置</a-button>
    </a-space-compact>
  </a-space>
  <a-table
      ref="e"
      :columns="empTableColumns"
      :data-source="empTableDataSource"
      bordered
      :loading="isLoading"
  >
    <template #bodyCell="{column,record}">
      <template v-if="column.dataIndex==='edit'">
        <a-space size="middle">
          <a-button type="primary" @click="handleEdit(record)">修改</a-button>
          <a-popconfirm
              title="确认要删除吗？"
              @confirm="onDelete(record)"
              cancel-text="取消"
              ok-text="确认"
          >
            <a-button type="primary" danger>删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </template>
  </a-table>
  <div style="text-align: right">
    <a-button type="primary" @click="showModal">新增员工</a-button>
  </div>
  
  <a-modal v-model:open="open" title="新增员工" @cancel="handleCancel" @ok="handleOk" ok-text="确认"
           cancel-text="取消">
    <a-form
        ref="formRef"
        :rules="rules"
        :model="formState"
        :label-col="{ span: 8 }"
        :wrapper-col="{ span: 10 }"
        autocomplete="off"
    >
      <a-form-item
          label="员工号"
          name="empId"
      >
        <a-input-number v-model:value="formState.empId"/>
      </a-form-item>
      
      <a-form-item
          label="姓名"
          name="name"
      >
        <a-input v-model:value="formState.name"/>
      </a-form-item>
      
      <a-form-item
          label="性别"
          name="gender"
      >
        <a-radio-group v-model:value="formState.gender">
          <a-radio value="男">男</a-radio>
          <a-radio value="女">女</a-radio>
        </a-radio-group>
      </a-form-item>
      
      <a-form-item
          label="身份证号"
          name="idCard"
      >
        <a-input v-model:value="formState.idCard"/>
      </a-form-item>
      
      <a-form-item
          label="电话号码"
          name="phone"
      >
        <a-input v-model:value="formState.phone"/>
      </a-form-item>
      
      <a-form-item
          label="职位名称"
          name="posName"
      >
        <a-select v-model:value="formState.posName">
          <a-select-option v-for="item in positionsList" :value="item"/>
        </a-select>
      </a-form-item>
      
      <a-form-item
          label="部门名称"
          name="deptName"
      >
        <a-select v-model:value="formState.deptName">
          <a-select-option v-for="item in departmentList" :value="item"/>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
  
  <a-modal v-model:open="openEdit" title="修改员工信息" @cancel="handleEditCancel" @ok="handleEditOk" ok-text="确认"
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
          label="姓名"
          name="name"
      >
        <a-input v-model:value="editFormState.name"/>
      </a-form-item>
      
      <a-form-item
          label="性别"
          name="gender"
      >
        <a-radio-group v-model:value="editFormState.gender">
          <a-radio value="男">男</a-radio>
          <a-radio value="女">女</a-radio>
        </a-radio-group>
      </a-form-item>
      
      <a-form-item
          label="身份证号"
          name="idCard"
      >
        <a-input v-model:value="editFormState.idCard"/>
      </a-form-item>
      
      <a-form-item
          label="电话号码"
          name="phone"
      >
        <a-input v-model:value="editFormState.phone"/>
      </a-form-item>
      
      <a-form-item
          label="职位名称"
          name="posName"
      >
        <a-select v-model:value="editFormState.posName">
          <a-select-option v-for="item in positionsList" :value="item"/>
        </a-select>
      </a-form-item>
      
      <a-form-item
          label="部门名称"
          name="deptName"
      >
        <a-select v-model:value="editFormState.deptName">
          <a-select-option v-for="item in departmentList" :value="item"/>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script lang="ts" setup>
import {onMounted, reactive, ref} from 'vue';
import type {Ref, UnwrapRef} from 'vue';
import axios from "axios";
import type {SelectProps} from 'ant-design-vue';
import type {Rule} from 'ant-design-vue/es/form';
import {message} from 'ant-design-vue';

//===修改员工
let editFormRef = ref()

const handleEdit = async (record: Employee) => {
  await getDepartmentList()
  await getPositionsList()
  Object.assign(editFormState, record)
  openEdit.value = true;
}

let openEdit = ref(false);


const handleEditCancel = (e: MouseEvent) => {
  openEdit.value = false;
}


const handleEditOk = (e: MouseEvent) => {
  editFormRef.value
      .validate()
      .then(() => {
        axios.put("/employee/update", editFormState).then(res => {
          let code = res.data.code;
          if (code === 200) {
            //将empTableDataSource内与editFormState.empId相同的行的值替换成editFormState
            let findIndex = empTableDataSource.value.findIndex(item => item.empId === editFormState.empId);
            const updatedRow = {
              key: findIndex,
              empId: editFormState.empId as number,
              name: editFormState.name,
              gender: editFormState.gender,
              idCard: editFormState.idCard,
              phone: editFormState.phone,
              posName: editFormState.posName,
              deptName: editFormState.deptName,
            };
            empTableDataSource.value[findIndex] = updatedRow;
            openEdit.value = false
            message.success("修改成功");
          } else {
            message.error(res.data.message);
          }
        })
      })
      .catch((error: object) => {
        console.log('error', error);
      });
};


const editFormState: UnwrapRef<Employee> = reactive({
  empId: undefined,
  name: '',
  gender: '',
  idCard: '',
  phone: '',
  posName: '',
  deptName: '',
});

import {getPositionsList, positionsList} from "@/entity/positions";
import {getDepartmentList, departmentList} from "@/entity/department";
//===修改员工


const rules: Record<string, Rule[]> = {
  empId: [
    {required: true, message: '请输入员工号', trigger: 'blur'},
    {type: 'number', min: 1, message: '员工号必须为正数', whitespace: true, trigger: ['change', 'blur']},
  ],
  name: [
    {required: true, message: '请输入姓名', trigger: 'blur'},
    {min: 2, max: 10, message: '姓名长度必须在2到10个字符之间', whitespace: true, trigger: ['change', 'blur']},
  ],
  gender: [
    {required: true, message: '请选择性别', trigger: ['change', 'blur']}
  ],
  idCard: [
    {required: true, message: '请输入身份证号', trigger: 'blur'},
    {pattern: /^[1-9]\d{16}[0-9X]$/, message: '身份证号格式不正确', whitespace: true, trigger: ['change', 'blur']}
  ],
  phone: [
    {required: true, message: '请输入电话号码', trigger: 'blur'},
    {pattern: /^1[3-9]\d{9}$/, message: '电话号码格式不正确', whitespace: true, trigger: ['change', 'blur']}
  ],
  posName: [
    {required: true, message: '请选择职位', trigger: ['change', 'blur']},
  ],
  deptName: [
    {required: true, message: '请选择部门', trigger: ['change', 'blur']},
  ]
}
const formState: UnwrapRef<Employee> = reactive({
  empId: undefined,
  name: '',
  gender: '',
  idCard: '',
  phone: '',
  posName: '',
  deptName: '',
});

const open = ref<boolean>(false);

const showModal = async () => {
  await getDepartmentList()
  await getPositionsList()
  open.value = true;
};
const formRef = ref();
const handleCancel = (e: MouseEvent) => {
  open.value = false;
}


const handleOk = (e: MouseEvent) => {
  formRef.value
      .validate()
      .then(() => {
        axios.post("/employee/add", formState).then(res => {
          let code = res.data.code;
          if (code === 200) {
            getList()
            open.value = false
          } else {
            message.error(res.data.message);
          }
        })
      })
      .catch((error: object) => {
        console.log('error', error);
      });
};


const isLoading = ref<boolean>(true)

const onDelete = (record: any) => {
  axios.delete(`/employee/deleteById?empId=${record.empId}`).then(res => {
    if (res.data.code === 200) {
      message.success("删除成功")
      empTableDataSource.value = empTableDataSource.value.filter(item => item.key !== record.key);
    } else {
      message.warn(res.data.message)
    }
  })
};

function resetTable() {
  isLoading.value = true;
  getList()
  inputValue.value = ''
}

function getSpecifiedList() {
  const field = currentSelectValue.value;
  const value = inputValue.value;
  if (field === "请选择") {
    message.warn("请选择查询条件")
    return;
  }
  if (value === '') {
    message.warn("请输入需要查询的内容")
    return
  }
  axios.get(`/employee/getSpecifiedList?field=${field}&value=${value}`).then(value => {
    if (value.data.code != 200) {
      console.log(value.data.message)
      return
    }
    empTableDataSource.value.length = 0
    let data = value.data.data as Array<DataItem>;
    for (let i = 0; i < data.length; i++) {
      empTableDataSource.value.push({
        key: i,
        empId: data[i].empId,
        name: data[i].name,
        gender: data[i].gender,
        idCard: data[i].idCard,
        phone: data[i].phone,
        posName: data[i].posName,
        deptName: data[i].deptName
      });
    }
  })
}

//输入框
const inputValue = ref<string>('');

interface DataItem {
  key: number;
  empId: number;
  name: string;
  gender: string;
  idCard: string;
  phone: string;
  posName: string;
  deptName: string;
}

interface Employee {
  empId: number | undefined;
  name: string;
  gender: string;
  idCard: string;
  phone: string;
  posName: string;
  deptName: string;
}


const empTableColumns = [
  {
    align: 'center',
    title: '员工号',
    dataIndex: 'empId',
  },
  {
    align: 'center',
    title: '姓名',
    dataIndex: 'name',
  },
  {
    align: 'center',
    title: '性别',
    dataIndex: 'gender',
  },
  {
    align: 'center',
    title: '身份证号',
    dataIndex: 'idCard',
  },
  {
    align: 'center',
    title: '电话号码',
    dataIndex: 'phone',
  },
  {
    align: 'center',
    title: '职位名称',
    dataIndex: 'posName',
    
  },
  {
    align: 'center',
    title: '部门名称',
    dataIndex: 'deptName',
    
  },
  {
    align: 'center',
    title: '操作',
    dataIndex: 'edit',
    width: "10%",
  },
];
const options1 = ref<SelectProps['options']>([]);

const currentSelectValue = ref('请选择')

for (let i = 0; i < empTableColumns.length; i++) {
  if (empTableColumns[i].dataIndex === 'edit' || empTableColumns[i].dataIndex === 'deptName' || empTableColumns[i].dataIndex === 'posName') {
    continue
  }
  options1.value?.push({
    value: empTableColumns[i].dataIndex,
    label: empTableColumns[i].title,
  });
}

const focus = () => {
  console.log('focus');
};

const handleChange = (value: string) => {
  console.log(`selected ${value}`);
};


function getList() {
  empTableDataSource.value.length = 0;
  axios.get("/employee/getList").then(value => {
    let data = value.data.data as Array<DataItem>;
    for (let i = 0; i < data.length; i++) {
      empTableDataSource.value.push({
        key: i,
        empId: data[i].empId,
        name: data[i].name,
        gender: data[i].gender,
        idCard: data[i].idCard,
        phone: data[i].phone,
        posName: data[i].posName,
        deptName: data[i].deptName
      });
      isLoading.value = false;
    }
  })
}

const empTableDataSource: Ref<DataItem[]> = ref([]);

onMounted(() => {
  getList()
})


</script>

<style scoped>
.horizontally {
  display: flex;
  justify-content: center;
}

</style>

