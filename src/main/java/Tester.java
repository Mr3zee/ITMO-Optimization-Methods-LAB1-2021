import expression.expression_tools.OperationFabric;
import expression.expression_tools.Variable;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;
import expression.type.EType;


public class Tester {
    public static void main(String[] args) {
        Parser<Double> parser = new ExpressionParser<>(DoubleEType::parseDouble);
        System.out.println(parser.parse("sin(x)").toFunction(DoubleEType::toType).apply(Math.PI));
    }
}
