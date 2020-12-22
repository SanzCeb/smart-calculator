package calculator.engine;

public class Regex {
    public static final String IDENTIFIER = "[a-zA-Z]+";
    public static final String ASSIGNMENT_OPERATOR = "\\s*=\\s*";
    public static final String COMMAND = "/\\w+";
    public static final String ASSIGNMENT_EXPRESSION = String.format("\\w+%s[-+]?\\w+", ASSIGNMENT_OPERATOR);
    public static final String NEG_OPERATOR = "-+";
    public static final String PLUS_OPERATOR = "\\++";
    public static final String INTEGER_VALUE = "[-+]?\\d+";
    public static final String OPERATOR = "[-+*/]+";
    public static final String OPERAND = String.format("(%s|%s)", Regex.INTEGER_VALUE, Regex.IDENTIFIER);
    public static final String LOW_PRIORITY_OPERATOR = String.format("(%s|%s)", NEG_OPERATOR, PLUS_OPERATOR);
    public static final String HIGH_PRIORITY_OPERATOR = "[*/]";
    public static final String LEFT_PARENTHESIS = "[(]";
    public static final String RIGHT_PARENTHESIS = "[)]";

}
