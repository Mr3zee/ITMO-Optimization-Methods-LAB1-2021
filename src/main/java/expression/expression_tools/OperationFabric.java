package expression.expression_tools;

import expression.type.EType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class OperationFabric<T extends Number> {

    private final Map<String, BinaryOperator<CommonExpression<T>>> biMap;
    private final Map<String, UnaryOperator<CommonExpression<T>>> uMap;

    public OperationFabric() {
        this.biMap = new HashMap<>();
        this.uMap = new HashMap<>();
    }

    public CommonExpression<T> create(
        String name,
        CommonExpression<T> first,
        CommonExpression<T> second
    ) {
        return getBi(name).apply(first, second);
    }

    public CommonExpression<T> create(
            String name,
            CommonExpression<T> arg
    ) {
        return getU(name).apply(arg);
    }

    public BinaryOperator<CommonExpression<T>> getBi(String name) {
        return biMap.get(name);
    }

    public UnaryOperator<CommonExpression<T>> getU(String name) {
        return uMap.get(name);
    }

    public void add(
            String operand,
            int primary,
            int priority,
            boolean dependsOnOrder,
            BinaryOperator<EType<T>> f
    ) {
        BiFactory newFactory = new BiFactory(operand, primary, priority, dependsOnOrder, f);
        biMap.put(operand, newFactory.create);
    }

    public void add(
            String operand,
            String format,
            int primary,
            int priority,
            boolean dependsOnOrder,
            BinaryOperator<EType<T>> f
    ) {
        BiFactory newFactory = new BiFactory(operand, format, primary, priority, dependsOnOrder, f);
        biMap.put(operand, newFactory.create);
    }

    public void add(
            String operand,
            String texString,
            boolean override,
            int primary,
            UnaryOperator<EType<T>> f
    ) {
        UFactory newFactory = new UFactory(operand, texString, override, primary, f);
        uMap.put(operand, newFactory.create);
    }

    public boolean containsU(String name) {
        return uMap.containsKey(name);
    }

    public boolean containsBi(String name) {
        return biMap.containsKey(name);
    }

    private abstract static class Factory {
        protected final String operand;
        protected final int primary;
        protected final int priority;
        protected final boolean dependsOnOrder;

        private Factory(
                String operand,
                int primary,
                int priority,
                boolean dependsOnOrder
        ) {
            this.operand = operand;
            this.primary = primary;
            this.priority = priority;
            this.dependsOnOrder = dependsOnOrder;
        }
    }

    private class BiFactory extends Factory {
        private final BinaryOperator<EType<T>> f;
        private final boolean override;
        private final String format;

        private BiFactory(
                String operand,
                int primary,
                int priority,
                boolean dependsOnOrder,
                BinaryOperator<EType<T>> f
        ) {
            super(operand, primary, priority, dependsOnOrder);
            this.f = f;
            this.override = false;
            this.format = null;
        }

        private BiFactory(
                String operand,
                String format,
                int primary,
                int priority,
                boolean dependsOnOrder,
                BinaryOperator<EType<T>> f
        ) {
            super(operand, primary, priority, dependsOnOrder);
            this.f = f;
            this.override = true;
            this.format = format;
        }

        public BinaryOperator<CommonExpression<T>> create = Operation::new;

        private class Operation extends BinaryOperation<T> {
            public Operation(CommonExpression<T> firstExp, CommonExpression<T> secondExp) {
                super(firstExp, secondExp);
            }

            @Override
            protected EType<T> toCalculate(EType<T> firstArg, EType<T> secondArg) {
                return f.apply(firstArg, secondArg);
            }

            @Override
            public int getPriority() {
                return priority;
            }

            @Override
            public String toTex() {
                return format == null ? super.toTex() : String.format(format, firstExp.toTex(), secondExp.toTex());
            }

            @Override
            public boolean dependsOnOrder() {
                return dependsOnOrder;
            }

            @Override
            public String getOperand() {
                return operand;
            }

            @Override
            public int primary() {
                return primary;
            }
        }
    }

    private class UFactory extends Factory {
        private final String texString;
        private final boolean override;
        private final UnaryOperator<EType<T>> f;

        private UFactory(
                String operand,
                String texString,
                boolean override,
                int primary,
                UnaryOperator<EType<T>> f
        ) {
            super(operand, primary, 40, false);
            this.f = f;
            this.texString = texString;
            this.override = override;
        }

        public UnaryOperator<CommonExpression<T>> create = Operation::new;

        private class Operation extends UnaryOperation<T> {
            public Operation(CommonExpression<T> arg) {
                super(arg);
            }

            @Override
            protected EType<T> toCalculate(EType<T> arg) {
                return f.apply(arg);
            }

            @Override
            public String getOperand() {
                return operand;
            }

            @Override
            public String toTex() {
                return override ? String.format(texString, expression.toTex()) : super.toTex();
            }

            @Override
            public String getTexOperand() {
                return texString;
            }

            @Override
            public int primary() {
                return primary;
            }
        }
    }
    
}
