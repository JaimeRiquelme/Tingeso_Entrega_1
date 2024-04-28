import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button, TextField, Paper, Box } from "@mui/material";
import BonusesService from "../services/Bonuses.service";

const EditBonus = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [bonusData, setBonusData] = useState({
    disponibilidad: "",
    marca: "",
    monto: "",
  });

  const init = () => {
    BonusesService.get(id)
      .then((response) => {
        setBonusData(response.data);
      })
      .catch((error) => {
        console.error("Hubo un error al cargar el bono", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleChange = (event) => {
    setBonusData({ ...bonusData, [event.target.name]: event.target.value });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    BonusesService.update(id, bonusData)
      .then(() => {
        navigate("/bonuses/add");
      })
      .catch((error) => {
        console.error("Error actualizando el bono", error);
      });
  };

  return (
    <Paper elevation={3} style={{ padding: "20px", margin: "20px" }}>
      <form onSubmit={handleSubmit}>
        <Box display="flex" flexDirection="column" gap={2}>
          <TextField
            label="Cantidad"
            variant="outlined"
            name="disponibilidad"
            value={bonusData.disponibilidad}
            onChange={handleChange}
          />
          <TextField
            label="Marca"
            variant="outlined"
            name="marca"
            value={bonusData.marca}
            onChange={handleChange}
            InputProps={{ readOnly: true }}
          />
          <TextField
            label="Monto"
            variant="outlined"
            name="monto"
            value={bonusData.monto}
            onChange={handleChange}
            InputProps={{ readOnly: true }}
          />
          <Button variant="contained" color="primary" type="submit">
            Actualizar Bono
          </Button>
          <Button variant="contained" color="secondary" onClick={() => navigate("/bonuses/add")}>
            Cancelar
          </Button>
        </Box>
      </form>
    </Paper>
  );
};

export default EditBonus;
