/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 *
 * @author testo4218
 */


// make sure you rename this class if you are doing a copy/paste
public class FinalProject extends JComponent implements KeyListener{

    int[] Numx = new int[100];
    int[] Numy = new int[100];

    // Height and Width of our game
    static final int WIDTH =1000;
    static final int HEIGHT =1000;
        boolean right = false, left = false; 
int changeWallCoordinates = 0;
    int camy =0;
    int dy = 0;
int randNumx = 0;
int randNumy = 0;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
    Rectangle player = new Rectangle (500, 1100, 50 ,50);
    int playerSpeed = 10;
    
    BufferedImage mazePic = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
    
 
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        Graphics gPic = mazePic.getGraphics();
        gPic.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        Color myColour1 = new Color(247, 219, 158);

        // draw maze
        gPic.setColor(myColour1);
        gPic.fillRect(0, 0, WIDTH, HEIGHT);
     
        gPic.setColor(Color.BLUE);
        gPic.fillRect(player.x, player.y-camy, player.width, player.height);
        


         gPic.setColor(Color.RED);
         for(int i = 1; i < Numx.length; i++)
         {
             int[] xSpotsLeftside = {0, Numx[i-1], Numx[i], 0 };
             int[] ySpotsLeftside = {Numy[i-1] - camy, Numy[i-1] - camy,Numy[i] - camy, Numy[i] - camy};
             gPic.fillPolygon(xSpotsLeftside, ySpotsLeftside, 4);
         }
          gPic.setColor(Color.RED);
//         //for(int i = 1; i < Numx.length; i++)
//         {
//             int[] xSpotsRightside = {0+50, Numx[i+1]+50, Numx[i]+50, 0+50 };
//             int[] ySpotsRightside = {Numy[i+1] - camy+50, Numy[i+1] - camy+50,Numy[i] - camy+50, Numy[i] - camy+50};
//             gPic.fillPolygon(xSpotsRightside, ySpotsRightside, 4);
//         }
         //gPic.fillPolygon(WIDTH,randNumx,HEIGHT -camy, randNumy);

        
        //show the maze
        g.drawImage(mazePic, 0, 0, this);
        
        
        //draw player
       
       
        
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        
        redWalls();
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
            
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
           // if(mazePic.getRGB(110, 300) == Color.red.getRGB())
            //{
                //System.out.println("hit");
            //}
    //When player's y position gains another 200
            //Randomly set new x and y coordinates for walls
            
            if (player.y >= changeWallCoordinates)
            {
               changeWallCoordinates += 200;
                 redWalls();
            }
   // player moving right
            if(right)
            {
                player.x += playerSpeed;
                //player moving left
            }else if(left)
            {
                 player.x -= playerSpeed;
            }
  
            //player always moving up
            player.y--;  
//            //camera follows player
           camy = player.y - HEIGHT + 150;

            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        FinalProject game = new FinalProject();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        frame.addKeyListener(game);
        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
         int key = e.getKeyCode();
    
         if (key == KeyEvent.VK_RIGHT)
        {
            right = true;
        
        }
        if (key == KeyEvent.VK_LEFT)
        {
            left = true;
            
        }
       
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
         int key = e.getKeyCode();
    
         if (key == KeyEvent.VK_RIGHT)
        {
            right = false;
        
        }
        if (key == KeyEvent.VK_LEFT)
        {
            left = false;
            
        }
       
    }
    
     public void redWalls(){

         for(int i = 0; i < Numx.length; i++)
         {
            randNumx = (int)(Math.random()*((600 - 70) - 1 + 1))+ 1;

            Numx[i] = randNumx;
            Numy[i] = player.y - 100 - i*100;
         }

    }
     
    }

