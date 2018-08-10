package Main.EngineIO;

import java.util.Dictionary;

/**
 * 
 * @author Anon
 *
 */
public final class Transformation {
	
	/**
	 * Transform the board representation of the piece into a String one for output.
	 * 
	 * @param piece : number between -1 and +1 used as representation of a piece
	 * @return String-representation of the piece
	 */
	public static String numberToPiece(int piece) {
		switch (piece) {
			case 1: return "D";
			case 0: return "-"; 
			case -1: return "T";
			default: return "X";
		}
	}
	
	/**
	 * Transform the String representation of a square from input into an internal one.
	 * 
	 * @param square : Input square representation as String
	 * @return internal representation as number
	 */
	public static int squareToNumber(String square) {
		int squareNumber;
		
		switch(square) {
			case "f15": squareNumber = 0; break; case "g15": squareNumber = 1; break; case "h15": squareNumber = 2; break; case "i15": squareNumber = 3; break;
			case "j15": squareNumber = 4; break; case "e14": squareNumber = 5; break; case "f14": squareNumber = 6; break; case "g14": squareNumber = 7; break;
			case "h14": squareNumber = 8; break; case "i14": squareNumber = 9; break; case "j14": squareNumber = 10; break; case "k14": squareNumber = 11; break;
			case "d13": squareNumber = 12; break; case "e13": squareNumber = 13; break; case "f13": squareNumber = 14; break; case "g13": squareNumber = 15; break;
			case "h13": squareNumber = 16; break; case "i13": squareNumber = 17; break; case "j13": squareNumber = 18; break; case "k13": squareNumber = 19; break;
			case "l13": squareNumber = 20; break; case "c12": squareNumber = 21; break; case "d12": squareNumber = 22; break; case "e12": squareNumber = 23; break;
			case "f12": squareNumber = 24; break; case "g12": squareNumber = 25; break; case "h12": squareNumber = 26; break; case "i12": squareNumber = 27; break;
			case "j12": squareNumber = 28; break; case "k12": squareNumber = 29; break; case "l12": squareNumber = 30; break; case "m12": squareNumber = 31; break;
			case "b11": squareNumber = 32; break; case "c11": squareNumber = 33; break; case "d11": squareNumber = 34; break; case "e11": squareNumber = 35; break;
			case "f11": squareNumber = 36; break; case "g11": squareNumber = 37; break; case "h11": squareNumber = 38; break; case "i11": squareNumber = 39; break;
			case "j11": squareNumber = 40; break; case "k11": squareNumber = 41; break; case "l11": squareNumber = 42; break; case "m11": squareNumber = 43; break;
			case "n11": squareNumber = 44; break; case "a10": squareNumber = 45; break; case "b10": squareNumber = 46; break; case "c10": squareNumber = 47; break;
			case "d10": squareNumber = 48; break; case "e10": squareNumber = 49; break; case "f10": squareNumber = 50; break; case "g10": squareNumber = 51; break;
			case "h10": squareNumber = 52; break; case "i10": squareNumber = 53; break; case "j10": squareNumber = 54; break; case "k10": squareNumber = 55; break;
			case "l10": squareNumber = 56; break; case "m10": squareNumber = 57; break; case "n10": squareNumber = 58; break; case "o10": squareNumber = 59; break;
			case "a9": squareNumber = 60; break; case "b9": squareNumber = 61; break; case "c9": squareNumber = 62; break; case "d9": squareNumber = 63; break;
			case "e9": squareNumber = 64; break; case "f9": squareNumber = 65; break; case "g9": squareNumber = 66; break; case "h9": squareNumber = 67; break;
			case "i9": squareNumber = 68; break; case "j9": squareNumber = 69; break; case "k9": squareNumber = 70; break; case "l9": squareNumber = 71; break;
			case "m9": squareNumber = 72; break; case "n9": squareNumber = 73; break; case "o9": squareNumber = 74; break; case "a8": squareNumber = 75; break;
			case "b8": squareNumber = 76; break; case "c8": squareNumber = 77; break; case "d8": squareNumber = 78; break; case "e8": squareNumber = 79; break;
			case "f8": squareNumber = 80; break; case "g8": squareNumber = 81; break; case "i8": squareNumber = 82; break; case "j8": squareNumber = 83; break;
			case "k8": squareNumber = 84; break; case "l8": squareNumber = 85; break; case "m8": squareNumber = 86; break; case "n8": squareNumber = 87; break;
			case "o8": squareNumber = 88; break; case "a7": squareNumber = 89; break; case "b7": squareNumber = 90; break; case "c7": squareNumber = 91; break;
			case "d7": squareNumber = 92; break; case "e7": squareNumber = 93; break; case "f7": squareNumber = 94; break; case "g7": squareNumber = 95; break;
			case "h7": squareNumber = 96; break; case "i7": squareNumber = 97; break; case "j7": squareNumber = 98; break; case "k7": squareNumber = 99; break;
			case "l7": squareNumber = 100; break; case "m7": squareNumber = 101; break; case "n7": squareNumber = 102; break; case "o7": squareNumber = 103; break;
			case "a6": squareNumber = 104; break; case "b6": squareNumber = 105; break; case "c6": squareNumber = 106; break; case "d6": squareNumber = 107; break;
			case "e6": squareNumber = 108; break; case "f6": squareNumber = 109; break; case "g6": squareNumber = 110; break; case "h6": squareNumber = 111; break;
			case "i6": squareNumber = 112; break; case "j6": squareNumber = 113; break; case "k6": squareNumber = 114; break; case "l6": squareNumber = 115; break;
			case "m6": squareNumber = 116; break; case "n6": squareNumber = 117; break; case "o6": squareNumber = 118; break; case "b5": squareNumber = 119; break;
			case "c5": squareNumber = 120; break; case "d5": squareNumber = 121; break; case "e5": squareNumber = 122; break; case "f5": squareNumber = 123; break;
			case "g5": squareNumber = 124; break; case "h5": squareNumber = 125; break; case "i5": squareNumber = 126; break; case "j5": squareNumber = 127; break;
			case "k5": squareNumber = 128; break; case "l5": squareNumber = 129; break; case "m5": squareNumber = 130; break; case "n5": squareNumber = 131; break;
			case "c4": squareNumber = 132; break; case "d4": squareNumber = 133; break; case "e4": squareNumber = 134; break; case "f4": squareNumber = 135; break;
			case "g4": squareNumber = 136; break; case "h4": squareNumber = 137; break; case "i4": squareNumber = 138; break; case "j4": squareNumber = 139; break;
			case "k4": squareNumber = 140; break; case "l4": squareNumber = 141; break; case "m4": squareNumber = 142; break; case "d3": squareNumber = 143; break;
			case "e3": squareNumber = 144; break; case "f3": squareNumber = 145; break; case "g3": squareNumber = 146; break; case "h3": squareNumber = 147; break;
			case "i3": squareNumber = 148; break; case "j3": squareNumber = 149; break; case "k3": squareNumber = 150; break; case "l3": squareNumber = 151; break;
			case "e2": squareNumber = 152; break; case "f2": squareNumber = 153; break; case "g2": squareNumber = 154; break; case "h2": squareNumber = 155; break;
			case "i2": squareNumber = 156; break; case "j2": squareNumber = 157; break; case "k2": squareNumber = 158; break; case "f1": squareNumber = 159; break;
			case "g1": squareNumber = 160; break; case "h1": squareNumber = 161; break; case "i1": squareNumber = 162; break; case "j1": squareNumber = 163; break;
			default: squareNumber = -1; break;
		}	
		
		return squareNumber;
	}
	
