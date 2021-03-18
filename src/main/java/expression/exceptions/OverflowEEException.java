package expression.exceptions;

public class OverflowEEException extends EvaluatingExpressionException {
    public OverflowEEException(final String operation, final Object arg) {
        super("Overflow - operation: \"" + operation + "\"; Argument: (" + arg.toString() + ")");
    }

    public OverflowEEException(final String operation, final Object first, final Object second) {
        super("Overflow - operation: \"" + operation + "\"; Arguments: (" + first.toString() + ", " + second.toString() + ")");
    }
}