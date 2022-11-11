package org.example;

/**
 * Symbols which can be used
 */
enum LexemeType {
    LEFT_BRACKET, RIGHT_BRACKET, OPERATION_PLUS, OPERATION_MINUS, OPERATION_MULT, OPERATION_DIV,
    NUMBER,
    END_OF_LINE;
}
/**
 * Container class
 */
public class symbol {
    LexemeType type;
    String value;

    /**
     * Constructor - creating a new object with specific values
     * @param type symbol which can be used
     * @param value processed symbol
     */
    public symbol(LexemeType type, String value) {
        this.type = type;
        this.value = value;
    }
    /**
     * Constructor - creating a new object with specific values
     * @param type symbol which can be used
     * @param value processed symbol
     */
    public symbol(LexemeType type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

    /**
     * Display method
     * @return line with information
     */
    public String toString() {
        return "Symbol{" + "type=" + type + ", value='" + value + '\'' + '}';
    }
}
