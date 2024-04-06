import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import AddEditVehicle from './components/AddEditVehicle';


function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/vehicles" element={<AddEditVehicle/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App
