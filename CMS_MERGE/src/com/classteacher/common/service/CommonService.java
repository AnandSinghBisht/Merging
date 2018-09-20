package com.classteacher.common.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.classteacher.common.model.CmsVersion;

public interface CommonService {



	List<CmsVersion> getAllCmsVersion() throws ParseException;

	Map<Integer,String> mergeCmsData(String endDate) throws ParseException;

	Boolean removeModules(String endDate) throws ParseException;
	
	String uploadModule(File excelFile)throws IOException;

}
