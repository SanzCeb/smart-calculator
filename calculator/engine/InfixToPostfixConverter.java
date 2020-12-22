package calculator.engine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Scanner;


public class InfixToPostfixConverter {
    private final Scanner expressionScanner;
    private final Deque<String> operatorsStack;

    public InfixToPostfixConverter(String infixExpression) {
        expressionScanner = new Scanner(infixExpression);
        operatorsStack = new ArrayDeque<>();
    }

    public Queue<String> convertToPostfix() {
        var result = new ArrayDeque<String>();

        while (expressionScanner.hasNext()) {
            if (isAnOperandComing()) {
                result.offer(readOperand());
            } else if (isAnOperatorComing()){

                if (operatorsStack.isEmpty() || isALeftParenthesisAtTheTop()) {
                    operatorsStack.push(readOperator());
                } else if (isALowPriorityOperatorComing()) {

                    exportOperators(result);
                    operatorsStack.push(readOperator());

                } else {

                    while (!operatorsStack.isEmpty() && isAHighPriorityOperatorAtTheTop()) {
                        result.offer(operatorsStack.pop());
                    }
                    operatorsStack.push(readOperator());

                }


            } else if (isALeftParenthesisComing()){
                operatorsStack.push(readLeftParenthesis());
            } else if (isARightParenthesisComing()){

                discardRightParenthesis();
                exportOperators(result);

                if (operatorsStack.isEmpty()) {
                    throw new RuntimeException("Invalid expression");
                }

                discardLeftParenthesis();
            } else {
                throw new RuntimeException("Invalid expression");
            }
        }

        exportOperators(result);
        if (isALeftParenthesisAtTheTop()) {
            throw new RuntimeException("Invalid expression");
        }

        return result;
    }

    private boolean isAnOperandComing() {
        return expressionScanner.hasNext(Regex.OPERAND) || nextSymbolMatches(Regex.OPERAND);
    }

    private String readOperand() {
        return expressionScanner.findInLine(Regex.OPERAND);
    }

    private boolean isAnOperatorComing() {
        return expressionScanner.hasNext(Regex.OPERATOR) || nextSymbolMatches(Regex.OPERATOR);
    }

    private boolean isALowPriorityOperatorComing() {
        return expressionScanner.hasNext(Regex.LOW_PRIORITY_OPERATOR);
    }

    private void exportOperators(ArrayDeque<String> result) {
        while (!operatorsStack.isEmpty() && !isALeftParenthesisAtTheTop()) {
            result.offer(operatorsStack.pop());
        }
    }

    private boolean isAHighPriorityOperatorAtTheTop() {
        if (operatorsStack.isEmpty()) {
            return false;
        }

        return operatorsStack.peek().matches(Regex.HIGH_PRIORITY_OPERATOR);
    }

    private String readOperator() {
        var operator = expressionScanner.findInLine(Regex.OPERATOR);
        if (operator.matches(Regex.NEG_OPERATOR)){
            operator = (operator.length() & 1) == 0 ? "+" : "-";
        } else if (operator.matches(Regex.PLUS_OPERATOR)){
                operator = operator.substring(0, 1);
        } else if (operator.length() > 1){
            throw new RuntimeException("Invalid expression");
        }
        return operator;
    }

    private boolean isALeftParenthesisComing() {
        return nextSymbolMatches(Regex.LEFT_PARENTHESIS);
    }

    private boolean isARightParenthesisComing() {
        return nextSymbolMatches(Regex.RIGHT_PARENTHESIS);
    }

    private void discardRightParenthesis() {
        expressionScanner.findInLine(Regex.RIGHT_PARENTHESIS);
    }

    private void discardLeftParenthesis() {
       if (isALeftParenthesisAtTheTop()){
           operatorsStack.pop();
       }
    }

    private boolean isALeftParenthesisAtTheTop() {
        if (operatorsStack.isEmpty()) {
            return false;
        }
        return operatorsStack.peek().matches(Regex.LEFT_PARENTHESIS);
    }

    private String readLeftParenthesis() {
        return expressionScanner.findInLine(Regex.LEFT_PARENTHESIS);
    }

    private boolean nextSymbolMatches(String regex) {
        var delimiter = expressionScanner.delimiter();
        expressionScanner.useDelimiter("\\s*");
        var hasNext = expressionScanner.hasNext(regex);
        expressionScanner.useDelimiter(delimiter);
        return hasNext;
    }

}
