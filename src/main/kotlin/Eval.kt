class RuntimeError(val token: Token, val msg: String) : Exception(msg) {

}

fun eval(expr: Expr): Any? = when (expr) {
    is Expr.Literal -> expr.value
    is Expr.Grouping -> eval(expr.expr)
    is Expr.Binary -> evalBinary(expr)
    is Expr.Unary -> evalUnary(expr)
}


fun evalBinary(expr: Expr.Binary): Any? {
    val right = eval(expr.right)
    val left = eval(expr.left)
    return when (expr.operator.type) {
        TokenType.MINUS -> when {
            left is Double && right is Double -> left - right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.SLASH -> when {
            left is Double && right is Double -> left / right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.STAR -> when {
            left is Double && right is Double -> left * right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.PLUS -> when {
            left is Double && right is Double -> left + right
            left is String && right is String -> left + right
            else -> throw RuntimeError(expr.operator, "Operands must two numbers of two strings.")
        }
        TokenType.GREATER -> when {
            left is Double && right is Double -> left > right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.GREATER_EQUAL -> when {
            left is Double && right is Double -> left >= right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.LESS -> when {
            left is Double && right is Double -> left < right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.LESS_EQUAL -> when {
            left is Double && right is Double -> left <= right
            else -> throw RuntimeError(expr.operator, "Operands must be numbers.")
        }
        TokenType.BANG_EQUAL -> !isEqual(left, right)
        TokenType.EQUAL_EQUAL -> isEqual(left, right)
        else -> {
            null
        }
    }
}

fun isEqual(a: Any?, b: Any?): Boolean = when {
    a == null && b == null -> true
    a == null -> false
    else -> a == b
}

fun evalUnary(expr: Expr.Unary): Any? {
    val right = eval(expr.expr)
    return when (expr.operator.type) {
        TokenType.BANG -> !isTruthy(right)
        TokenType.MINUS -> when {
            right is Double -> -right
            else -> throw RuntimeError(expr.operator, "Operand must be a number.")
        }
        else -> null
    }
}

fun isTruthy(value: Any?): Boolean = when (value) {
    null -> false
    is Boolean -> value
    else -> true
}

