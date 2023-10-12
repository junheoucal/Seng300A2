/**Group 11 
*Ananya Jain 30196069 
*Jun Heo 30173430 
*Sua Lim 30177039 
*Hillary Nguyen 30161137
**/

package com.thelocalmarketplace.hardware.test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.*;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.*;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

/**
 * A class for testing the CoinDispenser concrete class, AbstractCoinDispenser, and it's related interfaces and observers.
 */

public class CoinDispenserTest {
	private CoinDispenser validDispenser;
	private Coin testCoin;
	
	/**
	 * sets up a new CoinDispenser with size 1, which is connected and activated, as well as a valid instance of a coin.
	 */
	
	@Before
	public void setup() {
		validDispenser = new CoinDispenser(1);
		validDispenser.connect(PowerGrid.instance());
		validDispenser.activate();
		Currency testCurrency = Currency.getInstance("CAD");
		testCoin = new Coin(testCurrency, new BigDecimal("0.25"));
	}
	
	/**
	 * Tests the constructor when creating an instance with size 0
	 */
	
	@Test (expected = SimulationException.class)
	public void testConstructorAtZero() {
		CoinDispenser testDispenser1 = new CoinDispenser(0);
		Assert.assertEquals(0, testDispenser1.getCapacity());
	}
	
	/**
	 * Tests the constructor when creating an instance with a negative size
	 */

	@Test (expected = SimulationException.class)
	public void testConstructorForNegInt() {
		CoinDispenser testDispenser3 = new CoinDispenser(-1);
		Assert.assertEquals(-1, testDispenser3.getCapacity());
	}
	
	/**
	 * Tests the constructor for a positive integer
	 */
	
	@Test
	public void testConstructorForPosInt() {
		CoinDispenser testDispenser4 = new CoinDispenser(1);
		Assert.assertEquals(1, testDispenser4.getCapacity());
	}
	
	/**
	 * Tests the AbstractCoinValidator.receive() method when using a null coin
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test (expected = SimulationException.class)
	public void testReceiveForNullCoin() throws CashOverloadException, DisabledException {
		Currency testCurrency = Currency.getInstance("CAD");
		Coin nullCoin = null;
		validDispenser.receive(nullCoin);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.receive() method when the CoinDispenser is not powered.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test (expected = NoPowerException.class)
	public void testReceiveUnpoweredDispenser() throws CashOverloadException, DisabledException {
		validDispenser = new CoinDispenser(1);
		validDispenser.receive(testCoin);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.receive() method when the CoinDispenser is disabled.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test (expected = DisabledException.class)
	public void testReceiveDisabledDispenser() throws CashOverloadException, DisabledException {
		Currency testCurrency = Currency.getInstance("CAD");
		Coin validCoin = new Coin(testCurrency, new BigDecimal("0.25"));
		validDispenser.disable();
		validDispenser.receive(validCoin);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.receive() method when adding more coins than the size of the CoinDispenser.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test (expected = CashOverloadException.class)
	public void testCashOverloadDispenser() throws CashOverloadException, DisabledException {
		validDispenser.receive(testCoin);
		validDispenser.receive(testCoin);
	}
	
	/**
	 * Tests if AbstractCoinDispenser.receive() method creates a coinAdded event when adding a coin.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test
	public void testReceiveCoinAddedEvent() throws CashOverloadException, DisabledException {
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.receive(testCoin);
		Assert.assertTrue(testObserver.coinCount == 1);
	}
	
	/**
	 * Tests if AbstractCoinDispenser.receive() method creates a coinsFull event.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test
	public void testReceiveCoinsFullEvent() throws CashOverloadException, DisabledException {
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.receive(testCoin);
		Assert.assertTrue(testObserver.isFull);
	}
	
	/**
	 * Tests the CoinDispenser.hasSpace() method for when the CoinDispenser is empty.
	 */
	
	@Test
	public void testEmptyDispenserHasSpace() {
		Assert.assertTrue(validDispenser.hasSpace());
	}

	/**
	 * Tests the CoinDispenser.hasSpace() method for when the CoinDispenser has space after adding a coin.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test
	public void testNonEmptyDispenserHasSpace() throws CashOverloadException, DisabledException {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		validDispenser2.connect(PowerGrid.instance());
		validDispenser2.activate();
		validDispenser2.receive(testCoin);
		Assert.assertTrue(validDispenser2.hasSpace());
	}
	
	/**
	 * Tests the CoinDispenser.hasSpace() method for when the CoinDispenser is full.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test
	public void testFullDispenserHasSpace() throws CashOverloadException, DisabledException {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		validDispenser2.connect(PowerGrid.instance());
		validDispenser2.activate();
		validDispenser2.receive(testCoin);
		validDispenser2.receive(testCoin);
		Assert.assertFalse(validDispenser2.hasSpace());
	}
	
	/**
	 * Tests the CoinDispenser.hasSpace() method for when the CoinDispenser is not powered.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredDispenserHasSpace() {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		Assert.assertTrue(validDispenser2.hasSpace());
	}
	
	/**
	 * Tests the AbstractCoinDispenser.size() method for when the CoinDispenser is not powered.
	 */
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredDispenserSize() {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		Assert.assertEquals(0, validDispenser2.size());
	}
	
	/**
	 * Tests the AbstractCoinDispenser.size() method for when the CoinDispenser is empty.
	 */
	
	@Test
	public void testEmptyCoinDispenserSize() {
		Assert.assertEquals(0, validDispenser.size());
	}
	
