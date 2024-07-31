
class RuntimeError(token: Token, message:String):Exception(message){

}

fun eval(expr:Expr):Any? = when(expr){
    is Expr.Literal -> expr.value
    is Expr.Grouping -> eval(expr.expr)
    is Expr.Binary -> evalBinary(expr)
    is Expr.Unary -> evalUnary(expr)
}



fun evalBinary(expr:Expr.Binary):Any?{
    val right = eval(expr.right)
    val left = eval(expr.left)
    return when(expr.operator.type){
        TokenType.MINUS -> {
            if(left is Double && right is Double){
                left - right
            }else{
                null
            }
        }
        TokenType.SLASH -> {
            if(left is Double && right is Double){
                left / right
            }else{
                null
            }
        }
        TokenType.STAR -> {
            if(left is Double && right is Double){
                left * right
            }else{
                null
            }
        }
        TokenType.PLUS -> {
            when{
                left is Double && right is Double -> left + right
                left is String && right is String -> left + right
                else -> null
            }
        }
        TokenType.GREATER -> {
            when{
                left is Double && right is Double -> left > right
                else -> null
            }
        }
        TokenType.GREATER_EQUAL -> {
            if(left is Double && right is Double){
                left >= right
            }else{
                null
            }
        }
        TokenType.LESS -> {
            if(left is Double && right is Double){
                left < right
            }else{
                null
            }
        }
        TokenType.LESS_EQUAL -> {
            if(left is Double && right is Double){
                left <= right
            }else{
                null
            }
        }
        TokenType.BANG_EQUAL -> !isEqual(left, right)
        TokenType.EQUAL_EQUAL -> isEqual(left, right)
        else -> {
            null
        }
    }
}

fun isEqual(a:Any?, b:Any?):Boolean = when{
    a == null && b == null -> true
    a == null -> false
    else -> a == b
}


fun evalUnary(expr:Expr.Unary):Any?{
    val right = eval(expr.expr)
    return when(expr.operator.type){
        TokenType.BANG -> !isTruthy(right)
        TokenType.MINUS ->
            if(right is Double){
                -right
            }else{
                null
            }
        else -> null
    }
}

fun isTruthy(value:Any?):Boolean = when(value){
    null -> false
    is Boolean -> value
    else -> true
}

/*
fun checkNumberOperand(token:Token, operand:Any):Unit{
    if(operand !is Double){
        throw RuntimeException("token:$token message: Operand must be a number")
    }
}

fun checkNumberOperands():Unit{
    throw RuntimeException()
}
 */