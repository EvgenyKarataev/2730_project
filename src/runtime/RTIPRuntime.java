package runtime;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public abstract class RTIPRuntime {
	
	protected static String code_dir = "E:/2730_project_test/";
	private SecureRandom random = new SecureRandom();

	// Used to generate random filename.
	protected String nextSessionId() {
		return "J" + new BigInteger(130, random).toString(32);
	}

	public abstract RuntimeResult runCode(String code) throws IOException;
	
	public class RuntimeResult {
		public boolean isSuccess;
		public String output;
		public long timeElapse;
		
		public boolean getIsSuccess(){
			return isSuccess;
		}
		
		public String getOutput(){
			return output;
		}
		
		public long getTimeElapse(){
			return timeElapse;
		}
	}
}
