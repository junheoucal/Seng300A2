/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;
import java.math.BigDecimal;

/**
 * 
 */
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.*;

public class ValidatorObserverStub implements CoinValidatorObserver {
	public boolean validCoin = false;

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
	public void validCoinDetected(AbstractCoinValidator validator, BigDecimal value) {
		validCoin = true;
	}

	@Override
	public void invalidCoinDetected(AbstractCoinValidator validator) {
		validCoin = false;
		
	}

}