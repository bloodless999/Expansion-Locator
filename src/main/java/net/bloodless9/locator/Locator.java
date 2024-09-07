package net.bloodless9.locator;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public final class Locator extends PlaceholderExpansion {

    private static final String API_URL = "https://ipapi.co/";

    private static final HashMap<UUID, String> countryHashMap = new HashMap<>();

    private static final HashMap<UUID, String> countryNameHashMap = new HashMap<>();

    private static final HashMap<UUID, String> cityHashMap = new HashMap<>();

    private static final HashMap<UUID, String> regionHashMap = new HashMap<>();

    private static final HashMap<UUID, String> regionCodeHashMap = new HashMap<>();

    private static final HashMap<UUID, String> currencyHashMap = new HashMap<>();

    private static final HashMap<UUID, String> currencyNameHashMap = new HashMap<>();

    @Override
    public @NotNull String getIdentifier() {
        return "locator";
    }

    @Override
    public @NotNull String getAuthor() {
        return "bloodlessboat9";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean register() {
        return super.register();
    }
    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        String playerIP = player.getPlayer().getAddress().getHostString();
        UUID playerUUID = player.getUniqueId();
        if(params.equalsIgnoreCase("ip")){
            return playerIP;
        }
        else if(params.equalsIgnoreCase("country")){
            if(countryHashMap.get(playerUUID) == null){
                try {
                    countryHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return countryHashMap.get(playerUUID);
        }
        else if(params.equalsIgnoreCase("country_name")){
            if(countryNameHashMap.get(playerUUID) == null){
                try {
                    countryNameHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return countryNameHashMap.get(playerUUID);
        }
        else if(params.equalsIgnoreCase("city")){
            if(cityHashMap.get(playerUUID) == null){
                try {
                    cityHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return cityHashMap.get(playerUUID);
        }
        else if(params.equalsIgnoreCase("region")){
            if(regionHashMap.get(playerUUID) == null){
                try {
                    regionHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return regionHashMap.get(playerUUID);
        }
        else if(params.equalsIgnoreCase("region_code")){
            if(regionCodeHashMap.get(playerUUID) == null){
                try {
                    regionCodeHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return regionCodeHashMap.get(playerUUID);
        }
        else if(params.equalsIgnoreCase("currency")){
            if(currencyHashMap.get(playerUUID) == null){
                try {
                    currencyHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return currencyHashMap.get(playerUUID);
        }
        else if(params.equalsIgnoreCase("currency_name")){
            if(currencyNameHashMap.get(playerUUID) == null){
                try {
                    currencyNameHashMap.put(playerUUID, parseFromJson(getJsonFromApi(playerIP), params.toLowerCase()));
                } catch (IOException ignore) {
                }
            }
            return currencyNameHashMap.get(playerUUID);
        }
        return null;
    }

    public static String getJsonFromApi(String ipAddress) {
        String url = API_URL + ipAddress + "/json/";
        return fetchData(url);
    }

    private static String fetchData(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String parseFromJson(String jsonResponse, String object) throws IOException {
        if (jsonResponse != null) {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            return jsonObject.getString(object);
        }
        return null;
    }
}
