
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class particles extends JFrame {
	 // Define named-constants
	   private static final int CANVAS_WIDTH = 640;
	   private static final int CANVAS_HEIGHT = 480;
	   private static final int UPDATE_INTERVAL = 25; // milliseconds
	 
	   private DrawCanvas canvas;  // the drawing canvas (an inner class extends JPanel)
	 
	   // Attributes of moving object
	   private int x = 30;     // top-left (x, y)
	   private int y = 10;
	   private int size = 25;  // width and height
	   private int xSpeed = 2;  // moving speed in x and y directions
	   private int ySpeed = 3;  // displacement per step in x and y
	 
	   // Constructor to setup the GUI components and event handlers
	   public particles() {
	      canvas = new DrawCanvas();
	      canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	      this.setContentPane(canvas);
	      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	      this.pack();
	      this.setTitle("Particles");
	      this.setVisible(true);
	 
	      // Create a new thread to run update at regular interval
	      Thread updateThread = new Thread() {
	         @Override
	         public void run() {
	            while (true) {
	               update();   // update the (x, y) position
	               repaint();  // Refresh the JFrame. Called back paintComponent()
	               try {
	                  // Delay and give other thread a chance to run
	                  Thread.sleep(UPDATE_INTERVAL);  // milliseconds
	               } catch (InterruptedException ignore) {}
	            }
	         }
	      };
	      updateThread.start(); // called back run()
	   }
	 
	   // Update the (x, y) position of the moving object
	   public void update() {
	      x += xSpeed;
	      y += ySpeed;
	      if (x > CANVAS_WIDTH - size || x < 0) {
	         xSpeed = -xSpeed;
	      }
	      if (y > CANVAS_HEIGHT - size || y < 0) {
	         ySpeed = -ySpeed;
	      }
	   }
	 
	   // Define Inner class DrawCanvas, which is a JPanel used for custom drawing
	   class DrawCanvas extends JPanel {
	      @Override
	      public void paintComponent(Graphics g) {
	         super.paintComponent(g);  // paint parent's background
	         setBackground(Color.BLACK);
	         g.setColor(Color.BLUE);
	         g.fillOval(x, y, size, size);  // draw a circle
	      }
	   }
	 
	   // The entry main method
	   public static void main(String[] args) {
	      // Run GUI codes in Event-Dispatching thread for thread safety
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            new particles(); // Let the constructor do the job
	         }
	      });
	   }
	}
