package com.thelocalmarketplace.hardware.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.Sink;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;

import ca.ucalgary.seng300.simulation.*;
import powerutility.NoPowerException;
import powerutility.PowerGrid;

public class AbstractCVTest {
	private Currency currency = Currency.getInstance("CAD");
	private List<BigDecimal> coinDenominations = new ArrayList<>(Arrays.asList(new BigDecimal(0.05)));
	private CoinValidator coinValidator = new CoinValidator(currency,coinDenominations);
	private Map<BigDecimal, Sink<Coin>> standardSinks  = new HashMap<BigDecimal, Sink<Coin>>();
	private SinkStub<Coin> rejectionSinkStub = new SinkStub<Coin>();
	private SinkStub<Coin> rejectionSink = rejectionSinkStub;
	private SinkStub<Coin> overflowSinkStub = new SinkStub<Coin>();
	private SinkStub<Coin> overflowSink = overflowSinkStub;
	private Coin testCoin;
	private SinkStub<Coin> testStandardSink;
	@Before
	public void setUp() {
//		Sink<Coin> rejectionSink = new SinkStub<Coin>();
//		Sink<Coin> overflowSink = new SinkStub<Coin>();
//		Map<BigDecimal, Sink<Coin>> standardSinks = new HashMap<>();
		rejectionSinkStub = new SinkStub<Coin>();
		overflowSinkStub = new SinkStub<Coin>();
		rejectionSink = rejectionSinkStub;
		overflowSink = overflowSinkStub;
        coinValidator.connect(PowerGrid.instance());
        coinValidator.activate();
        testStandardSink = new SinkStub<Coin>();
        standardSinks.put(new BigDecimal(0.05), testStandardSink);
        coinValidator.setup(rejectionSink,standardSinks, overflowSink); 
        testCoin = new Coin(currency, new BigDecimal(0.05));
            }
	
	 @Test(expected = SimulationException.class)
	 public void testSetupWithNullRejectionSink() {
        CoinValidator coinValidator1 = new CoinValidator (currency,coinDenominations); // Create a concrete implementation    
        coinValidator1.setup(null, standardSinks, overflowSink);
	 }
	 
	 @Test(expected = SimulationException.class)
	 public void testSetupWithNullOverflowSink() {
	        CoinValidator coinValidator1 = new CoinValidator (currency,coinDenominations); // Create a concrete implementation    
	        coinValidator1.setup(rejectionSink, standardSinks, null);
		 }
	 @Test(expected = SimulationException.class)
	 public void testSetupWithNullStandardSinks() {
	        CoinValidator coinValidator1 = new CoinValidator (currency,coinDenominations); // Create a concrete implementation    
	        coinValidator1.setup(rejectionSink, null, overflowSink);
		 }
	 
	 @Test(expected = SimulationException.class)
	 public void testequalstdSinksandDenom() {
		 standardSinks.put(new BigDecimal(0.10), new SinkStub<Coin>());
		 standardSinks.put(new BigDecimal(0.15), new SinkStub<Coin>());
	     coinValidator.setup(rejectionSink, standardSinks, overflowSink); 
	 }
	 
	 @Test (expected = SimulationException.class)
	 public void testNullSinkOnStandardSink() {
         SinkStub<Coin> nullStub = null;
         standardSinks.put(new BigDecimal(0.25), nullStub);
         coinDenominations.add(new BigDecimal(0.25));
         coinValidator.setup(rejectionSink,standardSinks, overflowSink); 
	 }
	 
	 @Test (expected = SimulationException.class)
	 public void testUniqueSink() {
		SinkStub<Coin> sink1 = new SinkStub<Coin>();
		coinDenominations.add(new BigDecimal(0.25));
	    standardSinks.put(new BigDecimal(0.05), sink1);
        standardSinks.put(new BigDecimal(0.25), sink1);
        coinValidator.setup(rejectionSink,standardSinks, overflowSink);
		 }
		
	 @Test(expected = SimulationException.class)
	 public void testsetcontainRejectionSink() {
		 standardSinks.put(new BigDecimal(0.05), rejectionSink);
		 coinValidator.setup(rejectionSink,standardSinks, overflowSink);
	 }
	 
	 @Test (expected = SimulationException.class)
	 public void testSetContainsOverflowSink() {
		 standardSinks.put(new BigDecimal(0.05), overflowSink);
		 coinValidator.setup(rejectionSink,standardSinks, overflowSink);
	 }
	 
	 @Test (expected = NoPowerException.class)
	 public void testUnpoweredValidatorReceive() throws DisabledException, CashOverloadException {
		 CoinValidator tempValidator = new CoinValidator(currency,coinDenominations); 
		 tempValidator.receive(testCoin);
	 }
	 
	 @Test (expected = DisabledException.class)
	 public void testDisabledValiatorReceive() throws DisabledException, CashOverloadException {
		 coinValidator.disable();
		 coinValidator.receive(testCoin);
	 }
	 
	 @Test (expected = SimulationException.class)
	 public void testNullCoinReceive() throws DisabledException, CashOverloadException {
		 Coin nullCoin = null;
		 coinValidator.receive(nullCoin);
	 }
	 
	 @Test
	 public void testValidCoinDetectedReceive() throws DisabledException, CashOverloadException {
		 ValidatorObserverStub testObserver = new ValidatorObserverStub();
		 coinValidator.attach(testObserver);
		 coinValidator.receive(testCoin);
		 Assert.assertTrue(testObserver.validCoin && testStandardSink.coinCount == 1);
	 }
	 
	 @Test
	 public void testOverflowSinkReceive() throws DisabledException, CashOverloadException {
		 standardSinks  = new HashMap<BigDecimal, Sink<Coin>>();
		 standardSinks.put(new BigDecimal(0.05), new NoSpaceSinkStub<Coin>());
		 coinValidator.setup(rejectionSink, standardSinks, overflowSink);
		 coinValidator.receive(testCoin);
		 Assert.assertEquals(1, overflowSink.coinCount);
	 }
	 
	 @Test
	 public void testInvalidCoinReceive() throws DisabledException, CashOverloadException {
		 Coin invalidCoin = new Coin(currency, new BigDecimal(0.5));
		 coinValidator.receive(invalidCoin);
		 Assert.assertEquals(1, rejectionSink.coinCount);
	 }
	 
}
