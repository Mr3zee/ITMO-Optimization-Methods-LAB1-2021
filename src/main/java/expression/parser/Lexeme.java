package expression.parser;


public enum Lexeme {
    ADD("Add", 2, "+"),
    SUBTRACT("Subtract", 2, "-"),
    MULTIPLY("Multiply", 2, "*"),
    DIVIDE("Divide", 2, "/"),
    X("Variable x", 1, "x"),
    OPENING_PARENTHESIS("Opening parenthesis", 1, "("),
    CLOSING_PARENTHESIS("Closing parenthesis", 1, ")"),
    NUMBER("Number", 1, ""),
    START("Start of the expression", 1, ""),
    COUNT("Count", 1, "count"),
    POWER("Power", 2, "^"),
    LOGARITHM("Logarithm", 2, "//"),
    POW2("Pow 2", 1, "pow2"),
    LOG2("Log 2", 1, "log2"),
    LN("Ln", 1, "ln"),
    EXP("Exp", 1, "exp"),
    SIN("Sin", 1, "sin"),
    COS("Cos", 1, "cos"),
    TAN("Tan", 1, "tan"),
    ASIN("Arcsin", 1, "arcsin"),
    ACOS("Arccos", 1, "arccos"),
    ATAN("Arctan", 1, "arctan"),
    ;

    private final String name;
    private final int arity;
    private final String operand;

    Lexeme(String name, int arity, String operand) {
        this.name = name;
        this.arity = arity;
        this.operand = operand;
    }

    public String getName() {
        return name;
    }

    public int getArity() {
        return arity;
    }

    public String getOperand() {
        return operand;
    }
}
