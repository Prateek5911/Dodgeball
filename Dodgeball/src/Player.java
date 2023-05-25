import javax.swing.*;

public class Player {

	// Declaring the private variables and fields
	private ImageIcon imgPlayer;
	private int xPos, yPos, dir;
	private final int UP = 0, DOWN = 1;


	public Player () {

		// Constructor to initialize the player object 
		imgPlayer = new ImageIcon("images/Blue_Player.png"); // Set the default player image 

		// Initialize x and y position to 0 
		xPos = 0;
		yPos = 0;

		dir = UP;

	}


	// Method to set the y position of the player 

	public void setY (int y) {
		yPos = y;
	}

	// Method to get the x position of the player 

	public int getX()
	{
		return xPos; 
	}

	// Method to get the 7 position of the player 

	public int getY()
	{
		return yPos;
	}


	// Method to move the player 
	public int move()
	{
		if (dir == UP)
		{
			yPos -= 10; // Move up by decreasing the y position
		}
		else if (dir == DOWN)
		{
			yPos += 10; // Move down by increasing the y position 
		}

		return yPos; // Return the updating y position 
	}

	// Method to set the direction of the player, up or down. 
	public void setDirection(int yDir)
	{
		dir = yDir; 
	}

	// Method to get the current image 
	public ImageIcon getImage()
	{
		return imgPlayer; 
	}


	// Method to set the image of the player based on the choice 
	public void setImage (int choice)
	{		

		if (choice == 1) {	
			imgPlayer = new ImageIcon ("images/Green_Player.png");

		}			

		else if (choice == 2) {
			imgPlayer = new ImageIcon("images/Blue_Player.png");
		}
	}

}


