import java.util.Vector;
/*
 * Created on Jul 5, 2003
 *
 */

/**
 * Catfish - simulates a catfish - can swim, eat, and consume 
 * energy in the process.
 * 
 * @author iCarnegie av
 *
 */
public class Catfish extends LivingBeing {

	/**
	 * The catfish is born "alive". 
	 * Then it dies, becoming a corpse. 
	 */
	private static final String ALIVE = "alive";

	/**
	 * The catfish is born "alive". 
	 * Then it dies, becoming a "dead" corpse. 
	 */
	private static final String DEAD = "dead";


	/**
	 * Energy needed to swim in a block of time.
	 */
	private static final int ENERGY_TO_SWIM = 2;

	/**
	 * debugging level. 
	 */
	private static final int DEBUG = 0;
		
	/**
	 * Energy needed to look for food once.
	 */
	private static final int ENERGY_TO_LOOK_FOR_FOOD = 1;
	
	/**
	 * Energy expended to eat once.
	 */
	private static final int ENERGY_TO_EAT = 1;
	
	/**
	 * Energy gained when a full meal is eaten.
	 */
	private static final int ENERGY_IN_A_FULL_MEAL = 10;
	
	/**
	 * Lowest possible energy needed for a baby to survive. 
	 */
	private static final int BABY_MIN_ENERGY = 15;
	
	/**
	 * Maximum energy that a baby can store. 
	 */
	private static final int BABY_MAX_ENERGY = 100;

	/**
	 * For each block of time, the min energy grows by a certain amount
	 */
	private static final int MIN_ENERGY_GROWTH_INCREMENT = 5;
	
	/**
	 * For each block of time, the max energy grows by a certain amount
	 */
	private static final int MAX_ENERGY_GROWTH_INCREMENT = 10;
	
	// Concept example: final. since it is a constant 
	// Concept example: static. since only one value is needed 
	// 						irrespective of number of object instances 
	/**
	 * String constant - used to indicate the direction catfish is facing.
	 */
	private static final String RIGHT = "right";

	/**
	 * String constant - used to indicate the direction catfish is facing.
	 */
	private static final String LEFT = "left";

	/**
	 * String constant - used to indicate the direction catfish is facing.
	 */
	private static final String UP = "up";

	/**
	 * String constant - used to indicate the direction catfish is facing.
	 */
	private static final String DOWN = "down";

	/**
	 * Name of species
	 */
	private static final String SPECIES = "Catfish";

	/**
	 * Row-wise location of the catfish
	 */
	private int row;

	/**
	 * Column-wise location of the catfish
	 */
	private int column;

	/**
	 * Is the catfish dead or alive?
	 */
	private String deadOrAlive;
	
	/**
	 * Amount of energy the catfish has.
	 */
	private int energy;

	/**
	 * Age expressed as blocks of time lived
	 */
	private int age = 0;

	/**
	 * Name of this catfish.
	 */
	private final String name;

	/**
	 * The simulation to which this catfish belongs.
	 * This is needed so the catfish can send a message 
	 * to simulation and ask
	 * for prey (or predator) in the neighboring locations. 
	 * Prey is food. Food is good!
	 */
	private Simulation simulation;

	/**
	 * Minimum energy level needed to survive.
	 * The minimum could increase as the individual grows.
	 */
	private int minEnergy;
	
	/**
	 * Maximum energy level that the catfish could carry.
	 * The maximum could change as the individual grows.
	 */
	private int maxEnergy;
	
	/**
	 * Which direction am I facing.
	 */
	private String direction; 

	/**
	 * 
	 * Number of Catfish created
	 */
	private static int nCatfishCreated = 0; 

	/**
	 * Constructor. Initialize an algae to start life at a specified 
	 * location with a specified energy. If location is out of bounds,
	 * locate the catfish at the nearest edge.
	 * 
	 * @param initialRow - the row at which the catfish is located
	 * @param initialColumn - the column at which the catfish is located
	 * @param initialSimulation - the simulation that the catfish belongs to
	 */
	public Catfish(
		int initialRow,
		int initialColumn,
		Simulation initialSimulation) {
			name = SPECIES + nCatfishCreated;
			row = initialRow;
			column = initialColumn;
			simulation = initialSimulation;
			minEnergy = BABY_MIN_ENERGY;
			maxEnergy = BABY_MAX_ENERGY;
			energy = simulation.getRand().nextInt(maxEnergy-minEnergy)+minEnergy;
	}
	
	/**
	 * Get the row at which the catfish is located 
	 * 
	 * @return - the row of the catfish's location. 
	 */		
	public int getRow() {
		return row;
	}

	/**
	 * Get the column at which the catfish is located
	 * 
	 * @return - the column of the catfish's location. 
	 */		
	public int getColumn() {
		return column;
	}

	/**
	 * Get the catfish's age
	 * 
	 * @return the age of the catfish expressed in blocks of time
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Color of the catfish expressed in hex notation.
	 * For example, the "green-est" color is "#00FF00",
	 * "blue-est" is "#0000FF", the "red-est" is "#FF0000".
	 * 
	 * @return the rgb color in hex notation. preceded by a pound character '#'
	 */
	public String getColor() {
		return "#FFFFFF"; // default is white.
	}

