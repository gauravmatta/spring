package org.javaimplant.newsfeed.Init;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javaimplant.newsfeed.Servlets.NewsServlet;
import org.javaimplant.newsfeed.data.DataAccessObject;

public class Init implements ServletContextListener {
	
	private Logger logger=Logger.getLogger(this.getClass());

	public void contextInitialized(ServletContextEvent sce) 
	{
		ServletContext servletContext = sce.getServletContext();
		try 
		{
			contextInitialized2(servletContext);
		}
		catch (Exception e)
		{
			logger.error("Initialization failed.", e);
			throw new RuntimeException(e);
		}
		logger.debug("Initialization succeeded.");	
	}
	
	private void contextInitialized2(ServletContext servletContext)
	throws Exception 
	{
		InitialContext enc = new InitialContext();
		Context compContext = (Context) enc.lookup("java:/comp/env");
		DataSource dataSource = (DataSource) compContext.lookup("mydatasource");				
		NewsServlet.setDataSource(dataSource);
		DataAccessObject.setDatasource(dataSource);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
