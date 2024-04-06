import vehicleService from "../services/Vehicle.service";
import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { Box, FormControl, TextField, Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";


const AddEditVehicle = () => {
  const [patente, setPatente] = useState("");
  const [marca, setMarca] = useState("");
  const [modelo, setModelo] = useState("");
  const [tipo, setTipo] = useState("");
  const [anio_fabricacion, setAnio_fabricacion] = useState("");
  const [tipo_motor, setTipo_motor] = useState("");
  const [numero_asientos, setNumero_asientos] = useState("");
  const [numero_reparaciones, setNumero_reparaciones] = useState("");
  const [titleVehicleForm, setTitleVehicleForm] = useState("");
  const navigate = useNavigate();

  const saveVehicle = (e) => {
    e.preventDefault();

    const vehicle = {
      patente,
      marca,
      modelo,
      tipo,
      anio_fabricacion,
      tipo_motor,
      numero_asientos,
      numero_reparaciones,
    };

    if (patente) {
      vehicleService
        .update(vehicle)
        .then((response) => {
          console.log("Vehiculo actualizado con éxito", response.data);
        })
        .catch((error) => {
          console.log("Error al actualizar el vehiculo", error);
        });
    }else{
      vehicleService
        .create(vehicle)
        .then((response) => {
          console.log("Vehiculo creado con éxito", response.data);
        })
        .catch((error) => {
          console.log("Error al crear el vehiculo", error);
        });
    }
  };

  useEffect(() => {
    if (patente) {
      setTitleVehicleForm("Editar Vehículo");
      vehicleService.get(patente).then((response) => {
        setMarca(response.data.marca);
        setModelo(response.data.modelo);
        setTipo(response.data.tipo);
        setAnio_fabricacion(response.data.anio_fabricacion);
        setTipo_motor(response.data.tipo_motor);
        setNumero_asientos(response.data.numero_asientos);
        setNumero_reparaciones(response.data.numero_reparaciones);
      })
      .catch((error) => {
        console.log("Error al obtener el vehiculo", error);
      });
    }else{
      setTitleVehicleForm("Agregar Vehículo");
    }
  }, []);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={saveVehicle}
    >
      <h3>{titleVehicleForm}</h3>
      <hr />
      <FormControl fullWidth margin="normal">
        <TextField
          id="patente"
          label="Patente"
          value={patente}
          variant="standard"
          onChange={(e) => setPatente(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="marca"
          label="Marca"
          value={marca}
          variant="standard"
          onChange={(e) => setMarca(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="modelo"
          label="Modelo"
          value={modelo}
          variant="standard"
          onChange={(e) => setModelo(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="tipo"
          label="Tipo"
          value={tipo}
          variant="standard"
          onChange={(e) => setTipo(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="anio_fabricacion"
          label="Año de Fabricación"
          type="number"
          value={anio_fabricacion}
          variant="standard"
          onChange={(e) => setAnio_fabricacion(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="tipo_motor"
          label="Tipo de Motor"
          value={tipo_motor}
          variant="standard"
          onChange={(e) => setTipo_motor(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="numero_asientos"
          label="Número de Asientos"
          type="number"
          value={numero_asientos}
          variant="standard"
          onChange={(e) => setNumero_asientos(e.target.value)}
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="numero_reparaciones"
          label="Número de Reparaciones"
          type="number"
          value={numero_reparaciones}
          variant="standard"
          onChange={(e) => setNumero_reparaciones(e.target.value)}
        />
      </FormControl>

      <FormControl margin="normal">
        <Button
          variant="contained"
          color="primary"
          type="submit"
          onClick={(e) => saveVehicle(e)}
          startIcon={<SaveIcon />}
          
        >
          Grabar
        </Button>
      </FormControl>
      <hr />  
    </Box>
  );
};

export default AddEditVehicle;


