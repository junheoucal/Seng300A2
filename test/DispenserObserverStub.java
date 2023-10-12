/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.*;

public class DispenserObserverStub implements CoinDispenserObserver{
	
	public int coinCount = 0;
	public int coinsUnloaded = 0;
	public boolean isFull = false;
	public boolean isEmpty = false;
	
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinsFull(ICoinDispenser dispenser) {
		isFull = true;
	}

	@Override
	public void coinsEmpty(ICoinDispenser dispenser) {
		isEmpty = true;
	}

	@Override
	public void coinAdded(ICoinDispenser dispenser, Coin coin) {
		// TODO Auto-generated method stub
		coinCount += 1;
	}

	@Override
	public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
		// TODO Auto-generated method stub
		coinCount += -1;
	}

	@Override
	public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
		// TODO Auto-generated method stub
		for (Coin coin : coins) {
			coinCount++;
		}
	}

	@Override
	public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
		for (Coin coin : coins) {
			coinsUnloaded++;
		}
	}

}
