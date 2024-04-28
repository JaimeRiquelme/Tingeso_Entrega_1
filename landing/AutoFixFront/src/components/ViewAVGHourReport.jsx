import GenerateRepairService from "../services/GenerateRepairs.service";
import React, { useState, useEffect } from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import { DataGrid } from '@mui/x-data-grid';
import Box from '@mui/material/Box';


const AVGHourReport = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await GenerateRepairService.getReportAvgHours();
            setData(response.data);
        };
        fetchData();
    }, []);

    const columns = [
        { field: 'brand', headerName: 'Marca', width: 400 },
        { field: 'avgHours', headerName: 'Horas Promedio', width: 150, type: 'number' }
    ];

    const rows = data.map((item, index) => ({
        id: index,
        brand: item[0],
        avgHours: item[1]
    }));

    return (
        <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center">
            <BarChart
                width={600}
                height={400}
                margin={{ top: 50, right: 10, left: 10, bottom: 50 }}
                xAxis={[{ scaleType: 'band', dataKey: 'brand', align: 'center' }]}
                dataset={data.map(item => ({ brand: item[0], avgHours: item[1] }))}
                series={[{
                    dataKey: 'avgHours',
                    label: 'Horas Promedio',
                    valueFormatter: (value) => `${value.toFixed(2)} hrs`
                }]}
            />
            <Box sx={{ height: 300, width: '100%', marginTop: 2 }}>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    disableSelectionOnClick
                />
            </Box>
        </Box>
    );
};

export default AVGHourReport;