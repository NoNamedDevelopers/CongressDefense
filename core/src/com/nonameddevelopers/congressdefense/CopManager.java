package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;
import java.util.Iterator;

import com.nonameddevelopers.congressdefense.characters.Cop;

public class CopManager {
	
	private ArrayList<Cop> cops;
	private Cop copToPlant;

	public CopManager() {
		this.cops = new ArrayList<Cop>();
	}
	
	public void update(float delta)
	{
		for (Cop cop: cops)
			cop.update(delta);
	}	
	
	public void addCop(Cop cop) {
		cops.add(cop);	
	}
	
	public void addCopToPlant(Cop cop) {
		copToPlant = cop;
		cops.add(copToPlant);
	}
	
	public void deleteCopToPlant() {
		Iterator<Cop> iter = cops.iterator();
		while (iter.hasNext()) {
			if (iter.next() == copToPlant) {
				iter.remove();
				break;
			}
		}
		copToPlant = null;
		
	}
	
	public ArrayList<Cop> getCops() {
		return cops;
	}

	public void setCops(ArrayList<Cop> cops) {
		this.cops = cops;
	}
	
	

}
