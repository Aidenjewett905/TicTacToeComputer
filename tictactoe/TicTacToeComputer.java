package tictactoe;

import java.util.Scanner;

import java.util.Random;

public class TicTacToeComputer {

	public static Scanner keyboard = new Scanner(System.in);
	
	public static Random random = new Random(); //For Computer's Algorithm
	
	public static void main(String[] args) {
		final int boardRow = 3, boardCol = 3, choiceLength = 2; //Rows and columns for the game board, and number of coords for the choice.
		final int player = 1; //player number, used to plug into methods that are player or computer
		final int computer = 2; //computer number for board
		int[][] board = new int[boardRow][boardCol]; //Create empty board, 0 is empty tile, 1 is player 1 filled, 2 is player 2 filled
		int[] choice = new int[choiceLength]; //Coordinates for the tile choice of the player. 1st number is row, 2nd is column
		boolean isFirstTurn = true; //Used for computer algorithm
		
		//Inform user of purpose of program
		System.out.println("This is a game of Tic Tac Toe. You need 3 tiles in a row to win, in either a straight or diagonal line.");
		
		//Get user to choose their icon
		char playerIcon = getPlayerIcon();
		
		keyboard.nextLine();
		
		//Create a loop that continues until the game is won
		//Continues while game is not won by player (1) and not won by computer (2), and not a tie
		while((!gameWon(board, player, playerIcon) && !gameWon(board, computer, playerIcon)) && !gameTied(board, playerIcon))
		{
			//Display the board
			System.out.println(board);

			//Player Turn
			
			do { //Create loop in case the user enters an invalid coordinate
			choice = getUserChoice(boardRow, boardCol); //input choice
			}while(!validTile(board, choice)); //Make sure inputted coordinates are valid
			
			board = setTile(board, choice, player); //Fill in the tile with the inputed coordinates, index 0 is row and index 1 is column.
			
			//Output players choice for board
			System.out.println(board);
			
			//Computer Turn
			
			if(!gameTied(board, playerIcon) && !gameWon(board, player, playerIcon)) //Make sure game is not over yet
			{
			board = computerTurn(board, isFirstTurn, computer, player);
			}
			
			//First turn over
			if(isFirstTurn)
				isFirstTurn = false;
			
		}
		
		keyboard.close();

	}
	
