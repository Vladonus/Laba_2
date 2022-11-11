package org.example;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class with symbols list of expression
 */
public class symbol_list {
        private int position;
        public List<symbol> symbols;

    /**
     * Constructor - creating a new object with specific values
     * @param symbols a list of expression characters
     */
        public symbol_list(List<symbol> symbols) {
            this.symbols = symbols;
        }

    /**
     *method for moving to the next position
     * @return next element of the list
     */
        public symbol next() {
            return symbols.get(position++);
        }
    /**
     *method for changing position to the previous
     */
        public void back() {
            position--;
        }
    /**
     *method for getting position
     * @return position
     */
        public int getposition() {
            return position;
        }

    /**
     * Input expression parsing method
     * @param expression line with expression
     * @return character list
     */
    public static List<symbol> lexAnalyze(String expression) {
        ArrayList<symbol> symbols = new ArrayList<>();
        int position = 0;
        while (position< expression.length()) {
            char cur = expression.charAt(position);
            switch (cur) {
                case '(':
                    symbols.add(new symbol(LexemeType.LEFT_BRACKET, cur));
                    position++;
                    continue;
                case ')':
                    symbols.add(new symbol(LexemeType.RIGHT_BRACKET, cur));
                    position++;
                    continue;
                case '+':
                    symbols.add(new symbol(LexemeType.OPERATION_PLUS, cur));
                    position++;
                    continue;
                case '-':
                    symbols.add(new symbol(LexemeType.OPERATION_MINUS, cur));
                    position++;
                    continue;
                case '*':
                    symbols.add(new symbol(LexemeType.OPERATION_MULT, cur));
                    position++;
                    continue;
                case '/':
                    symbols.add(new symbol(LexemeType.OPERATION_DIV, cur));
                    position++;
                    continue;
                default:
                    if (cur <= '9' && cur >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(cur);
                            position++;
                            if (position >= expression.length()) {
                                break;
                            }
                            cur = expression.charAt(position);
                        } while (cur <= '9' && cur >= '0');
                        symbols.add(new symbol(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (cur != ' ') {
                            throw new RuntimeException("Unexpected character: " + cur);
                        }
                        position++;
                    }
            }
        }
        symbols.add(new symbol(LexemeType.END_OF_LINE, ""));
        return symbols;
    }

    /**
     * Method for finding the value of an expression
     * @param symbols a list of expression characters
     * @return expression value
     */
    public static int expression(symbol_list symbols) {
        symbol token = symbols.next();
        if (token.type == LexemeType.END_OF_LINE) {
            return 0;
        } else {
            symbols.back();
            return plusminus(symbols);
        }
    }

    /**
     * Method for finding the result between adjacent numbers
     * @param symbols a list of expression characters
     * @return calculation of an expression between adjacent numbers according to the symbol between them
     */
    public static int plusminus(symbol_list symbols) {
        int value = multdiv(symbols);
        while (true) {
            symbol token = symbols.next();
            switch (token.type) {
                case OPERATION_PLUS:
                    value += multdiv(symbols);
                    break;
                case OPERATION_MINUS:
                    value -= multdiv(symbols);
                    break;
                case END_OF_LINE:
                case RIGHT_BRACKET:
                    symbols.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + token.value
                            + " at position: " + symbols.getposition());
            }
        }
    }

    /**
     * Method for finding the result between adjacent numbers
     * @param symbols a list of expression characters
     * @return calculation of an expression between adjacent numbers according to the symbol between them
     */
    public static int multdiv(symbol_list symbols) {
        int value = factor(symbols);
        while (true) {
            symbol token = symbols.next();
            switch (token.type) {
                case OPERATION_MULT:
                    value *= factor(symbols);
                    break;
                case OPERATION_DIV:
                    value /= factor(symbols);
                    break;
                case END_OF_LINE:
                case RIGHT_BRACKET:
                case OPERATION_PLUS:
                case OPERATION_MINUS:
                    symbols.back();
                    return value;
                default:
                    throw new RuntimeException("Unexpected token: " + token.value
                            + " at position: " + symbols.getposition());
            }
        }
    }

    /**
     * Recursive descent method
     * @param symbols a list of expression characters
     * @return calculation of an expression between adjacent numbers according to the symbol between them
     */
    public static int factor(symbol_list symbols) {
        symbol token = symbols.next();
        switch (token.type) {
            case NUMBER:
                return Integer.parseInt(token.value);
            case LEFT_BRACKET:
                int value = plusminus(symbols);
                token = symbols.next();
                if (token.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + token.value
                            + " at position: " + symbols.getposition());
                }
                return value;
            default:
                 throw new RuntimeException("Unexpected token: " + token.value + " at position: " + symbols.getposition());

        }
    }
}
