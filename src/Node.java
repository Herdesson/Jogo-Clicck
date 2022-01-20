
import java.awt.Color;
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FLAVIO
 */
public class Node {
    private int x, y;
    private Color cor;
    public Node(){}
    public Node(int x, int y){
    this.x = x;
    this.y = y;
    }
    public void draw(Graphics g){
        g.setColor(cor);
        g.fillRect(x, y, 55, 10);
    }
    public void Color(Color c){
        this.cor = c;
    }
    public void bola(Graphics g){
        g.setColor(cor);
        g.fillRect(x, y, 10, 10);
    }
    public void setPositionX(int x){
        this.x = x;
    }
    public int getPositionX(){
        return x;
    }
    public void setPositionY(int y){
        this.y = y;
    }
    public int getPositionY(){
        return y;
    }
    
}
