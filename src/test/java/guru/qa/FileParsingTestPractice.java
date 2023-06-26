package guru.qa;

import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.model.ExampleModel;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class FileParsingTestPractice {

    ClassLoader cl = FileParsingTestPractice.class.getClassLoader();


    @Test
    void zipTest() throws Exception {
        ZipFile zf = new ZipFile(new File("src/test/resources/homework/zip_sample.zip"));
        ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("homework/zip_sample.zip"));
        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {

            try (InputStream inputStream = zf.getInputStream(entry)) {
                if (entry.getName().contains(".png")) assertEquals("pepe.png", entry.getName());

                if (entry.getName().contains(".json")) {
                    ObjectMapper mapper = new ObjectMapper();
                    Reader reader = new InputStreamReader(inputStream);
                    ExampleModel exampleModel = mapper.readValue(reader, ExampleModel.class);
                    assertEquals("simple json", exampleModel.getMyTitle());
                    assertEquals(3, exampleModel.getPerson().getId());
                    assertArrayEquals(new String[]{"QA", "Java", "JavaScript", "postman", "JUnit"}, exampleModel.getPerson().getSkills());
                    assertTrue(exampleModel.getPerson().isSelenide());
                    assertFalse(exampleModel.getPerson().isSelenium());
                }

                if (entry.getName().contains(".csv")) {
                    Reader reader = new InputStreamReader(inputStream);
                    CSVReader csvReader = new CSVReader(reader);
                    List<String[]> content = csvReader.readAll();
                    assertArrayEquals(new String[]{"okay", "test"}, content.get(0));
                }

                if (entry.getName().contains(".xls")) {
                    XLS xls = new XLS(inputStream);
                    assertEquals("check", xls.excel.getSheetAt(0)
                            .getRow(0).getCell(0).getStringCellValue());
                    assertEquals("test", xls.excel.getSheetAt(0)
                            .getRow(1).getCell(0).getStringCellValue());

                }
            }


        }
    }

}

