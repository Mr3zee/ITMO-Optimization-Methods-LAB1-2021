package expression.exceptions;

public class UnderflowEEException extends EvaluatingExpressionException {
    public UnderflowEEException(final String operation, final Object first, final Object second) {
        super("Underflow - operation: \"" + operation + "\"; Arguments: (" + first.toString() + ", " + second.toString() + ")");
    }

    public UnderflowEEException(final String operation, final Object arg) {
        super("Underflow - operation: \"" + operation + "\"; Argument: (" + arg.toString() + ")");
    }
}
