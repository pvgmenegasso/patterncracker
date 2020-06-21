package brutepattern;
import java.security.*;
import java.util.ArrayList;
import java.util.Random;
public class brutepattern {



static int LSecretMax = 4;
static int LSecretMin = 4;
static int SecretMaxNumber = 0;

public static String genhash(int size, ArrayList<String> dones)
{
	String hash = "";
	// Generate array with every possible point to avoid repetition
	ArrayList<Integer> points = new ArrayList<Integer>() ;
	for(int i = 0 ; i<10 ; i++)
	{
		points.add(i) ;
	}
	for(int i = 0 ; i<size ; i++)
	{
		int rand = (int)(Math.random() * ((points.size()-1) + 1)) ;
		System.out.println("point pos:" +rand);
		int point = points.remove(rand) ;
		hash += point ;
	}
	while(dones.contains(hash.getBytes()))
	{
		hash = genhash(size, dones).toString() ;
	}
	dones.add(hash) ;
	
	System.out.println("pattern = "+hash);
	return hash ;
}
public static String bytesToHex(byte[] b) {
    char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                       '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    StringBuffer buf = new StringBuffer();
    for (int j=0; j<b.length; j++) {
       buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
       buf.append(hexDigit[b[j] & 0x0f]);
    }
    return buf.toString();
 }

public static String genandcompare(int LSecretMax,String target, ArrayList<String> dones)
{
	String pat = null;
	boolean thesame = false ;
	while(thesame == false)
	{
		System.out.println("target = " + target);
		pat = genhash(LSecretMax, dones) ;
		pat = patToHex(pat) ;
		System.out.println("hash: "+pat);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(pat.getBytes());
		byte[] output = md.digest();
		System.out.println("SHA1 result:" +byteArraytoHexString(output));
		
		System.out.println("Comparing: " + byteArraytoHexString(output)+ "  with: "+ byteArraytoHexString(target.getBytes()));
		if(byteArraytoHexString(target.getBytes()).contains(byteArraytoHexString(output)) ) 
		{
			thesame = true ;
		}
		System.out.println("patterns tested: " +dones.size()+"/3024") ;
		System.out.println(dones.toString());
	}
	return pat ;
}
private static String byteArraytoHexString(byte[] dataBytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : dataBytes) {
        sb.append(String.format("%02X", b));
    }
    return sb.toString().toUpperCase();
}

public static String patToHex(String hash)
{
	String hex = "" ;
	String pat = hash;
	System.out.println(pat) ;
	for(int i = 0 ; i < pat.length() ; i++)
	{
		hex +=  "0x0" +pat.charAt(i) ;
	}
	System.out.println(hex);
	return hex ;
}
/**
* @param args the command line arguments
*/
public static void main(String[] args) 
{
	ArrayList<String> dones = new ArrayList<String>() ;
	dones.clear();
	String target = "kMürÿÖråk.¸”œJuÆ\"e" ;

	genandcompare(4, target, dones) ;
}

}
