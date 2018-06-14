package org.gooru.nucleus.insights.handlers.constants;

final public class CommandConstants {

  public final static String CREATE_EVENT = "create.event";
	public final static String DELETE_EVENT = "delete.event";
	public final static String UPDATE_EVENT = "update.event";
	public final static String FIND_EVENT = "find.event";
		
	private CommandConstants() {
		throw new AssertionError();
	}
}
