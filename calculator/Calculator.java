package calculator;

import calculator.engine.*;

import java.math.BigInteger;
import java.util.*;

public class Calculator {
    private static final Variables VARIABLES = new Variables();

    public static void run(Scanner scanner){
        CalculatorCommand command;
        do {
            var userInput = scanner.nextLine().trim();
            command = CalculatorCommand.parseCommand(userInput);
            runCommand(command, userInput);
        } while (command != CalculatorCommand.EXIT);
    }

    private static void runCommand(CalculatorCommand command, String userInput) {
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

    private static BigInteger evaluateExpression(String expression) {
        Queue<String> postFix = new InfixToPostfixConverter(expression).convertToPostfix();
        var stack = new ArrayDeque<BigInteger>();

        for (var item : postFix) {
            if (item.matches(Regex.INTEGER_VALUE)) {
                stack.push(new BigInteger(item));
            } else if (item.matches(Regex.IDENTIFIER)) {
                Optional.ofNullable(VARIABLES.getValue(item))
                        .ifPresentOrElse(stack::push, () -> {throw new RuntimeException("Unknown variable");});
            } else if (item.matches(Regex.OPERATOR)) {
                var operand2 = stack.pop();
                var operand1 = stack.pop();
                BigInteger result = runOperation(item, operand1, operand2);
                stack.push(result);
            }
        }

        return Optional.ofNullable(stack.peek())
                .orElseThrow(() -> {throw new RuntimeException("Invalid Expression");});
    }

    private static BigInteger runOperation(String item, BigInteger operand1, BigInteger operand2) {
        BigInteger result;
        switch (item) {
            case "*":
                result = operand1.multiply(operand2);
            break;
            case "+":
                result = operand1.add(operand2);
            break;
            case "-":
                result = operand1.subtract(operand2);
            break;
            case "/":
                result = operand1.divide(operand2);
                break;
            default:
                throw  new RuntimeException("Invalid expression");
        }

        return result;
    }

}
