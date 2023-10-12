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

public class NoSpaceSinkStub<T> implements Sink<T>{

	@Override
	public void receive(T cash) throws CashOverloadException, DisabledException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Used to control the flow of certain methods. Always returns false.
	 */
	
	@Override
	public boolean hasSpace() {
		// TODO Auto-generated method stub
		return false;
	}

}
