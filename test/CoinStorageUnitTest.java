/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;


public class CoinStorageUnitTest {

	private CoinStorageUnit coinStorageUnit;
	private int capacity;
	private Currency currency;
	
	/**
	 * setup for CoinStorageUnit. Has currency, capacity, and power grid
	 */
	@Before
	public void setup() {
		currency = Currency.getInstance("CAD");
        List<BigDecimal> denominations = Arrays.asList(new BigDecimal("0.25"), new BigDecimal("0.1"), new BigDecimal("0.05"));
        
		capacity = 10;
		this.coinStorageUnit = new CoinStorageUnit(capacity);
		coinStorageUnit.connect(PowerGrid.instance());
	}
	
	/**
	 * test for making coin storage unit with 0 capacity
	 * @throws InvalidArgumentSimulationException
	 */
	@Test (expected = InvalidArgumentSimulationException.class)
	public void testCoinStorageUnitNoCapacity() throws Exception {
		capacity = 0;
		CoinStorageUnit coinStorageUnitNull = new CoinStorageUnit(capacity);
	}
	
	/**
	 * test to get storage unit capacity
	 */
	@Test
	public void testGetCapacity() {
		int capacity = coinStorageUnit.getCapacity();
		Assert.assertEquals(10, capacity);
	}
	
	/**
	 * test to get the current count of coin in the unit
	 * 
	 * CONTAINS BUG: expected is 0 but actual is 1
	 */
	@Test
	public void testGetCoinCount() {
		coinStorageUnit.activate();
		int count = coinStorageUnit.getCoinCount();
		Assert.assertEquals(0, count);  // bug: there is no coin so count is 0 but expected is 1
	}
	
	/**
	 * test to get the current count of coin in a not activated storage unit
	 * @throws NoPowerException
	 */
	@Test (expected = NoPowerException.class)
	public void testGetCoinCountNotActivated() throws Exception {
		coinStorageUnit.disactivate();
		coinStorageUnit.getCoinCount();
	}
	
	/**
	 * test to load coins to a not activated storage unit
	 * @throws NoPowerException
	 */
	@Test (expected = NoPowerException.class)
	public void testLoadDisactivated() throws Exception {
		coinStorageUnit.disactivate();
		coinStorageUnit.load(new Coin(currency, BigDecimal.TEN));
	}
	
	/**
	 * test to load null coins to a storage unit
	 * @throws NullPointerSimulationException
	 */
	@Test (expected = NullPointerSimulationException.class)
	public void testLoadNull() throws Exception {                                      
		coinStorageUnit.activate();
		coinStorageUnit.load(null);
	}
	
	/**
	 * test to load too many coins (more that the storage unit capacity)
	 * @throws CashOverloadException
	 */
	@Test (expected = CashOverloadException.class)
	public void testLoadOverload() throws Exception {
		coinStorageUnit.activate();
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.load(coin, coin, coin, coin, coin, coin, coin, coin, coin, coin, coin);
	}
	
	/**
	 * test to load a set of coins with null
	 * @throws NullPointerSimulationException
	 */
	@Test (expected = NullPointerSimulationException.class)    // each coin cannot be null??
	public void testLoadCoinNull() throws Exception {
		coinStorageUnit.activate();
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.load(coin, coin, coin, coin, coin, coin, coin, coin, coin, null);
	}
	
	/**
	 * test to load 1 or more coins
	 * @throws Exception
	 * 
	 * CONTAINS BUG: expected is 1 but current count is 2
	 */
	@Test 
	public void testLoad() throws Exception{
		coinStorageUnit.activate();
		coinStorageUnit.disable();
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.load(coin);
		
		int currentCount = coinStorageUnit.getCoinCount();
		Assert.assertEquals(1, currentCount);
	}
	
	/**
	 * test to unload coins from a disactivated storage unit
	 * @throws NoPowerException
	 */
	@Test (expected = NoPowerException.class)
	public void testUnloadDisactivated() {
		coinStorageUnit.disactivate();
		coinStorageUnit.unload();
	}
	
	/**
	 * test to unload coins 
	 * 
	 * CONTAINS BUG: the storage is empty; expected result is 0 but the actual result is 10
	 */
	@Test 
	public void testUnload() {
		coinStorageUnit.activate();
		List<Coin> actual = coinStorageUnit.unload();
		Assert.assertEquals(actual.size(), 0);
	}
	
