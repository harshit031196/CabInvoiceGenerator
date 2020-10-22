package exception;

public class InvoiceServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6166634641136644488L;
	public ExceptionType type;

	public enum ExceptionType {
		NO_RIDE("Atleast 1 ride required for generating summary");

		private String exceptionMessage;

		ExceptionType(String exceptionMessage) {
			this.exceptionMessage = exceptionMessage;
		}
		public String getExceptionMessage() {
			return exceptionMessage;
		}
	}

	public InvoiceServiceException(ExceptionType type) {
		this.type = type;
	}
}
