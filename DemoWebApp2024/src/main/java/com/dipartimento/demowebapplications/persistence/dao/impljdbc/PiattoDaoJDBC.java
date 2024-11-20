package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PiattoDaoJDBC implements PiattoDao {

    private final Connection connection;

    public PiattoDaoJDBC(Connection conn) {
        this.connection = conn;
    }

    @Override
    public List<Piatto> findAll() {
        List<Piatto> piatti = new ArrayList<>();
        String query = "SELECT * FROM piatto";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Piatto piatto = new Piatto();
                piatto.setNome(rs.getString("nome"));
                piatto.setIngredienti(rs.getString("ingredienti"));
                piatti.add(piatto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return piatti;
    }

    @Override
    public Piatto findByPrimaryKey(String nome) {
        String query = "SELECT nome, ingredienti FROM piatto WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Piatto(resultSet.getString("nome"), resultSet.getString("ingredienti"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Piatto piatto) {
        String query = "INSERT INTO piatto (nome, ingredienti) VALUES (?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET ingredienti = EXCLUDED.ingredienti";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, piatto.getNome());
            statement.setString(2, piatto.getIngredienti());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Piatto piatto) {
        // Implementazione per eliminare un piatto
    }

    @Override
    public List<Piatto> findAllByRistoranteName(String ristoranteNome) {
        List<Piatto> piatti = new ArrayList<>();
        String query = "SELECT p.nome, p.ingredienti FROM piatto p " +
                "JOIN ristorante_piatto rp ON p.nome = rp.piatto_nome " +
                "WHERE rp.ristorante_nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristoranteNome);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    piatti.add(new Piatto(resultSet.getString("nome"), resultSet.getString("ingredienti")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return piatti;
    }
}
