class Lexer(val source: String) {
    var start: Int = 0
    var current: Int = 0
    var line: Int = 1

    private var keywords = mapOf<String, TokenType>(
        Pair("and", TokenType.AND),
        Pair("class", TokenType.CLASS),
        Pair("else", TokenType.ELSE),
        Pair("false", TokenType.FALSE),
        Pair("for", TokenType.FOR),
        Pair("fun", TokenType.FUN),
        Pair("if", TokenType.IF),
        Pair("nil", TokenType.NIL),
        Pair("or", TokenType.OR),
        Pair("print", TokenType.PRINT),
        Pair("return", TokenType.RETURN),
        Pair("super", TokenType.SUPER),
        Pair("this", TokenType.THIS),
        Pair("var", TokenType.VAR),
        Pair("while", TokenType.WHILE)
    )

    private var tokens = mutableListOf<Token>()

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            scan()
        }
        tokens.addLast(Token(TokenType.EOF, "", null, line))
        return tokens
    }

    private fun scan() {
        val c = advance()
        when (c) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '{' -> addToken(TokenType.LEFT_BRACE)
            '}' -> addToken(TokenType.RIGHT_BRACE)
            ',' -> addToken(TokenType.COMMA)
            '.' -> addToken(TokenType.DOT)
            '-' -> addToken(TokenType.MINUS)
            '+' -> addToken(TokenType.PLUS)
            ';' -> addToken(TokenType.SEMICOLON)
            '*' -> addToken(TokenType.STAR)
            '!' -> addToken(if (match('=')) TokenType.BANG_EQUAL else TokenType.BANG)
            '=' -> addToken(if (match('=')) TokenType.EQUAL_EQUAL else TokenType.EQUAL)
            '<' -> addToken(if (match('=')) TokenType.LESS_EQUAL else TokenType.LESS)
            '>' -> addToken(if (match('=')) TokenType.GREATER_EQUAL else TokenType.GREATER)
            '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) {
                        advance()
                    }
                } else {
                    addToken(TokenType.SLASH)
                }
            }

            ' ', '\r', '\t' -> {
                //ignore whitespace
            }

            '\n' -> {
                line++
            }

            '"' -> {
                string()
            }

            else -> {
                if (isDigit(c)) {
                    number()
                } else if (isAlpha(c)) {
                    identifier()
                } else {
                    error(line, "Unexpected character:$c\n")
                    hadError = true
                }
            }
        }
    }

    private fun isAlpha(char: Char): Boolean {
        return char in 'a'..'z'
                || char in 'A'..'Z'
                || char == '_'
    }

    private fun isDigit(c: Char): Boolean {
        return c in '0'..'9'
    }

    private fun isAlphaNumeric(c: Char): Boolean {
        return isAlpha(c) || isDigit(c)
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) {
            advance()
        }
        var text = source.substring(start until current)
        var type = keywords[text]
        if (type == null) {
            type = TokenType.IDENTIFIER
        }
        addToken(type)
    }

    private fun number() {
        while (isDigit(peek())) {
            advance()
        }
        if (peek() == '.' && isDigit(peekNext())) {
            advance()
            while (isDigit(peek())) {
                advance()
            }
        }
        val text = source.substring(start until current)
        addToken(TokenType.NUMBER, text.toDouble())
    }

    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') {
                line++
            }
            advance()
        }
        if (isAtEnd()) {
            error(line, "Unterminated string.")
            return
        }
        advance()
        val value = source.substring(start + 1 until current - 1)
        addToken(TokenType.STRING, value)
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) {
            return false
        }
        if (source[current] != expected) {
            return false
        }
        current++
        return true
    }

    private fun peekNext(): Char {
        return if (current + 1 >= source.length) {
            0.toChar()
        } else {
            source[current + 1]
        }
    }

    private fun isAtEnd(): Boolean {
        return current >= this.source.length
    }

    private fun advance(): Char {
        return this.source[this.current++]
    }

    private fun peek(): Char {
        return if (isAtEnd()) {
            0.toChar()
        } else {
            source[current]
        }
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start until current)
        tokens.addLast(Token(type, text, literal, line))
    }
}