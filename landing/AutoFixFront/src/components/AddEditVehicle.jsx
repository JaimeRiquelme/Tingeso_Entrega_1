import vehicleService from "../services/Vehicle.service";
import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { Box, FormControl, TextField, Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import { Select, MenuItem, InputLabel } from "@mui/material";
import Typography from "@mui/material/Typography";

const AddEditVehicle = () => {
  const [patente, setPatente] = useState("");
  const [marca, setMarca] = useState("");
  const [modelo, setModelo] = useState("");
  const [tipo, setTipo] = useState("");
  const [anio_fabricacion, setAnio_fabricacion] = useState("");
  const [tipo_motor, setTipo_motor] = useState("");
  const [numero_asientos, setNumero_asientos] = useState("");
  const [kilometraje, setKilometraje] = useState("");
  const [titleVehicleForm, setTitleVehicleForm] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

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
      numero_reparaciones:0,
      kilometraje
    };

    vehicleService
      .create(vehicle)
      .then((response) => {
        console.log("Vehiculo creado con éxito", response.data);
        navigate("/vehicles/List");
      })
      .catch((error) => {
        console.log("Error al crear el vehiculo", error);
      });
  };

  useEffect(() => {
      setTitleVehicleForm("Registrar Vehículo");
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
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="marca"
          label="Marca"
          value={marca}
          variant="standard"
          onChange={(e) => setMarca(e.target.value)}
          required
        />
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="modelo"
          label="Modelo"
          value={modelo}
          variant="standard"
          onChange={(e) => setModelo(e.target.value)}
          required
        />
      </FormControl>

      <FormControl fullWidth>
        <InputLabel id="tipo-label">Tipo</InputLabel>
        <Select
          labelId="tipo-label"
          id="tipo"
          value={tipo}
          label="Tipo"
          onChange={(e) => setTipo(e.target.value)}
          required
        >
          <MenuItem value={"Sedan"}>Sedán</MenuItem>
          <MenuItem value={"Hatchback"}>Hatchback</MenuItem>
          <MenuItem value={"SUV"}>SUV</MenuItem>
          <MenuItem value={"Pickup"}>Pickup</MenuItem>
          <MenuItem value={"Furgoneta"}>Furgoneta</MenuItem>
        </Select>
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="anio_fabricacion"
          label="Año de Fabricación"
          type="number"
          value={anio_fabricacion}
          variant="standard"
          onChange={(e) => setAnio_fabricacion(e.target.value)}
          required
        />
      </FormControl>

      <FormControl fullWidth>
        <InputLabel id="tipo_motor">Tipo_motor</InputLabel>
        <Select
          labelId="tipo_motor-label"
          id="tipo_motor"
          value={tipo_motor}
          label="tipo_motor"
          onChange={(e) => setTipo_motor(e.target.value)}
          required
        >
          <MenuItem value={"Gasolina"}>Gasolina</MenuItem>
          <MenuItem value={"Diesel"}>Diesel</MenuItem>
          <MenuItem value={"Híbrido"}>Híbrido</MenuItem>
          <MenuItem value={"Electrico"}>Eléctrico</MenuItem>
        </Select>
      </FormControl>

      <FormControl fullWidth margin="normal">
        <TextField
          id="numero_asientos"
          label="Número de Asientos"
          type="number"
          value={numero_asientos}
          variant="standard"
          onChange={(e) => setNumero_asientos(e.target.value)}
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <TextField
          id="kilometraje"
          label="Kiometraje"
          type="number"
          value={kilometraje}
          variant="standard"
          onChange={(e) => setKilometraje(e.target.value)}
          required
        />
      </FormControl>
      <FormControl margin="normal">
        <Button
          variant="contained"
          color="primary"
          type="submit"
          onClick={(e) => {
            if (
              patente &&
              marca &&
              modelo &&
              tipo &&
              anio_fabricacion &&
              tipo_motor &&
              numero_asientos &&           
              kilometraje

            ) {
              saveVehicle(e);
            } else {
              setErrorMessage("Todos los campos son obligatorios");
              // Esto evitará que la página se recargue, si estás usando un <form>
              e.preventDefault();
            }
          }}
          startIcon={<SaveIcon />}
        >
          Grabar
        </Button>
      </FormControl>
      <FormControl fullWidth margin="normal">
        {errorMessage && <Typography color="error">{errorMessage}</Typography>}
      </FormControl>   
      <hr />
      <Link to = "/vehicles/List">Regresar a la lista de vehículos</Link>
    </Box>
  );
};

export default AddEditVehicle;
