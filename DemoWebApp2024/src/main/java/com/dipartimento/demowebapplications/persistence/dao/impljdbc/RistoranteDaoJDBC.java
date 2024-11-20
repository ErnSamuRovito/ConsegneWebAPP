package com.dipartimento.demowebapplications.persistence.dao.impljdbc;

import com.dipartimento.demowebapplications.model.Piatto;
import com.dipartimento.demowebapplications.model.Ristorante;
import com.dipartimento.demowebapplications.persistence.DBManager;
import com.dipartimento.demowebapplications.persistence.dao.PiattoDao;
import com.dipartimento.demowebapplications.persistence.dao.RistoranteDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RistoranteDaoJDBC implements RistoranteDao {

    private final Connection connection;

    public RistoranteDaoJDBC(Connection conn) {
        this.connection = conn;
    }

    @Override
    public List<Ristorante> findAll() {
        List<Ristorante> ristoranti = new ArrayList<>();
        String query = "SELECT * FROM ristorante";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Ristorante ristorante = new RistoranteProxy();
                ristorante.setNome(rs.getString("nome"));
                ristorante.setDescrizione(rs.getString("descrizione"));
                ristorante.setUbicazione(rs.getString("ubicazione"));
                ristoranti.add(ristorante);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ristoranti;
    }

    @Override
    public Ristorante findByPrimaryKey(String nome) {
        String query = "SELECT nome, descrizione, ubicazione FROM ristorante WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Ristorante ristorante = new RistoranteProxy();
                    ristorante.setNome(nome);
                    ristorante.setDescrizione(resultSet.getString("descrizione"));
                    ristorante.setUbicazione(resultSet.getString("ubicazione"));
                    return ristorante;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Ristorante ristorante) {
        String query = "INSERT INTO ristorante (nome, descrizione, ubicazione) VALUES (?, ?, ?) " +
                "ON CONFLICT (nome) DO UPDATE SET descrizione = EXCLUDED.descrizione, ubicazione = EXCLUDED.ubicazione";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ristorante.getNome());
            statement.setString(2, ristorante.getDescrizione());
            statement.setString(3, ristorante.getUbicazione());
            statement.executeUpdate();
            savePiattiAssociati(ristorante);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePiattiAssociati(Ristorante ristorante) throws SQLException {
        PiattoDao piattoDao = DBManager.getInstance().getPiattoDao();
        for (Piatto piatto : ristorante.getPiatti()) {
            piattoDao.save(piatto);
            inserisciRelazioneRistorantePiatto(ristorante.getNome(), piatto.getNome());
        }
    }

    private void inserisciRelazioneRistorantePiatto(String nomeRistorante, String nomePiatto) throws SQLException {
        String query = "INSERT INTO ristorante_piatto (ristorante_nome, piatto_nome) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomeRistorante);
            statement.setString(2, nomePiatto);
            statement.execute();
        }
    }

    @Override
    public void delete(Ristorante ristorante) {
        // Implementazione per eliminare un ristorante
    }
}
