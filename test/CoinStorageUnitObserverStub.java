/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.AbstractCoinStorageUnit;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinStorageUnitObserver;

public class CoinStorageUnitObserverStub implements CoinStorageUnitObserver {
	
	public int coinCount;
	public boolean isFull = false;
	public boolean isLoaded = false;
	
	/**
	 * Announces that the storage is at its maximum capacity.
	 * 
	 * @param unit
	 * 			The storage unit where the event occurred
	 */
	public void coinsFull(AbstractCoinStorageUnit unit) {
		isFull = true;
	}

	/**
	 * Announces that a coin has been added to the indicated storage unit.
	 * 
	 * @param unit
	 *            The storage unit where the event occurred.
	 *            
	 *  NEVER CALLED IN ANY METHODS; SO EVEN IF IT IS IN THE INTERFACE, NO NEED TO MAKE TEST CASES
	 */
	public void coinAdded(AbstractCoinStorageUnit unit) {
		coinCount += 1;
	}

	/**
	 * Announces that the indicated storage unit has been loaded with coins. Used to
	 * simulate direct, physical loading of the unit.
	 * 
	 * @param unit
	 *            The storage unit where the event occurred.
	 */
	public void coinsLoaded(AbstractCoinStorageUnit unit) {
		isLoaded = true;
	}

	/**
	 * Announces that the storage unit has been emptied of coins. Used to simulate
	 * direct, physical unloading of the unit.
	 * 
	 * @param unit
	 *            The storage unit where the event occurred.
	 */
	public void coinsUnloaded(AbstractCoinStorageUnit unit) {
		coinCount = 0;
	}

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
}