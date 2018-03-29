package interfaces;

import clients.RemoteCorefClient;
import clients.RemoteOpenIEClient;
import data_structures.Triple;

import java.util.List;
import java.util.Map;

public class CoreNLPInterface {

    private static RemoteOpenIEClient openIEClient = new RemoteOpenIEClient();
    private static RemoteCorefClient corefClient = new RemoteCorefClient();

    public static List<Triple> getOpenIETriplesOf(String sentence){
        return openIEClient.getTriples(sentence);
    }

    public static List<Triple> getCorefTriplesOf(String sentence){
        return corefClient.getTriples(sentence);
    }


    public static void main(String[] args) {
        System.out.println(getOpenIETriplesOf("Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle."));
        System.out.println(getCorefTriplesOf("Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle."));
    }
}
