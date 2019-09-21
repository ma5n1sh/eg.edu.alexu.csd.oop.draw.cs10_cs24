package interfacesAndImplementations;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ellipse implements Shape {
    private Point position=new Point(0,0);
    private Map<String, Double> properties=new HashMap<>();
    private Color color=new Color(0);
    private Color fillColor=new Color(0);

    public ellipse(int X, int Y, double r1, double r2, Color color, Color fillColor){
        this.position.x=X;this.position.y=Y;
        this.properties.put("majorRadius",r1);
        this.properties.put("minorRadius",r2);
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
        this.properties.put("majorRadius",properties.get("majorRadius"));
        this.properties.put("minorRadius",properties.get("minorRadius"));
    }


    public Map<String, Double> getProperties() {
        Map<String, Double> temp=new HashMap<>();
        temp.put("majorRadius",this.properties.get("majorRadius"));
        temp.put("minorRadius",this.properties.get("minorRadius"));
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
        canvas.drawOval(this.position.x,this.position.y,this.properties.get("majorRadius").intValue(),this.properties.get("minorRadius").intValue());
    }


    public Object clone() throws CloneNotSupportedException {
        return new ellipse(this.position.x,this.position.y,this.properties.get("majorRadius"),this.properties.get("minorRadius"),this.color,this.fillColor);

    }
}

