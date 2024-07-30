sealed class Expr(){
    data class Grouping(val expr: Expr):Expr()
    data class Unary(val operator: Token, val expr: Expr):Expr()
    data class Binary(val left: Expr, val operator:Token, val right: Expr):Expr()
    data class Literal(val value:Any?):Expr()
}

fun printAst(expr:Expr):String = when(expr){
        is Expr.Grouping -> parenthesize("group", expr.expr)
        is Expr.Unary -> parenthesize(expr.operator.lexeme, expr.expr)
        is Expr.Literal -> {
            if(expr.value == null){
                "nil"
            }else{
                expr.value.toString()
            }
        }
        is Expr.Binary -> {
            parenthesize(expr.operator.lexeme, expr.left, expr.right)
        }
    }

fun parenthesize(name:String, vararg exprs:Expr):String {
    var result = "(${name}"
    for(expr in exprs){
        result += " ${printAst(expr)}"
    }
    result += ")"
    return result
}





