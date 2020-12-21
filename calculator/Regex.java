package calculator;

public class Regex {
    public static final String IDENTIFIER = "[a-zA-Z]+";
    public static final String ASSIGNMENT_OPERATOR = "\\s*=\\s*";
    public static final String COMMAND = "/\\w+";
    public static final String ASSIGNMENT_EXPRESSION = String.format("\\w+%s\\w+", ASSIGNMENT_OPERATOR);
    public static final String NEG_OPERATOR = "-+";
    public static final String PLUS_OPERATOR = "\\++";
    public static final String INTEGER_VALUE = "[-+]?\\d+";

}
