# Expense Tracker API

Expense Tracker API is a web API that allows users to track their expenses. Users can create, read, update, and delete expenses, as well as generate reports on a monthly or weekly basis. Users are required to sign in/register to access the API.

## Features

- User authentication: Users can sign in or register to access the API.
- Expense CRUD operations: Users can create, read, update, and delete their expenses.
- Report generation: Users can generate reports for a specific month or week, summarizing their expenses.
- Persistence: The API stores expense records for at least 3 months.

## Technologies Used

- Java
- Spring Boot
- Maven

## Setup Instructions

1. Clone the repository
2. Navigate to the project directory
3. Build the project using Maven
4. Run the application
5. The API will be accessible at `http://localhost:8080`.

## API Documentation

The [API documentation](https://documenter.getpostman.com/view/26741522/2s93m1bQfE) is provided using Swagger. You can access it at `http://localhost:8080/swagger-ui.html`. The Swagger UI provides detailed information about the available endpoints, request/response structures, and allows for easy testing of the API.

## Usage Examples
### Sign Up

Endpoint: `POST /api/signup`

Create a new user account.
### Sign In
Endpoint: POST /api/signin

Authenticate and retrieve an access token for subsequent API requests.
### Sign out
Endpoint: POST /api/signout
### Create Expense
Endpoint: POST /api/expenses
### Get Expense for particluar date
Endpoint: GET /api/expenses/date
Create a new expense.
### Get Monthly Expenditure
Endpoint: GET /api/expenses/monthly
### Get Weekly Expenditure
Endpoint: GET /api/expenses/weekly
### Get total  Expenditure
Endpoint: GET /api/expenses/totalExpenditure

Retrieve the total expenditure for a specific month.
## Contribution
Contributions to the Expense Tracker API are welcome! If you find any issues or would like to add new features, feel free to submit a pull request.

Please make sure to follow the existing code style and provide appropriate tests for any changes.