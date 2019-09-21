package interfacesAndImplementations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class rectangle implements Shape{
    private Point position=new Point(0,0);
    private Map<String, Double> properties = new HashMap<>() ;
    private Color color=new Color(0);
    private Color fillColor=new Color(0);

    public rectangle(int X, int Y, double length, double width, Color color, Color fillColor){
        this.position=new Point(X,Y);
        this.properties.put("length",length);
        this.properties.put("width",width);
        this.color=color;
        this.fillColor=fillColor;

    }

    public void setPosition(Point position) {
        this.position.x=position.x;
        this.position.y=position.y;
    }


    public Point getPosition() {
        return new Point(this.position.x,this.position.y);
    }


    public void setProperties(Map<String, Double> properties) {
        this.properties.put("length",properties.get("length"));
        this.properties.put("width",properties.get("width"));

    }


    public Map<String, Double> getProperties() {
        Map<String, Double> temp=new HashMap<>();
        temp.put("length",this.properties.get("length"));
        temp.put("width",this.properties.get("width"));
        return temp;
    }


    public void setColor(Color color) {
        this.color=color;
    }


    public Color getColor() {
        return color;
    }


    public void setFillColor(Color color) {
        this.fillColor=color;

    }


    public Color getFillColor() {
        return fillColor;
    }


    public void draw(Graphics canvas) {
        canvas.drawRect((int)position.x,(int)position.y, properties.get("length").intValue(),properties.get("width").intValue());

    }
    public Object clone() throws CloneNotSupportedException{
        return new rectangle(position.x,position.y,properties.get("length"),properties.get("width"),color,fillColor);
    }

}
