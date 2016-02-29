package com.benjamindward.compiler;

public class CompilerComponent {
	private Compiler compiler; // The compiler that the component is a member of
	/**
	 * Get parent compiler
	 * @return Parent compiler
	 */
	public Compiler getCompiler() {
		return compiler;
	}
	
    /**
     * Constructs new compiler component, saving parent compiler
     * @param compiler Parent compiler
     */
    public CompilerComponent(Compiler compiler)
    {
        this.compiler = compiler;
    }
}
