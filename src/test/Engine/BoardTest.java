package test.Engine;

import Main.Engine.Board;
import Main.Engine.BoardInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 *
 */
public class BoardTest implements Serializable {

	private BoardInterface board;

	@BeforeEach
	public void setup() {
		board = new Board();
	}

	@Test
	public void testDwarfLocations() {
		int[] locations = board.getDwarfLocations();
		for (int i = 1; i <= 32; i++) {
			Assertions.assertEquals(board.getSquare(locations[i]), i);
		}
	}

	@Test
	public void testTrollLocations() {
		int[] locations = board.getTrollLocations();
		for (int i = 1; i <= 8; i++) {
			Assertions.assertEquals(board.getSquare(locations[i]), -i);
		}
	}

	public void setBoard(BoardInterface board) {
		this.board = board;
	}
}
