import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeIcon from "@mui/icons-material/Home";
import { useNavigate } from "react-router-dom";
import PlusIcon from '@mui/icons-material/Add';

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={toggleDrawer(false)}
    >
      <List>
        <ListItemButton onClick={() => navigate("/home")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>

        <Divider />
        <ListItemButton onClick={() => navigate("/vehicles/List")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver vehículos" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/bonuses/add")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver y Actualizar Bonos" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/GenerateRepair/add")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Registrar Reparación" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/repairs/list")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Ver Reparaciones" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/GenerateRepair/AVGHourReport")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Reporte de horas promedio" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/GenerateRepair/ViewRepairsTypeReport")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Reporte Reparaciones vs Tipo" />
        </ListItemButton>
        <ListItemButton onClick={() => navigate("/GenerateRepair/ViewRepairsByCombustible")}>
          <ListItemIcon>
          <PlusIcon/>
          </ListItemIcon>
          <ListItemText primary="Reporte Reparaciones por combustible" />
        </ListItemButton>
        
      </List>
      <Divider />
    </Box>
  );

  return (
    <div>
      <Drawer anchor={"left"} open={open} onClose={toggleDrawer(false)}>
        {listOptions()}
      </Drawer>
    </div>
  );
}
