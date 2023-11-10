package jocdel7;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Joan Martorell Coll
 * @author Pere Antoni Prats Villalonga
 * 
 */

public class Tauler extends JPanel {
    
    //Declaracions
    private static final int TOTAL_JUG = 4;
    private static final int totalCartes = 52;
    private static final int files = 4;
    private static final int columnes = 13;
    private static final String [] palos = new String[]{"clubs", "diamonds", "hearts", "spades"};
    private boolean acabat = false;
    private int torn = 3;               //4 jugadors, 3 = usuari
    
    public Carta c[][];
    
    //Mètode constructor
    public Tauler() {
        setBackground(new Color(0,102,0));
        setLayout(new GridLayout(files, columnes, 5, 5));
        
        c = new Carta[files + 1][columnes + 1];
        
        //Cream el tauler
        for(int f = 0; f < files; f++) {
            for(int n = 1; n <= columnes; n++) {
                c[f][n] = new Carta(n, palos[f]);
                add(c[f][n]);
            }
        }
    }
    
    //Mètode per mesclar les cartes
    public void mescla() {
        //Declaracions
        Random r = new Random();
        int posRand;
        int [] posRandom;
        int [] pos;
        Carta aux = new Carta(-1, "clubs");
        
        // Recorrem totes les cartes
        for (int i = 1; i <= totalCartes; i++) {
            
            pos = convertirPosCarta(i); //Tenim [x][y] de la posició actual del recorregut
            
            // Es calcula una posició random
            posRand = r.nextInt(totalCartes) + 1; //Número aleatòri entre 1-52
            posRandom = convertirPosCarta(posRand); //Tenim [x][y] de una posició random de la baralla
            
            //Si les cartes són diferents...
            if(c[posRandom[0]][posRandom[1]] != c[pos[0]][pos[1]]) {
                //Guardam la carta aleatòria
                aux.cambiarCarta(c[posRandom[0]][posRandom[1]].getNum(), c[posRandom[0]][posRandom[1]].getNumPalo());
                //Cambiam la carta aleatòria per la de a posició actual
                c[posRandom[0]][posRandom[1]].cambiarCarta(c[pos[0]][pos[1]].getNum(), c[pos[0]][pos[1]].getNumPalo());
                //Cambiam la carta de la posició actual per la aleatòria
                c[pos[0]][pos[1]].cambiarCarta(aux.getNum(), aux.getNumPalo());
            }            
        }
    }
    
    
    //Mètode per començar a jugar
    public void jugar() {
        JocDel7.setInfo("Les cartes estan repartides, és el teu torn, posa un 7 si el tens.");
                
        //Repartim les tres primeres files entre els bots
        for(int i = 0; i < TOTAL_JUG-1; i++) {
            for(int j = 1; j <= columnes; j++) {
                
                JocDel7.jugador[i].afegirCartaJugador(this.c[i][j]);
                JocDel7.jugador[i].carta.ocultarCarta();
            }
        }
        
        //La darrera fila será per l'usuari
        Carta s;
        for(int j = 1; j <= columnes; j++) {
            s = this.c[3][j];
            JocDel7.panelUsuari.cartes[j-1].cambiarCarta(s.getNum(), s.getNumPalo());
            JocDel7.panelUsuari.labelCartesRest.setText(JocDel7.panelUsuari.getCartesRest() + "");
        }
        //Mostram les cartes al jugador
        JocDel7.panelUsuari.mostrarCartes();
        
        //Refeim el tauler i l'ocultam
        Carta aux;
        for(int f = 0; f < files; f++) {
            for(int n = 1; n <= columnes; n++) {
                
                this.c[f][n].pintar("Cartes/" + f + "_of_" + n + ".png");
                this.c[f][n].ocultarCarta();
            }
        }
        
        //Pròxim torn
        proximTorn();
    }
    
    //Mètode per el pròxim torn
    public void proximTorn() {
        if(torn == 3) { //Torn del jugador
            //Configuram els botons que han d'apareixer
            JocDel7.botoJuga.setVisible(false);
            JocDel7.botoMescla.setVisible(false);
            JocDel7.botoPassa.setVisible(true);
            JocDel7.botoTornJugador.setVisible(false);

            JocDel7.autoritzat = true; //Pot clicar

            torn = 0; //Següent torn

        } else { //Torn dels bots
            //Configuram els botons que han d'apareixer
            JocDel7.botoPassa.setVisible(false);
            JocDel7.botoTornJugador.setVisible(true);
            
            JocDel7.autoritzat = false; //No pot clicar
        }
    }
    
