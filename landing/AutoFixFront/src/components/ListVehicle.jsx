import vehicleService from '../services/Vehicle.service';
import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import DeleteIcon from '@mui/icons-material/Delete';



const ListVehicle = () => {
    const [vehicles, setVehicles] = useState([]);
    const navigate = useNavigate();

    const init = () => {
        vehicleService.getAll().then((response) => {
            console.log("Mostar vehiculos", response.data);
            setVehicles(response.data);
        })
        .catch((error) => {
            console.log("Hubo un error al mostrar vehiculos", error);
        });
    }

    useEffect(() => {
        init();
    }, []);

    const deleteVehicle = (id) => {
        console.log("Eliminar vehiculo", id);
        const confirm = window.confirm("¿Estás seguro de eliminar este vehículo?");
        if (confirm) {
            vehicleService.remove(id).then((response) => {
                console.log("Vehiculo eliminado", response.data);
                init();
            })
            .catch((error) => {
                console.log("Hubo un error al eliminar vehiculo", error);
            });
        }
    };

    return (
        <TableContainer component={Paper}>
          <br />
          <Link
            to="/vehicles/add"
            style={{ textDecoration: "none", marginBottom: "1rem" }}
          >
            <Button
              variant="contained"
              color="primary"
              startIcon={<PersonAddIcon />}
            >
              Añadir Vehiculo
            </Button>
          </Link>
          <br /> <br />
          <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Patente
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Marca
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Modelo
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Numero Asientos
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  numero reparaciones
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Año Fabricacion
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Tipo
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Tipo Motor
                </TableCell>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  Kilometraje
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {vehicles.map((vehicle) => (
                <TableRow
                  key={vehicle.id}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell align="left">{vehicle.patente}</TableCell>
                  <TableCell align="left">{vehicle.marca}</TableCell>
                  <TableCell align="right">{vehicle.modelo}</TableCell>
                  <TableCell align="right">{vehicle.numero_asientos}</TableCell>
                  <TableCell align="right">{vehicle.numero_reparaciones}</TableCell>
                  <TableCell align="right">{vehicle.anio_fabricacion}</TableCell>
                  <TableCell align="right">{vehicle.tipo}</TableCell>
                  <TableCell align="right">{vehicle.tipo_motor}</TableCell>
                  <TableCell align="right">{vehicle.kilometraje}</TableCell>
                  <TableCell>
                    
                    <Button
                      variant="contained"
                      color="error"
                      size="small"
                      onClick={() => deleteVehicle(vehicle.id)}
                      style={{ marginLeft: "0.5rem" }}
                      startIcon={<DeleteIcon />}
                    >
                      Eliminar
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      );
}

export default ListVehicle;