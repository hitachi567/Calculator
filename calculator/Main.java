package calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("This is a calculator.");
        System.out.println("It can handle 4 arithmetic operations on integers ranging from 1 to 10 inclusive:");
        System.out.println("(Addition, Subtraction, Multiplication and Integer Division).");
        System.out.println("The calculator also supports Roman numerals.");

        try {
            while (true) {
                System.out.print(">>> ");
                String input = in.nextLine().trim();
                System.out.println(calc(input));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        in.close();
    }

    public static String calc(String input) throws Exception {
        if (input.isBlank()) {
            throw new Exception("input must not be empty");
        }

        Operator operator = defineOperator(input);
        Operands operands = defineOperands(input);

        int result;

        if (operator == Operator.PLUS) {
            result = operands.numbers[0] + operands.numbers[1];
        } else if (operator == Operator.MINUS) {
            result = operands.numbers[0] - operands.numbers[1];
        } else if (operator == Operator.MULTIPLICATION) {
            result = operands.numbers[0] * operands.numbers[1];
        } else if (operator == Operator.DIVISION) {
            result = operands.numbers[0] / operands.numbers[1];
        } else {
            throw new Exception("unknown operator detected");
        }

        if (operands.numberSystem == NumberSystem.ROMAN && result < 1) {
            throw new Exception("Roman number system does not have zero or negative numbers");
        }

        if (operands.numberSystem == NumberSystem.ROMAN) {
            return NumberSystem.convertArabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static Operator defineOperator(String input) throws Exception {
        Operator operator;

        if (input.contains("+")) {
            operator = Operator.PLUS;
        } else if (input.contains("-")) {
            operator = Operator.MINUS;
        } else if (input.contains("*")) {
            operator = Operator.MULTIPLICATION;
        } else if (input.contains("/")) {
            operator = Operator.DIVISION;
        } else {
            throw new Exception("input must contain arithmetic operators (+, -, * or /)");
        }

        return operator;
    }

    enum Operator {
        PLUS,
        MINUS,
        MULTIPLICATION,
        DIVISION
    }

    private static Operands defineOperands(String input) throws Exception {
        String[] splittedInput = input.split("[\\+\\-\\*/]");

        if (splittedInput.length != 2) {
            throw new Exception("input must contain only 2 operands");
        }

        Operands operands = new Operands(splittedInput);

        for (int number : operands.numbers) {
            if (number < 1 || number > 10) {
                throw new Exception("operands mast be in range from 1 to 10 inclusive");
            }
        }

        return operands;
    }
}
