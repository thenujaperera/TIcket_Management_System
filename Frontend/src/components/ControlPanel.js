import React from "react";
import "./ControlPanel.css";

const ControlPanel = ({ handleSaveClick, startSimulation, stopSimulation, resetSimulation, running }) => (
  <div className="section">
    <h2>Control Panel</h2>
    <div className="buttons">
    <button onClick={handleSaveClick} disabled={running}>
        Save 
      </button>
    <button 
        onClick= { startSimulation} disabled={running}>
        Start
      </button>
      <button onClick={stopSimulation} disabled={!running}>
        Stop
      </button>
      <button onClick={resetSimulation}>Reset</button>
    </div>
  </div>
);

export default ControlPanel;
