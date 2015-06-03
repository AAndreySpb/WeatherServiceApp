package jsonweather;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;
import android.util.JsonToken;

/**
 * Parses the Json weather data returned from the Weather Services API
 * and returns a List of JsonWeather objects that contain this data.
 */
public class WeatherJSONParser {
    /**
     * Used for logging purposes.
     */
    private final String TAG =
            this.getClass().getCanonicalName();

    /**
     * Parse the @a inputStream and convert it into a List of JsonWeather
     * objects.
     */
    public List<JsonWeather> parseJsonStream(InputStream inputStream)
            throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            return parseJsonWeatherArray(reader);
        }finally {
                reader.close();
            }
    }

    /**
     * Parse a Json stream and convert it into a List of JsonWeather
     * objects.
     */
    public List<JsonWeather> parseJsonWeatherArray(JsonReader reader)
            throws IOException {

        List<JsonWeather> messages = new ArrayList();

        reader.beginObject();
        while (reader.hasNext()) {
            messages.add(parseJsonWeather(reader));
        }
        reader.endObject();
        return messages;
    }


    /**
     * Parse a Json stream and return a JsonWeather object.
     */
    public JsonWeather parseJsonWeather(JsonReader reader)
            throws IOException {

        JsonWeather jWeather = new JsonWeather();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("weather")) {
                jWeather.setWeather(parseWeathers(reader));
            } else if (name.equals("sys")) {
                jWeather.setSys(parseSys(reader));
            } else if (name.equals("main")) {
                jWeather.setMain(parseMain(reader));
            } else if (name.equals("wind")) {
                jWeather.setWind(parseWind(reader));
            }else if(name.equals("name")) {
                jWeather.setName(reader.nextString());
            } else {
                reader.skipValue();
            }
        }
        return jWeather;
    }

    /**
     * Parse a Json stream and return a List of Weather objects.
     */
    public List<Weather> parseWeathers(JsonReader reader) throws IOException {

        List<Weather> messages = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(parseWeather(reader));
        }
        reader.endArray();
        return messages;
    }

    /**
     * Parse a Json stream and return a Weather object.
     */
    public Weather parseWeather(JsonReader reader) throws IOException {

        Weather weather = new Weather();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("description")) {
                weather.setDescription(reader.nextString());
            }else if(name.equals("icon")) {
                weather.setIcon(reader.nextString());
            }else if(name.equals("id")) {
                weather.setId(reader.nextInt());
            }else if(name.equals("main")) {
                weather.setMain(reader.nextString());
            }else
                reader.skipValue();
        }
        reader.endObject();
        return  weather;
    }

    /**
     * Parse a Json stream and return a Main Object.
     */
    public Main parseMain(JsonReader reader)
            throws IOException {

        Main main = new Main();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("grnd_level")) {
                main.setGrndLevel(reader.nextDouble());
            }else if(name.equals("humidity")) {
                main.setHumidity(reader.nextLong());
            }else if(name.equals("pressure")) {
                main.setPressure(reader.nextDouble());
            }else if(name.equals("sea_level")) {
                main.setSeaLevel(reader.nextDouble());
            }else if(name.equals("temp_max")) {
                main.setTempMax(reader.nextDouble());
            }else if(name.equals("temp_min")) {
                main.setTempMin(reader.nextDouble());
            }else if(name.equals("temp")) {
                main.setTemp(reader.nextDouble());
            }else
                reader.skipValue();
        }
        reader.endObject();
        return  main;
    }

    /**
     * Parse a Json stream and return a Wind Object.
     */
    public Wind parseWind(JsonReader reader) throws IOException {

        Wind wind = new Wind();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("deg")) {
                wind.setDeg(reader.nextDouble());
            }else if(name.equals("speed")) {
                wind.setSpeed(reader.nextDouble());
            }else
                reader.skipValue();
        }
        reader.endObject();
        return  wind;
    }

    /**
     * Parse a Json stream and return a Sys Object.
     */
    public Sys parseSys(JsonReader reader) throws IOException{

        Sys sys = new Sys();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if(name.equals("country")) {
                sys.setCountry(reader.nextString());
            }else if(name.equals("message")) {
                sys.setMessage(reader.nextDouble());
            }else if(name.equals("sunrise")) {
                sys.setSunrise(reader.nextLong());
            }else if(name.equals("sunset")) {
                sys.setSunset(reader.nextLong());
            }else
                reader.skipValue();
        }
        reader.endObject();
        return  sys;
    }
}
