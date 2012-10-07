package mx.meido.binarygoes.binarypic;

public class BinaryPicException extends Exception {
	public BinaryPicException() {
		super();
	}
	
	public BinaryPicException(String msg){
		super(msg);
	}
	
	public BinaryPicException(Throwable cause){
		super(cause);
	}
	
	public BinaryPicException(String msg, Throwable cause){
		super(msg, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5187817678219457567L;

}
