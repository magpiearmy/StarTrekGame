package com.edocti.jnext.gof.behavioral;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


enum TokenType {
	INTEGER, PLUS, EOF
}

class Token {  // terminal
	TokenType type;
	String value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
}

class SimpleInterpreter {
	Token currentToken;
	private String text;
	private int pos;
	
	public SimpleInterpreter(String expression) {
		text = expression;
	}
	
	public Token getNextToken() {
		if (pos == text.length()) {
			return new Token(TokenType.EOF, "");
		}
		char currentChar = text.charAt(pos);
		if (Character.isDigit(currentChar)) {
			Token token = new Token(TokenType.INTEGER, currentChar + "");
			pos++;
			return token;
		}
		if (currentChar == '+') {
			Token token = new Token(TokenType.PLUS, "+");
			pos++;
			return token;
		}
		throw new IllegalArgumentException("Cannot parse expression");
	}
	
	public void consume(TokenType type) {
		if (currentToken.type == type) {
			currentToken = getNextToken();
		} else {
			throw new IllegalArgumentException("Cannot parse expression");
		}
	}
	
	public int calculateExression() {
		currentToken = getNextToken();
		Token left = currentToken;
		consume(TokenType.INTEGER);
		
		Token operator = currentToken;
		consume(TokenType.PLUS);
		
		Token right = currentToken;
		consume(TokenType.INTEGER);
		
		return Integer.parseInt(left.value) + Integer.parseInt(right.value);
	}
}

public class InterpreterDemo {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print("expr:");
			String expression = br.readLine();
			if (expression.trim().length() == 0) {
				break;
			}
			int result = new SimpleInterpreter(expression).calculateExression();
			System.out.println(result);
		}
	}
}
