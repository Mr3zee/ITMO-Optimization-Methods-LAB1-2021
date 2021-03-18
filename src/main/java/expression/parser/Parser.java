package expression.parser;

import expression.exceptions.ParsingExpressionException;
import expression.expression_tools.Expression;

public interface Parser<T extends Number> {
    Expression<T> parse(String expression) throws ParsingExpressionException;
}
