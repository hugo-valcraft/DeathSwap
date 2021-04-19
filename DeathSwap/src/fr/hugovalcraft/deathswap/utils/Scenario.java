package fr.hugovalcraft.deathswap.utils;

public enum Scenario {
	
	CutClean(false, "CutClean"),
	Fast_Smelting(false, "Fast Smelting"),
	UHC(false, "UHC"),
	Times_UP(false, "Times-UP"),
	Anonymous(false, "Anonymous");
	
	private boolean active;
	
	private String name;
	
	Scenario(boolean active, String name) {
		
		this.setActive(active);
		
		this.setName(name);
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
