package org.mozanta.cas.db;

import com.mongodb.*;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

public class MongoDbConnector extends AbstractUsernamePasswordAuthenticationHandler {

    private static final Logger logger = LoggerFactory.getLogger(MongoDbConnector.class);

    private String databaseHost;

    private int databasePort;

    private String databaseName;

    private String userCollection;

    private String username;

    private String password;

    private MongoClient mongoClient;

    private DBCollection usersCollection;

    @Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        // querying mongo db for user name
        final BasicDBObject query = new BasicDBObject(username, credential.getUsername());
        final DBCursor cursor = getUsersCollection().find(query);

        if (cursor.hasNext()) {
            try {
                //Get the password
                final String encryptedDbPassword = (String) cursor.next().get(password);
                if (BCrypt.checkpw(credential.getPassword(), encryptedDbPassword))
                    return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getUsername()), null);
            } finally {
                cursor.close();
            }
        }

        return null;
    }

    private DBCollection getUsersCollection() {
        if (null == usersCollection) {
            if (null == mongoClient) {
                try {
                    mongoClient = new MongoClient(databaseHost, databasePort);
                } catch (UnknownHostException e) {
                    logger.error("UnknownHostException " + e);
                    throw new RuntimeException(e);
                }
            }
            final DB db = mongoClient.getDB(databaseName);
            usersCollection = db.getCollection(userCollection);
        }
        return usersCollection;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public int getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(int databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(String userCollection) {
        this.userCollection = userCollection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}