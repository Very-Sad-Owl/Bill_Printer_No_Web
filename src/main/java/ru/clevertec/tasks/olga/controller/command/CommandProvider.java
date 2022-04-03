package ru.clevertec.tasks.olga.controller.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.tasks.olga.controller.command.impl.action.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandProvider {

	private static final Map<CommandName, Command> commands = new HashMap<>();
	private List<Command> commandList;

	@Autowired
	public void setCommandList(List<Command> commandList){
		this.commandList = commandList;
	}


	@PostConstruct
	public void init(){
		for (Command el : commandList){
			if (el.getClass() == DeleteManager.class) commands.put(CommandName.DELETE, el);
			if (el.getClass() == UpdateManager.class) commands.put(CommandName.UPDATE, el);
			if (el.getClass() == SavingManager.class) commands.put(CommandName.SAVE, el);
			if (el.getClass() == GuidePage.class) commands.put(CommandName.GUIDE, el);
			if (el.getClass() == LogManager.class) commands.put(CommandName.LOG, el);
			if (el.getClass() == PrinterManager.class) commands.put(CommandName.PRINT, el);
			if (el.getClass() == LanguageSwitcher.class) commands.put(CommandName.SWITCHLANGUAGE, el);
		}
	}

	//	public CommandProvider() {
//		commands.put(CommandName.SWITCHLANGUAGE, new LanguageSwitcher());
//		commands.put(CommandName.PRINT, new PrinterManager());
//		commands.put(CommandName.LOG, new LogManager());
//		commands.put(CommandName.GUIDE, new GuidePage());
//		commands.put(CommandName.SAVE, new SavingManager());
//		commands.put(CommandName.UPDATE, new UpdateManager());
//		commands.put(CommandName.DELETE, new DeleteManager());
//	}

	
	public static Command takeCommand(String name) {
		CommandName commandName;
		
		commandName = CommandName.valueOf(name.toUpperCase());
		
		return commands.get(commandName);
	}

}
