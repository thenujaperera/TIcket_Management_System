import React, { useState, useEffect } from "react";
import ConfigPanel from "./components/ConfigPanel";
import TicketStatus from "./components/TicketStatus";

import ControlPanel from "./components/ControlPanel";
import LogPanel from "./components/LogPanel";
import "./styles/App.css";

function App() {
  const defaultConfig = {
    maxCapacity: 0,
    numVendors: 0,
    totalTickets: 0,
    vendorRate: 0,
    numCustomers: 0,
    buyLimit: 0,
    customerRate: 0,
  };

  const [config, setConfig] = useState(defaultConfig);
  const [ticketPool, setTicketPool] = useState([]);
  const [log, setLog] = useState([]);
  const [running, setRunning] = useState(false);
  const [configLoaded, setConfigLoaded] = useState(false);
  const [ticketStatus, setTicketStatus] = useState({
    ticketsAvailable: 0,
    ticketsSold: 0,
    ticketsReleased: 0,
  });

  // Fetch initial configuration on load
  useEffect(() => {
    fetch("http://localhost:8080/api/configuration") // Replace with your backend URL
      .then((res) => res.json())
      .then((data) => {
        if (Object.keys(data).length > 0) {
          setConfig(data);
          setConfigLoaded(true);
        } else {
          console.warn("No configuration found. Please configure the system.");
          setConfig(defaultConfig);
          setConfigLoaded(false);
        }
      })
      .catch((err) => {
        console.error("Failed to fetch configuration:", err);
        setConfig(defaultConfig);
        setConfigLoaded(false);
      });
  }, []);

  useEffect(() => {
    const fetchLogs = () => {
      fetch("http://localhost:8080/api/logs") // Replace with your backend URL
        .then((res) => res.json())
        .then((data) => {
          setLog((prevLogs) => [...prevLogs, ...data]); // Append new logs
        })
        .catch((err) => console.error("Failed to fetch logs:", err));
    };

    const interval = setInterval(fetchLogs, 1000); // Fetch logs every second
    return () => clearInterval(interval); // Cleanup on unmount
  }, []);

  const flushLogs = () => {
    setLog([]); // Clear the logs
  };
  
  const validateConfig = () => {
    // Check if any of the config values are zero
    const invalidConfigKeys = Object.keys(config).filter(
      (key) => config[key] === 0
    );

    if (invalidConfigKeys.length > 0) {
      alert(
        `The following configuration fields have a value of zero: ${invalidConfigKeys
          .map((key) => key.replace(/([A-Z])/g, " $1"))
          .join(", ")}. Please review and update them before saving.`
      );
      return false;
    }
    return true;
  };

  const handleSaveClick = () => {
    if (validateConfig()) {
      saveConfig();
    }
  };
  const handleConfigChange = (e) => {
    const { name, value } = e.target;
    setConfig((prev) => ({ ...prev, [name]: parseInt(value) || 0 }));
  };

  const saveConfig = () => {
    fetch("http://localhost:8080/api/configure", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(config),
    })
      .then((res) => res.text())
      .then((msg) => {
        console.log(msg);
        setConfigLoaded(true);
      })
      .catch((err) => console.error("Failed to save configuration:", err));
  };

  const startSimulation = () => {
    if (!configLoaded) {
      console.warn("Cannot start simulation. Please save the configuration first.");
      return;
    }
    fetch("http://localhost:8080/api/start", { method: "POST" })
      .then((res) => res.text())
      .then((msg) => {
        console.log(msg);
        setRunning(true);
        startPollingTicketStatistics(); // Start polling after simulation starts
      })
      .catch((err) => console.error("Failed to start simulation:", err));
  };

  const stopSimulation = () => {
    fetch("http://localhost:8080/api/stop", { method: "POST" })
      .then((res) => res.text())
      .then((msg) => {
        console.log(msg);
        setRunning(false);
        /*stopPollingTicketStatistics(); // Stop polling when simulation stops*/
      })
      .catch((err) => console.error("Failed to stop simulation:", err));
  };

  const resetSimulation = () => {
    fetch("http://localhost:8080/api/reset", { method: "POST" })
      .then((res) => res.text())
      .then((msg) => {
        console.log(msg);
        setRunning(false);
        stopPollingTicketStatistics(); // Stop polling when simulation is reset
        setTicketStatus({ ticketsAvailable: 0, ticketsSold: 0, ticketsReleased: 0 });
        
      flushLogs();

        // Re-fetch configuration from the backend
        fetch("http://localhost:8080/api/configuration")
          .then((res) => res.json())
          .then((data) => {
            if (Object.keys(data).length > 0) {
              setConfig(data); // Restore configuration from backend
              setConfigLoaded(true);
              console.log("Configuration reset to the last saved state.");
            } else {
              console.warn("No configuration found on reset.");
              setConfig(defaultConfig); // Reset to defaults if no config found
              setConfigLoaded(false);
            }
          })
          .catch((err) => {
            console.error("Failed to fetch configuration during reset:", err);
            setConfig(defaultConfig);
            setConfigLoaded(false);
          });
      })
      .catch((err) => console.error("Failed to reset simulation:", err));
  };
  

  // Fetch ticket statistics from the backend
  const fetchStatus = () => {
    fetch("http://localhost:8080/api/status")
      .then((res) => res.json())
      .then((data) => setTicketStatus(data))
      .catch((err) => console.error("Failed to fetch ticket pool status:", err));
  };

  // Start polling ticket statistics every second (1000ms)
  const startPollingTicketStatistics = () => {
    const interval = setInterval(() => {
      fetchStatus();
    }, 1000);

    // Save the interval ID so it can be cleared later
    window.pollingInterval = interval;
  };

  // Stop polling
  const stopPollingTicketStatistics = () => {
    if (window.pollingInterval) {
      clearInterval(window.pollingInterval);
      window.pollingInterval = null;
    }
  };





  return (
    <div className="App">
      <h1>Ticket Management System</h1>
      <ConfigPanel
        config={config}
        handleConfigChange={handleConfigChange}
        saveConfig={saveConfig}
        running={running}
      />
      <TicketStatus ticketPool={ticketPool} ticketStatus={ticketStatus} />
      <ControlPanel
        handleSaveClick={handleSaveClick}
        startSimulation={startSimulation}
        stopSimulation={stopSimulation}
        resetSimulation={resetSimulation}
        running={running}
        configLoaded={configLoaded}
      />
      <LogPanel log={log} />
    </div>
  );
}

export default App;
