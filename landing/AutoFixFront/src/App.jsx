import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import AddEditVehicle from './components/AddEditVehicle';
import ListVehicle from './components/ListVehicle';
import AddBonuses from './components/AddBonuses';
import UpdateBonuses from './components/UpdateBonuses';

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/vehicles/add" element={<AddEditVehicle/>} />
              <Route path="/vehicles/list" element={<ListVehicle/>} />
              <Route path ="/bonuses/add" element={<AddBonuses/>} />
              <Route path ="/bonuses/edit/:id" element={<UpdateBonuses/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App
