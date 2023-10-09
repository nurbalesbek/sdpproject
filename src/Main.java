import java.util.Scanner;

// Интерфейс для ввода данных
interface InputProvider {
    double getPercentage();
}

// Реализация ввода данных студента
class StudentInput implements InputProvider {
    private Scanner scanner;

    public StudentInput() {
        scanner = new Scanner(System.in);
    }

    @Override
    public double getPercentage() {
        System.out.print("Введите процент: ");
        double percentage = scanner.nextDouble();
        return percentage;
    }
}

// Интерфейс для вычисления необходимого процента
interface Calculator {
    double calculateRequiredPercentage(double inputPercentage);
}

// Реализация вычисления необходимого процента за Final
class FinalExamCalculator implements Calculator {
    @Override
    public double calculateRequiredPercentage(double inputPercentage) {
        if (inputPercentage < 0 || inputPercentage > 100) {
            throw new IllegalArgumentException("Ошибка: Процент должен быть в пределах 0-100.");
        }

        double requiredFinalPercentage = 70.0;
        double requiredFinalPercentageScore = (requiredFinalPercentage - (inputPercentage * 0.6)) / 0.4;

        if (requiredFinalPercentageScore <= 100) {
            return requiredFinalPercentageScore;
        } else {
            throw new IllegalArgumentException("Ошибка: Невозможно достичь общего процента 70% и более.");
        }
    }
}

// Интерфейс для вывода результата
interface OutputProvider {
    void displayResult(double requiredPercentage);
}

// Реализация вывода результата в консоль
class ConsoleOutput implements OutputProvider {
    @Override
    public void displayResult(double requiredPercentage) {
        System.out.println("Студенту нужно набрать не менее " + requiredPercentage + "%.");
    }
}

// Декоратор для логирования
class LoggingDecorator implements Calculator {
    private Calculator calculator;

    public LoggingDecorator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public double calculateRequiredPercentage(double inputPercentage) {
        double result = calculator.calculateRequiredPercentage(inputPercentage);
        System.out.println("Лог: Рассчитан необходимый процент: " + result);
        return result;
    }
}

// Главный класс приложения
public class Main {
    public static void main(String[] args) {
        InputProvider inputProvider = new StudentInput();
        double inputPercentage = inputProvider.getPercentage();

        Calculator calculator = new FinalExamCalculator();
        calculator = new LoggingDecorator(calculator); // Добавляем логирование

        double requiredPercentage = calculator.calculateRequiredPercentage(inputPercentage);

        OutputProvider outputProvider = new ConsoleOutput();
        outputProvider.displayResult(requiredPercentage);
    }
}