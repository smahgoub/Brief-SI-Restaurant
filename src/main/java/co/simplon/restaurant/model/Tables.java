package co.simplon.restaurant.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Je crée une nouvelle classe
public class Tables {
       private int numTable;

    // Je contruit un Constructeur
    public Tables(int numTable) {

        this.numTable = numTable;
    }

    // Je redéfinie ma méthode toString
    @Override
    public String toString() {
        return " " + numTable;
    }

    // Je liste les tables disponibles
    public static void afficheTables(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultat = statement.executeQuery("SELECT num_table from tables");

        // Je boucle pour afficher tous les lignes du resultat
        while (resultat.next()) {
            Tables dbTables = new Tables(resultat.getInt("num_table"));

            // J'affiche dbTables
            System.out.println(dbTables.toString());

        }
    }}
