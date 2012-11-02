package com.duxland.basedatosprueba.SQL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CListDatos implements List<CDatos>
{
	ArrayList<CDatos> m_lista ;
	public CListDatos()
	{
		super();
		m_lista = new ArrayList<CDatos>();
	}
	public boolean add(CDatos object) 
	{			
		return m_lista.add(object);
	}

	public void add(int location, CDatos object) 
	{	
		m_lista.add(location, object);
	}

	public boolean addAll(Collection<? extends CDatos> arg0) 
	{
		return m_lista.addAll(arg0);		
	}

	public boolean addAll(int arg0, Collection<? extends CDatos> arg1) 
	{
		return m_lista.addAll(arg0, arg1);
	}

	public void clear() 
	{	
		m_lista.clear();
	}

	public boolean contains(Object object) 
	{	
		return m_lista.contains(object);
	}

	public boolean containsAll(Collection<?> arg0) 
	{	
		return m_lista.containsAll(arg0);
	}

	public CDatos get(int location) 
	{
		return m_lista.get(location);
	}

	public int indexOf(Object object) 
	{	
		return m_lista.indexOf(object);
	}

	public boolean isEmpty() 
	{	
		return m_lista.isEmpty();
	}

	public Iterator<CDatos> iterator() 
	{		
		return m_lista.iterator();
	}

	public int lastIndexOf(Object object) 
	{	
		return m_lista.lastIndexOf(object);
	}

	public ListIterator<CDatos> listIterator() 
	{	
		return m_lista.listIterator();
	}

	public ListIterator<CDatos> listIterator(int location) 
	{	
		return m_lista.listIterator(location);
	}

	public CDatos remove(int location) 
	{	
		return m_lista.remove(location);
	}

	public boolean remove(Object object) 
	{	
		return m_lista.remove(object);
	}

	public boolean removeAll(Collection<?> arg0) 
	{		
		return m_lista.removeAll(arg0);
	}

	public boolean retainAll(Collection<?> arg0) 
	{
		return m_lista.retainAll(arg0);
	}

	public CDatos set(int location, CDatos object) 
	{		
		return m_lista.set(location, object);
	}

	public int size() 
	{
		return m_lista.size();
	}

	public List<CDatos> subList(int start, int end) 
	{
		return m_lista.subList(start, end);
	}

	public Object[] toArray() 
	{
		return m_lista.toArray();
	}

	public <T> T[] toArray(T[] array) 
	{
		return m_lista.toArray(array);
	}
}