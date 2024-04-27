import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import GenerateRepairsDetailsService from '../services/GenerateRepairsDetails.service';
import { Button, Table, TableBody, TableCell, TableContainer, TableRow, Paper, Typography } from '@mui/material';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';

const ViewRepairsDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [repairsDetails, setRepairsDetails] = useState(null);

    const init = () => {
        GenerateRepairsDetailsService.getRepairsDetailsById(id).then((response) => {
            console.log("Mostrar detalles de reparaciones", response.data);
            setRepairsDetails(response.data);
        })
        .catch((error) => {
            console.log("Hubo un error al mostrar detalles de reparaciones", error);
        });
    };

    useEffect(() => {
        init();
    }, [id]);

    return (
        <div>
            <Typography variant="h4" gutterBottom>
                Detalles de Reparación
            </Typography>
            {repairsDetails ? (
                <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableBody>
                            <TableRow>
                                <TableCell component="th" scope="row">Id de reparación:</TableCell>
                                <TableCell>{repairsDetails.reparacion_id}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">Total descuento:</TableCell>
                                <TableCell>{repairsDetails.total_descuento}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">Total recargos:</TableCell>
                                <TableCell>{repairsDetails.total_recargos}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">Descuento bono:</TableCell>
                                <TableCell>{repairsDetails.descuento_bono}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">IVA:</TableCell>
                                <TableCell>{repairsDetails.iva}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">Monto reparaciones:</TableCell>
                                <TableCell>{repairsDetails.monto_reparaciones}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell component="th" scope="row">Total:</TableCell>
                                <TableCell>{repairsDetails.total}</TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            ) : (
                <Typography variant="subtitle1">Cargando detalles de la reparación...</Typography>
            )}
            <Button
                variant="contained"
                color="primary"
                startIcon={<ArrowBackIcon />}
                onClick={() => navigate('/repairs/list')}
                sx={{ mt: 2 }}
            >
                Volver a la lista de reparaciones
            </Button>
        </div>
    );
};

export default ViewRepairsDetails;
