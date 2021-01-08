/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_acces;

//import com.sun.istack.internal.NotNull;
import java.sql.*;

import database_acces.FichierConfig;
import database_acces.FichierLog;

/**
 *
 * @author isen0
 */
public class ConnexionDB {
    
//VARIABLES
    private String host;
    private String port;
    private String dbName;
    private String user;
    private String password;
    private String connectLink;
    private String className;
    private Connection connect;
    private String supplement;

//SETTERS
    private void setHost(String host)
    {
        this.host = host;
    }
    private String getHost()
    {
        return this.host;
    }

    private void setPort(String port) {
        this.port = port;
    }
    private String getPort() {
        return this.port;
    }

    private void setDbName(String dbName) {
        this.dbName = dbName;
    }
    private String getDbName() {
        return this.dbName;
    }

    private void setUser(String user)
    {
        this.user = user;
    }
    private String getUser()
    {
        return this.user;
    }

    private void setPassword(String password)
    {
        this.password = password;
    }
    private String getPassword()
    {
        return this.password;
    }

    private void setConnectLink(String connectLink)
    {
        this.connectLink = connectLink;
    }
    private String getConnectLink()
    {
        return this.connectLink;
    }

    private void setClassName(String className)
    {
        this.className = className;
    }
    private String getClassName()
    {
        return this.className;
    }

    private void setConnect(Connection connect)
    {
        this.connect = connect;
    }
    private Connection getConnect()
    {
        return this.connect;
    }

    private void setSupplement(String supplement)
    {
        this.supplement = supplement;
    }
    private String getSupplement()
    {
        return this.supplement;
    }
    
//CONSTRUCTEURS
    public ConnexionDB(String host,String port,String dbName,String user,String password,String connectLink,String className) throws ClassNotFoundException
    {
        this.setHost(host);
        this.setPort(port);
        this.setDbName(dbName);
        this.setUser(user);
        this.setPassword(password);
        this.setConnectLink(connectLink);
        this.setClassName(className);

        Class.forName(this.getClassName());
    }

    public ConnexionDB(String propertiesFileName,String dbname) throws ClassNotFoundException
    {
        System.out.println(" TEST PATH : "+ propertiesFileName);
        System.out.println(" TEST NOM DB : "+ dbname);
        if(propertiesFileName.equals(database_acces.FichierConfig.getNomsFichs("config")))
        {
            //System.out.println(" TEST PATH : "+ propertiesFileName);
            this.setHost(FichierConfig.get("HOST"));
            this.setPort(FichierConfig.get("PORT"));
            this.setDbName(dbname);
            this.setUser(FichierConfig.get("USER"));
            this.setPassword(FichierConfig.get("PASSWORD"));
            this.setConnectLink(FichierConfig.get("CONNECTLINK"));
            this.setClassName(FichierConfig.get("CLASSNAME"));
            if(FichierConfig.get("SUPP") != null)
            {
                this.setSupplement(FichierConfig.get("SUPP"));
            }
            System.out.println(" TEST DRIVER : "+ this.getConnectLink());
            System.out.println(" TEST CLASSNAME : "+ this.getClassName());
            Class.forName(this.getClassName());
        }
        else if(propertiesFileName.equals(database_acces.FichierConfig.getNomsFichs("configMouv")))
        {
            //System.out.println(" TEST PATH : "+ propertiesFileName);
            this.setHost(FichierConfig.getMouv("HOST"));
            this.setPort(FichierConfig.getMouv("PORT"));
            this.setDbName(dbname);
            this.setUser(FichierConfig.getMouv("USER"));
            this.setPassword(FichierConfig.getMouv("PASSWORD"));
            this.setConnectLink(FichierConfig.getMouv("CONNECTLINK"));
            this.setClassName(FichierConfig.getMouv("CLASSNAME"));
            if(FichierConfig.getMouv("SUPP") != null)
            {
                this.setSupplement(FichierConfig.getMouv("SUPP"));
            }
            //System.out.println(" TEST DRIVER : "+ this.getConnectLink());
            //System.out.println(" TEST CLASSNAME : "+ this.getClassName());
            Class.forName(this.getClassName());
        }
        else if(propertiesFileName.equals(database_acces.FichierConfig.getNomsFichs("configCompta")))
        {
            //System.out.println(" TEST PATH : "+ propertiesFileName);
            this.setHost(FichierConfig.getCompta("HOST"));
            this.setPort(FichierConfig.getCompta("PORT"));
            this.setDbName(dbname);
            this.setUser(FichierConfig.getCompta("USER"));
            this.setPassword(FichierConfig.getCompta("PASSWORD"));
            this.setConnectLink(FichierConfig.getCompta("CONNECTLINK"));
            this.setClassName(FichierConfig.getCompta("CLASSNAME"));
            if(FichierConfig.getCompta("SUPP") != null)
            {
                this.setSupplement(FichierConfig.getCompta("SUPP"));
            }
            //System.out.println(" TEST DRIVER : "+ this.getConnectLink());
            //System.out.println(" TEST CLASSNAME : "+ this.getClassName());
            Class.forName(this.getClassName());
        }
        else
        {
            //System.out.println(" TEST PATH : "+ propertiesFileName);
            this.setHost(FichierConfig.getWeb("HOST", propertiesFileName));
            this.setPort(FichierConfig.getWeb("PORT", propertiesFileName));
            this.setDbName(dbname);
            this.setUser(FichierConfig.getWeb("USER", propertiesFileName));
            this.setPassword(FichierConfig.getWeb("PASSWORD", propertiesFileName));
            this.setConnectLink(FichierConfig.getWeb("CONNECTLINK", propertiesFileName));
            this.setClassName(FichierConfig.getWeb("CLASSNAME", propertiesFileName));
            if(FichierConfig.getWeb("SUPP", propertiesFileName) != null)
            {
                this.setSupplement(FichierConfig.getWeb("SUPP", propertiesFileName));
            }
            //System.out.println(" TEST DRIVER : "+ this.getConnectLink());
            //System.out.println(" TEST CLASSNAME : "+ this.getClassName());
            Class.forName(this.getClassName());
        }
    }
    
    
    
//METHODES   
    public void openConnection() throws SQLException
    {
        this.setConnect(DriverManager.getConnection(this.getConnectLink() + this.getHost() + ":" + this.getPort() + "/" + this.getDbName() + ""+ this.getSupplement() +"",this.getUser(),/*this.getPassword()*/""));
    }

