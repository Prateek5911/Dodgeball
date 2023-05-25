import javax.swing.ImageIcon;

public class Enemy {
	
	// Declaring the private variables and fields
	private ImageIcon imgEnemy;
	private int xPos, yPos, dir;
	private final int UP = 0, DOWN = 1;

	// Constructor to initialize the enemy object 
	public Enemy () {

		// Intialize the x and y positions to 0 
		xPos = 0;
		yPos = 0;
		imgEnemy = new ImageIcon ("images/Enemy_Player.png");			

	}

	// Sets the location of the enemy to the specificed x and y coordinates
	public void setLocation (int x, int y) {

		xPos = x;
		yPos = y;
	}

	// Returns the x position of the enemy
	public int getX() {
		return xPos;
	}

	
	// Returns the y position of the enemy
	public int getY() {
		return yPos;
	}

	// Moves the enemy based on its direction and speed 
	public int move(int speed) {
		
		if (dir == UP)
		{
			yPos -= speed;
		}

		else if (dir == DOWN)
		{
			yPos += speed;
		}

		return yPos;
	}

	// Returns the enemy image
	public ImageIcon getImage() {
		return imgEnemy;
	}

	// Sets the direction of the enemy movement
	public void setDirection(int yDir)
	{
		dir = yDir; 
	}

}