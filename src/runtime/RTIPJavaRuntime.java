package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class RTIPJavaRuntime extends RTIPRuntime {

	private JavaCompiler compiler;
	private StandardJavaFileManager fileManager;

	public RTIPJavaRuntime() {
		System.setProperty("java.home", "D:/Java/jdk1.7");
		compiler = ToolProvider.getSystemJavaCompiler();
		fileManager = compiler.getStandardFileManager(null, null, null);
	}

	// Java code does not need to declare "class"
	@Override
	public RuntimeResult runCode(String code) throws IOException {

		// Write code to a temp file.
		String filename = nextSessionId();
		File codeFile = new File(code_dir + filename + ".java");
		for (int i = 1; codeFile.exists(); i++) {
			filename += "_" + i;
			codeFile = new File(code_dir + filename + ".java");
		}

		// Wrap class declaration.
		code = "public class " + filename + "{" + code + "}";

		PrintWriter out = new PrintWriter(new FileWriter(codeFile));
		out.write(code);
		out.close();

		// Compile java code.
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
				.getJavaFileObjects(codeFile);

		/* Create a diagnostic controller, which holds the compilation problems */
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		boolean isSuccess = compiler.getTask(null, fileManager, diagnostics,
				null, null, compilationUnits).call();

		fileManager.close();

		StringBuilder output = new StringBuilder();
		StringBuilder errOutput = new StringBuilder();

		if (isSuccess) {

			// Run java code.
			Process process = Runtime.getRuntime().exec("java " + filename,
					null, new File(code_dir));
			// Process process = Runtime.getRuntime().exec("java " + code_dir +
			// filename);
			BufferedReader execResult = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			BufferedReader errorResult = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));

			// Return output.
			String resultStr;
			while ((resultStr = execResult.readLine()) != null) {
				output.append(resultStr);
			}

			// Output error.
			String errorStr;
			while ((errorStr = errorResult.readLine()) != null) {
				errOutput.append(errorStr);
			}

			execResult.close();
			errorResult.close();
			process.destroy();

		} else {
			 /*Iterate through each compilation problem and print it*/
		    for (Diagnostic diagnostic : diagnostics.getDiagnostics()){
		    	errOutput.append(String.format("Error<%d>: %s", diagnostic.getLineNumber(), diagnostic.getMessage(null)));
		    }		
		}
		
		RuntimeResult runtimeResult = new RuntimeResult();
		runtimeResult.isSuccess = isSuccess;
		
		if (errOutput.length() > 0) {
			runtimeResult.output = errOutput.toString();
		} else {
			runtimeResult.output = output.toString();
		}
		
		return runtimeResult;
	}
}
