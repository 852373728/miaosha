package com.sun.exception;

import com.sun.result.CodeMsg;

public class MyselfException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private CodeMsg codeMsg;
	
	public MyselfException(CodeMsg cm) {
		this.codeMsg=cm;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
	
	
	
}
