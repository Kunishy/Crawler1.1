// 定義為全局組件
window.LoadingSpinner = () => {
  return (
    <div className="loading-spinner">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">載入中...</span>
      </div>
    </div>
  );
};
