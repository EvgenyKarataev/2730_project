package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import runtime.RTIPRuntime;
import runtime.RuntimeFactory;

public class RuntimeTest{

	@Test
	public void runJavaCode() {
		RTIPRuntime javaRuntime = RuntimeFactory
				.createRuntime(RuntimeFactory.RuntimeType.Java);
		try {
			System.out.println(javaRuntime.runCode("public static void main(String[] args){System.out.println(\"Hello World\");}"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}				
	}
}
