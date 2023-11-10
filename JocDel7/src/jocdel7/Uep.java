package jocdel7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Joan Martorell Coll
 * @author Pere Antoni Prats Villalonga
 * 
 */

public class Uep extends JDialog {
    
    //Mètode finestra final que mostra el guanyador
    public static void Uep(int jug) {
        
        JFrame frame = new JFrame("Uep !");
        frame.setLayout(new BorderLayout());
        
        //Imatge
        JLabel imatge = new JLabel(new ImageIcon("Cartes/Jug" + jug + "Riu.png"));
        
        //Text
        JLabel text;
        if(jug == 0) {
            text = new JLabel("HAS GUANYAT!!!!");
        } else {
            text = new JLabel("EL JUGADOR " + jug + " HA GUANYAT!!!!");
        }
        
        //---- PANEL -------
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(imatge);
        panel.add(text);
        
        //Boto
        JButton boto = new JButton("OK");
        boto.setBackground(new Color(0, 132, 254));
                
        frame.add(panel, BorderLayout.CENTER);
        frame.add(boto, BorderLayout.SOUTH);
        
        frame.setSize(new Dimension(400, 200));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        
        
        boto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                JocDel7.setInfo("Simulació acabada");
                JocDel7.botoReinicia.setBackground(new Color(0, 132, 254));
            }
        });
    }
}
