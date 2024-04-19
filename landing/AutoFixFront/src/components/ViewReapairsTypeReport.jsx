import GenerateRepairsService from "../services/GenerateRepairs.service";
import React, { useState, useEffect } from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import { DataGrid } from '@mui/x-data-grid';
import Box from '@mui/material/Box';



const ViewRepairsTypeReport = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await GenerateRepairsService.getGenerateRepairsGroupByTipe();
            setData(response.data);
        };
        fetchData();
    }, []);

    /*Ejemplo del data: 

    [
        [
        "Sistema de Combustible", //Nombre  
        1, // Cantidad
        220000.0 // Costo
    ],
    ]

    */

    const columns = [
        { field: 'name', headerName: 'Name', width: 150 },
        { field: 'quantity', headerName: 'Quantity', width: 150, type: 'number' },
        { field: 'cost', headerName: 'Cost', width: 150, type: 'number' }
    ];

    const rows = data.map((item, index) => ({
        id: index,
        name: item[0],
        quantity: item[1],
        cost: item[2]
    }));


    return (
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center">
            <BarChart
                width={600}
                height={400}
                margin={{ top: 50, right: 10, left: 10, bottom: 50 }}
                xAxis={[{ scaleType: 'band', dataKey: 'name', align: 'center' }]}
                dataset={data.map(item => ({ name: item[0], quantity: item[1], cost: item[2] }))}
                series={[{
                    dataKey: 'quantity',
                    label: 'Quantity',
                    valueFormatter: (value) => `${value}`
                },
                {
                    dataKey: 'cost',
                    label: 'Cost',
                    valueFormatter: (value) => `$${value}`
                }]}
            />
            <Box sx={{ height: 300, width: '100%', marginTop: 2 }}>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    checkboxSelection
                    disableSelectionOnClick
                />
            </Box>
        </Box>
    );

    
};

export default ViewRepairsTypeReport;