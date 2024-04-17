import httpCommon from "../http-common";

const getAll = () => {
    return httpCommon.get("api/v1/repairs/");
}

const create = data => {
    return httpCommon.post("api/v1/repairs/", data);
}

const getRepairs =() => {
    return httpCommon.get("api/v1/repairs/types");
}