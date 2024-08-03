import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanal extends JPanel implements ActionListener {
    static final int Screen_Width= 600;
    static final int Screen_Hight= 600;
    static final int unit_size= 25;
    static final int game_unit=(Screen_Width*Screen_Hight)/unit_size;
    static final int DELAY=500;
    final int x[]= new int[game_unit];
    final int y[]= new int[game_unit];
    int bodyparts=6;
    int appleEaten;
    int applex;
    int appley;
    char direction= 'R';
    boolean running= false;
    Timer timer;
    Random random;
    GamePanal(){
        random =new Random();
        this.setPreferredSize(new Dimension(Screen_Width,Screen_Hight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
}
public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
}
public void draw(Graphics g){
        if(running) {
           /* for (int i = 0; i < Screen_Hight / unit_size; i++) {
                g.drawLine(i * unit_size, 0, i * unit_size, Screen_Hight);
                g.drawLine(0, i * unit_size, Screen_Width, i * unit_size);
            }*/
            g.setColor(Color.red);
            g.fillOval(applex, appley, unit_size, unit_size);
            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    //this line is for colorfull snake
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics matrics= getFontMetrics(g.getFont());
            g.drawString("Score: "+ appleEaten,(Screen_Width-matrics.stringWidth("Score: "+ appleEaten))/2,g.getFont().getSize());

        }
        else{
            gameOver(g);
        }
}
public void newApple(){
        applex= random.nextInt((int)(Screen_Width/unit_size))*unit_size;
        appley= random.nextInt((int)(Screen_Hight/unit_size))*unit_size;
}
public void move(){
        for (int i=bodyparts; i>0; i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
    }
        switch (direction){
            case 'U':
                y[0]=y[0]-unit_size;
                break;
            case 'D':
                y[0]=y[0]+unit_size;
                break;
            case 'L':
                x[0]=x[0]-unit_size;
                break;
            case 'R':
                x[0]=x[0]+unit_size;
                break;
        }
}
public void checkApple(){
    if((x[0]==applex)&&(y[0]==appley)){
        bodyparts++;
        appleEaten++;
        newApple();
    }
}
public void checkCollision(){
        //head collide with body
    for (int i= bodyparts;i>0;i--){
        if((x[0]==x[i])&&(y[0]==y[i])){
            running= false;
        }
    }
    // if head touches the left border
    if(x[0]<0){
        running=false;
    }
    // if head touches the right border
    if(x[0] > Screen_Width){
        running=false;
    }
    // if head touches the top border
    if(y[0]<0){
        running=false;
    }
    // if head touches the left border
    if(y[0] > Screen_Hight){
        running=false;
    }
    if(!running){
        timer.stop();
    }
}
public void gameOver(Graphics g){
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 40));
    FontMetrics matrics= getFontMetrics(g.getFont());
    g.drawString("Score: "+ appleEaten,(Screen_Width-matrics.stringWidth("Score: "+ appleEaten))/2,g.getFont().getSize());

    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD, 75));
    FontMetrics matrics1= getFontMetrics(g.getFont());
    g.drawString("GAME OVER",(Screen_Width-matrics1.stringWidth("GAME OVER"))/2,Screen_Hight/2);
}
    @Override
    public void actionPerformed(ActionEvent e) {
            if(running){
                move();
                checkApple();
                checkCollision();
            }
            repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
            }
        }
    }
}
