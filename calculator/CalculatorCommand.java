package calculator;

public enum CalculatorCommand {
    EXPRESSION,
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
                result = (userInput.matches("/\\w+")) ? UNKNOWN : EXPRESSION;
        }
        return result;
    }
}
