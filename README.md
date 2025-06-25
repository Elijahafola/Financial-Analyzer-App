# Financial Analyzer

**Financial Analyzer** is a JavaFX desktop application that allows users to import and analyze financial transaction data from CSV files. The application generates insightful reports and trend data to help track income, expenses, and balance.

## ✨ Features

- Load and parse CSV-based financial records
- Calculate total income, expenses, and net balance
- Generate human-readable summary reports in plain text
- Export trend data for visualization in CSV format
- Ready for extension with charts and UI enhancements

## 📦 Project Structure

- `Main.java`: Launches the JavaFX GUI using `dashboard.fxml`
- `FinancialService.java`: Core logic for parsing CSVs and calculating financial metrics
- `resources/dashboard.fxml`: FXML layout for the main dashboard UI (not shown here)

## 📁 Sample CSV Format

```csv
Date,Description,Category,Amount
2025-01-01,Salary,Income,3000
2025-01-05,Rent,Expense,-1200
...

Run withn maven
mvn clean javafx:run

Build executable JAR
mvn clean package
java -jar target/financial-analyzer.jar
