import httpCommon from "../http-common";

const getAll = () => {
    return httpCommon.get("api/v1/vehicles");
}

const create = data => {
    return httpCommon.post("api/v1/vehicles", data);
}

const update = (patente, data) => {
    return httpCommon.put(`api/v1/vehicles/${patente}`, data);
}

const remove = id => {
    return httpCommon.delete(`api/v1/vehicles/${id}`);
}

const get = id => {
    return httpCommon.get(`api/v1/vehicles/${id}`);
}
export default { getAll, create, update, remove, get };
