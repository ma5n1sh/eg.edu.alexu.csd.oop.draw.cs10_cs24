package GuiAndController;

import interfacesAndImplementations.*;
import interfacesAndImplementations.Shape;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

 class guiController {
    private drawingEngineImpl engine=new drawingEngineImpl();
    private Shape[] arr=engine.getShapes();
    private int firstX,firstY, X, Y,newX,newY,prevX=-1,prevY=-1;
    private ArrayList<Shape> invisible=new ArrayList<>();
    private Shape inv;
    private boolean flag=true,isBeingResized=false;
    private Shape temp,selected;
    Color color=Color.black,fillColor=Color.black;


     void colorChooser(){

        color= JColorChooser.showDialog(null,"Please Select The Stroke Color",Color.BLACK);
        if(color==null){color=Color.black;}
        if(selected!=null){selected.setColor(color);}
    }


     void fillColorChooser(){

        fillColor= JColorChooser.showDialog(null,"Please Select The Fill Color",Color.BLACK);
        if(fillColor==null){fillColor=Color.black;}
        if(selected!=null){selected.setFillColor(fillColor);}
    }

    private double area(int x1, int y1, int x2, int y2,int x3, int y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+x3*(y1-y2))/2.0);
    }


    private boolean isInsideTriangle(int x1, int y1, int x2,int y2, int x3, int y3, int x, int y) {
        double A = area (x1, y1, x2, y2, x3, y3);
        double A1 = area (x, y, x2, y2, x3, y3);
        double A2 = area (x1, y1, x, y, x3, y3);
        double A3 = area (x1, y1, x2, y2, x, y);


        return (A == A1 + A2 + A3);
    }




    private boolean isInsideEllipse(double centerX, double centerY, double x, double y, double semiMajorAxis, double semiMinorAxis){
        return 1>( (Math.pow((x - centerX), 2) / Math.pow(semiMajorAxis, 2))+ (Math.pow((y - centerY), 2) / Math.pow(semiMinorAxis, 2)));
    }


    private boolean isOnTheLine(Shape shape,int x,int y){
        double a=Math.pow(shape.getPosition().x-shape.getProperties().get("endX"),2)+Math.pow(shape.getPosition().y-shape.getProperties().get("endY"),2);
        double b=Math.pow(shape.getPosition().x-x,2)+Math.pow(shape.getPosition().y-y,2);
        double c=Math.pow(x-shape.getProperties().get("endX"),2)+Math.pow(y-shape.getProperties().get("endY"),2);
        return Math.sqrt(a)+0.15>=Math.sqrt(b)+Math.sqrt(c);
    }

    private boolean isInside(Shape shape,int x,int y){
        if(shape.getClass()==rectangle.class){
            if(x>Math.min(shape.getPosition().x,shape.getPosition().x+shape.getProperties().get("length"))
            &&x<Math.max(shape.getPosition().x,shape.getPosition().x+shape.getProperties().get("length"))){
                if(y>Math.min(shape.getPosition().y,shape.getPosition().y+shape.getProperties().get("width"))
                        &&y<Math.max(shape.getPosition().y,shape.getPosition().y+shape.getProperties().get("width"))){
                    return true;
                }
            }
        }
        if(shape.getClass()==square.class){
            if(x>Math.min(shape.getPosition().x,shape.getPosition().x+shape.getProperties().get("length"))
                    &&x<Math.max(shape.getPosition().x,shape.getPosition().x+shape.getProperties().get("length"))){
                return y > Math.min(shape.getPosition().y, shape.getPosition().y + shape.getProperties().get("length"))
                        && y < Math.max(shape.getPosition().y, shape.getPosition().y + shape.getProperties().get("length"));
            }
        }
        if(shape.getClass()==circle.class){
            return (Math.pow(x - shape.getPosition().x - shape.getProperties().get("radius")/2f,2)+Math.pow(y - shape.getPosition().y-shape.getProperties().get("radius")/2f ,2) )<Math.pow(shape.getProperties().get("radius")/2f,2);
        }
        if(shape.getClass()==triangle.class){
            return isInsideTriangle(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("X2").intValue(),
                    shape.getProperties().get("Y2").intValue(), shape.getProperties().get("X3").intValue(), shape.getProperties().get("Y3").intValue(), x, y);
        }
        if(shape.getClass()== ellipse.class){
            return isInsideEllipse((shape.getPosition().x+shape.getProperties().get("majorRadius")/2),shape.getPosition().y+shape.getProperties().get("minorRadius")/2,x,y,shape.getProperties().get("majorRadius")/2,shape.getProperties().get("minorRadius")/2);
        }
        if(shape.getClass()==lineSegment.class){
            return isOnTheLine(shape, x, y);
        }
        return false;

    }



    private void shapeDetector( int firstX, int firstY){
        for (Shape shape : arr) {
            if (!flag||isBeingResized) break;
            if (isInside(shape, firstX, 720 - firstY)) {
                temp = shape;
                selected=shape;
            }
        }

    }

    private void shapeLimitAdder(Graphics g){
        if(selected!=null) {g.setColor(org.newdawn.slick.Color.lightGray);
            if(inv==null||inv!=selected) {
                if (selected.getClass() == rectangle.class) {

                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4 + selected.getProperties().get("length").intValue(), selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4 + selected.getProperties().get("width").intValue(), 8, 8);
                    g.setColor(org.newdawn.slick.Color.red);
                    if(selected.getProperties().get("length")>0&&selected.getProperties().get("width")>0) {
                        g.fillRect(selected.getPosition().x + selected.getProperties().get("length").intValue(), selected.getPosition().y + selected.getProperties().get("width").intValue(), 12, 12);
                    }
                    else{
                        g.fillRect(selected.getPosition().x-12 + selected.getProperties().get("length").intValue(), selected.getPosition().y-12 + selected.getProperties().get("width").intValue(), 12, 12);

                    }
                }
                if (selected.getClass() == square.class) {

                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4 + selected.getProperties().get("length").intValue(), selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4 + selected.getProperties().get("length").intValue(), 8, 8);
                    g.setColor(org.newdawn.slick.Color.red);
                    if(selected.getProperties().get("length")>0) {
                        g.fillRect(selected.getPosition().x + selected.getProperties().get("length").intValue(), selected.getPosition().y + selected.getProperties().get("length").intValue(), 12, 12);
                    }
                    else{
                        g.fillRect(selected.getPosition().x + selected.getProperties().get("length").intValue()-12, selected.getPosition().y-12 + selected.getProperties().get("length").intValue(), 12, 12);

                    }

                }
                if (selected.getClass() == circle.class) {

                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4 + selected.getProperties().get("radius").intValue(), selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4 + selected.getProperties().get("radius").intValue(), 8, 8);
                    g.setColor(org.newdawn.slick.Color.red);
                    g.fillRect(selected.getPosition().x - 4 + selected.getProperties().get("radius").intValue(), selected.getPosition().y - 4 + selected.getProperties().get("radius").intValue(), 12, 12);


                }
                if (selected.getClass() == ellipse.class) {
                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4 + selected.getProperties().get("majorRadius").intValue(), selected.getPosition().y - 4, 8, 8);
                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4 + selected.getProperties().get("minorRadius").intValue(), 8, 8);
                    g.setColor(org.newdawn.slick.Color.red);
                    g.fillRect(selected.getPosition().x  + selected.getProperties().get("majorRadius").intValue(), selected.getPosition().y  + selected.getProperties().get("minorRadius").intValue(), 12, 12);

                }
                if (selected.getClass() == triangle.class) {
                    g.setColor(org.newdawn.slick.Color.red);
                    g.fillRect(selected.getPosition().x - 4, selected.getPosition().y - 4, 12, 12);
                    g.fillRect(selected.getProperties().get("X2").intValue()-4, selected.getProperties().get("Y2").intValue()-4, 12, 12);
                    g.fillRect(selected.getProperties().get("X3").intValue()-4, selected.getProperties().get("Y3").intValue()-4, 12, 12);

                }
                if (selected.getClass() == lineSegment.class) {
                    g.setColor(org.newdawn.slick.Color.red);
                    g.fillRect(selected.getPosition().x -4, selected.getPosition().y - 4, 12, 12);
                    g.fillRect(selected.getProperties().get("endX").intValue(), selected.getProperties().get("endY").intValue(), 12, 12);

                }
            }


        }
    }
    private boolean isInsideResizeRectangle(int x,int y,int redPosX,int redPosY){
        return (x >= redPosX) && (x <= (redPosX + 12)) && (y >= redPosY) && (y <= (redPosY + 12));
    }




    private void shapeResize(Graphics g, int firstX, int firstY, int X, int Y, boolean notClicked){
        if(isDrawn(firstX,firstY,X,Y)) {
            if (selected != null) {
                if (selected.getClass() == rectangle.class) {

                    int newLen , newWid;
                    if ((isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x + selected.getProperties().get("length").intValue(), selected.getPosition().y + selected.getProperties().get("width").intValue())
                            && selected.getProperties().get("length") > 0 && selected.getProperties().get("width") > 0) ||
                            (isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x - 12 + selected.getProperties().get("length").intValue(), selected.getPosition().y - 12 + selected.getProperties().get("width").intValue()))
                    ) {

                        inv = selected;
                        if (!notClicked) {
                            isBeingResized = true;
                            newLen = (int) (selected.getProperties().get("length") + X - firstX);
                            newWid = (int) (selected.getProperties().get("width") + firstY - Y);
                            g.drawRect(selected.getPosition().x, selected.getPosition().y, newLen, newWid);

                        } else {
                            Map<String, Double> z = new HashMap<>();
                            z.put("length", selected.getProperties().get("length") + X - firstX);
                            z.put("width", selected.getProperties().get("width") + firstY - Y);
                            selected.setProperties(z);
                            inv = null;
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                        }
                    }

                }
                if (selected.getClass() == square.class) {

                    if ((isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x + selected.getProperties().get("length").intValue(), selected.getPosition().y + selected.getProperties().get("length").intValue()) && selected.getProperties().get("length") > 0)
                            || (isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x - 12 + selected.getProperties().get("length").intValue(), selected.getPosition().y - 12 + selected.getProperties().get("length").intValue()))) {
                        int newLen ;
                        inv = selected;
                        if (!notClicked) {
                            isBeingResized = true;
                            int max = Math.max(X - firstX, firstY - Y + 1 - 1);
                            newLen = (int) (selected.getProperties().get("length") + max);

                            g.drawRect(selected.getPosition().x, selected.getPosition().y, newLen, newLen);

                        } else {
                            Map<String, Double> z = new HashMap<>();

                            z.put("length", selected.getProperties().get("length") + Math.max(firstY - Y - 1 + 1, X - firstX));

                            selected.setProperties(z);
                            inv = null;
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                        }
                    }
                }
                if (selected.getClass() == circle.class) {
                    if (isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x - 4 + selected.getProperties().get("radius").intValue(), selected.getPosition().y - 4 + selected.getProperties().get("radius").intValue())) {
                        int newLen ;
                        inv = selected;
                        if (!notClicked) {
                            isBeingResized = true;
                            int max = Math.max(X - firstX, firstY - Y);
                            newLen = (int) (selected.getProperties().get("radius") + max);

                            g.drawOval(selected.getPosition().x, selected.getPosition().y, newLen, newLen);

                        } else {
                            Map<String, Double> z = new HashMap<>();
                            z.put("radius", selected.getProperties().get("radius") + Math.max(firstY - Y, X - firstX));
                            selected.setProperties(z);
                            inv = null;
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                        }
                    }
                }
                if (selected.getClass() == ellipse.class) {
                    if ((isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x + selected.getProperties().get("majorRadius").intValue(), selected.getPosition().y + selected.getProperties().get("minorRadius").intValue()))) {

                        int newLenX, newLenY;
                        inv = selected;
                        if (!notClicked) {
                            isBeingResized = true;
                            newLenX = (int) (selected.getProperties().get("majorRadius") + X - firstX);
                            newLenY = (int) (selected.getProperties().get("minorRadius") + firstY - Y);
                            g.drawOval(selected.getPosition().x, selected.getPosition().y, newLenX, newLenY);

                        } else {
                            Map<String, Double> z = new HashMap<>();

                            z.put("majorRadius", selected.getProperties().get("majorRadius") + X - firstX);
                            z.put("minorRadius", selected.getProperties().get("minorRadius") + firstY - Y);
                            selected.setProperties(z);
                            inv = null;
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                        }
                    }
                }
                if (selected.getClass() == lineSegment.class && isDrawn(firstX, firstY, X, Y)) {

                    if (isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x - 4, selected.getPosition().y - 4)) {

                        if (!notClicked) {
                            isBeingResized = true;
                            inv = selected;
                            g.drawLine(selected.getProperties().get("endX").intValue(), selected.getProperties().get("endY").intValue(), selected.getPosition().x + X - firstX, selected.getPosition().y + firstY - Y);
                        } else {
                            selected.setPosition(new Point(selected.getPosition().x + X - firstX, selected.getPosition().y + firstY - Y));
                            inv = null;
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;

                        }

                    }
                    if (isInsideResizeRectangle(firstX, 720 - firstY, selected.getProperties().get("endX").intValue(), selected.getProperties().get("endY").intValue())) {
                        if (!notClicked) {
                            isBeingResized = true;
                            inv = selected;
                            g.drawLine(selected.getPosition().x, selected.getPosition().y, (float) (selected.getProperties().get("endX") + X - firstX), (float) (selected.getProperties().get("endY") + firstY - Y));

                        } else {
                            Map<String, Double> z = new HashMap<>();
                            z.put("endX", selected.getProperties().get("endX") + X - firstX);
                            z.put("endY", selected.getProperties().get("endY") + firstY - Y);
                            selected.setProperties(z);
                            inv = null;
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                        }
                    }
                }


                if (selected.getClass() == triangle.class) {
                    if (isInsideResizeRectangle(firstX, 720 - firstY, selected.getProperties().get("X2").intValue() - 4, selected.getProperties().get("Y2").intValue() - 4)) {

                        if (!notClicked) {
                            isBeingResized = true;
                            inv = selected;
                            g.drawLine(selected.getPosition().x, selected.getPosition().y, selected.getProperties().get("X2").intValue() + X - firstX, selected.getProperties().get("Y2").intValue() + firstY - Y);
                            g.drawLine(selected.getProperties().get("X2").intValue() + X - firstX, selected.getProperties().get("Y2").intValue() + firstY - Y, selected.getProperties().get("X3").intValue(), selected.getProperties().get("Y3").intValue());
                            g.drawLine(selected.getProperties().get("X3").intValue(), selected.getProperties().get("Y3").intValue(), selected.getPosition().x, selected.getPosition().y);

                        } else {
                            Map<String, Double> z = new HashMap<>();
                            z.put("X2", selected.getProperties().get("X2") + X - firstX);
                            z.put("Y2", selected.getProperties().get("Y2") + firstY - Y);
                            z.put("Y3", selected.getProperties().get("Y3"));
                            z.put("X3", selected.getProperties().get("X3"));
                            selected.setProperties(z);
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                            inv = null;

                        }

                    }
                    if (isInsideResizeRectangle(firstX, 720 - firstY, selected.getProperties().get("X3").intValue() - 4, selected.getProperties().get("Y3").intValue() - 4)) {

                        if (!notClicked) {
                            isBeingResized = true;
                            inv = selected;
                            g.drawLine(selected.getPosition().x, selected.getPosition().y, selected.getProperties().get("X3").intValue() + X - firstX, selected.getProperties().get("Y3").intValue() + firstY - Y);
                            g.drawLine(selected.getProperties().get("X3").intValue() + X - firstX, selected.getProperties().get("Y3").intValue() + firstY - Y, selected.getProperties().get("X2").intValue(), selected.getProperties().get("Y2").intValue());
                            g.drawLine(selected.getProperties().get("X2").intValue(), selected.getProperties().get("Y2").intValue(), selected.getPosition().x, selected.getPosition().y);

                        } else {
                            Map<String, Double> z = new HashMap<>();
                            z.put("X3", selected.getProperties().get("X3") + X - firstX);
                            z.put("Y3", selected.getProperties().get("Y3") + firstY - Y);
                            z.put("Y2", selected.getProperties().get("Y2"));
                            z.put("X2", selected.getProperties().get("X2"));
                            selected.setProperties(z);
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                            inv = null;

                        }

                    }


                    if (isInsideResizeRectangle(firstX, 720 - firstY, selected.getPosition().x - 4, selected.getPosition().y - 4)) {

                        if (!notClicked) {
                            isBeingResized = true;
                            inv = selected;
                            g.drawLine(selected.getProperties().get("X2").intValue(), selected.getProperties().get("Y2").intValue(), selected.getPosition().x + X - firstX, selected.getPosition().y + firstY - Y);
                            g.drawLine(selected.getProperties().get("X2").intValue(), selected.getProperties().get("Y2").intValue(), selected.getProperties().get("X3").intValue(), selected.getProperties().get("Y3").intValue());
                            g.drawLine(selected.getProperties().get("X3").intValue(), selected.getProperties().get("Y3").intValue(), selected.getPosition().x + X - firstX, selected.getPosition().y + firstY - Y);

                        } else {
                            Point p = new Point(selected.getPosition().x + X - firstX, selected.getPosition().y + firstY - Y);
                            selected.setPosition(p);
                            this.firstX = firstX;
                            this.firstY = firstY;
                            this.X = X;
                            this.Y = Y;
                            isBeingResized = false;
                            inv = null;

                        }

                    }

                }

            }
        }
    }

    private void shapeMover(Graphics g, int firstX, int firstY, int X, int Y, boolean notClicked) {
        shapeDetector(firstX,firstY);
        if(!isBeingResized) {

            if (isDrawn(firstX, firstY, X, Y)) {
                Shape temp2 = selected;
                if (temp != null && temp.getClass() == rectangle.class) {
                    invisible.add(temp);
                    if (!notClicked) {
                        newX = temp.getPosition().x + (X - firstX);
                        newY = temp.getPosition().y + (firstY - Y);
                        g.drawRect(newX, newY, temp.getProperties().get("length").floatValue(), temp.getProperties().get("width").floatValue());
                        flag = false;
                        selected = null;
                    } else {
                        positionSetter(firstX, firstY, X, Y);
                        flag = true;
                        selected = temp2;
                    }
                }
                if (temp != null && temp.getClass() == square.class) {
                    invisible.add(temp);
                    if (!notClicked) {
                        newX = temp.getPosition().x + (X - firstX);
                        newY = temp.getPosition().y + (firstY - Y);
                        g.drawRect(newX, newY, temp.getProperties().get("length").floatValue(), temp.getProperties().get("length").floatValue());
                        flag = false;
                        selected = null;
                    } else {
                        positionSetter(firstX, firstY, X, Y);
                        flag = true;
                        selected = temp2;
                    }
                }
                if (temp != null && temp.getClass() == circle.class) {
                    invisible.add(temp);
                    if (!notClicked) {
                        newX = temp.getPosition().x + (X - firstX);
                        newY = temp.getPosition().y + (firstY - Y);
                        g.drawOval(newX, newY, temp.getProperties().get("radius").floatValue(), temp.getProperties().get("radius").floatValue());
                        flag = false;
                        selected = null;
                    } else {
                        positionSetter(firstX, firstY, X, Y);
                        flag = true;
                        selected = temp2;
                    }
                }
                if (temp != null && temp.getClass() == triangle.class) {
                    invisible.add(temp);
                    if (!notClicked) {
                        newX = (X - firstX);
                        newY = (firstY - Y);
                        g.drawLine(temp.getPosition().x + newX, temp.getPosition().y + newY, temp.getProperties().get("X2").intValue() + newX, temp.getProperties().get("Y2").intValue() + newY);
                        g.drawLine(temp.getProperties().get("X2").intValue() + newX, temp.getProperties().get("Y2").intValue() + newY, temp.getProperties().get("X3").intValue() + newX, temp.getProperties().get("Y3").intValue() + newY);
                        g.drawLine(temp.getPosition().x + newX, temp.getPosition().y + newY, temp.getProperties().get("X3").intValue() + newX, temp.getProperties().get("Y3").intValue() + newY);
                        flag = false;
                        selected = null;
                    } else {
                        temp.setPosition(new Point(temp.getPosition().x + newX, temp.getPosition().y + newY));
                        Map<String, Double> m = new HashMap<>();
                        m.put("X2", temp.getProperties().get("X2") + newX);
                        m.put("Y2", temp.getProperties().get("Y2") + newY);
                        m.put("X3", temp.getProperties().get("X3") + newX);
                        m.put("Y3", temp.getProperties().get("Y3") + newY);
                        temp.setProperties(m);
                        this.firstX = firstX;
                        this.firstY = firstY;
                        this.X = X;
                        this.Y = Y;
                        invisible = new ArrayList<>();
                        temp = null;
                        flag = true;
                        selected = temp2;
                    }
                }
                if (temp != null && temp.getClass() == ellipse.class) {
                    invisible.add(temp);
                    if (!notClicked) {
                        newX = temp.getPosition().x + (X - firstX);
                        newY = temp.getPosition().y + (firstY - Y);
                        g.drawOval(newX, newY, temp.getProperties().get("majorRadius").floatValue(), temp.getProperties().get("minorRadius").floatValue());
                        flag = false;
                        selected = null;
                    } else {
                        positionSetter(firstX, firstY, X, Y);
                        flag = true;
                        selected = temp2;
                    }

                }
                if (temp != null && temp.getClass() == lineSegment.class) {
                    invisible.add(temp);
                    float newX2, newY2;
                    if (!notClicked) {
                        newX = temp.getPosition().x + (X - firstX);
                        newY = temp.getPosition().y + (firstY - Y);
                        newX2 = temp.getProperties().get("endX").intValue() + (X - firstX);
                        newY2 = temp.getProperties().get("endY").intValue() + (firstY - Y);
                        g.drawLine(newX, newY, newX2, newY2);
                        flag = false;
                        selected = null;
                    } else {
                        Map<String, Double> z = new HashMap<>();
                        z.put("endX", temp.getProperties().get("endX") + (X - firstX));
                        z.put("endY", temp.getProperties().get("endY") + (firstY - Y));
                        temp.setProperties(z);
                        positionSetter(firstX, firstY, X, Y);
                        flag = true;
                        selected = temp2;
                    }
                }

                /*else{this.firstX=firstX;
                    this.firstY=firstY;
                    this.X=X;
                    this.Y=Y;
                    invisible=new ArrayList<>();
                }*/
            }
        }
    }

    private void positionSetter(int firstX, int firstY, int X, int Y) {

        this.firstX=firstX;
        this.firstY=firstY;
        this.X=X;
        this.Y=Y;
        invisible=new ArrayList<>();
        temp.setPosition(new Point(newX, newY));
        temp=null;
    }


    private boolean isDrawn(int firstX,int firstY,int X,int Y){
        return firstX != this.firstX || firstY != this.firstY || X != this.X || Y != this.Y;

    }

    void realtimeDrawer(Graphics g, char s, int firstX, int firstY, int X, int Y, boolean notClicked, int mousex, int mousey)  {
        int max = Math.max(X - firstX, ((720 - Y) - (720 - firstY)));


        if(s!='s'){selected=null;}
        shapeLimitAdder(g);
        switch (s){

            case 's':
                shapeResize(g,firstX,firstY,X,Y,notClicked);
                shapeMover(g,firstX,firstY,X,Y,notClicked);
                break;

            case 'l':
                if(!notClicked&&isDrawn(firstX,firstY,X,Y)){g.drawLine(firstX,720-firstY,X,720-Y);}
                if(notClicked&&isDrawn(firstX,firstY,X,Y)){
                    engine.addShape(new lineSegment(firstX,720-firstY,X,720-Y, color,fillColor));
                    this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;
                }
            break;



            case'r':if(!notClicked&&isDrawn(firstX,firstY,X,Y)){
                        g.drawRect(firstX,720-firstY,X-firstX,(720-Y)-(720-firstY));
                    }
                    if(notClicked&&isDrawn(firstX,firstY,X,Y)){
                        engine.addShape(new rectangle(firstX,720-firstY,X-firstX,(720-Y)-(720-firstY), color,fillColor));
                        this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;
                    }
            break;



            case'c':
                if(!notClicked&&isDrawn(firstX,firstY,X,Y))g.drawRect(firstX,720-firstY,max,max);
                if(notClicked&&isDrawn(firstX,firstY,X,Y)) {
                    engine.addShape(new square(firstX,720-firstY, max, color,fillColor));
                    this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;
                }

            break;




            case't':
                if(!notClicked&&isDrawn(firstX,firstY,X,Y)){
                    g.drawLine(firstX,720-firstY,X,720-Y);g.drawLine(X,720-Y,firstX,((720-Y)+(720-firstY))/2f);
                    g.drawLine(firstX,720-firstY,firstX,((720-Y)+(720-firstY))/2f);
                }
                if(notClicked&&isDrawn(firstX,firstY,X,Y)) {
                    engine.addShape(new triangle(firstX,720-firstY,X,720-Y,firstX,((720-Y)+(720-firstY))/2,color,fillColor));
                    this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;
                }
            break;




            case'd':if(!notClicked&&isDrawn(firstX,firstY,X,Y))g.drawOval(firstX,720-firstY, max, max);

                if(notClicked&&isDrawn(firstX,firstY,X,Y)) {
                    engine.addShape(new circle(firstX,720-firstY, max,color,fillColor));
                    this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;

                }
            break;






            case'e':if(!notClicked&&isDrawn(firstX,firstY,X,Y)){
                g.drawOval(firstX,720-firstY,X-firstX,(720-Y)-(720-firstY));

            }
                if(notClicked&&isDrawn(firstX,firstY,X,Y)) {
                    engine.addShape(new ellipse(firstX,720-firstY,X-firstX,(720-Y)-(720-firstY),color,fillColor));
                    this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;
                }
            break;

            case'f':
                if(!notClicked&&isDrawn(firstX,firstY,X,Y)){
                    freehandLine z=new freehandLine(firstX,720-firstY,firstX,firstY,color,fillColor, new ArrayList<>());
                    if((!(720-mousey>10&&720-mousey<30&&((mousex>1150&&mousex<1170)||(mousex>1180&&mousex<1200))))&&(!(mousex<41&&mousey>60&&mousey<446))){


                        engine.addShape(z);
                        z.addPoint(mousex, 720 - mousey);
                        if(prevY!=-1&&prevX!=-1){
                            if(Math.sqrt(Math.pow(mousex-prevX,2)+Math.pow(mousey-prevY,2))>4){int inc=(int)Math.sqrt(Math.pow(mousex-prevX,2)+Math.pow(mousey-prevY,2));
                                for(int i=0;i<inc/4;i++){
                                    System.out.println("daddy"+i);
                                    z.addPoint(prevX+ ((mousex-prevX)*4/inc) *(i+1), 720 - prevY+ ((prevY-mousey)*4/inc) *(i+1));
                                }
                            }
                        }
                        prevY=mousey;prevX=mousex;
                    }
                }
               else if(notClicked&&isDrawn(firstX,firstY,X,Y)){this.firstX=firstX;this.firstY=firstY;this.X=X;this.Y=Y;prevX=-1;prevY=-1;}
                break;
            default:

        }
    }

    void drawer(Graphics g){
        arr=engine.getShapes();
        g.setColor(org.newdawn.slick.Color.black);
        for (Shape shape : arr) {
            if ((invisible.isEmpty()||!invisible.contains(shape))&&inv!=shape) {
                if (shape.getClass() == rectangle.class) {

                    org.newdawn.slick.geom.Rectangle rectangle=new org.newdawn.slick.geom.Rectangle(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("length").floatValue(), shape.getProperties().get("width").floatValue());
                    g.draw(rectangle);
                    org.newdawn.slick.Color z=new org.newdawn.slick.Color(shape.getFillColor().getRGB());
                    g.setColor(z);
                    g.fill(rectangle);
                    g.setColor(new org.newdawn.slick.Color( shape.getColor().getRGB()));
                    g.drawRect(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("length").floatValue(), shape.getProperties().get("width").floatValue());

                } else if (shape.getClass() == lineSegment.class) {g.setColor(new org.newdawn.slick.Color(shape.getColor().getRGB()));
                    g.drawLine((float) shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("endX").floatValue(), shape.getProperties().get("endY").floatValue());

                } else if (shape.getClass() == square.class) {
                    org.newdawn.slick.geom.Rectangle rectangle=new org.newdawn.slick.geom.Rectangle(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("length").floatValue(), shape.getProperties().get("length").floatValue());
                    org.newdawn.slick.Color z=new org.newdawn.slick.Color(shape.getFillColor().getRGB());
                    g.setColor(z);
                    g.fill(rectangle);
                    g.setColor(new org.newdawn.slick.Color( shape.getColor().getRGB()));
                    g.drawRect(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("length").floatValue(), shape.getProperties().get("length").floatValue());

                } else if (shape.getClass() == circle.class) {
                    Circle circe=new Circle(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("radius").floatValue()/2);
                    circe.setLocation(shape.getPosition().x, shape.getPosition().y);
                    g.setColor(new org.newdawn.slick.Color(shape.getFillColor().getRGB()));
                    g.fill(circe);
                    g.setColor(new org.newdawn.slick.Color( shape.getColor().getRGB()));
                    g.drawOval(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("radius").floatValue(), shape.getProperties().get("radius").floatValue());

                } else if (shape.getClass() == ellipse.class) {
                    Ellipse temp=new Ellipse(1,2,Math.round(shape.getProperties().get("majorRadius")/2),Math.round(shape.getProperties().get("minorRadius")/2));
                    temp.setLocation(shape.getPosition().x,shape.getPosition().y);
                    g.setColor(new org.newdawn.slick.Color(shape.getFillColor().getRGB()));
                    g.fill(temp);
                    g.setColor(new org.newdawn.slick.Color( shape.getColor().getRGB()));
                    g.drawOval(shape.getPosition().x, shape.getPosition().y, shape.getProperties().get("majorRadius").floatValue(), shape.getProperties().get("minorRadius").floatValue());


                } else if (shape.getClass() == triangle.class) {
                    org.newdawn.slick.geom.Polygon tri=new org.newdawn.slick.geom.Polygon();
                    tri.addPoint(shape.getPosition().x, shape.getPosition().y);
                    tri.addPoint(shape.getProperties().get("X2").floatValue(), shape.getProperties().get("Y2").floatValue());
                    tri.addPoint(shape.getProperties().get("X3").floatValue(), shape.getProperties().get("Y3").floatValue());
                    g.setColor(new org.newdawn.slick.Color(shape.getFillColor().getRGB()));
                    g.fill(tri);
                    g.setColor(new org.newdawn.slick.Color(shape.getColor().getRGB()));
                    g.draw(tri);

                }
                else if(shape.getClass()==freehandLine.class){
                    freehandLine z=(freehandLine) shape;
                    g.setColor(new org.newdawn.slick.Color(shape.getColor().getRGB()));


                    for(int i=0;i<z.getPoints().length;i++){
                        g.fillOval(z.getPoints()[i].x,z.getPoints()[i].y,15,15);
                    }

                }



            }
        }
    }

}
