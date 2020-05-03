package com.app.travelapp.data.datasources;

import com.app.travelapp.data.model.Place;
import com.app.travelapp.data.model.User;
import com.app.travelapp.utils.Result;
import com.app.travelapp.data.model.LoggedInUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSourceCache {

    private static Map<String, User> usersMap;
    private static Map<String, Place> placesMap;
    private User loggedUser;
    private static DataSourceCache instance;

    private DataSourceCache(){
        usersMap = new HashMap<>();
        User user = new User("camilop12","camilo@mail.com", "camilo123", "juan camilo");
        User user2 = new User("juana1loski","juan@mail.com", "juan123", "juan ruskin");
        usersMap.put(user.getEmail(), user);
        usersMap.put(user2.getEmail(), user2);


        Place place = new Place(UUID.randomUUID().toString()+"",
                "Guatape", "Un lugar hermoso y fascinante",
                "Guatapé, Antioquia, Colombia",
                new ArrayList<String>(), user);

        Place place2 = new Place(UUID.randomUUID().toString()+"",
                "Sopo", "Un lugar tranquilo, y perfecto para relajarse",
                "Sopó, Cundinamarca, Colombia",
                new ArrayList<String>(), user2);
        Place place3 = new Place(UUID.randomUUID().toString()+"",
                "Sopo", "Un lugar espectacular para descansar",
                "Sopó, Cundinamarca, Colombia",
                new ArrayList<String>(), user);
        placesMap = new HashMap<>();
        placesMap.put(place.getPlaceId(), place);
        placesMap.put(place2.getPlaceId(), place2);
        placesMap.put(place3.getPlaceId(), place3);
        System.out.println(placesMap.size());
    }

    public static DataSourceCache getInstance(){
        if(instance==null){
            instance = new DataSourceCache();
        }
        return instance;
    }

    // Users
    public Result<LoggedInUser> login(String email, String password) {
        try {
            // TODO: handle loggedInUser authentication
            if(usersMap.containsKey(email) && usersMap.get(email).getPassword().equals(password)){
                loggedUser = usersMap.get(email);
                //String first_name = loggedUser.getFull_name().split(" ")[0];
                LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getUsername()+"",
                        loggedUser.getEmail()+"", loggedUser.getFull_name()+"");
                return new Result.Success<>(loggedInUser);
            }
            return new Result.Error(new IOException("Invalid login"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public Result<LoggedInUser> signUp(User user){
        if(usersMap.containsKey(user.getEmail())){
            return new Result.Error(new IOException("Email has been already taken!"));
        }
        if(checkUsernameAlreadyExists(user.getUsername())){
            return new Result.Error(new IOException("Username has been already taken!"));
        }
        loggedUser = user;
        usersMap.put(user.getEmail(), user);
        //String first_name = loggedUser.getFull_name().split(" ")[0];
        LoggedInUser loggedInUser = new LoggedInUser(loggedUser.getUsername()+"",
                loggedUser.getEmail()+"", loggedUser.getFull_name()+"");
        return new Result.Success<>(loggedInUser);
    }

    private boolean checkUsernameAlreadyExists(String username){
        for(Map.Entry<String,User> entry: usersMap.entrySet()){
            if(entry.getValue().getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public List<Place> getPlaces(){
        List<Place> places = new ArrayList<>();
        for (Map.Entry<String, Place> entry: placesMap.entrySet()){
            places.add(entry.getValue());
        }
        return places;
    }

    public List<Place> getPlacesByName(String name){
        List<Place> places = new ArrayList<>();
        for(Map.Entry<String, Place> entry: placesMap.entrySet()){
            if(entry.getValue().getName().toLowerCase().equals(name.toLowerCase())){
                places.add(entry.getValue());
            }
        }
        return places;
    }

    public List<Place> getPlacesFromUser(User user) {
        List<Place> places = new ArrayList<>();
        for(Map.Entry<String, Place> entry: placesMap.entrySet()){
            if(entry.getValue().getAuthor().getEmail().equals(user.getEmail())){
                places.add(entry.getValue());
            }
        }
        return places;
    }

    public Result savePlace(Place place) {
        try{
            placesMap.put(place.getPlaceId(), place);
            return new Result.Success<>("Place was successfully saved");
        }catch(Exception ex){
            return new Result.Error(new IOException("Whoops!!!. An error occurred while saving place."));
        }
    }

    public Place getPlaceById(String placeId){
        if(placesMap.containsKey(placeId)){
            return placesMap.get(placeId);
        }
        return null;
    }

    public Result updatePlace(Place place){
        if(placesMap.containsKey(place.getPlaceId())){
            placesMap.put(place.getPlaceId(), place);
            return new Result.Success<>("Place was successfully updated");
        }else{
            return new Result.Error(new IOException("Whoops!!!. There is no place registered with the specified id"));
        }
    }

    public Result deletePlace(String placeId) {
        if(placesMap.containsKey(placeId)){
            placesMap.remove(placeId);
            return new Result.Success<>("Place was successfully deleted");
        }else{
            return new Result.Error(new IOException("Whoops!!!. There is no place registered with the specified id"));
        }
    }
}