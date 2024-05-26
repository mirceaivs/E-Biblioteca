import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;


public class ImprumutFrame2 extends JFrame {
    JFrame frame;
    JLabel l1,l2,l3,l4,l5;
    JTextField tf1,tf2,tf3;
    JTextArea ta1;
    JButton b;
    JComboBox cb;
    JScrollPane scroll;
    long days;

    Imprumut imp;
    ImprumutFrame2(Imprumut imprumut){
        frame=new JFrame("Returnare imprumut");
        //frame.setSize(420,240);
        frame.setBounds(960,540,550,240);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);


        l1=new JLabel("Nume si prenume:");
        l1.setBounds(5,5,150,20);
        l2=new JLabel("CNP:");
        l2.setBounds(5,45,100,20);
        l3=new JLabel("Carti imprumutate:");
        l3.setBounds(5,85,150,20);
        l4=new JLabel("Ramas de plata:");
        l4.setBounds(5,140,100,20);
        l5=new JLabel("Cate carti inapoiati?");
        l5.setBounds(330,70,150,20);


        String [] nrCarti=new String[imprumut.getListaCartiImprumutate().size()+1];
        nrCarti[0]=String.valueOf(0);
        for(int i=1;i<imprumut.getListaCartiImprumutate().size();i++){
            nrCarti[i]=String.valueOf(i);
        }
        nrCarti[imprumut.getListaCartiImprumutate().size()]=String.valueOf(imprumut.getListaCartiImprumutate().size());
        cb=new JComboBox(nrCarti);
        cb.setBounds(330,100,150,20);


        tf1=new JTextField(imprumut.getPersoana().getNumeSiPrenume());//trebuie sa ia Numele si Prenumele din fisier
       // tf1=new JTextField();
        tf1.setBounds(170,5,150,20);
        tf2=new JTextField(imprumut.getPersoana().getCNP());
        //tf2=new JTextField();
        tf2.setBounds(170,45,150,20);
        tf3=new JTextField();


        Date data=new Date();

        SimpleDateFormat dtf1=new SimpleDateFormat("dd.MM.yyyy");
        String dataString=dtf1.format(data);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date1 = LocalDate.parse(dataString,dtf);
        long daysBetween = ChronoUnit.DAYS.between(date1, imprumut.getDataReturnare());
        days=daysBetween;



        //trebuie sa ia Suma dintr-un fisier sau sa o calculeze
        tf3.setBounds(170,140,100,20);
        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);

        String fasole="";
        for(int i=0;i<imprumut.getListaCartiImprumutate().size();i++)
                fasole=imprumut.getListaCartiImprumutate().get(i).toString()+"\n"+fasole;
                
        
       
        
        ta1=new JTextArea(fasole); // trebuie sa ia titlul cartilor imprumutate de undeva...

        ta1.setEditable(false);
        
        ta1.setBounds(170,70,150,60);
        scroll = new JScrollPane(ta1);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        ta1.setLineWrap(true);

        scroll.setBounds(170,70,150,60);


        Ascultator e=new Ascultator();
        b=new JButton("OK");
        b.setBounds(150,170,60,20);
        b.addActionListener(e);
        cb.addActionListener(e);

         
       



        frame.add(l1);
        frame.add(l2);
        frame.add(l3);
        frame.add(l4);
        frame.add(b);
        frame.add(tf1);
        frame.add(tf2);
        frame.add(tf3);
        frame.add(scroll);
        frame.add(l5);
        frame.add(cb);
        
        imp=imprumut;

    }
    public void removeImprumut() {
        Iterator<Imprumut> it = imp.getImprumuturi().iterator();
        while (it.hasNext()) {
            if (it.next().equals(imp)) {
                it.remove();
            }
        }
    }

    class Ascultator implements ActionListener{//clasa interna
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==cb){
                if(String.valueOf(cb.getSelectedIndex()).equals("0")){
                    tf3.setText(String.valueOf(imp.getListaCartiImprumutate().size()*50)+"RON");
                }else{


                if(days<=14){

                    tf3.setText(((imp.getListaCartiImprumutate().size()*0.5)*days)+(((imp.getListaCartiImprumutate().size()-(cb.getSelectedIndex()))*50))+" RON");  //amenda 50 lei daca pierde vreo carte
                }else{
                    tf3.setText((imp.getListaCartiImprumutate().size()*7)+(((imp.getListaCartiImprumutate().size()-(cb.getSelectedIndex()))*50))+(days-14)*(imp.getListaCartiImprumutate().size()*2)+" RON");
                }
            }}


            if(e.getSource()==b){


                
                

                try (FileWriter f = new FileWriter("eBibloteca/arhiva.txt", true);
                BufferedWriter b = new BufferedWriter(f);
                PrintWriter p = new PrintWriter(b);) {

                p.println(imp.toString());
                

                } 
                catch (IOException i) {
                    i.printStackTrace();
                }
                removeImprumut();
                try (FileWriter f = new FileWriter("imprumuturi.txt", false);
                     BufferedWriter b = new BufferedWriter(f);
                     PrintWriter p = new PrintWriter(b);) {

                    for(int i=0;i<imp.getImprumuturi().size();i++){
                        p.println(imp.getImprumuturi().get(i).toString());
                    }



                }
                catch (IOException i) {
                    i.printStackTrace();
                }
                
                Carte ca=new Carte();

                ca.updateCartiStatic(imp.getListaCartiImprumutate(),cb.getSelectedIndex());
                ca.updateCarti();
                frame.dispose();
                new Biblioteca();
            }


         }
     }

}
