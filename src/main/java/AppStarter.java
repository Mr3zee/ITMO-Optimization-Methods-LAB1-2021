import algo.Optimization;
import algo.OptimizationResult;
import algo.Variant;
import app.App;

public class AppStarter {
    public static void main(String[] args) {
//        App.main(args);
        OptimizationResult result = Optimization.run(Optimization.GOLDEN_SECTION, Variant.VAR_5);
        System.out.println(result.getResult());
        OptimizationResult result1 = Optimization.run(Optimization.DICHOTOMY, Variant.VAR_5);
        System.out.println(result1.getResult());
    }
}
