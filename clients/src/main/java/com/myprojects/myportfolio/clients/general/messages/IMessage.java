package com.myprojects.myportfolio.clients.general.messages;

/**
 * @author Roberto97
 * Interface used to define the type of message returned to the FE
 */
public interface IMessage {
	enum Level {
		TRACE,
		DEBUG,
		INFO,
		WARNING,
		ERROR,
		FATAL
	}
	String getText();
	String getCode();
	Level getLevel();
}