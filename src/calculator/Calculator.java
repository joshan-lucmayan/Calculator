package calculator;

public class Calculator {

    private double firstNumber;
    private String operator;

    public void setFirstNumber(double number) {
        this.firstNumber = number;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Number calculate(double secondNumber) {
        double result = switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "×" -> firstNumber * secondNumber;
            case "÷" -> secondNumber == 0 ? 0 : firstNumber / secondNumber;
            default -> secondNumber;
        };

        if (result == Math.floor(result)) {
            return (int) result;
        } else {
            return result;
        }
    }

    public void clear() {
        firstNumber = 0;
        operator = "";
    }

    public boolean hasOperator() {
        return operator != null && !operator.isEmpty();
    }
}