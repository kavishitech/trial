package dao.impl;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import dao.UserDAO;
import model.User;
import models.DatabaseExecutionContext;
import play.db.jpa.JPAApi;

public class UserDAOImpl implements UserDAO {

	private final JPAApi jpaApi;
	private final DatabaseExecutionContext executionContext;

	@Inject
	public UserDAOImpl(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
		this.jpaApi = jpaApi;
		this.executionContext = executionContext;
	}

	@Override
	public CompletionStage<User> create(User user) {
		return supplyAsync(() -> wrap(em -> insert(em, user)), executionContext);
	}

	private <T> T wrap(Function<EntityManager, T> function) {
		return jpaApi.withTransaction(function);
	}

	private User insert(EntityManager em, User user) {
		em.persist(user);
		return user;
	}

	@Override
	public List<User> getUsers(String userName) throws InterruptedException,ExecutionException {
		// TODO Auto-generated method stub
		CompletionStage<List<User>> user = supplyAsync(() -> wrap(em -> get(em, userName)), executionContext);
        return user.toCompletableFuture().get(); 

	}

	private List<User> get(EntityManager em, String userName) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cqry = cb.createQuery(User.class);
		Root<User> from = cqry.from(User.class);
		cqry.where(cb.equal(from.get("userName"), userName));

		List<User> user = em.createQuery(cqry).getResultList();
		return user;
	}
}
