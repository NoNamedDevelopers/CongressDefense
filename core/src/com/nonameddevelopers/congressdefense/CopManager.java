package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;
import java.util.Iterator;

import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.characters.cops.PoliceVan;

public class CopManager {

	private ArrayList<Cop> cops;
	private ArrayList<Cop> copsToAdd;
	private Cop copToPlant;

	public CopManager() {
		this.cops = new ArrayList<Cop>();
		this.copsToAdd = new ArrayList<Cop>();
	}

	public void update(float delta) {
		Iterator<Cop> iter = cops.iterator();

		while (iter.hasNext()) {
			Cop police = iter.next();
			police.update(delta);
			if (police.isDead()) {
				iter.remove();
				police = null;
			}
		}
		if (copsToAdd.size() > 0)
			System.out.println("Cops size: " + copsToAdd.size());
		System.out.println();
		for (Cop cop : copsToAdd) {
			cops.add(cop);
		}
		copsToAdd.clear();

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

	public ArrayList<Cop> getCopsToAdd() {
		return copsToAdd;
	}

}
