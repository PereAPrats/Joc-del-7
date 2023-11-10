package jocdel7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Joan Martorell Coll
 * @author Pere Antoni Prats Villalonga
 * 
 */

public class JocDel7 extends JFrame implements MouseListener {
    
    //DECLARACIONS
    private JPanel panelCartesRestantsJugadors;
    public static JPanel panelJoc;
    private JPanel panelAccions;
    public static Tauler panelTauler;
    public static Jugador panelUsuari;
    public static Jugador [] jugador;
    public static JPanel panelBotons;
    public static JButton botoMescla;
    public static JButton botoJuga;
    public static JButton botoPassa;
    public static JButton botoTornJugador;
    public static JButton botoReinicia;
    private static JLabel labelInfo;
    
    public static boolean autoritzat = false;      //Autoritza el MouseListener
    
    //Constructor
    public JocDel7() {
        setSize(1000, 850);
        setTitle("Joc del 7");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JocDel7.EXIT_ON_CLOSE);
        initComponents();
    }
    
    //Inicialitzam components
    private void initComponents() {
        //------- PANEL DE JOC -------------------------------------------------
        //------  CARTES JUGADORS PANEL --------
        panelCartesRestantsJugadors = new JPanel();
        panelCartesRestantsJugadors.setBackground(new Color(0,102,0));
        panelCartesRestantsJugadors.setPreferredSize(new Dimension(800,120));
        panelCartesRestantsJugadors.setLayout(new GridLayout(1, 3, 5, 5));
        panelCartesRestantsJugadors.setBorder(BorderFactory.createEmptyBorder(0, 100, 3, 100));
        
        jugador = new Jugador[3];
        for(int i = 0; i < jugador.length; i++) {
            //Cream nou jugador IA
            jugador[i] = new Jugador("jugador");
            panelCartesRestantsJugadors.add(jugador[i]);
        }
                
        //------ TAULER DE JOC ----------
        panelTauler = new Tauler();
        panelTauler.setBackground(new Color(0,102,0));
        
        //------ PANEL DE USUARI ---------        
        panelUsuari = new Jugador("usuari");
        permitirClicar();
        
        //------ PANEL DE JOC --------- 
        panelJoc = new JPanel();
        panelJoc.setLayout(new BorderLayout());
        panelJoc.add(panelCartesRestantsJugadors, BorderLayout.NORTH);
        panelJoc.add(panelTauler, BorderLayout.CENTER);
        panelJoc.add(panelUsuari, BorderLayout.SOUTH);
        
        //--------- PANEL ACCIONS ------------------------------------------------
        //Boto Mescla
        botoMescla = new JButton("Mescla");
        botoMescla.setBackground(new Color(0, 132, 254));
        botoMescla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Mesclam
                panelTauler.mescla();
                setInfo("La baralla està mesclada.");
                
                //Modificam els botons
                botoMescla.setBackground(new Color(219, 230, 242));
                botoJuga.setEnabled(true);
                botoJuga.setBackground(new Color(0, 132, 254));
                botoReinicia.setEnabled(true);
            }
        });
        
        //Boto Juga
        botoJuga = new JButton("Juga");
        botoJuga.setBackground(new Color(219, 230, 242));
        botoJuga.setEnabled(false);
        botoJuga.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Jugam
                panelTauler.jugar();
            }
        });
        
        //Boto Passa
        botoPassa = new JButton("Passa");
        botoPassa.setBackground(new Color(0, 132, 254));
        botoPassa.setVisible(false);
        botoPassa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Acció de passar
                setInfo("Has passat");
                panelTauler.proximTorn();
            }
        });
        
        //Boto Torn jugador
        botoTornJugador = new JButton("Torn jugador");
        botoTornJugador.setBackground(new Color(0, 132, 254));
        botoTornJugador.setVisible(false);
        botoTornJugador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Mou bot
                panelTauler.mouBot(panelTauler.getTorn());
                //Movem al següent jugador
                panelTauler.addTorn();
                //Pròxim torn
                panelTauler.proximTorn();
            }
        });
        
        //Boto Reinicia
        botoReinicia = new JButton("Reinicia");
        botoReinicia.setBackground(new Color(219, 230, 242));
        botoReinicia.setEnabled(false);
        botoReinicia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Reiniciam tot
                panelJoc.remove(panelTauler); //llevam tauler
                panelTauler = new Tauler(); //posam nou tauler
                panelJoc.add(panelTauler, BorderLayout.CENTER);
                
                //Reiniciam jugadors (borram i tornam a declarar)
                for(int i = 0; i < jugador.length; i++) {
                    panelCartesRestantsJugadors.remove(jugador[i]);
                    jugador[i] = new Jugador("jugador");
                    panelCartesRestantsJugadors.add(jugador[i]);
                }
                
                panelJoc.remove(panelUsuari); //llevam panel d'usuari
                panelUsuari = new Jugador("usuari"); //posam nou usuari
                panelJoc.add(panelUsuari, BorderLayout.SOUTH);
            
                permitirClicar();
                                
                setInfo("Abans de jugar cal mesclar la baralla");

                revalidate();
                repaint();
                
                autoritzat = false;
                
                //Reiniciam botons
                botoPassa.setVisible(false);
                botoPassa.setEnabled(true);
                botoPassa.setBackground(new Color(0, 132, 254));
                botoTornJugador.setVisible(false);
                botoTornJugador.setEnabled(true);
                botoTornJugador.setBackground(new Color(0, 132, 254));
                botoMescla.setVisible(true);
                botoMescla.setBackground(new Color(0, 132, 254));
                botoReinicia.setEnabled(false);
                botoJuga.setVisible(true);
                botoJuga.setEnabled(false);
                botoJuga.setBackground(new Color(219, 230, 242));
            }
        });
        
        //Panel botons
        panelBotons = new JPanel();
        panelBotons.add(botoMescla);
        panelBotons.add(botoJuga);
        panelBotons.add(botoPassa);
        panelBotons.add(botoTornJugador);
        panelBotons.add(botoReinicia);
        
        //Label Informació
        labelInfo = new JLabel("Abans de jugar cal mesclar la baralla");
        labelInfo.setBackground(Color.white);
        labelInfo.setOpaque(true);
        labelInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
        panelAccions = new JPanel();
        panelAccions.setPreferredSize(new Dimension(800,60));
        panelAccions.setLayout(new BorderLayout());
        panelAccions.add(panelBotons, BorderLayout.CENTER);
        panelAccions.add(labelInfo, BorderLayout.SOUTH);
        
        //-------- CONTENT PANE ----------------------------------------------------
        getContentPane().setLayout(new BorderLayout());
        
        getContentPane().add(panelJoc, BorderLayout.CENTER);
        getContentPane().add(panelAccions, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        JocDel7 j = new JocDel7();
        j.setVisible(true);
    }
    
    //Mètode per modificar el text de la informació
    public static void setInfo(String text) {
        labelInfo.setText(text);
    }
    
    //Mètode per permitir clicar damunt cada carta de l'usuari
    public void permitirClicar() {
        for(int i = 0; i < panelUsuari.cartes.length; i++) {
            panelUsuari.cartes[i].addMouseListener(this);
        }
    }

    //MOUSE EVENTS
    @Override
    public void mouseClicked(MouseEvent event) {
        if(autoritzat) { //Si està autoritzat a clicar...
            int index = 0;
            //Detectam l'índex de la carta seleccionada
            for(int i = 0; i < 13; i++) {
                if(event.getSource() == panelUsuari.cartes[i]) {
                    index = i;
                }
            }
            
            //Carta seleccionada
            Carta carta = panelUsuari.cartes[index];
            
            if(panelTauler.cartaPosible(carta)) { //Si podem posar la carta...
                
                panelUsuari.decCartesRest();  //Restam una carta a l'usuari
                setInfo("Has posat el " + panelUsuari.cartes[index].getInfo());
                panelUsuari.cartes[index].removeMouseListener(this); //Ja no podem clicar damunt la carta
                
                if(panelUsuari.getCartesRest() != 0) { //Si no és la darrera carta...
                    //Pintam la carta al tauler
                    panelTauler.c[carta.getNumPalo()][carta.getNum()].pintar("Cartes/" + carta.getNum() + "_of_" + carta.getPalo() + ".png");
                    panelTauler.c[carta.getNumPalo()][carta.getNum()].ocultarCarta();

                    if(index == 0) { //Si és la primera de la barralla de l'usuari...
                        panelUsuari.cartes[index].ocultarCarta(); //Només l'ocultam
                    } else {
                        panelUsuari.cartes[index].llevarCarta(); //Sinó la llevam
                    }
                    
                } else { //Si és la darrera carta...
                    Uep.Uep(0); //Mostram nova pestanya
                    
                    //Modificam els botons
                    botoPassa.setEnabled(false);
                    botoPassa.setBackground(new Color(219, 230, 242));
                    botoTornJugador.setEnabled(false);
                    botoTornJugador.setBackground(new Color(219, 230, 242));
                }
                
                //Pròxim torn
                panelTauler.proximTorn();            
            } else { //Si no es pot posar...
                Toolkit.getDefaultToolkit().beep(); //Fa beep
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
