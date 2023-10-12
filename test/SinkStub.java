/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.Sink;

public class SinkStub<T> implements Sink<T>{
	
	public int coinCount = 0;
	@Override
	public void receive(Object cash) throws CashOverloadException, DisabledException {
		coinCount++;
	}

	@Override
	public boolean hasSpace() {
		// TODO Auto-generated method stub
		return true;
	}

}
