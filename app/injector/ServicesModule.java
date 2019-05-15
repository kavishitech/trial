package injector;

import com.google.inject.AbstractModule;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import service.UserService;
import service.impl.UserServiceImpl;

public class ServicesModule extends AbstractModule{
	
	@Override
	  protected void configure() {
	    bindService();
	  }

	  private void bindService() {
	    bind(UserService.class).to(UserServiceImpl.class);
	    bind(UserDAO.class).to(UserDAOImpl.class);
	  }

}
