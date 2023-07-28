
import java.util.ArrayList;

public class Persoana {
    private  String numeSiPrenume;
    private  String CNP;
    private int nrCartiImprumutate;
    private static ArrayList<Persoana>  persoane=new ArrayList<>();

    public Persoana(String numeSiPrenume, String CNP) {
        this.numeSiPrenume=numeSiPrenume;
        this.CNP = CNP;
        this.nrCartiImprumutate=1;

    }


    @Override
    public String toString() {
        return  numeSiPrenume + "_" + CNP + "_" + nrCartiImprumutate;
    }

    public void adaugarePersoana(){
        persoane.add(this);
    }



    public String getNumeSiPrenume() {
        return numeSiPrenume;
    }


    public String getCNP() {
        return CNP;
    }





}
