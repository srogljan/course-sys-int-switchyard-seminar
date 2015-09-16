package com.redhat.brq.integration.switchyard.inventory;

import com.redhat.brq.integration.switchyard.models.InventoryReply;
import com.redhat.brq.integration.switchyard.models.OrderItem;
import org.switchyard.annotations.Transformer;

import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named("CsvTransformer")
public class CsvTransformer {

    @Transformer(from = "java:com.redhat.brq.integration.switchyard.models.OrderItem[]",
            to = "{urn:com.redhat.brq.integration.exercise.inventory:1.0}reserve")
    public String transform(OrderItem[] from) {
        StringWriter writer = new StringWriter();
        Arrays.stream(from).forEach(item -> {
            writer.append(Long.toString(item.getArticleId()));
            writer.append(";");
            writer.append(Integer.toString(item.getCount()));
            writer.append(System.lineSeparator());
        });
        return writer.toString();
    }

    @Transformer(from = "{urn:com.redhat.brq.integration.exercise.inventory:1.0}response",
            to = "java:com.redhat.brq.integration.switchyard.models.InventoryReply[]")
    public InventoryReply[] reply(String from) throws IOException {
        if (from == null) return null;
        BufferedReader readed = new BufferedReader(new StringReader(from));
        String line;
        List<InventoryReply> list = new ArrayList<>();
        while ((line = readed.readLine()) != null) {
            String[] data = line.split(";");
            InventoryReply reply = new InventoryReply(
                    Long.parseLong(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])
            );
            list.add(reply);
        }
        return list.toArray(new InventoryReply[0]);
    }
}
