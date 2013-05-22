import com.jogamp.opengl.util.Animator;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.swing.JOptionPane;

public class Main {
   
    public static final String title = "AFK Arena";
    
    public static final JFrame jframe = new JFrame(title);
    
    public static void setTitle(String str)
    {
        jframe.setTitle(title + " - " + str);
    }
    
    public static void main( String [] args ) {
        final GLProfile glprofile = GLProfile.getDefault();
        final GLCapabilities glcapabilities = new GLCapabilities( glprofile );
        glcapabilities.setDoubleBuffered(true);
        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
        glcanvas.setPreferredSize(new Dimension(Athens.WINDOW_WIDTH, Athens.WINDOW_HEIGHT));
        
        final Athens app = new Athens();

        jframe.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent windowevent ) {
                jframe.dispose();
                System.exit( 0 );
            }
        });
        jframe.setResizable(false);
        
        Animator animator = new Animator(glcanvas);
        
        glcanvas.addKeyListener(new KeyAdapter()
        {

            @Override
            public void keyPressed(KeyEvent e) {
                app.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                app.keyReleased(e);
            }
            
        });
        
        MouseAdapter mouse = new MouseAdapter()
        {
            int cx = glcanvas.getWidth()/2;
            int cy = glcanvas.getHeight()/2;

            @Override
            public void mousePressed(MouseEvent e) {

                cx = e.getX();
                cy = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (e.getX() == cx && e.getY() == cy) return;

                app.mouseMoved(cx-e.getX(), cy-e.getY());

                cx = e.getX();
                cy = e.getY();
            }
        };
        
        glcanvas.addMouseListener(mouse);
        glcanvas.addMouseMotionListener(mouse);

        glcanvas.addGLEventListener( new GLEventListener() {
            
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                app.reshape( glautodrawable.getGL().getGL2(), width, height );
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
                GL gl = glautodrawable.getGL().getGL2();
                System.out.println("OpenGL Version: " + gl.glGetString(GL.GL_VERSION));
                app.init( gl.getGL2() );
            }
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
                app.cleanUp( glautodrawable.getGL().getGL2() );
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                app.display( glautodrawable.getGL().getGL2());
            }
        });

        jframe.getContentPane().add( glcanvas, BorderLayout.CENTER );
        //jframe.setSize( 1280, 768 );
        jframe.pack();
        jframe.setVisible( true );
        
        animator.start();
    }
    
}
