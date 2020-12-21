package calculator;

public enum CalculatorCommand {
    EXPRESSION,
    ASSIGNMENT,
    HELP,
    EXIT,
    EMPTY,
    UNKNOWN;

    public static CalculatorCommand parseCommand(String userInput) {
        CalculatorCommand result;
        switch (userInput.toLowerCase()) {
            case "/help":
                result = HELP;
                break;
            case "/exit":
                result = EXIT;
                break;
            case "":
                result = EMPTY;
                break;
            default:
                if (userInput.matches(Regex.COMMAND)) {
                    result = UNKNOWN;
                } else if (userInput.contains("=")){
                    result = ASSIGNMENT;
                } else {
                    result = EXPRESSION;
                }
        }
        return result;
    }
}
