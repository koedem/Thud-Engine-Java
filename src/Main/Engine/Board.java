package Main.Engine;

import Main.EngineIO.Logging;
import Main.EngineIO.Transformation;
import Main.Utility.DeepCopy;

import java.util.Hashtable;

/**
 *
 */
public class Board implements BoardInterface {

	/** NOTE: The locations arrays MUST be initialized before anything else!
	 * dwarfLocations[i] contains where dwarf i is.
	 * dwarfLocations[0] is unused.
	 */
	private int[] dwarfLocations = {-1, 0, 1, 3, 4, 5, 11, 12, 20, 21, 31, 32, 44, 45, 59, 60, 74, 89, 103, 104, 118, 119,
	                                131, 132, 142, 143, 151, 152, 158, 159, 160, 162, 163};

	/**
	 * trollLocations[i] contains where troll i is.
	 * trollLocations[0] is unused.
	 */
	private int[] trollLocations = {-1, 66, 67, 68, 81, 82, 95, 96, 97};

	private MoveGeneratorInterface[] moveGenerator = // with iterative move generation we need multiple instances. TODO rework MoveGenerator object
			{new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this),
			 new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this),
			 new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this),
			 new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this),
			 new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this),
			 new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this), new MoveGenerator(this)
			};
	private SearchInterface search = new Search(this);
	private EvaluationInterface evaluation = new Evaluation(this);
	private Hashtable<Long, Node> hashtable = new Hashtable<Long, Node>();

	/**
	 * +1 to +32 for dwarf, -1 to -8 for troll, 0 for empty square
	 */
	private byte[] squares = new byte[] { 1, 2, 0, 3, 4, 5, 0, 0, 0, 0, 0, 6, 7, 0, 0, 0, 0, 0, 0, 0, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10,
	                                      11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 15, 0, 0, 0,
	                                      0, 0, -1, -2, -3, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0, -4, -5, 0, 0, 0, 0, 0, 0, 17, 0, 0, 0, 0, 0, -6,
	                                      -7, -8, 0, 0, 0, 0, 0, 18, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 21, 0, 0, 0, 0, 0, 0, 0, 0,
	                                      0, 0, 0, 22, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 24, 25, 0, 0, 0, 0, 0, 0, 0, 26, 27, 0, 0, 0, 0, 0, 28, 29,
	                                      30, 0, 31, 32 };
	private boolean toMove = true; // true means dwarfs to move
	private String bestmove = "";
	private short[] rootMoves = new short[MoveGenerator.MAX_MOVE_COUNT];

	@Override
	public String getBestmove() {
		return bestmove;
	}

	@Override
	public void setBestmove(String bestmove) {
		this.bestmove = bestmove;
	}

	@Override
	public void printBoard() {
		StringBuilder printedBoard = new StringBuilder();
		printedBoard.append("x x x x x ").append(Transformation.numberToPiece(squares[0])).append(" ").append(Transformation.numberToPiece(squares[1])).append(" ")
		    .append(Transformation.numberToPiece(squares[2])).append(" ").append(Transformation.numberToPiece(squares[3])).append(" ")
		    .append(Transformation.numberToPiece(squares[4])).append(" ").append("x x x x x\nx x x x ").append(Transformation.numberToPiece(squares[5])).append(" ")
		    .append(Transformation.numberToPiece(squares[6])).append(" ").append(Transformation.numberToPiece(squares[7])).append(" ")
		    .append(Transformation.numberToPiece(squares[8])).append(" ").append(Transformation.numberToPiece(squares[9])).append(" ")
		    .append(Transformation.numberToPiece(squares[10])).append(" ").append(Transformation.numberToPiece(squares[11])).append(" ").append("x x x x\nx x x ")
		    .append(Transformation.numberToPiece(squares[12])).append(" ").append(Transformation.numberToPiece(squares[13])).append(" ")
		    .append(Transformation.numberToPiece(squares[14])).append(" ").append(Transformation.numberToPiece(squares[15])).append(" ")
		    .append(Transformation.numberToPiece(squares[16])).append(" ").append(Transformation.numberToPiece(squares[17])).append(" ")
		    .append(Transformation.numberToPiece(squares[18])).append(" ").append(Transformation.numberToPiece(squares[19])).append(" ")
		    .append(Transformation.numberToPiece(squares[20])).append(" ").append("x x x\nx x ").append(Transformation.numberToPiece(squares[21])).append(" ")
		    .append(Transformation.numberToPiece(squares[22])).append(" ").append(Transformation.numberToPiece(squares[23])).append(" ")
		    .append(Transformation.numberToPiece(squares[24])).append(" ").append(Transformation.numberToPiece(squares[25])).append(" ")
		    .append(Transformation.numberToPiece(squares[26])).append(" ").append(Transformation.numberToPiece(squares[27])).append(" ")
		    .append(Transformation.numberToPiece(squares[28])).append(" ").append(Transformation.numberToPiece(squares[29])).append(" ")
		    .append(Transformation.numberToPiece(squares[30])).append(" ").append(Transformation.numberToPiece(squares[31])).append(" ").append("x x\nx ")
		    .append(Transformation.numberToPiece(squares[32])).append(" ").append(Transformation.numberToPiece(squares[33])).append(" ")
		    .append(Transformation.numberToPiece(squares[34])).append(" ").append(Transformation.numberToPiece(squares[35])).append(" ")
		    .append(Transformation.numberToPiece(squares[36])).append(" ").append(Transformation.numberToPiece(squares[37])).append(" ")
		    .append(Transformation.numberToPiece(squares[38])).append(" ").append(Transformation.numberToPiece(squares[39])).append(" ")
		    .append(Transformation.numberToPiece(squares[40])).append(" ").append(Transformation.numberToPiece(squares[41])).append(" ")
		    .append(Transformation.numberToPiece(squares[42])).append(" ").append(Transformation.numberToPiece(squares[43])).append(" ")
		    .append(Transformation.numberToPiece(squares[44])).append(" ").append("x\n").append(Transformation.numberToPiece(squares[45])).append(" ")
		    .append(Transformation.numberToPiece(squares[46])).append(" ").append(Transformation.numberToPiece(squares[47])).append(" ")
		    .append(Transformation.numberToPiece(squares[48])).append(" ").append(Transformation.numberToPiece(squares[49])).append(" ")
		    .append(Transformation.numberToPiece(squares[50])).append(" ").append(Transformation.numberToPiece(squares[51])).append(" ")
		    .append(Transformation.numberToPiece(squares[52])).append(" ").append(Transformation.numberToPiece(squares[53])).append(" ")
		    .append(Transformation.numberToPiece(squares[54])).append(" ").append(Transformation.numberToPiece(squares[55])).append(" ")
		    .append(Transformation.numberToPiece(squares[56])).append(" ").append(Transformation.numberToPiece(squares[57])).append(" ")
		    .append(Transformation.numberToPiece(squares[58])).append(" ").append(Transformation.numberToPiece(squares[59])).append(" ").append("\n")
		    .append(Transformation.numberToPiece(squares[60])).append(" ").append(Transformation.numberToPiece(squares[61])).append(" ")
		    .append(Transformation.numberToPiece(squares[62])).append(" ").append(Transformation.numberToPiece(squares[63])).append(" ")
		    .append(Transformation.numberToPiece(squares[64])).append(" ").append(Transformation.numberToPiece(squares[65])).append(" ")
		    .append(Transformation.numberToPiece(squares[66])).append(" ").append(Transformation.numberToPiece(squares[67])).append(" ")
		    .append(Transformation.numberToPiece(squares[68])).append(" ").append(Transformation.numberToPiece(squares[69])).append(" ")
		    .append(Transformation.numberToPiece(squares[70])).append(" ").append(Transformation.numberToPiece(squares[71])).append(" ")
		    .append(Transformation.numberToPiece(squares[72])).append(" ").append(Transformation.numberToPiece(squares[73])).append(" ")
		    .append(Transformation.numberToPiece(squares[74])).append(" ").append("\n").append(Transformation.numberToPiece(squares[75])).append(" ")
		    .append(Transformation.numberToPiece(squares[76])).append(" ").append(Transformation.numberToPiece(squares[77])).append(" ")
		    .append(Transformation.numberToPiece(squares[78])).append(" ").append(Transformation.numberToPiece(squares[79])).append(" ")
		    .append(Transformation.numberToPiece(squares[80])).append(" ").append(Transformation.numberToPiece(squares[81])).append(" ").append("X ") // Thud stone
		    .append(Transformation.numberToPiece(squares[82])).append(" ").append(Transformation.numberToPiece(squares[83])).append(" ")
		    .append(Transformation.numberToPiece(squares[84])).append(" ").append(Transformation.numberToPiece(squares[85])).append(" ")
		    .append(Transformation.numberToPiece(squares[86])).append(" ").append(Transformation.numberToPiece(squares[87])).append(" ")
		    .append(Transformation.numberToPiece(squares[88])).append(" ").append("\n").append(Transformation.numberToPiece(squares[89])).append(" ")
		    .append(Transformation.numberToPiece(squares[90])).append(" ").append(Transformation.numberToPiece(squares[91])).append(" ")
		    .append(Transformation.numberToPiece(squares[92])).append(" ").append(Transformation.numberToPiece(squares[93])).append(" ")
		    .append(Transformation.numberToPiece(squares[94])).append(" ").append(Transformation.numberToPiece(squares[95])).append(" ")
		    .append(Transformation.numberToPiece(squares[96])).append(" ").append(Transformation.numberToPiece(squares[97])).append(" ")
		    .append(Transformation.numberToPiece(squares[98])).append(" ").append(Transformation.numberToPiece(squares[99])).append(" ")
		    .append(Transformation.numberToPiece(squares[100])).append(" ").append(Transformation.numberToPiece(squares[101])).append(" ")
		    .append(Transformation.numberToPiece(squares[102])).append(" ").append(Transformation.numberToPiece(squares[103])).append(" ").append("\n")
		    .append(Transformation.numberToPiece(squares[104])).append(" ").append(Transformation.numberToPiece(squares[105])).append(" ")
		    .append(Transformation.numberToPiece(squares[106])).append(" ").append(Transformation.numberToPiece(squares[107])).append(" ")
		    .append(Transformation.numberToPiece(squares[108])).append(" ").append(Transformation.numberToPiece(squares[109])).append(" ")
		    .append(Transformation.numberToPiece(squares[110])).append(" ").append(Transformation.numberToPiece(squares[111])).append(" ")
		    .append(Transformation.numberToPiece(squares[112])).append(" ").append(Transformation.numberToPiece(squares[113])).append(" ")
		    .append(Transformation.numberToPiece(squares[114])).append(" ").append(Transformation.numberToPiece(squares[115])).append(" ")
		    .append(Transformation.numberToPiece(squares[116])).append(" ").append(Transformation.numberToPiece(squares[117])).append(" ")
		    .append(Transformation.numberToPiece(squares[118])).append(" ").append("\nx ").append(Transformation.numberToPiece(squares[119])).append(" ")
		    .append(Transformation.numberToPiece(squares[120])).append(" ").append(Transformation.numberToPiece(squares[121])).append(" ")
		    .append(Transformation.numberToPiece(squares[122])).append(" ").append(Transformation.numberToPiece(squares[123])).append(" ")
		    .append(Transformation.numberToPiece(squares[124])).append(" ").append(Transformation.numberToPiece(squares[125])).append(" ")
		    .append(Transformation.numberToPiece(squares[126])).append(" ").append(Transformation.numberToPiece(squares[127])).append(" ")
		    .append(Transformation.numberToPiece(squares[128])).append(" ").append(Transformation.numberToPiece(squares[129])).append(" ")
		    .append(Transformation.numberToPiece(squares[130])).append(" ").append(Transformation.numberToPiece(squares[131])).append(" ").append("x\nx x ")
		    .append(Transformation.numberToPiece(squares[132])).append(" ").append(Transformation.numberToPiece(squares[133])).append(" ")
		    .append(Transformation.numberToPiece(squares[134])).append(" ").append(Transformation.numberToPiece(squares[135])).append(" ")
		    .append(Transformation.numberToPiece(squares[136])).append(" ").append(Transformation.numberToPiece(squares[137])).append(" ")
		    .append(Transformation.numberToPiece(squares[138])).append(" ").append(Transformation.numberToPiece(squares[139])).append(" ")
		    .append(Transformation.numberToPiece(squares[140])).append(" ").append(Transformation.numberToPiece(squares[141])).append(" ")
		    .append(Transformation.numberToPiece(squares[142])).append(" ").append("x x\nx x x ").append(Transformation.numberToPiece(squares[143])).append(" ")
		    .append(Transformation.numberToPiece(squares[144])).append(" ").append(Transformation.numberToPiece(squares[145])).append(" ")
		    .append(Transformation.numberToPiece(squares[146])).append(" ").append(Transformation.numberToPiece(squares[147])).append(" ")
		    .append(Transformation.numberToPiece(squares[148])).append(" ").append(Transformation.numberToPiece(squares[149])).append(" ")
		    .append(Transformation.numberToPiece(squares[150])).append(" ").append(Transformation.numberToPiece(squares[151])).append(" ").append("x x x\nx x x x ")
		    .append(Transformation.numberToPiece(squares[152])).append(" ").append(Transformation.numberToPiece(squares[153])).append(" ")
		    .append(Transformation.numberToPiece(squares[154])).append(" ").append(Transformation.numberToPiece(squares[155])).append(" ")
		    .append(Transformation.numberToPiece(squares[156])).append(" ").append(Transformation.numberToPiece(squares[157])).append(" ")
		    .append(Transformation.numberToPiece(squares[158])).append(" ").append("x x x x\nx x x x x ").append(Transformation.numberToPiece(squares[159])).append(" ")
		    .append(Transformation.numberToPiece(squares[160])).append(" ").append(Transformation.numberToPiece(squares[161])).append(" ")
		    .append(Transformation.numberToPiece(squares[162])).append(" ").append(Transformation.numberToPiece(squares[163])).append(" ").append("x x x x x\n");

		Logging.printLine(printedBoard.toString());
	}

	@Override
	public int makeMove(int move) {
		assert squares[move & 0xFF] == 0 && squares[move >>> 8] != 0;
		int startSquare = move >>> 8;
		int endSquare = move & 0xFF;
		if (toMove) {
			dwarfLocations[squares[startSquare]] = endSquare;
		} else {
			trollLocations[-squares[startSquare]] = endSquare;
		}
		squares[endSquare] = squares[startSquare];
		squares[startSquare] = 0;
		changeToMove();
		return 0; // TODO implement captures
	}

	@Override
	public void unmakeMove(int move, int token) {
		int startSquare = move >>> 8;
		int endSquare = move & 0xFF;
		if (toMove) {
			trollLocations[-squares[endSquare]] = startSquare;
		} else {
			dwarfLocations[squares[endSquare]] = startSquare;
		}
		squares[startSquare] = squares[endSquare];
		squares[endSquare] = 0;
		changeToMove();
		// TODO implement captures
	}

	@Override
	public short[] getRootMoves() {
		return rootMoves;
	}

	@Override
	public void setRootMoves(short[] rootMoves) {
		this.rootMoves = rootMoves;
	}

	@Override
	public byte getSquare(int square) {
		return squares[square];
	}

	/**
	 *
	 * @return who to move it is, true means dwarfs to move.
	 */
	@Override
	public boolean getToMove() {
		return toMove;
	}

	/**
	 * Change who to move it is.
	 */
	@Override
	public void changeToMove() {
		toMove = !toMove;
	}

	@Override
	public int getMoveNumber() {
		return 0; // TODO implement
	}

	@Override
	public BoardInterface cloneBoard() {
		return (BoardInterface) DeepCopy.copy(this);
	}

	@Override
	public Hashtable<Long, Node> getHashTable() {
		return hashtable; // TODO implement
	}

	@Override
	public long getZobristHash() {
		return 0; // TODO implement
	}

	@Override
	public int getPieceAdvancement(int piece) {
		return 0; // TODO implement
	}

	@Override
	public short getMaterialCount() {
		return 0; // TODO implement
	}

	@Override
	public int getPiecesLeft() {
		return 0; // TODO implement
	}

	@Override
	public void putHashTableElement(Node node) {
		// TODO implement
	}

	@Override
	public void makeMove(String move) {
		// TODO implement
	}

	@Override
	public SearchInterface getSearch() {
		return search;
	}

	@Override
	public MoveGeneratorInterface[] getMoveGenerators() {
		return moveGenerator;
	}

	@Override
	public MoveGeneratorInterface getMoveGenerator() {
		return moveGenerator[0];
	}

	@Override
	public EvaluationInterface getEvaluation() {
		return evaluation;
	}

	@Override
	public BitBoardInterface getBitboard() {
		return null; // TODO implement
	}

	@Override
	public void setBitboard(BitBoardInterface bitboard) {
		// TODO implement
	}

	@Override
	public int[] getDwarfLocations() {
		return dwarfLocations;
	}

	@Override
	public int[] getTrollLocations() {
		return trollLocations;
	}
}
