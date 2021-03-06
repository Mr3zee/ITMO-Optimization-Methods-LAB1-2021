package expression.exceptions;

public class InvalidFunctionParametersEEException extends EvaluatingExpressionException {
    public InvalidFunctionParametersEEException(String name, Object arg) {
        super("Parameter(s) in function \"" + name + "\" is(are) invalid; Parameter(s): (" + arg.toString() + ")");
    }

    public InvalidFunctionParametersEEException(String name, Object arg1, Object arg2) {
        super("Parameter(s) in function \"" + name + "\" is(are) invalid; Parameter(s): (" + arg1.toString() + ", " + arg2.toString() + ")");
    }
}
