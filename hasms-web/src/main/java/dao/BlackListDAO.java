package main.java.dao;

import org.hibernate.Session;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Blacklist;


public class BlackListDAO {

	public void insert()
	{
		// insert
		BlacklistManagerDAO bm=new BlacklistManagerDAO();
		Blacklist b=new Blacklist();
		b.setNumber("875345");
				bm.add(b);
		
	}
	public void delete()
	{// Delete
		BlacklistManagerDAO bm=new BlacklistManagerDAO();
		int id=4;
		Session sess;
		try {
			
			Blacklist b=bm.findById(id);
		
			bm.delete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
