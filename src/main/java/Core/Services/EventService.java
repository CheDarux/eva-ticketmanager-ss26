package Core.Services;

import Core.Models.exceptions.EventException;
import Core.Interfaces.EventServiceInterface;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import Core.Models.Event;

public class EventService implements EventServiceInterface {

    //TODO variable to store events

    public Event createEvent(String name, String location, LocalDateTime time, int ticketsAvailable) throws EventException {

        //TODO

        return null;
    }

    @Override
    public Event getEventById(UUID id) {

        //TODO

        return null;
    }

    @Override
    public void updateEvent(Event event) throws EventException {

        //TODO

    }

    private void validateUpdatedEvent(Event event){

        //TODO

    }


    @Override
    public void deleteEvent(UUID id) {

        //TODO

    }

    @Override
    public List<Event> getAllEvents() {

        //TODO

        return null;
    }

    @Override
    public void deleteAllEvents() {

        //TODO

    }


}
