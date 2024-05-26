import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Imprumut {

    private Persoana persoana;
    private ArrayList <Carte>listaCartiImprumutate=new ArrayList<>();
    private LocalDate dataReturnare;
    private int nrCarti;
    private static ArrayList<Imprumut>  imprumuturi=new ArrayList<>();


    public Imprumut(Persoana persoana, ArrayList <Carte> listaCartiImprumutate, LocalDate dataReturnare,int nrCarti) {
        this.persoana = persoana;
        this.listaCartiImprumutate=new ArrayList<>(listaCartiImprumutate);
        this.dataReturnare = dataReturnare;
        this.nrCarti=nrCarti;
    }

    
    public Imprumut(String Nume,String CNP,LocalDate dataReturnare,int nrCarti, ArrayList <Carte> listaCartiImprumutate) 
    {
        Persoana p=new Persoana(Nume,CNP);
        this.listaCartiImprumutate=new ArrayList<>(listaCartiImprumutate);
        this.persoana = p;
        this.dataReturnare = dataReturnare;
        this.nrCarti=nrCarti;
    }

    public ArrayList<Carte> getListaCartiImprumutate() {
        return listaCartiImprumutate;
    }

    public Imprumut() {
    }

    public void adaugareImprumut(){

        imprumuturi.add(this);
    }

    public Imprumut cautaImprumut(String CNP){
        for(int i=0;i<imprumuturi.size();i++){
            if(imprumuturi.get(i).persoana.getCNP().equals(CNP)){
                return imprumuturi.get(i);
            }
        }
        return null; //cam imposibil
    }

    public boolean isValid(){
        return persoana != null;
    }

    //public Imprumut(String Nume,String CNP,LocalDate dataReturnare,int nrCarti, Carte
     public void citesteImprumuturi()throws FileNotFoundException, IOException
    {
        BufferedReader buf=new BufferedReader(new FileReader("imprumuturi.txt"));
        String line=null;
       
        while((line = buf.readLine()) != null)
        {
            
            String info[]=line.split("_");

            LocalDate datafrt= LocalDate.parse(info[2]);
            ArrayList<Carte> cartisoare=new ArrayList<>();
            for(int i=4;i<(4+Integer.valueOf(info[3])*3);i+=3)
            {
               
                Carte c=new Carte(info[i],info[i+1],info[i+2],true);
                cartisoare.add(c);
            }

            Imprumut imprumuts=new Imprumut(info[0], info[1], datafrt, Integer.valueOf(info[3]),cartisoare);
            imprumuturi.add(imprumuts);
            System.out.println(info);
        }
        
    }
     
    

    public Persoana getPersoana() {
        return persoana;
    }

    public int getNrCarti() 
            
    {
        return nrCarti;
    }

   

    public static ArrayList<Imprumut> getImprumuturi() {
        return imprumuturi;
    }

    @Override
    public String toString() 
    {
        return persoana+ "_" +listaCartiImprumutate+ "_" + dataReturnare +"\n";
    }




    public LocalDate getDataReturnare() {
        return dataReturnare;
    }
    

}
