import { Table, TableContainer } from "@mui/material";
import GenerateRepairService from "../services/GenerateRepairs.service";
import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button, Paper, TableBody, TableCell, TableHead, TableRow } from '@mui/material';
import PersonAddIcon from '@mui/icons-material/PersonAdd';


const RepairsList = () => {


    const [repairs, setRepairs] = useState([]);
    const navigate = useNavigate();
     
    const init = () => {
        GenerateRepairService.getAll().then((response) => {
            console.log("Mostar reparaciones", response.data);
            setRepairs(response.data);
        })
        .catch((error) => {
            console.log("Hubo un error al mostrar reparaciones", error);
        });
    }

    useEffect(() => {
        init();
    }, []);

    return (
        <TableContainer component={Paper}>
            <br />
            <Link
                to="/GenerateRepair/add"
                style={{ textDecoration: "none", marginBottom: "1rem" }}
            >
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<PersonAddIcon />}
                >
                    Añadir Reparación
                </Button>
            </Link>
            <br /> <br />
            <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table" >
                <TableHead>
                    <TableRow>
                        <TableCell>Id Reparación</TableCell>
                        <TableCell>Fecha Ingreso Taller</TableCell>
                        <TableCell>Hora Ingreso Taller</TableCell>
                        <TableCell>Tipo Reparación</TableCell>
                        <TableCell>Monto Total Reparación</TableCell>
                        <TableCell>Fecha Salida Reparación</TableCell>
                        <TableCell>Hora Salida Reparación</TableCell>
                        <TableCell>Fecha Entrega Cliente</TableCell>
                        <TableCell>Hora Entrega Cliente</TableCell>
                        <TableCell>Patente Vehículo</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {repairs.map((repair) => (
                        <TableRow key={repair.id}>
                            <TableCell>{repair.id}</TableCell>
                            <TableCell>{repair.fecha_ingreso_taller}</TableCell>
                            <TableCell>{repair.hora_ingreso_taller}</TableCell>
                            <TableCell>{repair.tipo_reparacion}</TableCell>
                            <TableCell>{repair.monto_total_reparacion}</TableCell>
                            <TableCell>{repair.fecha_salida_reparacion}</TableCell>
                            <TableCell>{repair.hora_salida_reparacion}</TableCell>
                            <TableCell>{repair.fecha_entrega_cliente}</TableCell>
                            <TableCell>{repair.hora_entrega_cliente}</TableCell>
                            <TableCell>{repair.patente_vehiculo}</TableCell>
                            <TableCell>
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={() => navigate(`/repairs/details/${repair.id}`)}
                                >
                                    Ver Detalles
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );

}

export default RepairsList;