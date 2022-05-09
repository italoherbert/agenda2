package italo.agenda.exception;

public class SistemaException extends Exception {

	private static final long serialVersionUID = 1L;

	private String msg;
	private String[] params;
	
	public SistemaException( String msg, String... params ) {
		this.msg = msg;
		this.params = params;
	}
	
	public String mensagem() {
		String mensagem = msg;
		int k = 1;
		for( String param : params )
			mensagem = mensagem.replaceAll( "$"+(k++), param );		
		return mensagem;
	}
	
}
