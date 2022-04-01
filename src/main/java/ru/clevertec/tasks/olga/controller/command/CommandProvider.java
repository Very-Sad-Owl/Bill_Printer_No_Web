package ru.clevertec.tasks.olga.controller.command;

import ru.clevertec.tasks.olga.controller.command.impl.action.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.SWITCHLANGUAGE, new LanguageSwitcher());
		commands.put(CommandName.PRINT, new PrinterManager());
		commands.put(CommandName.LOG, new LogManager());
		commands.put(CommandName.GUIDE, new GuidePage());
		commands.put(CommandName.SAVE, new SavingManager());
		commands.put(CommandName.UPDATE, new UpdateManager());
		commands.put(CommandName.DELETE, new DeleteManager());
	}

	
	public Command takeCommand(String name) {
		CommandName commandName;
		
		commandName = CommandName.valueOf(name.toUpperCase());
		
		return commands.get(commandName);
	}

}
