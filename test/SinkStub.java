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

/**
 * A stub to isolate the behaviour of sinks. In this version, the hasSpace() method always returns as true.
 * @param <T>
 */

public class SinkStub<T> implements Sink<T>{
	/**
	 * Used to keep track of how many coins are received to the sink.
	 */
	public int coinCount = 0;
	@Override
	public void receive(Object cash) throws CashOverloadException, DisabledException {
		coinCount++;
	}

	/**
	 * Used to control the flow of certain methods. Always returns true.
	 */
	@Override
	public boolean hasSpace() {
		// TODO Auto-generated method stub
		return true;
	}

}
