package calculator;

import java.util.HashSet;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            var line = scanner.nextLine();
            exit = line.equalsIgnoreCase("/exit");
            if (exit) {
                System.out.println("Bye!");
            } else if (line.equalsIgnoreCase("/help")){
                System.out.println("The program calculates the sum and subtraction of n numbers in a single line");
            } else if (!line.isEmpty()){
                var operands = new HashSet<Integer>();
                var tokens = line.split("\\s+");
                boolean neg = false;
                for (var token : tokens) {
                    if (token.matches("-?\\d+")) {
                        var operand = Integer.parseInt(token);
                        if (neg) {
                            operand = Math.negateExact(operand);
                            neg = false;
                        }
                        operands.add(operand);
                    } else if (token.matches("-+")){
                        neg = token.length() % 2 == 1;
                    }
                }
                System.out.println(operands.stream().mapToInt(Integer::intValue).sum());
            }
        }
    }
}
