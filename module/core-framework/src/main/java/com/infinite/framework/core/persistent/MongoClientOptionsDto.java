package com.infinite.framework.core.persistent;

import com.mongodb.*;
import org.bson.codecs.configuration.CodecRegistry;

import javax.net.SocketFactory;

/**
 * Created by hx on 16-5-31.
 */
public class MongoClientOptionsDto {
    private String description;
    private ReadPreference readPreference = ReadPreference.primary();
    private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;
    private CodecRegistry codecRegistry = MongoClient.getDefaultCodecRegistry();

    private int minConnectionsPerHost;
    private int maxConnectionsPerHost = 100;
    private int threadsAllowedToBlockForConnectionMultiplier = 5;
    private int serverSelectionTimeout = 1000 * 30;
    private int maxWaitTime = 1000 * 60 * 2;
    private int maxConnectionIdleTime;
    private int maxConnectionLifeTime;
    private int connectTimeout = 1000 * 10;
    private int socketTimeout = 0;
    private boolean socketKeepAlive = false;
    private boolean sslEnabled = false;
    private boolean sslInvalidHostNameAllowed = false;
    private boolean alwaysUseMBeans = false;

    private int heartbeatFrequency = 10000;
    private int minHeartbeatFrequency = 500;
    private int heartbeatConnectTimeout = 20000;
    private int heartbeatSocketTimeout = 20000;
    private int localThreshold = 15;

    private String requiredReplicaSetName;
    private DBDecoderFactory dbDecoderFactory = DefaultDBDecoder.FACTORY;
    private DBEncoderFactory dbEncoderFactory = DefaultDBEncoder.FACTORY;
    private SocketFactory socketFactory = SocketFactory.getDefault();
    private boolean cursorFinalizerEnabled = true;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReadPreference getReadPreference() {
        return readPreference;
    }

    public void setReadPreference(ReadPreference readPreference) {
        this.readPreference = readPreference;
    }

    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

    public CodecRegistry getCodecRegistry() {
        return codecRegistry;
    }

    public void setCodecRegistry(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    public int getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(int minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public int getServerSelectionTimeout() {
        return serverSelectionTimeout;
    }

    public void setServerSelectionTimeout(int serverSelectionTimeout) {
        this.serverSelectionTimeout = serverSelectionTimeout;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getMaxConnectionIdleTime() {
        return maxConnectionIdleTime;
    }

    public void setMaxConnectionIdleTime(int maxConnectionIdleTime) {
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }

    public int getMaxConnectionLifeTime() {
        return maxConnectionLifeTime;
    }

    public void setMaxConnectionLifeTime(int maxConnectionLifeTime) {
        this.maxConnectionLifeTime = maxConnectionLifeTime;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public boolean isSocketKeepAlive() {
        return socketKeepAlive;
    }

    public void setSocketKeepAlive(boolean socketKeepAlive) {
        this.socketKeepAlive = socketKeepAlive;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public boolean isSslInvalidHostNameAllowed() {
        return sslInvalidHostNameAllowed;
    }

    public void setSslInvalidHostNameAllowed(boolean sslInvalidHostNameAllowed) {
        this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
    }

    public boolean isAlwaysUseMBeans() {
        return alwaysUseMBeans;
    }

    public void setAlwaysUseMBeans(boolean alwaysUseMBeans) {
        this.alwaysUseMBeans = alwaysUseMBeans;
    }

    public int getHeartbeatFrequency() {
        return heartbeatFrequency;
    }

    public void setHeartbeatFrequency(int heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }

    public int getMinHeartbeatFrequency() {
        return minHeartbeatFrequency;
    }

    public void setMinHeartbeatFrequency(int minHeartbeatFrequency) {
        this.minHeartbeatFrequency = minHeartbeatFrequency;
    }

    public int getHeartbeatConnectTimeout() {
        return heartbeatConnectTimeout;
    }

    public void setHeartbeatConnectTimeout(int heartbeatConnectTimeout) {
        this.heartbeatConnectTimeout = heartbeatConnectTimeout;
    }

    public int getHeartbeatSocketTimeout() {
        return heartbeatSocketTimeout;
    }

    public void setHeartbeatSocketTimeout(int heartbeatSocketTimeout) {
        this.heartbeatSocketTimeout = heartbeatSocketTimeout;
    }

    public int getLocalThreshold() {
        return localThreshold;
    }

    public void setLocalThreshold(int localThreshold) {
        this.localThreshold = localThreshold;
    }

    public String getRequiredReplicaSetName() {
        return requiredReplicaSetName;
    }

    public void setRequiredReplicaSetName(String requiredReplicaSetName) {
        this.requiredReplicaSetName = requiredReplicaSetName;
    }

    public DBDecoderFactory getDbDecoderFactory() {
        return dbDecoderFactory;
    }

    public void setDbDecoderFactory(DBDecoderFactory dbDecoderFactory) {
        this.dbDecoderFactory = dbDecoderFactory;
    }

    public DBEncoderFactory getDbEncoderFactory() {
        return dbEncoderFactory;
    }

    public void setDbEncoderFactory(DBEncoderFactory dbEncoderFactory) {
        this.dbEncoderFactory = dbEncoderFactory;
    }

    public SocketFactory getSocketFactory() {
        return socketFactory;
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        this.socketFactory = socketFactory;
    }

    public boolean isCursorFinalizerEnabled() {
        return cursorFinalizerEnabled;
    }

    public void setCursorFinalizerEnabled(boolean cursorFinalizerEnabled) {
        this.cursorFinalizerEnabled = cursorFinalizerEnabled;
    }

    public MongoClientOptions build() {
        return new MongoClientOptions.Builder()
                .alwaysUseMBeans(alwaysUseMBeans)
                .codecRegistry(codecRegistry)
                .connectionsPerHost(maxConnectionsPerHost)
                .connectTimeout(connectTimeout)
                .cursorFinalizerEnabled(cursorFinalizerEnabled)
                .dbDecoderFactory(dbDecoderFactory)
                .dbEncoderFactory(dbEncoderFactory)
                .description(description)
                .heartbeatConnectTimeout(heartbeatConnectTimeout)
                .heartbeatFrequency(heartbeatFrequency)
                .heartbeatSocketTimeout(heartbeatSocketTimeout)
                .localThreshold(localThreshold)
                .minConnectionsPerHost(minConnectionsPerHost)
                .maxConnectionIdleTime(maxConnectionIdleTime)
                .maxConnectionLifeTime(maxConnectionLifeTime)
                .maxWaitTime(maxWaitTime)
                .readPreference(readPreference)
                .requiredReplicaSetName(requiredReplicaSetName)
                .serverSelectionTimeout(serverSelectionTimeout)
                .socketFactory(socketFactory)
                .socketKeepAlive(socketKeepAlive)
                .socketTimeout(socketTimeout)
                .sslEnabled(sslEnabled)
                .sslInvalidHostNameAllowed(sslInvalidHostNameAllowed)
                .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier)
                .writeConcern(writeConcern)
                .build();
    }
}
