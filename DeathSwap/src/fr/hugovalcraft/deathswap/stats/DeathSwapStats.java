package fr.hugovalcraft.deathswap.stats;

public enum DeathSwapStats {
	
	WAIT(true), INVULNERABLE(false),INGAME(false),END(false), GENERATION(false), CONFIG(false);
	
	private boolean canJoin;
	
	private static DeathSwapStats currentstats;
	
	DeathSwapStats(boolean canJoin) {
		this.canJoin = canJoin;
	}
	
	public boolean canJoin() {
		
		return canJoin;
		
	}
	
	public static void setState(DeathSwapStats state) {
		
		DeathSwapStats.currentstats = state;
		
	}
	
	public static boolean isState(DeathSwapStats state) {
		
		return DeathSwapStats.currentstats == state;
		
	}
	
	public static DeathSwapStats getState() {
		
		return currentstats;
		
	}
	
}
