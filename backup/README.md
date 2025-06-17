# Digital Wallet Backend

A simple digital wallet application built with Spring Boot that allows users to manage their virtual money.

## Features

- User registration and authentication using JWT
- View current balance
- Add money to wallet
- Withdraw money from wallet
- View transaction history
- In-memory H2 database
- Unit tests for core functionality

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd wallet-backend
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

- Register: `POST /api/auth/register`
  ```json
  {
    "username": "user",
    "password": "password"
  }
  ```

- Login: `POST /api/auth/login`
  ```json
  {
    "username": "user",
    "password": "password"
  }
  ```

### Wallet Operations

All wallet operations require a valid JWT token in the Authorization header:
`Authorization: Bearer <token>`

- Get Balance: `GET /api/wallet/balance`

- Add Money: `POST /api/wallet/add`
  ```json
  {
    "amount": 100.00
  }
  ```

- Withdraw Money: `POST /api/wallet/withdraw`
  ```json
  {
    "amount": 50.00
  }
  ```

- Get Transaction History: `GET /api/wallet/transactions`

## Testing

Run the tests using:
```bash
mvn test
```

## Database

The application uses an H2 in-memory database. You can access the H2 console at:
`http://localhost:8080/h2-console`

Database credentials (default):
- JDBC URL: `jdbc:h2:mem:walletdb`
- Username: `sa`
- Password: (empty)

## Security

- The application uses JWT for authentication
- Passwords are encrypted using BCrypt
- CORS is enabled for all origins (can be configured in SecurityConfig.java)
- H2 console is accessible without authentication (disable in production) 