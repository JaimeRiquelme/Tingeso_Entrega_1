import React from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack'; // Importa Stack para un mejor alineamiento

const Home = () => {
  const navigate = useNavigate();

  return (
    <div style={{ margin: '20px' }}>
      <h1>AutoFix : Sistema de Gestión de Reparaciones y Mantenciones de Vehículos</h1>
      <p>
        AutoFix es una aplicación web para gestionar las reparaciones y mantenciones de vehículos.
        Esta aplicación ha sido desarrollada usando tecnologías como 
        <a href="https://spring.io/projects/spring-boot"> Spring Boot</a> (para
        el backend), <a href="https://reactjs.org/">React</a> (para el Frontend)
        y <a href="https://www.postgresql.org">PostgreSQL</a> (para la
        base de datos).
      </p>
      <Stack direction="column" spacing={2} alignItems={'center'}>
        <Button variant="contained" size="large"  onClick={() => navigate("/vehicles/List")} style={{ width: 250 }}>
          Ver Listado vehículos
        </Button>
        <Button variant="contained" size="large" onClick={() => navigate("/bonuses/add")} style={{ width: 250 }}>
          Ver y Actualizar Bonos
        </Button>
        <Button variant="contained" size="large" onClick={() => navigate("/GenerateRepair/add")} style={{ width: 250 }}>
          Registrar Reparación
        </Button>
        <Button variant="contained" size="large" onClick={() => navigate("/repairs/list")} style={{ width: 250 }}>
          Ver Reparaciones
        </Button>
        <Button variant="contained" size="large" onClick={() => navigate("/GenerateRepair/AVGHourReport")} style={{ width: 250 }}>
          Reporte de horas promedio
        </Button>
        <Button variant="contained" size="large" onClick={() => navigate("/GenerateRepair/ViewRepairsTypeReport")} style={{ width: 250 }}>
          Reporte Reparaciones vs Tipo
        </Button>
        <Button variant="contained" size="large" onClick={() => navigate("/GenerateRepair/ViewRepairsByCombustible")} style={{ width: 250 }}>
          Reporte Reparaciones por combustible
        </Button>
      </Stack>
    </div>
  );
};

export default Home;
