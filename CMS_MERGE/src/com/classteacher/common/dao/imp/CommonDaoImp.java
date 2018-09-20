package com.classteacher.common.dao.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.classteacher.common.dao.CommonDao;
import com.classteacher.common.dao.berkeley.DocumentEntityDAO;
import com.classteacher.common.dao.berkeley.DocumentEntityDaoSchool;
import com.classteacher.common.model.CmsVersion;
import com.classteacher.common.model.Module;
import com.classteacher.common.model.SubjectTopic;
import com.classteacher.common.model.SubjectTopicModule;

@Repository
public class CommonDaoImp implements CommonDao {

	@Autowired
	@Qualifier(value = "sessionFactorySchool")
	private SessionFactory sessionFactorySch;

	@Autowired
	@Qualifier(value = "sessionFactoryAdmin")
	private SessionFactory sessionFactoryAdmin;

	@Autowired
	private DocumentEntityDAO<byte[]> appStore;

	@Autowired
	private DocumentEntityDaoSchool<byte[]> appStore1;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Map<Integer, Integer> moduleMap = null;
	Map<Integer, Integer> topicMap = null;
	Map<Integer, Integer> subjectMap = null;
	Map<Integer, String> moduleMapWithCon = null;

	@Override
	@Transactional
	public List<CmsVersion> getAllCmsVersion() throws ParseException {
		List<CmsVersion> cmsLst = new ArrayList<CmsVersion>();
		Session sessionAdmin = sessionFactoryAdmin.openSession();
		// query for get all Cms version from Admin table
		Query query = sessionAdmin.createSQLQuery("select * from ct_version");
		List<Object[]> lst = query.list();
		for (Object[] o : lst) {
			CmsVersion cv = new CmsVersion();
			cv.setVersionName(o[1].toString());
			cv.setEndDate(sdf.parse(o[4].toString()));
			cv.setId(Integer.parseInt(o[0].toString()));
			cmsLst.add(cv);

		}

		return cmsLst;
	}

	@Override
	public Map<Integer, String> mergeCmsData(String endDate) throws ParseException {
		moduleMap = new LinkedHashMap<Integer, Integer>();
		topicMap = new HashMap<Integer, Integer>();
		subjectMap = new HashMap<Integer, Integer>();
		moduleMapWithCon=new HashMap<Integer,String>();
		Session sessionAdmin = sessionFactoryAdmin.openSession();
		Query query = sessionAdmin.createSQLQuery(
				"select cm.*, cstm.topic_id,cst.topic_name,cs.subject_id,cs.subject_name,cst.topic_level from ct_module cm "
						+ "inner join ct_class_subject_topic_module cstm on" + " "
						+ "cstm.module_Id=cm.module_Id and cstm.is_active=:status" + " "
						+ "inner join ct_class_subject_topic cst on cstm.topic_id=cst.topic_id" + " "
						+ "inner join ct_subject cs on cs.subject_id=cst.subject_id" + " "
						+ "where cm.is_active=1 and cm.created_date >:date group by cm.module_id");
		query.setParameter("status", 1);
		query.setParameter("date", sdf.parse(endDate));
		List<Object[]> moduleLst = query.list();

		for (Object[] row : moduleLst) {
			Module module = new Module();
			module.setModule_id(Integer.parseInt(row[0].toString()));
			module.setCreated_date(sdf.parse(row[1].toString()));
			module.setLast_modified_date(sdf.parse(row[1].toString()));

			module.setModule_name(row[3].toString());
			if (row[4].toString().equals(null)) {
				module.setModule_descr(null);
			} else {
				module.setModule_descr(row[4].toString());
			}
			module.setResource_type_id(Integer.parseInt(row[5].toString()));
			module.setTags(row[6] == null ? null : row[6].toString());
			module.setUser_id(Integer.parseInt(row[7].toString()));
			module.setBoard(row[8] == null ? null : row[8].toString());
			module.setIs_active(row[9].toString());
			module.setContent_url(row[10].toString());
			module.setThumbnail_url(row[11].toString());

			if (row[12] != null && row[12] != "") {
				module.setContent_id(Integer.parseInt(row[12].toString()));
			}
			if (row[13] != null && row[13] != "") {
				module.setThumbnail_id(Integer.parseInt(row[13].toString()));
			}
			if (row[14] != null && row[14] != "") {
				module.setResource_grade_id(Integer.parseInt(row[14].toString()));
			}
			if (row[15] != null && row[15] != "") {
				module.setResource_animation_id(Integer.parseInt(row[15].toString()));
			}

			if (row[16] != null && row[16] != "") {
				module.setResource_platform_id(Integer.parseInt(row[16].toString()));
			}

			module.setTopicId(Integer.parseInt(row[17].toString()));
			module.setTopicName(row[18].toString());
			module.setSubjectId(Integer.parseInt(row[19].toString()));
			module.setSubjectName(row[20].toString());
			if (row[21] != null && row[21] != "") {
				module.setTopicLevelId(Integer.parseInt(row[21].toString()));
			}

			moduleMapWithCon = saveModuleAndMapping(sessionAdmin, module);
			module = null;
		}

		sessionAdmin.close();
		return moduleMapWithCon;
	}

