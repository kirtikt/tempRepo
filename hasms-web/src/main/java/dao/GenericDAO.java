package main.java.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO <T,ID extends Serializable> {
	public void add(T obj);
	
	public void update(T obj);
	public void delete(long id);
	public T findById(long id);
	public List<T> getList();	
}