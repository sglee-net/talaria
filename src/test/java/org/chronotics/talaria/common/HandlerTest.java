package org.chronotics.talaria.common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.chronotics.talaria.common.Handler.PROPAGATION_RULE;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class HandlerTest {
	private static Handler<List<Integer>> finalHandler = null;
	private static Handler<List<Integer>> increaseHandler = null;
	private static Handler<List<Integer>> decreaseHandler = null;
	
	@Rule
    public ExpectedException exceptions = ExpectedException.none();

	@BeforeClass
	public static void setUp() {
		finalHandler = 
				new Handler<List<Integer>>(
						PROPAGATION_RULE.SIMULTANEOUSLY, 
						null) {
			@Override
			public List<Integer> call() throws Exception {
				List<Integer> v = this.getValue();
				List<Integer> rt = 
						v.stream()
						.map(e -> e)
						.collect((Collectors.toList()));
						return rt;
			}

			@Override
			public int getFutureTimeout() {
				// TODO Auto-generated method stub
				return 500;
			}
		};
		
		increaseHandler = 
				new Handler<List<Integer>>(
						PROPAGATION_RULE.SIMULTANEOUSLY,
						null) {
					@Override
					public List<Integer> call() throws Exception {
						List<Integer> v = this.getValue();
						List<Integer> rt = 
								v.stream()
								.map(e -> e=e+1)
								.collect((Collectors.toList()));
								return rt;
					}

					@Override
					public int getFutureTimeout() {
						// TODO Auto-generated method stub
						return 500;
					}
		};		
		
		decreaseHandler = 
				new Handler<List<Integer>>(
						PROPAGATION_RULE.SIMULTANEOUSLY,
						null) {
					@Override
					public List<Integer> call() throws Exception {
						List<Integer> v = this.getValue();
						List<Integer> rt = 
								v.stream()
								.map(e -> e=e-1)
								.collect((Collectors.toList()));
								return rt;
					}

					@Override
					public int getFutureTimeout() {
						// TODO Auto-generated method stub
						return 500;
					}
		};
	}

	@Test
	public void increase() {
		List<Integer> v = new ArrayList<Integer>();
		for(int i=0; i<100; i++) {
			v.add(i);
		}
		increaseHandler.addNextHandler(PROPAGATION_RULE.SIMULTANEOUSLY, null);
		try {
			Future<List<Integer>> future = increaseHandler.execute(v);
			List<Integer> rt = future.get();
			assertEquals(rt.size(), 100);
			int expected = 1;
			for(Integer e : rt) {
//				System.out.println(e);
				assertEquals(expected, e.intValue());
				expected++;
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void decrease() {
		List<Integer> v = new ArrayList<Integer>();
		for(int i=0; i<100; i++) {
			v.add(i);
		}
		decreaseHandler.addNextHandler(PROPAGATION_RULE.SIMULTANEOUSLY, null);
		try {
			Future<List<Integer>> future = decreaseHandler.execute(v);
			List<Integer> rt = future.get();
			assertEquals(rt.size(), 100);
			int expected = -1;
			for(Integer e : rt) {
//				System.out.println(e);
				assertEquals(expected, e.intValue());
				expected++;
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void increaseDecrease() {
		List<Integer> v = new ArrayList<Integer>();
		for(int i=0; i<100; i++) {
			v.add(i);
		}
		decreaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, null);
		increaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, decreaseHandler);
		try {
			Future<List<Integer>> future = increaseHandler.execute(v);
			List<Integer> rt = future.get();
			int expected = 0;
			for(Integer e : rt) {
//				System.out.println(e);
				assertEquals(expected, e.intValue());
				expected++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void propagateHandler() {
		List<Integer> v = new ArrayList<Integer>();
		for(int i=0; i<100; i++) {
			v.add(i);
		}
		finalHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, null);
		decreaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, finalHandler);
		increaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, decreaseHandler);
		try {
			Future<List<Integer>> future = increaseHandler.execute(v);
			List<Integer> rt = future.get();
			int expected = 0;
			for(Integer e : rt) {
//				System.out.println(e);
				assertEquals(expected, e.intValue());
				expected++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void propagationRuleException() {
		exceptions.expect(IllegalStateException.class);

		finalHandler.addNextHandler(PROPAGATION_RULE.SIMULTANEOUSLY, null);
		decreaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, finalHandler);
		increaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, decreaseHandler);
	}
	
	@Test
	public void nextHandlerException() {
		exceptions.expect(IllegalStateException.class);

		finalHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, null);
		decreaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, null);
		increaseHandler.addNextHandler(PROPAGATION_RULE.STEP_BY_STEP_REGENERATED_ARG, decreaseHandler);
	}
}
