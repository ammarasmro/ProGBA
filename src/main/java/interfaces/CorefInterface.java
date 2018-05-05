package interfaces;

import clients.CorefClient;
import data_structures.Triple;

import java.util.Collection;

public class CorefInterface {

    private static CorefClient corefClient = new CorefClient();

    public static Collection<Triple> getTriplesOf(String sentence){
        return corefClient.getTriples(sentence);
    }


    public static void main(String[] args) {
        System.out.println(getTriplesOf("Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle."));
    }
}
