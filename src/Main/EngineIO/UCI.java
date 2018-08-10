package Main.EngineIO;

import Main.Engine.*;
import Main.MultiThreading.MultiThreadSearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author Anon
 * 
 */
public final class UCI {
	
	public static boolean uci = true;
	
	public static String logfile = "";
	
	private static int baseTime = 100;
	private static int incTime = 2;
	private static int minLeft = 20;
											// For scenarios with possibly inaccurate system time we have backup:
	private static int lowerKN_Bound = 0;   // We don't move before we searched lowerKN_Bound * timeLimit many nodes.
											// Should be lower than kN/s
	private static int upperKN_Bound = 500; // No matter what the timer says, when upperKN_Bound * timeLimit nodes
											// are exceeded we move. Should be higher than kN/s
	private static int kingSafety = 10;
	private static int dynamism = 10;

	private static int threadCount = 1;
	private static final int LOWER_THREAD_COUNT = 1;
	private static final int UPPER_THREAD_COUNT = 5;

	private static boolean threadFinished = false;

	private static ExecutorService executor            = Executors.newFixedThreadPool(5);
	static String                  engineName          = "Koedem";
	static BoardInterface                   board               = new Board();
	static Perft perft = new Perft(board);
	private static String          lastPositionCommand = "";
	private static Scanner         sc                  = new Scanner(System.in);
	
	public static void main(String[] args) {
		uciCommunication();
		System.exit(0);
	}
	
	private static void uciCommunication() {
		Main.EngineIO.Logging.setup();
		String command = "";
		Main.EngineIO.Logging.printLine(engineName + " by Tom Marvolo.");
		while (!command.equals("quit")) {
			command = sc.nextLine();
			Main.EngineIO.Logging.printLine("info command received at milli: " + Long.toString(System.currentTimeMillis()));
			Main.EngineIO.Logging.addToLogFile(">> " + command);
			
			if (command.equals("uci")) {
				inputUCI();
			} else if (command.startsWith("setoption name")) {
				inputSetOption(command);
			} else if (command.equals("isready")) {
				inputIsReady();
			} else if (command.equals("ucinewgame")) {
				inputUCINewGame();
			} else if (command.startsWith("position")) {
				inputPosition(command);
				lastPositionCommand = command;
			} else if (command.contains("go")) {
				inputGo(command);
			} else if (command.equals("print")) {
				board.printBoard();
			} else if (command.equals("print legal moves")) {
				short[] moves = new short[MoveGenerator.MAX_MOVE_COUNT];
				moves = board.getMoveGenerator().collectMoves(board.getToMove(), moves);
				for (short i = 1; i <= moves[0]; i++) {
					Main.EngineIO.Logging.printLine(Main.EngineIO.Transformation.numberToMove(Short.toUnsignedInt(moves[i])));
				}
			} else if (command.equals("print legal captures")) {
				short[] captures = board.getMoveGenerator().collectCaptures(board.getToMove(), new short[MoveGenerator.MAX_MOVE_COUNT]);
				if (captures[0] == 0) {
					Main.EngineIO.Logging.printLine("There are no legal captures.");
					continue;
				}
				if (captures[0] == -1) {
					Main.EngineIO.Logging.printLine("Illegal position.");
					continue;
				}
				for (int i = 1; i <= captures[0]; i++) {
					Main.EngineIO.Logging.printLine(Main.EngineIO.Transformation.numberToMove(Short.toUnsignedInt(captures[i])));
				}
			} else if (command.equals("q search")) {
				ArrayList<Integer> reversePV = board.getSearch().qSearch(board.getToMove(), -30000, 30000, 0);
				Main.EngineIO.Logging.printLine(Integer.toString(reversePV.get(0)));
				reversePV.remove(0);
				StringBuilder pv = new StringBuilder();
				for (Integer capture : reversePV) {
					pv.append(Main.EngineIO.Transformation.numberToMove(capture)).append(" ");
				}
				pv.append("Node count: ").append(board.getSearch().getNodes());
				Main.EngineIO.Logging.printLine(pv.toString());
				board.getSearch().setNodes(0);
			} else if (command.equals("evaluate")) {
				Main.EngineIO.Logging.printLine(Integer.toString(board.getEvaluation().evaluation(board.getToMove(), -30000)));
			} else if (command.equals("Hashtable")) {
				/*for (Node value : board.getHashTable().values()) {
					value.print();
				}*/ // TODO
			} else if (command.equals("materialOnly on")) {
				board.getEvaluation().setMaterialOnly(true);
			} else if (command.equals("materialOnly off")) {
				board.getEvaluation().setMaterialOnly(false);
			} else if (command.equals("print bitboard")) {
				board.getBitboard().printBitBoard();
			} else if (command.startsWith("perft")) {
				String[] parts = command.split(" ");
				perft.rootPerft(Integer.parseInt(parts[1]));
			}
		}
		Main.EngineIO.Logging.close();
	}

