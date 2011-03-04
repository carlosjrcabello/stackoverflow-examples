package de.fencing_game.paul.examples;

import de.fencing_game.gui.basics.TransformedStroke;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * Example program showing the Stroke distortion when using
 * scaling of the graphics, and how to avoid it by using my
 * {@link TransformedStroke}.
 *
 *<p>
 * <img src="doc-files/distorted-stroke.png" alt=""/>
 * <img src="doc-files/undistorted-stroke.png" alt="" />
 *</p>
 * @author Paŭlo Ebermann
 */
public class StrokeExample extends JPanel {
  
    private boolean transformed;

    StrokeExample(boolean trans) {
        this.transformed = trans;
    }


    public void paintComponent(Graphics context) {
        super.paintComponent(context);
        Graphics2D g = (Graphics2D)context.create();

        int height = getHeight();
        int width = getWidth();

        g.scale(width/4.0, height/7.0);

        Stroke trans = new BasicStroke(0.2f);
        if(transformed) {
            Stroke base = new BasicStroke(2f);
            try {
                trans = new TransformedStroke(base, g.getTransform());
            }
            catch(NoninvertibleTransformException ex) {
                ex.printStackTrace();
            }
        }
        g.setStroke(trans);

        g.setColor(Color.BLACK);
        g.draw(new Rectangle( 1, 2, 2, 4));
    }

    public static void main(String[] params) {
        EventQueue.invokeLater(new Runnable(){public void run() {
            
            StrokeExample[] examples = { new StrokeExample(false),
                                         new StrokeExample(true) };
            for(StrokeExample example : examples) {
                JFrame f =
                    new JFrame("StrokeExample" +
                               (example.transformed ? " transformed" : ""));
                f.setSize(320, 100);
                f.setLocation(example.transformed ? 340 : 0, 100);
                f.getContentPane().setLayout(new BorderLayout());
                f.getContentPane().add(example);
                f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                f.setVisible(true);
            }
        }});

    }

}