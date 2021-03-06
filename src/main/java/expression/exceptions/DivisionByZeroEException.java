package expression.exceptions;

public class DivisionByZeroEException extends EvaluatingExpressionException {
    public DivisionByZeroEException(final Object arg) {
        super("Division by zero - division " + arg.toString() + " by 0");
    }
}

