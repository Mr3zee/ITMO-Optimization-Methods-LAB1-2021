package expression.expression_tools;

import expression.exceptions.ParsingExpressionException;
import expression.parser.ExpressionParser;
import expression.type.DoubleEType;

public class DoubleEParser extends ExpressionParser<Double> {
    private static DoubleEParser instance;

    private DoubleEParser() {
        super(DoubleEType::parseDouble);
    }

    public static DoubleEParser getInstance() {
        if (instance == null) {
            instance = new DoubleEParser();
        }
        return instance;
    }
}
