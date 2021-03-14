import algo.Optimization;
import algo.OptimizationResult;
import algo.Variant;
import expression.expression_tools.OperationFabric;
import expression.expression_tools.Variable;
import expression.parser.ExpressionParser;
import expression.parser.Parser;
import expression.type.DoubleEType;
import expression.type.EType;


public class Tester {
    public static void main(String[] args) {
        OptimizationResult result = Optimization.run(Optimization.DICHOTOMY, Variant.VAR_5, 0.001);
        System.out.println(result.getResult());
    }
}
