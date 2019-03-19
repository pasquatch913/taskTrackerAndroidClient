package com.example.namegame

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate

class LocalDateAdapter: TypeAdapter<LocalDate>(){

    override fun write(jsonWriter: JsonWriter, localDate: LocalDate?) {
        if (localDate == null) {
            jsonWriter.nullValue()
        }
        else {
            jsonWriter.value(localDate.toString())
        }
    }

    override fun read(jsonReader: JsonReader): LocalDate {
        if (jsonReader.peek() == JsonToken.NULL) {
            // TODO is this the behavior we want out of our children?
            return LocalDate.now()
        }
        else {
            return LocalDate.parse(jsonReader.nextString())
        }
    }

}