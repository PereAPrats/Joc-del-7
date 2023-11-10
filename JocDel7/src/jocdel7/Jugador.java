package jocdel7;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 *
 * @author Joan Martorell Coll
 * @author Pere Antoni Prats Villalonga
 * 
 */

public class Jugador extends JPanel {
    //Declaracions
    private String nom;
    private int cartesRest;
    public Carta[] cartes;
    private int pos;
    
    public JLabel labelCartesRest;
    private JPanel panelCartes;
    public Carta carta;
    
    //Mètode constructor
    public Jugador(String nom) {
        this.nom = nom;
        this.cartes = new Carta[13];
        this.pos = 0;
        
        setBackground(new Color(0,102,0));
        
        if(this.nom == "jugador") { //És un bot
            jugador();
            this.cartesRest = 0;
        } else { //És l'usuari
            usuari();
            this.cartesRest = 13;
        }
        
        
    }
    
    //Mètode que construeix el bot
    private void jugador() {
        carta = new Carta(-1, "");
        
        labelCartesRest = new JLabel(cartesRest + "");
        labelCartesRest.setForeground(Color.white);
        labelCartesRest.setFont(new Font("Arial", Font.BOLD, 50));
        labelCartesRest.setAlignmentX(0.5f);
        labelCartesRest.setAlignmentY(0.5f);  
        
        setBorder(BorderFactory.createEmptyBorder(3, 95, 3, 92));
        setLayout(new OverlayLayout(this));
        
        add(labelCartesRest);
        add(carta);
        carta.ocultarCarta();
    }
    
    //Mètode que construeix l'usuari
    private void usuari() {
        setPreferredSize(new Dimension(800,170));
        setLayout(new BorderLayout());
        
        labelCartesRest = new JLabel("0");
        labelCartesRest.setForeground(Color.white);
        labelCartesRest.setFont(new Font("Arial", Font.BOLD, 20));
        labelCartesRest.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 5));
        
        panelCartes = new JPanel();
        panelCartes.setBackground(new Color(0,102,0));
        panelCartes.setLayout(new GridLayout(1,13, 5, 5));
        panelCartes.setBorder(BorderFactory.createEmptyBorder(2, 2, 10, 10));
        
        cartes = new Carta[13];
        
        //Declaram les cartes
        for(int i = 0; i < cartes.length; i++) {
            cartes[i] = new Carta(1, "clubs");
            
            if(i == 0) {
                cartes[i].ocultarCarta();
            } else {
                cartes[i].llevarCarta();
            }
            
            panelCartes.add(cartes[i]);
        }
                
        add(labelCartesRest, BorderLayout.NORTH);
        add(panelCartes, BorderLayout.CENTER);
        
    }
    
    //Mètode per mostrar les cartes a l'usuari
    public void mostrarCartes() {
        for(int i = 0; i < cartes.length; i++) {            
            if(i != 0) {
                cartes[i].llevarCarta();
                cartes[i].llevarCarta();
            }
        }
    }
    
    //Mètode per afegir una carta al jugador
    public void afegirCartaJugador(Carta carta) {
        cartes[this.pos] = carta;      //afegim la carta
        cartesRest++;
        labelCartesRest.setText(cartesRest + "");
        pos++;
    }
    
    //Mètode que retorna el número de cartes restants del jugador
    public int getCartesRest() {
        return cartesRest;
    }
    
    //Mètode que decrementa 1 el númeor de cartes restants
    public void decCartesRest() {
        cartesRest = cartesRest - 1;
        labelCartesRest.setText(cartesRest + ""); //Actualitza l'etiqueta
    }
}
