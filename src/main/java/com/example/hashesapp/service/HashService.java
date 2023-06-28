package com.example.hashesapp.service;

import com.example.hashesapp.exceptions.RepositoryException;
import com.example.hashesapp.model.Hash;
import com.example.hashesapp.repository.HashRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.util.encoders.Hex;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HashService {
  @Inject
  private HashRepository hashRepository;

  public Hash saveHash(Hash hash) throws RepositoryException {
    return hashRepository.saveHash(hash);
  }

  public Hash getHash(UUID id) {
    Hash hash = hashRepository.getHash(id);
    if (hash != null) {
      return hash;
    } else {
      throw new NullPointerException();
    }
  }

  public void removeHash(UUID id) throws RepositoryException {
    Hash hash = getHash(id);
    hashRepository.removeHash(hash);
  }

  public List<Hash> getAllHashes() {
    return hashRepository.getAllHashes();
  }

  public Hash generateHash(byte[] file) {
    //TODO convert to hash from file using algorithm
    Blake2bDigest blake2bDigest = new Blake2bDigest(512); // Ustawienie rozmiaru hasha na 512 bitów

    blake2bDigest.update(file, 0, file.length);

    byte[] hashBytes = new byte[blake2bDigest.getDigestSize()];
    blake2bDigest.doFinal(hashBytes, 0);

    String hashString = Hex.toHexString(hashBytes);

    return new Hash(UUID.randomUUID(), hashString);

  }
}
