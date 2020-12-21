package calculator;

import java.util.*;

public class Calculator {
    private static final Variables VARIABLES = new Variables();

    public static void run(Scanner scanner){
        CalculatorCommand command;
        do {
            var userInput = scanner.nextLine().trim();
            command = CalculatorCommand.parseCommand(userInput);

            switch (command) {
                case EXPRESSION:
                    tryEvaluateExpression(userInput);
                    break;
                case ASSIGNMENT:
                    tryAssignment(userInput);
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

    private static void tryAssignment(String userInput) {
        if (isAssignmentSyntaxValid(userInput)) {
            var assignmentTokens = userInput.split(Regex.ASSIGNMENT_OPERATOR);
            var identifier1 = assignmentTokens[0];
            var operand = assignmentTokens[1];
            try {
                VARIABLES.assign(identifier1, operand);
            }catch (RuntimeException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            System.out.println("Invalid assignment");
        }
    }

    private static boolean isAssignmentSyntaxValid(String userInput) {
        return userInput.matches(Regex.ASSIGNMENT_EXPRESSION);
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
            System.out.println(evaluateExpression(expression));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static int evaluateExpression(String expression) {
        var operands = new ArrayDeque<Integer>();
        var tokens = expression.split("\\s+");
        boolean neg = false;
        for (var token : tokens) {
            if (token.matches(Regex.INTEGER_VALUE)) {
                var operand = parseOperand(token);
                if (neg) {
                    operand = Math.negateExact(operand);
                    neg = false;
                }
                operands.add(operand);
            } else if (token.matches(Regex.NEG_OPERATOR)){
                neg = token.length() % 2 == 1;
            } else if (token.matches(Regex.PLUS_OPERATOR)) {
                neg = false;
            } else if (token.matches(Regex.IDENTIFIER)) {
                boolean finalNeg = neg;
                Optional.ofNullable(VARIABLES.getValue(token))
                        .map(value -> finalNeg ? -value : value)
                        .ifPresentOrElse(operands::add
                        , () -> {throw new RuntimeException("Unknown Variable");});
            } else {
                throw new RuntimeException("Invalid Expression");
            }
        }
        return operands.stream().mapToInt(Integer::intValue).sum();
    }

    private static int parseOperand(String operand ){
        var integer = operand.startsWith("+") ? operand.substring(1) : operand;
        return Integer.parseInt(integer);
    }
}
