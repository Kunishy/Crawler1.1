import React from 'react';
import './LoadingSpinner.css';

const LoadingSpinner = () => {
  return (
    <div className="loading-spinner">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">載入中...</span>
      </div>
      <span className="ms-2">載入中...</span>
    </div>
  );
};

export default LoadingSpinner; 