	/**
	 * Get the name of this catfish
	 * 
	 * @return the name of the catfish.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the minimum energy needed to live.
	 * 
	 * @return the minimum energy needed for the catfish to live.
	 */
	private int getMinEnergy() {
		return minEnergy;
	}
	
	/**
	 * get the maximum energy that the catfish can carry.
	 * 
	 * @return the maximum energy the catfish can carry.
	 */
	private int getMaxEnergy() {
		return maxEnergy;
	}
	

	/**
	 * Get the energy currently carried by the catfish.
	 * 
	 * @return current energy level of the organism
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * Sets energy level.
	 * If new energy level is less than minimum energy level, the organism dies.
	 * New energy level is capped at maximum energy level.
	 */
	private void setEnergy(int newEnergy) {
		if(newEnergy < minEnergy)
			die();
		if(newEnergy >= maxEnergy) {
			energy = maxEnergy;
			return;
		}
		energy = newEnergy;
	}

	/**
	 * Die: Change the deadOrAlive to DEAD.
	 */
	public void die() {
		deadOrAlive = DEAD;
	}

	/**
	 * Is the catfish dead?
	 * 
	 * @return <code>true</code> if dead. <code>false</code>, otherwise.
	 */
	public boolean isDead() {
		return (deadOrAlive == DEAD);
	}

	/**
	 * Get the direction faced by the catfish.
	 * 
	 * @return the facing direction.
	 */
	private String getDirection() {
		return direction;
	}

	/** 
	 * Is the catfish hungry?
	 * 
	 * @return True, if hungry. False, otherwise.
	 */
	private boolean isHungry() {
		if(energy < minEnergy*2)
			return true;
		// Hungry, if current energy level is less than twice the 
		// amount needed for survival.
		return false;
	}

	/**
	 * Move the catfish to a new row, if new row is within lake bounds.
	 * 
	 * @param newRow - the row to move to.
	 * @return the row moved to. Lake boundary limits movement.
	 */
	private int moveToRow(int newRow) {
		if(newRow >= simulation.getFirstRow() && newRow <= simulation.getLastRow()){
			row = newRow;
		}
		return row;
	}

	/**
	 * Move the catfish to a new column, if new column is within lake bounds.
	 * 
	 * @param newColumn - the column to move to.
	 * @return the column moved to. Lake boundary limits movement.
	 */
	private int moveToColumn(int newColumn) {
		if(newColumn >= simulation.getFirstColumn() && newColumn <= simulation.getLastRow()){
			column = newColumn;
		}
		return column;
	}

	/**
	 * This individual belongs to the Catfish species.
	 *  
	 * @return The string indicating the species
	 */
	public String getSpecies() {
		return SPECIES;
	}

	/**
	 * Catfish should be displayed as an image.
	 * 
	 * @return a constant defined in {@link Simulation#IMAGE Simulation} class
	 */
	public String getDisplayMechanism() {
		return Simulation.IMAGE;
	}

	/**
	 * Get the image of the catfish
	 * 
	 * @return filename of Catfish image
	 */
	public String getImage() {
		if(isDead())
			return "static/blank.gif";
		if(direction == null)
			return "static/CatFish-down.gif";
		return "static/CatFish-"+direction+".gif";
	}


	/**
	 * Look for food in the neighborhood. Consume some energy in the process.
	 * 
	 * @return a neighboring algae that is food.
	 */
	private AlgaeColony lookForFoodInNeighborhood() {
		energy -= ENERGY_TO_LOOK_FOR_FOOD;
		Vector neigbors = simulation.getNeighbors(row,column,0);
		for(int i=0;i<neigbors.size();i++){
			LivingBeing being = (LivingBeing)neigbors.get(i);
			if(being.getSpecies() == "Algae")
				return (AlgaeColony)being;
		}
		return null;
	}

	
	/** 
	 * Swim to a new location if possible.
	 * Consumes some energy.
	 */
	private void swimIfPossible() {
		if(lookForFoodInNeighborhood()!=null){
			eatIfPossible();
			return;
		}
		energy-=ENERGY_TO_SWIM;
		// Consume ENERGY_TO_SWIM units of energy to swim.

		// Check if there is any energy left after consumption(~2/3lines)
		if(energy>=minEnergy){
			int directionNum = simulation.getRand().nextInt(4);
			switch (directionNum) {
				case 0:
					row--;
					this.direction = UP;
					break;
				case 1:
					row++;
					this.direction = DOWN;
					break;
				case 2:
					column++;
					this.direction = RIGHT;
					break;
				case 3:
					column--;
					this.direction = LEFT;
					break;
			}
			eatIfPossible();
		}
	}

	/**
	 * Eat food if available.
	 *
	 */
	private void eatIfPossible() {
		AlgaeColony algae = lookForFoodInNeighborhood();
		if(algae!=null){
			setEnergy(getEnergy() + algae.giveUpEnergy(ENERGY_IN_A_FULL_MEAL)-ENERGY_TO_EAT);
		}
	}

	/**
	 * Catfish lives its life. It may lose or gain energy.
	 */
	public void liveALittle() {
		if(energy < minEnergy) {
			die();
			return;
		}
		age++;
		minEnergy += MIN_ENERGY_GROWTH_INCREMENT;
		maxEnergy += MAX_ENERGY_GROWTH_INCREMENT;
		swimIfPossible();
	}
	
}
