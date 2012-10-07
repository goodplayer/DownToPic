package mx.meido.comm.stream;

public class StreamException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2459345137768701555L;

	public StreamException() {
		super();
	}
	
	public StreamException(String msg){
		super(msg);
	}
	
	public StreamException(Throwable cause){
		super(cause);
	}
	
	public StreamException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
