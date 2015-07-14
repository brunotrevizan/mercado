package com.trevizan.util;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RequestScoped
public class EntityManagerProducer implements Serializable{
	private static final long serialVersionUID = 8139412935870189243L;
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("mercadotrevizan");
    private EntityManager entityManager = factory.createEntityManager();

    @Produces
    @RequestScoped
    public EntityManager criaEntityManager(){
        return entityManager;
    }

    public void dispose(@Disposes EntityManager em){
        em.close();
    }

}