package com.ecomm.ultimatesms.designpattern;

import com.ecomm.ultimatesms.messaging.model.TextMessage1;

public class TestObserverPattern {
	
	public static void main(){
	
	TextMessage1 textMessage=new TextMessage1();
	ObserverImpl observer=new ObserverImpl(textMessage);
	Observable observableImpl=new ObservableImpl();
	observableImpl.add(observer);
	//observableImpl.notifyOperations();
	}
}
