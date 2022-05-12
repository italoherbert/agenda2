package italo.agenda.exception;

public class ValidationException extends SistemaException {

	private static final long serialVersionUID = 1L;

	public ValidationException( String msg, String... params ) {
		super( msg, params );
	}

}
