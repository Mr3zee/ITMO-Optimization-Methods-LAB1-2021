package expression.exceptions;

public class UOEException extends ExpressionException {
    public UOEException(String operation) {
        super(String.format("Unsupported operation \"%s\" for this type", operation));
    }
}
