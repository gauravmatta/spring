package com.springimplant.complaintmanager.dao;

import com.springimplant.complaintmanager.entities.Complaint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class ComplaintDao {
	
	private Session session;
	
	public ComplaintDao(SessionFactory sessionFactory)
	{
		session=sessionFactory.openSession();
	}

	public void insertComplaint(Complaint complaint)
	{
		Transaction tx=session.beginTransaction();
		session.save(complaint);
		session.flush();
		tx.commit();
	}
	
	public List<Complaint> getAllComplaints()
	{
		Query<Complaint> q=session.createQuery("from Complaint",Complaint.class);
		List<Complaint> complaints=q.list();
		return complaints;
	}
	
	public void deleteComplaint(Integer ComplaintId)
	{
		Query<Complaint> q=session.createQuery("delete form Complaint where id=" + ComplaintId,Complaint.class);
		q.executeUpdate();
	}
	
	public void close()
	{
		session.close();
	}
}