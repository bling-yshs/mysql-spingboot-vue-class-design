import axios from 'axios';

// Define interface
interface ResponseData {
    code: number;
    data: Array<string>;
    message: string;
}

let departmentList: Array<string> = [];

async function getDepartmentList() {
    try {
        const response = await axios.get<ResponseData>("/department/getNameList");
        // Store the response data in the global variable
        if (response.data.code === 200) {
            departmentList = response.data.data;
        }
    } catch (error) {
        console.error(error);
    }
}

export { getDepartmentList, departmentList };