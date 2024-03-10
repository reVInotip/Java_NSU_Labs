public enum Operations {
    ADD(),
    SUB(),
    MUL(),
    DIV(),
    MOD(),
    VAL();

    static public Operations getOperation(char operation) {
        switch (operation) {
            case '+':
                return ADD;
            case '-':
                return SUB;
            case '*':
                return MUL;
            case '/':
                return DIV;
            case '%':
                return MOD;
            default:
                return VAL;
        }
    }

    static public char getOperationSymbol(Operations operation) {
        return switch (operation) {
            case ADD -> '+';
            case SUB -> '-';
            case MUL -> '*';
            case DIV -> '/';
            case MOD -> '%';
            case VAL -> '\0';
        };
    }
}
