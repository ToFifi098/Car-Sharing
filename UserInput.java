package carsharing;

import java.util.Scanner;

public class UserInput {
    static int listen(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextInt();
    }
}
