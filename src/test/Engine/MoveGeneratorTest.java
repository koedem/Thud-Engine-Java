package test.Engine;

import Main.Engine.MoveGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
public class MoveGeneratorTest {

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
