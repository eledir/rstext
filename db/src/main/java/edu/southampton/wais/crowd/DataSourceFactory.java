package edu.southampton.wais.crowd;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import edu.southampton.wais.utility.Logger;



public class DataSourceFactory {

    private static DataSourceFactory singleton = new DataSourceFactory();

    public static DataSourceFactory getInstance() {
        return singleton;
    }
    
    private Properties proprieta;
    private DataSource dataSource;
    
    public DataSource getDataSource() {
        return this.dataSource;
    }

    private DataSourceFactory() {
        try {
            caricaProprieta();
            inizializza();
        } catch (DAOException e) {
            Logger.logSevere("Impossibile inizializzare la DataSourceFactory");
        }
    }
         
    private void caricaProprieta() throws DAOException {
        
        
        InputStream stream = null;
        try {
            String nomeFile="/pool.properties";
        	
        	stream= this.getClass().getResourceAsStream(nomeFile);
            
        	
        	
            this.proprieta = new Properties();
            this.proprieta.load(stream);
            Logger.logFiner(this.proprieta.toString());
            
        } catch (Exception e) {
            Logger.logSevere(e.toString());
            throw new DAOException("Impossibile caricare il file di configurazione " + e);
        } finally {
            if (stream != null) {
                try {stream.close();} catch (Exception e) {}
            }
        }
    }
    
    private void inizializza() throws DAOException {
        String tipoDataSource = this.proprieta.getProperty("tipoDataSource");
        if (!tipoDataSource.equals("semplice") && !tipoDataSource.equals("pool")) {
            throw new DAOException("Valore scorretto del parametro di configurazione tipoDataSource: " + tipoDataSource);
        }
        String driver = this.proprieta.getProperty("driver");
        String databaseUri = this.proprieta.getProperty("databaseUri");
        String userName = this.proprieta.getProperty("userName");
        String password = this.proprieta.getProperty("password");
        if (tipoDataSource.equals("semplice")) {
            this.dataSource = new DataSourceSimple(driver, databaseUri, userName, password);
        } else if (tipoDataSource.equals("pool")) {
            this.dataSource = new DataSourcePool(driver, databaseUri, userName, password);
        }
    }

    
}
