import axios from 'axios';

const AutoFixBackendServer = import.meta.env.VITE_AUTOFIX_BACKEND_SERVER;
const AutoFixBackendPort = import.meta.env.VITE_AUTOFIX_BACKEND_PORT;

export default axios.create({
    baseURL: `http://${AutoFixBackendServer}:${AutoFixBackendPort}`,
    headers: {
        "Content-type": "application/json"
    }
});