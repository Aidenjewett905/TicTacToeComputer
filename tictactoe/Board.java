package tictactoe;

public class Board {
	
	private int[][] board;
	
	private char playerIcon;
	
	private char computerIcon;
	
	public Board(int[][] board) {
		this.board = board;
	}
	
	public Board(int row, int column) {
		board = new int[row][column];
	}
	
	public Board(int[][] board, char playerIcon) {
		this.board = board;
		this.playerIcon = playerIcon;
		if(this.playerIcon == 'X')
			computerIcon = 'O';
		else
			computerIcon = 'X';
	}
	
	public Board(Board board2) {
		board = board2.getBoard();
		playerIcon = board2.getPlayerIcon();
	}
	
	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public int length() {
		return board.length;
	}
	
	public void setIcon(char playerIcon) {
		this.playerIcon = playerIcon;
		if(this.playerIcon == 'X')
			computerIcon = 'O';
		else
			computerIcon = 'X';
	}
	
	public void setPlayerIcon(char playerIcon) {
		this.playerIcon = playerIcon;
	}
	
	public void setComputerIcon(char computerIcon) {
		this.computerIcon = computerIcon;
	}
	
	public char getPlayerIcon() {
		return playerIcon;
	}
	
	public char getComputerIcon() {
		return computerIcon;
	}
	
	public boolean gameWon(int player) { //Which player is it, 1 or 2
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
					return true;
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
					return true;
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
					return true;
				
			}
			
			//If none of this are winning, then game is not yet over.
			return false;
			
	}
	
	public boolean gameTied() {
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
//			System.out.println(board.toString());
//			System.out.println("A tie has been reached.");
			return true;
		}
		else
			return false;
	}
	
//	private boolean playerWins(int player) {
////		System.out.println(board.toString());
//		
//		if(player == 1)
//		{
//			System.out.println("Player has won!");
//			return true;
//		}
//		else
//		{
//			System.out.println("Computer has won!");
//			return true;
//		}
//	}
	
	public boolean validTile(int[] choice) {
		//If the tile selected is 0, it is valid. Choice[0] is the row value selected and choice[1] is the column value selected.
		if(board[choice[0]][choice[1]] == 0)
			return true;
		else
		{
			System.out.println("Invalid Choice, tile already taken.");
			return false;
		}
	}
	
	public boolean occupiedTile(int[] choice, int occupier) {
		//If the tile selected is 0, it is valid. Choice[0] is the row value selected and choice[1] is the column value selected.
		if(board[choice[0]][choice[1]] == occupier)
			return true;
		else
			return false;
	}
	
	public void setTile(int[] choice, int player) {
		board[choice[0]][choice[1]] = player; //Assign the chosen tile to the player that chose it
	}
	
	public String toString() {

		StringBuilder output = new StringBuilder("\n  0 1 2\n _______\n"); //Top of board
		
		for(int r = 0; r<board.length; r++)
		{
			//Append first wall of row
			output.append(r + "|");
			
			for(int c = 0; c<board[r].length; c++) //Append each row and column, replacing integers when appropriate
			{
				if(board[r][c] == 0)
					output.append(" |"); //unoccupied tile
				else if(board[r][c] == 1)
					output.append(playerIcon + "|");
				else
					output.append(computerIcon + "|");
			}
			//Append floor of row
			output.append("\n -------\n");
		}
		
		return output.toString();
	}
}
