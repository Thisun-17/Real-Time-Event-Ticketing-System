// src/components/ConfigurationForm.js
import React from 'react';

const ConfigurationForm = ({ config, onChange, disabled }) => {
  return (
    <div className="config-section">
      <h2>System Configuration</h2>
      <div className="config-form">
        <div>
          <label htmlFor="totalTickets">Total Tickets:</label>
          <input
            id="totalTickets"
            type="number"
            name="totalTickets"
            value={config.totalTickets}
            onChange={onChange}
            disabled={disabled}
            min="0"
          />
        </div>
        <div>
          <label htmlFor="releaseRate">Release Rate:</label>
          <input
            id="releaseRate"
            type="number"
            name="releaseRate"
            value={config.releaseRate}
            onChange={onChange}
            disabled={disabled}
            min="1"
          />
        </div>
        <div>
          <label htmlFor="maxCapacity">Maximum Capacity:</label>
          <input
            id="maxCapacity"
            type="number"
            name="maxCapacity"
            value={config.maxCapacity}
            onChange={onChange}
            disabled={disabled}
            min="1"
          />
        </div>
      </div>
    </div>
  );
};

export default ConfigurationForm;