	/**
	 * Tests the AbstractCoinDispenser.size() method when the CoinDispenser has a coin.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test
	public void testFilledCoinDispenserSize() throws CashOverloadException, DisabledException {
		validDispenser.receive(testCoin);
		Assert.assertEquals(1, validDispenser.size());
	}
	
	/**
	 * Tests the AbstractCoinDispenser.load() method when the CoinDispenser is not powered.
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredDispenserLoad() throws SimulationException, CashOverloadException {
		CoinDispenser unpoweredDispenser = new CoinDispenser(5);
		unpoweredDispenser.load(testCoin, testCoin, testCoin);
	}
	
	/**
	 * Tests if AbstractCoinDispenser.load() method announces coinsLoaded event.
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	
	@Test
	public void testLoadEvent() throws SimulationException, CashOverloadException {
		validDispenser = new CoinDispenser(5);
		validDispenser.connect(PowerGrid.instance());
		validDispenser.activate();
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.load(testCoin, testCoin, testCoin);
		Assert.assertEquals(3, testObserver.coinCount);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.load() method has the correct size after loading 4 coins.
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	
	@Test
	public void testLoad() throws SimulationException, CashOverloadException {
		validDispenser = new CoinDispenser(5);
		validDispenser.connect(PowerGrid.instance());
		validDispenser.activate();
		validDispenser.load(testCoin, testCoin, testCoin, testCoin);
		Assert.assertEquals(4,validDispenser.size());
	}
	
	/**
	 * Tests the AbstractCoinDispenser.load() method when the CoinDispenser is smaller than the number of coins loaded.
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	
	@Test (expected = CashOverloadException.class)
	public void testCoinOverloadLoad() throws SimulationException, CashOverloadException {
		validDispenser.load(testCoin, testCoin);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.load() method when loading a null coin.
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	
	@Test (expected = SimulationException.class)
	public void testNullCoinLoad() throws SimulationException, CashOverloadException {
		Coin nullCoin = null;
		validDispenser.load(nullCoin);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.unload() method when the CoinDispenser is not powered.
	 */
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredUnload() {
		validDispenser.disactivate();
		validDispenser.unload();
	}
	
	/**
	 * Tests if the coin that is returned when calling AbstractCoinDispenser.unload() is the same as the initial coin that is input.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	
	@Test
	public void testUnloadOneCoin() throws CashOverloadException, DisabledException {
		validDispenser.receive(testCoin);
		Assert.assertEquals(testCoin, validDispenser.unload().get(0));
	}
	
	/**
	 * Tests that the return value for AbstractCoinDispenser.unload() on an empty CoinDispenser is an empty ArrayList.
	 */
	
	@Test
	public void testUnloadEmptyDispenser() {
		Assert.assertEquals(new ArrayList<>(), validDispenser.unload());
	}
	
	/**
	 * Tests that the AbstractCoinDispenser.unload() method announces the coinsUnloaded event.
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	
	@Test
	public void testUnloadEvent() throws SimulationException, CashOverloadException {
		validDispenser = new CoinDispenser(5);
		validDispenser.connect(PowerGrid.instance());
		validDispenser.activate();
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.load(testCoin, testCoin, testCoin);
		validDispenser.unload();
		Assert.assertEquals(3, testObserver.coinsUnloaded);
	}
	
	/**
	 * Tests the AbstractCoinDispenser.emit() method when the CoinDispenser is not powered.
	 * @throws CashOverloadException
	 * @throws NoCashAvailableException
	 * @throws DisabledException
	 */
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredEmit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		CoinDispenser unpoweredDispenser = new CoinDispenser(1);
		unpoweredDispenser.emit();
	}
	
	/**
	 * Tests the AbstractCoinDispenser.emit() method when the CoinDispenser is disabled.
	 * @throws CashOverloadException
	 * @throws NoCashAvailableException
	 * @throws DisabledException
	 */
	
	@Test (expected = DisabledException.class)
	public void testDisabledEmit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		validDispenser.disable();
		validDispenser.emit();
	}
	
	/**
	 * Tests the AbstractCoinDispenser.emit() method when the CoinDispenser is empty.
	 * @throws CashOverloadException
	 * @throws NoCashAvailableException
	 * @throws DisabledException
	 */
	
	@Test (expected = NoCashAvailableException.class)
	public void testEmptyDispenserEmit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		validDispenser.emit();
	}
	
	/**
	 * Tests that the AbstractCoinDispenser.emit() method has consistent values in the CoinDispenser and it's sink after the method is called.
	 * @throws CashOverloadException
	 * @throws NoCashAvailableException
	 * @throws DisabledException
	 */
	
	@Test
	public void testRemovalEmit() throws SimulationException, CashOverloadException, NoCashAvailableException, DisabledException {
		validDispenser = new CoinDispenser(5);
		validDispenser.connect(PowerGrid.instance());
		validDispenser.activate();
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.load(testCoin, testCoin, testCoin);
		SinkStub testStub = new SinkStub();
		validDispenser.sink = testStub;
		validDispenser.emit();
		Assert.assertTrue(testObserver.coinCount == 2 && validDispenser.size() == 2 && testStub.coinCount == 1);
	}
	
	/**
	 * Tests if the AbstractCoinDispenser.emit() method announces the coinsEmpty event when the CoinDispenser is emptied.
	 * @throws CashOverloadException
	 * @throws DisabledException
	 * @throws NoCashAvailableException
	 */
	
	@Test
	public void testEmptyingDispenserEmit() throws CashOverloadException, DisabledException, NoCashAvailableException {
		SinkStub testStub = new SinkStub();
		validDispenser.sink = testStub;
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.receive(testCoin);
		validDispenser.emit();
		Assert.assertTrue(testObserver.isEmpty);
	}
	
}