	//Method checks if the game has been won by the given player, and once won will output a message.
	public static boolean gameWon(int[][] board, int player, char playerIcon) {
		
		//Check for winning horizontally
		for(int r = 0; r<board.length; r++)
		{
			boolean IsWon = true; //Is this individual row won?
			for(int c = 0; c<board[r].length; c++)
			{
				if(board[r][c] != player)
					IsWon = false;
			}
			if(IsWon)
				return playerWins(player, board, playerIcon);
		}
		
		//Check for winning vertically
		for(int c = 0; c<board.length; c++)
		{
			boolean IsWon = true; //Is this individual column won?
			for(int r = 0; r<board.length; r++)
			{
				if(board[r][c] != player)
					IsWon = false;
			}
			if(IsWon)
				return playerWins(player, board, playerIcon);
		}
		
		//Check for winning diagonally
		for (int n = 0; n<2; n++) //Will repeat twice, as there are only 2 diagonal lines that can be formed
		{
			boolean IsWon = true; //Is this diagonal line won?
			if(n==0) //Start from top left side
			{
				//Top left side is (0,0), so both r and c start at 0 and increase at the same time
				for(int r = 0, c = 0; r<board.length && c<board.length; r++, c++)
				{
					if(board[r][c] != player)
						IsWon = false;
				}
			}
			else //Start from the top right side
			{
				//Top right side is (row 1, last column). The rows will increase top to bottom, while columns will decrease left to right
				for(int r = 0, c = (board.length - 1); r<board.length && c>=0; r++, c--)
				{
					if(board[r][c] != player)
						IsWon = false;
				}
			}
			
			if(IsWon)
				return playerWins(player, board, playerIcon);
			
		}
		
		//If none of this are winning, then game is not yet over.
		return false;
		
	}
	//Method outputs a congratulatory message and returns a value of true to conclude the game
	public static boolean playerWins(int player, int[][] board, char playerIcon) {
		System.out.println(board);
		
		if(player == 1)
		{
			System.out.println("Player has won!");
			return true;
		}
		else
		{
			System.out.println("Computer has won!");
			return true;
		}
	}
	//Check if the tile the user selected is already taken
	public static boolean validTile(int[][] board, int[] choice) {
		//If the tile selected is 0, it is valid. Choice[0] is the row value selected and choice[1] is the column value selected.
		if(board[choice[0]][choice[1]] == 0)
			return true;
		else
		{
			System.out.println("Invalid Choice, tile already taken.");
			return false;
		}
	}
	//Used to check if the tile is occupied by a specific player
	public static boolean occupiedTile(int[][] board, int[] choice, int occupier) {
		//If the tile selected is 0, it is valid. Choice[0] is the row value selected and choice[1] is the column value selected.
		if(board[choice[0]][choice[1]] == occupier)
			return true;
		else
			return false;
	}
	//Display the game board to the player
//	public static void displayBoard(int[][] board, char playerIcon) {
//		
//		char computerIcon;
//		if(playerIcon == 'X')
//			computerIcon = 'O';
//		else
//			computerIcon = 'X';
//		
//		//Output top of board
//		System.out.println("\n  0 1 2");
//		System.out.println(" _______");
//		for (int r = 0; r<board.length; r++)
//		{
//			//Output first wall of row
//			System.out.print(r + "|");
//			for (int c = 0; c<board[r].length; c++) //Output each row and column, replacing the 0, 1, and 2 values with X, O, or nothing.
//			{
//				if(board[r][c] == 0)
//					System.out.print(" |");
//				else if(board[r][c] == 1)
//					System.out.print(playerIcon + "|");
//				else
//					System.out.print(computerIcon + "|");
//			}
//			//Output floor of the row
//			System.out.println("\n -------");
//			
//		}
//		//Create a space after the board
//		System.out.println();
//	}
	//Get the player input for which tile they would like to take
	public static int[] getUserChoice(int boardRow, int boardCol) {
		//Create variable for number of coordinates
		int choiceLength = 2;
		//Create array for coordinates
		int[] choice = new int[choiceLength];
		
		String input;
		do {
			System.out.print("Select a tile (Row, Column): ");
			input = keyboard.nextLine();
			//keyboard.nextLine();
		}while(!validCoordinates(input));
		
		int counter = 0; //Used to put coordinates in right spots
		for(int i = 0; i < input.length(); i++) {
			if(Character.isDigit(input.charAt(i))) {
				choice[counter] = Integer.parseInt(input.substring(i, i+1));
				counter++;
			}
		}
		
		
		return choice;
		
	}
	//Check if the inputed choice is within the bounds of the array.
	public static boolean validCoordinates(String input) {
		
		input.trim();
		
		int validCoordinates = 0; //Counts to check how many valid coordinates are in the string
		int[] coordinates = new int[2];
		
		for(int i = 0; i < input.length(); i++) {
			if(Character.isDigit(input.charAt(i)))
			{
				if(validCoordinates < 2) //If there are more than 2 valid coordinates, it is invalid and dont put in array
				{
				coordinates[validCoordinates] = Integer.parseInt(input.substring(i, i+1));
				}
				
				validCoordinates++;
			}
		}
		
		boolean validNumbers = true;
		for(int i = 0; i < coordinates.length; i++) {
			if(!(coordinates[i] >= 0 && coordinates[i] <=2)) //Check to make sure the coordinates themselves are valid tiles
				validNumbers = false;
		}
		
		if(validCoordinates == 2 && validNumbers)
			return true;
		else
		{
			System.out.println("Please enter 2 valid coordinate values for the tile you wish to select");
			return false;
		}
	}
	//Once player has chosen a valid tile, change it to the players control
	public static int[][] setTile(int[][] board, int[] choice, int player) {
		board[choice[0]][choice[1]] = player; //Assign the chosen tile to the player that chose it
		return board;
	}
	public static boolean gameTied(int[][] board, char playerIcon) {
		boolean isTied = true; //create flag to check if game is tied, initialized as true and will be proven false if game not tied
		
		//Check if there is still an available space on the board
		for(int r = 0; r<board.length; r++)
		{
			for(int tile: board[r])
			{
				if(tile == 0)
				{
					isTied = false;
				}
			}
		}
		
		if(isTied)
		{
			System.out.println(board);
			System.out.println("A tie has been reached.");
			return true;
		}
		else
			return false;
	}
	//Gets the icon that the player wishes to use
	public static char getPlayerIcon() {
		
		char icon;
		do {
		System.out.print("Would you like to use X or O as your icon? ");
		String input = keyboard.next();
		icon = input.charAt(0);
		
		//Capitalize the icon if user did not capitalize it
		if(icon == 'x')
			icon = 'X';
		else if(icon == 'o')
			icon = 'O';
		
		}while(invalidIcon(icon));
		
		return icon;
	}
	//Validate the icon that the player chose
	public static boolean invalidIcon(char icon) {
		if(icon == 'X' || icon == 'O')
			return false; //Not invalid
		else
		{
			System.out.println("Invalid icon, please select X or O");
			return true; //Invalid icon
		}
	}
	//Computer methods
	public static int[][] computerTurn(int[][] board, boolean firstTurn, int computer, int player) {
		
		//If it is the first turn, go for the center spot
		if(firstTurn)
		{
			if(centerTileAvailable(board))
			{
				System.out.println("Computer is taking tile [1, 1]");
				board[1][1] = computer; //Center spot taken by computer
				return board;
			}
			else
			{
				//Otherwise go for a random corner spot
				int[] choice = getRandomCorner(); //No need to validate, if center is occupied it is the only player tile so far
				outputComputerChoice(choice);
				board = setTile(board, choice, computer);
				return board;
			}
		}
		else //Not turn 1
		{
			
			int[] choice;
			
			choice = aboutToWin(computer, board);
			
			if(choice[0] <0 && choice[1] <0) //A negative value for the choice means not about to win
			{
				choice = aboutToWin(player, board); //Check if the player is about to win
				
				if(choice[0] <0 && choice[1] <0) //A negative value means that player not about to win
				{
					choice = computerPickTile(computer, player, board); //Pick another tile in a line that can win, otherwise pick a random tile
					
					outputComputerChoice(choice);
					board = setTile(board, choice, computer); //Take the winning tile
					return board;
					
				}
				else //Player about to win, take their winning tile
				{
					outputComputerChoice(choice);
					board = setTile(board, choice, computer); //Take the winning tile
					return board;
				}
			}
			else //A positive value means that the computer is about to win
			{
				outputComputerChoice(choice);
				board = setTile(board, choice, computer); //Take the winning tile
				return board;
			}
		}
	}
	//selects 1 of 4 corner (top left, top right, bottom left, bottom right) tiles
	public static int[] getRandomCorner() {
		int[] choice = new int[2]; //Board is 2D, so 2 coordinates. Coordinates are in order [row, column]
		
		//If random int is 0, choose row 0, otherwise choose row 2
		if(random.nextInt(2) == 0) 
		{
			choice[0] = 0;
			if(random.nextInt(2) == 0) { //If random int is 0, choose column 0, otherwise column 2
				choice[1] = 0;
			}
			else
				choice[1] = 2;
		}
		else
		{
			choice[0] = 2;
			if(random.nextInt(2) == 0) { //If random int is 0, choose column 0, otherwise column 2
				choice[1] = 0;
			}
			else
				choice[1] = 2;
			
		}
		
		return choice;
	}
	//Outputs the computer's tile choice to the player
	public static void outputComputerChoice(int[] choice) {
		System.out.println("Computer is taking tile [" + choice[0] + "," + choice[1] + "]");
		
	}
	//Returns boolean based on whether the center tile ([1,1]) is taken or not
	public static boolean centerTileAvailable(int[][] board) {
		if(board[1][1] == 0)
			return true;
		else
			return false;
	}
	public static int[] aboutToWin(int playerOrComputer, int[][] board) {
		
		int[] choice = {-1, -1}; //Default choice is negative, as it is assumed there is no winning tile
		
		int opponent = (playerOrComputer == 1) ? 2: 1; //If player is 1, opponent is 2 and vice versa
		
		//Check rows for victory conditions
		for(int r = 0; r<board.length; r++)
		{
			int tileCounter = 0; //Counts how many tiles are of player or computer in row, if 2 then about to win
			//Check each column in the row
			for(int c = 0; c<board.length; c++)
			{
				if(board[r][c] == playerOrComputer)
					tileCounter++;
				else if(board[r][c] == opponent)
					tileCounter = -3; //If there is an opponent tile in the row, it cannot be won.
			}
			
			if(tileCounter == 2)
			{
				for(int c = 0; c<board.length; c++) //Find the winning tile and return it
				{
					if(board[r][c] == 0)
					{
						choice[0] = r;
						choice[1] = c;
						return choice;
					}
				}
				
			}
		}
		
		//Check Columns for victory conditions
		for(int c = 0; c<board.length; c++)
		{
			int tileCounter = 0; //Counts how many tiles are of player or computer in row, if 2 then about to win
			//Check each column in the row
			for(int r = 0; r<board.length; r++)
			{
				if(board[r][c] == playerOrComputer)
					tileCounter++;
				else if(board[r][c] == opponent)
					tileCounter = -3; //If there is an opponent tile in the row, it cannot be won.
			}
			
			if(tileCounter == 2)
			{
				for(int r = 0; r<board.length; r++) //Find the winning tile and return it
				{
					if(board[r][c] == 0)
					{
						choice[0] = r;
						choice[1] = c;
						return choice;
					}
				}
				
			}
		}
		
		//Check diagonal, only if the player or computer owns the center tile
		if(board[1][1] == playerOrComputer)
		{
			for (int n = 0; n<2; n++) //Will repeat twice, as there are only 2 diagonal lines that can be formed
			{
				if(n==0) //Start from top left side
				{
					int tileCounter = 0;
					
					//Left to right
					for(int r = 0, c = 0; r<board.length && c<board.length; r++, c++)
					{
						if(board[r][c] == playerOrComputer)
							tileCounter++;
						else if(board[r][c] == opponent)
							tileCounter = -3; //Can't win
					}
					
					if(tileCounter == 2)
					{
						for(int r = 0, c = 0; r<board.length && c<board.length; r++, c++)
						{
							if(board[r][c] == 0)
							{
								choice[0] = r;
								choice[1] = c;
								return choice;
							}
								
						}
						
					}
				}
				else //Start from the top right side
				{
					int tileCounter = 0;
					
					//Right to left
					for(int r = 0, c = (board.length - 1); r<board.length && c>=0; r++, c--)
					{
						if(board[r][c] == playerOrComputer)
							tileCounter++;
						else if(board[r][c] == opponent)
							tileCounter = -3; //Cannot win
					}
					
					if(tileCounter == 2)
					{
						for(int r = 0, c = (board.length - 1); r<board.length && c>=0; r++, c--)
						{
							if(board[r][c] == 0)
							{
								choice[0] = r;
								choice[1] = c;
								return choice;
							}
								
						}
						
					}
				}
			}
			//Everything checked, and not about to win.
			return choice;
		}
		else //If neither rows nor columns are winning, and player or computer does not own the center, they are not close to winning
			return choice;
	}
	
