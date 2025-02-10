package dev.parser;

import dev.ast.expressions.*;
import dev.ast.statements.AssignmentStatement;
import dev.ast.statements.IfStatement;
import dev.ast.statements.PrintStatement;
import dev.ast.statements.Statement;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<Token> tokens;
    static final Token EOF = new Token(TokenType.EOF, "");
    int position;
    int size;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.size = tokens.size();
    }

    public List<Statement> parse(){
        final List<Statement> result = new ArrayList<>();
        while(!match(TokenType.EOF)){
            result.add(statement());
        }
        return result;
    }

    public Statement statement(){
        if(match(TokenType.PRINT)){
            return new PrintStatement(expression());
        }

        if(match(TokenType.IF)){
            return ifElse();
        }

        return assignmentStatement();
    }

    private Statement assignmentStatement(){
        final Token current = get(0);
        if(match(TokenType.WORD) && get(0).getType() == TokenType.EQUALS){
            final String variable = current.getText();
            consume(TokenType.EQUALS);
            return new AssignmentStatement(variable, expression());
        }
        throw new RuntimeException("Unknown statement");

    }

    private Statement ifElse(){
        final Expression condition = expression();
        final Statement ifStatement = statement();
        final Statement elseStatement;

        if(match(TokenType.ELSE)){
            elseStatement = statement();
        }else{
            elseStatement = null;
        }

        return new IfStatement(ifStatement, elseStatement, condition);

    }

    private Expression expression(){
        return logicalOr();
    }

    private Expression logicalOr(){
        Expression result = logicalAnd();

        while (true){
            if(match(TokenType.BARBAR)){
                result = new ConditionalExpression(result, logicalAnd(), ConditionalExpression.Operator.OR);
                continue;
            }
            break;
        }
        return result;
    }

    private Expression logicalAnd(){
        Expression result = equality();
        while (true){
            if(match(TokenType.AMPAMP)){
                result = new ConditionalExpression(result, equality(), ConditionalExpression.Operator.AND);
                continue;
            }
            break;
        }
        return result;
    }

    private Expression equality(){
        Expression result = condition();
        if(match(TokenType.EQEQ)){
            return new ConditionalExpression(result, condition(), ConditionalExpression.Operator.EQUALS);
        }
        if(match(TokenType.EXCLEQ)){
            return new ConditionalExpression(result, condition(), ConditionalExpression.Operator.NOT_EQUALS);
        }
        return result;
    }

    private Expression condition(){
        Expression result = additive();
        while (true){
            if(match(TokenType.LT)){
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operator.LT);
                continue;
            }
            if(match(TokenType.LTEQ)){
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operator.LTEQ);
                continue;
            }

             if(match(TokenType.GT)){
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operator.GT);
                continue;
            }
            if(match(TokenType.GTEQ)){
                result = new ConditionalExpression(result, additive(), ConditionalExpression.Operator.GTEQ);
                continue;
            }

            break;
        }
        return result;
    }

    public Expression additive(){
        Expression result = multiplicative();
        while (true){
            if(match(TokenType.MINUS)){
                result = new BinaryExpression(result, multiplicative(), '-');
                continue;
            }

            else if(match(TokenType.PLUS)){
                result = new BinaryExpression(result, multiplicative(), '+');
                continue;
            }
            break;
        }
        return result;
    }

    public Expression multiplicative(){
        Expression result = unary();
        while (true){
            if(match(TokenType.STAR)){
                result = new BinaryExpression(result, unary(), '*');
                continue;
            }
            if(match(TokenType.SLASH)){
                result = new BinaryExpression(result, unary(), '/');
                continue;
            }
            break;
        }
        return result;
    }

    public Expression unary(){
        if(match(TokenType.MINUS)){
            return new UnaryExpression(primary(), '-');
        }

        else if(match(TokenType.PLUS)){
            return new UnaryExpression(primary(), '+');
        }

        return primary();
    }

    public Expression primary(){
        final Token current = get(0);
        if(match(TokenType.NUMBER)){
            return new ValueExpression(Double.parseDouble(current.getText()));
        }

        if(match(TokenType.WORD)){
            return new VariableExpression(current.getText());
        }

        if (match(TokenType.TEXT)){
            return new ValueExpression(current.getText());
        }

        if(match(TokenType.LPAREN)){
            Expression result = expression();
            match(TokenType.RPAREN);
            return result;
        }

        throw new RuntimeException("Unknown expression");
    }

    Token consume(TokenType type){
        Token current = get(0);
        if(type != current.getType()) throw new RuntimeException("Token " + current + " doesn't match token type " + type);
        position++;
        return current;
    }

    public boolean match(TokenType type){
        final Token current = get(0);
        if(type != current.getType()) return false;
        position++;
        return true;
    }

    public void next(int relativePosition){
        position += relativePosition;
    }
    public void next(){
        position++;
    }

    public Token get(int relativePosition){
        final int localPosition = position + relativePosition;

        if(localPosition >= size) return EOF;
        return tokens.get(position);
    }
}
