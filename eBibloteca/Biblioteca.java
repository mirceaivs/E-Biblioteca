import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Biblioteca implements ActionListener {
   private ArrayList<Carte> carti=new ArrayList<>();
   //private ArrayList<Imprumut> imprumuturi=new ArrayList<>();
   private JButton b1,b2;
   private JFrame f=new JFrame("Biblioteca");


   Biblioteca(){
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setBounds(960,540,300,100);

       b1=new JButton("Imprumut");
       b2=new JButton("Inapoiere");
      b1.setBounds(50,5,95,30);
      b2.setBounds(150,5,95,30);
      f.add(b1);
      f.add(b2);
      f.setResizable(false);

      f.setLayout(null);
      f.setVisible(true);
      this.b1.addActionListener(this);
      this.b2.addActionListener(this);
   }







   @Override
   public void actionPerformed(ActionEvent e){

      if(e.getSource()==b1){
         f.dispose();
         new ImprumutFrame1();

      } else {
         f.dispose();
         new Inapoiere();
      }



   }







}
