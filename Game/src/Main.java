import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		checkArgs(args);
		byte[] secretKeyBytes = getKey(256);
		int cumputerTurn = ThreadLocalRandom.current().nextInt(0, args.length);
		System.out.println("HMAC: "+generateHMAC(secretKeyBytes, args[cumputerTurn]));
		int userTurn = getUserTurn(args);
		if(userTurn == 0) {
			System.exit(0);
		}
		String secretKeyString = convertBytesToString(secretKeyBytes);
		if(userTurn-1 == cumputerTurn) {
			System.out.println("Friendship won!");
		} else if(isUserWinner(cumputerTurn, userTurn-1, args)) {
			System.out.println("You won!");
		} else {
			System.out.println("Computer won!");
		}
		System.out.println("HMAC Key: "+secretKeyString);
	}

	public static String generateHMAC(byte[] key, String message) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		SecretKeySpec skey = new SecretKeySpec(key, "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(skey);
		byte[] hmacBytes = mac.doFinal(message.getBytes("ASCII"));
	    StringBuffer hash = new StringBuffer();
	    for (int i = 0; i < hmacBytes.length; i++) {
	    	String hex = Integer.toHexString(0xFF & hmacBytes[i]);
	    	if (hex.length() == 1) {
	    		hash.append('0');
	    	}
	    	hash.append(hex);
	    }
	    return hash.toString();
	}
	
	public static byte[] getKey(int length) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		SecretKey key = keyGen.generateKey();
		return key.getEncoded();
	}
		
	public static int getUserTurn(String[] turnNames) {
		int turn = -1;
		String input = null;
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("Your turn:");
			for(int i = 0; i<turnNames.length; i++) {
				System.out.println((i+1)+". "+turnNames[i]);
			}
			System.out.println("0. Exit\n");
			System.out.print("Your turn: ");
			input = scanner.nextLine();
			try {
				turn = Integer.parseInt(input);
				if(turn >= 0 && turn <= turnNames.length) {
					break;
				}
			}catch(NumberFormatException p){
				System.out.println("Incorrect input!");
				continue;
			}
		}
		scanner.close();
		return turn;
	}
	
	public static boolean isUnique(String[] params) {
		for(int i = 0; i < params.length; i++) {
			for(int k = i+1; k < params.length; k++) {
				if(params[i].equals(params[k])) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void checkArgs(String[] args) {
		if(args.length < 3) {
			System.out.println("You have not specified the required number of parameters!");
			System.exit(1);
		}else if(args.length%2 == 0) {
			System.out.println("You have specified an even number of parameters!");
			System.exit(2);
		}else if(!isUnique(args)) {
			System.out.println("The parameters you specified are not unique!");
			System.exit(3);
		}
	}

	public static String convertBytesToString(byte[] input) {
		return new String(Base64.getEncoder().encode(input));
	}

	public static boolean isUserWinner(int cumputerTurn, int userTurn, String[] moves) {
		System.out.println("Your move: " + moves[userTurn]);
		System.out.println("Computer move: " + moves[cumputerTurn]);
		if(cumputerTurn < userTurn) {
			if(userTurn - cumputerTurn <= (moves.length-1)/2) {
				return false;
			} else {
				return true;
			}
		} else {
			if(userTurn - cumputerTurn <= (moves.length-1)/2) {
				return true;
			} else {
				return false;
			}
		}

	}

}