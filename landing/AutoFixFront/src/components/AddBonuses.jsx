import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import BonusesService from "../services/Bonuses.service";
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit'; // Asegúrate de importar este ícono

const ListBonuses = () => {
    const [bonuses, setBonuses] = useState([]);
    const [selectedBonusId, setSelectedBonusId] = useState(null);
    const navigate = useNavigate();

    const init = () => {
        BonusesService.getAll().then((response) => {
            setBonuses(response.data);
        })
        .catch((error) => {
            console.error("Hubo un error al mostrar bonos", error);
        });
    };

    useEffect(() => {
        init();
    }, []);

    const updateBonus = (id) => {
        navigate(`/bonuses/edit/${id}`);
    };

    return (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell>ID</TableCell>
                <TableCell align="right">Cantidad</TableCell>
                <TableCell align="right">Marca</TableCell>
                <TableCell align="right">Monto</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {bonuses.map((bonus) => (
                <TableRow
                  key={bonus.id}
                  sx={{
                    '&:last-child td, &:last-child th': { border: 0 },
                    '&:hover': {
                      backgroundColor: 'rgba(0, 0, 0, 0.1)',
                      cursor: 'pointer' 
                    }
                  }}
                  selected={selectedBonusId === bonus.id}
                  onClick={() => setSelectedBonusId(bonus.id)}
                >
                  <TableCell component="th" scope="row">{bonus.id}</TableCell>
                  <TableCell align="right">{bonus.disponibilidad}</TableCell>
                  <TableCell align="right">{bonus.marca}</TableCell>
                  <TableCell align="right">{bonus.monto}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
          {selectedBonusId && (
            <Button
              variant="contained"
              color="primary"
              startIcon={<EditIcon />}
              onClick={() => updateBonus(selectedBonusId)}
              style={{ marginTop: "20px" }}
            >
              Actualizar Bono
            </Button>
          )}
        </TableContainer>
    );
};

export default ListBonuses;
