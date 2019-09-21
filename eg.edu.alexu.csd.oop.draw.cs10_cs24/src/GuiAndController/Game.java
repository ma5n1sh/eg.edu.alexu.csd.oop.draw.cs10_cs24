package GuiAndController;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Game extends StateBasedGame {
    private static final String appName="paint";
    private static final int menu=0;
    private static final int play=1;

    private Game(String appName) throws SlickException {
        super(appName);
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        this.enterState(menu);
    }

    public void initStatesList(GameContainer gc )throws SlickException{
        this.getState(menu).init(gc,this);
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appc = new AppGameContainer(new Game(appName));

            appc.setDisplayMode(1280, 720, false);
            appc.start();
            appc.setTargetFrameRate(30);


        } catch (SlickException e) {
            System.out.println("Slick Exception Found");
        }

    }
}