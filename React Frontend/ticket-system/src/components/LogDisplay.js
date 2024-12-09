// src/components/LogDisplay.js
import React from 'react';

const LogDisplay = ({ logs }) => {
  return (
    <div className="log-section">
      <h3>System Logs</h3>
      <div className="log-container">
        {logs.map((log, index) => (
          <div key={index} className="log-entry">
            <span className="log-timestamp">{log.timestamp}</span>
            <span className="log-message">{log.message}</span>
          </div>
        ))}
        {logs.length === 0 && (
          <div className="log-empty">No logs available</div>
        )}
      </div>
    </div>
  );
};

export default LogDisplay;