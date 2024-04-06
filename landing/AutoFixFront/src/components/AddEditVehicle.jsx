import vehicleService from '../services/Vehicle.service';
import { useState,useEffect } from 'react';
import { Link,useParams, useNavigate } from 'react-router-dom';


const AddEditVehicle = () => {
    const [patente, setPatente] = useState('');
    const [marca, setMarca] = useState('');
    const [modelo, setModelo] = useState('');
    const [tipo, setTipo] = useState('');
    const [anio_fabricacion, setAnio_fabricacion] = useState('');
    const [tipo_motor, setTipo_motor] = useState('');
    const [numero_asientos, setNumero_asientos] = useState('');
    const [numero_reparaciones, setNumero_reparaciones] = useState('');
}