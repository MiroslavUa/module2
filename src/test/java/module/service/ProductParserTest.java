package module.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class ProductParserTest {
    private ProductParser target;

    private List<List<String>> info;

    @BeforeEach
    void setUp() {
        target = new ProductParser();
        info = target.getInfo();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void parseCsvTestCorrectLines(){
        target.parseCsv("correctLines.csv");
        Assertions.assertEquals(11, target.getInfo().size());
    }

    @Test
    public void parseCsvWrongLines(){
        target.parseCsv("wrongLines.csv");
        Assertions.assertEquals(4, target.getInfo().size());
        for(List<String> lists : info) {
            for(String s : lists){
                Assertions.assertTrue(s.contains("ERROR"));
            }
        }
    }
}