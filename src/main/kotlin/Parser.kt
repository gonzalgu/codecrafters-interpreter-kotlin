class Parser(val tokens: List<Token>){
    var current = 0
    fun parse():Expr{
        return expression()
    }

    fun expression():Expr{
        return equality()
    }

    fun equality():Expr{
        var expr = comparison()
        while(match(TokenType.BANG_EQUAL, TokenType.EQUAL_EQUAL)){
            val operator = previous()
            val right = comparison()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    fun match(vararg types: TokenType):Boolean{
        for(type in types){
            if(check(type)){
                advance()
                return true
            }
        }
        return false
    }

    fun comparison():Expr {
        var expr = term()
        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            val operator = previous()
            val right = term()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    fun term():Expr{
        var expr = factor()
        while(match(TokenType.MINUS, TokenType.PLUS)){
            val operator = previous()
            var right = factor()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    fun factor():Expr{
        var expr = unary()
        while(match(TokenType.SLASH, TokenType.STAR)){
            val operator = previous()
            val right = unary()
            expr = Expr.Binary(expr, operator, right)
        }
        return expr
    }

    fun unary():Expr{
        if(match(TokenType.BANG, TokenType.MINUS)){
            val operator = previous()
            val right = unary()
            return Expr.Unary(operator, right)
        }
        return primary()
    }

    fun primary():Expr{
        if(match(TokenType.FALSE)) return Expr.Literal(false)
        if(match(TokenType.TRUE)) return Expr.Literal(true)
        if(match(TokenType.NIL)) return Expr.Literal(null)
        if(match(TokenType.NUMBER, TokenType.STRING)){
            return Expr.Literal(previous().literal)
        }
        if(match(TokenType.LEFT_PAREN)){
            val expr = expression()
            consume(TokenType.RIGHT_PAREN, "Expect ')' after expression.")
            return Expr.Grouping(expr)
        }
        //error
        error(peek(), "Expect expression.")
        throw RuntimeException()
    }
    fun consume(type:TokenType, message:String):Token{
        if(check(type)){
            return advance()
        }
        error(peek(), message)
        throw RuntimeException()
    }

    fun check(type: TokenType):Boolean{
        return if(isAtEnd()){
            false
        }else{
            peek().type == type
        }
    }
    fun advance():Token{
        if(!isAtEnd()){
            current++
        }
        return previous()
    }
    fun peek():Token{
        return tokens[current]
    }
    fun isAtEnd():Boolean{
        return peek().type == TokenType.EOF
    }
    fun previous():Token{
        return tokens[current-1]
    }

}