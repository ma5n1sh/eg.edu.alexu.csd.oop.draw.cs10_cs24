package interfacesAndImplementations;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class square implements Shape {
    private Point position=new Point(0,0);
    private Map<String, Double> properties = new HashMap<>() ;
    private Color color=new Color(0);
    private Color fillColor=new Color(0);

    public square(int X,int Y,double length,Color color,Color fillColor){
        this.position.x=X;
        this.position.y=Y;
        this.properties.put("length",length);

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

    }

    public Map<String, Double> getProperties() {
        Map<String, Double> temp=new HashMap<>();
        temp.put("length",this.properties.get("length"));


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
        canvas.drawRect(this.position.x,this.position.y,properties.get("length").intValue(),properties.get("length").intValue());
    }

    public Object clone() throws CloneNotSupportedException {
        return new square(this.position.x,this.position.y,properties.get("length").intValue(),color,fillColor);
    }
}
