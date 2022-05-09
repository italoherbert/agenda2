package italo.agenda.exception;

public class ServiceException extends SistemaException {

	private static final long serialVersionUID = 1L;

	public ServiceException( String msg, String... params ) {
		super( msg, params );
	}
	
}
