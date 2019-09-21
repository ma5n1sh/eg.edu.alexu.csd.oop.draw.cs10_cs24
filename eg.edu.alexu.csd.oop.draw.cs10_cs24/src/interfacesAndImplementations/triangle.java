package interfacesAndImplementations;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class triangle implements Shape {

    private Point position=new Point(0,0);
    private Map<String, Double> properties=new HashMap<>();
    private Color color=new Color(0);
    private Color fillColor=new Color(0);

    public triangle(int X1,int Y1,int X2,int Y2,int X3,int Y3,Color color,Color fillColor){
        this.position.x=X1;this.position.y=Y1;
        this.properties.put("X2",(double)X2);
        this.properties.put("X3",(double)X3);
        this.properties.put("Y2",(double)Y2);
        this.properties.put("Y3",(double)Y3);
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
        this.properties.put("X2",properties.get("X2"));
        this.properties.put("X3",properties.get("X3"));
        this.properties.put("Y2",properties.get("Y2"));
        this.properties.put("Y3",properties.get("Y3"));
    }


    public Map<String, Double> getProperties() {
        Map<String, Double> temp=new HashMap<>();
        temp.put("X2",this.properties.get("X2"));
        temp.put("X3",this.properties.get("X3"));
        temp.put("Y2",this.properties.get("Y2"));
        temp.put("Y3",this.properties.get("Y3"));
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
        int x[]={this.position.x,this.properties.get("X2").intValue(),this.properties.get("X3").intValue()};
        int y[]={this.position.y,this.properties.get("Y2").intValue(),this.properties.get("Y3").intValue()};

        canvas.drawPolygon(x,y,3);
    }


    public Object clone() throws CloneNotSupportedException {
        return new triangle(this.position.x,this.position.y,this.properties.get("X2").intValue(),this.properties.get("Y2").intValue(),
                this.properties.get("X3").intValue(),this.properties.get("Y3").intValue(),this.color,this.fillColor
        );
    }
}
