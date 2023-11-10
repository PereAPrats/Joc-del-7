package jocdel7;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Joan Martorell Coll
 * @author Pere Antoni Prats Villalonga
 * 
 */

public class Carta extends JPanel {
    
    //Declaracions
    private int num;
    private String palo;
    private ImageIcon imatge;
    private JLabel carta;
    private boolean ocultada;
    private boolean llevada;
    
    //Mètode constructor
    public Carta(int num, String palo) {
        this.num = num;
        this.palo = palo;
        this.ocultada = false;
        this.llevada = false;
        
        setBackground(new Color(0,82,0));
        
        if(num == -1) { //Pinta una carta girada
            pintar("Cartes/card_back_blue.png");
                        
        } else { //Pinta una carta normal
            pintar("Cartes/" + this.num + "_of_" + this.palo + ".png");
        }
        
    }
    
    //Mètode per pintar una carta
    public void pintar(String texto) {
        //Si carta existeix la borram
        if(this.carta != null) {
            remove(this.carta);
        }
        
        //Declaram imatge
        this.imatge = new ImageIcon(texto);
        this.imatge.setImage(this.imatge.getImage().getScaledInstance(70, 107, Image.SCALE_DEFAULT));
        
        //Afegim imatge
        this.carta = new JLabel(this.imatge);
        add(this.carta);
        
        revalidate();
        repaint();
    }
    
    //Mètode per ocultar una carta
    public void ocultarCarta() {
        if(!ocultada) {
            carta.setVisible(false);
            ocultada = true;
        } else {
            carta.setVisible(true);
            ocultada = false;
        }
    }
    
    //Mètode per llevar una carta
    public void llevarCarta() {
        if(!llevada) {
            setVisible(false);
            llevada = true;
        } else {
            setVisible(true);
            llevada = false;
        }
    }
    
    //Mètode per cambiar una carta
    public void cambiarCarta(int newNum, int newPalo) {
        this.num = newNum;
        this.ocultada = false;
        this.llevada = false;
        
        setPalo(newPalo);
        
        pintar("Cartes/" + this.num + "_of_" + this.palo + ".png");
    }
    
    //Mètode per saber si una carta està ocultada
    public boolean getOcultada() {
        return ocultada;
    }
    
    //Mètode per saber el número d'una carta (1-13)
    public int getNum() {
        return num;
    }
    
    //Mètode per saber el número de palo d'una carta (0-3)
    public int getNumPalo() {
        int res = 0;
        switch(palo) {
            case "clubs":
                res = 0;
                break;
            case "diamonds":
                res = 1;
                break;
            case "hearts":
                res = 2;
                break;
            case "spades":
                res = 3;
                break;
        }
        
        return res;
    }
    
    //Mètode que retorna informació de la carta (numero i palo)
    public String getInfo() {
        String s = "[";
        
        if(num < 10) {
            s = s + "0";
        }
        
        s = s + num + " " + palo + "]";
        
        return s;
    }
    
    //Mètode que retorna el palo de la carta
    public String getPalo() {
        return palo;
    }
    
    //Mètode per indicar el palo a partir de un número
    public void setPalo(int newPalo) {
        switch(newPalo) {
            case 0:
                this.palo = "clubs";
                break;
            case 1:
                this.palo = "diamonds";
                break;
            case 2:
                this.palo = "hearts";
                break;
            default:
                this.palo = "spades";
                break;
        }
    }
}
