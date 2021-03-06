package calculator.engine;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Variables {
    private final HashMap<String, BigInteger> variables = new HashMap<>();

    public void assign(String identifier1, String operand) {

        if (!identifier1.matches(Regex.IDENTIFIER)){
            throw new RuntimeException("Invalid Identifier");
        } else if (operand.matches(Regex.INTEGER_VALUE)) {
            variables.put(identifier1, new BigInteger(operand));
        } else if (operand.matches(Regex.IDENTIFIER)) {
            var intValue = variables.get(operand);
            if (Objects.isNull(intValue)) {
                throw new RuntimeException("Unknown variable");
            } else {
                variables.put(identifier1, intValue);
            }
        } else {
            throw new RuntimeException("Invalid Assignment");
        }
    }

    public BigInteger getValue(String token) {
        return variables.get(token);
    }
}
