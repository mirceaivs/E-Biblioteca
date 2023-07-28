import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ImprumutFrame1 extends JFrame {
    private JTextField tf1,tf2,tf3;
    private JFrame frame; // am declarat aici frame-ul pt ca nu merge sa dau super.dispose in clasa Ascultator
    private JLabel numeSiPrenume,carteDeIdentitate, alegeCarte, dataReturnare;
    private JComboBox cb;
    private JTextArea area;
    private JButton b1,b2;
    int nrCarti=0;




    ImprumutFrame1() {



        frame=new JFrame("Imprumut");
        frame.setBounds(960,540,450,370);
        frame.setResizable(true);
        frame.setLayout(null);
        frame.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //label-uri
        numeSiPrenume = new JLabel("Nume si Prenume:");
        numeSiPrenume.setBounds(5, 5, 120, 15);
        carteDeIdentitate = new JLabel("Carte de identitate: CNP");
        carteDeIdentitate.setBounds(5, 50, 150, 15);
        alegeCarte = new JLabel("Alege dintre cărți ");
        alegeCarte.setBounds(5, 150, 170, 15);
        dataReturnare = new JLabel("Data returnare:");
        dataReturnare.setBounds(5, 260, 150, 15);

        //text field-uri
        tf1 = new JTextField("Nume-Prenume");
        tf1.setBounds(150, 5, 270, 20);

        tf2 = new JTextField();
        tf2.setBounds(150, 50, 270, 20);
        tf3 = new JTextField("dd.MM.yyyy");
        tf3.setBounds(150, 260, 270, 20);


        Ascultator e=new Ascultator();
        //comboBox

        Carte buc=new Carte();
        String[] titluriCarti=buc.titluriCarti();

        cb = new JComboBox(titluriCarti);
        cb.addActionListener(e);
        cb.setBounds(150, 150, 100, 20);

        //textArea
        area = new JTextArea();
        area.setBounds(260, 125, 150, 75);
        area.setEditable(false);



        //buton

        b1 = new JButton("Imprumuta");
        b1.setBounds(190, 290, 120, 20);
        b1.addActionListener(e);
        b2=new JButton("Inapoi");
        b2.addActionListener(e);
        b2.setBounds(100,290,70,20);


        //adaugam componentele grafice
        frame.add(numeSiPrenume);
        frame.add(carteDeIdentitate);
        frame.add(alegeCarte);
        frame.add(dataReturnare);
        frame.add(cb);
        frame.add(area);
        frame.add(tf1);
        frame.add(tf2);
        frame.add(tf3);
        frame.add(b1);
        frame.add(b2);
    }



    class Ascultator implements ActionListener{//clasa interna
        ArrayList<Carte> listaCartiImprumutare=new ArrayList<>();
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==b2){
                frame.dispose();
                new Biblioteca();
            }
            


            Carte carte=new Carte();



            if(e.getSource()==cb){

                nrCarti++;
                if(nrCarti<=5){
                    String x = String.valueOf(cb.getSelectedItem());
                    carte=carte.cautaCarte(x);
                    if(carte.isEsteImprumutata()==false)
                    {
                        listaCartiImprumutare.add(carte);
                        carte.setEsteImprumutata(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "Cartea este deja imprumutata!!11!!!");
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(frame, "S-a atins numarul MAXIM de carti imprumutate!!1");
                }
                area.setText((carte.cautaCarte(cb.getSelectedItem().toString()).toString()));
                
            }




            if(e.getSource()==b1){
                boolean ok=true;

                if(tf2.getText().length()!=13){
                    JOptionPane.showMessageDialog(frame, "Introduceti un CNP de 13 caractere");
                    ok=false;
                }

                if(!tf3.getText().matches("\\d{2}.\\d{2}.\\d{4}")){
                    JOptionPane.showMessageDialog(frame, "Introduceti o data sub formatul indicat");
                    ok=false;

                }

                Persoana pers1= new Persoana(tf1.getText(),tf2.getText());
                pers1.adaugarePersoana();

                String data1String=tf3.getText();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate date1 = LocalDate.parse(data1String,dtf);

               
               

                if(nrCarti>0 && ok==true)
                {
                    Imprumut imp =new Imprumut(pers1,listaCartiImprumutare,date1,nrCarti);
                    imp.adaugareImprumut();
                    
                    Carte ca=new Carte();
                    ca.updateCarti();
                    
                    
                    frame.dispose();
                    new Biblioteca();
                    try (FileWriter f = new FileWriter("imprumuturi.txt", true);
                         BufferedWriter b = new BufferedWriter(f);
                         PrintWriter p = new PrintWriter(b);) {

                        
                        p.print(imp.getPersoana().getNumeSiPrenume()+"_"+imp.getPersoana().getCNP()+"_"+imp.getDataReturnare()+"_"+imp.getNrCarti()+"_");
                        for(int i=0;i<imp.getListaCartiImprumutate().size();i++)
                        {
                            p.print(imp.getListaCartiImprumutate().get(i).getCodISBN()+"_"+imp.getListaCartiImprumutate().get(i).getTitlu()+"_"+imp.getListaCartiImprumutate().get(i).getDenumireEditura()+"_");
                        }
                        p.println();


                    }
                    catch (IOException i) {
                        i.printStackTrace();
                    }
                }
                else if(nrCarti==0)
                     JOptionPane.showMessageDialog(frame, "Trebuie sa selectati cel putin o carte");
                


            }




        }


    }




}
