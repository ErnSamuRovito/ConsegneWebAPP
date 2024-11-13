package org.example.patterndao.persistence;

import org.example.patterndao.model.Ristorante;

import java.util.List;

public interface RistoranteDao {
    public List<Ristorante> findAll();
    public Ristorante findByPrimaryKey(String nome);
    public void save(Ristorante ristorante);
    public void delete(Ristorante ristorante);
}
