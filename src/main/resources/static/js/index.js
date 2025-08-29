console.log('React應用程式正在載入...');
console.log('React版本:', React.version);
console.log('ReactDOM版本:', ReactDOM.version);

// 檢查組件是否已載入
console.log('LoadingSpinner:', typeof LoadingSpinner);
console.log('ErrorMessage:', typeof ErrorMessage);
console.log('SightCard:', typeof SightCard);
console.log('ZoneButtons:', typeof ZoneButtons);
console.log('App:', typeof App);

// 錯誤處理
window.addEventListener('error', (event) => {
  console.error('JavaScript錯誤:', event.error);
});

window.addEventListener('unhandledrejection', (event) => {
  console.error('未處理的Promise拒絕:', event.reason);
});

try {
  const root = ReactDOM.createRoot(document.getElementById('root'));
  root.render(<App />);
  console.log('React應用程式已載入完成！');
} catch (error) {
  console.error('React應用程式載入失敗:', error);
  document.getElementById('root').innerHTML = `
    <div style="padding: 2rem; text-align: center; color: red;">
      <h3>應用程式載入失敗</h3>
      <p>錯誤: ${error.message}</p>
      <p>請檢查瀏覽器控制台獲取詳細信息</p>
    </div>
  `;
}