	/**
	 * test to add a coin to a disactivated storage unit
	 * @throws NoPowerException
	 */
	@Test (expected = NoPowerException.class)
	public void testReceiveDisactivated() throws Exception {
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.disactivate();
		coinStorageUnit.receive(coin);
	}
	
	/**
	 * test to add a coin to a disabled storage unit
	 * @throws DisabledException
	 */
	@Test (expected = DisabledException.class)
	public void testReceiveDisabled() throws Exception {
		coinStorageUnit.activate();
		coinStorageUnit.disable();
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.receive(coin);   
	}
	
	/**
	 * test to receive a null coin
	 * @throws NullPointerSimulatinException
	 */
	@Test (expected = NullPointerSimulationException.class)
	public void testReceiveCoinNull() throws Exception {
		Coin coin = null;
		coinStorageUnit.activate();
		coinStorageUnit.enable();          
		coinStorageUnit.receive(coin);
	}
	
	/**
	 * test to receive a valid coin
	 * @throws Exception
	 * 
	 * CONTAINS BUG: since the storage received 10 coins, the expected result is 10 but the actual result is 11
	 */
	@Test 
	public void testReceive() throws Exception {
		coinStorageUnit.activate();
		coinStorageUnit.enable();
		
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.load(coin, coin, coin, coin, coin, coin, coin, coin, coin);
		coinStorageUnit.receive(coin);
		
		int actual = coinStorageUnit.getCoinCount();
		Assert.assertEquals(10, actual);
	}
	
	/**
	 * test to add a coin when the storage unit is at its maximum capacity
	 * @throws CashOverloadException
	 */
	@Test (expected = CashOverloadException.class)
	public void testReceiveOverload() throws Exception {
		coinStorageUnit.activate();
		coinStorageUnit.enable();
		
		Coin coin = new Coin(currency, new BigDecimal("0.25"));
		coinStorageUnit.load(coin, coin, coin, coin, coin, coin, coin, coin, coin, coin);
		coinStorageUnit.receive(coin);
	}
	
	/**
	 * test to see if a disactivated storage unit has space
	 * @throws NoPowerException
	 */
	@Test (expected = NoPowerException.class)
	public void testHasSpaceDisactivated() throws Exception {
		coinStorageUnit.disactivate();
		coinStorageUnit.hasSpace();
	}
	
	/**
	 * test to see if an activated storage unit has space
	 * @throws Exception
	 */
	@Test
	public void testHasSpace() throws Exception {
		coinStorageUnit.activate();
		boolean actual = coinStorageUnit.hasSpace();
		Assert.assertTrue(actual);
	}
	
	/**
	 * test to check if coinAdded event is announced when a coin is received
	 * @throws CashOverloadException
	 * @throws DisabledException
	 * 
	 * CONTAINS BUG: expected result is 1 but the actual result is 0
	 */
	@Test
    public void testReceiveCoinAddedEvent() throws CashOverloadException, DisabledException {
        CoinStorageUnitObserverStub testObserver = new CoinStorageUnitObserverStub();
        Coin coin = new Coin(currency, new BigDecimal("0.25"));
        
        coinStorageUnit.activate();
        coinStorageUnit.enable();
        coinStorageUnit.attach(testObserver);
        coinStorageUnit.receive(coin);
        Assert.assertEquals(1, testObserver.coinCount);
    }
	
	/**
	 * test to check if coinFull event is announced when coins are received and the unit is at its maximum capacity
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	@Test
    public void testReceiveCoinFullEvent() throws CashOverloadException, DisabledException {
        CoinStorageUnitObserverStub testObserver = new CoinStorageUnitObserverStub();
        Coin coin = new Coin(currency, new BigDecimal("0.25"));
        
        coinStorageUnit.activate();
        coinStorageUnit.enable();
        coinStorageUnit.attach(testObserver);
        coinStorageUnit.load(coin, coin, coin, coin, coin, coin, coin, coin, coin);
        coinStorageUnit.receive(coin);
        Assert.assertTrue(testObserver.isFull);
    }
	
	/**
	 * test to check if coinUnloaded event is announced when coins are unloaded from the storage unit
	 */
	@Test
    public void testUnloadCoinUnloadedEvent(){
        CoinStorageUnitObserverStub testObserver = new CoinStorageUnitObserverStub();
        Coin coin = new Coin(currency, new BigDecimal("0.25"));
        
        coinStorageUnit.activate();
        coinStorageUnit.attach(testObserver);
        coinStorageUnit.unload();
        Assert.assertTrue(testObserver.coinCount == 0);
    }

}