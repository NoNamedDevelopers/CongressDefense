package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;

/**
 * NO USAR AÚN
 * @author Noé
 *
 */
public class CollissionManager {

	private List<Protester> protesters;

	private HashMap<float[], Cop> copPositions;

	public CollissionManager() {
		this.protesters = new ArrayList<Protester>();
		this.copPositions = new HashMap<float[], Cop>();
	}

	public List<Protester> getProtesters() {
		return this.protesters;
	}

	public void addProtester(Protester protester) {
		this.protesters.add(protester);
	}

	public void addCop(Cop cop) {
		this.copPositions.put(new float[] { cop.getX(), cop.getY() }, cop);
		System.out.println("New cop added, actual amount of cops:"
				+ this.copPositions.size());
	}
}
