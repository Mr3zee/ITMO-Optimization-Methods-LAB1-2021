package expression.parser;

import expression.exceptions.*;
import expression.expression_tools.*;
import expression.type.EType;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExpressionParser<T extends Number> extends BaseParser implements Parser<T> {
    private final Function<String, EType<T>> parseEType;
    private Lexeme lastLexeme;

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
        lastLexeme = Lexeme.START;
        CommonExpression<T> result = additiveParse();
        if (hasNext()) {
            throw new ParenthesisAbsencePEException("opening", getExceptionParameters());
        }
        return result;
    }

    @SafeVarargs
    private Supplier<CommonExpression<T>> parseLevel(Supplier<CommonExpression<T>> nextLevel, LexemeInfo<T>... lexemes) {
        return () -> {
            CommonExpression<T> result = nextLevel.get();
            skipWhitespaces();
            String[] stringLexemes = Arrays.stream(lexemes).map(a -> a.lexeme.getOperand()).toArray(String[]::new);
            while (compare(stringLexemes)) {
                for (var lexeme : lexemes) {
                    if (compareAndSkip(lexeme.lexeme.getOperand())) {
                        lastLexeme = lexeme.lexeme;
                        result = lexeme.create(result, nextLevel.get());
                        break;
                    }
                }
                skipWhitespaces();
            }
            return result;
        };
    }

    private CommonExpression<T> additiveParse() throws ParsingExpressionException {
        CommonExpression<T> result = parseLevel(
                multiplicativeParse,
                new LexemeInfo<>(Lexeme.ADD, Add::new),
                new LexemeInfo<>(Lexeme.SUBTRACT, Subtract::new)
        ).get();
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
                lastLexeme = Lexeme.OPENING_PARENTHESIS;
                CommonExpression<T> result = additiveParse();
                if (compareAndSkip(")")) {
                    lastLexeme = Lexeme.CLOSING_PARENTHESIS;
                    return result;
                }
                throw new ParenthesisAbsencePEException("closing", getExceptionParameters());
            } else if (compare("x")) {
                lastLexeme = Lexeme.X;
                nextChar();
                return new Variable<>(lastLexeme.getOperand());
            } else if (isDigit()) {
                lastLexeme = Lexeme.NUMBER;
                return parseNumber(takeNumber());
            } else if (compareAndSkip("-")) {
                if (isDigit()) {
                    lastLexeme = Lexeme.NUMBER;
                    return parseNumber("-" + takeNumber());
                }
                lastLexeme = Lexeme.SUBTRACT;
                return new Negate<>(lowLevelParse.get());
            } else {
                String word = getCheckedWord();
                return expressionWrapper(word);
            }
        }
    };

    private final Supplier<CommonExpression<T>> powLogParse = parseLevel(
            lowLevelParse,
            new LexemeInfo<>(Lexeme.POWER, Power::new),
            new LexemeInfo<>(Lexeme.LOGARITHM, Logarithm::new)
    );

    private final Supplier<CommonExpression<T>> multiplicativeParse = parseLevel(
            powLogParse,
            new LexemeInfo<>(Lexeme.MULTIPLY, Multiply::new),
            new LexemeInfo<>(Lexeme.DIVIDE, Divide::new)
    );

    private String getCheckedWord() {
        if (LEXEMES.contains(getCurrentLex())) {
            throw missingLexemeOrIllegalSymbolException(getExceptionParameters());
        }
        String word = takeUnaryOperationWord();
        Lexeme lexeme = WORDS.get(word);
        if (lexeme == null || lexeme.getArity() != 1) {
            throw missingLexemeOrIllegalSymbolException(getExceptionParameters(word, source.getPosition() - word.length() - 1));
        }
        lastLexeme = lexeme;
        checkAfterWordSymbol();
        return word;
    }

    private void checkAfterWordSymbol() {
        if (!(Character.isWhitespace(getCurrentLex()) || compare("-", "(", "\0"))) {
            throw new MissingWhitespacePEException(lastLexeme.getName(), getExceptionParameters());
        }
    }

    private CommonExpression<T> expressionWrapper(String word) {
        CommonExpression<T> nextExpression = lowLevelParse.get();
        return switch (word) {
            case "pow2" -> new Pow2<>(nextExpression);
            case "log2" -> new Log2<>(nextExpression);
            case "ln" -> new Ln<>(nextExpression);
            case "exp" -> new Exp<>(nextExpression);
            case "sin" -> new Sin<>(nextExpression);
            case "cos" -> new Cos<>(nextExpression);
            case "tan" -> new Tan<>(nextExpression);
            case "arcsin" -> new Arcsin<>(nextExpression);
            case "arccos" -> new Arccos<>(nextExpression);
            case "arctan" -> new Arctan<>(nextExpression);
            default -> null;
        };
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
            return new MissingLexemePEException(lastLexeme.getName(), nextWord);
        }
        return new IllegalSymbolPEException(nextWord);
    }

    private static class LexemeInfo<T extends Number> {
        public final Lexeme lexeme;
        private final BiFunction<CommonExpression<T>, CommonExpression<T>, CommonExpression<T>> constructor;

        private LexemeInfo(final Lexeme lexeme, final BiFunction<CommonExpression<T>, CommonExpression<T>, CommonExpression<T>> constructor) {
            this.lexeme = lexeme;
            this.constructor = constructor;
        }

        private CommonExpression<T> create(final CommonExpression<T> first, final CommonExpression<T> second) {
            return constructor.apply(first, second);
        }
    }
}