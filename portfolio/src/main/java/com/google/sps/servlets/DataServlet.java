// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.io.*; 

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comments")
public class DataServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lang = request.getParameter("lang");
        // System.out.println(lang);
        Query query = new Query("Comment").addSort("timestamp", SortDirection.ASCENDING);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        // Do the translation.
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        ArrayList<String> comments = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String text = (String) entity.getProperty("text");
            long timestamp = (long) entity.getProperty("timestamp");
            String  comment = text;
            Translation translation =
                translate.translate(comment, Translate.TranslateOption.targetLanguage(lang);
            String translatedText = translation.getTranslatedText();
            comments.add(translatedText);
        }

        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String json = convertToJson(comments);
        response.getWriter().println(json);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the input from the form.
        String text = getParameter(request, "text-input", "");
        long timestamp = System.currentTimeMillis();

        Entity taskEntity = new Entity("Comment");
        taskEntity.setProperty("text", text);
        taskEntity.setProperty("timestamp", timestamp);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity);

        // Redirect back to the HTML page.
        response.sendRedirect("/index.html");
    }

    /**
     * Converts a ServerStats instance into a JSON string using the Gson library.
     */
    private String convertToJson(ArrayList arr) {
        Gson gson = new Gson();
        String json = gson.toJson(arr);
        return json;
    }

    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
        return defaultValue;
        }
        return value;
    }
}
