package co.simplon.restaurant;

import co.simplon.restaurant.model.Plat;
import co.simplon.restaurant.model.Serveurs;
import co.simplon.restaurant.model.Tables;

import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        // Je me connecte à la base
        String url = "jdbc:postgresql://localhost:5432/Restaurant";
        String user = "postgres";
        String password = "postgres";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password);

            // J'affiche le menu du programme

            // On déclare un scanner pour lire les entrées du scanner
            Scanner sc = new Scanner(System.in);

            // J'affiche le menu du programme
            System.out.println("Bonjour, merci de faire votre choix : ");
            System.out.println("Choix 1 : Sauvegarder une nouvelle facture");
            System.out.println("Choix 2 : Lister les plats les plus vendus");
            System.out.println("Choix 3 : Lister les tables les plus rentables");
            System.out.println("Choix 0 : Fin");

            // Je récupère le choix utilisateur et je mets en place d'un switch case
            int number = sc.nextInt();

            switch (number) {
                case 0: {
                    System.out.println("Merci et bonne journée.");
                    break;
                }
                case 1: {
                    choice1(connection);
                    break;
                }
                case 2: {
                    choice2(connection);
                    break;
                }
                case 3: {
                    choice3(connection);
                    break;
                }
                default: {
                    System.out.println("Merci d'entrer une valeur correcte !");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Methode pour afficher les plats les plus vendus
    private static void choice2(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultat = statement.executeQuery("SELECT plat.nom, SUM(facture_ligne.nb_plat * plat.px_unitaire) as somme_plat\n" +
                "FROM facture_ligne\n" +
                "         JOIN plat ON facture_ligne.plat_idx = plat.id\n" +
                "GROUP BY plat.nom\n" +
                "ORDER BY somme_plat DESC;");

        while (resultat.next()) {
            System.out.println(resultat.getString("nom") + " " + resultat.getFloat("somme_plat"));
        }
        resultat.close();
        statement.close();
        connection.close();
    }

    // Methode pour afficher les plats les plus vendus
    private static void choice3(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultat = statement.executeQuery("SELECT tables.num_table, SUM(facture_ligne.nb_plat * plat.px_unitaire) as somme_plat\n" +
                "FROM facture\n" +
                "         JOIN tables ON facture.tables_idx = tables.id\n" +
                "         JOIN facture_ligne ON facture.id = facture_ligne.facture_idx\n" +
                "         JOIN plat ON facture_ligne.plat_idx = plat.id\n" +
                "GROUP BY tables.num_table\n" +
                "ORDER BY somme_plat DESC;\n");

        while (resultat.next()) {
            System.out.println(resultat.getInt("num_table") + " " + resultat.getFloat("somme_plat"));
        }
        resultat.close();
        statement.close();
        connection.close();
    }

    // Methode pour sauvegarder une nouvelle facture
    private static void choice1(Connection connection) throws SQLException {
        // On déclare un scanner pour lire les entrées du scanner
        Scanner sc = new Scanner(System.in);

        //  J'affiche les serveurs et les tables disponibles
        System.out.println("Merci de choisir un ID Serveur dans la lsite ci dessous : ");
        Serveurs.afficheServeurs(connection);
        int idServeur = sc.nextInt();

        System.out.println("Merci de choisir le numéro de la table dans la lsite ci dessous : ");
        Tables.afficheTables(connection);
        int idTable = sc.nextInt();

        // J'insers dans la table Facture les données saisies pour serveurs et tables
        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("INSERT INTO facture (serveurs_idx,tables_idx) VALUES ('" + idServeur + "','" + idTable + "')", Statement.RETURN_GENERATED_KEYS);
        ResultSet cleFact = ordreSQL.getGeneratedKeys();

        int idFact=0;

        while (cleFact.next()) {
            idFact = cleFact.getInt(1);
        }

        // Tant que j'ai des plats à saisir, j'affiche l'id, le nom des plats et leur prix unitaire
        int idPlat;
        do {
            System.out.println("Merci de choisir un plat dans la liste ci dessous : ");
            Plat.affichePlat(connection);
            idPlat = sc.nextInt();
            sc.nextLine();

            if (idPlat > 0) {
                System.out.print("En quelle quantité ?");
                int quantite = sc.nextInt();
                sc.nextLine();

                // J'insers l'id facture, l'id plat et la quatite  dans facture_ligne
                String sql = "INSERT INTO facture_ligne (facture_idx,plat_idx,nb_plat) VALUES (" + idFact + "," + idPlat + " ," + quantite + ")";

                ordreSQL.execute(sql);
            }
        }
        while (idPlat > 0);

        ordreSQL.close();
    }
}
