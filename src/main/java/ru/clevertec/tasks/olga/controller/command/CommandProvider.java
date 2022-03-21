package ru.clevertec.tasks.olga.controller.command;

import ru.clevertec.tasks.olga.controller.command.impl.action.GuidePage;
import ru.clevertec.tasks.olga.controller.command.impl.action.LanguageSwitcher;
import ru.clevertec.tasks.olga.controller.command.impl.action.PrinterManager;
import ru.clevertec.tasks.olga.controller.command.impl.redirect.GoToErrorPage;
import ru.clevertec.tasks.olga.controller.command.impl.redirect.GoToMainPage;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private Map<CommandName, Command> commands = new HashMap<>();
	
	public CommandProvider() {
		commands.put(CommandName.GOTOERRORPAGE, new GoToErrorPage());
		commands.put(CommandName.GOTOINDEXPAGE, new GoToMainPage());
		commands.put(CommandName.SWITCHLANGUAGE, new LanguageSwitcher());
		commands.put(CommandName.PRINT, new PrinterManager());
		commands.put(CommandName.GUIDE, new GuidePage());
	}

	
	public Command takeCommand(String name) {
		CommandName commandName;
		
		commandName = CommandName.valueOf(name.toUpperCase());
		
		return commands.get(commandName);
	}

}
