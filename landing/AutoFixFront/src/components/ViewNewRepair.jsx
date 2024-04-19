import React from "react";
import { useLocation } from "react-router-dom";
import GenerateRepairsService from "../services/GenerateRepairs.service";
import { useEffect, useState } from "react";
import { Card, CardContent } from "@mui/material";
import { Grid, Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Button from "@mui/material/Button";

const ViewNewRepair = () => {
  const location = useLocation();
  const repairId = location.state.repairId;
  const [repair, setRepair] = useState({});

  const navigate = useNavigate();

  const ObtenerReparacion = async () => {
    try {
      const response = await GenerateRepairsService.getGenerateRepairsById(
        repairId
      );
      setRepair(response.data);
      console.log(response.data);
    } catch (error) {
      console.log("Error al cargar la reparación", error);
    }
  };

  useEffect(() => {
    ObtenerReparacion();
  }, []);

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" component="h2">
          Datos de la Reparación
        </Typography>
        <Grid container spacing={2}>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">ID de la reparación</Typography>
            <Typography variant="body2" component="p">
              {repair.id}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">Patente del vehículo</Typography>
            <Typography variant="body2" component="p">
              {repair.patente_vehiculo}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">
              Fecha de ingreso al taller
            </Typography>
            <Typography variant="body2" component="p">
              {repair.fecha_ingreso_taller}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">
              Hora de ingreso al taller
            </Typography>
            <Typography variant="body2" component="p">
              {repair.hora_ingreso_taller}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">
              Fecha de salida de reparación
            </Typography>
            <Typography variant="body2" component="p">
              {repair.fecha_salida_reparacion}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">
              Hora de salida de reparación
            </Typography>
            <Typography variant="body2" component="p">
              {repair.hora_salida_reparacion}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">
              Fecha de entrega al cliente
            </Typography>
            <Typography variant="body2" component="p">
              {repair.fecha_entrega_cliente}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">
              Hora de entrega al cliente
            </Typography>
            <Typography variant="body2" component="p">
              {repair.hora_entrega_cliente}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">Tipo de Reparación</Typography>
            <Typography variant="body2" component="p">
              {repair.tipo_reparacion}
            </Typography>
          </Grid>
          <Grid item xs={12} md={6}>
            <Typography color="textSecondary">Monto total</Typography>
            <Typography variant="body2" component="p">
              {repair.monto_total_reparacion}
            </Typography>
          </Grid>
          <Grid container justifyContent="center" style={{ marginTop: "20px" }}>
            <Grid item>
              <Button
                variant="contained"
                color="success"
                onClick={() => navigate("/GenerateRepair/add")}
              >
                Generar Nueva Reparación
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

export default ViewNewRepair;
