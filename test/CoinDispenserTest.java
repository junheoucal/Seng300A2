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
 * Tests for the CoinDispenser concrete class, AbstractCoinDispenser, and it's related interfaces and observers.
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
	
	@Test (expected = SimulationException.class)
	public void testReceiveForNullCoin() throws CashOverloadException, DisabledException {
		Currency testCurrency = Currency.getInstance("CAD");
		Coin nullCoin = null;
		validDispenser.receive(nullCoin);
	}
	
	@Test (expected = NoPowerException.class)
	public void testReceiveUnpoweredDispenser() throws CashOverloadException, DisabledException {
		validDispenser = new CoinDispenser(1);
		validDispenser.receive(testCoin);
	}
	
	@Test (expected = DisabledException.class)
	public void testReceiveDisabledDispenser() throws CashOverloadException, DisabledException {
		Currency testCurrency = Currency.getInstance("CAD");
		Coin validCoin = new Coin(testCurrency, new BigDecimal("0.25"));
		validDispenser.disable();
		validDispenser.receive(validCoin);
	}
	
	@Test (expected = CashOverloadException.class)
	public void testCashOverloadDispenser() throws CashOverloadException, DisabledException {
		validDispenser.receive(testCoin);
		validDispenser.receive(testCoin);
	}
	
	@Test
	public void testReceiveCoinAddedEvent() throws CashOverloadException, DisabledException {
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.receive(testCoin);
		Assert.assertTrue(testObserver.coinCount == 1);
	}
	
	@Test
	public void testReceiveCoinsFullEvent() throws CashOverloadException, DisabledException {
		DispenserObserverStub testObserver = new DispenserObserverStub();
		validDispenser.attach(testObserver);
		validDispenser.receive(testCoin);
		Assert.assertTrue(testObserver.isFull);
	}
	
	@Test
	public void testEmptyDispenserHasSpace() {
		Assert.assertTrue(validDispenser.hasSpace());
	}

	@Test
	public void testNonEmptyDispenserHasSpace() {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		validDispenser2.connect(PowerGrid.instance());
		validDispenser2.activate();
		Assert.assertTrue(validDispenser2.hasSpace());
	}
	
	@Test
	public void testFullDispenserHasSpace() throws CashOverloadException, DisabledException {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		validDispenser2.connect(PowerGrid.instance());
		validDispenser2.activate();
		validDispenser2.receive(testCoin);
		validDispenser2.receive(testCoin);
		Assert.assertFalse(validDispenser2.hasSpace());
	}
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredDispenserHasSpace() {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		Assert.assertTrue(validDispenser2.hasSpace());
	}
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredDispenserSize() {
		CoinDispenser validDispenser2 = new CoinDispenser(2);
		Assert.assertEquals(0, validDispenser2.size());
	}
	
	@Test
	public void testEmptyCoinDispenserSize() {
		Assert.assertEquals(0, validDispenser.size());
	}
	
	@Test
	public void testFilledCoinDispenserSize() throws CashOverloadException, DisabledException {
		validDispenser.receive(testCoin);
		Assert.assertEquals(1, validDispenser.size());
	}
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredDispenserLoad() throws SimulationException, CashOverloadException {
		CoinDispenser unpoweredDispenser = new CoinDispenser(1);
		unpoweredDispenser.load(testCoin, testCoin, testCoin);
	}
	
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
	
	@Test
	public void testLoad() throws SimulationException, CashOverloadException {
		validDispenser = new CoinDispenser(5);
		validDispenser.connect(PowerGrid.instance());
		validDispenser.activate();
		validDispenser.load(testCoin, testCoin, testCoin, testCoin);
		Assert.assertEquals(4,validDispenser.size());
	}
	
	@Test (expected = CashOverloadException.class)
	public void testCoinOverloadLoad() throws SimulationException, CashOverloadException {
		validDispenser.load(testCoin, testCoin);
	}
	
	@Test (expected = NullPointerSimulationException.class)
	public void testNullCoinLoad() throws SimulationException, CashOverloadException {
		Coin nullCoin = null;
		validDispenser.load(nullCoin);
	}
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredUnload() {
		validDispenser.disactivate();
		validDispenser.unload();
	}
	
	@Test
	public void testUnloadOneCoin() throws CashOverloadException, DisabledException {
		validDispenser.receive(testCoin);
		Assert.assertEquals(testCoin, validDispenser.unload().get(0));
	}
	
	@Test
	public void testUnloadEmptyDispenser() {
		Assert.assertEquals(new ArrayList<>(), validDispenser.unload());
	}
	
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
	
	@Test (expected = NoPowerException.class)
	public void testUnpoweredEmit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		CoinDispenser unpoweredDispenser = new CoinDispenser(1);
		unpoweredDispenser.emit();
	}
	
	@Test (expected = DisabledException.class)
	public void testDisabledEmit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		validDispenser.disable();
		validDispenser.emit();
	}
	
	@Test (expected = NoCashAvailableException.class)
	public void testEmptyDispenserEmit() throws CashOverloadException, NoCashAvailableException, DisabledException {
		validDispenser.emit();
	}
	
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
