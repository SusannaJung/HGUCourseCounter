package edu.handong.analysise.utils;

public class NotEnoughArgumnetException extends Exception {
	
	public NotEnoughArgumentException()
	{
		super("No CLI argument Exception! Please put a file path.");
	}		

	public NotEnoughArgumentException(String message)
	{
		super(message);
	}

}
