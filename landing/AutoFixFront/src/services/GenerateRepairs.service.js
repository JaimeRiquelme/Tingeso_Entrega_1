import httpCommon from "../http-common";

const generateRepairs = data => {
    return httpCommon.post("api/v1/generateRepairs/", data);
}

const getGenerateRepairs = () => {
    return httpCommon.get("api/v1/genereateRepairs/");
}