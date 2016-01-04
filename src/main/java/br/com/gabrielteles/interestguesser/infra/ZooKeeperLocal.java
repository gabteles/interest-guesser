package br.com.gabrielteles.interestguesser.infra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
 
public class ZooKeeperLocal {
	
	private ZooKeeperServer zkServer;
	private ServerConfig config;
	private ServerCnxnFactory cnxnFactory;
	private FileTxnSnapLog txnLog;
	
	public ZooKeeperLocal(Properties zkProperties) throws FileNotFoundException, IOException {
		QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
		
		try {
		    quorumConfiguration.parseProperties(zkProperties);
		} catch(Exception e) {
		    throw new RuntimeException(e);
		}
 
		config = new ServerConfig();
		config.readFrom(quorumConfiguration);
	}
	
	public void start() throws IOException {
		try {
        	zkServer = new ZooKeeperServer();

            txnLog = new FileTxnSnapLog(
        		new File(config.getDataLogDir()), 
        		new File(config.getDataDir())
    		);
            
            zkServer.setTxnLogFactory(txnLog);
            zkServer.setTickTime(config.getTickTime());
            zkServer.setMinSessionTimeout(config.getMinSessionTimeout());
            zkServer.setMaxSessionTimeout(config.getMaxSessionTimeout());
            
            cnxnFactory = ServerCnxnFactory.createFactory();
            cnxnFactory.configure(
        		config.getClientPortAddress(),
                config.getMaxClientCnxns()
            );
            cnxnFactory.startup(zkServer);            
        } catch (InterruptedException e) {
        }
	}
	
	public void shutdown() throws IOException {
		cnxnFactory.shutdown();
		if (zkServer.isRunning()) {
            zkServer.shutdown();
        }
		
		if (txnLog != null) {
			txnLog.close();
		}
	}
}