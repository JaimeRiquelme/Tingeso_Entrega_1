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



    const columns = [
        { field: 'name' , headerName: 'Nombre', width: 150 },
        { field: 'sedan', headerName: 'Sedan', width: 150, type: 'number' },
        { field: 'hatchback', headerName: 'Hatchback', width: 150, type: 'number' },
        { field: 'suv', headerName: 'SUV', width: 150, type: 'number' },
        { field: 'pickup', headerName: 'PickUp', width: 150, type: 'number' },
        { field: 'furgoneta', headerName: 'Furgoneta', width: 150, type: 'number' },
        { field: 'cost', headerName: 'Costo Total', width: 150, type: 'number' }
    ];


    const rows = data.map((item, index) => ({
        id: index,
        name: item[0],
        sedan: item[1],
        hatchback: item[2],
        suv: item[3],
        pickup: item[4],
        furgoneta: item[5],
        cost: item[6]
    }));

    return (
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center">
            <BarChart
                width={1300}
                height={400}
                margin={{ top: 45, right: 10, left: 10, bottom: 30 }}
                xAxis={[{ scaleType: 'band', dataKey: 'name', align: 'center' }]}
                dataset={data.map(item => ({ name: item[0], sedan: item[1], hatchback: item[2], suv: item[3], pickUp: item[4], furgoneta: item[5], cost: item[6] }))}
                series={[
                {
                    dataKey: 'cost',
                    label: 'Total',
                    valueFormatter: (value) => `$${value}`
                }]}
            />
            <Box sx={{ height: 400, width: '100%', marginTop: 2 }}>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}                
                />
            </Box>
        </Box>
    );

    
};

export default ViewRepairsTypeReport;