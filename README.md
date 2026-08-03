# FinSight AI

FinSight AI is a Spring Boot service for tracking investment holdings and
generating portfolio insights with Google Gemini.

## Architecture

```text
Client
  -> HoldingController / PortfolioController
  -> HoldingService / PortfolioAnalysisService
  -> H2 database and PriceService
  -> GeminiClient
  -> Google Gemini API
```

The portfolio analysis endpoint builds a prompt from the stored holdings and
sends it to Gemini `gemini-3.5-flash-lite`.

## Requirements

- Java 17 or newer
- Maven
- A Google Gemini API key

## Configuration

The API key is read from the `GEMINI_API_KEY` environment variable. Do not
commit a real key to this repository.

```bash
export GEMINI_API_KEY='your-google-gemini-api-key'
```

## Run locally

```bash
mvn spring-boot:run
```

Start the application as your normal user so that it inherits
`GEMINI_API_KEY`. If you must use `sudo`, explicitly pass the variable:

```bash
sudo env GEMINI_API_KEY="$GEMINI_API_KEY" mvn spring-boot:run
```

## API

### Create a holding

```bash
curl -X POST http://localhost:8080/api/holdings \
  -H 'Content-Type: application/json' \
  -d '{"symbol":"AAPL","quantity":11,"buyPrice":130}'
```

### List holdings

```bash
curl http://localhost:8080/api/holdings
```

### Delete a holding

```bash
curl -X DELETE http://localhost:8080/api/holdings/1
```

### Generate portfolio analysis

```bash
curl -X POST http://localhost:8080/api/portfolio/analyse
```

## Tests

```bash
mvn test
```

## Notes

- Generated Maven output is excluded through `target/` in `.gitignore`.
- Gemini API errors are returned as JSON by the global exception handler.
