
/* Import all of the required packages */
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Dodgeball extends JPanel implements ActionListener, KeyListener {

	/* Declaring all of the required variables */
	private JFrame frame;
	private Timer startTimer, gameTimer, playerTimer, enemyTimer, ballTimer, spawnTimer;
	private ImageIcon startImg, backgroundImg, iconImg, ballImg, loseImg, winImg;
	private boolean startDone, moveUp, moveDown, collision, playerLost, playerWon;
	private int time, colourChoice, xPosBall, yPosBall;
	private Ellipse2D ballMask;
	private Rectangle2D playerMask;
	private Player player = new Player();
	private Enemy enemy = new Enemy();
	private Font font;
	private FontMetrics fm;

	/* Creates the constructor to initiliaze variables and frame */
	public Dodgeball() {

		/* Initializing all of the required ImageIcons */
		startImg = new ImageIcon("images/Title_Page.jpg");
		backgroundImg = new ImageIcon("images/Background.jpg");
		iconImg = new ImageIcon("images/MenuIcon.png");
		ballImg = new ImageIcon("images/Ball.png");
		loseImg = new ImageIcon("images/Lose.jpg");
		winImg = new ImageIcon("images/Winner.jpg");

		/* Creating the JFrame and initializing its properties */
		frame = new JFrame();
		frame.setSize(700, 700);
		frame.setTitle("DODGEBALL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		/* Initializing all of the required Timers */
		startTimer = new Timer(3000, this);
		ballTimer = new Timer(10, this);
		gameTimer = new Timer(1000, this);
		spawnTimer = new Timer(2000, this);
		playerTimer = new Timer(10, this);
		enemyTimer = new Timer(10, this);

		/* Initializing the time remaining */
		time = 20;

		/* Initializing the X and Y coordinates for the ball*/
		xPosBall = 700;
		yPosBall = 350;

		/* Initializing the X and Y coordinates for the player and enemy */
		player.setY(350);
		enemy.setLocation(700 - enemy.getImage().getIconWidth(), 0);
		
		/* Initializing the entity masks */
		ballMask = new Ellipse2D.Double(xPosBall, yPosBall, ballImg.getIconWidth(), ballImg.getIconHeight());
		playerMask = new Rectangle2D.Double(player.getX(), player.getY(), player.getImage().getIconWidth(), player.getImage().getIconHeight());

		/* Initializing the main font using try-catch */
		try {

			/* Using the "emulogic" font from the folder */
			font = Font.createFont(Font.TRUETYPE_FONT, new File("images/emulogic.ttf")).deriveFont(30f);

			/* Creating the GraphicsEnvironment variable */
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			/* Registering the font for the GraphicsEnvironment variable */
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("images/emulogic.ttf")));

			/* Storing the font metrics data into the variable 'fm' */
			fm = getFontMetrics(font);
		} catch (Exception e) {}

		/* Required methods for KeyListener */
		addKeyListener(this);
		setFocusable(true);
		requestFocus();

		/* Starting the initial timer */
		startTimer.start();
	}

	/* The main method where the initial code is executed */
	public static void main(String[] args) {

		/* Call the main constructor */
		new Dodgeball();
	}

	/* The paint method used to draw the graphical user interface */
	public void paint(Graphics g) {

		/* Initialize the Graphics2D object and repaint the frame */
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		/* Set the font of the Graphics2D object */
		g2.setFont(font);

		/* Checking if the player has lost */
		if (playerLost) {

			/* Draws the losing image */
			g2.drawImage(loseImg.getImage(), 0, 0, this);
		}

		/* Checking if the player has won */
		else if (playerWon) {

			/* Displaying the winning JOptionPane */
			JOptionPane.showMessageDialog(null, "Congratulations!\nYou survived and won the game!", 
					"DODGEBALL", JOptionPane.PLAIN_MESSAGE, iconImg);

			/* Draws the winning image */
			g2.drawImage(winImg.getImage(), 0, 0, this);
		}

		/* Checking if the player has not lost */
		else {

			/* Checking if the starting phase is not complete */
			if (!startDone) {

				/* Draws the starting image */
				g2.drawImage(startImg.getImage(), 0, 0, this);
			}

			/* Checking if the starting phase is complete */
			else if (startDone) {

				/* Start the game timer */
				gameTimer.start();

				/* Draws the background image */
				g2.drawImage(backgroundImg.getImage(), 0, 0, this);

				/* Draws the time remaining */
				g2.drawString("TIME: " + time, 200, 40);

				/* Drawing all of the moving entities */
				g2.drawImage(player.getImage().getImage(), player.getX(), player.getY(), this);
				g2.drawImage(enemy.getImage().getImage(), enemy.getX(), enemy.getY(), this);
				g2.drawImage(ballImg.getImage(), xPosBall, yPosBall, this);
			}
		}

		/* Repaints the frame's components */
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		/* Checking if the starting timer's interval has elapsed */
		if (e.getSource() == startTimer) {

			/* Declaring and initializing the string array of buttons*/
			String[] buttons = { "BLUE", "GREEN" };

			/* Asking the user for their choice of colour */
			int colourChoice = JOptionPane.showOptionDialog(null, "Select your player colour:", "DODGEBALL",
					JOptionPane.WARNING_MESSAGE, 0, iconImg, buttons, null);

			/* If the player chooses the first colour (blue) */
			if (colourChoice == 1) 
				player.setImage(1);

			/* If the player chooses the second colour (green) */
			else if (colourChoice == 2)
				player.setImage(2);

			/* Stopping the starting timer and setting startDone to true */
			startTimer.stop();
			startDone = true;
			
			ballTimer.start();
			playerTimer.start();
			enemyTimer.start();
			spawnTimer.start();
		}

		/* Checking if the game timer's interval has elapsed */
		if (e.getSource() == gameTimer) {

			/* Checking if the time has reached 0 */
			if (time == 0) {

				/* Set the playerWon variable to true */
				playerWon = true;
			}

			/* Decrease the time by 1 second */
			time -= 1;
		}

		if (e.getSource() == playerTimer)
		{
			/* Checking if an intersection occcurs */
			if (ballMask.intersects(playerMask)) {

				/* Stop all of the current timers */
				startTimer.stop();
				ballTimer.stop();
				playerTimer.stop();
				enemyTimer.stop();
				spawnTimer.stop();
				gameTimer.stop();

				/* Set the playerLost variable to true */
				playerLost = true;
			}

			if (moveUp == true)
			{
				player.setDirection(0);

				if (player.getY()>= 0)
				{
					player.move();
				}

				playerMask = new Rectangle2D.Double(player.getX(), player.getY(), player.getImage().getIconWidth(), player.getImage().getIconHeight());
			}
			else if (moveDown == true)
			{
				player.setDirection(1);

				if (player.getY() + player.getImage().getIconHeight()+ 45 <= frame.getHeight())
				{
					player.move();
				}

				playerMask = new Rectangle2D.Double(player.getX(), player.getY(), player.getImage().getIconWidth(), player.getImage().getIconHeight());
			}

		}

		if (e.getSource() == enemyTimer)
		{
			// Boundary checking for the Pizza box on the BOTTOM of the window. 
			if ((enemy.getY() + 40) + enemy.getImage().getIconHeight() >= frame.getHeight())
			{
				// Change the direction to UP so that the box now moves up. 
				enemy.setDirection(0);
			}
			// Check if the pizza box hits the TOP of the window. 
			else if (enemy.getY() <= 0)
			{
				// Change the direction to DOWN so the box moves down. 
				enemy.setDirection(1);
			}
			
			if (time >= 10)
				enemy.move(10);
			else
				enemy.move(11);

		}

		/* Checking if the spawn timer's interval has elapsed */
		if (e.getSource() == spawnTimer) {

			yPosBall = enemy.getY();
			
			/* Start the ball timer */
			ballTimer.start();
		}

		/* Checking if the ball timer's interval has elapsed */
		if (e.getSource() == ballTimer) {

			/* Checking if the ball is not yet visible */
			if (xPosBall + ballImg.getIconWidth() <= 0) {

				/* Setting the ball's X-position to the enemy's X-position */
				xPosBall = enemy.getX() + ballImg.getIconWidth();

				/* Update the ball mask with the new position */
				ballMask = new Ellipse2D.Double(xPosBall, yPosBall, ballImg.getIconWidth(), ballImg.getIconHeight());

				/* Stop the ball timer */
				ballTimer.stop();
			}

			/* Decreases the X-coordinate of the ball */
			if (time >= 10)
				xPosBall -= 15;
			else
				xPosBall -= 17;

			/* Update the ball mask after the position has changed */
			ballMask = new Ellipse2D.Double(xPosBall, yPosBall, ballImg.getIconWidth(), ballImg.getIconHeight());
		}

	}


	/* The keyPressed method checks when the keyboard is pressed */
	public void keyPressed(KeyEvent e) {

		/* Checking if the up arrow key is pressed */
		if (e.getKeyCode() == KeyEvent.VK_UP) {

			/* Set the moveUp boolean to true */
			moveUp = true;
		}
		/* Checking if the down arrow key is pressed */
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			/* Set the moveDown boolean to true */
			moveDown = true; 
		}	
	}

	/* The keyReleased method checks when the keyboard is released */
	public void keyReleased(KeyEvent e) {

		/* Checking if the up arrow key is released */
		if (e.getKeyCode() == KeyEvent.VK_UP) {

			/* Set the moveUp boolean to false */
			moveUp = false;
		}
		/* Checking if the down arrow key is released */
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

			/* Set the moveDown boolean to false */
			moveDown = false; 
		}	

	}

	/* Unimplemented method from KeyListener */
	public void keyTyped(KeyEvent e) {}

}
