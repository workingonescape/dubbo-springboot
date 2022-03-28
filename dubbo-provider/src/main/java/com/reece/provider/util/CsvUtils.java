package com.reece.provider.util;

import com.alibaba.spring.context.config.ConfigurationBeanBinder;
import com.alibaba.spring.util.PropertySourcesUtils;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Reece
 * @createTime 2022/03/17 01:24:30
 * @Description
 */
public class CsvUtils {


	//CSV文件分隔符
	private final static String NEW_LINE_SEPARATOR = "\n";

	public static void main(String[] args) throws Exception {
		String[] headers = new String[]{"交易对方", "金额"};

		String filePath = "/Users/reece/Downloads/wechatbill.xls";


		CsvUtils csvUtils = new CsvUtils(filePath);
		// 对读取Excel表格内容测试

		Map<Integer, Map<Integer, Object>> map = csvUtils.readExcelContent();

		System.out.println("获得Excel表格的内容:");

		List<Map<Integer, Object>> values = new ArrayList<>(map.values());


		Map<String, BigDecimal> totalMap = new HashMap<>();


		for (int i = 0; i < values.size(); i++) {
			Map<Integer, Object> integerObjectMap = values.get(i);
			String item = String.valueOf(integerObjectMap.get(0)).trim();
			BigDecimal count = new BigDecimal(String.valueOf(integerObjectMap.get(1)));
			if (!totalMap.containsKey(item)) {
				totalMap.put(item, count);
			}else {
				BigDecimal total = totalMap.get(item);
				totalMap.put(item, total.add(count));
			}
		}

		Map<String, BigDecimal> result = new HashMap<>();

		totalMap.forEach((k,v)->{
			if (v.subtract(new BigDecimal("0")).intValue() > 0) {
				result.put(k, v);
			}
		});


		System.out.println(result);


	}


	/**
	 * 读取csv文件
	 *
	 * @param filePath 文件路径
	 * @param headers  csv列头
	 * @return CSVRecord 列表
	 * @throws IOException
	 **/
	public static List<CSVRecord> readCSV(String filePath, String[] headers) throws IOException {

		//创建CSVFormat
		CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);

		FileReader fileReader = new FileReader(filePath);

		//创建CSVParser对象
		CSVParser parser = new CSVParser(fileReader, formator);

		List<CSVRecord> records = parser.getRecords();

		parser.close();
		fileReader.close();

		return records;
	}


	/**
	 * 写入csv文件
	 *
	 * @param headers  列头
	 * @param data     数据内容
	 * @param filePath 创建的csv文件路径
	 * @throws IOException
	 **/
	public static void writeCsv(String[] headers, List<String[]> data, String filePath) throws IOException {

		//初始化csvformat
		CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		//创建FileWriter对象
		FileWriter fileWriter = new FileWriter(filePath);

		//创建CSVPrinter对象
		CSVPrinter printer = new CSVPrinter(fileWriter, formator);

		//写入列头数据
		printer.printRecord(headers);

		if (null != data) {
			//循环写入数据
			for (String[] lineData : data) {

				printer.printRecord(lineData);

			}
		}

		System.out.println("CSV文件创建成功,文件路径:" + filePath);

	}

	/**
	 * 读取csv文件内容
	 *
	 * @return 返回csv文件中的数据
	 * @throws Exception
	 */
	public static List<String[]> read(String filePath) throws Exception {
		if (StringUtils.isEmpty(filePath)) {
			return new ArrayList<>();
		}

		FileInputStream inputStream = new FileInputStream(filePath);
		//1. 存储csv文件中的内容
		List<String[]> csvList = new ArrayList<String[]>();

		//2. 创建CsvReader
		CsvReader reader = new CsvReader(inputStream, ',', StandardCharsets.UTF_8);

		//3. 跳过表头,如果需要表头的话，不要写这句
		reader.readHeaders();

		//4.逐行读入除表头的数据
		while (reader.readRecord()) {
			csvList.add(reader.getValues());
		}

		//5. 释放资源
		reader.close();
		return csvList;
	}

	/**
	 * 数据写入csv文件
	 *
	 * @param list     UTF-8编码写入csv文件的内容
	 * @param filePath 写入的csv文件的指定路劲
	 * @throws Exception
	 */
	public static void write(List<String[]> list, String filePath) throws Exception {
		CsvWriter wr = new CsvWriter(filePath, ',', StandardCharsets.UTF_8);
		for (int i = 0; i < list.size(); i++) {
			wr.writeRecord(list.get(i));
		}
		wr.close();
	}

	private Workbook wb;
	private Sheet sheet;
	private Row row;

	public CsvUtils(String filepath) {
		if (filepath == null) {
			return;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取Excel表格表头的内容
	 *
	 * @param
	 * @return String 表头内容的数组
	 * @author zengwendong
	 */
	public String[] readExcelTitle() throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = row.getCell(i).getCellFormula();
		}
		return title;
	}

	/**
	 * 读取Excel数据内容
	 *
	 * @param
	 * @return Map 包含单元格数据内容的Map对象
	 * @author zengwendong
	 */
	public Map<Integer, Map<Integer, Object>> readExcelContent() throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();

		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);

		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			int j = 0;
			Map<Integer, Object> cellValue = new HashMap<Integer, Object>();
			while (j < colNum) {
				if (row == null) {
					continue;
				}
				Cell cell = row.getCell(j);
				Object obj = getCellFormatValue(cell);
				cellValue.put(j, obj);
				j++;
			}
			content.put(i, cellValue);
		}
		return content;
	}

	/**
	 * 根据Cell类型设置数据
	 *
	 * @param cell
	 * @return
	 * @author zengwendong
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
				case Cell.CELL_TYPE_FORMULA: {
					// 判断当前的cell是否为Date
					if (DateUtil.isCellDateFormatted(cell)) {
						// 如果是Date类型则，转化为Data格式
						// data格式是带时分秒的：2013-7-10 0:00:00
						// cellvalue = cell.getDateCellValue().toLocaleString();
						// data格式是不带带时分秒的：2013-7-10
						Date date = cell.getDateCellValue();
						cellvalue = date;
					} else {// 如果是纯数字

						// 取得当前Cell的数值
						cellvalue = String.valueOf(cell.getNumericCellValue());
					}
					break;
				}
				case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
					// 取得当前的Cell字符串
					cellvalue = cell.getRichStringCellValue().getString();
					break;
				default:// 默认的Cell值
			}
		}
		return cellvalue;
	}








}

