package pl.edu.icm.cermine.bibref.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * Bibliographic reference entry, modelled after BibTeX format.
 * 
 * @author Lukasz Bolikowski (bolo@icm.edu.pl)
 * 
 */
public class BibEntry {

    protected String type;
    protected String key;
    protected String text;
    protected final SortedMap<String, List<BibEntryField>> fields = new TreeMap<String, List<BibEntryField>>();

    public BibEntry() {
    }

    public BibEntry(String type) {
        this.type = type;
    }

    public BibEntry(String type, String key) {
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public BibEntry setType(String type) {
        this.type = type;
        return this;
    }

    public String getKey() {
        return key;
    }

    public BibEntry setKey(String key) {
        this.key = key;
        return this;
    }

    public String getText() {
        return text;
    }

    public BibEntry setText(String text) {
        this.text = text;
        return this;
    }

    public Set<String> getFieldKeys() {
        return fields.keySet();
    }

    public BibEntryField getFirstField(String key) {
        List<BibEntryField> fieldList = fields.get(key);
        if (fieldList == null) {
            return null;
        }
        return (fieldList.isEmpty()) ? null : fieldList.get(0);
    }

    public String getFirstFieldValue(String key) {
        BibEntryField field = getFirstField(key);
        if (field == null) {
            return null;
        }
        return field.getText();
    }

    public List<BibEntryField> getAllFields(String key) {
        return (fields.get(key) == null) ? new ArrayList<BibEntryField>() : fields.get(key);
    }

    public List<String> getAllFieldValues(String key) {
        List<BibEntryField> beFields = getAllFields(key);
        List<String> values = new ArrayList<String>();

        for (BibEntryField field : beFields) {
            values.add(field.getText());
        }
        return values;
    }

    public BibEntry addField(String key, String value) {
        if (key == null || value == null) {
            return this;
        }
        return addField(key, new BibEntryField(value));
    }

    public BibEntry setField(String key, String value) {
        if (key == null || value == null) {
            return this;
        }
        return setField(key, new BibEntryField(value));
    }

    public BibEntry addField(String key, String value, int startIndex, int endIndex) {
        if (key == null || value == null) {
            return this;
        }
        return addField(key, new BibEntryField(value, startIndex, endIndex));
    }

    public BibEntry setField(String key, String value, int startIndex, int endIndex) {
        if (key == null || value == null) {
            return this;
        }
        return setField(key, new BibEntryField(value, startIndex, endIndex));
    }

    public BibEntry addField(String key, BibEntryField field) {
        if (key == null || field == null) {
            return this;
        }
        if (fields.get(key) == null) {
            fields.put(key, new ArrayList<BibEntryField>());
        }
        fields.get(key).add(field);

        return this;
    }

    public BibEntry setField(String key, BibEntryField field) {
        if (key == null || field == null) {
            return this;
        }
        if (fields.get(key) != null) {
            fields.get(key).clear();
        }
        return addField(key, field);
    }

    public String generateKey() {
        String result = "Unknown";
        if (fields.get(FIELD_AUTHOR) != null && fields.get(FIELD_AUTHOR).size() > 0) {
            result = fields.get(FIELD_AUTHOR).get(0).getText();
            Pattern pattern = Pattern.compile("^[\\p{L}' ]+");
            Matcher matcher = pattern.matcher(result);
            result = matcher.find() ? matcher.group().replaceAll("[' ]", "") : "Unknown";
        }
        if (fields.get(FIELD_YEAR) != null) {
            result += fields.get(FIELD_YEAR).get(0).getText();
        }
        return result;
    }

    /** Escapes curly braces and underscores. */
    protected String escape(String text) {
        text = text.replace("{", "\\{");
        text = text.replace("}", "\\}");
        text = text.replace("_", "\\_");
        return text;
    }

    public String toBibTeX() {
        StringBuilder sb = new StringBuilder();
        sb.append('@').append(type == null ? TYPE_MISC : type);
        sb.append('{').append(key == null ? generateKey() : key).append(",\n");
        for (String field : fields.keySet()) {
            sb.append('\t').append(field).append(" = {");
            List<String> values = new ArrayList<String>();
            for (BibEntryField bef : fields.get(field)) {
                values.add(bef.getText());
            }
            sb.append(escape(StringUtils.join(values, ", "))).append("},\n");
        }
        sb.append('}');
        return sb.toString();
    }
    /**
     * Returns an array of authors. Possibly empty, never <code>null</code>.
     * 
     * @return an array of authors. Possibly empty, never <code>null</code>.
     */
    /*    public String[] getAuthors() {
    if (fields.get(FIELD_AUTHOR) == null) {
    return new String[0];
    }
    String[] authors = new String[fields.get(FIELD_AUTHOR).size()];
    int i = 0;
    for (BibEntryField authorField : fields.get(FIELD_AUTHOR)) {
    authors[i] = authorField.getValue();
    i++;
    }

    return authors;
    }

    public String[] getEditors() {
    if (fields.get(FIELD_EDITOR) == null) {
    return new String[0];
    }
    String[] editors = new String[fields.get(FIELD_EDITOR).size()];
    int i = 0;
    for (BibEntryField editorField : fields.get(FIELD_EDITOR)) {
    editors[i] = editorField.getValue();
    i++;
    }

    return editors;
    }
     */
    /* Field constants */
    public static final String FIELD_ABSTRACT = "abstract";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_AFFILIATION = "affiliation";
    public static final String FIELD_ANNOTE = "annote";
    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_BOOKTITLE = "booktitle";
    public static final String FIELD_CHAPTER = "chapter";
    public static final String FIELD_CONTENTS = "contents";
    public static final String FIELD_COPYRIGHT = "copyright";
    public static final String FIELD_CROSSREF = "crossref";
    public static final String FIELD_DOI = "doi";
    public static final String FIELD_EDITION = "edition";
    public static final String FIELD_EDITOR = "editor";
    public static final String FIELD_HOWPUBLISHED = "howpublished";
    public static final String FIELD_INSTITUTION = "institution";
    public static final String FIELD_ISBN = "isbn";
    public static final String FIELD_ISSN = "issn";
    public static final String FIELD_JOURNAL = "journal";
    public static final String FIELD_KEY = "key";
    public static final String FIELD_KEYWORDS = "keywords";
    public static final String FIELD_LANGUAGE = "language";
    public static final String FIELD_LCCN = "lccn";
    public static final String FIELD_LOCATION = "location";
    public static final String FIELD_MONTH = "month";
    public static final String FIELD_MRNUMBER = "mrnumber";
    public static final String FIELD_NOTE = "note";
    public static final String FIELD_NUMBER = "number";
    public static final String FIELD_ORGANIZATION = "organization";
    public static final String FIELD_PAGES = "pages";
    public static final String FIELD_PUBLISHER = "publisher";
    public static final String FIELD_SCHOOL = "school";
    public static final String FIELD_SERIES = "series";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_URL = "url";
    public static final String FIELD_VOLUME = "volume";
    public static final String FIELD_YEAR = "year";

    /* Type constants */
    public static final String TYPE_ARTICLE = "article";
    public static final String TYPE_BOOK = "book";
    public static final String TYPE_BOOKLET = "booklet";
    public static final String TYPE_INBOOK = "inbook";
    public static final String TYPE_INCOLLECTION = "incollection";
    public static final String TYPE_INPROCEEDINGS = "inproceedings";
    public static final String TYPE_MANUAL = "manual";
    public static final String TYPE_MASTERSTHESIS = "mastersthesis";
    public static final String TYPE_MISC = "misc";
    public static final String TYPE_PHDTHESIS = "phdthesis";
    public static final String TYPE_PROCEEDINGS = "proceedings";
    public static final String TYPE_TECHREPORT = "techreport";
    public static final String TYPE_UNPUBLISHED = "unpublished";
}