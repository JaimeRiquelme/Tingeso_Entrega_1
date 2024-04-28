import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import AddEditVehicle from './components/AddEditVehicle';
import ListVehicle from './components/ListVehicle';
import AddBonuses from './components/AddBonuses';
import UpdateBonuses from './components/UpdateBonuses';
import CreateRepair from './components/CreateRepair';
import AVGHourReport from './components/ViewAVGHourReport';
import ViewNewRepair from './components/ViewNewRepair';
import ViewRepairsTypeReport from './components/ViewReapairsTypeReport';
import ViewRepairsByCombustible from './components/ViewRepairsByCombustible';
import RepairList from './components/RepairsList';
import ViewRepairsDetails from './components/ViewRepairsDetails';

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
              <Route path = "/GenerateRepair/add" element={<CreateRepair/>} />
              <Route path = "/GenerateRepair/AVGHourReport" element={<AVGHourReport/>} />
              <Route path = "/GenerateRepair/ViewNewRepair" element={<ViewNewRepair/>} />
              <Route path = "/GenerateRepair/ViewRepairsTypeReport" element={<ViewRepairsTypeReport/>} />
              <Route path = "/GenerateRepair/ViewRepairsByCombustible" element={<ViewRepairsByCombustible/>} />
              <Route path = "/repairs/list" element={<RepairList/>} />
              <Route path = "/repairs/details/:id" element={<ViewRepairsDetails/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App
