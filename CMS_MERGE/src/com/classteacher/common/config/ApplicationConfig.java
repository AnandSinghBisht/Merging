package com.classteacher.common.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.classteacher.common.dao.berkeley.DocumentEntityDAO;
import com.classteacher.common.dao.berkeley.DocumentEntityDaoSchool;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.classteacher.common")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class ApplicationConfig extends WebMvcConfigurerAdapter {
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_URL_Admin = "db.url.admin";
	private static final String PROPERTY_NAME_DATABASE_URL_school = "db.url.school";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDLAUTO = "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_USE_UNICODE = "hibernate.connection.useUnicode";
	private static final String PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
	private static final String PROPERTY_NAME_HIBERNATE_CHARSET = "hibernate.connection.charSet";
	/*
	 * private static final String PROPERTY_NAME_CURRENT_SESSION_COTEXT_CLASS=
	 * "current_session_context_class";
	 */
	/*
	 * private static final String TRANSACTION_FACTORY_CLASS=
	 * "org.hibernate.transaction.JTATransactionFactory";
	 */

	@Resource
	private Environment env;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactorySchool() {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(this.restDataSource());
		sessionFactory.setPackagesToScan(
				new String[] { env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN) });
		sessionFactory.setHibernateProperties(this.hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryAdmin() {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(this.restDataSourceOld());
		sessionFactory.setPackagesToScan(
				new String[] { env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN) });
		sessionFactory.setHibernateProperties(this.hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource restDataSourceOld() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL_Admin));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}

	@Bean
	public DataSource restDataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL_school));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}

	final Properties hibernateProperties() {
		return new Properties() {
			private static final long serialVersionUID = 1L;
			{
				this.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
				this.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
				this.put(PROPERTY_NAME_HIBERNATE_HBM2DDLAUTO,
						env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDLAUTO));
				this.put(PROPERTY_NAME_HIBERNATE_USE_UNICODE,
						env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_USE_UNICODE));
				this.put(PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING,
						env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_CHARACTER_ENCODING));
				this.put(PROPERTY_NAME_HIBERNATE_CHARSET, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_CHARSET));
				/*
				 * this.put(PROPERTY_NAME_CURRENT_SESSION_COTEXT_CLASS,
				 * env.getRequiredProperty(
				 * PROPERTY_NAME_CURRENT_SESSION_COTEXT_CLASS));
				 * this.put("transaction.factory_class",
				 * TRANSACTION_FACTORY_CLASS);
				 */

			}
		};
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer() {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(restDataSource());
		return initializer;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(this.sessionFactorySchool().getObject());
		return txManager;
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(150000000);
		return multipartResolver;
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public DocumentEntityDAO documentEntityDAO() throws Exception {

		DocumentEntityDAO dea = new DocumentEntityDAO();
		dea.setDataDirectory(env.getRequiredProperty("document.store.apps"));
		//dea.init();
		return dea;

	}

	@SuppressWarnings("rawtypes")
	@Bean
	public DocumentEntityDaoSchool documentEntityDAOSchool() throws Exception {

		DocumentEntityDaoSchool dea = new DocumentEntityDaoSchool();
		dea.setDataDirectory(env.getRequiredProperty("document.store.apps1"));
		//dea.init();
		return dea;

	}

}