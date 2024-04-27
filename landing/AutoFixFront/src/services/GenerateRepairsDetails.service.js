import httpCommon from "../http-common";

const getRepairsDetailsById = (id) => {
    return httpCommon.get(`api/v1/discountrepairsdetails/${id}`);
}

export default {
    getRepairsDetailsById
}