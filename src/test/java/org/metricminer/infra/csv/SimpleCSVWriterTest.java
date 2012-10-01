package org.metricminer.infra.csv;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

public class SimpleCSVWriterTest {

    private SimpleCSVWriter writer;
    private List<Map<String, Object>> results;

    @Before
    public void setUp() {
        writer = new SimpleCSVWriter();
        results = new ArrayList<Map<String, Object>>();
    }

    @Test
    public void shouldWriteCsv() {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("column1", "value11");
        row.put("column2", "value21");
        results.add(row);
        
        row = new HashMap<String, Object>();
        row.put("column1", "value21");
        row.put("column2", "value22");
        results.add(row);
    
        ByteArrayOutputStream csvOutputStream = new ByteArrayOutputStream();
        writer.write(csvOutputStream, results);
        
        String expected = "\"column1\";\"column2\";\n" +
                "\"value11\";\"value21\";\n" +
                "\"value21\";\"value22\";\n";
        String csv = new String(csvOutputStream.toByteArray());

        assertEquals(expected, csv);
    }
    
    @Test
    public void shouldReplaceQuotes() {
        Map<String, Object> row = new HashMap<String, Object>();
        row.put("column1", "value11");
        row.put("column2", "some \"quotes\"");
        results.add(row);
        
        ByteArrayOutputStream csvOutputStream = new ByteArrayOutputStream();
        writer.write(csvOutputStream, results);
        
        String expected = "\"column1\";\"column2\";\n" +
                "\"value11\";\"some 'quotes'\";\n";
        String csv = new String(csvOutputStream.toByteArray());

        assertEquals(expected, csv);
    }
    
}
