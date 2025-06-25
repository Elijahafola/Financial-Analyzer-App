package hellofx;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class FinancialService {

    private final List<String> errorLog = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void calculateMetrics(File file) {
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            logError("Unsupported file type for financial analysis: " + file.getName());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                logError("Empty CSV file.");
                return;
            }

            String[] headers = headerLine.split(",");
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i].trim().toLowerCase(), i);
            }

            if (!columnIndex.containsKey("date") || !columnIndex.containsKey("amount")) {
                logError("CSV must contain 'Date' and 'Amount' columns.");
                return;
            }

            int dateIndex = columnIndex.get("date");
            int amountIndex = columnIndex.get("amount");

            double totalIncome = 0.0;
            double totalExpense = 0.0;
            List<LocalDate> dates = new ArrayList<>();
            List<Double> amounts = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length <= Math.max(dateIndex, amountIndex)) continue;

                try {
                    LocalDate date = LocalDate.parse(parts[dateIndex].trim(), formatter);
                    double amount = Double.parseDouble(parts[amountIndex].trim());
                    if (amount > 0) totalIncome += amount;
                    else totalExpense += amount;

                    dates.add(date);
                    amounts.add(amount);
                } catch (DateTimeParseException | NumberFormatException e) {
                    logError("Skipping invalid entry: " + line);
                }
            }

            double balance = totalIncome + totalExpense;

            System.out.printf("✅ Total Income: %.2f%n", totalIncome);
            System.out.printf("📉 Total Expenses: %.2f%n", totalExpense);
            System.out.printf("💼 Balance: %.2f%n", balance);

            generateTextReport(file.getParentFile(), totalIncome, totalExpense, balance);
            generateCSVReport(file.getParentFile(), dates, amounts);
            showErrors();

        } catch (IOException e) {
            logError("Error reading file: " + e.getMessage());
        }
    }

    private void generateTextReport(File folder, double income, double expenses, double balance) throws IOException {
        File report = new File(folder, "report.txt");
        try (PrintWriter writer = new PrintWriter(report)) {
            writer.println("Financial Summary Report");
            writer.println("------------------------");
            writer.printf("Total Income: %.2f%n", income);
            writer.printf("Total Expenses: %.2f%n", expenses);
            writer.printf("Balance: %.2f%n", balance);
        }
        System.out.println("📝 Text report saved as: " + report.getAbsolutePath());
    }

    private void generateCSVReport(File folder, List<LocalDate> dates, List<Double> amounts) throws IOException {
        File trendFile = new File(folder, "trend.csv");
        try (PrintWriter writer = new PrintWriter(trendFile)) {
            writer.println("Date,Amount");
            for (int i = 0; i < dates.size(); i++) {
                writer.printf("%s,%.2f%n", dates.get(i), amounts.get(i));
            }
        }
        System.out.println("📈 Trend data saved as: " + trendFile.getAbsolutePath());
    }

    private void logError(String message) {
        errorLog.add(message);
    }

    private void showErrors() {
        if (!errorLog.isEmpty()) {
            System.out.println("⚠️ Some issues were encountered:");
            errorLog.forEach(msg -> System.out.println(" - " + msg));
        }
    }
}
