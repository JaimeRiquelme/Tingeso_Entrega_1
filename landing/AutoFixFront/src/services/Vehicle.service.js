import httpCommon from "../http-common";

const getAll = () => {
    return httpCommon.get("api/v1/vehicles/");
}

const getPatente = patente => {
    return httpCommon.get(`api/v1/vehicles/${patente}`);
}

const create = data => {
    return httpCommon.post("api/v1/vehicles/", data);
}

const update = data => {
    return httpCommon.put('api/v1/vehicles/',data);
}

const remove = id => {
    return httpCommon.delete(`api/v1/vehicles/${id}`);
}

const get = id => {
    return httpCommon.get(`api/v1/vehicles/${id}`);
}

const getAllPatentes = () => {
    return httpCommon.get("api/v1/vehicles/patentes");
}
export default { getAll, create, update, remove, get, getPatente, getAllPatentes};
