package hkust.cse.calendar.apptstorage;

import hkust.cse.calendar.gui.Utility;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

public class ApptStorageNullImpl extends ApptStorage {

	private User defaultUser = null;
	
	public ApptStorageNullImpl( User user )
	{
		defaultUser = user;
		mAppts = new HashMap<Integer, Appt>();
	}
	
	@Override
	public void SaveAppt(Appt appt) {
		ArrayList<Appt> apptList = new ArrayList<Appt>(mAppts.values());
		boolean overlap = false;
		System.out.println(appt.getID());
		if (!apptList.isEmpty()) {
			for (Appt anAppt : apptList) {
				if (anAppt.TimeSpan().Overlap(appt.TimeSpan()))
					overlap = true;
					break;
			}
		}
		
		if (overlap == false) {
			// We put the pair appointment and its id into the HashMap
			int key = LengthInMemory() + 1;
			mAppts.put(key, appt);

			
		}
	}

	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		// TODO Retrieve Appointments by time
		// Create an array list, add items in, convert back to regular array and return
		
		// Retrieve the whole Appointments (in a set of keys) into the container ArrayList
		ArrayList<Appt> apptList = new ArrayList<Appt>(mAppts.values());
		// Create a container ArrayList to contain the appointments which fall inside the requirement
		ArrayList<Appt> apptsByTime = new ArrayList<Appt>();
		for (Appt anAppt: apptList) {
			
			// The below code returns false, which is weird.
			// System.out.println(anAppt.TimeSpan().EndTime().before(d.EndTime()));
			// The below code returns true, which is weird.
			// System.out.println(anAppt.TimeSpan().EndTime().after(d.EndTime()));
			
			// TODO MAKE YOUR OWN AFTER AND BEFORE AND EQUAL TIMESTAMP CHECKER
			// Need to do more test cases
			
			// Check which appointments is inside TimeSpan d
			if (Utility.AfterBeforeEqual(anAppt.TimeSpan().StartTime(), d.StartTime()) == 1  | 
					Utility.AfterBeforeEqual(anAppt.TimeSpan().StartTime(), d.StartTime()) == 0) {
			    if (Utility.AfterBeforeEqual(anAppt.TimeSpan().EndTime(), d.EndTime()) == - 1 | 
			    		Utility.AfterBeforeEqual(anAppt.TimeSpan().EndTime(), d.EndTime()) == 0) {
			    	apptsByTime.add(anAppt);
			    }
			}		
		}
		
		if (apptsByTime.isEmpty()) {
			return null;
		}
		else {
			// Convert ArrayList back into array of Appt
			Appt[] apptArray = new Appt[apptsByTime.size()];
			apptArray = apptsByTime.toArray(apptArray);
			return apptArray;	
		}
 	}		 	
 		 
 	@Override		 	
 	public Appt[] RetrieveAppts(User entity, TimeSpan time) {		 	
 		// TODO Retrieve Appointments by time and user (for now its default)
 		// Call RetrieveAPpts with time parameter
 		if (entity.equals(defaultUser)) {
 			return RetrieveAppts(time);
 		}
 		return null;
	}

	@Override
	public Appt RetrieveAppts(int joinApptID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateAppt(Appt appt) {
		int apptID = appt.getID();
		// According to Java Doc, If the map previously contained a mapping for this key, 
		// the old value is replaced by the specified value.
		mAppts.put(apptID, appt);
	}

	@Override
	public void RemoveAppt(Appt appt) {
		// TODO Auto-generated method stub
		mAppts.remove(appt, appt.getID());

	}

	@Override
	public User getDefaultUser() {
		// TODO Auto-generated method stub
		return defaultUser;
	}

	@Override
	public void LoadApptFromXml() {
		// TODO Auto-generated method stub

	}
	
	/* creating a Location array */
	Location[] _locations;
	
	@Override
	public Location[] getLocationList(){
		return _locations;
	}
	
	@Override
	public void setLocationList(Location[] locations){
		this._locations = locations;
	}
	
	@Override
	public int getLocationCapacity(){
		return this._locations.length;
	}

	@Override
	// Return the length of mAppts
	public int LengthInMemory() {
		return mAppts.size();
	}
}