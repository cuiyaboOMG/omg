package com.omg.enumerate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
* @Author:         cyb
* @CreateDate:     2019/5/8 18:58
*/
public class OmgEnumSerializer extends JsonSerializer<OmgEnum> {

    @Override
    public void serialize(OmgEnum value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("value", value.getValue());
        jgen.writeStringField("chinese", value.getChinese());
        jgen.writeEndObject();
    }

}
