package interfacesAndImplementations;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class drawingEngineImpl implements DrawingEngine {

    private ArrayList<Shape> saved= new ArrayList<>();


    public void refresh(Graphics canvas) {
        for (Shape shape : saved) {
            shape.draw(canvas);
        }
    }


    public void addShape(Shape shape) {
        saved.add(shape);
    }


    public void removeShape(Shape shape) {
        for (int i=0;i<saved.size();i++){
            if(saved.get(i).equals(shape)){saved.remove(i);}
        }
    }


    public void updateShape(Shape oldShape, Shape newShape) {
        for (int i=0;i<saved.size();i++){
            if(saved.get(i).equals(oldShape)){saved.set(i,newShape);}
        }
    }


    public Shape[] getShapes() {
       Shape[] temp=new Shape[saved.size()];
        for (int i=0;i<saved.size();i++){
            temp[i]=saved.get(i);
        }
        return temp;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return null;
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void save(String path) {

    }

    @Override
    public void load(String path) {

    }
}
