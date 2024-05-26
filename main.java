import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
//import javax.swing.JtextArea;


public class main{

        public static void main(String[] args) throws IOException {

                Carte c=new Carte();
                c.citesteCarti("eBibloteca/carti.txt");
                File file = new File("eBibloteca/imprumuturi.txt");


                if (file.length() != 0) {
                    Imprumut imp=new Imprumut();
                    imp.citesteImprumuturi();
                }
                
                new Biblioteca();


        }



        }