    //Mètode per moure el bot
    public void mouBot(int jug) {   
        //Declaracions
        boolean posada = false;
        Carta [] cartes = JocDel7.jugador[jug].cartes;
        Carta aux;
        int j = jug + 1; //Nom del jugador
        
        int i = 0;
        while(!posada) { //Mentre cap carta s'hagi posat
            //Quan ja hem mirat totes les cartes sense poder-ne posar cap
            if(i > 12) {
                JocDel7.setInfo("El jugador " + j + " passa");
                posada = true; //Sortim del bucle
                
            //Miram si podem posar cap carta    
            } else {
                if(cartaPosible(cartes[i])) { //Si la carta es pot posar...
                    JocDel7.jugador[jug].decCartesRest(); //Decrementam el número de cartes al bot
                    
                    JocDel7.setInfo("El jugador " + j + " posa el " + cartes[i].getInfo());
                    
                    if(JocDel7.jugador[jug].getCartesRest() != 0) { //Si el número de cartes del bot és diferent de 0...
                        //Pintam la carta al tauler
                        this.c[cartes[i].getNumPalo()][cartes[i].getNum()].pintar("Cartes/" + cartes[i].getNum() + "_of_" + cartes[i].getPalo() + ".png");
                        this.c[cartes[i].getNumPalo()][cartes[i].getNum()].ocultarCarta();

                    } else { //Si és 0...
                        Uep.Uep(j); //Mostram pestanya
                        
                        //Modificam els botons
                        JocDel7.botoPassa.setEnabled(false);
                        JocDel7.botoPassa.setBackground(new Color(219, 230, 242));
                        JocDel7.botoTornJugador.setEnabled(false);
                        JocDel7.botoTornJugador.setBackground(new Color(219, 230, 242));
                    }
                    
                    posada = true; //Sorim del bucle
                
                }
                i++; //Següent carta
            }
        }
    }
    
    //Mètode per saber si una carta es pot posar
    public boolean cartaPosible(Carta s) {
        boolean res = false;
        
        if(this.c[s.getNumPalo()][s.getNum()].getOcultada()) { //Si la carta està ocultada...

            if(s.getNum() == 7) { //Si es un 7 la podem posar
                res = true;
            } else if(s.getNum() == 1) { //Si es de la primera columna miram la següent
                Carta seguent = this.c[s.getNumPalo()][s.getNum()+1];

                if(!seguent.getOcultada()) {//Si la següent no está ocultada la podem posar
                    res = true;
                }

            } else if(s.getNum() == 13) {  //Si es de la darrera columna miram l'anterior
                Carta anterior = this.c[s.getNumPalo()][s.getNum()-1];

                if(!anterior.getOcultada()) { //Si l'anterior no está ocultada la podem posar
                    res = true;
                }

            } else { //Si la carta està per enmig mirar la següent i l'anterior
                Carta anterior = this.c[s.getNumPalo()][s.getNum()-1];
                Carta seguent = this.c[s.getNumPalo()][s.getNum()+1];

                if(!seguent.getOcultada() || !anterior.getOcultada()) { //Si alguna de les dues no está ocultada la podem posar
                    res = true;
                }
            }
        }
        
        return res;
    }
    
    //Mètode per convertir un número en coordenades n -> [x,y]
    private int [] convertirPosCarta(int pos) {
        int [] res = new int[2];
        
        int i; //fila
        int j; //columna
        
        if(pos <= columnes) {
            i = 0;
            j = pos;
            
        } else if(pos > columnes && pos <= columnes*2) {
            i = 1;
            j = pos - columnes;
            
        } else if(pos > columnes*2 && pos <= columnes*3) {
            i = 2;
            j = pos - columnes*2;
            
        } else {
            i = 3;
            j = pos - columnes*3;
        }
        
        res[0] = i;
        res[1] = j;
        
        return res;
    }
    
    //Mètode que retorna el torn
    public int getTorn() {
        return torn;
    }
    
    //Mètode per sumar 1 a torn
    public void addTorn() {
        torn++;
    }
}
