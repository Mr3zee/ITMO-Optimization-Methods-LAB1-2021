import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;


public class Tester {
    public static void main(String[] args) {
        Parser<Double> parser = new ExpressionParser<>(DoubleEType::parseDouble);

        var e = parser.parse("4 // -0.5");
        System.out.println(e.evaluate(new DoubleEType(2.0)));
    }
}
