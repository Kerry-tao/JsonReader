package json_reader;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;

class RecordCollection {
    TopLayer topPart;
    protected ArrayList<ArrayList<Record>> records;

    public RecordCollection() {
        this.topPart = new TopLayer();
        this.records = new ArrayList<>();
    }

    public void buildRecordFromJson(JsonNode jsonRecord) {
        topPart.buildWABFromJson(jsonRecord);
        JsonNode jsonRecords = jsonRecord.get("records");

        for (JsonNode jsonRecordPart : jsonRecords) {
            ArrayList<Record> recordPart = new ArrayList<>();
            for (JsonNode recordContend : jsonRecordPart) {
                Record record = new Record();
                record.buildOneRecordFromJson(recordContend);
                recordPart.add(record);
            }
            this.records.add(recordPart);
        }
    }

    @Override
    public String toString() {
        return "RecordCollection{" +
                "recordsSize=" + records.size() +
                '}';
    }
}