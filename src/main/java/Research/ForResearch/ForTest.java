package Research.ForResearch;

public class ForTest {

	public static void main(String[] args) {
//		isSubstringPresent

	}
	
    public boolean isSubstringPresent(String s) {
        
    	String sReverse = new StringBuilder(s).reverse().toString();
        
    	for (int i = 0; i < s.length()-1; i++) {
    		String sSub = s.substring(i, i+2);
    		if(sReverse.contains(sSub)) {
    			return true;
    		}
    	}
        return false;
    }
	
}
