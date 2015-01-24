package com.ecomm.ultimatesms.designpattern;


public class ObservableImpl implements Observable  {
	
	//public ArrayList observers=new ArrayList();
	Observer observer;
	
	
	public void add(Observer ob) {
			observer=ob;
	}

	public void remove(Observer ob) {
	}

	public void notifyOperations() {
			((Observer)observer).addToKannel();
		
	}
}
