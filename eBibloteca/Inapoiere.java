import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Inapoiere extends JFrame{
    JFrame frame; // am declarat aici frame-ul pt ca nu merge sa dau super.dispose in clasa Ascultator

    JTextField tf;
    JLabel label;
    JButton b,b2;
    Inapoiere() {
        frame=new JFrame("Inapoiere");
        frame.setBounds(960,540,280,110);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        tf=new JTextField();
        tf.setBounds(110,5,150,20);



        label=new JLabel("Introduceti CNP:");
        label.setBounds(5,5,100,20);


        Ascultator e=new Ascultator();
        b=new JButton("Cauta");
        b.setBounds(130,40,70,25);
        b.addActionListener(e);
        b2=new JButton("Inapoi");
        b2.addActionListener(e);
        b2.setBounds(50,40,70,25);


        frame.add(tf);
        frame.add(label);
        frame.add(b);
        frame.add(b2);

    }


    class Ascultator implements ActionListener{//clasa interna
        @Override
        public void actionPerformed(ActionEvent e){
            Imprumut imp=new Imprumut();
            Imprumut imp2=imp.cautaImprumut(tf.getText());
            if(e.getSource()==b){
                if(tf.getText().length()!=13){
                    JOptionPane.showMessageDialog(frame, "Introduceti un CNP de 13 caractere");

                }else if(imp2==null ){
                    JOptionPane.showMessageDialog(frame, "Introduceti un CNP valid/care are imprumut efectuat");
                    frame.dispose();
                    new Inapoiere();

                }else {
                    new ImprumutFrame2(imp2);
                    frame.dispose();
                }





//            3

            }

            if(e.getSource()==b2) {


                frame.dispose();
                new Biblioteca();
            }




            }

        }
    }



