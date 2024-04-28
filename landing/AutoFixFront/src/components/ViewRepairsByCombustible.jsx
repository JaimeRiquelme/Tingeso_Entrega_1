import GenerateRepairsService from "../services/GenerateRepairs.service";

import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';


const ViewRepairsByCombustible = () => {
    const [data, setData] = useState([]);


    useEffect(() => {
        const fetchData = async () => {
            const response = await GenerateRepairsService.getGenerateRepairsGroupByCombustible();
            setData(response.data);
        };
        fetchData();
    }, []);


    const columns = [
        { field: 'name', headerName: 'Nombre', width: 150 },
        { field: 'gasoline', headerName: 'Gasolina', width: 150, type: 'number' },
        { field: 'diesel', headerName: 'Diesel', width: 150, type: 'number' },
        { field: 'hybrid', headerName: 'Híbrido', width: 150, type: 'number' },
        { field: 'electric', headerName: 'Electrico', width: 150, type: 'number' },
        { field: 'total', headerName: 'Total', width: 150, type: 'number' }
    ];

    const rows = data.map((item, index) => ({
        id: index,
        name: item[0],
        gasoline: item[1],
        diesel: item[2],
        hybrid: item[3],
        electric: item[4],
        total: item[5]
    }));

    return (
        <div style={{ height: 700, width: '100%' }}>
            <DataGrid
                rows={rows}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5]}
                disableSelectionOnClick
            />
        </div>
    );

    

}

export default ViewRepairsByCombustible;