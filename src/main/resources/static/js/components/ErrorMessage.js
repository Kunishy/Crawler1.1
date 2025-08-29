// 定義為全局組件
window.ErrorMessage = ({ message }) => {
  return (
    <div className="error-message">
      <i className="bi bi-exclamation-triangle me-2"></i>
      {message}
    </div>
  );
};
