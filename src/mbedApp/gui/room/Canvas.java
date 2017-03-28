package mbedApp.gui.room;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kölling (mik)
 *
 * @version 2016.02.29
 */
public class Canvas
{
    //  ----- instance part -----


    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, Drawable> items;
    
    /**
     * Create a Canvas.
     * @param width    the desired width for the canvas
     * @param height   the desired height for the canvas
     * @param bgColor the desired background color of the canvas
     */
    public Canvas(int width, int height, Color bgColor)
    {
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColor = bgColor;
        objects = new ArrayList<Object>();
        items = new HashMap<Object, Drawable>();
    }

    public CanvasPane getCanvas() {
        return canvas;
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false)
     */
    public void setVisible(boolean visible)
    {
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background color
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, Color color, Shape shape)
    {
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        items.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
    
    public void drawText(Object referenceObject, String text, int x, int y) 
    {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        items.put(referenceObject, new TextDescription(text, x, y));
        redraw();
    }
    
    public HashMap getShapes(){
        return items;
    }
    
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject)
    {
        objects.remove(referenceObject);   // just in case it was already there
        items.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground color of the Canvas.
     * @param  color   the new color for the foreground of the Canvas
     */
    public void setForegroundColor(Color color)
    {
        graphic.setColor(color);
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        } 
        catch (Exception e)
        {
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw()
    {
        erase();
        for(Object o : objects) {
            items.get(o).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel
    {
        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription implements Drawable
    {
        private Shape shape;
        private Color color;

        public ShapeDescription(Shape shape, Color color)
        {
            this.shape = shape;
            this.color = color;
        }

        @Override
        public void draw(Graphics2D graphic)
        {
            setForegroundColor(color);
            graphic.fill(shape);
        }
    }
    
    private class TextDescription implements Drawable
    {
        private String text;
        private int x;
        private int y;
        
        public TextDescription(String text, int x, int y) 
        {
            this.text = text;
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Graphics2D graphic)
        {
            graphic.setFont(new Font("Monospace", Font.BOLD, 18));
            graphic.drawString(text, x, y);
        }
    }

    private interface Drawable{

        void draw(Graphics2D graphic);
    }
}
