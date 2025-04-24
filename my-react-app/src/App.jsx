import { useState, useEffect } from 'react'
import './App.css'

function App() {
  const [stockPrices, setStockPrices] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    // Initial fetch
    fetchStockPrices();

    // Set up polling every 5 seconds
    const interval = setInterval(fetchStockPrices, 5000);

    return () => clearInterval(interval);
  }, []);

  const fetchStockPrices = async () => {
    try {
      const response = await fetch('http://localhost:8888/');
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      console.log('Received data:', data); // Debug log
      
      if (data.stockPrices) {
        setStockPrices(data.stockPrices);
        setError(null);
      } else {
        console.error('Invalid data format:', data);
        setError('Invalid data format received from server');
      }
    } catch (error) {
      console.error('Error fetching stock prices:', error);
      setError(`Failed to fetch stock prices: ${error.message}`);
    }
  };

  if (error) {
    return (
      <div className="container error">
        <h1>Error</h1>
        <p>{error}</p>
      </div>
    );
  }

  return (
    <div className="container">
      <h1>Real-time Stock Prices</h1>
      {Object.keys(stockPrices).length === 0 ? (
        <p className="loading">Loading stock prices...</p>
      ) : (
        <div className="stock-grid">
          {Object.entries(stockPrices).map(([symbol, data]) => (
            <div key={symbol} className="stock-card">
              <h2>{symbol}</h2>
              <p className="price">
                ${typeof data.price === 'number' ? data.price.toFixed(2) : 'N/A'}
              </p>
              <p className="timestamp">
                {data.timestamp ? new Date(data.timestamp).toLocaleString() : 'N/A'}
              </p>
            </div>
          ))}
        </div>
      )}
      <div className="debug">
        <p>Last update: {new Date().toLocaleString()}</p>
        <p>Number of stocks: {Object.keys(stockPrices).length}</p>
      </div>
    </div>
  )
}

export default App
