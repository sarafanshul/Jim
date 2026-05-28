package com.projectdelta.jim.util;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import timber.log.Timber;

public class JSONUtils {

    /**
     * Open a json file from raw and construct as class using Gson.
     *
     * @param resources Application Resource
     * @param resId Id of json resource to fetch from
     * @param classType java::class type descriptor
     * @param <T> k_class
     * @return Optional<T>
     */
    public static <T> Optional<T> getJsonFileAsClass(final Resources resources, final int resId, final Class<T> classType) {

        InputStream resourceReader = resources.openRawResource(resId);

        try {
            // ⚡ Bolt: Parse directly from the stream instead of loading the entire string into memory.
            // This reduces memory usage and avoids redundant string allocation, especially for large JSON files.
            InputStreamReader reader = new InputStreamReader(resourceReader, StandardCharsets.UTF_8);
            return Optional.of(constructUsingGson(classType, reader));
        } catch (Exception e) {
            // Crashlytics.logException(e);
            Timber.e(e, "Unhandled exception while using JSONResourceReader");
        } finally {
            try {
                resourceReader.close();
            } catch (Exception e) {
                Timber.e(e, "Unhandled exception while using JSONResourceReader");
            }
        }
        return Optional.empty();
    }

    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type   The type of the object to build.
     * @param reader The InputStreamReader containing the JSON.
     * @return An object of type T, with member fields populated using Gson.
     */
    private static <T> T constructUsingGson(final Class<T> type, final InputStreamReader reader) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(reader, type);
    }
}
