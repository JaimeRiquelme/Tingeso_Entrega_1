import GenerateRepairService from "../services/GenerateRepairs.service";
import VehicleService from "../services/Vehicle.service";
import RepairService from "../services/Repairs.service";

import { useState, useEffect } from "react";
import { Box, FormControl, TextField, Button } from "@mui/material";
import SaveIcon from "@mui/icons-material/Save";
import { Select, MenuItem, InputLabel } from "@mui/material";
import { Checkbox, ListItemText, OutlinedInput } from "@mui/material";
import { FormControlLabel } from "@mui/material";
import { useNavigate } from 'react-router-dom';


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
  const [usar_bono, setUsarBono] = useState(false);

  const navigate = useNavigate();

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

    GenerateRepairService.generateRepairs(GenerateRepair, usar_bono)
      .then((response) => {
        console.log("Reparacion creada con éxito", response.data);
        console.log("Total de Descuentos recibidos:", response.data.totalDescuentos);
        console.log("Total de Recargos recibidos:", response.data.totalRecargos);
        console.log("Descuento por bono:", response.data.descuentoBono)
        console.log("Iva:", response.data.iva)
        console.log("Monto Reparaciones:", response.data.montoReparaciones)

        navigate("/GenerateRepair/ViewNewRepair", { state: {
          repairId: response.data.generateRepair.id,
          totalDescuentos: response.data.totalDescuentos,
          totalRecargos: response.data.totalRecargos,
          descuentoBono: response.data.descuentoBono,
          iva: response.data.iva,
          montoReparaciones: response.data.montoReparaciones
         } })
      })
      .catch((error) => {
        console.log("Error al crear la reparacion", error);
      });
  };

  return (
    <Box
    display="flex"
    flexDirection="column"
    width={600}
    alignItems="center"
    justifyContent="center"
    component="form"
    onSubmit={saveCreateRepair}
    sx={{
      border: '1px solid #ccc', 
      boxShadow: '0px 0px 10px rgba(0,0,0,0.1)', 
      borderRadius: '8px', 
      bgcolor: 'background.paper', 
      p: 3, 
      mt: 2 
    }}
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
          onChange={(e) => setPatenteVehiculo(e.target.value)} 
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

      <FormControl fullWidth margin="normal">
        <FormControlLabel
          control={
            <Checkbox
              checked={usar_bono}
              onChange={(e) => setUsarBono(e.target.checked)}
              name="usar_bono"
            />
          }
          label="Usar bono (Solo se aplicará si está disponible)"
        />
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
            && fecha_ingreso_taller <= fecha_salida_reparacion
            && fecha_salida_reparacion <= fecha_entrega_cliente
          
          ) {
            saveCreateRepair(e);
          } else {
            if(fecha_ingreso_taller > fecha_salida_reparacion || fecha_salida_reparacion > fecha_entrega_cliente || fecha_entrega_cliente > fecha_ingreso_taller){
              alert("Debe ingresar las fechas correctamente.");
            }
            else{
              alert("Debe completar todos los campos");
            }
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
