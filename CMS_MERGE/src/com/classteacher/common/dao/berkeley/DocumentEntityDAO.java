package com.classteacher.common.dao.berkeley;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.SequenceConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

public class DocumentEntityDAO<T> {
	private static final Log log = LogFactory.getLog(DocumentEntityDAO.class);
	private String dataDirectory;
	private Environment env;
	private EntityStore store;
	private EnvironmentConfig environmentConfig;
	private StoreConfig storeConfig;

	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	public void setEnvironmentConfig(EnvironmentConfig environmentConfig) {
		this.environmentConfig = environmentConfig;
		this.environmentConfig.setConfigParam("je.log.fileMax", "262144000");
	}

	public void setConfig(EnvironmentConfig environmentConfig, StoreConfig storeConfig) {
		setEnvironmentConfig(environmentConfig);
		this.storeConfig = storeConfig;
	}

	@PostConstruct
	public void init() throws Exception {
		File dataDir = new File(dataDirectory);
		if (!dataDir.exists()) {
			FileUtils.forceMkdir(dataDir);
		}
		log.debug(dataDirectory);
		if (environmentConfig == null) {
			environmentConfig = new EnvironmentConfig();
			environmentConfig.setAllowCreate(true);
			environmentConfig.setTransactional(false);
		}
		env = new Environment(dataDir, environmentConfig);
		if (storeConfig == null) {
			storeConfig = new StoreConfig();
			storeConfig.setAllowCreate(true);
			storeConfig.setTransactional(false);
		}
		store = new EntityStore(env, dataDir.getName(), storeConfig);
		SequenceConfig sequenceConfig = new SequenceConfig();
		sequenceConfig.setAllowCreate(true);
		sequenceConfig.setInitialValue(1);
		store.setSequenceConfig("ID", sequenceConfig);
	}

	@PreDestroy
	public void destroy() throws Exception {
		if (store != null) {
			store.close();
		}
		if (env != null) {
			// env.cleanLog();
			env.close();
		}
	}

	@SuppressWarnings("unchecked")
	public T getValue(Long id) throws Exception {
		log.debug(String.format("Retrieving file %d from store %s", id, dataDirectory));
		T t = null;
		Class<?> entityClass = DocumentEntity.class;
		PrimaryIndex<Long, DocumentEntity<T>> primaryIndex = (PrimaryIndex<Long, DocumentEntity<T>>) store.getPrimaryIndex(id.getClass(), entityClass);
		if (primaryIndex.contains(id)) {
			DocumentEntity<T> entity = primaryIndex.get(id);
			t = entity.getValue();
		}
		log.debug(String.format("Retrieved file %d from store %s", id, dataDirectory));
		return t;
	}

	@SuppressWarnings("unchecked")
	public Long setValue(T t) throws Exception {
		log.debug(String.format("Storing file in store %s", dataDirectory));
		DocumentEntity<T> entity = new DocumentEntity<T>();
		entity.setValue(t);
		PrimaryIndex<Long, DocumentEntity<T>> primaryIndex = (PrimaryIndex<Long, DocumentEntity<T>>) store.getPrimaryIndex(Long.class, entity.getClass());
		primaryIndex.put(entity);
		log.debug(String.format("Stored file %d from store %s", entity.getKey(), dataDirectory));
		return entity.getKey();
	}

	@SuppressWarnings("unchecked")
	public Long setValue(Long id, T t) throws Exception {
		log.debug(String.format("Storing file %d from store %s", id, dataDirectory));
		DocumentEntity<T> entity = new DocumentEntity<T>();
		entity.setKey(id);
		entity.setValue(t);
		PrimaryIndex<Long, DocumentEntity<T>> primaryIndex = (PrimaryIndex<Long, DocumentEntity<T>>) store.getPrimaryIndex(Long.class, entity.getClass());
		primaryIndex.put(entity);
		log.debug(String.format("Stored file %d from store %s", entity.getKey(), dataDirectory));
		return entity.getKey();
	}

	@SuppressWarnings("unchecked")
	public void deleteValue(Long id) throws Exception {
		log.debug(String.format("Deleting file %d from store %s", id, dataDirectory));
		Class<?> entityClass = DocumentEntity.class;
		PrimaryIndex<Long, DocumentEntity<T>> primaryIndex = (PrimaryIndex<Long, DocumentEntity<T>>) store.getPrimaryIndex(id.getClass(), entityClass);
		if (primaryIndex.contains(id)) {
			primaryIndex.delete(id);
		}
		log.debug(String.format("Deleted file %d from store %s", id, dataDirectory));
	}

	@SuppressWarnings("unchecked")
	public EntityCursor<DocumentEntity<T>> getEntities() {
		Class<?> entityClass = DocumentEntity.class;
		PrimaryIndex<Long, DocumentEntity<T>> primaryIndex = (PrimaryIndex<Long, DocumentEntity<T>>) store.getPrimaryIndex(Long.class, entityClass);
		return primaryIndex.entities();
	}
}