package sample.models;

import org.json.JSONArray;
import org.json.JSONObject;
import sample.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Template {
    public static Template instance;
    private static final int MIN_PARAM_LENGTH = 1;
    private static final int MAX_PARAM_LENGTH = 25;
    public static final String paramPattern = "\\{#(?<param>[a-zA-Z-_0-9]{"
            + Template.MIN_PARAM_LENGTH + "," + Template.MAX_PARAM_LENGTH + "})#}";
    private String name;
    private String body;
    private ArrayList<String> params = new ArrayList<>(0); // optional
    private int createTimestamp;
    private int updateTimestamp;
    public File activeFile;

    private Template(
            String name,
            String body,
            int createTimestamp,
            int updateTimestamp
    ) {
        this.name = name;
        this.body = body;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    public void setParams(JSONArray jsonArray) {
        for (int key = 0; key < jsonArray.length(); key++) {
            this.params.add(jsonArray.getString(key));
        }
    }

    public ArrayList<String> getParams() {
        return this.params;
    }

    public static Template parseJSON(String json) {
        if (Template.instance == null) {
            JSONObject jsonObject = new JSONObject(json);
            Template.instance = new Template(
                    jsonObject.getString("name"),
                    jsonObject.getString("body"),
                    jsonObject.getInt("createTimestamp"),
                    jsonObject.getInt("updateTimestamp")
            );
            Template.instance.setParams(jsonObject.getJSONArray("params"));
        }

        return Template.instance;
    }

    /**
     * Hardcode
     * TODO Refactor
     *
     * @return String valid json for empty template
     */
    public static String getEmptyJSON() {
        int currentTimestamp = (int) new Timestamp(System.currentTimeMillis()).getTime();
        return "{\"name\" : \"New template\", \"body\" : \"\", \"params\" : [], \"createTimestamp\" : "
                + currentTimestamp + ", updateTimestamp: " + currentTimestamp + "}";
    }

    public String toJson() {
        return new JSONObject(this).toString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public int getCreateTimestamp() {
        return createTimestamp;
    }

    public int getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(int updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public void setBody(String text) {
        this.parseBody(text);
        this.body = text;
    }

    private void parseBody(String text) {
        Matcher matcher = Pattern.compile(Template.paramPattern).matcher(text);
        while (matcher.find()) {
            this.params.add(matcher.group("param"));
        }
    }

    public void writeToFile() {
        try {
            if (this.activeFile == null) {
                this.activeFile = new File(Main.currentPath + "/" + this.createTimestamp + ".json");
            }
            System.out.println(Main.currentPath);
            FileWriter writer = new FileWriter(
                    // TODO add setting template folder
                    this.activeFile
            );
            writer.write(this.toJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
