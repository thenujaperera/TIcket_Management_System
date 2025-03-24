import React, { useEffect, useRef, useState } from "react";
import "./LogPanel.css";

const LogPanel = ({ log }) => {
  const logsContainerRef = useRef(null);
  const [isAtBottom, setIsAtBottom] = useState(true);

  // Detect if the user has scrolled up
  const handleScroll = () => {
    if (!logsContainerRef.current) return;
    const { scrollTop, scrollHeight, clientHeight } = logsContainerRef.current;
    setIsAtBottom(scrollTop + clientHeight >= scrollHeight - 5); // Add margin for precision
  };

  // Scroll to bottom only if the user is already at the bottom
  useEffect(() => {
    if (isAtBottom && logsContainerRef.current) {
      logsContainerRef.current.scrollTop = logsContainerRef.current.scrollHeight;
    }
  }, [log, isAtBottom]);

  return (
    <div className="section">
      <h2>Logs</h2>
      <div
        className="logs"
        ref={logsContainerRef}
        onScroll={handleScroll}
      >
        {log.map((entry, index) => (
          <p key={index}>{entry}</p>
        ))}
      </div>
    </div>
  );
};

export default LogPanel;
