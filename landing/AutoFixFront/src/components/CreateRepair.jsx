import GenerateRepairService from "../services/GenerateRepairs.service";
import VehicleService from "../services/Vehicle.service";
import RepairService from "../services/Repairs.service";

import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import { Box, FormControl, TextField, Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import { Select, MenuItem, InputLabel } from "@mui/material";
import Typography from "@mui/material/Typography";
import e from "cors";
import { Checkbox, ListItemText, OutlinedInput } from "@mui/material";

const CreateRepair = () => {
  const [fecha_ingreso_taller, setFechaIngresoTaller] = useState("");
  const [hora_ingreso_taller, setHoraIngresoTaller] = useState("");
  const [tiposReparacionSeleccionados, setTiposReparacionSeleccionados] =
    useState([]);
  const [reparaciones, setReparaciones] = useState([]);
  const [fecha_salida_reparacion, setFechaSalidaReparacion] = useState("");
  const [hora_salida_reparacion, setHoraSalidaReparacion] = useState("");
  const [fecha_entrega_cliente, setFechaEntregaCliente] = useState("");
  const [hora_entrega_cliente, setHoraEntregaCliente] = useState("");
  const [patente_vehiculo, setPatenteVehiculo] = useState("");
  const [patentes, setPatentes] = useState([]);

  useEffect(() => {
    const cargarPatentes = async () => {
      try {
        const response = await VehicleService.getAllPatentes();
        setPatentes(response.data);
      } catch (error) {
        console.log("Error al cargar las patentes", error);
      }
    };

    const cargarReparaciones = async () => {
      try {
        // Suponiendo que esto es lo que devuelve tu backend
        const response = await RepairService.getRepairs();
        const tiposProcesados = response.data.map((tipo) => {
          const [id, nombre] = tipo.split(",");
          return { id, nombre: nombre.trim() };
        });
        setReparaciones(tiposProcesados);
      } catch (error) {
        console.log("Error al cargar las reparaciones", error);
      }
    };

    cargarPatentes();
    cargarReparaciones();
  }, []);

  const handleChange = (event) => {
    const value = event.target.value;
    setTiposReparacionSeleccionados(
      typeof value === "string" ? value.split(",") : value
    );
  };

  const saveCreateRepair = (e) => {
    e.preventDefault();

    const idsAEnviar = tiposReparacionSeleccionados
      .map((nombre) => reparaciones.find((tipo) => tipo.nombre === nombre).id)
      .join(",");

    const fechaIngresoTallerCompleta =
      fecha_ingreso_taller + "T" + hora_ingreso_taller + ":00";
    const fechaSalidaReparacionCompleta =
      fecha_salida_reparacion + "T" + hora_salida_reparacion + ":00";
    const fechaEntregaClienteCompleta =
      fecha_entrega_cliente + "T" + hora_entrega_cliente + ":00";

    const GenerateRepair = {
      fecha_ingreso_taller: fechaIngresoTallerCompleta,
      hora_ingreso_taller,
      tipo_reparacion: idsAEnviar,
      fecha_salida_reparacion: fechaSalidaReparacionCompleta,
      hora_salida_reparacion,
      fecha_entrega_cliente: fechaEntregaClienteCompleta,
      hora_entrega_cliente,
      patente_vehiculo,
    };

    //mostramos por consola todos los datos que se enviaran
    console.log(GenerateRepair);

    GenerateRepairService.generateRepairs(GenerateRepair)
      .then((response) => {
        console.log("Reparacion creada con éxito", response.data);
        //navigate("/repairs/List");
      })
      .catch((error) => {
        console.log("Error al crear la reparacion", error);
      });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={saveCreateRepair}
    >
      <h3>Registrar Reparación</h3>
      <hr />
      <FormControl fullWidth margin="normal">
        <TextField
          id="fecha_ingreso_taller"
          label="Fecha Ingreso Taller"
          type="date"
          value={fecha_ingreso_taller}
          variant="standard"
          onChange={(e) => setFechaIngresoTaller(e.target.value)}
          InputLabelProps={{ shrink: true }} // Esto asegura que la etiqueta no se superponga con la fecha seleccionada
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <TextField
          id="hora_ingreso_taller"
          label="Hora Ingreso Taller"
          type="time"
          value={hora_ingreso_taller}
          variant="standard"
          onChange={(e) => setHoraIngresoTaller(e.target.value)}
          InputLabelProps={{ shrink: true }}
          inputProps={{
            step: 60, // 5 minutos
          }}
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <InputLabel id="tipo-reparacion-label">Tipo de Reparación</InputLabel>
        <Select
          labelId="tipo-reparacion-label"
          id="tipo-reparacion"
          multiple
          value={tiposReparacionSeleccionados}
          onChange={handleChange}
          input={<OutlinedInput label="Tipo de Reparación" />}
          renderValue={(selected) => selected.join(", ")}
        >
          {reparaciones.map((tipo) => (
            <MenuItem key={tipo.id} value={tipo.nombre}>
              <Checkbox
                checked={tiposReparacionSeleccionados.indexOf(tipo.nombre) > -1}
              />
              <ListItemText primary={tipo.nombre} />
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <FormControl fullWidth margin="normal">
        <TextField
          id="fecha_salida_reparacion"
          label="Fecha Salida Reparación"
          type="date"
          value={fecha_salida_reparacion}
          variant="standard"
          onChange={(e) => setFechaSalidaReparacion(e.target.value)}
          InputLabelProps={{ shrink: true }}
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <TextField
          id="hora_salida_reparacion"
          label="Hora Salida Reparación"
          type="time"
          value={hora_salida_reparacion}
          variant="standard"
          onChange={(e) => setHoraSalidaReparacion(e.target.value)}
          InputLabelProps={{ shrink: true }}
          inputProps={{
            step: 300,
          }}
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <TextField
          id="fecha_entrega_cliente"
          label="Fecha Entrega Cliente"
          type="date"
          value={fecha_entrega_cliente}
          variant="standard"
          onChange={(e) => setFechaEntregaCliente(e.target.value)}
          InputLabelProps={{ shrink: true }}
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <TextField
          id="hora_entrega_cliente"
          label="Hora Entrega Cliente"
          type="time"
          value={hora_entrega_cliente}
          variant="standard"
          onChange={(e) => setHoraEntregaCliente(e.target.value)}
          InputLabelProps={{ shrink: true }}
          inputProps={{
            step: 300,
          }}
          required
        />
      </FormControl>
      <FormControl fullWidth margin="normal">
        <InputLabel id="patente_vehiculo-label">Patente</InputLabel>
        <Select
          labelId="patente_vehiculo-label"
          id="patente_vehiculo"
          value={patente_vehiculo}
          onChange={(e) => setPatenteVehiculo(e.target.value)} // Cambia aquí a setPatente
          label="Patente"
          required
        >
          {patentes.map((patente) => (
            <MenuItem key={patente} value={patente}>
              {patente}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <Button
        type="submit"
        variant="contained"
        color="primary"
        onClick={(e) => {
          if (
            fecha_ingreso_taller &&
            hora_ingreso_taller &&
            tiposReparacionSeleccionados &&
            fecha_salida_reparacion &&
            hora_salida_reparacion &&
            fecha_entrega_cliente &&
            hora_entrega_cliente &&
            patente_vehiculo
          ) {
            saveCreateRepair(e);
          } else {
            alert("Por favor, rellene todos los campos");
            e.preventDefault();
          }
        }}
        startIcon={<SaveIcon />}
      >
        Guardar
      </Button>
    </Box>
  );
};

export default CreateRepair;
