package calculator;

class Operands {

    NumberSystem numberSystem;
    int[] numbers;

    Operands(String[] operands) throws NumberSystemException {
        for (int i = 0; i < operands.length; i++) {
            String operand = operands[i] = operands[i].trim();

            if (i == 0) {
                numberSystem = NumberSystem.defineNumberSystem(operand);
                continue;
            }

            if (numberSystem != NumberSystem.defineNumberSystem(operand)) {
                throw new NumberSystemException("operands must be in the same number system");
            }
        }

        numbers = new int[operands.length];

        if (numberSystem == NumberSystem.ARABIC) {
            for (int i = 0; i < operands.length; i++) {
                numbers[i] = Integer.parseInt(operands[i]);
            }
        } else if (numberSystem == NumberSystem.ROMAN) {
            for (int i = 0; i < operands.length; i++) {
                numbers[i] = NumberSystem.convertRomanToArabic(operands[i]);
            }
        } else {
            throw new NumberSystemException("this number system is not supported");
        }
    }

}