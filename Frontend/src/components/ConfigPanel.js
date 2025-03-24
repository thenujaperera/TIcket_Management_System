import React from "react";
import "./ConfigPanel.css";

const customLabels = {
  maxCapacity: "Max Capacity",
  numVendors: "No of Vendors",
  totalTickets: "Tickets per Vendor",
  vendorRate: "Vendor Rate (in secs)",
  numCustomers: "No of Customers",
  buyLimit: "Limit per Customer",
  customerRate: "Customer Rate (in secs)",
};

const ConfigPanel = ({ config, handleConfigChange, saveConfig, running }) => {

  return (
    <div className="section">
      <h2>Configuration Settings</h2>
      <div className="config-panel">
        {Object.keys(config).map((key) => (
          <div key={key} className="input-group">
             <label>{customLabels[key] || key.replace(/([A-Z])/g, " $1")}</label> 
            <input
              type="number"
              name={key}
              value={config[key]}
              onChange={(e) => {
                if (e.target.value >= 0 || e.target.value === "") {
                  handleConfigChange(e);
                }
              }}
              min="0"
              disabled={running}
            />
          </div>
        ))}
      </div>
      
    </div>
  );
};

export default ConfigPanel;