	//Pick a tile in a row that already has a computer tile
	public static int[] computerPickTile(int computer, int opponent, int[][] board) {
		if(board[1][1] == computer) //If the computer owns the center, check for diagonal winnings 
		{
			int[] cornerTile = getRandomCorner(); //Get a random corner tile that is not occupied
			
			boolean canTakeCorner = true; //Assume that a corner can be taken until proven otherwise
			boolean firstCornerTaken = false; //If the first corner is taken, this prevents extra math later
			if(occupiedTile(board, cornerTile, opponent) || occupiedTile(board, cornerTile, computer))
			{
				firstCornerTaken = true; //If this tile is occupied by computer, then the opposite is opponent occupied as it would have been counted in computerAboutToWin check
				cornerTile[0] = getOpposite(cornerTile[0]); //Check the opposite row for a valid tile
				if(occupiedTile(board, cornerTile, opponent) || occupiedTile(board, cornerTile, computer))
					canTakeCorner = false; //If the opposite row is also taken, a corner cannot be taken
			}
			
			if(canTakeCorner)
			{
				//Find the 3rd diagonal tile, and check if opponent owns it
				int[] oppositeTile = new int[cornerTile.length];
				
				//find the opposite row
				oppositeTile[0] = getOpposite(cornerTile[0]);
				
				//Find the opposite column
				oppositeTile[1] = getOpposite(cornerTile[1]);
				
				//check if opposite tile is unoccupied
				if(!occupiedTile(board, oppositeTile, opponent))
					return cornerTile; //If unoccupied, take the tile as this row can be won
				else if (!firstCornerTaken)
				{
					
					cornerTile[0] = getOpposite(cornerTile[0]); //Check the opposite row for a valid tile
					if(!(occupiedTile(board, cornerTile, opponent) || occupiedTile(board, cornerTile, computer)))
					{
						//Find the 3rd diagonal tile, and check if opponent owns it						
						//find the opposite row
						oppositeTile[0] = getOpposite(cornerTile[0]);
						
						//Find the opposite column
						oppositeTile[1] = getOpposite(cornerTile[1]);
						
						//check if opposite tile is unoccupied
						if(!occupiedTile(board, oppositeTile, opponent))
							return cornerTile; //If unoccupied, take the tile as this row can be won
					}
					
				}
				
			}
				
				
		}
		
		//If the computer does not own the center or none of the corners are valid
		
		int[] choice = new int[2]; //2 Coordinates
		
		boolean pickedRow = false; //Determines if computer chose rows or columns first
		
		if(random.nextInt(2) == 0) //50% chance that the computer starts with rows
		{
			pickedRow = true;
			
			//Reused code to find the number of computer tiles per row
			for(int r = 0; r<board.length; r++)
			{
				int tileCounter = 0; //Counts how many tiles are of player or computer in row, if 2 then about to win
				//Check each column in the row
				for(int c = 0; c<board.length; c++)
				{
					if(board[r][c] == computer)
						tileCounter++;
					else if(board[r][c] == opponent)
						tileCounter = -3; //If there is an opponent tile in the row, it cannot be won.
				}
				
				boolean skippedTile = false; //Determines if computer skipped the first tile, so that the tile in the row actually slected is random
				if(tileCounter == 1) //Shouldn't be greater than 1 as that would be caught by the aboutToWin method
				{
					for(int c = 0; c<board.length; c++) //Find the winning tile and return it
					{
						if(board[r][c] == 0 && (random.nextInt(2) == 0 || skippedTile)) //50% chance, or if other tile skipped
						{
							choice[0] = r;
							choice[1] = c;
							return choice;
						}
						else if(board[r][c] == 0)
							skippedTile = true;
					}
					
				}
			}
		}
		else //50% chance that the computer starts with columns
		{
			
			for(int c = 0; c<board.length; c++)
			{
				int tileCounter = 0; //Counts how many tiles are of player or computer in row, if 2 then about to win
				//Check each row in the column
				for(int r = 0; r<board.length; r++)
				{
					if(board[r][c] == computer)
						tileCounter++;
					else if(board[r][c] == opponent)
						tileCounter = -3; //If there is an opponent tile in the row, it cannot be won.
				}
				
				boolean skippedTile = false; //Determines if computer skipped the first tile, so that the tile in the row actually slected is random
				if(tileCounter == 1) //Shouldn't be greater than 1 as that would be caught by the aboutToWin method
				{
					for(int r = 0; r<board.length; r++) //Find the winning tile and return it
					{
						if(board[r][c] == 0 && (random.nextInt(2) == 0 || skippedTile)) //50% chance, or if other tile skipped
						{
							choice[0] = r;
							choice[1] = c;
							return choice;
						}
						else if(board[r][c] == 0)
							skippedTile = true;
					}
					
				}
			}
			
		}
		
		//If nothing found in that direction, pick the other direction
		if(pickedRow) //50% chance that the computer starts with columns
		{
			
			for(int c = 0; c<board.length; c++)
			{
				int tileCounter = 0; //Counts how many tiles are of player or computer in row, if 2 then about to win
				//Check each row in the column
				for(int r = 0; r<board.length; r++)
				{
					if(board[r][c] == computer)
						tileCounter++;
					else if(board[r][c] == opponent)
						tileCounter = -3; //If there is an opponent tile in the row, it cannot be won.
				}
				
				boolean skippedTile = false; //Determines if computer skipped the first tile, so that the tile in the row actually slected is random
				if(tileCounter == 1) //Shouldn't be greater than 1 as that would be caught by the aboutToWin method
				{
					for(int r = 0; r<board.length; r++) //Find the winning tile and return it
					{
						if(board[r][c] == 0 && (random.nextInt(2) == 0 || skippedTile)) //50% chance, or if other tile skipped
						{
							choice[0] = r;
							choice[1] = c;
							return choice;
						}
						else if(board[r][c] == 0)
							skippedTile = true;
					}
					
				}
			}
			
		}
		else
		{
			//Reused code to find the number of computer tiles per row
			for(int r = 0; r<board.length; r++)
			{
				int tileCounter = 0; //Counts how many tiles are of player or computer in row, if 2 then about to win
				//Check each column in the row
				for(int c = 0; c<board.length; c++)
				{
					if(board[r][c] == computer)
						tileCounter++;
					else if(board[r][c] == opponent)
						tileCounter = -3; //If there is an opponent tile in the row, it cannot be won.
				}
				
				boolean skippedTile = false; //Determines if computer skipped the first tile, so that the tile in the row actually slected is random
				if(tileCounter == 1) //Shouldn't be greater than 1 as that would be caught by the aboutToWin method
				{
					for(int c = 0; c<board.length; c++) //Find the winning tile and return it
					{
						if(board[r][c] == 0 && (random.nextInt(2) == 0 || skippedTile)) //50% chance, or if other tile skipped
						{
							choice[0] = r;
							choice[1] = c;
							return choice;
						}
						else if(board[r][c] == 0)
							skippedTile = true;
					}
					
				}
			}
		}
		
		//If all else fails, pick a completely random unoccupied tile
		return randomTile(board, computer);
		
	}
	//Used to find the opposite tile on other side of board
	public static int getOpposite(int tile) {
		if(tile == 0)
			return 2; //If tile in 0th, opposite is in 2nd
		else
			return 0; //If the tile is in 2nd, the opposite is 0th
	}
	public static int[] randomTile(int[][] board, int computerOrPlayer) {
		
		int[] choice = new int[2]; //coordinates
		
		do {
		//Row
		choice[0] = random.nextInt(board.length); //Random valid coordinate
		//Column
		choice[1] = random.nextInt(board.length); //Random valid coordinate
		}while(!occupiedTile(board, choice, 0)); //Keep going while the tile is not empty (occupied by 0)
		
		return choice; //Once valid, return choice
	}

}
