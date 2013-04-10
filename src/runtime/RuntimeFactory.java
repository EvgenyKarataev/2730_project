package runtime;

public class RuntimeFactory {
	
	public static enum RuntimeType{
		Java,
		Python
	}
	
	/*
	private static RuntimeFactory instance = new RuntimeFactory();
	public static RuntimeFactory getInstance(){
		return instance;
	}
	*/
	
	private RuntimeFactory(){}
	
	public static RTIPRuntime createRuntime(RuntimeType type){
		if(type == RuntimeType.Java){
			return new RTIPJavaRuntime();
		}else if(type == RuntimeType.Python){
			return new RTIPPythonRuntime();
		}
		return null;
	}
}
