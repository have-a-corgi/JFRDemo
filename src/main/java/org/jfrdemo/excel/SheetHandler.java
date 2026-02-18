package org.jfrdemo.excel;

import org.apache.poi.xssf.model.SharedStrings;
import org.springframework.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class SheetHandler extends DefaultHandler {
    private final SharedStrings sst;
    private String lastContents;
    private boolean nextIsString;
    private boolean inlineStr;
    private final LruCache<Integer, String> lruCache = new LruCache<>(50);

    private static class LruCache<A, B> extends LinkedHashMap<A, B> {
        private final int maxEntries;

        public LruCache(final int maxEntries) {
            super(maxEntries + 1, 1.0f, true);
            this.maxEntries = maxEntries;
        }

        @Override
        protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
            return super.size() > maxEntries;
        }
    }

    public SheetHandler(SharedStrings sst) {
        this.sst = sst;
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        // c => cell
        if (name.equals("c")) {
            // Print the cell reference
            System.out.print(attributes.getValue("r") + " - ");
            // Figure out if the value is an index in the SST
            String cellType = attributes.getValue("t");
            nextIsString = cellType != null && cellType.equals("s");
            inlineStr = cellType != null && cellType.equals("inlineStr");
        }
        // Clear contents cache
        lastContents = "";
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if (nextIsString && !StringUtils.isEmpty(lastContents)) {
            Integer idx = Integer.valueOf(lastContents);
            lastContents = lruCache.get(idx);
            if (lastContents == null && !lruCache.containsKey(idx) && sst != null) {
                lastContents = sst.getItemAt(idx).getString();
                lruCache.put(idx, lastContents);
            }
            nextIsString = false;
        }

        // v => contents of a cell
        // Output after we've seen the string contents
        if (name.equals("v") || (inlineStr && name.equals("c"))) {
            System.out.println(lastContents);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
        lastContents += new String(ch, start, length);
    }
}

