package com.example.hashesapp.repository;

import com.example.hashesapp.exceptions.RepositoryException;
import com.example.hashesapp.model.Hash;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HashRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  public Hash saveHash(Hash hash) throws RepositoryException {
    hash.setId(UUID.randomUUID());
    try {
      userTransaction.begin();
      entityManager.persist(hash);
      userTransaction.commit();
      return hash;
    } catch (Exception e) {
      throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }
  }

  public Hash getHash(UUID id) {
    return entityManager.find(Hash.class, id);
  }

  public void removeHash(Hash hash) throws RepositoryException {
    try {
      userTransaction.begin();
      entityManager.remove(entityManager.merge(hash));
      userTransaction.commit();
    } catch (Exception e) {
      throw new RepositoryException(RepositoryException.REPOSITORY_REMOVE_EXCEPTION);
    }
  }

  public List<Hash> getAllHashes() {
    return entityManager.createQuery("SELECT entity FROM hash_table entity", Hash.class).getResultList();
  }
}
