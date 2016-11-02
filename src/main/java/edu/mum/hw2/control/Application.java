package edu.mum.hw2.control;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.mum.hw2.domain.Actor;
import edu.mum.hw2.domain.Category;
import edu.mum.hw2.domain.Movie;

public class Application {

	private static EntityManagerFactory emf;
	 
	static {
		try {
			emf = Persistence.createEntityManagerFactory("cs544");
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String[] args) {
		addMovies();
		printMoviesReport();
		emf.close();
	}

	private static void printMoviesReport() {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
			Query query = em.createQuery("SELECT e FROM Movie e");
			List<Movie> listMovies =  query.getResultList();
			
			for (Iterator iterator = listMovies.iterator(); iterator.hasNext();) {
				Movie movie = (Movie) iterator.next();
				System.out.println(movie.getName());
			}
			
			//Movie movie = em.find(Movie.class, 1);
			//System.out.println(movie.getName());
			tx.commit();
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) tx.rollback();
		} finally {
			if ((em != null) && (em.isOpen())) em.close();
		}
	}

	private static void addMovies() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

		// TODO your code
			
			Actor a1 = new Actor();
			a1.setName("Dipen");
			Actor a2 = new Actor();
			a2.setName("Suman");
			
			List<Actor> listActor = new ArrayList<>();
			listActor.add(a1);
			listActor.add(a2);
			
			Path p = FileSystems.getDefault().getPath("", "myFile");         
			byte [] fileData = Files.readAllBytes(p);
			//to see the contents of files
			//String str = new String(fileData, "UTF-8");
			
			
			Movie movie = new Movie();
			movie.setName("Superman");
			movie.setRating("5");
			movie.setCategory(Category.DRAMA);
			movie.setListActor(listActor);	
			movie.setCover(fileData);
			em.persist(movie);
			
			tx.commit();
		
		} catch (Throwable e) {
			if ((tx != null) && (tx.isActive())) tx.rollback();
		} finally {
			if ((em != null) && (em.isOpen())) em.close();
		}
	}

}
