package interfaces;

import clients.CorefClient;
import clients.OpenIEClient;
import data_structures.Triple;

import java.util.Collection;
import java.util.Scanner;

public class OpenIEInterface {

    private static OpenIEClient openIEClient = new OpenIEClient();

    public static Collection<Triple> getTriples(String sentence){
        return openIEClient.getTriples(sentence);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input;
        while(true){
            System.out.println("Please enter the next sentence: ");
            input = scanner.nextLine();
            if(input.equals("stop")) break;
            System.out.println(openIEClient.getTriples(input));
        }
        System.out.println("\n\nStopped!");
    }
}