    private static void inputUCINewGame() {
		board = new Board();
	}

	private static void inputSetOption(String command) {
		String[] parameters = command.split(" ");
		switch (parameters[2]) {
			case "BaseTime":
				try {
					baseTime = 10000 / Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'BaseTime'.");
				}
				break;
			case "IncTime":
				try {
					incTime = 10000 / Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'IncTime'.");
				}
				break;
			case "MinLeft":
				try {
					minLeft = 10000 / Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'MinLeft'.");
				}
				break;
			case "Lower_KN_searched_bound":
				try {
					lowerKN_Bound = Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'Lower_KN_searched_bound'.");
				}
				break;
			case "Upper_KN_searched_bound":
				try {
					upperKN_Bound = Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'Upper_KN_searched_bound'.");
				}
				break;
			case "KingSafety":
				try {
					kingSafety = Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'KingSafety'.");
				}
				break;
			case "Dynamism":
				try {
					dynamism = Integer.parseInt(parameters[4]);
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'Dynamism'.");
				}
				break;
			case "Threads":
				try {
					int newCount = Integer.parseInt(parameters[4]);
					if (newCount >= LOWER_THREAD_COUNT && newCount <= UPPER_THREAD_COUNT) {
						threadCount = newCount;
						//ThreadOrganization.updateThreadCount(threadCount, board);
					} else {
						Main.EngineIO.Logging.printLine("Illegal value for option 'Threads'. Should be between " + LOWER_THREAD_COUNT + " and " + UPPER_THREAD_COUNT + ".");
					}
				} catch (NumberFormatException e) {
					Main.EngineIO.Logging.printLine("Illegal value for option 'Threads'. Should be an integer.");
				}
		}
	}

	private static void inputUCI() {
		Main.EngineIO.Logging.printLine("id name " + engineName);
		Main.EngineIO.Logging.printLine("id author Tom Marvolo");
		
		Main.EngineIO.Logging.printLine("option name BaseTime type spin default 100 min 1 max 10000");
		Main.EngineIO.Logging.printLine("option name IncTime type spin default 5000 min 1 max 10000");
		Main.EngineIO.Logging.printLine("option name MinTime type spin default 500 min 1 max 10000");
		Main.EngineIO.Logging.printLine("option name Lower_KN_searched_bound type spin default 0 min 0 max 1000");
		Main.EngineIO.Logging.printLine("option name Upper_KN_searched_bound type spin default 500 min 1 max 10000");
		Main.EngineIO.Logging.printLine("option name KingSafety type spin default 10 min 1 max 100");
		Main.EngineIO.Logging.printLine("option name Dynamism type spin default 10 min 1 max 100");
		Main.EngineIO.Logging.printLine("option name Threads type spin default 1 min 1 max 5");
		
		Main.EngineIO.Logging.printLine("uciok");
	}
	
	private static void inputIsReady() {
		Main.EngineIO.Logging.printLine("readyok");
	}
	
	private static void inputPosition(String input) {
		String command;
		if (input.startsWith(lastPositionCommand)) {
			command = input.substring(lastPositionCommand.length()); // if we previously had the same command start we don't need to set that up again
			if (lastPositionCommand.contains("moves")) { // the only thing that changed is we have new moves; no need to go through the rest
				String[] parameters = command.split(" ");
				for (String parameter : parameters) {
					if (!parameter.equals("")) {
						//Node node = new Node(board, 0, 0, 0, board.getToMove());
						board.makeMove(parameter);
					}
				}
				return;
			}
		} else {
			command = input.substring(9);
		}
		String[] parameters = command.split(" ");
		for (int i = 0; i < parameters.length; i++) {
			switch (parameters[i]) {
				case "startpos":
					board = new Board();
					break;
				case "fen":
					StringBuilder fen = new StringBuilder();
					for (int j = 0; j < 6; j++) {
						fen.append(parameters[i + 1 + j]).append(" ");
					}
					//board = new Board(fen.toString());
					i += 6;
					break;
				case "moves":
					board.getHashTable().clear();
					i++;
					for (int j = 0; j < (parameters.length - i); j++) {
						//Node node = new Node(board, 0, 0, 0, board.getToMove());
						board.makeMove(parameters[i + j]);
					}
					break;
			}
		}
	}
	
