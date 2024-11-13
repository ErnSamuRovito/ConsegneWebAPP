package org.example.patterndao.persistence.dao.impljdbc;

import org.example.patterndao.model.Ristorante;
import org.example.patterndao.persistence.RistoranteDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDaoJDBC implements RistoranteDao {
    Connection connection = null;

    public RistoranteDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Ristorante> findAll() {
        List<Ristorante> ristoranti = new ArrayList<Ristorante>();
        String query = "select * from ristorante";

        System.out.println("going to execute:"+query);

        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Ristorante rist = new RistoranteProxy();
                rist.setNome(rs.getString("nome"));
                rist.setDescrizione(rs.getString("descrizione"));
                rist.setUbicazione(rs.getString("ubicazione"));
                ristoranti.add(rist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ristoranti;
    }

    @Override
    public Ristorante findByPrimaryKey(String nome) {
        return null;
    }

    @Override
    public void save(Ristorante ristorante) {

    }

    @Override
    public void delete(Ristorante ristorante) {

    }
}
