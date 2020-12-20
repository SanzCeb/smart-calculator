package calculator;

import java.util.Arrays;
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
                System.out.println("The program calculates the sum of numbers");
            } else if (!line.isEmpty()){
                var operands = Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt);
                System.out.println(operands.sum());
            }
        }
    }
}
