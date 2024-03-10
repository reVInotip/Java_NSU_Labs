import java.util.ArrayDeque;

import static java.lang.Character.isDigit;

final public class TreeCalculator {
    static private int currIndex = 0;

    static private double parseDigit(String arithmeticExpression) {
        int startIndex = currIndex;
        while (currIndex < arithmeticExpression.length() - 1 && isDigit(arithmeticExpression.charAt(currIndex))) {
            ++currIndex;
        }
        if (currIndex > startIndex) {
            return Double.parseDouble(arithmeticExpression.substring(startIndex, currIndex));
        } else if (currIndex == arithmeticExpression.length() - 1) {
            return Double.parseDouble(String.valueOf(arithmeticExpression.charAt(startIndex)));
        }
        return -1;
    }

    static private Node parseMulOrDivOrMod(String arithmeticExpression) {
        double value = parseDigit(arithmeticExpression);
        char operation = arithmeticExpression.charAt(currIndex);
        Node rightTree = null;
        Node currTree = new Node(Operations.VAL, value, null, null);
        while (operation == '*' || operation == '/' || operation == '%') {
            ++currIndex;

            value = parseDigit(arithmeticExpression);
            rightTree = new Node(Operations.VAL, value, null, null);
            currTree = new Node(Operations.getOperation(operation), operation, currTree, rightTree);

            operation = arithmeticExpression.charAt(currIndex);
        }

        return currTree;
    }

    static private Node parseMulOrDivOrMod(String arithmeticExpression, Node currTree, char operation) {
        Node rightTree = null;
        double value;
        while (operation == '*' || operation == '/' || operation == '%') {
            ++currIndex;

            value = parseDigit(arithmeticExpression);
            rightTree = new Node(Operations.VAL, value, null, null);
            currTree = new Node(Operations.getOperation(operation), operation, currTree, rightTree);

            operation = arithmeticExpression.charAt(currIndex);
        }

        return currTree;
    }

    static private Node parseAddOrSub(String arithmeticExpression) {
        double value = parseDigit(arithmeticExpression);
        char operation = arithmeticExpression.charAt(currIndex);
        Node tree = new Node(Operations.VAL, value, null, null);
        if (operation != '+' && operation != '-') {
            tree = parseMulOrDivOrMod(arithmeticExpression, tree, operation);
            operation = arithmeticExpression.charAt(currIndex);
        }

        while (operation == '+' || operation == '-') {
            ++currIndex;

            Node rightTree = parseMulOrDivOrMod(arithmeticExpression);
            tree = new Node(Operations.getOperation(operation), operation, tree, rightTree);

            operation = arithmeticExpression.charAt(currIndex);
        }

        return tree;
    }

    static public Node parse(String arithmeticExpression) {
        Node tree = parseAddOrSub(arithmeticExpression);
        return tree;
    }

    static public void print(Node tree) {
        ArrayDeque<Node> children = new ArrayDeque<Node>();
        Node.printContent(tree);
        if (tree.left != null && tree.right != null) {
            children.add(tree.left);
            children.add(tree.right);
        }

        Node currNode;
        int isNextLevel = 0;
        while (!children.isEmpty()) {
            currNode = children.pop();
            Node.printContent(currNode);
            if (isNextLevel == 1) {
                isNextLevel = 0;
            }

            if (currNode.left != null) {
                children.add(currNode.left);
            }
            if (currNode.right != null) {
                children.add(currNode.right);
            }

            ++isNextLevel;
        }
    }

    static public double calculate(Node tree) {
        if(tree.operation == Operations.VAL) {
            return tree.value;
        }

        assert tree.left != null;
        assert tree.right != null;
        double leftVal = calculate(tree.left);
        double rightVal = calculate(tree.right);

        double result = 0;
        switch (tree.operation) {
            case ADD -> result = leftVal + rightVal;
            case SUB -> result = leftVal - rightVal;
            case MUL -> result = leftVal * rightVal;
            case DIV -> result = leftVal / rightVal;
            case MOD -> result = leftVal % rightVal;
            case VAL -> result = 0;
        }

        return result;
    }
}
