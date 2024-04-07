import httpCommon from "../http-common";

const getAll = () => {
    return httpCommon.get("api/v1/bonuses/");
}

const get = id => {
    return httpCommon.get(`api/v1/bonuses/${id}`);
}

const create = data => {
    return httpCommon.post("api/v1/bonuses/", data);
}

const update = (id,data) => {
    return httpCommon.put(`api/v1/bonuses/${id}`,data);
}

const remove = id => {
    return httpCommon.delete(`api/v1/bonuses/${id}`);
}

export default { getAll, create, update, remove, get };