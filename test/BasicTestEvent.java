import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import models.Evento;
import models.Participante;

import org.junit.Before;
import org.junit.Test;


public class BasicTestEvent {


	String nameOfEvent;
	String descriptionOfEvent;
	String dateOFEvent;
	List<Evento> events;
	Participante participant;

	@Before
	public void setup() {
		events = new ArrayList<>();
		nameOfEvent = "Evento teste";
		descriptionOfEvent = "descricao";
		dateOFEvent = "2014-07-03";
		participant = new Participante("user", "user@gmail.com");
		
	}

	
	@Test
	public void testCreateEventNotRegistered () {
		assertEquals(0, events.size());
		createEvent(nameOfEvent, descriptionOfEvent, dateOFEvent);
		assertEquals(1, events.size());
	}
	
	@Test
	public void testCreateEventHasAlreadyRegistered () {
		assertEquals(0, events.size());
		for (int i = 0; i < 2; i++) {
			createEvent(nameOfEvent, descriptionOfEvent, dateOFEvent);
			assertEquals(1, events.size());
		}
	}
	
	@Test
	public void testAttendAnEvent () {
		createEvent(nameOfEvent, descriptionOfEvent, dateOFEvent);
		addParticipantInEvent(nameOfEvent);
		verifyParticipantWasAdded(nameOfEvent);
	}

	/**
	 * verify participant was added
	 * @param name: String with name of event for verify if the member was added
	 */

	private void verifyParticipantWasAdded(String name) {
		events = new ArrayList<>();
		for (Evento event : events) {
			if(event.getNome().equals(name)){
				assertEquals(participant.getId(), 
						event.getParticipantes().get(0).getId());
			}
		}
	}

	/**
	 * Create event method
	 * @param name: String with name of event
	 * @param description: String with description of event
	 * @param date: String with date od event
	 */
	private void createEvent(String name, String description, String date){
		events = new ArrayList<>();
		Evento evento = new Evento(name, description, date);
		events.add(evento);

	}
	
	/**
	 * add participant in event
	 * @param name: String with name of event for add participant
	 */

	private void addParticipantInEvent (String name){		
		events = new ArrayList<>();
		for (Evento event : events) {
			if(event.getNome().equals(name)){
				event.getParticipantes().add(participant);
				events.add(event);
			}
		}
	}
	
	
	

	
	
	
	
	
}