	private synchronized Map<Integer, String> saveModuleAndMapping(Session sessionAdmin, Module module)
			throws ParseException {
		// save module details in school table
		Query query = sessionFactorySch.getCurrentSession().createSQLQuery(
				"INSERT INTO ct_module(created_date,last_modified_date,module_name,module_descr,resource_type_id,tags,user_id,Board,"
						+ "is_active,content_url,thumbnail_url,content_id,thumbnail_id,resource_grade_id,"
						+ "resource_animation_id,resource_platform_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		query.setParameter(0, module.getCreated_date());
		query.setParameter(1, module.getLast_modified_date());
		query.setParameter(2, module.getModule_name());
		query.setParameter(3, module.getModule_descr());
		query.setParameter(4, module.getResource_type_id());
		query.setParameter(5, module.getTags());
		query.setParameter(6, module.getUser_id());
		query.setParameter(7, module.getBoard());
		query.setParameter(8, module.STATUS_ACTIVE);
		query.setParameter(9, module.getContent_url());
		query.setParameter(10, module.getThumbnail_url());
		query.setParameter(11, module.getContent_id());
		query.setParameter(12, module.getThumbnail_id());
		query.setParameter(13, module.getResource_grade_id());
		query.setParameter(14, module.getResource_animation_id());
		query.setParameter(15, module.getResource_platform_id());
		query.executeUpdate();
		Query query1 = sessionFactorySch.getCurrentSession().createSQLQuery("select max(module_id) from ct_module");
		Integer newModuleId = (Integer) query1.uniqueResult();
		moduleMap.put(module.getModule_id(), newModuleId);
		
		mapModuleWithTopic(sessionAdmin, module);
		
		
		if(module.getContent_url().contains(".mp4"))
				/*if(module.getContent_url().substring(module.getContent_url().lastIndexOf(".")+1,module.getContent_url().length()).equals("mp4")
				||module.getContent_url().substring(module.getContent_url().lastIndexOf(".")+1,module.getContent_url().length()).equals("mp4"))*/
			
		{
			
			moduleMapWithCon.put(module.getModule_id(), newModuleId.toString()+"@1");	
		
		}
		else
		{
			moduleMapWithCon.put(module.getModule_id(), newModuleId.toString()+"@0");
		}
		
		// code for save berkeley db from admin to school

		// code for map module with topic and chapter and subject
		
		return moduleMapWithCon;

	}

	public void mapModuleWithTopic(Session sessionAdmin, Module module) throws ParseException {
		Integer newModuleId = moduleMap.get(module.getModule_id());
		Integer newTopicId = topicMap.get(module.getTopicId());
		Integer newSubjectId = subjectMap.get(module.getSubjectId());
		if (newTopicId != null && newTopicId > 0) {
			addTopicModule(newTopicId, newModuleId, module.getTopicId(), module.getModule_id(), sessionAdmin);
		} else {
			newTopicId = checkTopicExits(module.getTopicId(), module.getTopicName());
			if (newTopicId != null) {
				topicMap.put(module.getTopicId(), newTopicId);
				// addTopicModule(newTopicId, newModuleId, module.getTopicId(),
				// module.getModule_id(),sessionAdmin);
			} else {
				if (newSubjectId == null) {

					newSubjectId = checkForSubject(module.getSubjectId(), module.getSubjectName(), sessionAdmin);
					subjectMap.put(module.getSubjectId(), newSubjectId);
				}
				if (module.getTopicLevelId() != null && module.getTopicLevelId().equals(0)) {

					newTopicId = saveTopic(module.getTopicId(), -1, newSubjectId, sessionAdmin);
					topicMap.put(module.getTopicId(), newTopicId);
					// addTopicModule(newTopicId, newModuleId,
					// module.getTopicId(), module.getModule_id(),sessionAdmin);
				} else {
					String str1 = "select cst1.topic_level as level2,cst1.topic_name as topicName2,cst1.topic_id as topic2,"
							+ " "
							+ "cst2.topic_level as level3,cst2.topic_name as topicName3 ,cst2.topic_id from ct_class_subject_topic cst"
							+ " " + "left join ct_class_subject_topic cst1 on cst.parent_topic_id=cst1.topic_id" + " "
							+ "left join ct_class_subject_topic cst2 on cst1.parent_topic_id=cst2.topic_id" + " "
							+ "where cst.topic_id=:topicId";
					Query query = sessionAdmin.createSQLQuery(str1);
					query.setMaxResults(1);
					query.setParameter("topicId", module.getTopicId());
					Object[] topicHierarchy = (Object[]) query.uniqueResult();
					if (topicHierarchy[2] != null) {
						Integer newP1TopicId = topicMap.get(Integer.parseInt(topicHierarchy[2].toString()));
						if (newP1TopicId != null) {
							newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId, sessionAdmin);
							topicMap.put(module.getTopicId(), newTopicId);
						} else if (topicHierarchy[5] != null) {

							Integer newP2TopicId = topicMap.get(Integer.parseInt(topicHierarchy[5].toString()));
							if (newP2TopicId != null) {
								topicMap.put(Integer.parseInt(topicHierarchy[5].toString()), newP2TopicId);
								newP1TopicId = checkTopicExits(Integer.parseInt(topicHierarchy[2].toString()),
										topicHierarchy[1].toString());
								if (newP1TopicId != null) {
									topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
									newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId,
											sessionAdmin);
									topicMap.put(module.getTopicId(), newTopicId);
								} else {
									newP1TopicId = saveTopic(Integer.parseInt(topicHierarchy[2].toString()),
											newP2TopicId, newSubjectId, sessionAdmin);
									topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
									newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId,
											sessionAdmin);
									topicMap.put(module.getTopicId(), newTopicId);
								}
							} else {
								newP2TopicId = checkTopicExits(Integer.parseInt(topicHierarchy[5].toString()),
										topicHierarchy[4].toString());
								if (newP2TopicId != null) {
									topicMap.put(Integer.parseInt(topicHierarchy[5].toString()), newP2TopicId);
									newP1TopicId = topicMap.get(Integer.parseInt(topicHierarchy[2].toString()));
									if (newP1TopicId != null) {
										newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId,
												sessionAdmin);
										topicMap.put(module.getTopicId(), newTopicId);
									} else {
										newP1TopicId = checkTopicExits(Integer.parseInt(topicHierarchy[2].toString()),
												topicHierarchy[1].toString());
										if (newP1TopicId != null) {
											topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
											newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId,
													sessionAdmin);
											topicMap.put(module.getTopicId(), newTopicId);
										} else {
											newP1TopicId = saveTopic(Integer.parseInt(topicHierarchy[2].toString()),
													newP2TopicId, newSubjectId, sessionAdmin);
											topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
											newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId,
													sessionAdmin);
											topicMap.put(module.getTopicId(), newTopicId);
										}
									}

								} else {
									newP2TopicId = saveTopic(Integer.parseInt(topicHierarchy[5].toString()), -1,
											newSubjectId, sessionAdmin);
									topicMap.put(Integer.parseInt(topicHierarchy[5].toString()), newP2TopicId);
									newP1TopicId = saveTopic(Integer.parseInt(topicHierarchy[2].toString()),
											newP2TopicId, newSubjectId, sessionAdmin);
									topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
									newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId,
											sessionAdmin);
									topicMap.put(module.getTopicId(), newTopicId);

								}
							}

						} else {
							newP1TopicId = checkTopicExits(Integer.parseInt(topicHierarchy[2].toString()),
									topicHierarchy[1].toString());
							if (newP1TopicId != null) {
								topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
								newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId, sessionAdmin);
								topicMap.put(module.getTopicId(), newTopicId);
							} else {
								newP1TopicId = saveTopic(Integer.parseInt(topicHierarchy[2].toString()), -1,
										newSubjectId, sessionAdmin);
								topicMap.put(Integer.parseInt(topicHierarchy[2].toString()), newP1TopicId);
								newTopicId = saveTopic(module.getTopicId(), newP1TopicId, newSubjectId, sessionAdmin);
								topicMap.put(module.getTopicId(), newTopicId);
							}
						}

					} else {
						newTopicId = saveTopic(module.getTopicId(), -1, newSubjectId, sessionAdmin);

						if (newTopicId.equals(null)) {

							System.out.println("hidasfasdfasdfasdfasdfasdf");

						}
						topicMap.put(module.getTopicId(), newTopicId);
					}

				}
			}
			Integer i = module.getTopicId();
			if (i.equals(61547)) {
				Integer j = 0;
				Integer h = j;
			}

			addTopicModule(newTopicId, newModuleId, module.getTopicId(), module.getModule_id(), sessionAdmin);
		}

	}

	// code for add new topic module mapping
	public void addTopicModule(int newTopicId, int newModuleId, int oldTopicId, int oldModuleId, Session sessionAdmin)
			throws ParseException {

		SubjectTopicModule sTopicModule = new SubjectTopicModule();
		String getDate = "SELECT * from ct_class_subject_topic_module where module_id=:moduleId AND topic_id=:topicId";
		Query getquery = sessionAdmin.createSQLQuery(getDate);
		getquery.setParameter("topicId", oldTopicId);
		getquery.setParameter("moduleId", oldModuleId);
		getquery.setMaxResults(1);
		Object[] getrows = (Object[]) getquery.uniqueResult();
		if (getrows[4] != null && getrows[4] != "") {
			sTopicModule.setUrl_in_cms(getrows[4].toString());
		}
		if (getrows[5] != null && getrows[5] != "") {
			sTopicModule.setContent_type(getrows[5].toString());
		}

		if (getrows[6] != null && getrows[6] != "") {
			sTopicModule.setModule_grading(getrows[6].toString());
		}
		if (getrows[7] != null && getrows[7] != "") {
			sTopicModule.setDescription(getrows[7].toString());
		}
		if (getrows[8] != null && getrows[8] != "") {
			sTopicModule.setUser_id(Integer.parseInt(getrows[8].toString()));
		}

		if (getrows[9] != null && getrows[9] != "") {
			sTopicModule.setCreated_date(sdf.parse(getrows[9].toString()));
		}

		if (getrows[10] != null && getrows[10] != "") {
			sTopicModule.setLast_updated_date(sdf.parse(getrows[10].toString()));
		}

		String strNotEmpty = "insert into ct_class_subject_topic_module(module_id,topic_id,url_in_cms,content_type,module_grading,description,user_id,created_date,last_updated_date,is_active) values(?,?,?,?,?,?,?,?,?,?)";
		Query qInsTopic = sessionFactorySch.getCurrentSession().createSQLQuery(strNotEmpty);
		qInsTopic.setParameter(0, newModuleId);
		qInsTopic.setParameter(1, newTopicId);
		qInsTopic.setParameter(2, sTopicModule.getUrl_in_cms());
		qInsTopic.setParameter(3, sTopicModule.getContent_type());
		qInsTopic.setParameter(4, sTopicModule.getModule_grading());
		qInsTopic.setParameter(5, sTopicModule.getDescription());
		qInsTopic.setParameter(6, sTopicModule.getUser_id());
		qInsTopic.setParameter(7, sTopicModule.getCreated_date());
		qInsTopic.setParameter(8, sTopicModule.getLast_updated_date());
		qInsTopic.setParameter(9, sTopicModule.STATUS_ACTIVE);
		qInsTopic.executeUpdate();
		

	}

	// code for check subject or create subject and return new subjectid
	public Integer checkForSubject(int oldSubjectId, String oldSubjectName, Session sessionAdmin) {

		System.out.println("check for subject " + oldSubjectId);
		// check subject already exists or not in school table
		Query querySubSch = sessionFactorySch.getCurrentSession()
				.createSQLQuery("Select subject_id,subject_name from ct_subject "
						+ " where subject_id=:subject_id and subject_name=:subject_name");
		querySubSch.setParameter("subject_id", oldSubjectId);
		querySubSch.setParameter("subject_name", oldSubjectName);
		querySubSch.setMaxResults(1);
		Object[] subId = (Object[]) querySubSch.uniqueResult();
		if (subId != null) {

			if (oldSubjectId == 18) {

				System.out.println("hi");

			}
			System.out.println("check for subject new " + subId[0].toString());
			System.out.println("check for subject " + oldSubjectId);

			String query = "select * from ct_board_class_subject where subject_id=:subjectId";

			Query q = sessionAdmin.createSQLQuery(query);
			q.setParameter("subjectId", subId[0].toString());
			List<Object[]> obj = q.list();
			if (!obj.isEmpty()) {
				for (Object[] o : obj) {
					Integer boardClassId = Integer.parseInt(o[2].toString());

					Query qe1 = sessionFactorySch.getCurrentSession().createSQLQuery(
							"select * from ct_board_class_subject where subject_id=:subjectId and board_class_id=:boardClassId");
					qe1.setParameter("subjectId", subId[0].toString());
					qe1.setParameter("boardClassId", boardClassId);
					Object[] resBcs = (Object[]) qe1.uniqueResult();

					if (resBcs != null) {

						Query qs = sessionFactorySch.getCurrentSession().createSQLQuery(
								"select * from ct_school_section_subject where subject_id=:subjectId and board_class_id=:boardClassId");
						qs.setParameter("subjectId", subId[0].toString());
						qs.setParameter("boardClassId", boardClassId);
						Object[] resBsss = (Object[]) qe1.uniqueResult();

						if (resBsss != null) {
							return Integer.parseInt(subId[0].toString());
						} else {
							Query qe3 = sessionFactorySch.getCurrentSession().createSQLQuery(
									"insert into ct_school_section_subject(section_id, board_class_id, subject_id) values ((select distinct a.section_id from ct_school_section a join ct_board_class_subject b on a.board_class_id=b.board_class_id and a.board_class_id=?), ?, ?) ");
							qe3.setParameter(0, boardClassId);
							qe3.setParameter(1, boardClassId);
							qe3.setParameter(2, subId[0].toString());

							System.out.println("new subject Id " + subId[0].toString());
							qe3.executeUpdate();
						}

					} else {
						Query qe = sessionFactorySch.getCurrentSession().createSQLQuery(
								"insert into ct_board_class_subject(is_elective,parent_subject_id,board_class_id,subject_id) values(?,?,?,?)");
						qe.setParameter(0, 1);
						qe.setParameter(1, 1);
						qe.setParameter(2, boardClassId);
						qe.setParameter(3, subId[0].toString());
						qe.executeUpdate();

						Query qe3 = sessionFactorySch.getCurrentSession().createSQLQuery(
								"insert into ct_school_section_subject(section_id, board_class_id, subject_id) values ((select distinct a.section_id from ct_school_section a join ct_board_class_subject b on a.board_class_id=b.board_class_id and a.board_class_id=?), ?, ?) ");
						qe3.setParameter(0, boardClassId);
						qe3.setParameter(1, boardClassId);
						qe3.setParameter(2, subId[0].toString());

						System.out.println("new subject Id " + subId[0].toString());
						qe3.executeUpdate();
					}
					/*
					 * Query qe =
					 * sessionFactorySch.getCurrentSession().createSQLQuery(
					 * "insert into ct_board_class_subject(is_elective,parent_subject_id,board_class_id,subject_id) values(?,?,?,?)"
					 * ); qe.setParameter(0, 1); qe.setParameter(1, 1);
					 * qe.setParameter(2, boardClassId); qe.setParameter(3,
					 * subId[0].toString()); qe.executeUpdate();
					 * 
					 * Query qe4 =
					 * sessionFactorySch.getCurrentSession().createSQLQuery(
					 * "insert into ct_school_section_subject(section_id, board_class_id, subject_id) values ((select distinct a.section_id from ct_school_section a join ct_board_class_subject b on a.board_class_id=b.board_class_id and a.board_class_id=?), ?, ?) "
					 * ); qe4.setParameter(0, boardClassId); qe4.setParameter(1,
					 * boardClassId); qe4.setParameter(2, subId[0].toString());
					 * 
					 * System.out.println("new subject Id "
					 * +subId[0].toString()); qe4.executeUpdate();
					 */
				}

			}
			return Integer.parseInt(subId[0].toString());
		}

		else {

			// insert new subject in school table
			String insertSub = "insert into ct_subject(subject_name) into values(?)";
			Query insertQuerySub = sessionFactorySch.getCurrentSession().createSQLQuery(insertSub);
			insertQuerySub.setParameter(0, oldSubjectName); // subject
			insertQuerySub.executeUpdate();
			String getSubId = "select max(subject_id) from ct_subject";
			Query getSubIdQ = sessionFactorySch.getCurrentSession().createSQLQuery(getSubId);
			Integer subid = Integer.parseInt(getSubIdQ.uniqueResult().toString());

			// map new subject with classes
			String query = "select * from ct_board_class_subject where subject_id=:subjectId";

			Query q = sessionAdmin.createSQLQuery(query);
			q.setParameter("subjectId", oldSubjectId);
			List<Object[]> obj = q.list();
			if (!obj.isEmpty()) {
				for (Object[] o : obj) {
					Integer boardClassId = Integer.parseInt(o[2].toString());
					Query qe = sessionFactorySch.getCurrentSession().createSQLQuery(
							"insert into ct_board_class_subject(is_elective,parent_subject_id,board_class_id,subject_id) values(?,?,?,?)");
					qe.setParameter(0, 1);
					qe.setParameter(1, 1);
					qe.setParameter(2, boardClassId);
					qe.setParameter(3, subid);
					qe.executeUpdate();

					Query qe1 = sessionFactorySch.getCurrentSession().createSQLQuery(
							"insert into ct_school_section_subject(section_id, board_class_id, subject_id) values ((select distinct a.section_id from ct_school_section a join ct_board_class_subject b on a.board_class_id=b.board_class_id and a.board_class_id=?), ?, ?) ");
					qe1.setParameter(0, boardClassId);
					qe1.setParameter(1, boardClassId);
					qe1.setParameter(2, subid);

					System.out.println("new subject Id " + subid);
					qe1.executeUpdate();
				}
			}
			return subid;
		}

	}

	public Integer saveTopic(int oldTopicId, int pTopicId, int subjectId, Session sessionAdmin) {

		SubjectTopic sTopic = new SubjectTopic();
		int newTopicId = 0;
		Query topicQuery = sessionAdmin
				.createSQLQuery("select * from ct_class_subject_topic cst where cst.topic_id=:topic_id");
		topicQuery.setParameter("topic_id", oldTopicId);
		topicQuery.setMaxResults(1);
		Object[] rows = (Object[]) topicQuery.uniqueResult();
		sTopic.setTopic_id(Integer.parseInt(rows[0].toString()));
		if (rows[1] != null && rows[1] != "") {
			sTopic.setDescription(rows[1].toString());
		}
		sTopic.setParent_topic_id(Integer.parseInt(rows[3].toString()));

		if (rows[4] != null && rows[4] != "") {
			sTopic.setTopic_name(rows[4].toString());
		}
		if (rows[5] != null && rows[5] != "") {
			sTopic.setBoard_class_id(Integer.parseInt(rows[5].toString()));
		}
		if (rows[6] != null && rows[6] != "") {
			sTopic.setTopic_level(Integer.parseInt(rows[6].toString()));
		}
		if (rows[7] != null && rows[7] != "") {
			sTopic.setModule_name(rows[7].toString());
		}
		if (rows[8] != null && rows[8] != "") {
			sTopic.setModule_description(rows[8].toString());
		}
		if (rows[9] != null && rows[9] != "") {
			sTopic.setUrl_in_cms(rows[9].toString());
		}
		if (rows[10] != null && rows[10] != "") {
			sTopic.setContent_type(rows[10].toString());
		}
		if (rows[11] != null && rows[11] != "") {
			sTopic.setModule_grading(rows[11].toString());
		}
		if (rows[12] != null && rows[12] != "") {
			sTopic.setSubject_id(Integer.parseInt(rows[12].toString()));
		}
		if (rows[13] != null && rows[13] != "") {
			sTopic.setUser_id(Integer.parseInt(rows[13].toString()));
		}
		if (rows[14] != null && rows[14] != "") {
			sTopic.setActive(Boolean.parseBoolean(rows[2].toString()));
		}
		// insert new topic

		String insertNewTopic = "insert into ct_class_subject_topic(description,topic_name,board_class_id,topic_level,module_name,"
				+ "module_description,url_in_cms,content_type,module_grading,subject_id,user_id,isActive,parent_topic_id)"
				+ " " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		Query qInsTopic = sessionFactorySch.getCurrentSession().createSQLQuery(insertNewTopic);
		qInsTopic.setParameter(0, sTopic.getDescription());
		qInsTopic.setParameter(1, sTopic.getTopic_name());
		qInsTopic.setParameter(2, sTopic.getBoard_class_id());
		qInsTopic.setParameter(3, sTopic.getTopic_level());
		qInsTopic.setParameter(4, sTopic.getModule_name());
		qInsTopic.setParameter(5, sTopic.getModule_description());
		qInsTopic.setParameter(6, sTopic.getUrl_in_cms());
		qInsTopic.setParameter(7, sTopic.getContent_type());
		qInsTopic.setParameter(8, sTopic.getModule_grading());
		// qInsTopic.setParameter(9, sTopic.getSubject_id());
		qInsTopic.setParameter(9, subjectId);
		qInsTopic.setParameter(10, sTopic.getUser_id());
		qInsTopic.setParameter(11, sTopic.isActive());
		qInsTopic.setParameter(12, pTopicId);

		System.out.println("subject Id" + subjectId + "board class Id" + sTopic.getBoard_class_id() + "Topic id"
				+ sTopic.getTopic_id());

		qInsTopic.executeUpdate();

		Query query4 = sessionFactorySch.getCurrentSession()
				.createSQLQuery("select max(topic_id) from ct_class_subject_topic ");
		newTopicId = Integer.parseInt(query4.uniqueResult().toString());

		return newTopicId;

	}

	// checking topic is exits or not
	public Integer checkTopicExits(int oldTopicId, String oldTopicName) {
		Query query1 = sessionFactorySch.getCurrentSession().createSQLQuery(
				"select cst.topic_id from ct_class_subject_topic cst where cst.topic_id=:topic_id and cst.topic_name=:topic_name");
		query1.setParameter("topic_id", oldTopicId);
		query1.setParameter("topic_name", oldTopicName);
		query1.setMaxResults(1);
		Object topic = query1.uniqueResult();
		if (topic != null) {
			return (Integer) topic;
		}

		return null;

	}

	@Override
	public Boolean removeModules(String endDate) throws ParseException {
		String sql = "select cstm.topic_module_id from ct_module cm "
				+ "inner join ct_class_subject_topic_module cstm on" + " "
				+ "cstm.module_Id=cm.module_Id and cstm.is_active=:status and cm.created_date <=:date";
		Session sessionAdmin = sessionFactoryAdmin.openSession();
		Query query = sessionAdmin.createSQLQuery(sql);
		query.setParameter("status", 0);
		query.setParameter("date", sdf.parse(endDate));
		List<Integer> toppicModuleId = query.list();

		String updateQuery = "update ct_class_subject_topic_module set is_active=:status where topic_module_id in (:toppicModuleId)";
		sessionFactorySch.getCurrentSession().createSQLQuery(updateQuery).setParameter("status", 0)
				.setParameterList("toppicModuleId", toppicModuleId).executeUpdate();

		Query q = sessionAdmin.createSQLQuery("select module_id from ct_user_module where is_active=:status");
		q.setParameter("status", 0);
		List<Integer> lst = q.list();

		sessionFactorySch.getCurrentSession()
				.createSQLQuery("update ct_user_module set is_active=:status where module_id in (:moduleId)")
				.setParameter("status", 0).setParameterList("moduleId", lst).executeUpdate();

		sessionAdmin.close();

		return true;

	}

	@Override
	public String uploadModule(Map<Integer, Integer> moduleIds, List<Integer> moduleIdsList) {

		// code for save berkeley db from admin to school

		Query query2 = sessionFactoryAdmin.openSession().createSQLQuery(
				"select cmf.file_id,cmf.contentURL,cmf.module_id,cmf.filepath from ct_module_files cmf where cmf.module_id in (:moduleList)");
		query2.setParameterList("moduleList", moduleIdsList);
		List<Object[]> list = query2.list();
		// byte[] value=null;
		Long key = null;
		for (Object[] row : list) {
			System.out.println(row[2].toString());
			/*Query query = sessionFactorySch.getCurrentSession()
					.createSQLQuery("select cmf.module_id from ct_module_files cmf where cmf.module_id=:newModuleId");
			query.setParameter("newModuleId", moduleIds.get(Integer.parseInt(row[2].toString())));
			query.setMaxResults(1);
			Object modululeId = query.uniqueResult();
			if (modululeId != null) {
				continue;
			} else {*/

				try {
					// value =
					// appStore.getValue(Long.parseLong(row[0].toString()));
					key = appStore1.setValue(appStore.getValue(Long.parseLong(row[0].toString())));
					// value = null;

				} catch (Exception e) {
					System.out.println("ji.....................//////////////////////////////////");
					e.printStackTrace();
				}

				Query query3 = sessionFactorySch.getCurrentSession().createSQLQuery("INSERT INTO ct_module_files(module_id,filepath,contentURL,file_id) values(?,?,?,?)");

				query3.setParameter(0, moduleIds.get(Integer.parseInt(row[2].toString())));

				query3.setParameter(1, row[1].toString());
				query3.setParameter(2, row[3].toString());
				query3.setParameter(3, key);
				query3.executeUpdate();
				key = null;
				
				System.out.println("naaaaaaaaaaaaaaaaaaaaaa,.......................////");
				System.gc();

		}

		/*
		 * for(Map.Entry<Integer, Integer> moduleMap:moduleIds.entrySet()){
		 * 
		 * Query query2 = sessionFactoryAdmin.openSession().createSQLQuery(
		 * "select cmf.file_id,cmf.contentURL from ct_module_files cmf where cmf.module_id=:module_id"
		 * ); query2.setParameter("module_id", moduleMap.getKey());
		 * List<Object[]> list = query2.list(); for (Object[] row : list) { Long
		 * key = null; try { byte[] value =
		 * appStore.getValue(Long.parseLong(row[0].toString())); key =
		 * appStore1.setValue(value); value = null; } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * String str3 =
		 * "INSERT INTO ct_module_files(module_id,filepath,contentURL,file_id) values(?,?,?,?)"
		 * ; Query query3 =
		 * sessionFactorySch.getCurrentSession().createSQLQuery(str3);
		 * query3.setParameter(0, moduleMap.getValue()); query3.setParameter(1,
		 * row[1].toString()); query3.setParameter(2, row[1].toString());
		 * query3.setParameter(3, key); query3.executeUpdate(); }
		 * 
		 * 
		 * }
		 */
		return "success";
	}

}
