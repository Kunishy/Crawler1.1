const { useState } = React;

// 定義為全局組件
window.App = function() {
  const [sights, setSights] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [selectedZone, setSelectedZone] = useState('');

  const zones = ['中山', '信義', '仁愛', '中正', '安樂', '七堵', '暖暖'];

  const loadZone = async (zone) => {
    setLoading(true);
    setError(null);
    setSelectedZone(zone);

    try {
      const response = await fetch(`/SightAPI?zone=${zone}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setSights(data);
    } catch (err) {
      setError(`資料載入失敗：${err.message}`);
      setSights([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="App bg-light min-vh-100">
      <div className="container-fluid container-lg py-3 py-lg-4 px-3 px-lg-4">
        {/* 響應式標題 */}
        <div className="text-center mb-3 mb-lg-4">
          <h1 className="app-title mb-2">基隆景點瀏覽器</h1>
          <p className="app-subtitle text-muted d-none d-md-block">
            探索基隆各區的精彩景點，體驗豐富的旅遊文化
          </p>
        </div>
        
        {/* 區域選擇按鈕 */}
        <ZoneButtons 
          zones={zones} 
          onZoneSelect={loadZone}
          selectedZone={selectedZone}
        />

        {/* 載入狀態 */}
        {loading && <LoadingSpinner />}
        
        {/* 錯誤訊息 */}
        {error && <ErrorMessage message={error} />}
        
        {/* 景點卡片網格 - 響應式佈局 */}
        {!loading && !error && sights.length > 0 && (
          <div className="sights-grid">
            <div className="row g-3 g-lg-4">
              {sights.map((sight, index) => (
                <div key={index} className="col-12 col-sm-6 col-lg-4 col-xl-3">
                  <SightCard sight={sight} index={index} />
                </div>
              ))}
            </div>
          </div>
        )}

        {/* 無資料狀態 */}
        {!loading && !error && sights.length === 0 && selectedZone && (
          <div className="text-center text-muted mt-4 py-5">
            <div className="no-data-icon mb-3">
              <i className="bi bi-search"></i>
            </div>
            <h5 className="text-muted">該區域暫無景點資料</h5>
            <p className="text-muted mb-0">請嘗試選擇其他區域或稍後再試</p>
          </div>
        )}

        {/* 歡迎訊息 - 首次訪問時顯示 */}
        {!loading && !error && sights.length === 0 && !selectedZone && (
          <div className="text-center text-muted mt-5 py-5">
            <div className="welcome-icon mb-3">
              <i className="bi bi-geo-alt"></i>
            </div>
            <h4 className="text-muted">歡迎使用基隆景點瀏覽器</h4>
            <p className="text-muted mb-0">請選擇上方區域按鈕開始探索基隆景點</p>
          </div>
        )}
      </div>
    </div>
  );
}
