package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.Db;
import models.Client;
import helpers.helper;

public class ClientService {
    private Connection connection = Db.makeConnection();

    public List<Client> getAllClients() {

        List<Client> ClientList = new ArrayList<Client>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM clients");

            while (rs.next()) {
                Client Client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("cin"),
                        rs.getString("phone"));

                ClientList.add(Client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ClientList;

    }

    public int add(Client book) {

        int status = 0;

        try {
            String query = "INSERT INTO clients (name , cin , phone ) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setString(1, book.getName());
            ps.setString(2, book.getCin());
            ps.setString(3, book.getPhone());

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public Client find(Integer id) {

        Client Client = null;

        try {
            String query = "SELECT * FROM clients WHERE id=?";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            // Not found
            if (!rs.next()) {
                return null;
            }

            Client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("cin"),
                    rs.getString("phone"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Client;

    }

    public int update(Client client) {
        int status = 0;

        try {
            String query = "UPDATE `clients` SET `name`=?,`cin`=?,`phone`=? WHERE `id`=?";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setString(1, client.getName());
            ps.setString(2, client.getCin());
            ps.setString(3, client.getPhone());
            ps.setInt(4, client.getId());

            status = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    public int destroy(Integer id) {
        int status = 0;
        try {
            String query = "DELETE FROM `clients` WHERE `id` = ?";
            PreparedStatement ps = connection.prepareStatement(query);

            // set parameters of query
            ps.setInt(1, id);

            status = ps.executeUpdate();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return status;
    }

    public Boolean checkIfClientExist(int id) {
        return helper.checkIfExist("clients", "id", Integer.toString(id));
    }

}
