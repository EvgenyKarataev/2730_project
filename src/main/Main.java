package main;

import static org.junit.Assert.fail;

import java.io.IOException;

import runtime.RTIPRuntime;
import runtime.RuntimeFactory;

public class Main {
	
	public static void main(String[] args) {
		RTIPRuntime javaRuntime = RuntimeFactory
				.createRuntime(RuntimeFactory.RuntimeType.Java);
		try {
			System.out.println(javaRuntime.runCode(""));
		} catch (IOException ioe) {
			ioe.printStackTrace();
			fail();
		}
	}

}
