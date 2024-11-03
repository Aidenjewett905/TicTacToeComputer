package tictactoe;

public class Board {

	private int[][] board;
	
	private char playerIcon;
	
	private char computerIcon;
	
	public Board(int[][] board) {
		this.board = board;
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
	
	public boolean gameWon() {
		
	}
	
	public boolean gameTied() {
		
	}
	
	private boolean playerWins() {
		
	}
	
	public boolean validTile() {
		
	}
	
	public boolean occupiedTile() {
		
	}
	
	public void setTile() {
		
	}
	
	public String toString() {

		StringBuilder output = new StringBuilder("\n  0 1 2\n _______"); //Top of board
		
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
			output.append("\n -------");
		}
		
		return output.toString();
	}
}