    public void closeConnection() throws SQLException
    {
        if(this.getConnect() != null)
        {
            this.getConnect().close();
            this.setConnect(null);
        }
    }

    public ResultSet select(String request) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        Statement statement = this.getConnect().createStatement();
        return statement.executeQuery(request);
    }
    
    public void delete(String request) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        Statement statement = this.getConnect().createStatement();
        statement.execute(request);
    }

    public ResultSet select(String request,Object... args) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        if(args == null)
        {
            return this.select(request);
        }
        PreparedStatement statement = this.prepareStatement(request,args);
        return statement.executeQuery();
    }

    public void insert(String request) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        Statement statement = this.getConnect().createStatement();
        statement.execute(request);
    }

    public void insert(String request, /*@NotNull*/ Object... args) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        PreparedStatement statement = this.prepareStatement(request,args);
        statement.execute();
    }

    public int update(String request) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        Statement statement = this.getConnect().createStatement();
        return statement.executeUpdate(request);
    }

    public int update(String request,Object... args) throws SQLException
    {
        if(this.getConnect() == null)
            throw new SQLException("Connection not established");
        if(args == null)
            return this.update(request);
        PreparedStatement statement = this.prepareStatement(request, args);
        return statement.executeUpdate();
    }

    private PreparedStatement prepareStatement(String request,Object... args) throws SQLException
    {
        PreparedStatement statement = this.getConnect().prepareStatement(request);
        int cpt = 1;
        for(Object arg : args)
        {
            switch(arg.getClass().getName())
            {
                case "java.lang.Integer":
                    statement.setInt(cpt++,(int)arg);
                    break;
                case "java.lang.Double":
                    statement.setDouble(cpt++,(double)arg);
                    break;
                case "java.lang.Float":
                    statement.setFloat(cpt++,(float)arg);
                    break;
                case "java.lang.Boolean":
                    statement.setBoolean(cpt++,(boolean)arg);
                    break;
                default:
                    statement.setString(cpt++,(String)arg);
                    break;
            }
        }
        return statement;
    }

    public void enableAutoCommit()
    {
        if(this.getConnect() == null)
            return;
        try
        {
            this.getConnect().setAutoCommit(true);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void disableAutoCommit()
    {
        if(this.getConnect() == null)
            return;
        try
        {
            this.getConnect().setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void commit()
    {
        if(this.getConnect() == null)
            return;
        try
        {
            this.getConnect().commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public void rollback()
    {
        if(this.getConnect() == null)
            return;
        try
        {
            this.getConnect().rollback();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
