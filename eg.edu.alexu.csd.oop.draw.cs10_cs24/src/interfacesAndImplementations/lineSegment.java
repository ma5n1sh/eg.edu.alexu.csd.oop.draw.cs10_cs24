package interfacesAndImplementations;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class lineSegment  implements Shape  {
    private Point position=new Point(0,0);
    private Map<String, Double> properties=new HashMap<>();
    private Color color=new Color(0);
    private Color fillColor=new Color(0);

    public lineSegment(int startX, int startY, int endX, int endY, Color color, Color fillColor){

        position.x=startX;
        position.y=startY;
        properties.put("endX",(double)endX);
        properties.put("endY",(double)endY);
        this.color=color;
        this.fillColor=fillColor;
    }

    public void setPosition(Point position) {
        this.position.x=position.x;
        this.position.y=position.y;
    }


    public Point getPosition() {
        return position;
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
        canvas.drawLine(position.x,position.y,properties.get("endX").intValue(),properties.get("endY").intValue());
    }


    public Object clone() throws CloneNotSupportedException {
        return new lineSegment(position.x,position.y,properties.get("endX").intValue(),properties.get("endY").intValue(),color,fillColor);
    }


}
