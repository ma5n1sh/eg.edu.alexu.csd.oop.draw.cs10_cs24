package GuiAndController;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import javax.swing.*;


public class Menu extends BasicGameState{

    private int firstX=-1,firstY=-1,X,Y;
    private boolean flag=false;
    private char s='s';
    private int xpos,ypos;
    guiController c=new guiController();

    public Menu(int state) throws SlickException {


    }

    public void init(GameContainer gc,StateBasedGame sbg )throws SlickException {

    }

    public void render(GameContainer gc,StateBasedGame sbg,Graphics g) throws SlickException{
        Image items=new Image("res/"+s+".png");
        g.setLineWidth(3);
        g.setColor(Color.white);
        g.fillRect(0,0,1280,720);
        g.setColor(Color.black);
        c.drawer(g);

        c.realtimeDrawer(g,s,firstX,firstY,X,Y,!Mouse.isButtonDown(0),Mouse.getX(),Mouse.getY());

        g.drawImage(items,0,60);
        g.setColor(Color.black);
        g.drawString("Stroke Fill",1125,35);
        g.drawString("Stroke Fill",1125,35);
        g.setColor(new Color(c.color.getRGB()));
        g.fillRect(1150,10,20,20);
        g.setColor(new Color(c.fillColor.getRGB()));
        g.fillRect(1180,10,20  ,20);
        g.setAntiAlias(true);
    }



    public void update(GameContainer gc,StateBasedGame sbg,int delta) throws SlickException{
        xpos=Mouse.getX();
        ypos=Mouse.getY();
        int y=720-ypos;
        if(xpos<41&&y>60&&y<446){
            if(Mouse.isButtonDown(0)){
                if(y<102){s='s';}//for selector
                else if(y<144){s='l';}//for line
                else if(y<186){s='r';}//for rectangle
                else if(y<228){s='c';}//for carré
                else if(y<270){s='t';}//for triangle
                else if(y<312){s='d';}//for dayra
                else if(y<362){s='e';}//for ellipse
                else if(y<404){s='f';}//for free hand
                else{s='x';}//for delete
            }
        }
        else if(Mouse.isButtonDown(0)&&y>10&&y<30&&((xpos>1150&&xpos<1170)||(xpos>1180&&xpos<1200))){
            if((xpos<1170)){c.colorChooser();}
            else {c.fillColorChooser();}
        }
        else {
            if (Mouse.isButtonDown(0)) {
                if (!flag) {
                    firstX = xpos;
                    firstY = ypos;
                    flag = true;
                }
                X = xpos;
                Y = ypos;

            } else if (flag) {
                X = xpos;
                Y = ypos;
                flag = false;

            }
        }
    }

    public void window(String window, int delta, StateBasedGame sbg) {}

    public int getID() {return 0;}

}
