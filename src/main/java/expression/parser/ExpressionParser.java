package expression.parser;

import expression.exceptions.*;
import expression.expression_tools.*;
import expression.type.EType;

import java.util.Arrays;
import java.util.Set;
import java.util.function.*;

public class ExpressionParser<T extends Number> extends BaseParser implements Parser<T> {
    private final Function<String, EType<T>> parseEType;
    private final OperationFabric<T> fabric;
    private String lastLexeme;

    public ExpressionParser(Function<String, EType<T>> parseEType) {
        super(Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'x', 'y', 'z', '+', '-', '*', '/', '(', ')', '\0', '^'),
                Set.of(
                        Lexeme.COUNT,
                        Lexeme.LOGARITHM,
                        Lexeme.POW2,
                        Lexeme.LOG2,
                        Lexeme.LN,
                        Lexeme.EXP,
                        Lexeme.SIN,
                        Lexeme.ASIN,
                        Lexeme.COS,
                        Lexeme.ACOS,
                        Lexeme.TAN,
                        Lexeme.ATAN
                ));
        this.parseEType = parseEType;
        this.fabric = new OperationFabric<>();
        initFabric();
    }

    private void initFabric() {
        fabric.add("+" ,                                3557, 10, false, EType::add);
        fabric.add("-" ,                                2777, 10, true, EType::subtract);
        fabric.add("*" ,                                1747, 20, false, EType::multiply);
        fabric.add("/" ,                                1213, 20, true, EType::divide);
        fabric.add("^" ,                                6827, 30, false, EType::pow);
        fabric.add("//", "\\log_{%2$s} {%1$s}",   4271, 30, true, EType::log);

        fabric.add("abs"   , "\\left|%s\\right|", true, 1607, EType::abs);
        fabric.add("-"     , "-"                , false, 2027, EType::negate);
        fabric.add("cos"   , "\\cos"            , false, 2797, EType::cos);
        fabric.add("arccos", "\\arccos"         , false, 6361, EType::acos);
        fabric.add("sin"   , "\\sin"            , false, 3463, EType::sin);
        fabric.add("arcsin", "\\arcsin"         , false, 6379, EType::asin);
        fabric.add("tan"   , "\\tan"            , false, 7873, EType::tan);
        fabric.add("arctan", "\\arctan"         , false, 7877, EType::atan);
        fabric.add("exp"   , "\\exp"            , false, 3469, EType::exp);
        fabric.add("ln"    , "\\ln"             , false, 7879, EType::ln);
        fabric.add("log2"  , "\\log_2 {%s}"     , true, 5693, EType::log2);
        fabric.add("pow2"  ,  "2 ^ {%s}"        , true, 4079, EType::pow2);
    }

    @Override
    public CommonExpression<T> parse(final String expression) throws ParsingExpressionException {
        if (expression == null) return null;
        source = new ExpressionSource(expression);
        nextChar();
        skipWhitespaces();
        if (!hasNext()) {
            throw new EmptyExpressionPEException();
        }
        lastLexeme = "START";
        CommonExpression<T> result = additiveParse();
        if (hasNext()) {
            throw new ParenthesisAbsencePEException("opening", getExceptionParameters());
        }
        return result;
    }

    private Supplier<CommonExpression<T>> parseLevel(Supplier<CommonExpression<T>> nextLevel, String... lexemes) {
        return () -> {
            CommonExpression<T> result = nextLevel.get();
            skipWhitespaces();
            while (compare(lexemes)) {
                for (var lexeme : lexemes) {
                    if (compareAndSkip(lexeme)) {
                        lastLexeme = lexeme;
                        result = fabric.getBi(lexeme).apply(result, nextLevel.get());
                        break;
                    }
                }
                skipWhitespaces();
            }
            return result;
        };
    }

    private CommonExpression<T> additiveParse() throws ParsingExpressionException {
        CommonExpression<T> result = parseLevel(multiplicativeParse, "+", "-").get();
        if (compare("\0", ")")) {
            return result;
        }
        throw missingLexemeOrIllegalSymbolException(getExceptionParameters());
    }

    private final Supplier<CommonExpression<T>> lowLevelParse = new Supplier<>() {
        @Override
        public CommonExpression<T> get() {
            skipWhitespaces();
            if (compareAndSkip("(")) {
                lastLexeme = ")";
                CommonExpression<T> result = additiveParse();
                if (compareAndSkip(")")) {
                    lastLexeme = "(";
                    return result;
                }
                throw new ParenthesisAbsencePEException("closing", getExceptionParameters());
            } else if (compare("x")) {
                lastLexeme = "x";
                nextChar();
                return new Variable<>(lastLexeme);
            } else if (isDigit()) {
                lastLexeme = "number";
                return parseNumber(takeNumber());
            } else if (compareAndSkip("-")) {
                if (isDigit()) {
                    lastLexeme = "number";
                    return parseNumber("-" + takeNumber());
                }
                lastLexeme = "-";
                return fabric.getU("-").apply(lowLevelParse.get());
            } else {
                return parseUnary();
            }
        }
    };

    private final Supplier<CommonExpression<T>> powLogParse = parseLevel(lowLevelParse,"^", "//");

    private final Supplier<CommonExpression<T>> multiplicativeParse = parseLevel(powLogParse, "*", "/");

    private CommonExpression<T> parseUnary() {
        if (LEXEMES.contains(getCurrentLex())) {
            throw missingLexemeOrIllegalSymbolException(getExceptionParameters());
        }
        String word = takeUnaryOperationWord();
        UnaryOperator<CommonExpression<T>> operation = fabric.getU(word);
        if (operation == null) {
            throw missingLexemeOrIllegalSymbolException(getExceptionParameters(word, source.getPosition() - word.length() - 1));
        }
        lastLexeme = word;
        checkAfterWordSymbol();
        return operation.apply(lowLevelParse.get());
    }

    private void checkAfterWordSymbol() {
        if (!(Character.isWhitespace(getCurrentLex()) || compare("-", "(", "\0"))) {
            throw new MissingWhitespacePEException(lastLexeme, getExceptionParameters());
        }
    }

    private CommonExpression<T> parseNumber(final String number) throws ParsingExpressionException {
        try {
            return new Const<>(parseEType.apply(number));
        } catch (NumberFormatException e) {
            int position = source.getPosition() - number.length() - 1;
            if (checkDouble(number)) {
                throw new UnsupportedCastPEException(getExceptionParameters(number, position));
            }
            if (number.charAt(0) == '-') {
                throw new ConstantUnderflowPEException(getExceptionParameters(number, position));
            }
            throw new ConstantOverflowPEException(getExceptionParameters(number, position));
        }
    }

    private ParsingExpressionException missingLexemeOrIllegalSymbolException(ExceptionParameters nextWord) {
        if (findLexeme(nextWord.getWord())) {
            return new MissingLexemePEException(lastLexeme, nextWord);
        }
        return new IllegalSymbolPEException(nextWord);
    }
}