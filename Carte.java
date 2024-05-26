import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static jogamp.common.os.elf.SectionArmAttributes.Tag.File;

public class Carte {
    private String codISBN;
    private String titlu;
    private String denumireEditura;
    private boolean esteImprumutata;
    private static ArrayList<Carte> carti=new ArrayList<>();

    public Carte(String codISBN, String titlu, String denumireEditura, boolean esteImprumutata) {
        this.codISBN = codISBN;
        this.titlu = titlu;
        this.denumireEditura = denumireEditura;
        this.esteImprumutata = esteImprumutata;
    }

    public Carte() {
    }


    public void citesteCarti(String fileName) {
        try (BufferedReader buf = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = buf.readLine()) != null) {
                String[] info = line.split("_");
                if (info.length >= 4) {
                    boolean imprumut = Boolean.parseBoolean(info[3]);
                    Carte carte = new Carte(info[0], info[1], info[2], imprumut);
                    carti.add(carte);
                } else {
                    System.out.println("Linie invalidă: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }
    }

    public void updateCartiStatic(ArrayList<Carte> books,int n)
    {
        for(int i=0;i<n;i++)
        {
            Carte bucc=books.get(i).cautaCarte(books.get(i).getTitlu());
            bucc.setEsteImprumutata(false);
        }
    }



    @Override
    public String toString() {
        return  titlu + "_" + denumireEditura ;
    }
    
    

    public String getTitlu() {
        return titlu;
    }
    
    public String getCodISBN() {
        return codISBN;
    }

    public boolean isEsteImprumutata() {
        return esteImprumutata;
    }


    public void setEsteImprumutata(boolean esteImprumutata) {
        this.esteImprumutata = esteImprumutata;
    }


    public Carte cautaCarte(String titlu){
        for(int i=0;i<carti.size();i++){
            if(carti.get(i).getTitlu().equals(titlu)){
                return carti.get(i);
            }
        }
        return null; //cam imposibil
    }




    public String[] titluriCarti(){
        String[] titluri=new String[carti.size()];

        for(int i=0;i<carti.size();i++){
                titluri[i]=(carti.get(i).getTitlu());
        }
        return titluri;
    }

    public static ArrayList<Carte> getCarti() {
        return carti;
    }
    
    
    
    

    public String getDenumireEditura() {
        return denumireEditura;
    }

    

public void updateCarti()
    {
         try (FileWriter f = new FileWriter("carti.txt", false);
                         BufferedWriter b = new BufferedWriter(f);
                         PrintWriter h = new PrintWriter(b);) {

                        Carte c=new Carte();
                        
                        for(int i=0;i<c.getCarti().size();i++)
                        {
                            
                           h.println(c.getCarti().get(i).getCodISBN()+"_"+c.getCarti().get(i).getTitlu()+"_"+c.getCarti().get(i).getDenumireEditura()+"_"+c.getCarti().get(i).isEsteImprumutata());
                            
                        }
                        


                    }
                    catch (IOException i) {
                        i.printStackTrace();
                    }
    }




}
