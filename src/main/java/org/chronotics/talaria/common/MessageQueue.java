package org.chronotics.talaria.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SG Lee
 * @since 3/20/2015
 * @description
 * This Class is wrapper class of ConcurrentLinkedQueue
 * You can manage the size of queue and the action when the queue is overflowed
 */

public class MessageQueue<E> implements Iterable<E> {
	private static final Logger logger = 
			LoggerFactory.getLogger(MessageQueue.class);
	public static int default_maxQueueSize = 1000;
	public enum OVERFLOW_STRATEGY {
		NO_INSERTION,
		DELETE_FIRST;
	}
	private ConcurrentLinkedQueue<E> queue = 
			new ConcurrentLinkedQueue<E>();
	
	private int maxQueueSize;
	private OVERFLOW_STRATEGY overflowStrategy;
	private Class<E> type;

	@SuppressWarnings("unused")
	private MessageQueue(Class<E> cls) {
		type = cls;
	}
	public MessageQueue(
			Class<E> cls,
			int _maxQueueSize, 
			OVERFLOW_STRATEGY _overflowStrategy) {
		type = cls;
		maxQueueSize = _maxQueueSize;
		overflowStrategy = _overflowStrategy;
	}
	
	public Class<E> getElementClass() {
		return type;
	}
	
	public int getMaxQueueSize() {
		return maxQueueSize;
	}

	public boolean add(E e) {
		if(queue.size() >= maxQueueSize) {
			switch (overflowStrategy) {
			case NO_INSERTION:
				logger.info("queue overflow, element is not inserted");
				return false;
			case DELETE_FIRST:
				logger.info("queue overflow, first element is removed");
				queue.poll();
				return queue.add(e);
			default:
				return false;
			}
		} else {
			return queue.add(e);
		}
	}
	
	public boolean addAll(Collection<? extends E> c) {
		if(queue.size() + c.size() >= maxQueueSize) {
			switch (overflowStrategy) {
			case NO_INSERTION:
				logger.info("queue overflow, element is not inserted");
				return false;
			case DELETE_FIRST:
				for(E e:c) {
					if(queue.size() >= maxQueueSize) {
						logger.info("queue overflow, first element is removed");
						queue.poll();
					}
					queue.add(e);
				}
				return true;
			default:
				return false;
			}
		} else {
			return queue.addAll(c);
		}
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public Iterator<E> iterator() {
		return queue.iterator();
	}
	
	public boolean offer(E e) {
		return queue.offer(e);
	}
	
	/**
	 * Description copied from interface: Queue
	 * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
	 * @return
	 * the head of this queue, or null if this queue is empty
	 */
	public E peek() {
		return queue.peek();
	}
	
	/**
	 * Description copied from interface: Queue
	 * Retrieves and removes the head of this queue, or returns null if this queue is empty.
	 * @return
	 * the head of this queue, or null if this queue is empty
	 */
	public E poll() {
		return queue.poll(); 
	}
	
	public boolean remove(Object o) {
		return queue.remove(o);
	}
	
	public int size() {
		return queue.size();
	}
	
	public Object[] toArray() {
		return queue.toArray();
	}
	
	public <E> E[] toArray(E[] a) {
		return queue.toArray(a);
	}
}
