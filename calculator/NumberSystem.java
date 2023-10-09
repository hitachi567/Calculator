package calculator;

import java.util.LinkedHashMap;
import java.util.Map;

public enum NumberSystem {
    ROMAN,
    ARABIC;

    public static NumberSystem defineNumberSystem(String number) throws NumberSystemException {
        if (isArabic(number)) {
            return NumberSystem.ARABIC;
        }

        if (isRoman(number)) {
            return NumberSystem.ROMAN;
        }

        throw new NumberSystemException("only arabic and roman number systems are supported");
    }

    private static Boolean isArabic(String number) {
        String patternForArabicNumerals = "^[0-9]+$";
        return number.trim().matches(patternForArabicNumerals);
    }

    private static Boolean isRoman(String number) {
        String patternForRomanNumerals = "^[IVX]+$";
        return number.trim().matches(patternForRomanNumerals);
    }

    public static int convertRomanToArabic(String roman) {
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

    public static String convertArabicToRoman(int arabic) {
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
