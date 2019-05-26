package edu.handong.analysise.utils;

public class NotEnoughArgumnetException extends Exception {
	
	public NotEnoughArgumnetException()
	{
		super("No CLI argument Exception! Please put a file path.");
	}		

	public NotEnoughArgumnetException(String message)
	{
		super(message);
	}

}
