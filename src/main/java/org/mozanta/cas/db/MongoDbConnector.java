package org.mozanta.cas.db;

import com.mongodb.*;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

@PropertySource("classpath:mozanta.properties")
public class MongoDbConnector extends AbstractJdbcUsernamePasswordAuthenticationHandler implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MongoDbConnector.class);

    @Value("${mongodb.server.name}")
    private String databaseHost;

    @Value("${mongodb.server.port}")
    private int databasePort;

    @Value("${mongodb.database.name}")
    private String databaseName;

    @Value("${mongodb.collection.user}")
    private String userCollection;

    @Value("${mongodb.users.username}")
    private String username;

    @Value("${mongodb.users.password}")
    private String password;

    @Override
    protected final HandlerResult authenticateUsernamePasswordInternal(final UsernamePasswordCredential credential)
            throws GeneralSecurityException, PreventedException {

        final String encryptedPassword = getPasswordEncoder().encode(credential.getPassword());
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(databaseHost, databasePort);
        } catch (UnknownHostException e) {
            logger.error("UnknownHostException " + e);
        }

        if(null == mongoClient){
            throw new RuntimeException("Unable to create the db connection");
        }

        final DB db = mongoClient.getDB(databaseName);
        final DBCollection coll = db.getCollection(userCollection);

        // querying mongo db. v
        final BasicDBObject query = new BasicDBObject(username, credential.getUsername()).append(password, encryptedPassword);
        final DBCursor cursor = coll.find(query);
        try {
            while (cursor.hasNext()) {
                return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getUsername()), null);
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}