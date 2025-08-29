import React from 'react';
import './ZoneButtons.css';

const ZoneButtons = ({ zones, onZoneSelect, selectedZone }) => {
  return (
    <div className="zone-buttons-wrapper">
      <div className="zone-buttons-container">
        {zones.map((zone) => (
          <button
            key={zone}
            className={`btn zone-button ${
              selectedZone === zone ? 'btn-primary active' : 'btn-outline-primary'
            }`}
            onClick={() => onZoneSelect(zone)}
            aria-label={`選擇${zone}區景點`}
          >
            <span className="zone-text">{zone}區</span>
          </button>
        ))}
      </div>
    </div>
  );
};

export default ZoneButtons; 