package interfaces;

import clients.RemoteCorefClient;
import clients.RemoteOpenIEClient;
import data_structures.Triple;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CoreNLPInterface {

    private static RemoteOpenIEClient openIEClient = new RemoteOpenIEClient();
    private static RemoteCorefClient corefClient = new RemoteCorefClient();

    public static List<Triple> getOpenIETriplesOf(String sentence){
        System.out.println(sentence);
        return openIEClient.getTriples(sentence);
    }

    public static List<Triple> getCorefTriplesOf(String sentence){
        System.out.println(sentence);
        return corefClient.getTriples(sentence);
    }

    public static boolean getStatusOfOpenIE(){
        return openIEClient.getStatus();
    }

    public static boolean getStatusOfCoref(){
        return corefClient.getStatus();
    }


    public static void main(String[] args) {
        System.out.println(getOpenIETriplesOf("Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle."));
//        System.out.println(getCorefTriplesOf("Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle."));

        String testString = "Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "These suspensions are placed often in a lowrider, a vehicle modified so that its ground clearance is less than its original design, to give extra leverage when encountering harsh road conditions.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "These modifications done to the automobile can enable the body and wheels of the car to be electronically lifted off the ground, while being remote controlled.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "Automotive suspension design is an aspect of automotive engineering, concerned with designing the suspension for cars and trucks.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "Suspension design for other vehicles is similar, though the process may not be as well established.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "Designing the structure of each component so that it is strong, stiff, light, and cheap Analysing the vehicle dynamics of the resulting design";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "Options included internal combustion engines fueled by petrol, diesel, propane, or natural gas; hybrid vehicles, plug-in hybrids, fuel cell vehicles fueled by hydrogen and all electric cars.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "internal combustion engines fueled by petrol, diesel, propane, or natural gas; hybrid vehicles, plug-in hybrids, fuel cell vehicles fueled by hydrogen and all electric cars.";
        System.out.println(getOpenIETriplesOf(testString));
        testString = "Fueled vehicles seemed to have the short term advantage due to the limited range and high cost of batteries.";
        System.out.println(getOpenIETriplesOf(testString));

        Scanner scanner = new Scanner(System.in);

        String input;
        while(true){
            System.out.println("Please enter the next sentence: ");
            input = scanner.nextLine();
            if(input.equals("stop")) break;
            System.out.println(getOpenIETriplesOf(input));
        }
        System.out.println("\n\nStopped OpenIE!");
        while(true){
            System.out.println("Please enter the next sentence. Or, enter stop to quit: ");
            input = scanner.nextLine();
            if(input.equals("stop")) break;
            System.out.println(getCorefTriplesOf(input));
        }
        System.out.println("\n\nStopped Coref!");
    }
}
