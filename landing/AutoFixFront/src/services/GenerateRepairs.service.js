import httpCommon from "../http-common";

const generateRepairs = (data,uso_bono) => {
    return httpCommon.post(`api/v1/generateRepairs/${uso_bono}`, data);
}

const getGenerateRepairs = () => {
    return httpCommon.get("api/v1/genereateRepairs/");
}

export default {
    generateRepairs,
    getGenerateRepairs
};