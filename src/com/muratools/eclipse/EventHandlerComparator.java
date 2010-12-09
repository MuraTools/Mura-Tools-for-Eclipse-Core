package com.muratools.eclipse;

import java.util.*;

public class EventHandlerComparator implements Comparator<EventHandler> 
{
	public int compare(EventHandler value1, EventHandler value2)
	{
		//int result = 0;
		return value1.getComponent().compareTo(value2.getComponent());
	}
}
