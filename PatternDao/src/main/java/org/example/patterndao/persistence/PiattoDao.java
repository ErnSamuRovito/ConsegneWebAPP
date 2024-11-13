package org.example.patterndao.persistence;

import org.example.patterndao.model.Piatto;

import java.util.List;

public interface PiattoDao {
    public List<Piatto> findAll();

    public Piatto findByPrimaryKey(String id);

    public void save(Piatto piatto);

    public void delete(Piatto piatto);

    List<Piatto> findAllByRistoranteName(String name);
}
