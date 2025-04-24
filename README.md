# Real-time Stock Price Streamer

A real-time stock price monitoring system built with Spring Boot, Apache Kafka, and React.

## System Architecture

The application consists of three main components:

- **Producer**: Spring Boot service that generates and publishes stock price updates to Kafka
- **Consumer**: Spring Boot service that consumes stock prices from Kafka and exposes them via REST API
- **Frontend**: React application that displays real-time stock prices

## Prerequisites

- Java 17 (for Consumer) and Java 11 (for Producer)
- Node.js (version 16 or higher)
- Docker and Docker Compose
- Maven

## Project Structure

```text
realtime-stocks/
├── producer/           # Stock price producer service
├── consumer/           # Stock price consumer service
├── my-react-app/      # React frontend application
└── docker-compose.yml # Docker configuration for Kafka
```

[Project Screenshot](docs/images/Screenshot 2025-04-24 at 9.50.23 AM.png)

## Getting Started

1. **Start Kafka Infrastructure**

   ```bash
   docker-compose up -d
   ```

2. **Start Producer Service**

   ```bash
   cd producer
   ./mvnw spring-boot:run
   ```

3. **Start Consumer Service**

   ```bash
   cd consumer
   ./mvnw spring-boot:run
   ```

4. **Start Frontend Application**

   ```bash
   cd my-react-app
   npm install
   npm run dev
   ```

## Configuration

### Producer Service

- Default port: 8080
- Java version: 11
- Generates random stock prices for configured symbols

### Consumer Service

- Default port: 8888
- Java version: 17
- CORS enabled for frontend applications

### Frontend Application

- Default port: 5173 (Vite dev server)
- Real-time updates every 5 seconds
- Responsive grid layout for stock prices

## Development Notes

### CORS Configuration

The consumer service is configured to accept requests from the following origins:

- <http://localhost:3000>
- <http://localhost:5173>

If you need to modify CORS settings, update the following files:

- `consumer/src/main/java/com/thejas/consumer/controller/StockPriceController.java`
- `consumer/src/main/java/com/thejas/consumer/config/CorsConfig.java`

### Running on Different Ports

To change the frontend port, modify `my-react-app/vite.config.js`:

```javascript
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000, // Change this to your desired port
    strictPort: true
  }
})
```

## Troubleshooting

### CORS Issues

If you encounter CORS errors:

1. Verify that the consumer service CORS configuration includes your frontend origin
2. Check that you're using the correct ports in your configuration
3. Ensure all services are running and accessible

### Kafka Connection Issues

If Kafka connection fails:

1. Ensure Docker containers are running: `docker ps`
2. Check Kafka logs: `docker logs <kafka-container-id>`
3. Verify Kafka is accessible on port 9092

## License

This project is licensed under the MIT License - see the LICENSE file for details.
