package data_structures;

public class DrQAResponseDocument {
    private String context;
    private String doc_id;
    private double doc_score;
    private int result_number;
    private String span;
    private double span_score;

    DrQAResponseDocument(){

    }

    @Override
    public String toString(){
        return context;
    }
//    {
//        "context": "This most commonly refers to automobiles but also refers to motorcycles, trucks, buses, coaches, and vans. The functional design and development of a modern motor vehicle is typically done by a large team from many different disciplines included within automotive engineering. Automotive design in this context is primarily concerned with developing the visual appearance or aesthetics of the vehicle, though it is also involved in the creation of the product concept. Automotive design is practiced by designers who usually have an art background and a degree in industrial design or transportation design.",
//            "doc_id": "Automotive design",
//            "doc_score": 233.30252356644363,
//            "result_number": 0,
//            "span": "industrial design or transportation design",
//            "span_score": 73.4535140991211
//    }
}
