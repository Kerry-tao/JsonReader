package json_reader;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;

public class Record {
    public int key;
    public double value;
    public ArrayList<Double> datas;

    public Record() {
        this.datas = new ArrayList<Double>();
    }

    public Record(int index, double value, ArrayList<Double> datas) {
        this.key = index;
        this.value = value;
        this.datas = datas;
    }

    public void buildOneRecordFromJson(JsonNode jsonRecord) {
        if (jsonRecord.isArray()) {
            this.key = jsonRecord.get(0).asInt();
            this.value = jsonRecord.get(1).asDouble();
            JsonNode datas = jsonRecord.get(2);
            if (datas.isArray()) {
                for (JsonNode data : datas) {
                    this.datas.add(data.asDouble());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Record [key=" + key + ", value=" + value + ", datas=" + datas + "]";
    }

}