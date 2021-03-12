import expression.expression_tools.OperationFabric;
import expression.expression_tools.Variable;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;
import expression.type.EType;


public class Tester {
    public static void main(String[] args) {
        int n = 17;
        double v = (Math.log(n - 1)) / Math.log(2);
        System.out.println(v);
        System.out.println(1 << (int)(v + 1));
    }
}
