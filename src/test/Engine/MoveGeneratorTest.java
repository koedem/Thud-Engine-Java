package test.Engine;

import Main.Engine.Board;
import Main.Engine.BoardInterface;
import Main.Engine.MoveGenerator;
import Main.Engine.MoveGeneratorInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
public class MoveGeneratorTest {

	private BoardInterface board = new Board();
	private MoveGenerator moveGenerator = (MoveGenerator) board.getMoveGenerator();

	@Test
	public void testDwarfMoveIterator() {
		short[] moveArray = moveGenerator.collectMoves(true, new short[MoveGenerator.MAX_MOVE_COUNT]);
		moveGenerator.setupDwarfMoveIterator();
		for (int i = 1; i <= moveArray[0]; i++) {
			Assertions.assertEquals(Short.toUnsignedInt(moveArray[i]), moveGenerator.iterativeDwarfMoveGenerator());
		}
		Assertions.assertEquals(0, moveGenerator.iterativeDwarfMoveGenerator());

		board.makeMove(7);
		board.makeMove(66 * 256 + 52);

		moveArray = moveGenerator.collectMoves(true, new short[MoveGenerator.MAX_MOVE_COUNT]);
		moveGenerator.setupDwarfMoveIterator();
		for (int i = 1; i <= moveArray[0]; i++) {
			Assertions.assertEquals(Short.toUnsignedInt(moveArray[i]), moveGenerator.iterativeDwarfMoveGenerator());
		}
		Assertions.assertEquals(0, moveGenerator.iterativeDwarfMoveGenerator());
	}

	@Test
	public void testTrollMoveIterator() {
		board.makeMove(7);
		short[] moveArray = moveGenerator.collectMoves(false, new short[MoveGenerator.MAX_MOVE_COUNT]);
		moveGenerator.setupTrollMoveIterator();
		for (int i = 1; i <= moveArray[0]; i++) {
			Assertions.assertEquals(Short.toUnsignedInt(moveArray[i]), moveGenerator.iterativeTrollMoveGenerator());
		}
		Assertions.assertEquals(0, moveGenerator.iterativeTrollMoveGenerator());

		board.makeMove(66 * 256 + 52);
		board.makeMove(1 * 256 + 0);

		moveArray = moveGenerator.collectMoves(false, new short[MoveGenerator.MAX_MOVE_COUNT]);
		moveGenerator.setupTrollMoveIterator();
		for (int i = 1; i <= moveArray[0]; i++) {
			Assertions.assertEquals(Short.toUnsignedInt(moveArray[i]), moveGenerator.iterativeTrollMoveGenerator());
		}
		Assertions.assertEquals(0, moveGenerator.iterativeTrollMoveGenerator());
	}

	@Test
	public void testDiagonalArray() {
		int[] count = new int[164];
		for (byte[] array : MoveGenerator.diagonalsForDwarfs) {
			for (byte value : array) {
				count[Byte.toUnsignedInt(value)]++;
			}
		}

		for (int i = 0; i < count.length; i++) {
			int value = 0;
			for (int j = 0; j < 4; j++) {
				value += MoveGenerator.diagonalsForDwarfs[i * 4 + j].length;
			}
			Assertions.assertEquals(count[i], value, "Error on square " + Integer.toString(i));
		}


	}

	@Test
	public void testRowArray() {
		int[] count = new int[164];
		for (byte[] array : MoveGenerator.rowsForDwarfs) {
			for (byte value : array) {
				count[Byte.toUnsignedInt(value)]++;
			}
		}

		for (int i = 0; i < count.length; i++) {
			int value = 0;
			for (int j = 0; j < 4; j++) {
				value += MoveGenerator.rowsForDwarfs[i * 4 + j].length;
			}
			Assertions.assertEquals(count[i], value, "Error on square " + Integer.toString(i));
		}
	}

	@Test
	public void testNeighborArray() {
		int[] count = new int[164];
		for (byte[] array : MoveGenerator.neighborSquares) {
			for (byte value : array) {
				count[Byte.toUnsignedInt(value)]++;
			}
		}

		for (int i = 0; i < count.length; i++) {
			Assertions.assertEquals(count[i], MoveGenerator.neighborSquares[i].length, "Error on square " + Integer.toString(i));
		}
	}
}
