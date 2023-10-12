/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;
import ca.ucalgary.seng300.simulation.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tdc.Sink;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;

/**
 * Tests for the CoinValidator concrete class
 * @author  
 */

public class CoinValidatorTest {
	/**
	 * Tests to make sure a null exception is thrown when the currency of a coin validator is null
	 */
	@Test(expected = SimulationException.class)
	public void testNullCurrency() {
		List<BigDecimal> coinDenominations = Arrays.asList(new BigDecimal(0.05));
		
		new CoinValidator(null,coinDenominations);
	}
	
	/**
	 * Tests to make sure a null exception is thrown when the denominations list is null
	 */
	@Test(expected = SimulationException.class)
	public void testNullDenomList() {
		Currency currency = Currency.getInstance("CAD");
		
		new CoinValidator(currency, null);
	}
	
	/**
	 * Tests to make sure an invalid argument exception is thrown when the denominations list is empty
	 */
	@Test(expected = SimulationException.class)
	public void testNoDenominations() {
		List<BigDecimal> coinDenominations = Arrays.asList();
		Currency currency = Currency.getInstance("CAD");
		
		new CoinValidator(currency, coinDenominations);
	}
	
	/**
	 * Tests a valid coin validator 
	 */
	@Test
	public void testValidCoinValidator() {
		List<BigDecimal> coinDenominations = Arrays.asList(new BigDecimal(0.05));
		Currency currency = Currency.getInstance("CAD");
		
		new CoinValidator(currency, coinDenominations);
	}
	
	/**
	 * Test throws a null exception when one of the denominations in the denomination list is null
	 */
	@Test(expected = SimulationException.class)
	public void testNullCoinDenom() {
		Currency currency = Currency.getInstance("CAD");
		List<BigDecimal> coinDenominations = Arrays.asList(new BigDecimal(0.05), null);
		
		new CoinValidator(currency, coinDenominations);
	}
	
	/**
	 * Test throws an invalid argument exception if a denomination in the list is zero
	 */
	@Test(expected = InvalidArgumentSimulationException.class)
	public void testZeroDenom() {
		List<BigDecimal> coinDenominations = Arrays.asList(new BigDecimal(0.05), BigDecimal.ZERO);
		Currency currency = Currency.getInstance("CAD");
		
		new CoinValidator(currency, coinDenominations);
	}
	
	/**
	 * Test throws an invalid argument exception if a denomination in the list is less than zero
	 */
	@Test(expected = SimulationException.class)
	public void testNonPosDenom() {
		List<BigDecimal> coinDenominations = Arrays.asList(new BigDecimal(0.05), new BigDecimal(-5));
		Currency currency = Currency.getInstance("CAD");
		
		new CoinValidator(currency, coinDenominations);
	}
	
	/**
	 * Test throws an invalid argument exception if there are multiple of the same denomination in the list
	 */
	@Test(expected = SimulationException.class)
	public void testUniqueDenom() {
		List<BigDecimal> coinDenominations = Arrays.asList(new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05));
		Currency currency = Currency.getInstance("CAD");
		
		new CoinValidator(currency, coinDenominations); 
	}
	
} 






	
	