// src/components/ControlPanel.js
import React from 'react';

const ControlPanel = ({ isRunning, onToggle, disabled }) => {
  return (
    <div className="control-panel">
      <button
        onClick={onToggle}
        disabled={disabled}
        className={`control-button ${isRunning ? 'running' : ''}`}
      >
        {isRunning ? 'Stop System' : 'Start System'}
      </button>
    </div>
  );
};

export default ControlPanel;