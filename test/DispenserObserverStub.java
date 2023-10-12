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

/**
 * A stub to isolate the behaviour of CoinDispenserObserver
 */

public class DispenserObserverStub implements CoinDispenserObserver{
	
	/**
	 * Used to keep track of the number of coins based on coinAdded, coinRemoved, and coinsLoaded events.
	 */
	public int coinCount = 0;
	/**
	 * Used to keep track of how many coins are unloaded based on the coinsUnloaded event.
	 */
	public int coinsUnloaded = 0;
	
	/**
	 * Used to test the coinsFull and coinsEmpty events.
	 */
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

	/**
	 * Announces that the dispenser is full.
	 */
	
	@Override
	public void coinsFull(ICoinDispenser dispenser) {
		isFull = true;
	}

	/**
	 * Announces that the dispenser is empty.
	 */
	
	@Override
	public void coinsEmpty(ICoinDispenser dispenser) {
		isEmpty = true;
	}

	/**
	 * Announces that a coin has been added to the dispenser.
	 */
	
	@Override
	public void coinAdded(ICoinDispenser dispenser, Coin coin) {
		// TODO Auto-generated method stub
		coinCount += 1;
	}

	/**
	 * Announces that a coin has been removed from the dispenser.
	 */
	
	@Override
	public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
		// TODO Auto-generated method stub
		coinCount += -1;
	}
	
	/**
	 * Announces that coins have been loaded to the dispenser.
	 */

	@Override
	public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
		// TODO Auto-generated method stub
		for (Coin coin : coins) {
			coinCount++;
		}
	}

	/**
	 * Announces that the dispenser has been unloaded.
	 */
	
	@Override
	public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
		for (Coin coin : coins) {
			coinsUnloaded++;
		}
	}

}
