import axios from 'axios';

// 定义接口
interface ResponseData {
    code: number;
    data: Array<string>;
    message: string;
}

let positionsList: Array<string> = [];

// 创建一个函数来发送GET请求并存储结果
async function getPositionsList() {
    try {
        const response = await axios.get<ResponseData>("/position/getList")
        // 将响应数据存储在全局变量中
        if (response.data.code === 200) {
            positionsList = response.data.data;
        }
    } catch (error) {
        console.error(error);
    }
}

// 导出函数和全局数据
export { getPositionsList, positionsList };
