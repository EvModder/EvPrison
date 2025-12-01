package EvPrison;

import java.util.Set;
import java.util.UUID;
import net.evmodder.EvLib.bukkit.Section;

public class Jail {
	public Set<UUID> inmates;
	public Section bounds;
	public String fullName;
	
	Jail(String name, Set<UUID> inmates, Section bounds){
		fullName = name;
		this.inmates = inmates;
		this.bounds = bounds;
	}
}
