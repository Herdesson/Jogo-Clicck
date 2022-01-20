
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author FLAVIO
 */
public class Jogo extends Canvas implements Runnable, KeyListener {
    
    private int width = 560;
    private int height = 600;
    private int tamanho = 55, bolaT = 10;  
    private Node[] play1 = new Node[tamanho];
    private Node[] play2 = new Node[tamanho];
    private Node[] bola = new Node[bolaT];
    private boolean right, left, inicio, teste = true;
    private String ultTec = "";
    private int play1X = 280, play2X = 290, play1Y = 530, play2Y = 70;
    private int bolaX = 80, bolaY = 130;
    private boolean dirCima = false, dirBaixo = false, dirCimaEsq = false, dirBaixoDir, dirBaixoEsq = false, dirCimaDir = false;
    private boolean toque = false, gamerOver = false;
    private int ultToc, eixoX,eixoY, resto, caminho;
    private String direct = "";
    private int timer;
    public Jogo(){
        Dimension dimensao = new Dimension(width, height);
        this.setPreferredSize(dimensao);
        this.addKeyListener(this);
        
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        
        for(int i = 0; i < play1.length; i++){
            play1[i] = new Node(play1X, play1Y);
            play1[i].Color(Color.red);
            play1[i].draw(g);
            
        }
        for(int e = 0; e < play2.length; e++){
            play2[e] = new Node(play2X, play2Y);
            play2[e].Color(Color.blue);
            play2[e].draw(g);
            
        }
        for(int b = 0; b < bola.length;b++){
            bola[b] = new Node(bolaX, bolaY);
            bola[b].Color(Color.green);
            bola[b].bola(g);
        }
        
        if(bolaY > play1Y){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Jogador 2 venceu!", 110, 280);
            g.dispose();
        }
        if(bolaY < play2Y){
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.drawString("Jogador 1 venceu!", 110, 280);
            g.dispose();
        }
        bs.show();
    }
    public void tick(){
        
        if(play1X >= width - 55){
            right = false;
        }
        if(play1X <= 0){
            left = false;
        }
        if(right){           
            play1X = play1[0].getPositionX();
            play1[0].setPositionX(play1X+=10);
            //for(int i = 0; i < 5; i++){
            //    play1[0].setPosition(position+=i);
            //}
            //position = 0;
            right = false;
            
        }
        else if(left){
            
            play1X = play1[0].getPositionX();
            play1[0].setPositionX(play1X-=10);
            //for(int i = 0; i < 5; i++){
             //   play1[0].setPosition(position-=i);
            //}
            //position = 0;          
            left = false;
        }
        movimento();
        colision();
        play2Movie();
        destruction();
    }
    public void play2Movie(){
        if(toque){
            timer++;
            ultToc = ultToc + 460;
            resto = ultToc - width + 1;
           //resto = width - ultToc;           
            if(resto < play2X && play2X >= 0){
                play2Esq(resto);                
            }else if(resto > play2X && play2X <= width - 55){
                play2Dir(resto);               
            }           
        }
    }
    public void play2Dir(int c){
        while(c > play2[0].getPositionX()){          
            play2X = play2[0].getPositionX();
            play2[0].setPositionX(play2X+=10);
            if(c == bolaX){
                return;
            }            
        }
    }
    public void play2Esq(int c){
        while(c < play2[0].getPositionX()){
            play2X = play2[0].getPositionX();
            play2[0].setPositionX(play2X-=10);       
            if(c == bolaX){
                return;
            }           
        }
    }
    public void movimento(){
        if(inicio){
        bolaX = bola[0].getPositionX();
        bolaY = bola[0].getPositionY();
        bola[0].setPositionX(bolaX++);
        bola[0].setPositionY(bolaY++);
        if(bolaX >= width){
           dirCima = true;}
        }
        if(dirCima){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX++);
            bola[0].setPositionY(bolaY--);
        }
        if(dirCimaEsq){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX--);
            bola[0].setPositionY(bolaY--);
            if(bolaX <= 0){
                dirBaixo = true;
            }
        }
        if(dirBaixo){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX--);
            bola[0].setPositionY(bolaY++);
        }if(dirBaixoDir){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX++);
            bola[0].setPositionY(bolaY++);
            if(bolaX >= width){
                dirBaixoEsq = true;
            }           
        }if(dirBaixoEsq){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX--);
            bola[0].setPositionY(bolaY++);           
        }
    }
    public void colision(){               
        if(new Rectangle(play1X,play1Y,55,10).intersects(new Rectangle(bolaX,bolaY,10,10))){           
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX++);
            bola[0].setPositionY(bolaY--);            
            inicio = false;
            dirCima = true;
            dirCimaEsq = false;
            dirBaixo = false;
            dirBaixoDir = false;
            dirBaixoEsq = false;
            if(dirCima){
                toque = true;
                ultToc = bolaX;              
            }
        }
        if(new Rectangle(play2X,play2Y,55,10).intersects(new Rectangle(bolaX,bolaY,10,10))){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX--);
            bola[0].setPositionY(bolaY++);            
            inicio = false;
            dirBaixo = true;
            dirCima = false;
            dirCimaEsq = false;
            dirBaixoDir = false;
            dirBaixoEsq = false;
            resto = 0;
            
        }
        if(bolaX >= width){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX--);
            bola[0].setPositionY(bolaY--);
            inicio = false;
            dirCimaEsq = true;
            dirCima = false;
            dirBaixo = false;
            dirBaixoDir = false;
            dirBaixoEsq = false;
        }
        if(bolaX <= 0){
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX++);
            bola[0].setPositionY(bolaY++);
            inicio = false;
            dirBaixoDir = true;
            dirCimaEsq = false;
            dirCima = false;
            dirBaixo = false;
            dirBaixoEsq = false;
        }
        if(dirBaixoEsq && bolaX >= width){           
            bolaX = bola[0].getPositionX();
            bolaY = bola[0].getPositionY();
            bola[0].setPositionX(bolaX--);
            bola[0].setPositionY(bolaY++);
            inicio = false;
            dirBaixoDir = false;
            dirCimaEsq = false;
            dirCima = false;
            dirBaixo = false;
            dirBaixoEsq = true;
        
    }}
    public void destruction(){
        if(bolaX < play2X){
            gamerOver = true;
        }else if(bolaX > play1X){
            gamerOver = true;
        }else{
            gamerOver = false;
        }
    }
    
    public static void main(String[] args) {
        Jogo game = new Jogo();
        JFrame frame = new JFrame("jogo");
        frame.add(game);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);       
        new Thread(game).start();
    }

    @Override
    public void run() {
        while(true){
            render();
            
            tick();
            if(teste){
                inicio = true;
                teste = false;
            }
            try{
                Thread.sleep(1000/800);
            }catch(Exception e){
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {       
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right = true;
            left = false;
            
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left = true;
            right = false;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
