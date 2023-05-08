package me.justeli.trim.api;

import com.google.gson.JsonParser;
import net.md_5.bungee.api.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

/* Eli @ April 29, 2021 (me.justeli.trim.api) */
public class Util
{
    private static final Pattern RGB_COLOR = Pattern.compile("(?<!\\\\)(&#[a-fA-F0-9]{6})");

    public static String color (String msg)
    {
        return ChatColor.translateAlternateColorCodes('&', parseRGB(msg));
    }

    private static String parseRGB (String message)
    {
        var matcher = RGB_COLOR.matcher(message);
        while (matcher.find())
        {
            String color = message.substring(matcher.start(), matcher.end());
            String hex = color.replace("&", "").toUpperCase();
            message = message.replace(color, ChatColor.of(hex).toString());
            matcher = RGB_COLOR.matcher(message);
        }

        return message;
    }

    public static String getLatestVersion (String githubRepo)
    {
        try
        {
            URL url = URI.create("https://api.github.com/repos/" + githubRepo + "/releases/latest").toURL();
            var request = url.openConnection();
            request.connect();

            JsonParser parser = new JsonParser();
            return parser.parse(
                new InputStreamReader((InputStream) request.getContent())
            ).getAsJsonObject().get("tag_name").getAsString();
        }
        catch (IOException ex)
        {
            return null;
        }
    }
}