	private static void inputGo(String command) {
		MultiThreadSearch thread = null;
		
		if (command.contains("depth")) {
			String[] parameters = command.split(" ");
			int depth = Integer.parseInt(parameters[2]);
			thread =  new MultiThreadSearch(board, depth, 1, true, 2000000000);
		} else if (command.contains("infinite")) {
			thread =  new MultiThreadSearch(board, 100, 1, true, 2000000000);
		} else {
			String[] parameters = command.split(" ");
			int searchTime = 0;
			if (board.getToMove()) {
				for (int i = 1; i < parameters.length; i++) {
					if (parameters[i].equals("wtime")) {
						searchTime += Integer.parseInt(parameters[i + 1]) / baseTime;
					} else if (parameters[i].equals("winc")) {
						if (searchTime + Integer.parseInt(parameters[i + 1]) / incTime < searchTime * baseTime / minLeft) {
							searchTime += Integer.parseInt(parameters[i + 1]) / incTime;
						} else {
							searchTime *= baseTime / minLeft;
						}
					}
				}
			} else {
				for (int i = 1; i < parameters.length; i++) {
					if (parameters[i].equals("btime")) {
						searchTime += Integer.parseInt(parameters[i + 1]) / baseTime;
					} else if (parameters[i].equals("binc")) {
						if (searchTime + Integer.parseInt(parameters[i + 1]) / incTime < searchTime * baseTime / minLeft) {
							searchTime += Integer.parseInt(parameters[i + 1]) / incTime;
						} else {
							searchTime *= baseTime / minLeft;
						}
					}
				}
			}
			Main.EngineIO.Logging.printLine("Trying to use " + searchTime + "ms + finishing current ply.");
			thread =  new MultiThreadSearch(board, 100, 1, true, searchTime);
		}
		
		UCI.setThreadFinished(false);
		Future<int[]> future = executor.submit(thread);
		
		
		int[] move = null;
		
		String stop = "";
		while (true) {
			if (future.isDone()) {
				try {
					move = future.get();
				} catch (Exception e) {
					short[] moves = new short[MoveGenerator.MAX_MOVE_COUNT];
					moves = board.getMoveGenerator().collectMoves(board.getToMove(), moves);
					Main.EngineIO.Logging.printLine("bestmove " + board.getBestmove());
				}
				break;
			} else {
				if (sc.hasNextLine()) {
					UCI.setThreadFinished(true);
				}
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isThreadFinished() {
		return threadFinished;
	}

	public static void setThreadFinished(boolean threadFinished) {
		UCI.threadFinished = threadFinished;
	}
	
	public static void printEngineOutput(String praefix, int[] move, BoardInterface board, boolean toMove, long time) {
		if (UCI.uci) {
			long timeUsed = System.currentTimeMillis() - time;
			StringBuilder pv = new StringBuilder();
			for (int i = 0; i < move.length - 1; i++) {
				pv.append(Main.EngineIO.Transformation.numberToMove(move[i])).append(" ");
			}
			Main.EngineIO.Logging.printLine("info depth " + (move.length - 1) + " score cp " + move[move.length - 1] + " nodes "
			                                + board.getSearch().getNodes() + " nps " + 1000 * board.getSearch().getNodes() / (timeUsed > 0 ? timeUsed : 1)
			                                + " time " + (System.currentTimeMillis() - time) + " pv " + pv);
		} else {
			StringBuilder pv = new StringBuilder(praefix);
			for (int j = 0; j < move.length - 1; j++) {
				if (move[j] == -1) {
					break;
				}
				if (toMove) {
					if (j % 2 == 0) {
						pv.append(board.getMoveNumber() + j / 2).append(".");
					}
					pv.append(Main.EngineIO.Transformation.numberToMove(move[j])).append(" ");
				} else {
					if (j == 0) {
						pv.append(board.getMoveNumber()).append("...");
					}
					if (j % 2 == 1) {
						pv.append(board.getMoveNumber() + j / 2 + 1).append(".");
					}
					pv.append(Main.EngineIO.Transformation.numberToMove(move[j])).append(" ");
				}
			}
			Main.EngineIO.Logging.printLine(pv.toString() + move[move.length - 1]);
			Main.EngineIO.Logging.printLine(praefix + "Node count: " + Main.EngineIO.Transformation.nodeCountOutput(((board.getSearch().getNodes()
			                                                                                                          + board.getSearch().getAbortedNodes()))) + "(" + Main.EngineIO.Transformation
					                                .nodeCountOutput(board.getSearch().getNodes())
			                                + ")" + ". Q-nodes: " + Main.EngineIO.Transformation.nodeCountOutput(board.getSearch().getQNodes()) + ". Time used: "
			                                + Main.EngineIO.Transformation.timeUsedOutput((System.currentTimeMillis() - time)));
		}
	}
	
	private UCI() {
	}

	public static int getKingSafety() {
		return kingSafety;
	}

	public static void setKingSafety(int kingSafety) {
		UCI.kingSafety = kingSafety;
	}

	public static int getDynamism() {
		return dynamism;
	}

	public static void setDynamism(int dynamism) {
		UCI.dynamism = dynamism;
	}

	public static int getLowerKN_Bound() {
		return lowerKN_Bound;
	}

	public static int getUpperKN_Bound() {
		return upperKN_Bound;
	}
}