package com.scholastic.intl.writingawards.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericProducer {
	@Produces
    @PersistenceContext(unitName = "awards")
    private EntityManager em;
}