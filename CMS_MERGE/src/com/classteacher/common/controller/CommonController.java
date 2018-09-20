package com.classteacher.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.classteacher.common.model.CmsVersion;
import com.classteacher.common.service.CommonService;

@Controller
@PropertySource("classpath:application.properties")
public class CommonController {

	@Autowired
	private CommonService commonService;

	@Resource
	private Environment env;

	
	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		// MergeData();
		List<CmsVersion> cmsVersionLst = commonService.getAllCmsVersion();
		model.addAttribute("cmsVersionLst", cmsVersionLst);

		return "home";
	}

	@RequestMapping(value = "/mergeData.htm", method = RequestMethod.GET)
	public String cmsMerge(HttpServletRequest request, HttpServletResponse response, @RequestParam String endDate)
			throws Exception {

		Map<Integer,String> moduleMap=commonService.mergeCmsData(endDate);
		Map<Integer,Integer> otherModule=new HashMap<Integer,Integer>();
		Map<Integer,Integer> mp4Module=new HashMap<Integer,Integer>();
	    commonService.removeModules(endDate);
	    String fileModule=env.getRequiredProperty("document.store.excelFileModule");
	    String mp4FileModule=env.getRequiredProperty("document.store.excelMp4Module");
	    int sizeFile=Integer.parseInt(env.getRequiredProperty("module.sizeOfOtherModule"));
	    int sizeMp4=Integer.parseInt(env.getRequiredProperty("module.sizeOfMp4rModule"));
	
		
	    Path path = Paths.get(fileModule);
	    Path path2 = Paths.get(mp4FileModule);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        
        if (!Files.exists(path2)) {
            try {
                Files.createDirectories(path2);
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        }
        
	    
	    
		Iterator it = moduleMap.entrySet().iterator();
		
			while(it.hasNext())
			{
				Map.Entry pair = (Map.Entry) it.next();
				String[] val=pair.getValue().toString().split("\\@");
				if(val[1].equals("1"))
				{
					mp4Module.put(Integer.parseInt(pair.getKey().toString()), Integer.parseInt(val[0].toString()));
				}else
				{
					otherModule.put(Integer.parseInt(pair.getKey().toString()), Integer.parseInt(val[0].toString()));
				}
					
				
			}
			Iterator otherMoudelIt = otherModule.entrySet().iterator();
			int k=1,count=1,rowCount = 1;
		XSSFSheet sheet = null;
		XSSFRow row;
		XSSFWorkbook workbook = null;
		while (otherMoudelIt.hasNext()) {

			
			if (k % sizeFile == 1) {
				workbook = new XSSFWorkbook();
				sheet = workbook.createSheet("report");
				row = sheet.createRow(0);
				row.createCell(0).setCellValue("Module Old Id");
				row.createCell(1).setCellValue("Module New Id");

			}
			row = sheet.createRow(rowCount);
			Map.Entry pair = (Map.Entry) otherMoudelIt.next();
			row.createCell(0).setCellValue(pair.getKey().toString());
			row.createCell(1).setCellValue(pair.getValue().toString());
			rowCount++;
			if (k % sizeFile == 0 || k==otherModule.size()) {
				FileOutputStream out = new FileOutputStream(
						fileModule+"/newModuelFiles" + count + ".xlsx");
				workbook.write(out);
				count++;
				rowCount=1;
			}
			k++;
				Runtime.getRuntime().gc();
		}
		
		Iterator mp4MoudelIt = mp4Module.entrySet().iterator();
		int count1=1,rowCount1 = 1, k1=1;
		XSSFSheet sheet1 = null;
		XSSFRow row1;
		XSSFWorkbook workbook1 = null;
		
		while (mp4MoudelIt.hasNext()) {
			if (k1 % sizeMp4 == 1) {
				workbook1 = new XSSFWorkbook();
				sheet1 = workbook1.createSheet("report");
				row1 = sheet1.createRow(0);
				row1.createCell(0).setCellValue("Module Old Id");
				row1.createCell(1).setCellValue("Module New Id");
			}
			row1 = sheet1.createRow(rowCount1);
			Map.Entry pair1 = (Map.Entry) mp4MoudelIt.next();
			row1.createCell(0).setCellValue(pair1.getKey().toString());
			row1.createCell(1).setCellValue(pair1.getValue().toString());
			rowCount1++;
			if (k1 % sizeMp4 == 0 || k1==mp4Module.size()) {
				FileOutputStream out = new FileOutputStream(
						mp4FileModule+"/mp4ModuelFiles" + count1 + ".xlsx");
				workbook1.write(out);
				count1++;
				rowCount1=1;
		}
		k1++;
				Runtime.getRuntime().gc();
		
		
		}

		return null;
	}

	@RequestMapping(value = "/uploadModule.htm", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String uploadModule(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mpf) {

		System.out.println("-----------------Start-----------------");

		String msg = null;
		try {
			File excelFile = new File(mpf.getOriginalFilename());
			String fileName = excelFile.getName();
			mpf.transferTo(excelFile);

			if (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xlsx")
					|| fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("xls"))
				msg = commonService.uploadModule(excelFile);
				
			else
				msg = "Please provide excel file";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;

	}

}
