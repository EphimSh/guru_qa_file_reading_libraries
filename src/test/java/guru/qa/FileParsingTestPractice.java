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



import static org.junit.jupiter.api.Assertions.*;

public class FileParsingTestPractice {
    ZipFile zf;


    {
        try {
            zf = new ZipFile(new File("src/test/resources/homework/zip_sample.zip"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void csvReadFromZipTest() throws Exception {
        try (InputStream inputStream = zf.getInputStream(zf.getEntry("some.csv"))) {
            Reader reader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> content = csvReader.readAll();
            assertArrayEquals(new String[]{"okay", "test"}, content.get(0));
        }
    }

    @Test
    void jsonReadFromZipTest() throws Exception {
        try (InputStream inputStream = zf.getInputStream(zf.getEntry("example.json"))) {
            ObjectMapper mapper = new ObjectMapper();
            Reader reader = new InputStreamReader(inputStream);
            ExampleModel exampleModel = mapper.readValue(reader, ExampleModel.class);
            assertEquals("simple json", exampleModel.getMyTitle());
            assertEquals(3, exampleModel.getPerson().getId());
            assertArrayEquals(new String[]{"QA", "Java", "JavaScript", "postman", "JUnit"}, exampleModel.getPerson().getSkills());
            assertTrue(exampleModel.getPerson().isSelenide());
            assertFalse(exampleModel.getPerson().isSelenium());
        }
    }

    @Test
    void xlsReadFromZipTest() throws Exception {
        try (InputStream inputStream = zf.getInputStream(zf.getEntry("file.xlsx"))) {
            XLS xls = new XLS(inputStream);
            System.out.println();
            assertEquals("check", xls.excel.getSheetAt(0)
                    .getRow(0).getCell(0).getStringCellValue());
            assertEquals("test", xls.excel.getSheetAt(0)
                    .getRow(1).getCell(0).getStringCellValue());
        }
    }

    @Test
    void pngNameFromZipTest() {
        ZipEntry entry = zf.getEntry("pepe.png");
        assertEquals("pepe.png", entry.getName());
    }

}

