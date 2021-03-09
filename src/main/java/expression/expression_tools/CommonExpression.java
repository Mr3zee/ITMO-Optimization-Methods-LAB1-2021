package expression.expression_tools;

public interface CommonExpression<T extends Number> extends Expression<T> {
    // Primary numbers used for hashCode():
    // Binary ops: 3527
    // Add: 3557
    // Abs: 1607
    // Divide: 1213
    // Multiply: 1747
    // Subtract: 2777
    // Unary ops: 2467
    // Negate: 2027
    // Cos: 2797
    // Arccos: 6361
    // Sin: 3463
    // Arcsin: 6379
    // Tan: 7873
    // Arctan: 7877
    // Exp: 3469
    // Ln: 7879
    // Logarithm: 4271
    // Log2: 5693
    // Power: 6827
    // Pow2: 4079

    // Abstract EType: 2399
    // Integer EType: 2153
    // Double EType: 1637
    // BigInteger EType: 2969
    // Unchecked Integer EType: 2213

    // Add / Subtract : 10
    // Multiply / Divide : 20
    // Power / Logarithm : 30
    // Variable / Const / Unary : 40

    int getPriority();

    boolean dependsOnOrder();

    // TODO: 06.03.2021 add new
}
