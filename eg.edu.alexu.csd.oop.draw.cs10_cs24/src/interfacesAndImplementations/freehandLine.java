package interfacesAndImplementations;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class freehandLine implements Shape {
    private Point position=new Point();
    private Color color;
    private Color fillColor;
    private ArrayList<Point> path=new ArrayList<>();
    private Map<String,Double> properties=new HashMap<>();

    public freehandLine(int startX, int startY, int endX, int endY, Color color, Color fillColor,ArrayList<Point> path){
        position.x=startX;
        position.y=startY;
        properties.put("endX",(double)endX);
        properties.put("endY",(double)endY);
        this.color=color;
        this.fillColor=fillColor;
        this.path= (ArrayList<Point>) path.clone();

    }



    public void setPosition(Point position) {
        this.position=position;
    }

    public void addPoint(int x ,int y){
        this.path.add(new Point(x,y));
    }

    public Point[] getPoints(){
        Point[] temp=new Point[path.size()];
        for(int i=0;i<path.size();i++){temp[i]=path.get(i);}
        return  temp;
    }



    public Point getPosition() {
        return this.position;
    }

    public void setProperties(Map<String, Double> properties) {
        this.properties.put("endX",properties.get("endX"));
        this.properties.put("endY",properties.get("endY"));
    }


    public Map<String, Double> getProperties() {
        Map<String, Double> temp=new HashMap<>();
        temp.put("endX",this.properties.get("endX"));
        temp.put("endY",this.properties.get("endY"));
        return temp;
    }


    public void setColor(Color color) {
        this.color=color;

    }


    public Color getColor() {
        return this.color;
    }


    public void setFillColor(Color color) {
        this.fillColor=color;
    }


    public Color getFillColor() {
        return this.fillColor;
    }


    public void draw(Graphics canvas) {
       for(int i=0;i<path.size();i++) {
           canvas.fillRect(path.get(i).x,path.get(i).y,1,1);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new freehandLine(position.x,position.y,properties.get("endX").intValue(),properties.get("endY").intValue(),color,fillColor, (ArrayList<Point>) this.path.clone());
    }
}
