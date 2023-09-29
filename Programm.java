import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Programm {
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
            result = operands.firstOperand + operands.secondOperand;
        } else if (operator == Operator.MINUS) {
            result = operands.firstOperand - operands.secondOperand;
        } else if (operator == Operator.MULTIPLICATION) {
            result = operands.firstOperand * operands.secondOperand;
        } else if (operator == Operator.DIVISION) {
            result = operands.firstOperand / operands.secondOperand;
        } else {
            throw new Exception("unknown operator detected");
        }

        if (operands.isRomanNumber && result < 1) {
            throw new Exception("Roman number system does not have zero or negative numbers");
        }

        if (operands.isRomanNumber) {
            return parseArabicToRoman(result);
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
        String[] operands = input.split("[\\+\\-\\*/]");

        if (operands.length != 2) {
            throw new Exception("input must contain only 2 operands");
        }

        for (String operand : operands) {
            if (!isRomanOrArabicNumeral(operand)) {
                throw new Exception("operands must contain Roman or Arabic numerals");
            }
        }

        boolean areBothOperandsInRomanNumberSystem = isRomanNumeral(operands[0]) && isRomanNumeral(operands[1]);
        boolean areBothOperandsInArabicNumberSystem = isArabicNumeral(operands[0]) && isArabicNumeral(operands[1]);

        Operands result = new Operands();

        if (areBothOperandsInRomanNumberSystem) {
            result.isRomanNumber = true;
            result.firstOperand = parseRomanToArabic(operands[0].trim());
            result.secondOperand = parseRomanToArabic(operands[1].trim());
        } else if (areBothOperandsInArabicNumberSystem) {
            result.isRomanNumber = false;
            result.firstOperand = Integer.parseInt(operands[0].trim());
            result.secondOperand = Integer.parseInt(operands[1].trim());
        } else {
            throw new Exception("operands must be in the same number system");
        }

        if (result.firstOperand < 1 || result.firstOperand > 10) {
            throw new Exception("operands mast be in range from 1 to 10 inclusive");
        }

        if (result.secondOperand < 1 || result.secondOperand > 10) {
            throw new Exception("operands mast be in range from 1 to 10 inclusive");
        }

        return result;
    }

    private static Boolean isArabicNumeral(String string) {
        String patternForArabicNumerals = "^[0-9]+$";
        return string.trim().matches(patternForArabicNumerals);
    }

    private static Boolean isRomanNumeral(String string) {
        String patternForRomanNumerals = "^[IVX]+$";
        return string.trim().matches(patternForRomanNumerals);
    }

    private static Boolean isRomanOrArabicNumeral(String string) {
        String patternForRomanOrArabicNumerals = "^([IVX]+)|([0-9]+)$";
        return string.trim().matches(patternForRomanOrArabicNumerals);
    }

    private static int parseRomanToArabic(String roman) {
        Map<Character, Integer> arabicNumerals = new LinkedHashMap<>();
        arabicNumerals.put('I', 1);
        arabicNumerals.put('V', 5);
        arabicNumerals.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char currentChar = roman.charAt(i);
            int currentValue = arabicNumerals.get(currentChar);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    public static String parseArabicToRoman(int arabic) {
        Map<Integer, String> romanNumerals = new LinkedHashMap<>();

        romanNumerals.put(1000, "M");
        romanNumerals.put(900, "CM");
        romanNumerals.put(500, "D");
        romanNumerals.put(400, "CD");
        romanNumerals.put(100, "C");
        romanNumerals.put(90, "XC");
        romanNumerals.put(50, "L");
        romanNumerals.put(40, "XL");
        romanNumerals.put(10, "X");
        romanNumerals.put(9, "IX");
        romanNumerals.put(5, "V");
        romanNumerals.put(4, "IV");
        romanNumerals.put(1, "I");

        if (arabic < 1 || arabic > 3999) {
            throw new IllegalArgumentException("Входное число должно быть от 1 до 3999.");
        }

        StringBuilder roman = new StringBuilder();

        for (Map.Entry<Integer, String> entry : romanNumerals.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();

            while (arabic >= value) {
                roman.append(symbol);
                arabic -= value;
            }
        }

        return roman.toString();
    }

}

class Operands {
    int firstOperand;
    int secondOperand;
    Boolean isRomanNumber;
}