	/**
	 * Transform internal square representation to a String for output.
	 * 
	 * @param square : a number, used to internally store squares
	 * @return square in String form
	 */
	public static String numberToSquare(int square) {
		String squareText;
		
		switch(square) {
			case 0: squareText = "f15"; break; case 1: squareText = "g15"; break; case 2: squareText = "h15"; break; case 3: squareText = "i15"; break;
			case 4: squareText = "j15"; break; case 5: squareText = "e14"; break; case 6: squareText = "f14"; break; case 7: squareText = "g14"; break;
			case 8: squareText = "h14"; break; case 9: squareText = "i14"; break; case 10: squareText = "j14"; break; case 11: squareText = "k14"; break;
			case 12: squareText = "d13"; break; case 13: squareText = "e13"; break; case 14: squareText = "f13"; break; case 15: squareText = "g13"; break;
			case 16: squareText = "h13"; break;	case 17: squareText = "i13"; break;	case 18: squareText = "j13"; break; case 19: squareText = "k13"; break;
			case 20: squareText = "l13"; break;	case 21: squareText = "c12"; break;	case 22: squareText = "d12"; break;	case 23: squareText = "e12"; break;
			case 24: squareText = "f12"; break;	case 25: squareText = "g12"; break;	case 26: squareText = "h12"; break;	case 27: squareText = "i12"; break;
			case 28: squareText = "j12"; break;	case 29: squareText = "k12"; break;	case 30: squareText = "l12"; break;	case 31: squareText = "m12"; break;
			case 32: squareText = "b11"; break; case 33: squareText = "c11"; break;	case 34: squareText = "d11"; break;	case 35: squareText = "e11"; break;
			case 36: squareText = "f11"; break;	case 37: squareText = "g11"; break;	case 38: squareText = "h11"; break;	case 39: squareText = "i11"; break;
			case 40: squareText = "j11"; break;	case 41: squareText = "k11"; break;	case 42: squareText = "l11"; break;	case 43: squareText = "m11"; break;
			case 44: squareText = "n11"; break;	case 45: squareText = "a10"; break;	case 46: squareText = "b10"; break;	case 47: squareText = "c10"; break;
			case 48: squareText = "d10"; break;	case 49: squareText = "e10"; break;	case 50: squareText = "f10"; break;	case 51: squareText = "g10"; break;
			case 52: squareText = "h10"; break;	case 53: squareText = "i10"; break;	case 54: squareText = "j10"; break;	case 55: squareText = "k10"; break;
			case 56: squareText = "l10"; break;	case 57: squareText = "m10"; break;	case 58: squareText = "n10"; break;	case 59: squareText = "o10"; break;
			case 60: squareText = "a9"; break; case 61: squareText = "b9"; break; case 62: squareText = "c9"; break; case 63: squareText = "d9"; break;
			case 64: squareText = "e9"; break; case 65: squareText = "f9"; break; case 66: squareText = "g9"; break; case 67: squareText = "h9"; break;
			case 68: squareText = "i9"; break; case 69: squareText = "j9"; break; case 70: squareText = "k9"; break; case 71: squareText = "l9"; break;
			case 72: squareText = "m9"; break; case 73: squareText = "n9"; break; case 74: squareText = "o9"; break; case 75: squareText = "a8"; break;
			case 76: squareText = "b8"; break; case 77: squareText = "c8"; break; case 78: squareText = "d8"; break; case 79: squareText = "e8"; break;
			case 80: squareText = "f8"; break; case 81: squareText = "g8"; break; case 82: squareText = "i8"; break; case 83: squareText = "j8"; break;
			case 84: squareText = "k8"; break; case 85: squareText = "l8"; break; case 86: squareText = "m8"; break; case 87: squareText = "n8"; break;
			case 88: squareText = "o8"; break; case 89: squareText = "a7"; break; case 90: squareText = "b7"; break; case 91: squareText = "c7"; break;
			case 92: squareText = "d7"; break; case 93: squareText = "e7"; break; case 94: squareText = "f7"; break; case 95: squareText = "g7"; break;
			case 96: squareText = "h7"; break; case 97: squareText = "i7"; break; case 98: squareText = "j7"; break; case 99: squareText = "k7"; break;
			case 100: squareText = "l7"; break; case 101: squareText = "m7"; break; case 102: squareText = "n7"; break; case 103: squareText = "o7"; break;
			case 104: squareText = "a6"; break; case 105: squareText = "b6"; break; case 106: squareText = "c6"; break; case 107: squareText = "d6"; break;
			case 108: squareText = "e6"; break; case 109: squareText = "f6"; break; case 110: squareText = "g6"; break; case 111: squareText = "h6"; break;
			case 112: squareText = "i6"; break; case 113: squareText = "j6"; break; case 114: squareText = "k6"; break; case 115: squareText = "l6"; break;
			case 116: squareText = "m6"; break; case 117: squareText = "n6"; break; case 118: squareText = "o6"; break; case 119: squareText = "b5"; break;
			case 120: squareText = "c5"; break; case 121: squareText = "d5"; break; case 122: squareText = "e5"; break; case 123: squareText = "f5"; break;
			case 124: squareText = "g5"; break; case 125: squareText = "h5"; break; case 126: squareText = "i5"; break; case 127: squareText = "j5"; break;
			case 128: squareText = "k5"; break; case 129: squareText = "l5"; break; case 130: squareText = "m5"; break; case 131: squareText = "n5"; break;
			case 132: squareText = "c4"; break; case 133: squareText = "d4"; break; case 134: squareText = "e4"; break; case 135: squareText = "f4"; break;
			case 136: squareText = "g4"; break; case 137: squareText = "h4"; break; case 138: squareText = "i4"; break; case 139: squareText = "j4"; break;
			case 140: squareText = "k4"; break; case 141: squareText = "l4"; break; case 142: squareText = "m4"; break; case 143: squareText = "d3"; break;
			case 144: squareText = "e3"; break; case 145: squareText = "f3"; break; case 146: squareText = "g3"; break; case 147: squareText = "h3"; break;
			case 148: squareText = "i3"; break; case 149: squareText = "j3"; break; case 150: squareText = "k3"; break; case 151: squareText = "l3"; break;
			case 152: squareText = "e2"; break; case 153: squareText = "f2"; break; case 154: squareText = "g2"; break; case 155: squareText = "h2"; break;
			case 156: squareText = "i2"; break; case 157: squareText = "j2"; break; case 158: squareText = "k2"; break; case 159: squareText = "f1"; break;
			case 160: squareText = "g1"; break; case 161: squareText = "h1"; break; case 162: squareText = "i1"; break; case 163: squareText = "j1"; break;
			default: return "x";
		}
		
		return squareText;
	}
	
