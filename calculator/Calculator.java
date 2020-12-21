package calculator;

import java.util.HashSet;
import java.util.Scanner;

public class Calculator {
    public static void run(Scanner scanner){
        CalculatorCommand command;
        do {
            var userInput = scanner.nextLine();
            command = CalculatorCommand.parseCommand(userInput);

            switch (command) {
                case EXPRESSION:
                    tryEvaluateExpression(userInput);
                    break;
                case HELP:
                    printHelpMessage();
                    break;
                case UNKNOWN:
                    printUnknownCommandMessage();
                    break;
                case EXIT:
                    printExitMessage();
                    break;
                case EMPTY:
                    break;
            }

        } while (command != CalculatorCommand.EXIT);
    }

    private static void printExitMessage() {
        System.out.println("Bye!");
    }

    private static void printUnknownCommandMessage() {
        System.out.println("Unknown Command");
    }

    private static void printHelpMessage() {
        System.out.println("The program calculates the sum and subtraction of n numbers in a single line");
    }

    private static void tryEvaluateExpression(String expression) {
        try {
            var result = evaluateExpression(expression);
            System.out.println(result);
        } catch (NumberFormatException ignored) {
            System.out.println("Invalid Expression");
        }
    }

    private static int evaluateExpression(String expression) {
        var operands = new HashSet<Integer>();
        var tokens = expression.split("\\s+");
        boolean neg = false;
        for (var token : tokens) {
            if (token.matches("[-+]?\\d+")) {
                var integer = token.startsWith("+") ? token.substring(1) : token;
                var operand = Integer.parseInt(integer);
                if (neg) {
                    operand = Math.negateExact(operand);
                    neg = false;
                }
                operands.add(operand);
            } else if (token.matches("-+")){
                neg = token.length() % 2 == 1;
            } else if (token.matches("\\++")) {
                neg = false;
            } else {
                throw new NumberFormatException();
            }
        }
        return operands.stream().mapToInt(Integer::intValue).sum();
    }
}
