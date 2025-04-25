import './polyfills.js';
import { useState, useEffect, useCallback } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import './App.css';

function App() {
  const [stockPrices, setStockPrices] = useState({});
  const [error, setError] = useState(null);
  const [connectionStatus, setConnectionStatus] = useState('Connecting...');

  const setupStompClient = useCallback(() => {
    const stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8888/ws'),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        setConnectionStatus('Connected');
        setError(null);
        
        // Subscribe to stock price updates
        stompClient.subscribe('/topic/stock-prices', (message) => {
          const data = JSON.parse(message.body);
          if (data.stockPrices) {
            setStockPrices(prevPrices => {
              const newPrices = {...data.stockPrices};
              // Add price change indicators
              Object.keys(newPrices).forEach(symbol => {
                newPrices[symbol].change = 
                  prevPrices[symbol] ? 
                  newPrices[symbol].price - prevPrices[symbol].price : 
                  0;
              });
              return newPrices;
            });
          }
        });
      },
      onDisconnect: () => {
        setConnectionStatus('Disconnected');
      },
      onStompError: (frame) => {
        setError(`Connection error: ${frame.headers?.message || 'Unknown error'}`);
        setConnectionStatus('Error');
      }
    });

    // Activate the client
    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, []);

  useEffect(() => {
    const cleanup = setupStompClient();
    return () => cleanup();
  }, [setupStompClient]);

  return (
    <div className="container">
      <h1>Real-time Stock Prices</h1>
      <div className={`connection-status ${connectionStatus.toLowerCase()}`}>
        {connectionStatus}
      </div>
      
      {error && <div className="error-banner">{error}</div>}
      
      <div className="stock-grid">
        {Object.entries(stockPrices).map(([symbol, data]) => (
          <div key={symbol} 
               className={`stock-card ${data.change > 0 ? 'positive' : data.change < 0 ? 'negative' : ''}`}>
            <h2>{symbol}</h2>
            <p className="price">
              ${typeof data.price === 'number' ? data.price.toFixed(2) : 'N/A'}
            </p>
            {data.change !== 0 && (
              <p className="change">
                {data.change > 0 ? '▲' : '▼'} 
                ${Math.abs(data.change).toFixed(2)}
              </p>
            )}
            <p className="timestamp">
              {new Date(data.timestamp).toLocaleTimeString()}
            </p>
          </div>
        ))}
      </div>

      <div className="debug">
        <p>Last update: {new Date().toLocaleString()}</p>
        <p>Number of stocks: {Object.keys(stockPrices).length}</p>
      </div>
    </div>
  );
}

export default App;
