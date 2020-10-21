package ink.fujisann.learning.base.utils.office;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class ParseExcelUtil {

    public static ArrayList<ArrayList<String>> parseExcel(MultipartFile multipartFile) {
        long startTime = new Date().getTime();
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        try {
            InputStream inputStream = multipartFile.getInputStream();
            // 通过文件名后缀判断excel版本
            String fileName = multipartFile.getOriginalFilename();
            // 字符串中\需要转义\\, 正则中.需要转义\.
            String reg = "^(.*)(\\.xls)$";
            Workbook workbook = null;
            if (fileName.matches(reg)) {
                // xls
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }
            Sheet sheet0 = workbook.getSheetAt(0);
            int rowLength = sheet0.getRow(0).getPhysicalNumberOfCells();
            for (int i = 1; i < sheet0.getPhysicalNumberOfRows(); i++) {
                Row sheetRow = sheet0.getRow(i);
                ArrayList<String> cells = new ArrayList<>();
                for (int j = 0; j < rowLength; j++) {
                    Cell cell = sheetRow.getCell(j);
                    if (null != cell) {
                        // if (CellType.STRING.equals(cell.getCellType())) {
                        //     cells.add(cell.getStringCellValue());
                        // } else if (CellType.NUMERIC.equals(cell.getCellType())) {
                        //     cells.add(String.valueOf(cell.getNumericCellValue()));
                        // }
                        cell.setCellType(CellType.STRING);
                        cells.add(cell.getStringCellValue());
                    } else {
                        cells.add(null);
                    }
                }
                rows.add(cells);
            }
            // 关闭资源
            workbook.close();
            inputStream.close();
            log.info("parse excel, rows {}, cost {}ms.", rows.size(), new Date().getTime() - startTime);
            return rows;
        } catch (Exception e) {
            log.error("parse excel exception {}", e);
        }
        return rows;
    }
}
