package org.example.patterndao.persistence.dao.impljdbc;

import org.example.patterndao.model.Piatto;
import org.example.patterndao.model.Ristorante;
import org.example.patterndao.DBManager;

import java.util.List;

public class RistoranteProxy extends Ristorante {
    public List<Piatto> getPiatti() {
        if(this.piatti==null){
            this.piatti= DBManager.getInstance().getPiattoDao().findAllByRistoranteName(this.nome);
        }
        return piatti;
    }
}
