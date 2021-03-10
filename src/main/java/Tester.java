import expression.expression_tools.OperationFabric;
import expression.expression_tools.Variable;
import expression.type.DoubleEType;
import expression.type.EType;


public class Tester {
    public static void main(String[] args) {
        OperationFabric<Double> fabric = new OperationFabric<>();
        fabric.add("add", "+", 31, 10, false, EType::add);
        Variable<Double> v =  new Variable<>("x");
        var add = fabric.create("add", v, v);
        var add2 = fabric.getBi("add");
        System.out.println(add2.apply(v, v).evaluate(new DoubleEType(20.0)));
        System.out.println(add.toTex());
    }
}