	/**
	 * Transform move which is stored as number internally to a String
	 * 
	 * @param move Number of the move in internal representation
	 * @return Move as String (readable to human)
	 */
	public static String numberToMove(int move) {
		return numberToSquare(move / 256) + numberToSquare(move % 256);
	}
	
	/**
	 * 
	 * @param piece Should be a char containing a the first letter of a piece.
	 * @return The numerical value of the piece.
	 */
	public static byte stringToPiece(char piece) {
		if (piece == 'D') {
			return 1;
		} else if (piece == 'T') {
			return -1;
		} else {
			return 0;
		}
	}
	
	public static String nodeCountOutput(long nodeCount) {
		long count = nodeCount;
		String output;
		if (count > 100000) {
			output = "KN";
			count /= 1000;
			if (count > 100000) {
				output = "MN";
				count /= 1000;
				if (count > 100000) {
					output = "GN";
					count /= 1000;
					if (count > 100000) {
						output = "TN";
						count /= 1000;
					}
				}
			}
		} else {
			output = "N";
		}
		output = count + output;
		return output;
	}
	
	/**
	 * 
	 * @param time The time, given in milli seconds.
	 * @return Time in a human readable format.
	 */
	public static String timeUsedOutput(long time) {
		long timeUsed = time;
		if (time > 60000) {
			timeUsed /= 1000;
			if (time > 3600000) {
				timeUsed /= 60;
				if (time > 86400000) {
					timeUsed /= 60;
					return (timeUsed / 24) + "d " + (timeUsed % 24) + "h";
				}
				return (timeUsed / 60) + "h" + (timeUsed % 60) + "m";
			}
			return (timeUsed / 60) + "m " + (timeUsed % 60) + "s";
		}
		return (timeUsed / 1000) + "s " + (timeUsed % 1000) + "ms";
	}
	
	private Transformation() {
	}
}
