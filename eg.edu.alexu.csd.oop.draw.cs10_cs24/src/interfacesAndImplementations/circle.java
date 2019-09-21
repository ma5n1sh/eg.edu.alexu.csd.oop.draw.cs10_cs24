package interfacesAndImplementations;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class circle implements Shape{
    private Point position=new Point(0,0);
    private Map<String, Double> properties=new HashMap<>();
    private Color color=new Color(0);
    private Color fillColor=new Color(0);

    public circle(int X,int Y,double raduis,Color color,Color fillColor){
        this.position.x=X;this.position.y=Y;
        this.properties.put("radius",raduis);
        this.color=color;
        this.fillColor=fillColor;
    }

    public void setPosition(Point position) {
        this.position.x=position.x;
        this.position.y=position.y;

    }


    public Point getPosition() {
        return new Point(position.x,position.y);
    }


    public void setProperties(Map<String, Double> properties) {
        this.properties.put("radius",properties.get("radius"));
    }


    public Map<String, Double> getProperties() {
        Map<String, Double> temp=new HashMap<>();
        temp.put("radius",this.properties.get("radius"));
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
        canvas.drawOval(this.position.x,this.position.y,this.properties.get("radius").intValue(),this.properties.get("radius").intValue());
    }


    public Object clone() throws CloneNotSupportedException {
        return new circle(this.position.x,this.position.y,this.properties.get("radius"),this.color,this.fillColor);

    }
}
