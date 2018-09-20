package com.classteacher.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.classteacher.common.dao.CommonDao;
import com.classteacher.common.model.CmsVersion;
import com.classteacher.common.service.CommonService;

@Component
@Service
public class ComonServiceImpl implements CommonService {


	@Autowired
	private CommonDao commonDao;

	@Override
	@Transactional
	public List<CmsVersion> getAllCmsVersion() throws ParseException{
		return commonDao.getAllCmsVersion();
	}

	@Override
	@Transactional
	public Map<Integer,String> mergeCmsData(String endDate)throws ParseException {
		return commonDao.mergeCmsData(endDate);
	}

	@Override
	@Transactional
	public Boolean removeModules(String endDate) throws ParseException{
		return commonDao.removeModules(endDate);
	}

	@Override
	@Transactional
	public String uploadModule(File excelFile) throws IOException {
		String msg="";
		ByteArrayInputStream bis=null;
		try{
			
			Map<Integer,Integer> modulesId=new HashMap<Integer,Integer>();
			List<Integer> modulesIdList=new ArrayList<Integer>();
			
		bis = new ByteArrayInputStream(FileUtils.readFileToByteArray(excelFile));
		
		XSSFWorkbook workbook = new XSSFWorkbook(bis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		if (rowIterator.hasNext())
		{
			rowIterator.next();
		}
		
		while (rowIterator.hasNext())
		{

			Row row = rowIterator.next();

			Cell cellOldModul = row.getCell(0);
			Cell cellNewModul = row.getCell(1);
			String question = cellOldModul != null ? handleCell(cellOldModul, cellOldModul.getCellType()) : "";
			if(question.equals(""))
			{
					break;
			}
			modulesId.put(Integer.parseInt(cellOldModul.toString()), Integer.parseInt(cellNewModul.toString()));
			modulesIdList.add(Integer.parseInt(cellOldModul.toString()));
			
		}
		
		msg=commonDao.uploadModule(modulesId,modulesIdList);
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			bis.close();
		}
		
		return msg;
	}



	private String handleCell(Cell cell, int cellType)
	{

		if (HSSFCell.CELL_TYPE_NUMERIC == cellType)
		{
			DataFormatter formate = new DataFormatter();
			return formate.formatCellValue(cell);
		}
		else if (HSSFCell.CELL_TYPE_STRING == cellType)
		{
			return cell.getStringCellValue();
		}
		else if (HSSFCell.CELL_TYPE_BOOLEAN == cellType)
			return Boolean.toString(cell.getBooleanCellValue()).toUpperCase();

		else if (HSSFCell.CELL_TYPE_BLANK == cellType)
			return "";
		else if (HSSFCell.CELL_TYPE_FORMULA == cellType)

			return handleCell(cell, cell.getCachedFormulaResultType());
		else
			return "";
	}
}
