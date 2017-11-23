package com.example.gustavo.books.interfaces;

import java.util.List;

/**
 * Created by gustavo on 11/19/17.
 */

public interface IBaseRepository<G>{

    void save(G object);
    void update(G object);
    void delete(int id);
    G findOne(int id);
    List<G> findAll();
}
