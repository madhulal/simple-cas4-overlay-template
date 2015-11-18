package org.mozanta.cas.db;

import com.mongodb.*;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        final String encryptedPassword = getPasswordEncoder().encode(credential.getPassword());
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(databaseHost, databasePort);
        } catch (UnknownHostException e) {
            logger.error("UnknownHostException " + e);
            throw new GeneralSecurityException(e);
        }

        final DB db = mongoClient.getDB(databaseName);
        final DBCollection usersCollection = db.getCollection(userCollection);

        // querying mongo db.
        final BasicDBObject query = new BasicDBObject(username, credential.getUsername()).append(password, encryptedPassword);
        final DBCursor cursor = usersCollection.find(query);
        try {
            while (cursor.hasNext()) {
                return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getUsername()), null);
            }
        } finally {
            cursor.close();
        }
        return null;
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