package com.classteacher.common.dao;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.classteacher.common.model.CmsVersion;

public interface CommonDao {

	public List<CmsVersion> getAllCmsVersion() throws ParseException;

	public Map<Integer,String> mergeCmsData(String endDate) throws ParseException;

	public Boolean removeModules(String endDate) throws ParseException;

	public String uploadModule(Map<Integer, Integer> modulesId, List<Integer> modulesIdList);
	

}
