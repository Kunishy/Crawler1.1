const { useState } = React;

// 定義為全局組件
window.SightCard = ({ sight, index }) => {
  const [imageError, setImageError] = useState(false);
  const [imageLoaded, setImageLoaded] = useState(false);
  const [showDetails, setShowDetails] = useState(false);

  const fallbackImages = [
    'https://via.placeholder.com/300x200/cccccc/666666?text=備用圖片1',
    'https://via.placeholder.com/300x200/e3f2fd/1976d2?text=備用圖片2',
    'https://via.placeholder.com/300x200/f3e5f5/7b1fa2?text=備用圖片3'
  ];

  const handleImageError = () => {
    setImageError(true);
  };

  const handleImageLoad = () => {
    setImageLoaded(true);
    setImageError(false);
  };

  const getGoogleMapsUrl = (address) => {
    return `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(address)}`;
  };

  const renderImage = () => {
    if (imageError || !sight.photoURL) {
      return (
        <div className="fallback-image">
          <span>圖片載入失敗</span>
        </div>
      );
    }

    return (
      <img
        src={sight.photoURL}
        className={`sight-image ${imageLoaded ? 'image-success' : ''}`}
        alt={sight.sightName}
        onError={handleImageError}
        onLoad={handleImageLoad}
      />
    );
  };

  return (
    <div className="card sight-card h-100">
      <div className="card-body d-flex flex-column">
        <h5 className="card-title">{sight.sightName}</h5>
        <p className="card-text">區域：{sight.zone}</p>
        <p className="card-text">分類：{sight.category}</p>
        
        <div className="btn-group mt-auto">
          <a
            className="btn btn-outline-primary mb-2"
            href={getGoogleMapsUrl(sight.address)}
            target="_blank"
            rel="noopener noreferrer"
          >
            地址
          </a>
          <button
            className="btn btn-secondary"
            onClick={() => setShowDetails(!showDetails)}
          >
            {showDetails ? '隱藏詳細資訊' : '詳細資訊'}
          </button>
        </div>

        {showDetails && (
          <div className="mt-3">
            <div className="image-container mb-2">
              {renderImage()}
            </div>
            <p className="card-text">{sight.description}</p>
          </div>
        )}
      </div>
    </div>
  );
};
